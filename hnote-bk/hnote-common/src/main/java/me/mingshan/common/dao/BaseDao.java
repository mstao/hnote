package me.mingshan.common.dao;

import java.util.List;

/**
 * @Description:
 * @Author: Minsghan
 * @Date: Created in 9:20 2017/10/21
 */
public interface BaseDao<T> {
    /**
     * Deletes model by id.
     * @param id
     * @return
     */
    long deleteByPrimaryKey(long id);

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
    List<T> selectAll();
}
