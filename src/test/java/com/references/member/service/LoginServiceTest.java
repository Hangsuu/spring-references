package com.references.member.service;

import com.references.common.model.CmmnPagingModel;
import com.references.common.model.CmmnPagingVO;
import com.references.common.mybatis.AESHandler;
import com.references.member.model.AnnotationTestVO;
import com.references.member.model.EncryptTestVO;
import com.references.member.model.PaginationTestVO;
import com.references.member.repository.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class LoginServiceTest {
    @Autowired
    private MemberMapper memberMapper;

    @Test
    void getDecrypt() throws Exception {
        AESHandler handler = new AESHandler();
        System.out.println(handler.encrypt("abc"));
    }
    @Test
    void selectEncrypt() throws Exception {
        EncryptTestVO vo = memberMapper.selectEncrypt();
        assertThat(vo.getId()).isEqualTo("abc");
    }
}