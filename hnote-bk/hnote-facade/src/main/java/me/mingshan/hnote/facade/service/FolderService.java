package me.mingshan.hnote.facade.service;

import me.mingshan.hnote.common.exception.ServerException;
import me.mingshan.hnote.facade.model.Folder;

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
    void delete(Long id) throws ServerException;

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
    void rename(long id, String newName) throws ServerException;

    /**
     * Find by label and uid.
     *
     * @param label
     * @param uid
     * @return
     */
    Folder findByLabel(String label, Long uid);
}
