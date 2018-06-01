package me.mingshan.service.impl;

import me.mingshan.common.exception.ServerException;
import me.mingshan.facade.model.User;
import me.mingshan.facade.service.UserService;
import me.mingshan.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: mingshan
 * @Date: Created in 10:56 2018/4/25
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User findById(Long id) {
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public void delete(Long id) {
        Integer version = userDao.selectVersion(id);
        Integer result = userDao.delete(id, version);
        if (result == 0) {
            throw new ServerException();
        }
    }

    @Override
    public User findByUserName(String userName) {
        User user = userDao.selectByUserName(userName);
        return user;
    }
}
