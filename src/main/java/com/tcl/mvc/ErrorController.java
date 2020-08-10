package com.tcl.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/5 14:30
 */
@RestController
public class ErrorController {
    @GetMapping("/zero")
    public String error() {
        int i = 10 / 0;
        return "zero";
    }
}
