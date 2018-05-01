package me.mingshan.service.dao;

import me.mingshan.common.dao.BaseDao;
import me.mingshan.facade.model.Folder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 20:21 2018/4/30
 */
public interface FolderDao extends BaseDao<Folder> {

    /**
     * Select all notes by uid.
     * @param uid
     * @return
     */
    List<Folder> selectAllByUid(long uid);

    /**
     * Rename folder.
     * @param id
     * @param newName
     */
    void rename(@Param("id")long id, @Param("name")String newName);
}
