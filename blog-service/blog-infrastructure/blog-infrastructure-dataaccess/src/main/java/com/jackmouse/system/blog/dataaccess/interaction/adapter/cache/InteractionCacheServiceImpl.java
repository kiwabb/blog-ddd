package com.jackmouse.system.blog.dataaccess.interaction.adapter.cache;

import com.jackmouse.system.blog.domain.comment.entity.Comment;
import com.jackmouse.system.blog.domain.comment.valueobject.CommentId;
import com.jackmouse.system.blog.domain.interaction.cache.InteractionCacheService;
import com.jackmouse.system.blog.domain.interaction.entity.Favorite;
import com.jackmouse.system.blog.domain.interaction.entity.Like;
import com.jackmouse.system.blog.domain.interaction.valueobject.CommentInteraction;
import com.jackmouse.system.blog.domain.interaction.valueobject.TargetId;
import com.jackmouse.system.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

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
    public void updateArticleLikeCount(Like like) {
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

    @Override
    public Map<CommentId, CommentInteraction> batchGetCommentInteractions(Set<CommentId> rootIds) {
        Map<CommentId, CommentInteraction> result = new HashMap<>();

        // 批量构造Redis键
        List<String> allKeys = new ArrayList<>(rootIds.size() * 2);
        Map<CommentId, Integer> keyIndexMap = new HashMap<>();

        int index = 0;
        for (CommentId commentId : rootIds) {
            String likeKey = COMMENT_LIKE_COUNT_KEY + commentId.getValue();
            String commentCountKey = COMMENT_COUNT_KEY + commentId.getValue();

            allKeys.add(likeKey);
            allKeys.add(commentCountKey);
            keyIndexMap.put(commentId, index);
            index += 2;
        }

        // 批量获取Redis值
        List<String> values = redisUtil.multiGet(allKeys);

        // 解析结果
        for (Map.Entry<CommentId, Integer> entry : keyIndexMap.entrySet()) {
            CommentId commentId = entry.getKey();
            int baseIndex = entry.getValue();

            Integer likeCount = parseRedisValue(values.get(baseIndex));
            Integer favoriteCount = parseRedisValue(values.get(baseIndex + 1));

            result.put(commentId, new CommentInteraction(
                    likeCount,
                    favoriteCount
            ));
        }

        return result;
    }

    @Override
    public void addReplyCount(Comment comment) {
        String commentCountKey = COMMENT_COUNT_KEY + comment.getTargetId().value();
        long commentCount = redisUtil.incr(commentCountKey, 1);
        log.info("增加评论回复数，评论ID：{}，回复数：{}", comment.getId(), commentCount);
    }

    @Override
    public void subReplyCount(Comment comment) {
        String commentCountKey = COMMENT_COUNT_KEY + comment.getTargetId().value();
        long commentCount = redisUtil.decr(commentCountKey, 1);
        log.info("减少评论回复数，评论ID：{}，回复数：{}", comment.getId(), commentCount);
    }

    @Override
    public void updateCommentLikeCount(Like like) {
        boolean active = like.getInteractionStatus().isActive();
        TargetId targetId = like.getTargetId();
        String commentLikeKey = COMMENT_LIKE_COUNT_KEY + targetId.value();
        long commentLikeCount;
        if (active) {
            commentLikeCount = redisUtil.incr(commentLikeKey, 1);
        } else {
            commentLikeCount = redisUtil.decr(commentLikeKey, 1);
        }
        log.info("更新评论点赞数，评论ID：{}，点赞数：{}", targetId, commentLikeCount);
    }

    private Integer parseRedisValue(String value) {
        if (value == null) return 0;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.warn("Redis值解析失败: {}", value);
            return 0;
        }
    }
}
