package me.mingshan.service.dao;

import me.mingshan.common.dao.BaseDao;
import me.mingshan.facade.model.Folder;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 20:21 2018/4/30
 */
public interface FolderDao extends BaseDao<Folder> {

    List<Folder> selectAllByUid(long uid);
}
