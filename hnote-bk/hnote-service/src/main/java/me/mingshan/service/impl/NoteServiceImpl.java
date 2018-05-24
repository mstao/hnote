package me.mingshan.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.mingshan.facade.model.Note;
import me.mingshan.facade.service.NoteService;
import me.mingshan.service.dao.NoteDao;
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
    public PageInfo<Note> findAll(Integer pageNumber, Integer pageSize, Long fid, String sort, String sortType) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Note> notes = noteDao.selectByPage(fid, sort, sortType);
        PageInfo<Note> page = new PageInfo<>(notes);
        return page;
    }

    @Override
    public void deleteByNidTid(Long nid, Long tid) {
        noteDao.deleteByNidTid(nid, tid);
    }

    @Override
    public PageInfo<Note> findLastestNotes(Integer pageNumber, Integer pageSize, String sort, String sortType) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Note> notes = noteDao.selectLastestNotes(sort, sortType);
        PageInfo<Note> page = new PageInfo<>(notes);
        return page;
    }

    @Override
    public PageInfo<Note> findByToken(String token, Integer pageNumber, Integer pageSize,
                                      String sort, String sortType) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Note> notes = noteDao.selectByToken(token, sort, sortType);
        PageInfo<Note> page = new PageInfo<>(notes);
        return page;
    }

    @Override
    public PageInfo<Note> findByTid(Long tid, Integer pageNumber, Integer pageSize,
                                    String sort, String sortType) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Note> notes = noteDao.selectByTid(tid, sort, sortType);
        PageInfo<Note> page = new PageInfo<>(notes);
        return page;
    }

    @Override
    public void updateFolder(Integer folderId, Long id) {
        noteDao.updateFolder(folderId, id);
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
