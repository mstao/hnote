package me.mingshan.search.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.mingshan.facade.service.SearchClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>用户搜索客户端</p>
 *
 * @author: XianGuo
 * @date: 2018年01月31日
 */
@Service("searchClient")
public class EsSearchClient implements SearchClient {
    private static final Logger logger = LoggerFactory.getLogger(EsSearchClient.class);

    @Autowired
    private RestHighLevelClient rhlClient;

    @Override
    public List<JSONObject> search(String token) {
        try {
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("hnote");
            searchRequest.types("doc");
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("title", token));
            searchRequest.source(searchSourceBuilder);
            SearchResponse response = rhlClient.search(searchRequest);
            logger.info("search result: {} ", response);
            if (response.getHits() == null) {
                return null;
            }
            List<JSONObject> list = new ArrayList<>();
            response.getHits().forEach(item -> list.add(JSON.parseObject(item.getSourceAsString())));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> List<T> search(String token, Class<T> tClass) {
        List<JSONObject> searchResponse = this.search(token);
        if (searchResponse == null) {
            return null;
        }
        List<T> list = new ArrayList<>(searchResponse.size());
        searchResponse.forEach(item -> list.add(JSON.parseObject(JSON.toJSONString(item), tClass)));
        return list;
    }
}
