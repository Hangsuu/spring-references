package com.example.insiderback.common.fileUpload.repository;

import com.example.insiderback.common.fileUpload.model.AttachmentVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachmentMapper {
    public int selectAttachmentNo();
    public void insertAttachment(AttachmentVO attachmentVO);
    public AttachmentVO selectOne(int attachmentNo);
}
