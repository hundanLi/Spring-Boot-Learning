package com.tcl.nosql.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    void testOperations() {
        log.info("=========Redis Repository 测试========");
        User user = new User();
        user.setId(100);
        user.setUsername("hundanli");
        user.setPassword("password");
        repository.save(user);
        User get = repository.findById(user.getId()).orElse(null);
        System.out.println(get);
        Assertions.assertEquals(user, get);

        repository.delete(user);
        Assertions.assertEquals(0, repository.count());

    }
}