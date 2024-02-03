package com.example.insiderback.member.service;

import com.example.insiderback.common.model.CmmnPagingModel;
import com.example.insiderback.common.model.CmmnPagingVO;
import com.example.insiderback.common.mybatis.AESHandler;
import com.example.insiderback.member.model.AnnotationTestVO;
import com.example.insiderback.member.model.EncryptTestVO;
import com.example.insiderback.member.model.PaginationTestVO;
import com.example.insiderback.member.repository.MemberMapper;
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
    void selectPage() {
        CmmnPagingModel model = new CmmnPagingModel();
        model.setCurrentPage(1);
        model.setFirstIndex(1);
        model.setLastIndex(5);
        PaginationTestVO one = memberMapper.selectPage(model);

        assertThat(one.getRnum()).isEqualTo(1);
    }

    @Test
    void selectAnnotation() {
        AnnotationTestVO model = memberMapper.selectAnnotation();
        assertThat(model.getName()).isEqualTo("ab*");
    }

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