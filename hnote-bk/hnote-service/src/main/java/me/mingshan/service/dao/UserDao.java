package me.mingshan.service.dao;

import me.mingshan.common.dao.BaseDao;
import me.mingshan.facade.model.User;

/**
 * @Author: mingshan
 * @Date: Created in 21:11 2018/4/28
 */
public interface UserDao extends BaseDao<User> {
    /**
     * Select user by username
     * @param userName
     * @return
     */
    User selectByUserName(String userName);

    /**
     * Select version by id.
     * @param id
     * @return
     */
    Integer selectVersion(Long id);
}
