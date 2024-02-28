package com.example.insiderback.common.interceptor;

import com.example.insiderback.common.model.CmmnPagingModel;
import com.example.insiderback.member.model.PaginationTestVO;
import com.example.insiderback.member.repository.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MybatisInterceptorTest {
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
}
