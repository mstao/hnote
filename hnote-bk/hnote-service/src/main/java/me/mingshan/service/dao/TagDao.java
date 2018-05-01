package me.mingshan.service.dao;

import me.mingshan.common.dao.BaseDao;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.model.NoteToTag;
import me.mingshan.facade.model.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 20:21 2018/4/30
 */
public interface TagDao extends BaseDao<Tag> {

    /**
     * Select all tags by note id.
     * @param nid
     * @return
     */
    List<Tag> selectAllByNid(long nid);

    /**
     * Insert notetotag
     *
     * @param noteToTag
     */
    void insertNoteToTag(NoteToTag noteToTag);
}
