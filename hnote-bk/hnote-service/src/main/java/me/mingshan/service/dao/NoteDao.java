package me.mingshan.service.dao;

import me.mingshan.facade.model.Note;
import me.mingshan.hnote.cache.annotation.Cache;
import me.mingshan.hnote.cache.annotation.CacheDelete;
import me.mingshan.hnote.cache.annotation.CacheDeleteKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 15:05 2018/4/29
 */
public interface NoteDao {

    /**
     * Delete by id and version.
     * @param id
     * @param version
     * @return
     */
    @CacheDelete({@CacheDeleteKey(value="'note_mapper_selectNoteById_'+#args[0]")})
    Integer delete(@Param("id") Long id, @Param("version") Integer version);

    /**
     * Inserts model to database.
     * @param note
     * @return
     */
    Long insert(Note note);

    /**
     * Selects model by id.
     * @param id
     * @return
     */
    @Cache(expire=600, autoload=true, key="'note_mapper_selectNoteById_'+#args[0]", condition="#args[0]>0")
    Note selectByPrimaryKey(Long id);

    /**
     * Update model information.
     * @param note
     * @return
     */
    @CacheDelete({@CacheDeleteKey(value="'note_mapper_selectNoteById_'+#args[0].id")})
    Integer update(Note note);

    /**
     * Select by paignation.
     *
     * @param fid
     * @param sort
     * @param sortType
     * @return
     */
    List<Note> selectByPage(@Param("fid") Long fid,
                            @Param("sort") String sort,
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
    List<Note> selectByToken(@Param("token") String token,
                             @Param("sort") String sort,
                             @Param("sortType") String sortType);

    /**
     * Select by tagId.
     * @param tid
     * @param sort
     * @param sortType
     * @return
     */
    List<Note> selectByTid(@Param("tid") Long tid,
                           @Param("sort") String sort,
                           @Param("sortType") String sortType);

    /**
     * Update folder of note.
     *
     * @param folderId
     * @param id
     */
    Integer updateFolder(@Param("folderId") Integer folderId, @Param("id") Long id, @Param("version") Integer version);

    /**
     * Get vesion by id.
     *
     * @param id
     * @return
     */
    Integer selectVersion(Long id);
}
