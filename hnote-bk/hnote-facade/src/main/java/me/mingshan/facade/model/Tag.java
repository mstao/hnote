package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.mingshan.common.model.BaseModel;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 16:40 2018/4/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Tag extends BaseModel implements Serializable {
    private static final long serialVersionUID = -4305093510865788901L;

    private String name;
    private int isDeleted;
}
