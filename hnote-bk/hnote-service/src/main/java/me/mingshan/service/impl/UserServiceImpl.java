package me.mingshan.service.impl;

import me.mingshan.common.exception.ServerException;
import me.mingshan.facade.model.Folder;
import me.mingshan.facade.model.User;
import me.mingshan.facade.service.UserService;
import me.mingshan.service.config.Constants;
import me.mingshan.service.dao.FolderDao;
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

    @Autowired
    private FolderDao folderDao;

    @Override
    public User findById(Long id) {
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public void delete(Long id) throws ServerException {
        Integer version = userDao.selectVersion(id);
        Integer result = userDao.delete(id, version);
        if (result == 0) {
            throw new ServerException();
        }
    }

    @Override
    public Long insert(User user) {
        userDao.insert(user);
        Long userId = user.getId();
        // 设置默认文件夹
        Folder folder = new Folder();
        folder.setPid(0L);
        folder.setLevel(0);
        folder.setUid(userId);
        folder.setLabel(Constants.DEFAULT_FOLDER);
        folderDao.insert(folder);

        return userId;
    }

    @Override
    public User findByUserName(String userName) {
        return userDao.selectByUserName(userName);
    }
}
