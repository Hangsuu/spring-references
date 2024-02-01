package com.example.insiderback.member.repository;

import com.example.insiderback.common.model.CmmnPagingModel;
import com.example.insiderback.common.model.CmmnPagingVO;
import com.example.insiderback.member.model.AnnotationTestVO;
import com.example.insiderback.member.model.PaginationTestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public PaginationTestVO selectPage(CmmnPagingModel model);

    public AnnotationTestVO selectAnnotation();
}
