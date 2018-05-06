package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author mingshan
 * @Date: Created in 16:18 2018/4/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Note implements Serializable {
    private static final long serialVersionUID = 8323875163366873309L;

    private Long id;
    private String title;
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

    /**
     *  类别
     */
    private NoteType noteType;

    /**
     * 文件夹
     */
    private Folder folder;

    /**
     * 标签
     */
    private List<Tag> tags;

    /**
     * 批量删除
     */
    private List<Long> ids;
}
