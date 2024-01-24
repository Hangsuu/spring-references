package com.example.insiderback.member.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public Integer selectOne();
}
