package com.tcl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/4 15:16
 */
@Component
@ConfigurationProperties(prefix = "my.config")
@Data
public class MyConfigProperties {

    private String name;
    private Date birth;
    private Map<String, String> map;
    private List<String> list;

}
