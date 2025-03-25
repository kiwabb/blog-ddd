package com.jackmouse.system.blog.domain.interaction.valueobject;

import com.jackmouse.system.blog.domain.valueobject.BaseId;

import java.util.UUID;

/**
 * @ClassName ArticleInteractionId
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 10:23
 * @Version 1.0
 **/
public class LikeId extends BaseId<UUID> {
    public LikeId(UUID value) {
        super(value);
    }
}
