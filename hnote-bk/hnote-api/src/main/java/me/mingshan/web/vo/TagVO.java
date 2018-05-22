package me.mingshan.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mingshan
 * @Date: Created in 19:47 2018/5/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TagVO implements Serializable {
    private static final long serialVersionUID = -3356168576030113008L;

    private Long id;
    private Long uid;
    private String name;
    private Integer isDeleted;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
