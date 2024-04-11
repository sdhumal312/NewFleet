package org.fleetopgroup.web.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class OTPValiadationInterceptor extends HandlerInterceptorAdapter{
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

//	@Autowired private ICompanyConfigurationService	companyConfigurationService;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	 HashMap<String, Object>	configuration			= null;
		 boolean					isOTPValidationAtLogin	= false;
    	try {
           
    		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUserDetails) {
            	CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                
            	if(userDetails.isOTPValidated())
            			return true;
            	
            	String actionName = "";
                if( handler instanceof HandlerMethod ) {
                    // there are cases where this handler isn't an instance of HandlerMethod, so the cast fails.
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    actionName = handlerMethod.getMethod().getName();
                  
                }
                CompanyConfigurationService	companyConfigurationService	= new CompanyConfigurationService();
            		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
            		isOTPValidationAtLogin	= (boolean) configuration.get(ICompanyConfigurationService.IS_OTP_VALIDATION_AT_LOGIN);
            		if(isOTPValidationAtLogin && !userDetails.isOTPValidated() && !CompanyConstant.getAuthorizedPagesNames().contains(actionName) && userDetails.isTwoFactorLogin()) {
            			
            			return false;
            	}
            }
		} catch (Exception e) {
			LOGGER.error("Exception : ", e);
		}
        return true;
    }
 
  /*  @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
 
        System.out.println("Inside post handle");
    }*/

}
