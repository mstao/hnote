package me.mingshan.web.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: mingshan
 * @Date: Created in 21:53 2018/5/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateNoteFolderVO implements Serializable {
    private static final long serialVersionUID = -3300319994406997281L;

    private Integer folderId;
    private Long id;
}
