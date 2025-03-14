package com.jackmouse.system.blog.domain.specification;

import java.util.function.Predicate;

/**
 * @ClassName PageSpec
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 09:11
 * @Version 1.0
 **/
public interface PageSpec<T> {
    int getPage();
    int getSize();
    SortSpec getSort();
    Predicate<T> getPredicate();
}
