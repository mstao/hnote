package me.mingshan.facade.model;

import me.mingshan.common.model.BaseModel;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 16:26 2018/4/27
 */
public class NoteType extends BaseModel implements Serializable {
    private String name;
    private int isDeleted;
}
