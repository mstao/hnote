package me.mingshan.facade.service;

import me.mingshan.common.service.BaseService;
import me.mingshan.facade.model.Folder;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 20:34 2018/4/30
 */
public interface FolderService extends BaseService<Folder> {

    /**
     * Find all folders by uid.
     * @param uid
     * @return
     */
    List<Folder> findAllByUid(long uid);
}
