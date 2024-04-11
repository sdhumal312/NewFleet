package org.fleetopgroup.web.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionProcess {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionProcess.class);
	
	public static ValueObject execute(HttpServletRequest request, Exception exception) throws Exception {
		ValueObject	object = new ValueObject();
		object.put("hasError", true);
		logger.error(exception.getMessage());
		return Utility.setUniqueTokenInSession(object, request);
		
	}
}
