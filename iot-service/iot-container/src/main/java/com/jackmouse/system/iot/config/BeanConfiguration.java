package com.jackmouse.system.iot.config;

import com.jackmouse.system.iot.device.DeviceDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName BeanConfiguration
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/31 22:11
 * @Version 1.0
 **/
@Configuration
public class BeanConfiguration {
    @Bean
    public DeviceDomainService deviceDomainService () {
        return new DeviceDomainService();
    }
}
