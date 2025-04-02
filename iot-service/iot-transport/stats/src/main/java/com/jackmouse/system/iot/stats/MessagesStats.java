package com.jackmouse.system.iot.stats;

/**
 * @ClassName MessagesStats
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/4/2 13:38
 * @Version 1.0
 **/
public interface MessagesStats {
    default void incrementTotal() {
        incrementTotal(1);
    }

    void incrementTotal(int amount);

    default void incrementSuccessful() {
        incrementSuccessful(1);
    }

    void incrementSuccessful(int amount);

    default void incrementFailed() {
        incrementFailed(1);
    }

    void incrementFailed(int amount);

    int getTotal();

    int getSuccessful();

    int getFailed();

    void reset();
}
