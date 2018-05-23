package me.mingshan.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 16:04 2018/5/22
 */
@Data
public class CreateTagVO implements Serializable {
    private static final long serialVersionUID = 5380268643965890644L;

    private Long uid;
    private String name;
    private Boolean deleted;
}
