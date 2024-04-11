package org.fleetopgroup.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.CompanyConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.CompanyWisePrivilegesRepository;
import org.fleetopgroup.persistence.dao.RoleRepository;
import org.fleetopgroup.persistence.dao.TwoFactorAuthenticationDetailsRepository;
import org.fleetopgroup.persistence.dao.UserRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Privilege;
import org.fleetopgroup.persistence.model.Role;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.service.ConfiguarationCacheService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IOTPService;
import org.fleetopgroup.persistence.serviceImpl.IUserDetailsCacheService;
import org.fleetopgroup.web.error.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {
	
	  private final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired	private LoginAttemptService loginAttemptService;
    
    @Autowired	private ConfiguarationCacheService	configuarationCacheService;
    
    @Autowired	private CompanyWisePrivilegesRepository			companyWisePrivilegesRepository;
    
    @Autowired	private IOTPService		otpService	;
    
    @Autowired private IUserDetailsCacheService		userDetailsCacheService;
    
    @Autowired	ICompanyConfigurationService	companyConfigurationService;
    
    @Autowired	TwoFactorAuthenticationDetailsRepository	authenticationDetailsRepository;
   
    @Autowired
    private HttpServletRequest request;
    		
    
    public MyUserDetailsService() {
        super();
    }

    // API

	@Override
	@Transactional
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final String ip = request.getRemoteAddr();
        if (loginAttemptService.isBlocked(ip)) {
        	logger.error("Your System IP "+ip+" is blocked . User is : "+email+" Company : "+request.getParameter("companyCode"));
            throw new RuntimeException("blocked");
        }

        try {
        	User user = userRepository.findUser(email , request.getParameter("companyCode"));
            if (user == null) {
            	logger.error("Login failed for User : "+email+" company : "+request.getParameter("companyCode"));
                return new org.springframework.security.core.userdetails.User(" ", " ", true, true, true, true, getAuthorities(Arrays.asList(roleRepository.findByNameAndCompanyId("ROLE_USER", 0)), 0, null));
            }
            configuarationCacheService.cacheImporttantConfiguration(user.getCompany_id());
            
            HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(user.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
            
            if(request.getParameter("validateotp") != null && (boolean)configuration.get(ICompanyConfigurationService.IS_OTP_VALIDATION_AT_LOGIN)) {
            	if(otpService.getOtp(user.getId()) == Integer.parseInt(request.getParameter("validateotp"))) {
            		otpService.clearOTP(user.getId());
            		userDetailsCacheService.clearUserDetails(user.getId());
            		authenticationDetailsRepository.updateLastOTPValidated(new Date(), user.getId());
            		return new CustomUserDetails(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles(), user.getCompany_id(), email),user.getCompany_id(),user.getFirstName(),user.getLastName(),user.getId(), request.getParameter("companyCode"), true);
            	}else {
            		return new CustomUserDetails(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles(), user.getCompany_id(), email),user.getCompany_id(),user.getFirstName(),user.getLastName(),user.getId(), request.getParameter("companyCode"), false);
            	}
        	}
            if((boolean)configuration.get(ICompanyConfigurationService.IS_OTP_VALIDATION_AT_LOGIN)) {
            	userDetailsCacheService.setOTPUserDetails(email, request.getParameter("j_password"), request.getParameter("companyCode"), user.getId());
            	return new CustomUserDetails(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles(), user.getCompany_id(), email),user.getCompany_id(),user.getFirstName(),user.getLastName(),user.getId(), request.getParameter("companyCode"), false);
            }
            return new CustomUserDetails(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles(), user.getCompany_id(), email),user.getCompany_id(),user.getFirstName(),user.getLastName(),user.getId(), request.getParameter("companyCode"), true);
        } catch (final Exception e) {
        	throw new UserNotFoundException(e);
        }
    }
	

    public final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles, Integer companyId, String email) throws Exception {
        return getGrantedAuthorities(getPrivileges(roles, companyId, email));
    }

    private final List<String> getPrivileges(final Collection<Role> roles, Integer companyId, String email) throws Exception {
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
        		if(companyId == CompanyConstant.COMPANY_CODE_SRS) {
        			privileges.add("OLD_RENEWAL");
        		}
        }
        return privileges;
    }

    private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
      
        return authorities;
    }
    

}
