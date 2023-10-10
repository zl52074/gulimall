package com.zl52074.gulimall.common.exception;

import com.zl52074.gulimall.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: zl52074
 * @time: 2023/10/10 7:47
 */
@Slf4j
@RestControllerAdvice("com.zl52074.gulimall")
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R HandleValidException(MethodArgumentNotValidException e){
        log.error("数据校验异常：");
        Map<String,String> map = new HashMap<>();
        BindingResult result = e.getBindingResult();
        result.getFieldErrors().forEach(error->{
            log.error(e.getMessage());
            map.put(error.getField(),error.getDefaultMessage());
        });
        return R.error(BizCodeEnum.VAILD_EXCEPTION.getCode(), BizCodeEnum.VAILD_EXCEPTION.getMsg()).put("data",map);
    }

    @ExceptionHandler(Throwable.class)
    public R HandleException(Throwable throwable){
        log.error("未知异常\n",throwable);
        return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(), BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }
}
