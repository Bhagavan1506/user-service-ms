package com.javaexpress.interceptors;

import java.time.Instant;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserLoggingInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		
		log.info("[REQUEST] Method: {}, URI: {},IP: {},Start Time: {}",request.getMethod(),request.getRequestURI(),request.getRemoteAddr(),
					Instant.ofEpochMilli(startTime));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long startTime = (long)request.getAttribute("startTime");
		long duration = System.currentTimeMillis() - startTime;
		
		log.info("[RESPONSE] Status : {}, URI: {}, Duration: {} ms",response.getStatus(),request.getRequestURI(),duration);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("afterCompletion");
		if(ex != null) {
			log.error("[ERROR] Exception occured for URI : {}, Error: {} ",request.getRequestURI(),ex.getMessage());
		}
	}
}
