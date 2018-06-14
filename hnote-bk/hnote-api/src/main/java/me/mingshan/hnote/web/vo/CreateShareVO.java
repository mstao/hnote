package me.mingshan.hnote.web.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 9:22 2018/6/13
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateShareVO implements Serializable {
    private static final long serialVersionUID = -8120754220816551802L;

    private Long noteId;
    private String viewPassword;
}
