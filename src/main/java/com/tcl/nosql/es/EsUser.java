package com.tcl.nosql.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.redis.core.RedisHash;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/6 11:29
 */
@Document(indexName = "esusers", type = "info")
@Data
public class EsUser {
    @Id
    private Integer id;
    private String username;
    private String password;
}
