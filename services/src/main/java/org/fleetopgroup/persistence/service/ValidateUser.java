package org.fleetopgroup.persistence.service;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SMSConstant;
import org.fleetopgroup.persistence.bl.MobileAppUserRegistrationBL;
import org.fleetopgroup.persistence.dao.CompanyWisePrivilegesRepository;
import org.fleetopgroup.persistence.dao.MobileAppUserRegistrationRepository;
import org.fleetopgroup.persistence.dao.UserProfileRepository;
import org.fleetopgroup.persistence.dao.UserRepository;
import org.fleetopgroup.persistence.dto.MobileUserDto;
import org.fleetopgroup.persistence.model.MobileAppUserRegistration;
import org.fleetopgroup.persistence.model.Privilege;
import org.fleetopgroup.persistence.model.Role;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.report.dao.MobileUserDataDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IValidateUser;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
@Service
public class ValidateUser implements IValidateUser{

	@Autowired PasswordEncoder										passwordEncoder;
	@Autowired MobileUserDataDao									mobileUserDataDao;
	@Autowired ICompanyConfigurationService							companyConfigurationService;
	@Autowired MobileAppUserRegistrationRepository					mobileAppUserRegistrationRepository;
	@Autowired UserRepository 										userRepository;
	@Autowired CompanyWisePrivilegesRepository 						companyWisePrivilegesRepository;
	@Autowired UserProfileRepository 								userProfileRepository;
	@Autowired IEmailSenderService				                    emailSenderService;
	

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public ValueObject validateUser(ValueObject valueObjIn) throws Exception {
		ValueObject						valueOutObj					= null;
		String 							email						= null;
		String 							companyCode					= null;
		String 							password					= null;
		MobileUserDto 					mobileUser					= null;
		boolean							isPasswordMatches			= false;
		boolean							sendOTP						= true;
		try {
			valueOutObj = new ValueObject();

			if(!valueObjIn.containsKey("companyCode")) {
				valueOutObj.put("message", "Unauthorized Access !!");
				return valueOutObj;
			}

			email 			= valueObjIn.getString("email",null);
			companyCode 	= valueObjIn.getString("companyCode",null);
			password 		= valueObjIn.getString("password",null);

			mobileUser		= mobileUserDataDao.getUserDataForMobileApp(companyCode, email);
			//userDetails 	= new CustomUserDetails(mobileUser.getCompanyId(), mobileUser.getUserId());
			
			User         user 		= userRepository.findUser(email , companyCode);
			List<String> privileges = getPrivilegesForMobile(user.getRoles(), mobileUser.getCompanyId(), email);

			if(mobileUser != null) {
				isPasswordMatches 	= passwordEncoder.matches(password, mobileUser.getPassword());
				if(isPasswordMatches) {
					if(mobileUser.isEnabled()) {
						mobileUser.setPassword(password);
						
						valueOutObj.put("mobileUser", mobileUser);
						valueOutObj.put("permission", privileges);
						
						sendOTP			= mobileUserDataDao.getValidateUserRegistraion(mobileUser);
						valueOutObj.put("sendOTP", sendOTP);
						
					} else {
						valueOutObj.put("message", "User is Deactivated !!");
					}
				} else {
					valueOutObj.put("message", "Company name or Password is wrong !!");
				}
			} else {
				valueOutObj.put("message", "Unauthorized Access !!");
			}
			return valueOutObj;
		} catch (Exception e) {
			System.err.println("Exception : "+e);
			throw e;
		}finally {
			valueOutObj					= null; 
			email						= null; 
			companyCode					= null; 
			password					= null; 
			mobileUser					= null; 
			isPasswordMatches			= false;
			sendOTP						= true; 
		}
	}

