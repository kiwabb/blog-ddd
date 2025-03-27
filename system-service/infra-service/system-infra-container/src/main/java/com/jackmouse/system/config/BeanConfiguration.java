package com.jackmouse.system.config;

import com.jackmouse.system.system.infra.domain.RoleMenuDomainServiceImpl;
import com.jackmouse.system.system.infra.domain.rolemenu.RoleMenuDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName BeanConfigration
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/27 14:14
 * @Version 1.0
 **/
@Configuration
public class BeanConfiguration {
    @Bean
    public RoleMenuDomainService roleMenuDomainService() {
        return new RoleMenuDomainServiceImpl();
    }
}
