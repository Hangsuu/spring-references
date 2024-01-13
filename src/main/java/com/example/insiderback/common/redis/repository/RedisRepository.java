package com.example.insiderback.common.redis.repository;

import com.example.insiderback.common.redis.entity.MemberRedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<MemberRedisEntity, String> {
}
