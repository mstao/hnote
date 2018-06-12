package me.mingshan.hnote.facade.service;


import com.github.pagehelper.PageInfo;
import me.mingshan.hnote.facade.model.Note;

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
    PageInfo<Note> findAll(Integer pageNumber, Integer pageSize, String sort, String sortType);

    /**
     * Reset note which is already deleted.
     * @param id
     */
    void recover(Long id);
}
