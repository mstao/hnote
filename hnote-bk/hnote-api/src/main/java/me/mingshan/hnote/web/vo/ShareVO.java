package me.mingshan.hnote.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.mingshan.hnote.facade.model.Note;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: mingshan
 * @Date: Created in 9:59 2018/6/13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareVO {
    private static final long serialVersionUID = 4265612278662010439L;

    private Long id;
    private Long noteId;
    private String code;
    private Integer likeNum;
    private Integer commentNum;
    private Integer reportNum;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date gmtCreate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date gmtModified;

    private Note note;
}
