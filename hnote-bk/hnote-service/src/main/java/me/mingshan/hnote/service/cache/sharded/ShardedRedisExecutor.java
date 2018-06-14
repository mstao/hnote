package me.mingshan.hnote.service.cache.sharded;

import redis.clients.jedis.ShardedJedis;

/**
 * Redis具体逻辑接口
 *
 * @author mingshan
 * @param <T>
 */
public interface ShardedRedisExecutor<T> {

    /**
     * 执行方法
     *
     * @param jedis
     * @return
     */
    T execute(ShardedJedis jedis);
}
