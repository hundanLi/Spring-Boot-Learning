package com.tcl.nosql.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/6 14:07
 *
 * 版本兼容性难题  Spring Data 3.2 ==> ES 6.5
 */
public interface EsUserRepository extends ElasticsearchCrudRepository<EsUser, Integer> {

}
