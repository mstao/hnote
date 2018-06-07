package me.mingshan.common.dao;

import org.apache.ibatis.annotations.Param;

/**
 * 公共DAO
 * @author mingshan
 *
 * @param <T>
 */
public interface BaseDao<T> {

    /**
     * Delete by id and version.
     * @param id
     * @param version
     * @return
     */
    Integer delete(@Param("id") Long id, @Param("version") Integer version);

    /**
     * Inserts model to database.
     * @param model
     * @return
     */
    Long insert(T model);

    /**
     * Selects model by id.
     * @param id
     * @return
     */
    T selectByPrimaryKey(Long id);

    /**
     * Update model information.
     * @param model
     * @return
     */
    Integer update(T model);
}