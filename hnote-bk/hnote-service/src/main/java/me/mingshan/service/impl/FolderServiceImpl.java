package me.mingshan.service.impl;

import me.mingshan.facade.model.Folder;
import me.mingshan.facade.service.FolderService;
import me.mingshan.service.dao.FolderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 20:34 2018/4/30
 */
@Service
public class FolderServiceImpl implements FolderService {
    @Autowired
    private FolderDao folderDao;

    @Override
    public Folder findById(Long id) {
        return folderDao.selectByPrimaryKey(id);
    }

    @Override
    public Long insert(Folder model) {
        folderDao.insert(model);
        return model.getId();
    }


    @Override
    public void delete(Long id) {
        folderDao.delete(id);
    }

    @Override
    public List<Folder> findAllByUid(long uid) {
        return folderDao.selectAllByUid(uid);
    }

    @Override
    public void rename(long id, String newName) {
        folderDao.rename(id, newName);
    }
}
