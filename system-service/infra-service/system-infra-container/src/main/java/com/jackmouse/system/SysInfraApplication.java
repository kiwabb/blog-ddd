package com.jackmouse.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @ClassName SysInfraApplication
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 09:48
 * @Version 1.0
 **/
@EnableJpaRepositories(basePackages = "com.jackmouse.system.system.dataaccess")
@EntityScan(basePackages = "com.jackmouse.system.system.dataaccess")
@SpringBootApplication(scanBasePackages = "com.jackmouse.system")
public class SysInfraApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysInfraApplication.class, args);
    }
}
