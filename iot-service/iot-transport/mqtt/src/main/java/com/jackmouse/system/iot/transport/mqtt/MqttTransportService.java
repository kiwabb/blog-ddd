package com.jackmouse.system.iot.transport.mqtt;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.ResourceLeakDetector;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * @ClassName MqttTransportService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/7 14:33
 * @Version 1.0
 **/
@Slf4j
@Service
public class MqttTransportService {

    public static AttributeKey<InetSocketAddress> ADDRESS = AttributeKey.newInstance("SRC_ADDRESS");


    @Value("${transport.mqtt.bind_address}")
    private String host;
    @Value("${transport.mqtt.bind_port}")
    private Integer port;
    @Value("${transport.mqtt.ssl.enabled}")
    private boolean sslEnabled;
    @Value("${transport.mqtt.ssl.bind_address}")
    private String sslHost;
    @Value("${transport.mqtt.ssl.bind_port}")
    private Integer sslPort;
    @Value("${transport.mqtt.netty.leak_detector_level}")
    private String leakDetectorLevel;
    @Value("${transport.mqtt.netty.boss_group_thread_count}")
    private Integer bossGroupThreadCount;
    @Value("${transport.mqtt.netty.worker_group_thread_count}")
    private Integer workerGroupThreadCount;
    @Value("${transport.mqtt.netty.so_keep_alive}")
    private boolean keepAlive;

    @Resource
    private MqttTransportContext context;
    private Channel serverChannel;
    private Channel sslServerChannel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @PostConstruct
    public void init() throws InterruptedException {
        log.info("Setting resource leak detector level to {}", leakDetectorLevel);
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(leakDetectorLevel));
        log.info("Starting MQTT transport...");
        bossGroup = new NioEventLoopGroup(bossGroupThreadCount);
        workerGroup = new NioEventLoopGroup(workerGroupThreadCount);

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                //子通道（每个客户端连接）初始化逻辑 它的作用是为每个客户端连接配置：解码器、编码器、业务处理器
                .childHandler(new MqttTransportServerInitializer(context, false))
                .childOption(ChannelOption.SO_KEEPALIVE, keepAlive);

        serverChannel = b.bind(host, port).sync().channel();
        if (sslEnabled){
            b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MqttTransportServerInitializer(context, true))
                    .childOption(ChannelOption.SO_KEEPALIVE, keepAlive);
            sslServerChannel = b.bind(sslHost, sslPort).sync().channel();

        }
        log.info("Mqtt transport started!");
    }
}
