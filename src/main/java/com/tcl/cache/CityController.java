package com.tcl.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/6 17:47
 */
@Slf4j
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    CityService cityService;

    @GetMapping("/list")
    public List<City> getCityList() {
        return cityService.getCityList();
    }

    @GetMapping("/evict")
    public void deleteCity() {
        cityService.deleteCity();
    }
}
