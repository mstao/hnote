package me.mingshan.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: mingshan
 * @Date: Created in 16:11 2018/4/27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseModel {
    protected long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date gmtCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date gmtModified;
}
