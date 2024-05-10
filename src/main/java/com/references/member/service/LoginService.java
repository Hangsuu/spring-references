package com.references.member.service;

import com.references.member.model.MemberVO;

public interface LoginService {
    public MemberVO getUser(String id);
    public void setUser(MemberVO vo);
    public void delUser(String id);
}
