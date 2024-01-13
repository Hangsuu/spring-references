package com.example.insiderback.common.redis.repository;

import com.example.insiderback.common.jwt.model.JwtTokenVO;
import com.example.insiderback.common.redis.entity.MemberEntity;
import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<MemberEntity, String> {
}
