package com.athena.framework.web.exception;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.athena.common.constant.HttpStatus;
import com.athena.common.exception.BaseException;
import com.athena.common.exception.CustomException;
import com.athena.common.exception.DemoModeException;
import com.athena.framework.web.domain.ResResult;

/**
 * 全局异常处理器
 * 
 * @author athena
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 基础异常
     */
    @ExceptionHandler(BaseException.class)
    public ResResult baseException(BaseException e)
    {
        return ResResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public ResResult businessException(CustomException e)
    {
        if (ObjectUtil.isNull(e.getCode()))
        {
            return ResResult.error(e.getMessage());
        }
        return ResResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResResult handlerNoFoundException(Exception e)
    {
        log.error(e.getMessage(), e);
        return ResResult.error(HttpStatus.NOT_FOUND, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResResult handleAuthorizationException(AccessDeniedException e)
    {
        log.error(e.getMessage());
        return ResResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResResult handleAccountExpiredException(AccountExpiredException e)
    {
        log.error(e.getMessage(), e);
        return ResResult.error(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResResult handleUsernameNotFoundException(UsernameNotFoundException e)
    {
        log.error(e.getMessage(), e);
        return ResResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResResult handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return ResResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResResult validatedBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return ResResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validExceptionHandler(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResResult.error(message);
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public ResResult demoModeException(DemoModeException e)
    {
        return ResResult.error("演示模式，不允许操作");
    }
}
