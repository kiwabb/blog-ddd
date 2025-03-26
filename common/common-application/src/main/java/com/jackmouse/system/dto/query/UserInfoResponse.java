package com.jackmouse.system.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * @ClassName UserInfoResponse
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/26 13:14
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private String avatar;
}
