package com.references.member.service;

import com.references.common.jwt.model.JwtTokenVO;
import com.references.member.model.MemberVO;

public interface JwtLoginService {
    public JwtTokenVO login(MemberVO vo);

    public void logout(MemberVO vo);

    JwtTokenVO requestToken(String accessToken);
}
