package com.example.insiderback.member.service;

import com.example.insiderback.member.model.MemberVO;

public interface LoginService {
    public MemberVO getUser(String id);
    public void setUser(MemberVO vo);
    public void delUser(String id);
}
