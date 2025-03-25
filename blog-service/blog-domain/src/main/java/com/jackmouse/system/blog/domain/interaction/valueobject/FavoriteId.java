package com.jackmouse.system.blog.domain.interaction.valueobject;

import com.jackmouse.system.blog.domain.valueobject.BaseId;

import java.util.UUID;

/**
 * @ClassName FavoriteId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/25 11:11
 * @Version 1.0
 **/
public class FavoriteId extends BaseId<UUID> {
    public FavoriteId(UUID value) {
        super(value);
    }
}
