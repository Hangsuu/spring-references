package com.references.member.service.impl;

import com.references.member.model.MemberVO;
import com.references.member.repository.MemoryRepo;
import com.references.member.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    MemoryRepo memoryRepo;

    @Override
    public MemberVO getUser(String id) {
        return memoryRepo.getUser(id);
    }

    @Override
    public void setUser(MemberVO vo) {
        memoryRepo.setUser(vo);
    }

    @Override
    public void delUser(String id) {
        memoryRepo.delUser(id);
    }
}
