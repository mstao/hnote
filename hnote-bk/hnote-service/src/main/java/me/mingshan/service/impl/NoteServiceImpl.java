package me.mingshan.service.impl;

import com.alicp.jetcache.anno.Cached;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.service.NoteService;
import me.mingshan.service.dao.NoteDao;
import me.mingshan.service.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 17:16 2018/4/27
 */
@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteDao noteDao;

    @Override
    public Note findById(Long id) {
        Note note = noteDao.selectByPrimaryKey(id);
        return note;
    }

    @Override
    public PageInfo<Note> findAll(int pageNumber, int pageSize, long fid) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Note> notes = noteDao.selectByPage(fid);
        PageInfo<Note> page = new PageInfo<>(notes);
        return page;
    }

    @Override
    public void deleteByNidTid(long nid, long tid) {
        noteDao.deleteByNidTid(nid, tid);
    }

    @Override
    public PageInfo<Note> findLastestNotes(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Note> notes = noteDao.selectLastestNotes();
        PageInfo<Note> page = new PageInfo<>(notes);
        return page;
    }

    @Override
    public Long insert(Note model) {
        noteDao.insert(model);
        return model.getId();
    }

    @Override
    public void update(Note model) {
        noteDao.update(model);
    }

    @Override
    public void delete(Long id) {
         noteDao.delete(id);
    }
}
