package com.example.insiderback.member.service;

import com.example.insiderback.common.jwt.model.JwtTokenVO;
import com.example.insiderback.member.model.MemberVO;

public interface JwtLoginService {
    public JwtTokenVO login(MemberVO vo);
}
