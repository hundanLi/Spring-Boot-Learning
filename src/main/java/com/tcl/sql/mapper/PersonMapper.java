package com.tcl.sql.mapper;

import com.tcl.sql.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author li
 * @version 1.0
 * @date 2020/8/5 18:41
 */
@Mapper
public interface PersonMapper {

    List<Person> selectAll();
}
