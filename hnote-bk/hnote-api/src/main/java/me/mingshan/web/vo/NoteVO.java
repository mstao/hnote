package me.mingshan.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mingshan
 * @Date: Created in 15:51 2018/5/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoteVO implements Serializable {
    private static final long serialVersionUID = -532603166481657790L;

    private Long id;
    private String content;
    private String author;
    private String source;
    private Long uid;
    private Long typeId;
    private Long folderId;
    private Integer isDeleted;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
