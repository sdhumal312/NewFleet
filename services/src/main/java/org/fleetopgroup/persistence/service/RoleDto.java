package org.fleetopgroup.persistence.service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class RoleDto {
    @NotNull
    @Size(min = 1)
    private String roleName;

    /**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Role [roleName=").append(roleName).append("]");
        return builder.toString();
    }
}
