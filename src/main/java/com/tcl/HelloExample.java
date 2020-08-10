package com.tcl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

/**
 * @author hundanli
 */
@RestController
@SpringBootApplication
@MapperScan(basePackages = "com.tcl.sql.mapper")
@EnableCaching
public class HelloExample {

    @RequestMapping("/home")
    String home() {
        return "Hello TCL!";
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloExample.class, args);
    }

}