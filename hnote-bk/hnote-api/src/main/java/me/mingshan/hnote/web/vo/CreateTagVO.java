package me.mingshan.hnote.web.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 16:04 2018/5/22
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTagVO implements Serializable {
    private static final long serialVersionUID = 5380268643965890644L;

    private Long uid;
    private String name;
    private Boolean deleted;
}
