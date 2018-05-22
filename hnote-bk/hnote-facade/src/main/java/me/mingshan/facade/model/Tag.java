package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mingshan
 * @Date: Created in 16:40 2018/4/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Tag implements Serializable {
    private static final long serialVersionUID = -4305093510865788901L;

    private Long id;
    private Long uid;
    private String name;
    private Integer isDeleted;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
