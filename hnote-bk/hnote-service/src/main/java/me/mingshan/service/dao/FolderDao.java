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
     * Select all folders by uid.
     * @param uid
     * @return
     */
    List<Folder> selectAllByUid(long uid);

    /**
     * Select by label and uid.
     * @param label
     * @param uid
     * @return
     */
    Folder selectByLabel(@Param("label") String label, @Param("uid") Long uid);

    /**
     * Rename folder.
     * @param id
     * @param newName
     */
    Integer rename(@Param("id")long id, @Param("name")String newName, @Param("version") Integer version);

    /**
     * Select version by id.
     * @param id
     * @return
     */
    Integer selectVersion(Long id);
}
