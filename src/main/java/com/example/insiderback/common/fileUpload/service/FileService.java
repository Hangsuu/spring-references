package com.example.insiderback.common.fileUpload.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public boolean upload(MultipartFile attach);
}
