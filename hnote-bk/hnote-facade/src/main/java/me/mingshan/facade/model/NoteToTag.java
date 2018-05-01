package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mingshan
 * @Date: Created in 21:28 2018/5/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoteToTag implements Serializable {
    private static final long serialVersionUID = -6653618463628769977L;

    private Long id;
    private Long noteId;
    private Long tagId;
    private Integer isDeleted;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
