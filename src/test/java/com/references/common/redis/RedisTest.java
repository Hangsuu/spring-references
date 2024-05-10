package com.references.common.redis;

import com.references.common.redis.entity.MemberRedisEntity;
import com.references.common.redis.repository.RedisRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisRepository redisRepository;

    @Test
    void RedisTest() {
        MemberRedisEntity memberRedisEntity = new MemberRedisEntity("test", "test");
        redisRepository.save(memberRedisEntity);
        MemberRedisEntity resultEntity = redisRepository.findById("test").get();

        Assertions.assertThat(resultEntity.getJwtToken()).isEqualTo("test");
    }

    @AfterEach
    void removeData() {
        redisRepository.deleteById("test");
    }
}
