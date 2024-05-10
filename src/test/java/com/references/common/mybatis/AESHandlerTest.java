package com.references.common.mybatis;

import com.references.member.model.EncryptTestVO;
import com.references.member.repository.MemberMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AESHandlerTest {
    @Autowired
    MemberMapper memberMapper;

    @Test
    void AESHandlerTest() {
        EncryptTestVO testVO = memberMapper.selectEncrypt();
        Assertions.assertThat(testVO.getId()).isEqualTo("abc");
    }
}
