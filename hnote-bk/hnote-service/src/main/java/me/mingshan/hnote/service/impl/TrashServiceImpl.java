package me.mingshan.hnote.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import me.mingshan.hnote.facade.model.Note;
import me.mingshan.hnote.facade.service.TrashService;
import me.mingshan.hnote.service.dao.TrashDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 17:40 2018/4/27
 */
@Service
public class TrashServiceImpl implements TrashService {

    @Autowired
    private TrashDao trashDao;

    @Override
    public PageInfo<Note> findAll(Integer pageNumber, Integer pageSize, String sort, String sortType) {
        PageHelper.startPage(pageNumber, pageSize);
        List<Note> notes = trashDao.selectByPage(sort, sortType);
        PageInfo<Note> page = new PageInfo<>(notes);
        return page;
    }

    @Override
    public void recover(Long id) {
        trashDao.recover(id);
    }
}
