package com.example.insiderback.common.fileUpload.service.impl;

import com.example.insiderback.common.fileUpload.model.AttachmentVO;
import com.example.insiderback.common.fileUpload.repository.AttachmentMapper;
import com.example.insiderback.common.fileUpload.service.FileService;
import com.example.insiderback.common.responseModel.model.SingleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    AttachmentMapper attachmentMapper;

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
}
