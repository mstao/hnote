package me.mingshan.hnote.service.impl;

import me.mingshan.hnote.common.exception.ServerException;
import me.mingshan.hnote.facade.model.Share;
import me.mingshan.hnote.facade.service.ShareService;
import me.mingshan.hnote.service.dao.ShareDao;
import me.mingshan.hnote.service.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;

/**
 * @Author: mingshan
 * @Date: Created in 8:29 2018/6/13
 */
@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private ShareDao shareDao;

    @Override
    public Share findByCode(String code) {
        Objects.requireNonNull(code);

        return shareDao.selectByCode(code);
    }

    @Override
    public String insert(Share share) {
        Objects.requireNonNull(share);
        Share share1 = shareDao.selectByNoteId(share.getNoteId());
        String code = null;
        if (share1 == null) {
            code = UUID.randomUUID().toString();
            share.setCode(code);

            if (!StringUtils.isEmpty(share.getViewPassword())) {
                String password = MD5Util.md5(share.getViewPassword(), code);
                share.setViewPassword(password);
            }

            shareDao.insert(share);
        } else {
            code = share1.getCode();
        }

        return code;
    }

    @Override
    public void update(Share share) throws ServerException {
        Objects.requireNonNull(share);

        int version = shareDao.selectVersion(share.getId());
        share.setVersion(version);
        int result = shareDao.update(share);
        if (result == 0) {
            throw new ServerException();
        }
    }
}
