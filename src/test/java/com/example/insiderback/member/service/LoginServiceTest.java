package com.example.insiderback.member.service;

import com.example.insiderback.common.model.CmmnPagingModel;
import com.example.insiderback.common.model.CmmnPagingVO;
import com.example.insiderback.member.repository.MemberMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceTest {
    @Autowired
    private MemberMapper memberMapper;

    @Test
    void selectOne() {
        CmmnPagingModel model = new CmmnPagingModel();
        model.setCurrentPage(1);
        model.setFirstIndex(1);
        model.setLastIndex(5);
        CmmnPagingVO one = memberMapper.selectOne(model);

        Assertions.assertThat(one.getRnum()).isEqualTo(1);

        System.out.println("테스트 완료");
    }

}
