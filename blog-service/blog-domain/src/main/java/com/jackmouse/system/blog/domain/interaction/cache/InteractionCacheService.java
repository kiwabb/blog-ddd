package com.jackmouse.system.blog.domain.interaction.cache;

import com.jackmouse.system.blog.domain.interaction.entity.Favorite;
import com.jackmouse.system.blog.domain.interaction.entity.Like;

/**
 * @ClassName InteractionCacheService
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 10:53
 * @Version 1.0
 **/
public interface InteractionCacheService {
    void updateLikeCount(Like like);

    void updateFavoriteCount(Favorite favorite);
}
