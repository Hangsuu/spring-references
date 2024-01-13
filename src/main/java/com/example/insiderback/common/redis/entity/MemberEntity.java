package com.example.insiderback.common.redis.entity;

import com.example.insiderback.common.jwt.model.JwtTokenVO;
import com.example.insiderback.member.model.MemberVO;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
//value : Redis 의 keyspace 값으로 사용됩니다.
//timeToLive : 만료시간을 seconds 단위로 설정할 수 있습니다. 기본값은 만료시간이 없는 -1L 입니다.
@RedisHash(value="member", timeToLive = 30)
public class MemberEntity {
    //keyspace 와 합쳐져서 레디스에 저장된 최종 키 값은 keyspace:id 가 됩니다.
    @Id
    private String id;
    private JwtTokenVO jwtTokenVO;

    public MemberEntity(String id, JwtTokenVO jwtTokenVO) {
        this.id = id;
        this.jwtTokenVO = jwtTokenVO;
    }
}
