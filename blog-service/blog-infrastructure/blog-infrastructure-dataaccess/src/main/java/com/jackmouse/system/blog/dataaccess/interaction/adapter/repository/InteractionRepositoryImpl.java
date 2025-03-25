package com.jackmouse.system.blog.dataaccess.interaction.adapter.repository;

import com.jackmouse.system.blog.dataaccess.interaction.entity.FavoriteEntity;
import com.jackmouse.system.blog.dataaccess.interaction.entity.LikeEntity;
import com.jackmouse.system.blog.dataaccess.interaction.repository.FavoriteRepository;
import com.jackmouse.system.blog.dataaccess.interaction.repository.LikeRepository;
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

    private final LikeRepository likeRepository;
    private final FavoriteRepository favoriteRepository;

    public InteractionRepositoryImpl(LikeRepository likeRepository, FavoriteRepository favoriteRepository) {
        this.likeRepository = likeRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public Optional<Like> findLikeByArticleAndUserId(ArticleId articleId, UserId userId) {
        Optional<LikeEntity> likeEntity = likeRepository.findByTargetIdAndUserId(articleId.getValue(), userId.getValue());
        return likeEntity.map(LikeEntity::toLike);
    }

    @Override
    public Like saveLike(Like like) {
        return likeRepository.save(LikeEntity.from(like)).toLike();
    }

    @Override
    public Optional<Favorite> findFavoriteByArticleAndUserId(FavoriteId favoriteId, UserId userId) {
        Optional<FavoriteEntity> favorite = favoriteRepository.findByTargetIdAndUserId(favoriteId.getValue(), userId.getValue());
        return favorite.map(FavoriteEntity::toFavorite);
    }

    @Override
    public Favorite saveFavorite(Favorite favorite) {
        return favoriteRepository.save(FavoriteEntity.from(favorite)).toFavorite();
    }
}
