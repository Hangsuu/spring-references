package com.references.common.interceptor;

import com.references.common.model.CmmnPagingModel;
import com.references.member.model.PaginationTestVO;
import com.references.member.repository.MemberMapper;
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
