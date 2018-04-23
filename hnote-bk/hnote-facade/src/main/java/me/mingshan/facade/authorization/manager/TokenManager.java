package me.mingshan.facade.authorization.manager;


import me.mingshan.facade.model.TokenModel;

/**
 * @Description: The manager of token with Redis.
 * @Author: Minsghan
 * @Date: Created in 23:22 2017/10/13
 * @Modified By:
 */
public interface TokenManager {

    /**
     * Creates the token of authorization.
     * @param userId
     * @return The model of Token.
     */
    TokenModel creatToken(long userId);

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
    boolean checkToken(TokenModel token);

    /**
     * Gets the model of Token from authorization string.
     * @param authorization
     * @return The model of Token.
     */
    TokenModel getToken(String authorization);
}
