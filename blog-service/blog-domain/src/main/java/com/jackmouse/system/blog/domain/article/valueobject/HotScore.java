package com.jackmouse.system.blog.domain.article.valueobject;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @ClassName HotScore
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/7 13:21
 * @Version 1.0
 **/
public record HotScore(double value) implements Comparable<HotScore>{

    public static HotScore calculate(ArticleStats stats, LocalDateTime publishTime) {
        // 基础分数计算
        double baseScore = stats.likes() * HotScoreConfig.LIKE_WEIGHT
                + stats.favorites() * HotScoreConfig.FAVORITE_WEIGHT
                + stats.readCount() * HotScoreConfig.READ_WEIGHT;

        // 时间衰减计算
        long days = Duration.between(publishTime, LocalDateTime.now()).toDays();
        double decayFactor = Math.pow(HotScoreConfig.DECAY_RATE, days);

        // 最终热度值（保留两位小数）
        double finalScore = Math.round(baseScore * decayFactor * 100) / 100.0;
        return new HotScore(finalScore);
    }

    public static class HotScoreConfig {
        public static final double LIKE_WEIGHT = 0.3;
        public static final double FAVORITE_WEIGHT = 0.5;
        public static final double READ_WEIGHT = 0.2;
        public static final double DECAY_RATE = 0.95; // 每日衰减率
    }

    // 比较逻辑
    @Override
    public int compareTo(HotScore other) {
        return Double.compare(this.value, other.value);
    }

    // 实用方法
    public boolean isHot() {
        return value > 100.0;
    }
}
