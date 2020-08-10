package com.tcl.nosql.redis;

import org.springframework.data.repository.CrudRepository;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/6 11:31
 */
public interface UserRepository extends CrudRepository<User, Integer> {

}
