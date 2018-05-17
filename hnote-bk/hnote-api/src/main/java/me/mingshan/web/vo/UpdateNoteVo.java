package me.mingshan.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mingshan
 * @Date: Created in 15:50 2018/5/17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateNoteVo implements Serializable {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String source;
    private Long folderId;
}
