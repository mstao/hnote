package me.mingshan.hnote.service.impl;

import me.mingshan.hnote.common.exception.ServerException;
import me.mingshan.hnote.facade.model.Token;
import me.mingshan.hnote.facade.model.User;
import me.mingshan.hnote.facade.service.TokenService;
import me.mingshan.hnote.service.cache.sharded.ShardedRedisHelper;
import me.mingshan.hnote.service.config.Constants;
import me.mingshan.hnote.service.util.JWTUtil;
import me.mingshan.hnote.service.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @Author: mingshan
 * @Date: Created in 22:11 2018/6/11
 */
@Service
public class RedisTokenServiceImpl implements TokenService {
    private static final Logger logger = LoggerFactory.getLogger(RedisTokenServiceImpl.class);

    @Autowired
    private ShardedRedisHelper shardedRedisHelper;

    @Override
    public Token creatToken(long userId) throws ServerException {
        User user = new User();
        user.setId(userId);
        String subject = JWTUtil.generalSubject(user);
        String token = JWTUtil.createJWT(userId, subject, Constants.JWT_TTL);
        Token model = new Token(userId, token);

        logger.info("create token -> userId: {}", userId);
        shardedRedisHelper.setex(String.valueOf(userId), Constants.TOKEN_EXPIRES_HOUR * 3600, token);

        return model;
    }

    @Override
    public void deleteToken(long userId) {
        boolean broken = false;

        logger.info("delete token -> userId: {}", userId);
        shardedRedisHelper.del(String.valueOf(userId));
    }

    @Override
    public boolean checkToken(Token model) {
        Objects.requireNonNull(model);

        logger.info("check token -> userId: {}", model.getUserId());
        String source = shardedRedisHelper.get(String.valueOf(model.getUserId()));
        if (StringUtils.isEmpty(source) || !source.equals(model.getToken())) {
            return false;
        }
        // 延长过期时间
        shardedRedisHelper.expire(String.valueOf(model.getUserId()), Constants.TOKEN_EXPIRES_HOUR * 3600);

        return true;
    }

    @Override
    public Token getToken(String authorization) {
        if (authorization == null || "".equals(authorization)) {
            return null;
        }

        User user = TokenUtil.getUserFromToken(authorization);
        if (user == null) {
            return null;
        }

        long userId = user.getId();

        String token = TokenUtil.extractJwtTokenFromAuthorizationHeader(authorization);
        Token model = new Token(userId, token);
        return model;
    }
}
