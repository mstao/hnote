package me.mingshan.facade.search.enums;

/**
 * 查询类型
 */
public enum EsQueryTypeEnum {
    MATCH(1, "match", "全文匹配"), MATCH_PHRASE(2, "match_phrase", "紧邻搜索"), TERM(3, "term", "精确匹配");

    private Integer code;
    private String englishCode;
    private String desc;

    EsQueryTypeEnum(Integer code, String englishCode, String desc) {
        this.code = code;
        this.desc = desc;
        this.englishCode = englishCode;
    }
}
