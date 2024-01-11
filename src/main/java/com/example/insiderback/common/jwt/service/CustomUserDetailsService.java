package com.example.insiderback.common.jwt.service;

import com.example.insiderback.member.model.MemberVO;
import com.example.insiderback.member.repository.MemoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemoryRepo memoryRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberVO vo = memoryRepo.getUser(username);
        if(vo != null) {
            return createUserDetails(vo);
        } else {
            throw new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));
        }
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(MemberVO vo) {
        return User.builder()
                .username(vo.getId())
                .password(passwordEncoder.encode(vo.getPassword()))
                .roles(vo.getRoles().toArray(new String[0]))
                .build();
    }

}