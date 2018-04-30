package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.mingshan.common.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author mingshan
 * @Date: Created in 16:18 2018/4/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Note extends BaseModel implements Serializable {
    private static final long serialVersionUID = 8323875163366873309L;

    private String content;
    private String author;
    private String source;
    private long uid;
    private long typeId;
    private long folderId;
    private int isDeleted;

    // 类别
    private NoteType noteType;
    // 文件夹
    private Folder folder;
    // 标签
    private List<Tag> tags;
    // 批量删除
    private List<Long> ids;
}
