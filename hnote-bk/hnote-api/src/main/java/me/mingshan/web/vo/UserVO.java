package me.mingshan.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 22:11 2018/5/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserVO implements Serializable {
    private static final long serialVersionUID = -5749205658779456295L;

    private Long id;
    private String name;
    private Long age;
    private String password;
    private String email;
    private String salt;
    private Integer status;
    private String avatarUrl;
}
