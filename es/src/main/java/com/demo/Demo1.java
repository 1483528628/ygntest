package com.demo;


import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Arrays;
import java.util.*;

@Slf4j
public class Demo1 {
    public static void main(String[] args) {
        HttpHost[] hosts = Arrays.stream("")
                .map(Demo1::makeHttpHost)
                .filter(Objects::nonNull)
                .toArray(HttpHost[]::new);
        log.warn("hosts:{}", Arrays.asList(hosts));

        //配置权限验证
        //credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClientBuilder restClientBuilder = RestClient.builder(hosts).setHttpClientConfigCallback(httpAsyncClientBuilder ->
                httpAsyncClientBuilder.setDefaultCredentialsProvider(new BasicCredentialsProvider()));
        restHighLevelClient = new RestHighLevelClient(restClientBuilder);
    }

    private HttpHost makeHttpHost(String s) {
        assert StringUtils.isNotEmpty(s);
        String[] address = s.split(":");
        if (address.length == 2) {
            String ip = address[0];
            int port = Integer.parseInt(address[1]);
            return new HttpHost(ip, port, "http");
        } else {
            return null;
        }
    }
}
