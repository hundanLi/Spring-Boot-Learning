package com.tcl.cache;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.time.Duration;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/6 17:18
 */
@Configuration
public class CacheConfig {
    @Bean
    public CacheManagerCustomizer<RedisCacheManager> cacheManagerCustomizer() {

        return new CacheManagerCustomizer<RedisCacheManager>() {
            @Override
            public void customize(RedisCacheManager cacheManager) {
//                System.out.println("RedisCacheManager# " + cacheManager.getCacheConfigurations());
            }
        };
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer cacheManagerBuilderCustomizer() {
        return new RedisCacheManagerBuilderCustomizer() {
            @Override
            public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
                builder.
                        withCacheConfiguration("cache-1",
                                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(10)));

                builder.
                        withCacheConfiguration("cache-2",
                                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(20)));

                builder.
                        withCacheConfiguration("cache-3",
                                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30)));


            }
        };
    }
}
