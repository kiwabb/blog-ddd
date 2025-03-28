package com.jackmouse.system.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName IotApplication
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/28 15:32
 * @Version 1.0
 **/

@SpringBootApplication(scanBasePackages = "com.jackmouse.system")
public class IotApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(IotApplication.class, args);
    }
}
