package com.jackmouse.system.iot.transport.http;

import com.jackmouse.system.iot.transport.TransportContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName HttpTransportContext
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/1 10:57
 * @Version 1.0
 **/
@Slf4j
@Component
public class HttpTransportContext extends TransportContext {

    @Getter
    @Value("${transport.http.request_timeout}")
    private long defaultTimeout;

    @Getter
    @Value("${transport.http.max_request_timeout}")
    private long maxRequestTimeout;

    @Bean
    public TomcatConnectorCustomizer tomcatAsyncTimeoutConnectorCustomizer() {
        return connector -> {
            ProtocolHandler handler = connector.getProtocolHandler();
            if (handler instanceof Http11NioProtocol) {
                log.trace("Setting async max request timeout {}", maxRequestTimeout);
                connector.setAsyncTimeout(maxRequestTimeout);
            }
        };
    }
}
