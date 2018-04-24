package me.mingshan.service.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 进行登录认证实体类
 * @author mingshan
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TokenModel implements Serializable {
    private static final long serialVersionUID = -4743364335923069447L;

    private long userId;
    private String token;

    public TokenModel() { }

    public TokenModel(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
