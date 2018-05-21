package me.mingshan.facade.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import me.mingshan.facade.model.Folder;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 20:34 2018/4/30
 */
public interface FolderService {

    /**
     * Find by folder by id.
     *
     * @param id
     * @return
     */
    @Cached(name="folderCache-", key="#id", expire = 3600)
    Folder findById(Long id);

    /**
     * Insert folder.
     *
     * @param folder
     * @return
     */
    Long insert(Folder folder);

    /**
     * Delete by id.
     *
     * @param id
     * @return
     */
    @CacheInvalidate(name="folderCache-", key="#id")
    void delete(Long id);

    /**
     * Find all folders by uid.
     *
     * @param uid
     * @return
     */
    List<Folder> findAllByUid(long uid);

    /**
     * Rename folder.
     *
     * @param id
     * @param newName
     */
    void rename(long id, String newName);
}
