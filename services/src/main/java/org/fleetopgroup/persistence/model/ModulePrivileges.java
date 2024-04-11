package org.fleetopgroup.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ModulePrivileges")
public class ModulePrivileges {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="modulePrivilegesId")
	private Long	modulePrivilegesId;
	
	@Column(name="moduleName")
	private String	moduleName;
	
	@Column(name="displayName")
	private String	displayName;
	

	public Long getModulePrivilegesId() {
		return modulePrivilegesId;
	}

	public void setModulePrivilegesId(Long modulePrivilegesId) {
		this.modulePrivilegesId = modulePrivilegesId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ModulePrivileges [modulePrivilegesId=");
		builder.append(modulePrivilegesId);
		builder.append(", moduleName=");
		builder.append(moduleName);
		builder.append(", displayName=");
		builder.append(displayName);
		builder.append("]");
		return builder.toString();
	}

}
