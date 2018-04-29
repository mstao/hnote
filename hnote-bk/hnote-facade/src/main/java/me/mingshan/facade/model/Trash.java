package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.mingshan.common.model.BaseModel;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 16:41 2018/4/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Trash extends BaseModel implements Serializable {
    private static final long serialVersionUID = -1715340505113819658L;

    private long noteId;
}
