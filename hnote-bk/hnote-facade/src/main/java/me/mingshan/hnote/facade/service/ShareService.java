package me.mingshan.hnote.facade.service;

import me.mingshan.hnote.common.exception.ServerException;
import me.mingshan.hnote.facade.model.Share;

/**
 * @Author: mingshan
 * @Date: Created in 19:17 2018/6/12
 */
public interface ShareService {

    /**
     * Get by code.
     *
     * @param code
     * @return
     */
    Share findByCode(String code);

    /**
     * Insert share.
     *
     * @param share
     * @return
     */
    String insert(Share share);

    /**
     *  Update share.
     *
     * @param share
     */
    void update(Share share) throws ServerException;
}
