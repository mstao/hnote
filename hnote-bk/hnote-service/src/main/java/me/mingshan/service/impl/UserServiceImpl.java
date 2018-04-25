package me.mingshan.service.impl;

import me.mingshan.facade.model.User;
import me.mingshan.facade.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 10:56 2018/4/25
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public User findById(long id) {
        User user = new User();
        user.setName("mingshan");
        return user;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public long insert(User model) {
        return 0;
    }

    @Override
    public long insertSelective(User model) {
        return 0;
    }

    @Override
    public User selectByUniqueFiled(User model) {
        return null;
    }

    @Override
    public long update(User model) {
        return 0;
    }

    @Override
    public long delete(long id) {
        return 0;
    }
}
