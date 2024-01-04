package com.example.insiderback.member.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {
    private String id;
    private String password;
    private String name;
    private String age;
}
