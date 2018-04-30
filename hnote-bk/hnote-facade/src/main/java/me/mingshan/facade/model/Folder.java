package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.mingshan.common.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: mingshan
 * @Date: Created in 21:50 2018/4/29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Folder extends BaseModel implements Serializable {
    private static final long serialVersionUID = -2582952414547485569L;

    private String name;
    private int level;
    private long pid;
    private long uid;
    private int isDeleted;

    // 批量删除
    private List<Long> ids;
}
