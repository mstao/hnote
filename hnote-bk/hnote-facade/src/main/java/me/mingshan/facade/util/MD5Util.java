package me.mingshan.facade.util;

import org.springframework.util.DigestUtils;

/**
 * Encryption util with MD5.
 * @Author: mingshan
 * @Date: Created in 23:50 2017/10/19
 */
public class MD5Util {

    /**
     * 使用MD5作信息摘要，并以十六进制表示
     * @param bytes
     * @return 加密后的字符串
     */
    public static String md5(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }

    /**
     * 使用MD5作信息摘要，并以十六进制表示
     * @param source
     * @return 加密后的字符串
     */
    public static String md5(String source) {
        if (source == null || source.length() == 0) {
            return null;
        }
        return MD5Util.md5(source.getBytes());
    }

    /**
     * 使用MD5作信息摘要，包含盐值，并以十六进制表示
     * @param source
     * @param salt
     * @return 加密后的字符串
     */
    public static String md5(String source, String salt) {
        String saltSource = Salter.salt(source, salt);
        String md5 = MD5Util.md5(saltSource);
        return md5;
    }

    /**
     * 盐值处理
     */
    private static class Salter {
        private static final String PREFIX = "lightblog";

        public static String salt(String source, String salt) {
            if (source == null || source.length() == 0) {
                return null;
            }

            StringBuffer sb = new StringBuffer(source);
            if ("".equals(salt) || salt == null) {
                sb.insert(0, PREFIX);
            }

            return sb.append(salt).toString();
        }
    }
}
