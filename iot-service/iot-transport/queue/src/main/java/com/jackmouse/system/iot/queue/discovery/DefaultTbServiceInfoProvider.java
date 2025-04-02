package com.jackmouse.system.iot.queue.discovery;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName DefaultTbServiceInfoProvider
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 14:39
 * @Version 1.0
 **/
@Slf4j
@Component
public class DefaultTbServiceInfoProvider implements JmServiceInfoProvider{
    @Getter
    @Value("${service.id:#{null}}")
    private String serviceId;
}
