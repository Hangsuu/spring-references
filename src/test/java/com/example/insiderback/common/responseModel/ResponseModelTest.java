package com.example.insiderback.common.responseModel;

import com.example.insiderback.common.responseModel.model.CmmnResponseModel;
import com.example.insiderback.common.responseModel.model.ListResponse;
import com.example.insiderback.common.responseModel.model.SingleResponse;
import com.example.insiderback.member.model.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ResponseModelTest {
    @Test
    public void CmmnResponseModelTest() {
        MemberVO vo = new MemberVO();
        vo.setId("ID");
        vo.setName("testName");

        CmmnResponseModel<?> cmmnResponse = new CmmnResponseModel<>(vo);
        Assertions.assertThat(((MemberVO)cmmnResponse.getData()).getId()).isEqualTo("ID");
    }

    public void ListResponseTest() {
        Map<String, String> map = new HashMap<>();
        map.put("test1", "test1 value");
        map.put("test2", "test2 value");

        List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");

        MemberVO vo = new MemberVO();
        vo.setId("ID");
        vo.setName("testName");

        SingleResponse<?> listResponse = new SingleResponse<>(list);
        log.info("singleResponse = " + listResponse.getData());
        Assertions.assertThat(((List) listResponse.getData()).get(0)).isEqualTo("test1");

        SingleResponse<?> mapResponse = new SingleResponse<>(map);
        Assertions.assertThat(((HashMap) mapResponse.getData()).get("test1")).isEqualTo("test1 value");

        ListResponse<?> listResponse2 = new ListResponse<>(list);
        Assertions.assertThat(listResponse2.getList().get(0)).isEqualTo("test1");

        CmmnResponseModel<?> cmmnResponse = new CmmnResponseModel<>(vo);
        Assertions.assertThat(((MemberVO)cmmnResponse.getData()).getId()).isEqualTo("ID");
    }

    public void SingleResponseTest() {
        Map<String, String> map = new HashMap<>();
        map.put("test1", "test1 value");
        map.put("test2", "test2 value");

        List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");

        MemberVO vo = new MemberVO();
        vo.setId("ID");
        vo.setName("testName");

        SingleResponse<?> listResponse = new SingleResponse<>(list);
        log.info("singleResponse = " + listResponse.getData());
        Assertions.assertThat(((List) listResponse.getData()).get(0)).isEqualTo("test1");

        SingleResponse<?> mapResponse = new SingleResponse<>(map);
        Assertions.assertThat(((HashMap) mapResponse.getData()).get("test1")).isEqualTo("test1 value");

        ListResponse<?> listResponse2 = new ListResponse<>(list);
        Assertions.assertThat(listResponse2.getList().get(0)).isEqualTo("test1");

        CmmnResponseModel<?> cmmnResponse = new CmmnResponseModel<>(vo);
        Assertions.assertThat(((MemberVO)cmmnResponse.getData()).getId()).isEqualTo("ID");
    }
}
