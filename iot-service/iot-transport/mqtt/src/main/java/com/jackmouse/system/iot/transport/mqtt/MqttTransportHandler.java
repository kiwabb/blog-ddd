package com.jackmouse.system.iot.transport.mqtt;

import com.jackmouse.server.gen.transport.TransportProtos;
import com.jackmouse.system.blog.domain.valueobject.DeviceTransportType;
import com.jackmouse.system.iot.adaptor.AdaptorException;
import com.jackmouse.system.iot.common.constant.DataConstants;
import com.jackmouse.system.iot.common.constant.MqttTopics;
import com.jackmouse.system.iot.common.rpc.RpcStatus;
import com.jackmouse.system.iot.message.JmMsgMetaData;
import com.jackmouse.system.iot.queue.scheduler.SchedulerComponent;
import com.jackmouse.system.iot.transport.SessionMsgLister;
import com.jackmouse.system.iot.transport.TransportService;
import com.jackmouse.system.iot.transport.TransportServiceCallback;
import com.jackmouse.system.iot.transport.mqtt.adaptors.MqttTransportAdaptor;
import com.jackmouse.system.iot.transport.auth.SessionInfoCreator;
import com.jackmouse.system.iot.transport.auth.ValidateDeviceCredentialsResponse;
import com.jackmouse.system.iot.transport.service.SessionMetaData;
import com.jackmouse.system.iot.transport.mqtt.session.DeviceSessionCtx;
import com.jackmouse.system.iot.transport.mqtt.session.MqttTopicMatcher;
import com.jackmouse.system.iot.transport.mqtt.util.ReturnCodeResolver;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.SSLPeerUnverifiedException;
import java.net.InetSocketAddress;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static com.jackmouse.system.iot.transport.service.DefaultTransportService.SESSION_EVENT_MSG_OPEN;
import static io.netty.handler.codec.mqtt.MqttMessageType.*;
import static io.netty.handler.codec.mqtt.MqttQoS.AT_LEAST_ONCE;
import static io.netty.handler.codec.mqtt.MqttQoS.AT_MOST_ONCE;

/**
 * @ClassName MqttTransportHandler
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 15:18
 * @Version 1.0
 **/
@Slf4j
public class MqttTransportHandler extends ChannelInboundHandlerAdapter implements GenericFutureListener<Future<? super Void>>, SessionMsgLister {
    private final UUID sessionId;
    final DeviceSessionCtx deviceSessionCtx;
    private final ConcurrentMap<MqttTopicMatcher, Integer> mqttQoSMap;

    volatile InetSocketAddress address;
    private final SchedulerComponent scheduler;

    protected final MqttTransportContext context;
    private final SslHandler sslHandler;

    private final ConcurrentMap<Integer, TransportProtos.ToDeviceRpcRequestMsg> rpcAwaitingAck;
    private TopicType attrSubTopicType;
    private static final MqttQoS MAX_SUPPORTED_QOS_LVL = AT_LEAST_ONCE;

    private final TransportService transportService;


