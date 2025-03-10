package com.jackmouse.system.blog.application.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @ClassName Tag
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 15:19
 * @Version 1.0
 **/
@Getter
@Builder
@AllArgsConstructor
public class TagResponse {
    private Long tagId;
    private String tagName;
}
