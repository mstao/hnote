package me.mingshan.facade.service;

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
    void delete(Long id) throws ServerException;

    /**
     * Add user.
     * @return
     */
    Long insert(User user);
}
