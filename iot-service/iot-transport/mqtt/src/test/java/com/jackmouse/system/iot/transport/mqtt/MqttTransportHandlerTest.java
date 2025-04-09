package com.jackmouse.system.iot.transport.mqtt;

import com.jackmouse.system.iot.transport.TransportService;
import com.jackmouse.system.iot.transport.mqtt.MqttTransportHandler;
import com.jackmouse.system.utils.JackmouseThreadFactory;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

/**
 * @ClassName MqttTransportHandlerTest
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/8 14:13
 * @Version 1.0
 **/
@Slf4j
@ExtendWith(MockitoExtension.class)
public class MqttTransportHandlerTest {
    public static final InetSocketAddress IP_ADDR = new InetSocketAddress("127.0.0.1", 9876);
    public static final int MSG_QUEUE_LIMIT = 10;
    public static final int TIMEOUT = 30;

    @Mock
    MqttTransportContext context;
    @Mock
    SslHandler sslHandler;
    AtomicInteger packedId = new AtomicInteger();
    MqttTransportHandler handler;
    ExecutorService executor;

    @Mock
    ChannelHandlerContext ctx;

    @Spy
    TransportService transportService;

    @BeforeEach
    public void setUp() {
        lenient().doReturn(MSG_QUEUE_LIMIT).when(context).getMessageQueueSizePerDeviceLimit();
        lenient().doReturn(transportService).when(context).getTransportService();
        handler = spy(new MqttTransportHandler(context, sslHandler));
        lenient().doReturn(IP_ADDR).when(handler).getAddress(any());
    }

    private MqttConnectMessage getMqttConnectMessage() {
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(
                MqttMessageType.CONNECT,  // 消息类型：CONNECT 连接报文
                false,                     // DUP 标志（重复传输）一般 CONNECT 是 false
                MqttQoS.AT_LEAST_ONCE,    // QoS 等级，CONNECT 报文其实是没用 QoS 的
                false,                    // RETAIN 标志，对 CONNECT 也无效
                123                       // 剩余长度（Remaining Length）
        );
        MqttConnectVariableHeader variableHeader = new MqttConnectVariableHeader(
                "device",
                packedId.incrementAndGet(),
                true,
                true,
                true,
                1,
                true,
                true,
                60);
        MqttConnectPayload payload = new MqttConnectPayload(
                "clientId",
                "topic",
                "message".getBytes(StandardCharsets.UTF_8),
                "username",
                "password".getBytes(StandardCharsets.UTF_8)
        );
        return new MqttConnectMessage(mqttFixedHeader, variableHeader, payload);
    }

    MqttPublishMessage getMqttPublishMessage() {
        return getMqttPublishMessage("v1/gateway/telemetry");
    }
    MqttPublishMessage getMqttPublishMessage(String topicName) {
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(
                MqttMessageType.PUBLISH,
                true,
                MqttQoS.AT_LEAST_ONCE,
                false,
                123);
        MqttPublishVariableHeader variableHeader = new MqttPublishVariableHeader(topicName, packedId.incrementAndGet());
        ByteBuf payload = Unpooled.wrappedBuffer("{\"testKey\":\"testValue\"}".getBytes());
        return new MqttPublishMessage(mqttFixedHeader, variableHeader, payload);
    }
    @Test
    public void givenMqttConnectMessage_whenProcessMqttMsg_thenProcessConnect() {
        MqttConnectMessage msg = getMqttConnectMessage();
        willDoNothing().given(handler).processConnect(ctx, msg);
        handler.channelRead(ctx, msg);
        verify(handler, times(1)).processConnect(ctx, msg);
    }

    @Test
    public void givenQueueLimit_whenEnqueueRegularSessionMsgOverLimit_thenOK() {
        List<MqttPublishMessage> messages = Stream.generate(this::getMqttPublishMessage).limit(MSG_QUEUE_LIMIT).toList();
        messages.forEach(msg -> handler.enqueueRegularSessionMsg(ctx, msg));
        assertThat(handler.deviceSessionCtx.getMsgQueueSize(), is(MSG_QUEUE_LIMIT));
        assertThat(handler.deviceSessionCtx.getMsgQueueSnapshot(), Matchers.contains(messages.toArray()));
    }

