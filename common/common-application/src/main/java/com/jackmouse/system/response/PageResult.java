package com.jackmouse.system.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PageResult
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/17 09:01
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    /**
     * 总数
     */
    private Long total;
    /**
     * 页数
     */
    private Long page;

    private List<T> data;

    private Boolean success;


    public PageResult(long totalRow, long pageNumber, List<T> records) {
        this.total = totalRow;
        this.page = pageNumber;
        this.data = records;
        this.success = true;
    }

    public static <T> PageResult<T> succeed(com.jackmouse.system.blog.domain.valueobject.PageResult<T> model) {
        return of(model);
    }

    public static <T> PageResult<T> of(com.jackmouse.system.blog.domain.valueobject.PageResult<T> page) {
        return new PageResult<>(page.totalPages(), page.currentPage(), page.data());
    }
}
