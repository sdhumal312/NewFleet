package org.fleetopgroup.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CorporateAccount")
public class CorporateAccount {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="corporateAccountId")
	private Long	corporateAccountId;
	
	private String	corporateName;
	
	private String	address;
	
	private String	mobileNumber;
	
	private String	alternateMobileNumber;
	
	private String	gstNumber;
	
	private Double	perKMRate;
	
	private Double	balanceAmount;
	
	private Integer	companyId;
	
	private boolean	markForDelete;

	public Long getCorporateAccountId() {
		return corporateAccountId;
	}

	public void setCorporateAccountId(Long corporateAccountId) {
		this.corporateAccountId = corporateAccountId;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(String alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public Double getPerKMRate() {
		return perKMRate;
	}

	public void setPerKMRate(Double perKMRate) {
		this.perKMRate = perKMRate;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((corporateAccountId == null) ? 0 : corporateAccountId.hashCode());
		result = prime * result + ((corporateName == null) ? 0 : corporateName.hashCode());
		result = prime * result + ((gstNumber == null) ? 0 : gstNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorporateAccount other = (CorporateAccount) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (corporateAccountId == null) {
			if (other.corporateAccountId != null)
				return false;
		} else if (!corporateAccountId.equals(other.corporateAccountId))
			return false;
		if (corporateName == null) {
			if (other.corporateName != null)
				return false;
		} else if (!corporateName.equals(other.corporateName))
			return false;
		if (gstNumber == null) {
			if (other.gstNumber != null)
				return false;
		} else if (!gstNumber.equals(other.gstNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CorporateAccount [corporateAccountId=");
		builder.append(corporateAccountId);
		builder.append(", corporateName=");
		builder.append(corporateName);
		builder.append(", address=");
		builder.append(address);
		builder.append(", mobileNumber=");
		builder.append(mobileNumber);
		builder.append(", alternateMobileNumber=");
		builder.append(alternateMobileNumber);
		builder.append(", gstNumber=");
		builder.append(gstNumber);
		builder.append(", perKMRate=");
		builder.append(perKMRate);
		builder.append(", balanceAmount=");
		builder.append(balanceAmount);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}
	
	
}
