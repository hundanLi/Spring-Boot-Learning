package com.tcl.nosql.es;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;


/**
 * @author hundanli
 * 版本兼容性问题
 */
@Slf4j
@SpringBootTest
class ElasticSearchRestTemplateTest {

    @Autowired
    ElasticsearchRestTemplate template;

    @Test
    void testTemplate() {
        Assertions.assertTrue(template.createIndex("esusers"));
    }

}