package me.mingshan.facade.authorization.check;

import me.mingshan.facade.config.Constants;
import me.mingshan.facade.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author: mingshan
 * @Date: Created in 12:41 2017/12/16
 */
public class RequestCheck {
    protected static final Logger logger = LoggerFactory.getLogger(RequestCheck.class);

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
