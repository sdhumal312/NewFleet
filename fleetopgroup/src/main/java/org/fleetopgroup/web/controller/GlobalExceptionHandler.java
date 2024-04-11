package org.fleetopgroup.web.controller;

import java.io.EOFException;
import java.io.IOException;
import java.sql.SQLException;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import io.sentry.Sentry;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public GlobalExceptionHandler() {
		super();
		Sentry.init(options -> {
			  options.setDsn("https://d7a2e64f080c46c69ba4c8b9e2b18357@o313768.ingest.sentry.io/1785802");
		});
	}
	
	@ExceptionHandler(SQLException.class)
	public String handleSQLException(HttpServletRequest request, Exception ex){
		captureException(request, ex);
		logger.error("DB Error: ", ex);
		return "database_error";
	}
	
	@ExceptionHandler(IOException.class)
	public void handleIOException(HttpServletRequest request, Exception ex){
		captureException(request, ex);
		logger.error("IOException handler executed");
		logger.error("IO Error: ", ex);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public void handleMethodArgumentTypeMismatchException(HttpServletRequest request, Exception ex){
	}
	
	@ExceptionHandler(AddressException.class)
	public void handleAddressException(HttpServletRequest request, Exception ex){
		captureException(request, ex);
		logger.error("AddressException Error: ", ex);
	}
	
	@ExceptionHandler(EOFException.class)
	public void handleEOFException(HttpServletRequest request, Exception ex){
		logger.info("EOFException Exception Occured:: URL="+request.getRequestURL());
	}
	
	@ExceptionHandler(MultipartException.class)
	public void handleMultipartException(HttpServletRequest request, Exception ex){
		logger.info("EOFException Exception Occured:: URL="+request.getRequestURL());
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public void handleHttpRequestMethodNotSupportedException(HttpServletRequest request, Exception ex){
	}
	@ExceptionHandler(Exception.class)
	public void handleAllException(HttpServletRequest request, Exception ex){
		captureException(request, ex);
		logger.error("Exception Occured: ", ex);
	}
	@ExceptionHandler(NullPointerException.class)
	public void handleNullPointerException(HttpServletRequest request, Exception ex){
		captureException(request, ex);
		logger.error(" null Exception Occured: ", ex);
	}
	
	
	private void captureException(HttpServletRequest request, Exception ex) {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			logger.error("Exception For User : "+userDetails.getEmail()+" and company : "+userDetails.getCompanyCode()+"URL="+request.getRequestURL(), ex);
			if(!request.getRequestURL().toString().contains("localhost") && !request.getRequestURL().toString().contains("demo.fleetop")) {
				Sentry.captureException(ex, "From Url : "+request.getRequestURL());
			}
	}
}
