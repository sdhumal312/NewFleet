package org.fleetopgroup.persistence.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class UserDto {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private boolean enabled;

    private boolean tokenExpired;

    private long company_Id;
    
    
    
    public long getCompany_Id() {
		return company_Id;
	}


	public void setCompany_Id(long company_Id) {
		this.company_Id = company_Id;
	}


	private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
    
    
    //

    public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
		return grantedAuthoritiesList;
	}


	public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
		this.grantedAuthoritiesList = grantedAuthoritiesList;
	}


	public UserDto() {
        super();
        this.enabled = false;
        this.tokenExpired = false;
    }

   
    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserDto user = (UserDto) obj;
        if (!email.equals(user.email)) {
            return false;
        }
        return true;
    }


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserDto [id=");
		builder.append(id);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", password=");
		builder.append(password);
		builder.append(", enabled=");
		builder.append(enabled);
		builder.append(", tokenExpired=");
		builder.append(tokenExpired);
		builder.append("]");
		return builder.toString();
	}

   

}