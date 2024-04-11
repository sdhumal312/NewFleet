package org.fleetopgroup.persistence.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LaundryClothReceiveHistory")
public class LaundryClothReceiveHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="clothReceiveHistoryId")
	private Long	clothReceiveHistoryId;
	
	@Column(name="laundryInvoiceId")
	private Long	laundryInvoiceId;
	
	@Column(name="laundryClothDetailsId")
	private Long	laundryClothDetailsId;
	
	@Column(name="receiveQuantity")
	private Double	receiveQuantity;
	
	@Column(name="description")
	private String	description;
	
	@Column(name="receiveById")
	private Long	receiveById;
	
	@Column(name="clothTypesId")
	private Long	clothTypesId;
	
	@Column(name="receiveDate")
	private Timestamp	receiveDate;
	
	@Column(name="systemDateTime")
	private Timestamp	systemDateTime;
	
	@Column(name="companyId")
	private Integer		companyId;
	
	@Column(name="markForDelete")
	private boolean		markForDelete;
	
	@Column(name="receiveTypeId")
	private short	receiveTypeId;
	
	@Column(name = "losedQuantity")
	private Double		losedQuantity;
	
	@Column(name = "damagedQuantity")
	private Double		damagedQuantity;
	

	public Long getClothReceiveHistoryId() {
		return clothReceiveHistoryId;
	}

	public void setClothReceiveHistoryId(Long clothReceiveHistoryId) {
		this.clothReceiveHistoryId = clothReceiveHistoryId;
	}

	public Long getLaundryInvoiceId() {
		return laundryInvoiceId;
	}

	public void setLaundryInvoiceId(Long laundryInvoiceId) {
		this.laundryInvoiceId = laundryInvoiceId;
	}

	public Long getLaundryClothDetailsId() {
		return laundryClothDetailsId;
	}

	public void setLaundryClothDetailsId(Long laundryClothDetailsId) {
		this.laundryClothDetailsId = laundryClothDetailsId;
	}

	public Double getReceiveQuantity() {
		return receiveQuantity;
	}

	public void setReceiveQuantity(Double receiveQuantity) {
		this.receiveQuantity = receiveQuantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getReceiveById() {
		return receiveById;
	}

	public void setReceiveById(Long receiveById) {
		this.receiveById = receiveById;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Timestamp getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Timestamp receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Timestamp getSystemDateTime() {
		return systemDateTime;
	}

	public void setSystemDateTime(Timestamp systemDateTime) {
		this.systemDateTime = systemDateTime;
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

	public short getReceiveTypeId() {
		return receiveTypeId;
	}

	public void setReceiveTypeId(short receiveTypeId) {
		this.receiveTypeId = receiveTypeId;
	}

	public Double getLosedQuantity() {
		return losedQuantity;
	}

	public void setLosedQuantity(Double losedQuantity) {
		this.losedQuantity = losedQuantity;
	}

	public Double getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(Double damagedQuantity) {
		this.damagedQuantity = damagedQuantity;
	}
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LaundryClothReceiveHistory [clothReceiveHistoryId=");
		builder.append(clothReceiveHistoryId);
		builder.append(", laundryInvoiceId=");
		builder.append(laundryInvoiceId);
		builder.append(", laundryClothDetailsId=");
		builder.append(laundryClothDetailsId);
		builder.append(", receiveQuantity=");
		builder.append(receiveQuantity);
		builder.append(", description=");
		builder.append(description);
		builder.append(", receiveById=");
		builder.append(receiveById);
		builder.append(", clothTypesId=");
		builder.append(clothTypesId);
		builder.append(", receiveDate=");
		builder.append(receiveDate);
		builder.append(", systemDateTime="); 
		builder.append(systemDateTime);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", receiveTypeId=");
		builder.append(receiveTypeId);
		builder.append(", damagedQuantity=");
		builder.append(damagedQuantity);
		builder.append(", losedQuantity=");
		builder.append(losedQuantity);
		builder.append("]");
		return builder.toString();
	}

	
	
}
