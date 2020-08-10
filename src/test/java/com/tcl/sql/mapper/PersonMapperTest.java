package com.tcl.sql.mapper;

import com.tcl.sql.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PersonMapperTest {

    @Autowired
    PersonMapper mapper;

    @Test
    void selectAll() {
        List<Person> people = mapper.selectAll();
        System.out.println(people);

    }
}