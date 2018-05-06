package me.mingshan.service.impl;

import com.alicp.jetcache.anno.Cached;
import com.github.pagehelper.PageInfo;
import me.mingshan.facade.model.Folder;
import me.mingshan.facade.service.FolderService;
import me.mingshan.service.dao.FolderDao;
import me.mingshan.service.util.StringUtil;
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
    @Cached(name="folderCache.", key="#id", expire = 3600)
    public Folder findById(long id) {
        return folderDao.selectByPrimaryKey(id);
    }

    @Override
    public long insert(Folder model) {
        folderDao.insert(model);
        return model.getId();
    }

    @Override
    public long update(Folder model) {
        return 0;
    }

    @Override
    public long delete(long id) {
        return folderDao.delete(id);
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
