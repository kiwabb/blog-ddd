package com.jackmouse.system.blog.domain.valueobject;

import com.jackmouse.system.blog.domain.specification.SortSpec;

/**
 * @ClassName PageParam
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 09:22
 * @Version 1.0
 **/
public record PageParam(int page, int size, SortSpec sort) {
}
