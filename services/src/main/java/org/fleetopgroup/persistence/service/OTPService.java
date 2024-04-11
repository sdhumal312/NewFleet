package org.fleetopgroup.persistence.service;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.OTPRequiredTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SMSConstant;
import org.fleetopgroup.persistence.bl.TwoFactorAuthenticationBL;
import org.fleetopgroup.persistence.dao.TwoFactorAuthenticationDetailsRepository;
import org.fleetopgroup.persistence.dao.UserProfileRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TwoFactorAuthenticationDetails;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IOTPService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Service
public class OTPService implements IOTPService {

	 @Autowired	private ICompanyConfigurationService				companyConfigurationService;
	 //@Autowired	private IUserProfileService							userProfileService;
	 @Autowired	private JavaMailSender								mailSender;
	 @Autowired private TwoFactorAuthenticationDetailsRepository	twoFactorAuthenticationDetailsRepository;
	 @Autowired private UserProfileRepository						userProfileRepository;

	
	//cache based on username and OPT MAX 8 
	 private static final Integer EXPIRE_MINS = 5;
	 private LoadingCache<Long, Integer> otpCache;
	 
	 public OTPService(){
		 super();
		 otpCache = CacheBuilder.newBuilder().
	     expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<Long, Integer>() {
	      public Integer load(Long key) {
	             return 0;
	       }
	   });
	 }
	 
	 
	//This method is used to push the opt number against Key. Rewrite the OTP if it exists
	 //Using user id  as key
	 @Override
	 public int generateOTP(Long key) throws Exception{
		HashMap<String, Object> 	configuration			= null;
		String						smsAPIKey				= null;
		String						maskingId				= null;
		HashMap<String, Object> 	companyConfiguration	= null;
		HttpResponse<String> 		response 				= null;
		 try {
			 Random random = new Random();
				int otp = 1000 + random.nextInt(9000);
				otpCache.put(key, otp);
				
				TwoFactorAuthenticationDetails	authenticationDetails	=		twoFactorAuthenticationDetailsRepository.getTwoFactorAuthenticationDetails(key);
				
				if(authenticationDetails != null) {
					configuration			= companyConfigurationService.getCompanyConfiguration(authenticationDetails.getCompanyId(), PropertyFileConstants.SMS_CONFIG);
					companyConfiguration	= companyConfigurationService.getCompanyConfiguration(authenticationDetails.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
					
					smsAPIKey	= (String) configuration.get(SMSConstant.SMS_WORKING_KEY);
					maskingId	= (String) configuration.get(SMSConstant.SMS_MASKING_KEY);
					
					String content = otp+" is your Fleetop verification code. Code valid for 5 minutes only, Please DO NOT share this OTP with anyone to ensure account security.";
				if((int)companyConfiguration.get("OTPSendToType") == OTPRequiredTypeConstant.SEND_OTP_TYPE_SMS || (int)companyConfiguration.get("OTPSendToType") == OTPRequiredTypeConstant.SEND_OTP_TYPE_BOTH) {
					 
					if((int)configuration.get("smsAPIFlavor") == SMSConstant.SEND_API_FLAVOR_ONE) {
						response =		Unirest.get((String)configuration.get(SMSConstant.SMS_API_FLAVOR1_URL)).queryString("method", "sms")
								.queryString(SMSConstant.API_KEY, smsAPIKey)
								.queryString(SMSConstant.TO, authenticationDetails.getMobileNumber())
								.queryString(SMSConstant.SENDER, maskingId)
								.queryString(SMSConstant.MESSAGE, content).asString();
						
					
					}else if((int)configuration.get("smsAPIFlavor") == SMSConstant.SEND_API_FLAVOR_TWO) {
						response =		Unirest.get((String)configuration.get(SMSConstant.SMS_API_FLAVOR2_URL)).queryString(SMSConstant.USER_NAME, configuration.get(SMSConstant.USER_NAME)+"")
								.queryString(SMSConstant.PASSWORD, configuration.get(SMSConstant.PASSWORD)+"")
								.queryString(SMSConstant.TO, authenticationDetails.getMobileNumber())
								.queryString(SMSConstant.FROM, (String)configuration.get(SMSConstant.FROM))
								.queryString(SMSConstant.TEXT, content).asString();
						
					}
					
					
			 if (response.getStatus() == HttpURLConnection.HTTP_OK) {
				 System.err.println("SMS Sent Successfully !");
			 }else {
				 System.err.println("SMS Sending failed !");
			 }	
				}	
					if((int)companyConfiguration.get("OTPSendToType") == OTPRequiredTypeConstant.SEND_OTP_TYPE_EMAIL || (int)companyConfiguration.get("OTPSendToType") == OTPRequiredTypeConstant.SEND_OTP_TYPE_BOTH) {
						mailSender.send(new MimeMessagePreparator() {
							@Override
							public void prepare(MimeMessage mimeMessage) throws MailParseException  {
								try {
									MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
									messageHelper.setTo(authenticationDetails.getEmail());		
									messageHelper.setSubject("Fleetop Login OTP");
									messageHelper.setText(content);
									
								}catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				}
				
			return otp;	
		} catch (Exception e) {
			System.out.println("Exception "+ e);
			throw e;
		}finally {
			configuration			= null;
			smsAPIKey				= null;
			maskingId				= null;
			companyConfiguration	= null;
		}
		
		
	 }
	 //This method is used to return the OPT number against Key->Key values is username
	 @Override
	 public int getOtp(Long key){ 
		try{
		 return otpCache.get(key); 
		}catch (Exception e){
		 return 0; 
		}
	 }
	//This method is used to clear the OTP catched already
	 @Override
	public void clearOTP(Long key){ 
		otpCache.invalidate(key);
	 }
	 
	 @Override
	public void validateIdOTPValidated(HttpServletRequest	request, HttpServletResponse response) throws Exception {
		 CustomUserDetails			userDetails				= null;
		 HashMap<String, Object>	configuration			= null;
		 boolean					isOTPValidationAtLogin	= false;
		 try {
			 userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			 
			 isOTPValidationAtLogin	= (boolean) configuration.get(ICompanyConfigurationService.IS_OTP_VALIDATION_AT_LOGIN);
			 if(isOTPValidationAtLogin && !userDetails.isOTPValidated()) {
				 HttpSession session= request.getSession(false);
				 SecurityContextHolder.clearContext();
			        if(session != null) {
			            session.invalidate();
			        }
			        response.sendRedirect("/login.html?otp=false");
			 }
			 
		} catch (Exception e) {
			throw e;
		}
	}
	 
	@Override
	@Transactional
	public ValueObject saveTwoFactorAuthDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails					userDetails				= null;
		TwoFactorAuthenticationDetails		authenticationDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("userDetails", userDetails);
			
			authenticationDetails	=	TwoFactorAuthenticationBL.getTwoFactorAuthenticationBLDTO(valueObject);
			
			twoFactorAuthenticationDetailsRepository.save(authenticationDetails);
			userProfileRepository.updateTwoFactorEnableDisableState(true, valueObject.getLong("userprofile_id"));
			
			valueObject.clear();
			
			valueObject.put("message", "Data Saved Successfully !");
			
			return valueObject;
		} catch (Exception e) {
			valueObject.put("message", "Operation failed please contact to system administrator !");
			throw e;
		}finally {
			userDetails				= null;
			authenticationDetails	= null;
		}
	}
	 
	@Override
	public ValueObject getTwoFactorAuthDetails(ValueObject valueObject) throws Exception {
		
		valueObject.put("details", twoFactorAuthenticationDetailsRepository.getTwoFactorAuthenticationDetails(valueObject.getLong("userId")));
		
		
		return valueObject;
	}
	
	@Override
	public ValueObject resendOTPValidationCode(ValueObject valueObject) throws Exception {
		try {
			clearOTP(valueObject.getLong("id"));
			
			valueObject.put("OTP", generateOTP(valueObject.getLong("id")));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	 
}
