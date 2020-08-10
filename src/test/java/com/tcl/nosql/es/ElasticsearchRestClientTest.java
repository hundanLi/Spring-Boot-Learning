package com.tcl.nosql.es;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class ElasticsearchRestClientTest {

    @Autowired
    RestClient restClient;

    @Autowired
    RestHighLevelClient highLevelClient;

    @Test
    void testLowLevel() throws IOException {
        Request request = new Request("GET", "/");
        Response response = restClient.performRequest(request);
        System.out.println(response.getEntity());
    }

    @Test
    void testHighLevel() throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", "hundanli");
        jsonMap.put("age", 20);
        jsonMap.put("email", "hundanli@tcl.com");
        IndexRequest indexRequest = new IndexRequest("users", "info", "1").source(jsonMap);
        IndexResponse response = highLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response.getResult());

        GetRequest getRequest = new GetRequest("users", "info", "1");
        GetResponse getResponse = highLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());

        DeleteRequest deleteRequest = new DeleteRequest("users", "info", "1");
        DeleteResponse deleteResponse = highLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.getResult());
    }
}