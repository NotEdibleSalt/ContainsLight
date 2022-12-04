package com.ss.contains.light.common.exception.handler;

import com.ss.contains.light.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


/**
 * 全局异常处理
 *
 * @version 1.0
 * @date 2022-5-2
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    /**
     * 处理security抛出的无访问权限异常
     *
     * @param ex
     * @return org.springframework.http.ResponseEntity<com.ss.contains.light.common.R>
     * @date 2022-5-2
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<R> exceptionHandler(AccessDeniedException ex) {

        Integer errorCode = 403;
        String message = "无权限";

        R<Object> build = R.builder().code(errorCode).message(message).build();
        return ResponseEntity.status(errorCode).body(build);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<R> exceptionHandler(Exception ex) {

        log.error("未知异常", ex);
        Integer errorCode = 500;
        String message = ex.getMessage();

        R<Object> build = R.builder().code(errorCode).message(message).build();
        return ResponseEntity.status(errorCode).body(build);
    }

}
