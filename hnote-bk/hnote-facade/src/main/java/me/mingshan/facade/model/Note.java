package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.mingshan.common.model.BaseModel;

import java.io.Serializable;

/**
 * @author mingshan
 * @Date: Created in 16:18 2018/4/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Note extends BaseModel implements Serializable {
    private String content;
    private String author;
    private String source;
    private long folderId;
    private int isDeleted;
}
