package me.mingshan.facade.service;

import me.mingshan.common.service.BaseService;
import me.mingshan.facade.model.NoteToTag;
import me.mingshan.facade.model.Tag;
import me.mingshan.facade.model.User;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 17:03 2018/4/27
 */
public interface TagService extends BaseService<Tag> {

    /**
     * Select all tags by note id.
     * @param nid
     * @return
     */
    List<Tag> findAllByNid(long nid);

    long insert(Tag tag, long nid);
}
