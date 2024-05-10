package com.references.member.repository;

import com.references.member.model.MemberVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MemoryRepo {

    private Map<String, MemberVO> userMap;

    public MemoryRepo() {
        this.userMap = new HashMap<>();
    }

    public MemberVO getUser(String id) {
        return userMap.get(id);
    }

    public void setUser(MemberVO vo) {
        userMap.put(vo.getId(), vo);
    }

    public void delUser(String id) {
        userMap.remove(id);
    }
}
