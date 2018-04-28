package me.mingshan.common.service;

import java.util.Map;

/**
 * @Author: mingshan
 * @Date: Created in 10:47 2018/4/24
 */
public interface BaseService<T> {
    T findById(long id);

    Map<String, Object> findAll(int pageNumber, int pageSize);

    long insert(T model);

    long update(T model);

    long delete(String ids);
}
