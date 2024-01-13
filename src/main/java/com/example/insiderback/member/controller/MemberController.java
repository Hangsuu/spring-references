package com.example.insiderback.member.controller;

import com.example.insiderback.common.jwt.model.JwtTokenVO;
import com.example.insiderback.common.redis.entity.MemberRedisEntity;
import com.example.insiderback.common.redis.repository.RedisRepository;
import com.example.insiderback.common.responseModel.model.SingleResponse;
import com.example.insiderback.member.model.MemberVO;
import com.example.insiderback.member.service.JwtLoginService;
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
    @Autowired
    JwtLoginService jwtLoginService;
    @Autowired
    RedisRepository redisRepository;

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
        log.info("request username = {}, password = {}", memberVO.getId(), memberVO.getPassword());
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        redisRepository.save(new MemberRedisEntity(memberVO.getId(), jwtToken));
        log.info("redisRepository.findById = {}", (redisRepository.findById(memberVO.getId()).get()).getJwtTokenVO());
        log.info("id = {}", (redisRepository.findById(memberVO.getId()).get()).getId());
        log.info("accessToken = {}", (redisRepository.findById(memberVO.getId()).get()).getJwtTokenVO().getAccessToken());
        log.info("refreshToken = {}", (redisRepository.findById(memberVO.getId()).get()).getJwtTokenVO().getRefreshToken());
        return new SingleResponse(jwtToken);
    }
}
