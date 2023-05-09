package com.zoulj.msapp.infrastructure.config;

import com.zoulj.msapp.infrastructure.exception.BusinessException;
import com.zoulj.msapp.interfaces.vo.CommonReturnType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * Rest控制器异常处理类
 * @author leonard
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public CommonReturnType globalException(HttpServletResponse response, BusinessException ex) {
        log.info("错误代码：" + response.getStatus());
        log.error("GlobalExceptionHandler...", ex);
        for (StackTraceElement ste : ex.getStackTrace()) {
            log.error("GlobalExceptionHandler...{}", ste.toString());
        }
        return CommonReturnType.create(ex.getErrCode()+" "+ex.getErrMsg(),"fail");
    }

}
