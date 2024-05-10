package com.references.common.redis.repository;

import com.references.common.redis.entity.MemberRedisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<MemberRedisEntity, String> {
}
