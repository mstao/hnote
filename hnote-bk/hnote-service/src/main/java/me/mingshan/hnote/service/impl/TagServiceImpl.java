package me.mingshan.hnote.service.impl;

import me.mingshan.hnote.facade.model.NoteToTag;
import me.mingshan.hnote.facade.model.Tag;
import me.mingshan.hnote.facade.service.TagService;
import me.mingshan.hnote.service.dao.TagDao;
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
    public List<Tag> findByUid(Long uid) {
        return tagDao.selectByUid(uid);
    }

    @Override
    public Long insert(Tag tag, Long nid) {
        Tag curr = tagDao.selectByNameUid(tag.getName(), tag.getUid());
        long tagId = 0L;
        if (curr == null) {
            tagDao.insert(tag);
            tagId = tag.getId();
        } else {
            tagId = curr.getId();
        }

        NoteToTag noteToTag = new NoteToTag();
        noteToTag.setTagId(tagId);
        noteToTag.setNoteId(nid);
        tagDao.insertNoteToTag(noteToTag);

        return tagId;
    }

}
