package com.tcl.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MyConfigPropertiesTest {

    @Autowired
    MyConfigProperties myConfigProperties;

    @Test
    void getName() {
//        Assert.assertEquals(propertyValue.getName(), "myproject");
        System.out.println(myConfigProperties);
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
    }
}