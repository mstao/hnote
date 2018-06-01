package me.mingshan.facade.service;


import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.Cached;
import me.mingshan.common.exception.ServerException;
import me.mingshan.facade.model.User;

/**
 * @Author: mingshan
 * @Date: Created in 10:46 2018/4/24
 */
public interface UserService {

    /**
     * Find by id.
     * @param id
     * @return
     */
    @Cached(name="userCache-", key="#id", expire = 3600)
    User findById(Long id);

    /**
     * Finds user by userName.
     *
     * @param userName
     * @return The modle of user.
     */
    User findByUserName(String userName);

    /**
     * Delete user.
     * @param id
     * @return
     */
    @CacheInvalidate(name="userCache-", key="#id")
    void delete(Long id) throws ServerException;
}
