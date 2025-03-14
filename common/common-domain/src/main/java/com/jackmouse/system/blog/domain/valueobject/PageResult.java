package com.jackmouse.system.blog.domain.valueobject;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description 分页结果值对象
 * @Author zhoujiaangyao
 * @Date 2025/3/13 16:55
 * @Version 1.0
 **/
public record PageResult<T> (
        List<T> data,
        long total,
        int currentPage,
        int totalPages
) {}
