package me.mingshan.facade.service;

import com.alicp.jetcache.anno.Cached;
import me.mingshan.facade.model.Tag;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 17:03 2018/4/27
 */
public interface TagService {

    /**
     *
     * @param id
     * @return
     */
    @Cached(name="tagCache-", key="#id", expire = 3600)
    Tag findById(Long id);

    /**
     * Select all tags by note id.
     * @param nid
     * @return
     */
    List<Tag> findAllByNid(Long nid);

    /**
     *
     * @param tag
     * @param nid
     * @return
     */
    Long insert(Tag tag, Long nid);
}
