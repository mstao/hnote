package me.mingshan.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 10:46 2018/5/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FolderVO implements Serializable {
    private static final long serialVersionUID = 5238578535589478796L;

    private Long id;
    private String name;
    private Integer level;
    private Long pid;
    private Long uid;
    private Integer isDeleted;
}
