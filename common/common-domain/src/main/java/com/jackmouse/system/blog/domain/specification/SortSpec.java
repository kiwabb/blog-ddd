package com.jackmouse.system.blog.domain.specification;

/**
 * @ClassName SortSpec
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 09:10
 * @Version 1.0
 **/
public interface SortSpec {
    String getSortField();
    Direction getDirection();

    enum Direction {
        ASC, DESC
    }
}
