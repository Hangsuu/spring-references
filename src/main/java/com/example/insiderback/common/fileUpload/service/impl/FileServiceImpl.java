package com.example.insiderback.common.fileUpload.service.impl;

import com.example.insiderback.common.fileUpload.config.FileUploadProperties;
import com.example.insiderback.common.fileUpload.model.AttachmentVO;
import com.example.insiderback.common.fileUpload.repository.AttachmentMapper;
import com.example.insiderback.common.fileUpload.service.FileService;
import com.example.insiderback.common.responseModel.model.SingleResponse;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    AttachmentMapper attachmentMapper;
    @Autowired
    private FileUploadProperties fileUploadProperties;

    private File dir;

    @PostConstruct
    public void init() {
        dir = new File(fileUploadProperties.getPath());
        dir.mkdirs();
    }

    @Override
    public boolean upload(MultipartFile attach) {
        if(attach.isEmpty()) {
            return false;
        }
        try {
            //번호생성
            int attachmentNo = attachmentMapper.selectAttachmentNo();
            File dir = new File("D:/upload/insider-back");	//파일 저장 위치
            dir.mkdirs(); 		//폴더 생성 명령
            File target = new File(dir, String.valueOf(attachmentNo));		//파일명은 int로 안들어감
            attach.transferTo(target);
            //DB 저장
            AttachmentVO attachmentVO = AttachmentVO.builder()
                    .attachmentNo(attachmentNo)
                    .attachmentName(attach.getName())
                    .attachmentType(attach.getContentType())
                    .attachmentRoute("D:/upload/insider-back/" + attachmentNo)
                    .attachmentSize(attach.getSize())
                    .build();
            attachmentMapper.insertAttachment(attachmentVO);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AttachmentVO selectOne(int attachmentNo) {
        return attachmentMapper.selectOne(attachmentNo);
    }

    @Override
    public ResponseEntity<ByteArrayResource> download(int attachmentNo) throws IOException {
        //DB 조회
        AttachmentVO attachmentVO = attachmentMapper.selectOne(attachmentNo);
        if(attachmentVO == null) {//없으면 404
            return ResponseEntity.notFound().build();
        }

        //파일 찾기
        File target = new File(dir, String.valueOf(attachmentNo));

        //보낼 데이터 생성
        byte[] data = FileUtils.readFileToByteArray(target);
        ByteArrayResource resource = new ByteArrayResource(data);

//		제공되는 모든 상수와 명령을 동원해서 최대한 오류 없이 편하게 작성
        return ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_TYPE,
//							MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(attachmentVO.getAttachmentSize())
                .header(HttpHeaders.CONTENT_ENCODING,
                        StandardCharsets.UTF_8.name())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(
                                        attachmentVO.getAttachmentName(),
                                        StandardCharsets.UTF_8
                                ).build().toString()
                )
                .body(resource);
    }
}
