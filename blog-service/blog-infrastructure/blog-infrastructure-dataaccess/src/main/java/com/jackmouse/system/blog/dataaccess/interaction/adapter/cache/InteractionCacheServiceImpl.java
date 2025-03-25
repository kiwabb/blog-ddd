package com.jackmouse.system.blog.dataaccess.interaction.adapter.cache;

import com.jackmouse.system.blog.domain.interaction.cache.InteractionCacheService;
import com.jackmouse.system.blog.domain.interaction.entity.Favorite;
import com.jackmouse.system.blog.domain.interaction.entity.Like;
import com.jackmouse.system.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.jackmouse.system.blog.dataaccess.interaction.adapter.cache.RedisConstants.*;

/**
 * @ClassName InteractionCacheServiceImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 13:24
 * @Version 1.0
 **/
@Slf4j
@Component
public class InteractionCacheServiceImpl implements InteractionCacheService {
    private final RedisUtil redisUtil;

    public InteractionCacheServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void updateLikeCount(Like like) {
        String articleLikeKey = ARTICLE_LIKE_COUNT_KEY + like.getTargetId();
        String userLikeKey = USER_LIKE_KEY + like.getUserId();
        boolean active = like.getInteractionStatus().isActive();
        long likeCount;
        boolean add;
        double score;
        if (active) {
            likeCount = redisUtil.incr(articleLikeKey, 1);
            add = redisUtil.sAdd(userLikeKey, like.getTargetId().value());
            score = redisUtil.zIncrementScore(ARTICLE_HOT_KEY, 1.5, like.getTargetId().value());
        } else {
            likeCount = redisUtil.decr(articleLikeKey, 1);
            add = redisUtil.sRem(userLikeKey, like.getTargetId().value());
            score = redisUtil.zDecrementScore(ARTICLE_HOT_KEY, 1.5, like.getTargetId().value());
        }
        log.info("更新文章点赞数，文章ID：{}，点赞数：{}，点赞状态：{}，用户ID：{}，增加：{}，分数：{}",
                like.getTargetId(), likeCount, like.getInteractionStatus(), like.getUserId(), add, score);
    }

    @Override
    public void updateFavoriteCount(Favorite favorite) {
        String articleFavoriteKey = (ARTICLE_FAVORITE_COUNT_KEY + favorite.getTargetId());
        String userFavoriteKey = USER_FAVORITE_KEY + favorite.getUserId();
        boolean active = favorite.getInteractionStatus().isActive();
        long likeCount;
        boolean add;
        double score;
        if (active) {
            likeCount = redisUtil.incr(articleFavoriteKey, 1);
            add = redisUtil.sAdd(userFavoriteKey, favorite.getTargetId().value());
            score = redisUtil.zIncrementScore(ARTICLE_HOT_KEY, 2, favorite.getTargetId().value());
        } else {
            likeCount = redisUtil.decr(articleFavoriteKey, 1);
            add = redisUtil.sRem(userFavoriteKey, favorite.getTargetId().value());
            score = redisUtil.zDecrementScore(ARTICLE_HOT_KEY, 2, favorite.getTargetId().value());
        }
        log.info("更新文章收藏数，文章ID：{}，点赞数：{}，点赞状态：{}，用户ID：{}，增加：{}，分数：{}",
                favorite.getTargetId(), likeCount, favorite.getInteractionStatus(), favorite.getUserId(), add, score);
    }
}
