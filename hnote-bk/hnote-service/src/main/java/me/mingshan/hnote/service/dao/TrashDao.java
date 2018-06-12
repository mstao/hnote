package me.mingshan.hnote.service.dao;

import me.mingshan.hnote.facade.model.Note;
import org.apache.ibatis.annotations.Param;

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
    List<Note> selectByPage(@Param("sort") String sort, @Param("sortType") String sortType);

    /**
     * Reset note which is already deleted.
     * @param id
     */
    Integer recover(Long id);
}
