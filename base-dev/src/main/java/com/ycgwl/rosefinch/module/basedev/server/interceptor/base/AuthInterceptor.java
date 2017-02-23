package com.ycgwl.rosefinch.module.basedev.server.interceptor.base;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ycgwl.framework.springmvc.entity.ResultEntity;
import com.ycgwl.framework.springmvc.util.SecurityUtil;
import com.ycgwl.kafka.server.constant.LogConstant;
import com.ycgwl.kafka.server.logger.Logger;
import com.ycgwl.kafka.server.logger.LoggerFactory;
import com.ycgwl.rosefinch.module.basedev.server.services.base.IBaseRedisCacheService;
import com.ycgwl.rosefinch.module.basedev.shared.entity.base.BaseAppInfoEntity;


/**
 * 权限拦截器 Created by xieshenghua on 2014/8/14.
 */
@Aspect
@Component
public class AuthInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthInterceptor.class);
	private List<String> excludeUrls;
	//过期时间
	private String expiredTime;
	
	@Autowired
	private IBaseRedisCacheService baseRedisCacheService;
	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception exception)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		//System.out.println("进入了授权拦截器........");
		StringBuffer url = request.getRequestURL();
		if(!CollectionUtils.isEmpty(excludeUrls)){
			for(String megx:excludeUrls){
				if(url.toString().endsWith(megx)){
					return true;
				}
			}
		}
		PrintWriter out = null;
		try {
			String params = request.getParameter("params");// 参数
			//params = new String(params.getBytes("iso-8859-1"), "utf-8"); 
			String digest = request.getParameter("digest");// 摘要
			String timestamp = request.getParameter("timestamp");// 时间戳
			String appkey = request.getParameter("appkey");
			String requestIp = SecurityUtil.getReomteIpFromRequest(request);// 请求IP

			String verifyResult = verify(params, digest, requestIp, timestamp,appkey);
			if (!verifyResult.equals("true")) {
				String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
				response.setContentType(CONTENT_TYPE);
				out = response.getWriter();
				out.println(verifyResult);
				return false;
			} else {
				return true;
			}

		} catch (Exception e) {
			logger.error("系统异常：" + e.getMessage(),LogConstant.LOG_TYPE_EXCEPTION,e);
			String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";
			response.setContentType(CONTENT_TYPE);
			out = response.getWriter();
			ResultEntity res = new ResultEntity();
			res.setCode("9999");
			res.setMsg("系统异常：" + e.getMessage());
			out.println( JSONObject.toJSONString(res));
			return false;
		}
	}





	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public String verify(String params, String digest, String requestIp,
			String timestamp,String appkey) {
		Validator validator = Validator.getInstance();
		ResultEntity res = new ResultEntity();
		
		// 验证请求IP
		try {
			BaseAppInfoEntity basicAppInfo = validator.validateAppkey(appkey,baseRedisCacheService);
			if (basicAppInfo == null) {
				res.setCode("2001");
				res.setMsg("访问权限错误,appkey错误:" + appkey);
				return  JSONObject.toJSONString(res);
			}
		
			// 简单的参数校验
			if (StringUtils.isEmpty(params)) {
				res.setCode("3001");
				res.setMsg("参数错误,参数为空");
				return  JSONObject.toJSONString(res);
			}
			// 时间戳校验
			if (!validator.validateTimestamp(timestamp,expiredTime)) {
				res.setCode("2002");
				res.setMsg("时间戳无效,时间戳:" + timestamp);
				return  JSONObject.toJSONString(res);
			}
			// 摘要验证
			if (!validator.validateDigest(params, digest,appkey,basicAppInfo.getAppSecret())) {
				res.setCode("2002");
				res.setMsg("数据安全错误,摘要验证错误");
				return  JSONObject.toJSONString(res);
			}
		} catch (Exception e) {
			logger.error("系统错误,系统错误:AuthInterceptor.verify",LogConstant.LOG_TYPE_EXCEPTION,e);
			res.setCode("9999");
			res.setMsg("系统错误,系统错误:AuthInterceptor.verify");
			return  JSONObject.toJSONString(res);
		}
		return "true";
	}

	/**
	 * @return the expiredTime
	 */
	public String getExpiredTime() {
		return expiredTime;
	}

	/**
	 * @param expiredTime the expiredTime to set
	 */
	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

}
