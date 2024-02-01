package com.example.insiderback.common.interceptor;

import com.example.insiderback.common.mybatis.MaskingClass;
import com.example.insiderback.common.mybatis.MaskingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
@Component
public class MaskingInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        if (resultObject instanceof List) {
            ArrayList resultList = (ArrayList) resultObject;
            if(!CollectionUtils.isEmpty(resultList)) {
                for (Object result : resultList) {
                    MaskingUtil.maskingFieldProccess(result);
                }
            }
        } else {
            if (needToMasking(resultObject)) {
                MaskingUtil.maskingFieldProccess(resultObject);
            }
        }
        return resultObject;
    }

    // masking 대상 class 여부 확인
    private boolean needToMasking(Object object) {
        Class objectClass=  object.getClass();
        MaskingClass maskingClass = AnnotationUtils.getAnnotation(objectClass, MaskingClass.class);
        return Objects.nonNull(maskingClass);
    }
}
