package me.mingshan.hnote.service.dao;

import me.mingshan.hnote.facade.model.Share;

/**
 * @Author: mingshan
 * @Date: Created in 19:17 2018/6/12
 */
public interface ShareDao {

    /**
     *
     * @param code
     * @return
     */
    Share selectByCode(String code);

    /**
     *
     * @param share
     * @return
     */
    Long inset(Share share);

    /**
     *
     * @param share
     * @return
     */
    Integer update(Share share);
}
