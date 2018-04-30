package me.mingshan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.service.NoteService;
import me.mingshan.service.dao.NoteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: mingshan
 * @Date: Created in 17:16 2018/4/27
 */
@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteDao noteDao;

    @Override
    public Note findById(long id) {
        Note note = noteDao.selectByPrimaryKey(id);
        return note;
    }

    @Override
    public PageInfo<Note> findAll(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        List<Note> notes = noteDao.selectByPage();
        PageInfo<Note> page = new PageInfo<>(notes);
        return page;
    }

    @Override
    public long insert(Note model) {
        return 0;
    }

    @Override
    public long update(Note model) {
        return 0;
    }

    @Override
    public long delete(String ids) {
        return 0;
    }
}
