package me.mingshan.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/**
 * @Author: mingshan
 * @Date: Created in 10:45 2018/5/31
 */
@Configuration
@ComponentScan(basePackageClasses=ESClientSpringFactory.class)
public class ESConfig {

    @Value("${httpHost.host}")
    private String host;

    @Value("${httpHost.port}")
    private int port;

    @Value("${httpHost.schema}")
    private String schema;

    @Value("${esclient.connectNum}")
    private Integer connectNum;

    @Value("${esclient.connectPerRoute}")
    private Integer connectPerRoute;

    @Bean
    public HttpHost httpHost(){
        return new HttpHost(host,port,schema);
    }

    @Bean(initMethod="init",destroyMethod="close")
    public ESClientSpringFactory getFactory(){
        return ESClientSpringFactory.
                build(httpHost(), connectNum, connectPerRoute);
    }

    @Bean
    @Scope("singleton")
    public RestClient getRestClient(){
        return getFactory().getClient();
    }

    @Bean
    @Scope("singleton")
    public RestHighLevelClient getRHLClient(){
        return getFactory().getRhlClient();
    }
}