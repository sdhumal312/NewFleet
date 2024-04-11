package org.fleetopgroup.persistence.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{
	
	
	private static final long serialVersionUID = 1L;
	
	private	long Id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private boolean enabled;

    private boolean tokenExpired;
    
    private Integer company_id;
    
    private String companyCode;
    
    private boolean	isOTPValidated;
    
    private boolean	isTwoFactorLogin;
    
    
    private Collection<GrantedAuthority> 	grantedAuthoritiesList = new ArrayList<>();
    private List<VehicleGroupPermissionDto> vehicleGroupPermission		= new ArrayList<>();
    
    
    public CustomUserDetails() {
		super();
	}
    
    public CustomUserDetails(Integer companyId, Long userId) {
		super();
		this.company_id = companyId;
		if(userId != null) {
			this.Id = userId;
		}
	}
    
    public CustomUserDetails(Integer companyId, Long userId, String email) {
		super();
		this.company_id = companyId;
		if(userId != null) {
			this.Id = userId;
		}
		this.email = email;
	}
    
    
@SuppressWarnings("unchecked")
public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
		boolean credentialsNonExpired, boolean accountNonLocked,
		Collection<? extends GrantedAuthority> authorities,Integer company_Id,String firstname,String lastName,long userId, List<VehicleGroupPermissionDto> vehicleGroupPermission) {
			this.email	 	= username;
			this.password 	= password;
			this.enabled 	= enabled;
			this.company_id = company_Id;
			this.firstName	= firstname;
			this.lastName	= lastName;
			this.Id			= userId;
			this.grantedAuthoritiesList	= (Collection<GrantedAuthority>) authorities;
			this.vehicleGroupPermission	= vehicleGroupPermission;
}
    

@SuppressWarnings("unchecked")
public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired,
		boolean credentialsNonExpired, boolean accountNonLocked,
		Collection<? extends GrantedAuthority> authorities,Integer company_Id,String firstname,String lastName,long userId, String getCompanyCode, boolean isOTPValidated) {
			this.email	 	= username;
			this.password 	= password;
			this.enabled 	= enabled;
			this.company_id = company_Id;
			this.firstName	= firstname;
			this.lastName	= lastName;
			this.Id			= userId;
			this.grantedAuthoritiesList	= (Collection<GrantedAuthority>) authorities;
			this.companyCode = getCompanyCode;
			this.isOTPValidated = isOTPValidated;
}




	public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
		return grantedAuthoritiesList;
	}

	public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
		this.grantedAuthoritiesList = grantedAuthoritiesList;
	}

	

	public long getId() {
		return Id;
	}


	public void setId(long id) {
		Id = id;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthoritiesList;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	public Integer getCompany_id() {
		return company_id;
	}


	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}


	public String getCompanyCode() {
		return companyCode;
	}


	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}


	public Collection<VehicleGroupPermissionDto> getVehicleGroupPermission() {
		return vehicleGroupPermission;
	}


	public void setVehicleGroupPermission(List<VehicleGroupPermissionDto> vehicleGroupPermission) {
		this.vehicleGroupPermission = vehicleGroupPermission;
	}


	public boolean isOTPValidated() {
		return isOTPValidated;
	}


	public void setOTPValidated(boolean isOTPValidated) {
		this.isOTPValidated = isOTPValidated;
	}


	public boolean isTwoFactorLogin() {
		return isTwoFactorLogin;
	}


	public void setTwoFactorLogin(boolean isTwoFactorLogin) {
		this.isTwoFactorLogin = isTwoFactorLogin;
	}


}
