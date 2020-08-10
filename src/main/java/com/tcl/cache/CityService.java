package com.tcl.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/6 19:42
 */
@Service
@Slf4j
public class CityService {
    @Cacheable(cacheNames = "cache-1", keyGenerator = "cityListKeyGenerator")
    public List<City> getCityList() {
        log.info("+++++执行查询++++++++");
        List<City> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            City city = new City();
            city.setId(1);
            city.setCityName("city-" + (i + 1));
            city.setProvince("广东");
            list.add(city);
        }
        return list;
    }

    @CacheEvict(cacheNames = "cache-1", keyGenerator = "cityListKeyGenerator")
    public void deleteCity() {

    }
}
