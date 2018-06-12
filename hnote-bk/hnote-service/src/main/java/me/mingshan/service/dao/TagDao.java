package me.mingshan.service.dao;

import me.mingshan.common.dao.BaseDao;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.model.NoteToTag;
import me.mingshan.facade.model.Tag;
import me.mingshan.hnote.cache.annotation.Cache;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 20:21 2018/4/30
 */
public interface TagDao {

    /**
     * Inserts model to database.
     * @param tag
     * @return
     */
    Long insert(Tag tag);

    /**
     * Selects model by id.
     * @param id
     * @return
     */
    @Cache(expire=600, autoload=true, key="'tag_mapper_selectTagById_'+#args[0]", condition="#args[0]>0")
    Tag selectByPrimaryKey(Long id);

    /**
     * Select all tags by note id.
     * @param nid
     * @return
     */
    List<Tag> selectAllByNid(long nid);

    /**
     * Select tag by id.
     * @param name
     * @param uid
     * @return
     */
    Tag selectByNameUid(@Param("name") String name, @Param("uid") Long uid);

    /**
     * Select by uid.
     * @param uid
     * @return
     */
    List<Tag> selectByUid(Long uid);

    /**
     * Insert notetotag
     *
     * @param noteToTag
     */
    Long insertNoteToTag(NoteToTag noteToTag);
}
