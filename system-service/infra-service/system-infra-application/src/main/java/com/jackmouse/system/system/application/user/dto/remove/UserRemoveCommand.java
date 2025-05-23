package com.jackmouse.system.system.application.user.dto.remove;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName UserRemoveCommand
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 11:37
 * @Version 1.0
 **/

@Builder
@Data
@EqualsAndHashCode
public class UserRemoveCommand {
    private final List<Long> ids;
}