	@Override
	public ValueObject mobileRegistrationOTP(ValueObject valueInObject) throws Exception {

		HashMap<String, Object> 	configuration			= null;
		String						smsAPIKey				= null;
		String						maskingId				= null;
		String						content					= null;
		HttpResponse<String> 		response 				= null;
		ValueObject					valueOutObj				= null;
		int							companyId				= 0;
		String						mobileNumber			= null;
		String						emailId		        	= null;
		try {
			valueOutObj = new ValueObject();

			if(!valueInObject.containsKey("mobileNumber")) {
				valueOutObj.put("otpSend", false);
				return valueOutObj;
			}

			content					= valueInObject.getString("otpMessage",null);
			companyId				= valueInObject.getInt("companyId",0);
			mobileNumber			= valueInObject.getString("mobileNumber",null);
			configuration			= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.SMS_CONFIG);
			smsAPIKey				= (String) configuration.get(SMSConstant.SMS_WORKING_KEY);
			maskingId				= (String) configuration.get(SMSConstant.SMS_MASKING_KEY);
			emailId                 = valueInObject.getString("emailId",null);
			if(emailId!=null && !emailId.isEmpty()){
				sendOtpOnMail(content,emailId);
			}
			if((int)configuration.get("smsAPIFlavor") == SMSConstant.SEND_API_FLAVOR_ONE) {
				response =		Unirest.get((String)configuration.get(SMSConstant.SMS_API_FLAVOR1_URL)).queryString("method", "sms")
						.queryString(SMSConstant.API_KEY, smsAPIKey)
						.queryString(SMSConstant.TO, mobileNumber)
						.queryString(SMSConstant.SENDER, maskingId)
						.queryString(SMSConstant.MESSAGE, content).asString();


			}else if((int)configuration.get("smsAPIFlavor") == SMSConstant.SEND_API_FLAVOR_TWO) {
				response =		Unirest.get((String)configuration.get(SMSConstant.SMS_API_FLAVOR2_URL)).queryString(SMSConstant.USER_NAME,configuration.get(SMSConstant.USER_NAME)+"")
						.queryString(SMSConstant.PASSWORD, configuration.get(SMSConstant.PASSWORD)+"")
						.queryString(SMSConstant.TO, mobileNumber)
						.queryString(SMSConstant.FROM, (String)configuration.get(SMSConstant.FROM))
						.queryString(SMSConstant.TEXT, content).asString();

			}
			if (response.getStatus() == HttpURLConnection.HTTP_OK) {
				valueOutObj.put("otpSend", true);
			}else {
				valueOutObj.put("otpSend", false);
			}	
			return valueOutObj;
		} catch (Exception e) {
			throw e;
		} finally {
			configuration			= null; 
			smsAPIKey				= null; 
			maskingId				= null; 
			content					= null; 
			response 				= null; 
			valueOutObj				= null; 
			companyId				= 0;    
			mobileNumber			= null; 
		}
	}
	
	@Override
	public ValueObject mobileAppUserRegistration(ValueObject valueInObject) throws Exception {
		ValueObject					valueOutObj						= null;
		MobileAppUserRegistration	mobileAppUserRegistration		= null;
		try {
			MobileAppUserRegistrationBL mobileAppUserRegistrationbl = new MobileAppUserRegistrationBL();
			valueOutObj = new ValueObject();
			
			if(!valueInObject.containsKey("mobileNumber")) {
				valueOutObj.put("message", "Unauthorised Access !");
				return valueOutObj;
			}
			mobileAppUserRegistration	= mobileAppUserRegistrationbl.getMobileAppUserRegistrationDto(valueInObject);
			mobileAppUserRegistrationRepository.save(mobileAppUserRegistration);
			
			valueOutObj.put("success", true);
			
			return valueOutObj;
		} catch(Exception e) {
			throw e;
		} finally {
			
		}
	}
	
	private final List<String> getPrivilegesForMobile(final Collection<Role> roles, Integer companyId, String email) throws Exception {
        final List<String> privileges = new ArrayList<String>();
        final List<Privilege> collection = new ArrayList<Privilege>();
        
        List<Long> 	companyWisePrivileges	=  companyWisePrivilegesRepository.getCompanyWisePrivilegesListIds(companyId);
        
        for (final Role role : roles) {
        	if(role != null)
             collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
        	if(companyWisePrivileges.contains(item.getId()) || companyId == CompanyConstant.COMPANY_CODE_FLEETOP || companyId == CompanyConstant.COMPANY_CODE_SRS)
        		privileges.add(item.getName());
        		if(email != null && email.equalsIgnoreCase("admin") && (companyId == CompanyConstant.COMPANY_CODE_FLEETOP || companyId == CompanyConstant.COMPANY_CODE_SRS)) {
        			privileges.add("MASTER_SETUP_PRIVILEGE");
        			privileges.add("MASTER_COM_VIEW_PRIVILEGE");
        			privileges.add("MASTER_COM_ADD_PRIVILEGE");
        			privileges.add("MASTER_COM_EDIT_PRIVILEGE");
        			privileges.add("MASTER_RESET_PWD_PRIVILEGE");
        		}
        }
        return privileges;
    }
	
	private final List<GrantedAuthority> getGrantedAuthoritiesForMobile(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
      
        return authorities;
    }
	
	private void sendOtpOnMail(String content, String emailId)throws Exception {
		String               otpMessageSubject = "OTP For Registration on Fleetop Mobile Services";
		try{
			if(!content.isEmpty() && !emailId.isEmpty()){
				emailSenderService.sendFleetopOtpOnMail(emailId, otpMessageSubject,content,false);
			}
		}catch(Exception e){
			throw e;
		}
	}

	public void updateUserTokenForNotification(ValueObject valueInObject) throws Exception {
		try{
			mobileAppUserRegistrationRepository.updateUserTokenForNotification(valueInObject.getString("tokenForNotification",""),valueInObject.getLong("userId",0),valueInObject.getLong("companyId",0));
		}catch(Exception e){
			throw e;
		}
	}
}
