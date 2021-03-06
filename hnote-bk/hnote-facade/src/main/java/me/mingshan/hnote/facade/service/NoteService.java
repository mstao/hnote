package me.mingshan.hnote.facade.service;

import com.github.pagehelper.PageInfo;
import me.mingshan.hnote.common.exception.ServerException;
import me.mingshan.hnote.facade.model.Note;


/**
 * 支持乐观锁
 *
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
    void update(Note note) throws ServerException;

    /**
     *  Delete note by id.
     *
     * @param id
     */
    void delete(Long id) throws ServerException;

    /**
     * Get all note by pagination.
     * @param pageNumber
     * @param pageSize
     * @param fid
     * @param sort
     * @param sortType
     * @return
     */
    PageInfo<Note> findAll(Integer pageNumber, Integer pageSize, Long fid, String sort, String sortType);

    /**
     *
     * @param nid
     * @param tid
     */
    void deleteByNidTid(Long nid, Long tid);

    /**
     * Get the lastest notes.
     * @param pageNumber
     * @param pageSize
     * @param sort
     * @param sortType
     * @return
     */
    PageInfo<Note> findLastestNotes(Long uid, Integer pageNumber, Integer pageSize, String sort, String sortType);

    /**
     * Fuzzy search.
     * @param token
     * @param pageNumber
     * @param pageSize
     * @param sort
     * @param sortType
     * @return
     */
    PageInfo<Note> findByToken(String token, Integer pageNumber, Integer pageSize, String sort, String sortType);


    /**
     * Select by tagId.
     * @param tid
     * @param pageNumber
     * @param pageSize
     * @param sort
     * @param sortType
     * @return
     */
    PageInfo<Note> findByTid(Long tid, Integer pageNumber, Integer pageSize, String sort, String sortType);

    /**
     * Update folder of note.
     * @param folderId
     * @param id
     */
    void updateFolder(Integer folderId, Long id) throws ServerException;
}
