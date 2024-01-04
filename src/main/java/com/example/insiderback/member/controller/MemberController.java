package com.example.insiderback.member.controller;

import com.example.insiderback.member.model.MemberVO;
import com.example.insiderback.member.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    LoginService loginService;

    @PostMapping("/getUser")
    public MemberVO login(String id) {
        return loginService.getUser(id);
    }
    @PostMapping("/setUser")
    public void setUser(@RequestBody MemberVO vo) {
        log.info("전송 = {}", vo);
        loginService.setUser(vo);
        log.info("입력 성공 = {}", loginService.getUser(vo.getId()));
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
}
