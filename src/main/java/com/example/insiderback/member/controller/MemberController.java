package com.example.insiderback.member.controller;

import com.example.insiderback.member.model.MemberVO;
import com.example.insiderback.member.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/member")
public class MemberController {
    @Autowired
    LoginService loginService;

    @PostMapping("/getUser")
    public MemberVO login(String id) {
        return loginService.getUser(id);
    }
    @PostMapping("/setUser")
    public String setUser(MemberVO vo) {
        loginService.setUser(vo);
        return "입력 성공";
    }

    @PostMapping("/delUser")
    public String delUser(String id) {
        loginService.delUser(id);
        return "삭제 성공";
    }
}
