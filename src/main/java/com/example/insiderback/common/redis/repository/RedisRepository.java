package com.example.insiderback.common.redis.repository;

import com.example.insiderback.common.redis.entity.MemberRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<MemberRedisEntity, String> {
}
