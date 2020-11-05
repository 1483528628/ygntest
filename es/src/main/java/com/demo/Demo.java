package com.demo;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Demo {
    private RestHighLevelClient restHighLevelClient = EsUtils.getRestHighLevelClient();
    /**
     * 创建索引
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("properties")
                .startObject()
                .field("name").startObject().field("index", "true").field("type", "keyword").endObject()
                .field("age").startObject().field("index", "true").field("type", "integer").endObject()
                .field("money").startObject().field("index", "true").field("type", "double").endObject()
                .endObject()
                .endObject();
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("abc");
        createIndexRequest.mapping(builder);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        if (createIndexResponse.isAcknowledged()) {
            log.error("创建成功");
        } else {
            log.error("创建失败");
        }
    }

    /**
     * 判断索引是否存在并删除索引
     * @throws IOException
     */
    @Test
    public void deleteIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest("abc");
        getIndexRequest.humanReadable(true);
        if (restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT)) {
            log.error("索引存在");
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("abc");
            deleteIndexRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            if (delete.isAcknowledged()) {
                log.error("删除成功");
            } else {
                log.error("删除失败");
            }
        } else {
            log.error("索引不存在，删除失败");
        }
    }

    /**
     * 插入数据
     * @throws IOException
     */
    @Test
    public void insertData() throws IOException {
        IndexRequest indexRequest = new IndexRequest("abc");
        String jsonStr = "{\"name\":\"dan\",\"age\":24,\"money\":1.8}";
        indexRequest.source(jsonStr, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        if (indexResponse != null) {
            String id = indexResponse.getId();
            String index = indexResponse.getIndex();
            long version = indexResponse.getVersion();
            log.info("index:{},id:{}", index, id);
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                System.out.println("新增文档成功!" + index + "-" + id + "-" + version);
                log.error("插入成功");
            } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                System.out.println("修改文档成功!");
                log.error("插入失败");
            }
            // 分片处理信息
            ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                System.out.println("分片处理信息.....");
            }
            // 如果有分片副本失败，可以获得失败原因信息
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                    String reason = failure.reason();
                    System.out.println("副本失败原因：" + reason);
                }
            }
        }
    }

    /**
     * 查询数据
     * @throws IOException
     */
    @Test
    public void query() throws IOException {
        SearchRequest searchRequest = new SearchRequest("abc");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("money").from(1.5).to(2);
        //精准查询
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name.keyword", "abc");
        //前缀查询
//        PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("name.keyword", "a");
        //通配符查询
//        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("name.keyword", "*b*");
        //模糊查询
//        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("name", "三");
        //按照年龄排序
//        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("age");
        //从小到大排序
//        fieldSortBuilder.sortMode(SortMode.MIN);

        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            for (Map.Entry<String, Object> stringObjectEntry : sourceAsMap.entrySet()) {
                System.out.println(stringObjectEntry.getKey()+"--------"+stringObjectEntry.getValue());
            }
        }
    }

    /**
     * 删除数据
     * @throws IOException
     */
    @Test
    public void deleteData() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("abc");
        deleteRequest.id("fEIiiHUB275E_TMYnt0R");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            log.error("删除成功");
        } else {
            log.error("删除失败");
        }
    }

    /**
     * 更新数据
     * @throws IOException
     */
    @Test
    public void updateData() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("abc", "fUIiiHUB275E_TMY8t3h");
        Map<String, Object> map = new HashMap<>();
        map.put("money", 20);
        updateRequest.doc(map);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            log.error("更新成功");
        } else {
            log.error("更新失败");
        }
    }
}
