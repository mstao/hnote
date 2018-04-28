package me.mingshan.common.dao;

import java.util.List;

/**
 * 公共DAO
 * @author mingshan
 *
 * @param <T>
 */
public interface BaseDao<T> {
    /**
     * Deletes by batch.
     * @param model
     * @return
     */
    long deleteBatch(T model);

    /**
     * Inserts model to database.
     * @param model
     * @return
     */
    long insert(T model);

    /**
     * Selects model by id.
     * @param id
     * @return
     */
    T selectByPrimaryKey(long id);

    /**
     * Update model information.
     * @param model
     * @return
     */
    long update(T model);

    /**
     *
     * @return
     */
    List<T> selectByPage();
}