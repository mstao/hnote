package me.mingshan.service.dao;

import me.mingshan.facade.model.Folder;
import me.mingshan.hnote.cache.annotation.Cache;
import me.mingshan.hnote.cache.annotation.CacheDelete;
import me.mingshan.hnote.cache.annotation.CacheDeleteKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 20:21 2018/4/30
 */
public interface FolderDao {

    /**
     * Delete by id and version.
     * @param id
     * @param version
     * @return
     */
    @CacheDelete({@CacheDeleteKey(value="'folder_mapper_selectFolderById_'+#args[0]")})
    Integer delete(@Param("id") Long id, @Param("version") Integer version);

    /**
     * Inserts model to database.
     * @param folder
     * @return
     */
    Long insert(Folder folder);

    /**
     * Selects model by id.
     * @param id
     * @return
     */
    @Cache(expire=600, autoload=true, key="'folder_mapper_selectFolderById_'+#args[0]", condition="#args[0]>0")
    Folder selectByPrimaryKey(Long id);

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
