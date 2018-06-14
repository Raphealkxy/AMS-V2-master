package com.eric.common.exception;

import com.eric.common.utils.Res;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class AMSExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(AMSException.class)
	public Res handleAMSException(AMSException e){
		Res r = new Res();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Res handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return Res.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public Res handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return Res.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(UnauthorizedException.class)
	public Res handleUnauthorizedException(UnauthorizedException e){
		logger.error(e.getMessage(), e);
		return Res.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public Res handleException(Exception e){
		logger.error(e.getMessage(), e);
		return Res.error();
	}
}
