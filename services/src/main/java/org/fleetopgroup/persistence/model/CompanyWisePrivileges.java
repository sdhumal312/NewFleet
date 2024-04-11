package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CompanyWisePrivileges")
public class CompanyWisePrivileges implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="companyWisePrivilegesId")
	private Long	companyWisePrivilegesId;
	
	@Column(name="priviledgeId")
	private Long	priviledgeId;
	
	@Column(name="priviledgeName")
	private String	priviledgeName;
	
	@Column(name="priviledgeType")
	private short	priviledgeType;
	
	@Column(name="mainPrivilegeId")
	private Long	mainPrivilegeId;
	
	@Column(name="companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name="markForDelete", nullable = false)
	private	boolean markForDelete;

	public Long getCompanyWisePrivilegesId() {
		return companyWisePrivilegesId;
	}

	public void setCompanyWisePrivilegesId(Long companyWisePrivilegesId) {
		this.companyWisePrivilegesId = companyWisePrivilegesId;
	}

	public Long getPriviledgeId() {
		return priviledgeId;
	}

	public void setPriviledgeId(Long priviledgeId) {
		this.priviledgeId = priviledgeId;
	}

	public String getPriviledgeName() {
		return priviledgeName;
	}

	public void setPriviledgeName(String priviledgeName) {
		this.priviledgeName = priviledgeName;
	}

	public short getPriviledgeType() {
		return priviledgeType;
	}

	public void setPriviledgeType(short priviledgeType) {
		this.priviledgeType = priviledgeType;
	}

	public Long getMainPrivilegeId() {
		return mainPrivilegeId;
	}

	public void setMainPrivilegeId(Long mainPrivilegeId) {
		this.mainPrivilegeId = mainPrivilegeId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompanyWisePrivileges [companyWisePrivilegesId=");
		builder.append(companyWisePrivilegesId);
		builder.append(", priviledgeId=");
		builder.append(priviledgeId);
		builder.append(", priviledgeName=");
		builder.append(priviledgeName);
		builder.append(", priviledgeType=");
		builder.append(priviledgeType);
		builder.append(", mainPrivilegeId=");
		builder.append(mainPrivilegeId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	
}
