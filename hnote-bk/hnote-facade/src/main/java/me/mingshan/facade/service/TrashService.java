package me.mingshan.facade.service;


import com.github.pagehelper.PageInfo;
import me.mingshan.facade.model.Note;

/**
 * @Author: mingshan
 * @Date: Created in 17:04 2018/4/27
 */
public interface TrashService {

    /**
     * Get all note by pagination.
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageInfo<Note> findAll(Integer pageNumber, Integer pageSize);
}
