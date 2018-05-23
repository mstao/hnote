package me.mingshan.service.impl;

import me.mingshan.facade.model.NoteToTag;
import me.mingshan.facade.model.Tag;
import me.mingshan.facade.service.TagService;
import me.mingshan.service.dao.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 17:17 2018/4/27
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;

    @Override
    public Tag findById(Long id) {
        return tagDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Tag> findAllByNid(Long nid) {
        return tagDao.selectAllByNid(nid);
    }

    @Override
    public Long insert(Tag tag, Long nid) {
        tagDao.insert(tag);
        long tagId = tag.getId();
        NoteToTag noteToTag = new NoteToTag();
        noteToTag.setTagId(tagId);
        noteToTag.setNoteId(nid);
        tagDao.insertNoteToTag(noteToTag);

        return tagId;
    }

}
