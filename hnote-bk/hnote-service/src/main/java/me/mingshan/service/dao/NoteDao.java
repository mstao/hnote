package me.mingshan.service.dao;

import me.mingshan.common.dao.BaseDao;
import me.mingshan.facade.model.Note;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 15:05 2018/4/29
 */
public interface NoteDao extends BaseDao<Note> {

    /**
     * select by paignation.
     * @return
     */
    List<Note> selectByPage(long nid);
}
