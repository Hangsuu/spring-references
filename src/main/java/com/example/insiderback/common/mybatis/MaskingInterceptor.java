package com.example.insiderback.common.mybatis;

import io.swagger.v3.core.util.AnnotationsUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.util.CollectionUtils;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

@Intercepts({@Signature(type = ResultSetHandler.class, method = "handlerResultSets", args = {Statement.class})})
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
        MaskingClass maskingClass = AnnotationsUtils.getAnnotation(objectClass, MaskingClass.class);
        return Objects.nonNull(maskingClass);
    }
}
