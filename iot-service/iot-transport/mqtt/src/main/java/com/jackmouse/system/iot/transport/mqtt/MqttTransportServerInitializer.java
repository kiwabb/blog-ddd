package com.jackmouse.system.iot.transport.mqtt;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.ssl.SslHandler;

/**
 * @ClassName MqttTransportServerInitializer
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 14:55
 * @Version 1.0
 **/
public class MqttTransportServerInitializer extends ChannelInitializer<SocketChannel> {

    private final MqttTransportContext context;
    private final boolean ssl;

    public MqttTransportServerInitializer(MqttTransportContext context, boolean ssl) {
        this.context = context;
        this.ssl = ssl;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        SslHandler sslHandler = null;
        pipeline.addLast("decoder", new MqttDecoder());
        pipeline.addLast("encoder", MqttEncoder.INSTANCE);
        MqttTransportHandler handler = new MqttTransportHandler(context, sslHandler);
        pipeline.addLast(handler);
        socketChannel.closeFuture().addListener(handler);
    }
}
