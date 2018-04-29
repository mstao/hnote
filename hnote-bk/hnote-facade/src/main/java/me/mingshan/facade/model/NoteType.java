package me.mingshan.facade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.mingshan.common.model.BaseModel;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 16:26 2018/4/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoteType extends BaseModel implements Serializable {
    private static final long serialVersionUID = -6161486655922778147L;

    private String name;
    private int isDeleted;
}
