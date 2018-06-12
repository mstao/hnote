package me.mingshan.hnote.service.impl;

import me.mingshan.hnote.common.exception.ServerException;
import me.mingshan.hnote.facade.model.Folder;
import me.mingshan.hnote.facade.service.FolderService;
import me.mingshan.hnote.service.dao.FolderDao;
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
    public void delete(Long id) throws ServerException {
        Integer version = folderDao.selectVersion(id);
        Integer result = folderDao.delete(id, version);
        if (result == 0) {
            throw new ServerException();
        }
    }

    @Override
    public List<Folder> findAllByUid(long uid) {
        return folderDao.selectAllByUid(uid);
    }

    @Override
    public void rename(long id, String newName) throws ServerException {
        Integer version = folderDao.selectVersion(id);
        Integer result = folderDao.rename(id, newName, version);
        if (result == 0) {
            throw new ServerException();
        }
    }

    @Override
    public Folder findByLabel(String label, Long uid) {
        return folderDao.selectByLabel(label, uid);
    }
}
