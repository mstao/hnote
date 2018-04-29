package me.mingshan.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
    public Map<String, Object> findAll(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        List<Note> notes = noteDao.selectByPage();
        // 获取总记录数
        long total = ((Page<Note>) notes).getTotal();
        Map<String, Object> params = new HashMap<>();
        params.put("items", notes);
        params.put("total", total);

        return params;
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