    public MqttTransportHandler(MqttTransportContext context, SslHandler sslHandler) {
        this.sessionId = UUID.randomUUID();
        this.context = context;
        this.sslHandler = sslHandler;
        this.mqttQoSMap = new ConcurrentHashMap<>();
        this.deviceSessionCtx = new DeviceSessionCtx(sessionId, mqttQoSMap, context);
        this.rpcAwaitingAck = new ConcurrentHashMap<>();
        this.scheduler = context.getScheduler();
        this.transportService = context.getTransportService();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        context.channelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        context.channelUnregistered();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.trace("[{}] Processing msg: {}", sessionId, msg);
        if (address == null) {
            address = getAddress(ctx);
        }
        try {
            if (msg instanceof MqttMessage mqttMessage) {
                if (mqttMessage.decoderResult().isSuccess()) {
                    processMqttMsg(ctx, mqttMessage);
                } else {
                    log.error("[{}] Message decoding failed: {}", sessionId, mqttMessage.decoderResult().cause().getMessage());
                    closeCtx(ctx, MqttReasonCodes.Disconnect.MALFORMED_PACKET);
                }
            } else {
                log.debug("[{}] Received non mqtt message: {}", sessionId, msg.getClass().getSimpleName());
                closeCtx(ctx, (MqttMessage) null);
            }
        } finally {
            ReferenceCountUtil.safeRelease(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    void processMqttMsg(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        if (mqttMessage.fixedHeader() == null) {
            log.info("[{}:{}] Invalid message received", address.getHostName(), address.getPort());
            closeCtx(ctx, MqttReasonCodes.Disconnect.PROTOCOL_ERROR);
            return;
        }
        deviceSessionCtx.setChannel(ctx);
        if (CONNECT.equals(mqttMessage.fixedHeader().messageType())) {
            // 连接请求
            processConnect(ctx, (MqttConnectMessage) mqttMessage);
        } else if (deviceSessionCtx.isProvisionOnly()) {
            // 设备预注册x
            processProvisionSessionMsg(ctx, mqttMessage);
        } else {
            // 放入队列
            enqueueRegularSessionMsg(ctx, mqttMessage);
        }
    }

    void enqueueRegularSessionMsg(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        final int queueSize = deviceSessionCtx.getMsgQueueSize();
        if (queueSize >= context.getMessageQueueSizePerDeviceLimit()) {
            log.info("Closing current session because msq queue size for device {} exceed limit {} with msgQueueSize counter {} and actual queue size {}",
                    deviceSessionCtx.getDeviceId(), context.getMessageQueueSizePerDeviceLimit(), queueSize, deviceSessionCtx.getMsgQueueSize());
            closeCtx(ctx, MqttReasonCodes.Disconnect.QUOTA_EXCEEDED);
            return;
        }
        deviceSessionCtx.addToQueue(mqttMessage);
        processMsgQueue(ctx);
    }

    private void processProvisionSessionMsg(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        // TODO processProvision
    }

    void processConnect(ChannelHandlerContext ctx, MqttConnectMessage mqttMessage) {
        log.debug("[{}][{}] Processing connect msg for client: {}!", address, sessionId, mqttMessage.payload().clientIdentifier());
        String userName = mqttMessage.payload().userName();
        String clientId = mqttMessage.payload().clientIdentifier();
        deviceSessionCtx.setMqttVersion(getMqttVersion(mqttMessage.variableHeader().version()));
        if (DataConstants.PROVISION.equals(userName) || DataConstants.PROVISION.equals(clientId)) {
            deviceSessionCtx.setProvisionOnly(true);
            ctx.writeAndFlush(createMqttConnAckMsg(MqttConnectReturnCode.CONNECTION_ACCEPTED, mqttMessage));
        } else {
            X509Certificate cert;
            if (sslHandler != null && (cert = getX509Certificate()) != null) {
                // TODO X509Certificate
            } else {
                processAuthTokenConnect(ctx, mqttMessage);
            }
        }

    }

    private void processAuthTokenConnect(ChannelHandlerContext ctx, MqttConnectMessage connectMessage) {
        String userName = connectMessage.payload().userName();
        log.debug("[{}][{}] Processing connect msg for client with user name: {}!", address, sessionId, userName);
        TransportProtos.ValidateBasicMqttCredRequestMsg.Builder request = TransportProtos.ValidateBasicMqttCredRequestMsg.newBuilder()
                .setClientId(connectMessage.payload().clientIdentifier());
        if (userName != null) {
            request.setUserName(userName);
        }
        byte[] passwordInBytes = connectMessage.payload().passwordInBytes();
        if (passwordInBytes!= null) {
            String password = new String(passwordInBytes, CharsetUtil.UTF_8);
            request.setPassword(password);
        }
        transportService.process(DeviceTransportType.MQTT, request.build(),
                new TransportServiceCallback<>() {
                    @Override
                    public void onSuccess(ValidateDeviceCredentialsResponse msg) {
                        onValidateDeviceResponse(msg, ctx, connectMessage);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        log.trace("[{}] Failed to process credentials: {}", address, userName, t);
                        ctx.writeAndFlush(createMqttConnAckMsg(MqttConnectReturnCode.CONNECTION_REFUSED_SERVER_UNAVAILABLE_5, connectMessage));
                        closeCtx(ctx, MqttReasonCodes.Disconnect.SERVER_BUSY);
                    }
                });
    }

    private void onValidateDeviceResponse(ValidateDeviceCredentialsResponse msg, ChannelHandlerContext ctx, MqttConnectMessage connectMessage) {
        if (!msg.hasDeviceInfo()) {
            context.onAuthFailure(address);
            MqttConnectReturnCode returnCode = MqttConnectReturnCode.CONNECTION_REFUSED_NOT_AUTHORIZED_5;
            if (sslHandler == null || getX509Certificate() == null) {
                String username = connectMessage.payload().userName();
                byte[] passwordBytes = connectMessage.payload().passwordInBytes();
                String clientId = connectMessage.payload().clientIdentifier();
                if ((username != null && passwordBytes != null && clientId != null)
                        || (username == null ^ passwordBytes == null)) {
                    returnCode = MqttConnectReturnCode.CONNECTION_REFUSED_BAD_USERNAME_OR_PASSWORD;
                } else if (!StringUtils.isBlank(clientId)) {
                    returnCode = MqttConnectReturnCode.CONNECTION_REFUSED_CLIENT_IDENTIFIER_NOT_VALID;
                }
            }
            ctx.writeAndFlush(createMqttConnAckMsg(returnCode, connectMessage));
            closeCtx(ctx, returnCode);
        } else {
            context.onAuthSuccess(address);
            deviceSessionCtx.setDeviceInfo(msg.getDeviceInfo());
            deviceSessionCtx.setDeviceProfile(msg.getDeviceProfile());
            deviceSessionCtx.setSessionInfo(SessionInfoCreator.create(msg, context, sessionId));
            transportService.process(deviceSessionCtx.getSessionInfo(), SESSION_EVENT_MSG_OPEN,
                    new TransportServiceCallback<>() {
                        @Override
                        public void onSuccess(Void msg) {
                            SessionMetaData sessionMetaData = transportService.registerAsyncSession(deviceSessionCtx.getSessionInfo(), MqttTransportHandler.this);
                            if (deviceSessionCtx.isSparkplug()) {
                                checkSparkplugNodeSession(connectMessage, ctx, sessionMetaData);
                            } else {
                                checkGatewaySession(sessionMetaData);
                            }
                            ctx.writeAndFlush(createMqttConnAckMsg(MqttConnectReturnCode.CONNECTION_ACCEPTED, connectMessage));
                            deviceSessionCtx.setConnected(true);
                            log.debug("[{}] Client connected!", sessionId);
                            transportService.getCallbackExecutor().execute(() -> processMsgQueue(ctx));
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });

        }


    }

    void processMsgQueue(ChannelHandlerContext ctx) {
        if (!deviceSessionCtx.isConnected()) {
            log.trace("[{}][{}] Postpone processing msg due to device is not connected. Msg queue size is {}", sessionId, deviceSessionCtx.getDeviceId(), deviceSessionCtx.getMsgQueueSize());
            return;
        }
        deviceSessionCtx.tryProcessQueueMsgs(msg -> processRegularSessionMsg(ctx, msg));
    }

    void processRegularSessionMsg(ChannelHandlerContext ctx, MqttMessage msg) {
        switch (msg.fixedHeader().messageType()) {
            case PUBLISH -> processPublish(ctx, (MqttPublishMessage) msg);
            case SUBSCRIBE -> processSubscribe(ctx, (MqttSubscribeMessage) msg);
            case UNSUBSCRIBE -> processUnsubscribe(ctx, (MqttUnsubscribeMessage) msg);
            case PINGREQ -> {
                // 心跳报文处理
                if (checkConnected(ctx, msg)) {
                    ctx.writeAndFlush(new MqttMessage(new MqttFixedHeader(PINGRESP, false, AT_MOST_ONCE, false, 0)));
                    transportService.recordActivity(deviceSessionCtx.getSessionInfo());
                    // TODO gateway session handler
                }
            }
            case DISCONNECT -> closeCtx(ctx, MqttReasonCodes.Disconnect.NORMAL_DISCONNECT);
            case PUBACK -> {
                // ⬇下发RPC请求到设备返回的ACK处理
                int masId = ((MqttPubAckMessage) msg).variableHeader().messageId();
                TransportProtos.ToDeviceRpcRequestMsg rpcRequestMsg = rpcAwaitingAck.remove(masId);
                if (rpcRequestMsg != null) {
                    transportService.process(deviceSessionCtx.getSessionInfo(), rpcRequestMsg, RpcStatus.DELIVERED, true, TransportServiceCallback.EMPTY);
                }
            }
        }
    }

    private void processUnsubscribe(ChannelHandlerContext ctx, MqttUnsubscribeMessage msg) {
        if (!checkConnected(ctx, msg) &&!deviceSessionCtx.isProvisionOnly()) {
            ctx.writeAndFlush(createUnSubAckMessage(msg.variableHeader().messageId(),
                    Collections.singletonList((short) MqttReasonCodes.UnsubAck.NOT_AUTHORIZED.byteValue())));            return;
        }
        boolean activityReported = false;
        List<Short> unSubResults = new ArrayList<>();
        log.trace("[{}] Processing subscription [{}]!", sessionId, msg.variableHeader().messageId());
        for (String topic : msg.payload().topics()) {
            MqttTopicMatcher matcher = new MqttTopicMatcher(topic);
            if (mqttQoSMap.containsKey(matcher)) {
                mqttQoSMap.remove(matcher);
                try {
                    short resultValue = MqttReasonCodes.UnsubAck.SUCCESS.byteValue();
                    // TODO provisioning
                    switch (topic) {
                        case MqttTopics.DEVICE_ATTRIBUTES_TOPIC -> {
                            transportService.process(deviceSessionCtx.getSessionInfo(),
                                    TransportProtos.SubscribeToAttributeUpdatesMsg.newBuilder().setUnsubscribe(true).build(), null);
                            activityReported = true;
                        }
                    }
                    unSubResults.add(resultValue);
                } catch (Exception e) {
                    log.debug("[{}] Failed to process unsubscription [{}] to [{}]", sessionId, msg.variableHeader().messageId(), topic);
                    unSubResults.add((short) MqttReasonCodes.UnsubAck.IMPLEMENTATION_SPECIFIC_ERROR.byteValue());
                }
            } else {
                log.debug("[{}] Failed to process unsubscription [{}] to [{}] - Subscription not found", sessionId, msg.variableHeader().messageId(), topic);
                unSubResults.add((short) MqttReasonCodes.UnsubAck.NO_SUBSCRIPTION_EXISTED.byteValue());

            }
        }
        if (!activityReported && !deviceSessionCtx.isProvisionOnly()) {
            transportService.recordActivity(deviceSessionCtx.getSessionInfo());
        }
        ctx.writeAndFlush(createUnSubAckMessage(msg.variableHeader().messageId(), unSubResults));

    }

    private Object createUnSubAckMessage(int msgId, List<Short> resultCodes) {
        MqttMessageBuilders.UnsubAckBuilder unsubAckBuilder = MqttMessageBuilders.unsubAck();
        unsubAckBuilder.packetId(msgId);
        if (MqttVersion.MQTT_5.equals(deviceSessionCtx.getMqttVersion())) {
            unsubAckBuilder.addReasonCodes(resultCodes.toArray(Short[]::new));
        }
        return unsubAckBuilder.build();
    }

    private void processSubscribe(ChannelHandlerContext ctx, MqttSubscribeMessage msg) {
        if (!checkConnected(ctx, msg) && !deviceSessionCtx.isProvisionOnly()) {
            ctx.writeAndFlush(createSubAckMessage(msg.variableHeader().messageId(), Collections.singletonList(MqttReasonCodes.SubAck.NOT_AUTHORIZED.byteValue() & 0xFF)));
            return;
        }
        log.trace("[{}] Processing subscription [{}]!", sessionId, msg.variableHeader().messageId());
        List<Integer> grantedQoSList = new ArrayList<>();
        boolean activityReported = false;
        for (MqttTopicSubscription subscription : msg.payload().topicSubscriptions()) {
            String topic = subscription.topicFilter();
            MqttQoS reqQoS = subscription.qualityOfService();
            if (deviceSessionCtx.isProvisionOnly()) {
                //TODO provision only
            }
            try {
                // TODO sparkplug session handler

                switch (topic) {
                    case MqttTopics.DEVICE_ATTRIBUTES_TOPIC -> {
                        processAttributeSubscribe(grantedQoSList, topic, reqQoS, TopicType.V1);
                        activityReported = true;
                    }
                }
            } catch (Exception e) {
                log.warn("[{}] Failed to subscribe to [{}][{}]", sessionId, topic, reqQoS, e);

            }
            if (!activityReported) {
                transportService.recordActivity(deviceSessionCtx.getSessionInfo());
            }
            ctx.writeAndFlush(createSubAckMessage(msg.variableHeader().messageId(), grantedQoSList));

        }

    }

    private void processAttributeSubscribe(List<Integer> grantedQoSList, String topic, MqttQoS reqQoS, TopicType topicType) {
        transportService.process(deviceSessionCtx.getSessionInfo(), TransportProtos.SubscribeToAttributeUpdatesMsg.newBuilder().build(), null);
        attrSubTopicType = topicType;
        registerSubQoS(topic, grantedQoSList, reqQoS);
    }

    private void registerSubQoS(String topic, List<Integer> grantedQoSList, MqttQoS reqQoS) {
        grantedQoSList.add(getMinSupportedQos(reqQoS));
        mqttQoSMap.put(new MqttTopicMatcher(topic), getMinSupportedQos(reqQoS));
    }

    private Integer getMinSupportedQos(MqttQoS reqQoS) {
        return Math.min(reqQoS.value(), MAX_SUPPORTED_QOS_LVL.value());
    }

    private Object createSubAckMessage(int msgId, List<Integer> reasonCodes) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(SUBACK, false, AT_MOST_ONCE, false, 0);
        MqttMessageIdVariableHeader mqttMessageIdVariableHeader = MqttMessageIdVariableHeader.from(msgId);
        MqttSubAckPayload mqttSubAckPayload = new MqttSubAckPayload(reasonCodes);
        return new MqttSubAckMessage(mqttFixedHeader, mqttMessageIdVariableHeader, mqttSubAckPayload);
    }

    private void processPublish(ChannelHandlerContext ctx, MqttPublishMessage msg) {
        if (!checkConnected(ctx, msg)) {
            return;
        }
        String topicName = msg.variableHeader().topicName();
        int msgId = msg.variableHeader().packetId();
        log.trace("[{}][{}] Processing publish msg [{}][{}]!", sessionId, deviceSessionCtx.getDeviceId(), topicName, msgId);
        if (topicName.startsWith(MqttTopics.BASE_GATEWAY_API_TOPIC)) {
            // TODO gateway and sparkplug session handler
        } else {
            processDevicePublish(ctx, msg, topicName, msgId);
        }
    }

    private void processDevicePublish(ChannelHandlerContext ctx, MqttPublishMessage msg, String topicName, int msgId) {
        try {
            MqttTransportAdaptor payloadAdaptor = deviceSessionCtx.getPayloadAdaptor();
            if (deviceSessionCtx.isDeviceTelemetryTopic(topicName)) {
                TransportProtos.PostTelemetryMsg postTelemetryMsg = payloadAdaptor.convertToPostTelemetry(deviceSessionCtx, msg);
                transportService.process(deviceSessionCtx.getSessionInfo(), postTelemetryMsg, getMetadata(deviceSessionCtx, topicName),
                        getPubAckCallback(ctx, msgId, postTelemetryMsg));
            } else {
                transportService.recordActivity(deviceSessionCtx.getSessionInfo());
                ack(ctx, msgId, MqttReasonCodes.PubAck.TOPIC_NAME_INVALID);
            }
        } catch (AdaptorException e) {
            log.debug("[{}] Failed to process publish msg [{}][{}]", sessionId, topicName, msgId, e);
            sendResponseForAdaptorErrorOrCloseContext(ctx, topicName, msgId);
        }
    }

    private void sendResponseForAdaptorErrorOrCloseContext(ChannelHandlerContext ctx, String topicName, int msgId) {
        if ((deviceSessionCtx.isSendAckOnValidationException() || MqttVersion.MQTT_5.equals(deviceSessionCtx.getMqttVersion())) && msgId > 0) {
            log.debug("[{}] Send pub ack on invalid publish msg [{}][{}]", sessionId, topicName, msgId);
            ctx.writeAndFlush(createMqttPubAckMsg(deviceSessionCtx, msgId, MqttReasonCodes.PubAck.PAYLOAD_FORMAT_INVALID.byteValue()));
        } else {
            log.info("[{}] Closing current session due to invalid publish msg [{}][{}]", sessionId, topicName, msgId);
            closeCtx(ctx, MqttReasonCodes.Disconnect.PAYLOAD_FORMAT_INVALID);
        }
    }

    private <T> TransportServiceCallback<Void> getPubAckCallback(ChannelHandlerContext ctx, int msgId, T msg) {
        return new TransportServiceCallback<Void>() {
            @Override
            public void onSuccess(Void dummy) {
                log.trace("[{}] Published msg: {}", sessionId, msg);
                ack(ctx, msgId, MqttReasonCodes.PubAck.SUCCESS);
            }

            @Override
            public void onFailure(Throwable t) {
                log.trace("[{}] Failed to publish msg: {}", sessionId, msg, t);
                closeCtx(ctx, MqttReasonCodes.Disconnect.IMPLEMENTATION_SPECIFIC_ERROR);
            }
        };
    }

    private void ack(ChannelHandlerContext ctx, int msgId, MqttReasonCodes.PubAck returnCode) {
        ack(ctx, msgId, returnCode.byteValue());
    }
    private void ack(ChannelHandlerContext ctx, int msgId, byte returnCode) {
        if (msgId > 0) {
            ctx.writeAndFlush(createMqttPubAckMsg(deviceSessionCtx, msgId, returnCode));
        }
    }
    public static MqttMessage createMqttPubAckMsg(DeviceSessionCtx deviceSessionCtx, int requestId, byte returnCode) {
        MqttMessageBuilders.PubAckBuilder pubAckMsgBuilder = MqttMessageBuilders.pubAck().packetId(requestId);
        if (MqttVersion.MQTT_5.equals(deviceSessionCtx.getMqttVersion())) {
            pubAckMsgBuilder.reasonCode(returnCode);
        }
        return pubAckMsgBuilder.build();
    }

    private JmMsgMetaData getMetadata(DeviceSessionCtx deviceSessionCtx, String topicName) {
        if (deviceSessionCtx.isDeviceProfileMqttTransportType()) {
            JmMsgMetaData metaData = new JmMsgMetaData();
            metaData.putValue(DataConstants.MQTT_TOPIC, topicName);
            return metaData;
        }
        return null;
    }

    private boolean checkConnected(ChannelHandlerContext ctx, MqttMessage msg) {
        if (deviceSessionCtx.isConnected()) {
            return true;
        } else {
            log.info("[{}] Closing current session due to invalid msg order: {}", sessionId, msg);
            return false;
        }
    }

    private void checkGatewaySession(SessionMetaData sessionMetaData) {
        // TODO checkGatewaySession
    }

    private void checkSparkplugNodeSession(MqttConnectMessage connectMessage, ChannelHandlerContext ctx, SessionMetaData sessionMetaData) {
        // TODO checkSparkplugNode
    }

    private X509Certificate getX509Certificate() {
        try {
            Certificate[] certChain = sslHandler.engine().getSession().getPeerCertificates();
            if (certChain.length > 0) {
                return (X509Certificate) certChain[0];
            }
        } catch (SSLPeerUnverifiedException e) {
            log.warn(e.getMessage());
            return null;
        }
        return null;
    }

    private MqttConnAckMessage createMqttConnAckMsg(MqttConnectReturnCode returnCode, MqttConnectMessage mqttMessage) {
        MqttMessageBuilders.ConnAckBuilder connAckBuilder = MqttMessageBuilders.connAck();
        connAckBuilder.sessionPresent(!mqttMessage.variableHeader().isCleanSession());
        MqttConnectReturnCode finalReturnCode = ReturnCodeResolver.getConnectionReturnCode(deviceSessionCtx.getMqttVersion(), returnCode);
        connAckBuilder.returnCode(finalReturnCode);
        return connAckBuilder.build();
    }

    private MqttVersion getMqttVersion(int version) {
        return switch (version) {
            case 3 -> MqttVersion.MQTT_3_1;
            case 5 -> MqttVersion.MQTT_5;
            default -> MqttVersion.MQTT_3_1_1;
        };
    }
    private void closeCtx(ChannelHandlerContext ctx, MqttConnectReturnCode returnCode) {
        closeCtx(ctx, ReturnCodeResolver.getConnectionReturnCode(deviceSessionCtx.getMqttVersion(), returnCode).byteValue());
    }

    private void closeCtx(ChannelHandlerContext ctx, MqttReasonCodes.Disconnect returnCode) {
        closeCtx(ctx, returnCode.byteValue());
    }
    private void closeCtx(ChannelHandlerContext ctx, byte returnCode) {
        closeCtx(ctx, createMqttDisconnectMsg(deviceSessionCtx, returnCode));
    }

    private void closeCtx(ChannelHandlerContext ctx, MqttMessage mqttMessage) {
        if (!rpcAwaitingAck.isEmpty()) {
            log.debug("[{}] Cleanup RPC awaiting ack map due to session close!", sessionId);
            rpcAwaitingAck.clear();
        }

        if (ctx.channel() == null) {
            log.debug("[{}] Channel is null, closing ctx...", sessionId);
            ctx.close();
        } else if (ctx.channel().isOpen()) {
            if (mqttMessage != null && MqttVersion.MQTT_5 == deviceSessionCtx.getMqttVersion()) {
                ChannelFuture channelFuture = ctx.writeAndFlush(mqttMessage).addListener(future -> ctx.close());
                scheduler.schedule(() -> {
                    if (!channelFuture.isDone()) {
                        log.debug("[{}] Closing channel due to timeout!", sessionId);
                        ctx.close();
                    }
                }, context.getDisconnectTimeout(),  TimeUnit.MILLISECONDS);
            } else {
                ctx.close();
            }
        } else {
            log.debug("[{}] Channel is already closed!", sessionId);
        }
    }


    private static MqttMessage createMqttDisconnectMsg(DeviceSessionCtx deviceSessionCtx, byte returnCode) {
        MqttMessageBuilders.DisconnectBuilder disconnectBuilder = MqttMessageBuilders.disconnect();
        if (MqttVersion.MQTT_5.equals(deviceSessionCtx.getMqttVersion())) {
            disconnectBuilder.reasonCode(returnCode);
        }
        return disconnectBuilder.build();
    }

    InetSocketAddress getAddress(ChannelHandlerContext ctx) {
        var address = ctx.channel().attr(MqttTransportService.ADDRESS).get();
        if (address == null) {
            log.trace("[{}] Received empty address.", ctx.channel().id());
            InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            log.trace("[{}] Going to use address: {}", ctx.channel().id(), remoteAddress);
            return remoteAddress;
        } else {
            log.trace("[{}] Received address: {}", ctx.channel().id(), address);

        }
        return address;
    }

    @Override
    public void operationComplete(Future<? super Void> future) throws Exception {

    }
}
