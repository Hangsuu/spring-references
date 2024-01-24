package com.example.insiderback.member.repository;

import com.example.insiderback.common.model.CmmnPagingModel;
import com.example.insiderback.common.model.CmmnPagingVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public CmmnPagingVO selectOne(CmmnPagingModel model);
}
