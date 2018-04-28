package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.mingshan.common.model.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mingshan
 * @Date: Created in 21:40 2017/10/10
 * @Modified By:
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseModel implements Serializable {
    private static final long serialVersionUID = -875442365286566669L;

    private String name;
    private int age;
    private String password;
    private String email;
    private String salt;
    private int status;
    private String avatarUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
}
