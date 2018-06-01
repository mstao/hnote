package me.mingshan.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 19:18 2018/6/1
 */
@Data
public class CreateUserVO implements Serializable {
    private static final long serialVersionUID = -1176338448890533028L;

    private String name;
    private Long age;
    private String password;
    private String email;
}
