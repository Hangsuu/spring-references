package com.example.insiderback.member.controller;

import com.example.insiderback.common.fileUpload.config.FileUploadProperties;
import com.example.insiderback.common.fileUpload.model.AttachmentVO;
import com.example.insiderback.common.fileUpload.service.FileService;
import com.example.insiderback.common.jwt.model.JwtTokenVO;
import com.example.insiderback.common.responseModel.model.SingleResponse;
import com.example.insiderback.member.model.MemberVO;
import com.example.insiderback.member.service.JwtLoginService;
import com.example.insiderback.member.service.LoginService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/rest/member")
public class MemberController {
    @Autowired
    LoginService loginService;
    @Autowired
    JwtLoginService jwtLoginService;
    @Autowired
    FileService fileService;

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
    public SingleResponse upload(@RequestBody MultipartFile attach) {
        return new SingleResponse(fileService.upload(attach));
    }

    //다운로드
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> download(@RequestParam int attachmentNo) throws IOException {
        return fileService.download(attachmentNo);
    }
}
