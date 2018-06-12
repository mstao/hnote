package me.mingshan.service.impl;

import me.mingshan.common.exception.ServerException;
import me.mingshan.facade.model.Token;
import me.mingshan.facade.model.User;
import me.mingshan.facade.service.TokenService;
import me.mingshan.service.config.Constants;
import me.mingshan.service.util.JWTUtil;
import me.mingshan.service.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: mingshan
 * @Date: Created in 22:11 2018/6/11
 */
@Service
public class RedisTokenServiceImpl implements TokenService {
    private static final Logger logger = LoggerFactory.getLogger(RedisTokenServiceImpl.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Override
    public Token creatToken(long userId) throws ServerException {
        User user = new User();
        user.setId(userId);
        String subject = JWTUtil.generalSubject(user);
        String token = JWTUtil.createJWT(userId, subject, Constants.JWT_TTL);
        Token model = new Token(userId, token);

        // jedis实例是否已不能使用
        boolean broken = false;
        ShardedJedis shardedJedis = null;
        try {
            logger.info("create token -> userId: {}", userId);
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.set(String.valueOf(userId), token);
            shardedJedis.expire(String.valueOf(userId), Constants.TOKEN_EXPIRES_HOUR);
        } catch (Exception e) {
            broken = true;
            throw new ServerException(null, HttpStatus.BAD_REQUEST);
        } finally {
            if (broken) {
                // 无法正常使用，将jedis实例返回到池中,标识该jedis实例不能使用
                shardedJedisPool.returnBrokenResource(shardedJedis);
            } else {
                // 正常使用完后，将jedis实例返回到池中
                shardedJedisPool.returnResource(shardedJedis);
            }
        }

        return model;
    }

    @Override
    public void deleteToken(long userId) {
        boolean broken = false;
        ShardedJedis shardedJedis = null;
        try {
            logger.info("delete token -> userId: {}", userId);
            shardedJedis = shardedJedisPool.getResource();
            shardedJedis.del(String.valueOf(userId));
        } catch (Exception e) {
            broken=true;
        } finally {
            if (broken) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            } else {
                shardedJedisPool.returnResource(shardedJedis);
            }
        }
    }

    @Override
    public boolean checkToken(Token model) {
        boolean isValid = false;

        Objects.requireNonNull(model);

        boolean broken = false;
        ShardedJedis shardedJedis = null;
        try {
            logger.info("check token -> userId: {}", model.getUserId());
            shardedJedis = shardedJedisPool.getResource();
            String source = shardedJedis.get(String.valueOf(model.getUserId()));
            if (StringUtils.isEmpty(source) || !source.equals(model.getToken())) {
                isValid = false;
            } else {
                isValid = true;
                shardedJedis.expire(String.valueOf(model.getUserId()), Constants.TOKEN_EXPIRES_HOUR);
            }
        } catch (Exception e) {
            broken=true;
            isValid = false;
        } finally {
            if (broken) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            } else {
                shardedJedisPool.returnResource(shardedJedis);
            }
        }

        return isValid;
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
