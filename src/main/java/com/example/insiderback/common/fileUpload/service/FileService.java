package com.example.insiderback.common.fileUpload.service;

import com.example.insiderback.common.fileUpload.model.AttachmentVO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    boolean upload(MultipartFile attach);
    AttachmentVO selectOne(int attachmentNo);
    ResponseEntity<ByteArrayResource> download(int attachmentNo) throws IOException;
}
