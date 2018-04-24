package me.mingshan.common.service;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 10:47 2018/4/24
 */
public interface BaseService<T> {
    T findById(long id);

    List<T> findAll();

    long insert(T model);

    long insertSelective(T model);

    T selectByUniqueFiled(T model);

    long update(T model);

    long delete(long id);
}
