package com.references.member.repository;

import com.references.common.model.CmmnPagingModel;
import com.references.common.model.CmmnPagingVO;
import com.references.member.model.AnnotationTestVO;
import com.references.member.model.EncryptTestVO;
import com.references.member.model.PaginationTestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public PaginationTestVO selectPage(CmmnPagingModel model);

    public AnnotationTestVO selectAnnotation();

    public EncryptTestVO selectEncrypt();
}
