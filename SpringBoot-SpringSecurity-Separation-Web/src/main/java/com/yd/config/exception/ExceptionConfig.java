package com.yd.config.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yd.constant.StatusCodeEnum;
import com.yd.rest.ResultData;
import com.yd.util.ExceptionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建全局异常处理类：
 * @ExceptionHandler用来定义函数针对的异常类型
 */
@ControllerAdvice
@Slf4j
public class ExceptionConfig {

	/**
	 * 返回json，跳转到页面也可以  用ModelAndView
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object defaultErrorHandler(HttpServletRequest req, Exception ex) throws Exception {
		log.error(ExceptionUtil.getStackTrace(ex));
        return ResultData.error(StatusCodeEnum.SYS_ERROR);
	}
	
	/**
	 * 会输出runtime异常的信息
	 * @param req
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@ExceptionHandler(value = CommonException.class)
	public ResultData myErrorHandler(HttpServletRequest req, CommonException ex) throws Exception {
		log.error(ExceptionUtil.getStackTrace(ex));
		return ResultData.error(ex.getCode(), ex.getMessage());
	}
}
