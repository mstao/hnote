package me.mingshan.service.dao;

import me.mingshan.common.dao.BaseDao;
import me.mingshan.facade.model.User;
import me.mingshan.hnote.cache.annotation.Cache;
import me.mingshan.hnote.cache.annotation.CacheDelete;
import me.mingshan.hnote.cache.annotation.CacheDeleteKey;
import me.mingshan.hnote.cache.annotation.ExCache;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: mingshan
 * @Date: Created in 21:11 2018/4/28
 */
public interface UserDao {

    /**
     * Delete by id and version.
     * @param id
     * @param version
     * @return
     */
    @CacheDelete({@CacheDeleteKey(value="'user_mapper_selectUserById_'+#args[0]")})
    Integer delete(@Param("id") Long id, @Param("version") Integer version);

    /**
     * Inserts model to database.
     * @param user
     * @return
     */
    Long insert(User user);

    /**
     * Selects model by id.
     * @param id
     * @return
     */
    @Cache(expire=600, key="'user_mapper_selectUserById_'+#args[0]", exCache={@ExCache(expire=600, key="'user_mapper_selectByUserName_'+#retVal.name")})
    User selectByPrimaryKey(Long id);

    /**
     * Update model information.
     * @param user
     * @return
     */
    Integer update(User user);

    /**
     * Select user by username
     * @param name
     * @return
     */
    @Cache(expire=600, key="'user_mapper_selectByUserName'+#args[0]", exCache={@ExCache(expire=600, key="'user_mapper_selectUserById_'+#retVal.id")})
    User selectByUserName(@Param("name") String name);

    /**
     * Select version by id.
     * @param id
     * @return
     */
    Integer selectVersion(Long id);
}
