package com.demo;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class EsUtils {
    private static RestHighLevelClient restHighLevelClient;
    static{
        RestClientBuilder builder = RestClient.builder(new HttpHost("192.168.2.243", 9200));
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            return requestConfigBuilder.setSocketTimeout(10);
        });
        restHighLevelClient = new RestHighLevelClient(builder);
    }

    public static RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }
}
