package com.jackmouse.system.dto.query;

import com.jackmouse.system.blog.domain.specification.SortSpec;
import lombok.Data;

/**
 * @ClassName PageQuery
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/14 17:16
 * @Version 1.0
 **/
@Data
public class PageQuery implements SortSpec {
    int current = 1;
    int pageSize = 10;
    String sortField;
    String sortType = "DESC";

    @Override
    public Direction getDirection() {
        return Direction.valueOf(sortType);
    }

    public int getPage() {
        return current;
    }
    public int getSize() {
        return pageSize;
    }
}
