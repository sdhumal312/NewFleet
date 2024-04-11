package org.fleetopgroup.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.web.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class OneTimeTokenPostInterceptor extends HandlerInterceptorAdapter {

	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	 private static final String UNIQUE_ONE_TIME_TOKEN 	= "unique-one-time-token";
	 private static final String VALIDATE_DOUBLE_POST 	= "validateDoublePost";

	    @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    	if(!Utility.getBoolean(request.getParameter(VALIDATE_DOUBLE_POST))) {
	    		return true;
	    	}
	    	String key 	= request.getParameter(UNIQUE_ONE_TIME_TOKEN);
	    	
	        if(request.getSession().getAttribute(key) != null) {
	            request.getSession().removeAttribute(key);
		        return true;
	        }else {
	        	logger.error("token {} is null or allready used", key);
		           Thread.sleep(50000);
		            response.sendError(202, "Method already processing......"); // ignore request
		            return false;
	        }
	       
	    }
	  
}
