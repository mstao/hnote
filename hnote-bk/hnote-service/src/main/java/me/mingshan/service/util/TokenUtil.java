package me.mingshan.service.util;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import me.mingshan.facade.model.User;
import me.mingshan.service.config.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: mingshan
 * @Date: Created in 22:16 2018/6/11
 */
public class TokenUtil {
    protected static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    public static User getUserFromToken(String auth) {
        if (auth == null) {
            return null;
        }

        if (!Constants.TOKEN_PREFIX.equals(auth.substring(0, 7))) {
            return null;
        } else {
            String token = extractJwtTokenFromAuthorizationHeader(auth);
            try {
                Claims claims = JWTUtil.parseJWT(token);
                String subject = claims.getSubject();
                User user = JSONObject.parseObject(subject, User.class);
                return user;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("解析JWT token 失败！");
            }
        }
        return null;
    }

    /**
     * 获取真实的toekn，去掉‘Bearer ’
     * @param auth
     * @return
     */
    public static String extractJwtTokenFromAuthorizationHeader(String auth) {
        // Replace "Bearer Token" to "Token" directly
        return auth.replaceFirst("[B|b][E|e][A|a][R|r][E|e][R|r] ", "").replace(" ", "");
    }
}
