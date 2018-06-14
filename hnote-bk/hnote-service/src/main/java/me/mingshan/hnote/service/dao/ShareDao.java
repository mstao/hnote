package me.mingshan.hnote.service.dao;

import me.mingshan.hnote.cache.annotation.Cache;
import me.mingshan.hnote.facade.model.Note;
import me.mingshan.hnote.facade.model.Share;

/**
 * @Author: mingshan
 * @Date: Created in 19:17 2018/6/12
 */
public interface ShareDao {

    /**
     * Selects model by id.
     * @param noteId
     * @return
     */
    Share selectByNoteId(Long noteId);

    /**
     *
     * @param code
     * @return
     */
    Share selectByCode(String code);

    /**
     *
     * @param share
     * @return
     */
    Long insert(Share share);

    /**
     *
     * @param share
     * @return
     */
    Integer update(Share share);

    /**
     * Get vesion by id.
     *
     * @param id
     * @return
     */
    Integer selectVersion(Long id);
}
