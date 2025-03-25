package com.jackmouse.system.redis.utils;

import com.jackmouse.system.utils.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName RedisUtil
 * @Description
 * @Author zhoujiaangyao
 * @Date 2025/3/21 14:30
 * @Version 1.0
 **/
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {
    /**
     * 24小时过期，单位：秒.
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

    /**
     * 1小时过期，单位：秒.
     */
    public final static long ONE_HOUR_EXPIRE = 60 * 60;

    /**
     * 6小时过期，单位：秒.
     */
    public final static long SIX_HOUR_EXPIRE = 60 * 60 * 6;

    /**
     * 5分钟过期，单位：秒.
     */
    public final static long FIVE_MINUTE_EXPIRE = 5 * 60;

    /**
     * 永不过期.
     */
    public final static long NOT_EXPIRE = -1L;

    private final StringRedisTemplate redisTemplate;

    private final RedissonClient redissonClient;

    public List<Object> executePipelined(RedisCallback<?> action) {
        return redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            connection.openPipeline();
            try {
                return action.doInRedis(connection);
            } finally {
                connection.closePipeline();
            }
        });
    }

    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    public RLock getFencedLock(String key) {
        return redissonClient.getFencedLock(key);
    }

    public RLock getFairLock(String key) {
        return redissonClient.getFairLock(key);
    }

    public RLock getReadLock(String key) {
        return redissonClient.getReadWriteLock(key).readLock();
    }

    public RLock getWriteLock(String key) {
        return redissonClient.getReadWriteLock(key).writeLock();
    }

    public boolean tryLock(RLock lock, long timeout) throws InterruptedException {
        return lock.tryLock(timeout, TimeUnit.MILLISECONDS);
    }



    public void unlock(String key) {
        unlock(getLock(key));
    }

    public void unlock(RLock lock) {
        lock.unlock();
    }

    public void lock(String key) {
        lock(getLock(key));
    }

    public void lock(RLock lock) {
        lock.lock();
    }

    public boolean isLocked(String key) {
        return isLocked(getLock(key));
    }

    public boolean isLocked(RLock lock) {
        return lock.isLocked();
    }

    public boolean isHeldByCurrentThread(String key) {
        return isHeldByCurrentThread(getLock(key));
    }

    public boolean isHeldByCurrentThread(RLock lock) {
        return lock.isHeldByCurrentThread();
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public void set(String key, Object value, long expire) {
        if (expire == NOT_EXPIRE) {
            redissonClient.getBucket(key).set(value);
        }
        else {
            redissonClient.getBucket(key).set(value, Duration.ofSeconds(expire));
        }
    }

    public void lSet(String key, List<Object> objList, long expire) {
        RList<Object> rList = redissonClient.getList(key);
        rList.expireIfNotSet(Duration.ofSeconds(expire));
        rList.addAll(objList);
    }

    public void lSet(String key, Object obj, long expire) {
        RList<Object> rList = redissonClient.getList(key);
        rList.expireIfNotSet(Duration.ofSeconds(expire));
        rList.add(obj);
    }

    public int lSize(String key) {
        return redissonClient.getList(key).size();
    }

    public void lTrim(String key, int start, int end) {
        redissonClient.getList(key).trim(start, end);
    }

    public List<Object> lGetAll(String key) {
        return redissonClient.getList(key).readAll();
    }

    public Object lGet(String key, int index) {
        return redissonClient.getList(key).get(index);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public boolean setIfAbsent(String key, Object value) {
        return setIfAbsent(key, value, DEFAULT_EXPIRE);
    }

    public boolean setIfAbsent(String key, Object value, long expire) {
        return redissonClient.getBucket(key).setIfAbsent(value, Duration.ofSeconds(expire));
    }

    public Object get(String key) {
        return redissonClient.getBucket(key).get();
    }

    public void hDel(String key) {
        redissonClient.getMap(key).delete();
    }

    public boolean del(String... key) {
        return redissonClient.getKeys().delete(key) > 0;
    }

    public void hDel(String key, String field) {
        redissonClient.getMap(key).remove(field);
    }

    public void hDelFast(String key, String... field) {
        redissonClient.getMap(key).fastRemove(field);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public boolean hasHashKey(String key, String field) {
        return redissonClient.getMap(key).containsKey(field);
    }

    public long incr(String key, long value) {
        return redissonClient.getAtomicLong(key).addAndGet(value);
    }

    public long decr(String articleLikeKey, int i) {
        return redissonClient.getAtomicLong(articleLikeKey).addAndGet(-i);
    }

    public long incrementAndGet(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        atomicLong.expireIfNotSet(Duration.ofSeconds(ONE_HOUR_EXPIRE));
        return atomicLong.incrementAndGet();
    }

    public long decrementAndGet(String key) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        atomicLong.expireIfNotSet(Duration.ofSeconds(ONE_HOUR_EXPIRE));
        return atomicLong.decrementAndGet();
    }

    public long addAndGet(String key, long value) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
        atomicLong.expireIfNotSet(Duration.ofSeconds(ONE_HOUR_EXPIRE));
        return atomicLong.addAndGet(value);
    }


    public Set<String> keys() {
        return redissonClient.getKeys().getKeysStream().collect(Collectors.toSet());
    }

    public long getAtomicValue(String key) {
        return redissonClient.getAtomicLong(key).get();
    }

    public void hSet(String key, String field, Object value, long expire) {
        RMap<String, Object> map = redissonClient.getMap(key);
        map.expire(Duration.ofSeconds(expire));
        map.put(field, value);
    }

    public void hSetIfAbsentNative(String key, String field, Object value) {
        redisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    public void hSetFastAsync(String key, String field, Object value, long expire) {
        RMap<String, Object> map = redissonClient.getMap(key);
        map.expire(Duration.ofSeconds(expire));
        map.fastPutAsync(field, value);
    }

    public void hSetFast(String key, String field, Object value, long expire) {
        RMap<String, Object> map = redissonClient.getMap(key);
        map.expire(Duration.ofSeconds(expire));
        map.fastPut(field, value);
    }

    public void hSetIfAbsent(String key, String field, Object value, long expire) {
        RMap<String, Object> map = redissonClient.getMap(key);
        map.expire(Duration.ofSeconds(expire));
        map.putIfAbsent(field, value);
    }

    public void hSetIfAbsentAsync(String key, String field, Object value, long expire) {
        RMap<String, Object> map = redissonClient.getMap(key);
        map.expire(Duration.ofSeconds(expire));
        map.putIfAbsentAsync(field, value);
    }

    public void hSet(String key, Map<String, Object> map, long expire) {
        RMap<String, Object> rMap = redissonClient.getMap(key);
        rMap.expire(Duration.ofSeconds(expire));
        rMap.putAll(map);
    }

    public void hSet(String key, String field, Object value) {
        hSet(key, field, value, NOT_EXPIRE);
    }

    public Object hGet(String key, String field) {
        return redissonClient.getMap(key).get(field);
    }

    public Object hGetNative(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }






    public <T> T execute(RedisScript<T> script, List<String> keys, Object... args) {
        return redisTemplate.execute(script, keys, args);
    }


    public boolean sAdd(String userLikeKey, UUID value) {
        return redissonClient.getSet(userLikeKey).add(value);
    }

    public boolean sRem(String userLikeKey, UUID value) {
        return redissonClient.getSet(userLikeKey).remove(value);
    }

    public double zIncrementScore(String hotScoreKey, double i, UUID value) {
        return redissonClient.getScoredSortedSet(hotScoreKey).addScore(value, i);
    }


    public double zDecrementScore(String hotScoreKey, double i, UUID value) {
        return redissonClient.getScoredSortedSet(hotScoreKey).addScore(value, -i);
    }
}
