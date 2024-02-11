package com.example.insiderback.member.controller;

import com.example.insiderback.common.fileUpload.model.AttachmentVO;
import com.example.insiderback.common.fileUpload.repository.AttachmentMapper;
import com.example.insiderback.common.jwt.model.JwtTokenVO;
import com.example.insiderback.common.redis.entity.MemberRedisEntity;
import com.example.insiderback.common.redis.repository.RedisRepository;
import com.example.insiderback.common.responseModel.model.SingleResponse;
import com.example.insiderback.member.model.MemberVO;
import com.example.insiderback.member.service.JwtLoginService;
import com.example.insiderback.member.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/rest/member")
public class MemberController {
    @Autowired
    LoginService loginService;
    @Autowired
    JwtLoginService jwtLoginService;
    @Autowired
    AttachmentMapper attachmentMapper;

    @PostMapping("/getUser")
    public MemberVO login(String id) {
        return loginService.getUser(id);
    }
    @PostMapping("/setUser")
    public SingleResponse setUser(@RequestBody MemberVO vo) {
        log.info("전송 = {}", vo);
        loginService.setUser(vo);
        log.info("입력 성공 = {}", loginService.getUser(vo.getId()));
        return new SingleResponse(vo);
    }

    @PostMapping("/delUser")
    public String delUser(String id) {
        loginService.delUser(id);
        return "삭제 성공";
    }

    @GetMapping("/test")
    public String test() {
        return "성공";
    }

    @PostMapping("/login")
    public SingleResponse signIn(@RequestBody MemberVO memberVO) {
        JwtTokenVO jwtToken = jwtLoginService.login(memberVO);
        return new SingleResponse(jwtToken);
    }

    @PostMapping("/requestToken")
    public SingleResponse requestToken(HttpServletRequest request) {
        JwtTokenVO jwtTokenVO = jwtLoginService.requestToken(request.getHeader("Authorization").replaceAll("Bearer", ""));
        return new SingleResponse(jwtTokenVO);
    }

    @PostMapping("/logout")
    public SingleResponse logout(@RequestBody MemberVO memberVO) {
        jwtLoginService.logout(memberVO);
        return new SingleResponse(true);
    }

    @PostMapping("/upload")
    public SingleResponse upload(@RequestParam MultipartFile attach) throws IllegalStateException, IOException {
        if(!attach.isEmpty()) {	//파일이 있을 경우
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
            return new SingleResponse(true);
        }
        return new SingleResponse(false);
    }
}
