package me.mingshan.web.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 10:46 2018/5/1
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderVO implements Serializable {
    private static final long serialVersionUID = 5238578535589478796L;

    private Long id;
    private String label;
    private Integer level;
    private Long pid;
    private Long uid;
    private Boolean deleted;
}
