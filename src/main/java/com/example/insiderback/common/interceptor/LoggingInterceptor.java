package com.example.insiderback.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private final ObjectMapper objectMapper;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
        log.info("getClass : {}", request.getClass());
        if(request.getClass().getName().contains("SecurityContextHolderAwareRequestWrapper") //보안 관련 기능을 사용할 때 HTTP 요청 객체를 래핑하는 역할
            || request.getClass().getName().contains("StandardMultipartHttpServletRequest")) { // 파일 업로드와 관련된 요청을 처리하는 데 사용
            return;
        }
        // HTTP 요청과 응답의 내용을 캐싱할 수 있는 래퍼 클래스
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;

        if(cachingRequest.getContentType() != null && cachingRequest.getContentType().contains("application/json")) {
            if(cachingRequest.getContentAsByteArray() != null && cachingRequest.getContentAsByteArray().length != 0) {
                log.info("Request Body : {}", objectMapper.readTree(cachingRequest.getContentAsByteArray()));
            }
        }

        if(cachingResponse.getContentType() != null && cachingResponse.getContentType().contains("application/json")) {
            if(cachingResponse.getContentAsByteArray() != null && cachingResponse.getContentAsByteArray().length != 0) {
                log.info("Response Body : {}", objectMapper.readTree(cachingResponse.getContentAsByteArray()));
            }
        }
    }
}
