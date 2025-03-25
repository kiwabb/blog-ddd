package com.jackmouse.system.blog.domain.interaction.repository;

import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;
import com.jackmouse.system.blog.domain.interaction.entity.Favorite;
import com.jackmouse.system.blog.domain.interaction.entity.Like;
import com.jackmouse.system.blog.domain.interaction.valueobject.FavoriteId;
import com.jackmouse.system.blog.domain.valueobject.UserId;

import java.util.Optional;

/**
 * @ClassName InteractionRepository
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 10:20
 * @Version 1.0
 **/
public interface InteractionRepository {
    Optional<Like> findLikeByArticleAndUserId(ArticleId articleId, UserId userId);
    Like saveLike(Like like);

   Optional<Favorite> findFavoriteByArticleAndUserId(FavoriteId favoriteId, UserId userId);

    Favorite saveFavorite(Favorite favorite);
}
