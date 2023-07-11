package com.atguigu.common.exception;

import com.atguigu.gmall.common.execption.GmallException;
import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.common.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description：全局异常处理器
 * @Author: scv
 * @CreateTime: 2023-07-11  19:12
 * @Version: 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GmallException.class)
    public Result gmallExceptionHandler(GmallException e){
        return  Result.build(null,e.getResultCodeEnum());
    }
    @ExceptionHandler(Exception.class)
    public Result systemctlExceptionHandler(Exception e){
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }
}
