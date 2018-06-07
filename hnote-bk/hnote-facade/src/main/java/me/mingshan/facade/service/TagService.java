package me.mingshan.facade.service;

import me.mingshan.facade.model.Tag;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 17:03 2018/4/27
 */
public interface TagService {

    /**
     * Find by id.
     * @param id
     * @return
     */
    Tag findById(Long id);

    /**
     * Select all tags by note id.
     * @param nid
     * @return
     */
    List<Tag> findAllByNid(Long nid);

    /**
     * Find by uid.
     * @param uid
     * @return
     */
    List<Tag> findByUid(Long uid);

    /**
     *
     * @param tag
     * @param nid
     * @return
     */
    Long insert(Tag tag, Long nid);
}
