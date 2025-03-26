package com.jackmouse.system.blog.dataaccess.interaction.adapter.repository;

import com.jackmouse.system.blog.dataaccess.interaction.entity.FavoriteEntity;
import com.jackmouse.system.blog.dataaccess.interaction.entity.LikeEntity;
import com.jackmouse.system.blog.dataaccess.interaction.repository.FavoriteJpaRepository;
import com.jackmouse.system.blog.dataaccess.interaction.repository.LikeJpaRepository;
import com.jackmouse.system.blog.domain.article.valueobject.ArticleId;
import com.jackmouse.system.blog.domain.interaction.entity.Favorite;
import com.jackmouse.system.blog.domain.interaction.entity.Like;
import com.jackmouse.system.blog.domain.interaction.repository.InteractionRepository;
import com.jackmouse.system.blog.domain.interaction.valueobject.FavoriteId;
import com.jackmouse.system.blog.domain.valueobject.UserId;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @ClassName InteractionRepositoryImpl
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 11:15
 * @Version 1.0
 **/
@Component
public class InteractionRepositoryImpl implements InteractionRepository {

    private final LikeJpaRepository likeJpaRepository;
    private final FavoriteJpaRepository favoriteJpaRepository;

    public InteractionRepositoryImpl(LikeJpaRepository likeJpaRepository, FavoriteJpaRepository favoriteJpaRepository) {
        this.likeJpaRepository = likeJpaRepository;
        this.favoriteJpaRepository = favoriteJpaRepository;
    }

    @Override
    public Optional<Like> findLikeByArticleAndUserId(ArticleId articleId, UserId userId) {
        Optional<LikeEntity> likeEntity = likeJpaRepository.findByTargetIdAndUserId(articleId.getValue(), userId.getValue());
        return likeEntity.map(LikeEntity::toLike);
    }

    @Override
    public Like saveLike(Like like) {
        return likeJpaRepository.save(LikeEntity.from(like)).toLike();
    }

    @Override
    public Optional<Favorite> findFavoriteByArticleAndUserId(FavoriteId favoriteId, UserId userId) {
        Optional<FavoriteEntity> favorite = favoriteJpaRepository.findByTargetIdAndUserId(favoriteId.getValue(), userId.getValue());
        return favorite.map(FavoriteEntity::toFavorite);
    }

    @Override
    public Favorite saveFavorite(Favorite favorite) {
        return favoriteJpaRepository.save(FavoriteEntity.from(favorite)).toFavorite();
    }
}
