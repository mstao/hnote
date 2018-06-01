package me.mingshan.facade.search.request;

import lombok.Data;
import me.mingshan.facade.search.enums.EsQueryTypeEnum;

import java.io.Serializable;

/**
 * 搜索词
 */
@Data
public class SearchWord implements Serializable {

    private static final long serialVersionUID = 1347538182313047166L;

    /**
     * 搜索关键字
     */
    private String keyword;

    /**
     * 查询类型
     */
    private EsQueryTypeEnum queryType;

}
