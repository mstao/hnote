package me.mingshan.facade.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheUpdate;
import com.alicp.jetcache.anno.Cached;
import com.github.pagehelper.PageInfo;
import me.mingshan.facade.model.Note;

/**
 * @Author: mingshan
 * @Date: Created in 16:51 2018/4/27
 */
public interface NoteService {

    /**
     * Get by id.
     *
     * @param id
     * @return
     */
    @Cached(name="noteCache-", key="#id", expire = 3600)
    Note findById(Long id);

    /**
     * Insert note.
     *
     * @param note
     * @return
     */
    Long insert(Note note);

    /**
     *  Update note.
     *
     * @param note
     */
    @CacheUpdate(name="noteCache-", key="#note.id", value="#note")
    void update(Note note);

    /**
     *  Delete note by id.
     *
     * @param id
     */
    @CacheInvalidate(name="noteCache-", key="#id")
    void delete(Long id);

    /**
     * Get all note by pagination.
     *
     * @param pageNumber
     * @param pageSize
     * @param fid
     * @return
     */
    PageInfo<Note> findAll(int pageNumber, int pageSize, long fid);

    /**
     *
     * @param nid
     * @param tid
     */
    void deleteByNidTid(long nid, long tid);

    /**
     * Get the lastest notes.
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageInfo<Note> findLastestNotes(int pageNumber, int pageSize);
}