    @Test
    public void givenQueueLimit_whenEnqueueRegularSessionMsgOverLimit_thenCtxClose() {
        final int limit = MSG_QUEUE_LIMIT + 1;
        willDoNothing().given(handler).processMsgQueue(ctx);
        List<MqttPublishMessage> messages = Stream.generate(this::getMqttPublishMessage).limit(limit).toList();

        messages.forEach((msg) -> handler.enqueueRegularSessionMsg(ctx, msg));
        assertThat(handler.deviceSessionCtx.getMsgQueueSize(), is(MSG_QUEUE_LIMIT));
        verify(handler, times(limit)).enqueueRegularSessionMsg(any(), any());
        verify(handler, times(MSG_QUEUE_LIMIT)).processMsgQueue(any());
        verify(ctx, times(1)).close();
    }

    @Test
    public void givenMqttConnectMessageAndPublishImmediately_whenProcessMqttMsg_thenEnqueueRegularSessionMsg() {
        givenMqttConnectMessage_whenProcessMqttMsg_thenProcessConnect();
        List<MqttPublishMessage> messages = Stream.generate(this::getMqttPublishMessage).limit(MSG_QUEUE_LIMIT).toList();
        messages.forEach((msg) -> handler.channelRead(ctx, msg));
        assertThat(handler.address, is(IP_ADDR));
        assertThat(handler.deviceSessionCtx.getChannel(), is(ctx));
        assertThat(handler.deviceSessionCtx.isConnected(), is(false));
        assertThat(handler.deviceSessionCtx.getMsgQueueSize(), is(MSG_QUEUE_LIMIT));
        assertThat(handler.deviceSessionCtx.getMsgQueueSnapshot(), contains(messages.toArray()));
        verify(handler, times(1)).processConnect(any(), any());
        verify(handler, times(MSG_QUEUE_LIMIT)).enqueueRegularSessionMsg(any(), any());
        verify(handler, never()).processRegularSessionMsg(any(), any());
        messages.forEach((msg) -> verify(handler, times(1)).enqueueRegularSessionMsg(ctx, msg));

    }

    @Test
    public void givenMessageQueue_whenProcessMqttMsgConcurrently_thenEnqueueRegularSessionMsg() throws InterruptedException {
        assertThat(handler.deviceSessionCtx.isConnected(), is(false));
        assertThat(MSG_QUEUE_LIMIT, greaterThan(2));
        List<MqttPublishMessage> messages = Stream.generate(this::getMqttPublishMessage).limit(MSG_QUEUE_LIMIT).toList();
        messages.forEach((msg) -> handler.enqueueRegularSessionMsg(ctx, msg));
        willDoNothing().given(handler).processRegularSessionMsg(any(), any());
        executor = Executors.newCachedThreadPool(JackmouseThreadFactory.forName(getClass().getName()));
        CountDownLatch readyLatch = new CountDownLatch(MSG_QUEUE_LIMIT);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(MSG_QUEUE_LIMIT);
        Stream.iterate(0, i -> i + 1).limit(MSG_QUEUE_LIMIT).forEach((i) -> {
            executor.submit(() -> {
                try {
                    readyLatch.countDown();
                    assertThat(startLatch.await(TIMEOUT, TimeUnit.SECONDS), is(true));
                    handler.processMsgQueue(ctx);
                    finishLatch.countDown();
                } catch (Exception e) {
                    log.error("Failed to run processMsgQueue", e);
                    fail("Failed to run processMsgQueue");
                }
            });
        });
        assertThat(readyLatch.await(TIMEOUT, TimeUnit.SECONDS), is(true));
        handler.deviceSessionCtx.setConnected(true);
        startLatch.countDown();
        assertThat(finishLatch.await(TIMEOUT, TimeUnit.SECONDS), is(true));
        //then
        assertThat(handler.deviceSessionCtx.getMsgQueueSize(), is(0));
        assertThat(handler.deviceSessionCtx.getMsgQueueSnapshot(), empty());
        verify(handler, times(MSG_QUEUE_LIMIT)).processRegularSessionMsg(any(), any());
        messages.forEach((msg) -> verify(handler, times(1)).processRegularSessionMsg(ctx, msg));
    }
}



