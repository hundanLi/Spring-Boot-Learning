package com.tcl.nosql.redis;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.redis.core.RedisHash;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/6 11:29
 */
@RedisHash("User")
@Data
public class User {
    @Id
    private Integer id;
    private String username;
    private String password;
}
