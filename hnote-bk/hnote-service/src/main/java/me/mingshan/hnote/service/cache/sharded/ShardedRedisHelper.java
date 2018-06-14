package me.mingshan.hnote.service.cache.sharded;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 分片redis
 * 
 * @author zhengzhiyuan
 * @since May 20, 2016
 */
@Component
public class ShardedRedisHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShardedRedisHelper.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;


    /**
     * 实现jedis连接的获取和释放，具体的redis业务逻辑由executor实现
     * 
     * @param executor RedisExecutor接口的实现类
     * @return
     */
    public <T> T execute(String key, ShardedRedisExecutor<T> executor) {
        ShardedJedis jedis = shardedJedisPool.getResource();
        T result = null;
        try {
            result = executor.execute(jedis);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public String set(final String key, final String value) {
        return execute(key, jedis -> jedis.set(key, value));
    }

    public String set(final String key, final String value, final String nxxx, final String expx, final long time) {
        return execute(key, jedis -> jedis.set(key, value, nxxx, expx, time));
    }

    public String get(final String key) {
        return execute(key, jedis -> jedis.get(key));
    }

    public Boolean exists(final String key) {
        return execute(key, jedis -> jedis.exists(key));
    }

    public Long setnx(final String key, final String value) {
        return execute(key, jedis -> jedis.setnx(key, value));
    }

    public String setex(final String key, final int seconds, final String value) {
        return execute(key, jedis -> jedis.setex(key, seconds, value));
    }

    public Long expire(final String key, final int seconds) {
        return execute(key, jedis -> jedis.expire(key, seconds));
    }

    public Long incr(final String key) {
        return execute(key, jedis -> jedis.incr(key));
    }

    public Long decr(final String key) {
        return execute(key, jedis -> jedis.decr(key));
    }

    public Long hset(final String key, final String field, final String value) {
        return execute(key, jedis -> jedis.hset(key, field, value));
    }

    public String hget(final String key, final String field) {
        return execute(key, jedis -> jedis.hget(key, field));
    }

    public String hmset(final String key, final Map<String, String> hash) {
        return execute(key, jedis -> jedis.hmset(key, hash));
    }

    public List<String> hmget(final String key, final String... fields) {
        return execute(key, jedis -> jedis.hmget(key, fields));
    }

    public Long del(final String key) {
        return execute(key, jedis -> jedis.del(key));
    }

    public Map<String, String> hgetAll(final String key) {
        return execute(key, jedis -> jedis.hgetAll(key));
    }

    public void destroy() {
        this.shardedJedisPool.close();
    }
}
