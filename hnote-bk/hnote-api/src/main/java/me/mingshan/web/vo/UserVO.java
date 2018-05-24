package me.mingshan.web.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 22:11 2018/5/2
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
