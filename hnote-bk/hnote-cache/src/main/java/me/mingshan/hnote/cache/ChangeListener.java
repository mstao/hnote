package me.mingshan.hnote.cache;

import me.mingshan.hnote.cache.to.CacheKeyTO;
import me.mingshan.hnote.cache.to.CacheWrapper;

/**
 * 缓存更新
 * @author jiayu.qiu
 */
public interface ChangeListener {

    /**
     * 缓存更新
     * @param cacheKey 缓存Key
     * @param newVal 新缓存值
     */
    void update(CacheKeyTO cacheKey, CacheWrapper<Object> newVal);

    /**
     * 缓存删除
     * @param cacheKey 缓存Key
     */
    void delete(CacheKeyTO cacheKey);
}
