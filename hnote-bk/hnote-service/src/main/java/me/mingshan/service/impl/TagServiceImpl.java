package me.mingshan.service.impl;

import com.github.pagehelper.PageInfo;
import me.mingshan.facade.model.NoteToTag;
import me.mingshan.facade.model.Tag;
import me.mingshan.facade.service.TagService;
import me.mingshan.service.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: mingshan
 * @Date: Created in 17:17 2018/4/27
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;

    @Override
    public Tag findById(long id) {
        return tagDao.selectByPrimaryKey(id);
    }

    @Override
    public long insert(Tag model) {
        return 0;
    }

    @Override
    public long update(Tag model) {
        return 0;
    }

    @Override
    public long delete(String ids) {
        return 0;
    }

    @Override
    public List<Tag> findAllByNid(long nid) {
        return tagDao.selectAllByNid(nid);
    }

    @Override
    public long insert(Tag tag, long nid) {
        long tagId = tagDao.insert(tag);
        NoteToTag noteToTag = new NoteToTag();
        noteToTag.setTagId(tagId);
        noteToTag.setNoteId(nid);
        tagDao.insertNoteToTag(noteToTag);

        return tagId;
    }

}
