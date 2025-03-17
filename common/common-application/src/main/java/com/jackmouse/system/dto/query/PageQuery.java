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
    int page = 1;
    int size = 10;
    String sortField;
    String sortType = "DESC";

    @Override
    public Direction getDirection() {
        return Direction.valueOf(sortType);
    }
}
