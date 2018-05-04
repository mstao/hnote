package me.mingshan.service.impl;

import com.alicp.jetcache.anno.Cached;
import com.github.pagehelper.PageInfo;
import me.mingshan.facade.model.User;
import me.mingshan.facade.service.UserService;
import me.mingshan.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: mingshan
 * @Date: Created in 10:56 2018/4/25
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Cached(name="userCache.", key="#id", expire = 3600)
    public User findById(long id) {
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public long insert(User model) {
        return 0;
    }

    @Override
    public long update(User model) {
        return 0;
    }

    @Override
    public long delete(String ids) {
        return 0;
    }

    @Override
    @Cached(name="userCache.", key="#userName", expire = 3600)
    public User findByUserName(String userName) {
        User user = userDao.selectByUserName(userName);
        return user;
    }
}
