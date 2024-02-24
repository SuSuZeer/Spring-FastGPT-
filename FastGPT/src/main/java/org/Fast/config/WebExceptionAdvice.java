package org.Fast.config;

import lombok.extern.slf4j.Slf4j;
import org.Fast.dto.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


/***
 * @title WebExceptionAdvice
 * @author SUZE
 * @Date 16:41
 **/
@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Result> handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        log.error(e.toString(), e);
        Result result = Result.fail("服务器异常");
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;//500
        if (e instanceof UnAuthorException) {
            //这个是拦截器报错才设置的状态码
            status = HttpStatus.UNAUTHORIZED;//401
        }
        ResponseEntity<Result> resultResponseEntity = new ResponseEntity<>(result, status);
        log.error(resultResponseEntity.toString());
        return resultResponseEntity;
    }
}
