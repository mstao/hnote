package me.mingshan.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 21:21 2018/5/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TokenVO implements Serializable {
    private static final long serialVersionUID = -4520210204422219806L;

    private String userName;
    private String password;
}
