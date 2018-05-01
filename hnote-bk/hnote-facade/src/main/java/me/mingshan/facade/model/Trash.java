package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mingshan
 * @Date: Created in 16:41 2018/4/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Trash implements Serializable {
    private static final long serialVersionUID = -1715340505113819658L;

    private Long id;
    private Long noteId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
