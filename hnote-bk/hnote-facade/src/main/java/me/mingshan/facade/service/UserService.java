package me.mingshan.facade.service;


import me.mingshan.common.service.BaseService;
import me.mingshan.facade.model.User;

/**
 * @Author: mingshan
 * @Date: Created in 10:46 2018/4/24
 */
public interface UserService extends BaseService<User> {
    /**
     * Finds user by userName.
     * @param userName
     * @return The modle of user.
     */
    User findByUserName(String userName);
}
