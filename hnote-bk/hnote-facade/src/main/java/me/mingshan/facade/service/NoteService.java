package me.mingshan.facade.service;

import com.github.pagehelper.PageInfo;
import me.mingshan.common.service.BaseService;
import me.mingshan.facade.model.Note;

/**
 * @Author: mingshan
 * @Date: Created in 16:51 2018/4/27
 */
public interface NoteService extends BaseService<Note> {

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
}
