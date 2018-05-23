package me.mingshan.service.dao;

import me.mingshan.facade.model.Note;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 20:24 2018/4/30
 */
public interface TrashDao {
    /**
     * Select by paignation.
     * @return
     */
    List<Note> selectByPage();
}
