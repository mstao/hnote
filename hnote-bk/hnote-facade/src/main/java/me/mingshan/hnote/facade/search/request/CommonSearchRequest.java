package me.mingshan.hnote.facade.search.request;

import me.mingshan.hnote.facade.search.enums.EsQueryTypeEnum;

import java.util.List;

/**
 * 封装的搜索请求
 */
public class CommonSearchRequest {

    private List<SearchWord> searchWords;

    /**
     * 添加搜索关键字
     * @param keyword
     * @param esQueryType
     */
    private void addSearchWord(String keyword, EsQueryTypeEnum esQueryType){

        SearchWord searchWord = new SearchWord();

    }
}
