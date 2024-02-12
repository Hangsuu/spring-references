package com.example.insiderback.common.fileUpload.service;

import com.example.insiderback.common.fileUpload.model.AttachmentVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public boolean upload(MultipartFile attach);
    public AttachmentVO selectOne(int attachmentNo);
}
