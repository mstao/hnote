package me.mingshan.facade.service;

import me.mingshan.common.exception.ServerException;
import me.mingshan.facade.model.Token;

/**
 * @Author: mingshan
 * @Date: Created in 22:10 2018/6/11
 */
public interface TokenService {
    /**
     * Creates the token of authorization.
     * @param userId
     * @return The model of Token.
     */
    Token creatToken(long userId) throws ServerException;

    /**
     * Deteles the token of authorization.
     * @param userId
     */
    void deleteToken(long userId);

    /**
     * Checks out the token that is from Redis.
     * @param token
     * @return
     */
    boolean checkToken(Token token);

    /**
     * Gets the model of Token from authorization string.
     * @param authorization
     * @return The model of Token.
     */
    Token getToken(String authorization);
}
