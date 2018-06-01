package me.mingshan.service.dao;

import me.mingshan.common.dao.BaseDao;
import me.mingshan.facade.model.Note;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 15:05 2018/4/29
 */
public interface NoteDao extends BaseDao<Note> {


    /**
     * Select by paignation.
     *
     * @param fid
     * @param sort
     * @param sortType
     * @return
     */
    List<Note> selectByPage(@Param("fid") Long fid, @Param("sort") String sort,
                            @Param("sortType") String sortType);

    /**
     * Deletes tag by noteId and tagId
     * @param nid
     * @param tid
     */
    Integer deleteByNidTid(@Param("nid") Long nid, @Param("tid") Long tid);

    /**
     * Select the lastest notes.
     * @param sort
     * @param sortType
     * @return
     */
    List<Note> selectLastestNotes(@Param("sort") String sort, @Param("sortType") String sortType);

    /**
     * Fuzzy search.
     * @param token
     * @param sort
     * @param sortType
     * @return
     */
    List<Note> selectByToken(@Param("token") String token, @Param("sort") String sort,
                             @Param("sortType") String sortType);

    /**
     * Select by tagId.
     * @param tid
     * @param sort
     * @param sortType
     * @return
     */
    List<Note> selectByTid(@Param("tid") Long tid, @Param("sort") String sort,
                           @Param("sortType") String sortType);

    /**
     * Update folder of note.
     *
     * @param folderId
     * @param id
     */
    Integer updateFolder(@Param("folderId") Integer folderId, @Param("id") Long id, Integer version);

    /**
     * Get vesion by id.
     *
     * @param id
     * @return
     */
    Integer selectVersion(Long id);
}
