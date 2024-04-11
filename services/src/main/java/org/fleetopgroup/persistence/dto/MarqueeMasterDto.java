package org.fleetopgroup.persistence.dto;

import java.util.Date;

/**
 * @author fleetop
 *
 *
 *
 */

public class MarqueeMasterDto {

	private Long marquee_id;
	
	private String marquee_message;
	
	private Integer companyId;
	
	private String companyName;

	private Long createdById;

	private Long lastModifiedById;

	private String createdDate;

	private String lastupdatedDate;
	
	private String color_Id; 
	
	private Long StockCount; 
	
	public Long getStockCount() {
		return StockCount;
	}

	public void setStockCount(Long stockCount) {
		StockCount = stockCount;
	}

	boolean markForDelete;

	public Long getMarquee_id() {
		return marquee_id;
	}

	public void setMarquee_id(Long marquee_id) {
		this.marquee_id = marquee_id;
	}

	public String getMarquee_message() {
		return marquee_message;
	}

	public void setMarquee_message(String marquee_message) {
		this.marquee_message = marquee_message;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastupdatedDate() {
		return lastupdatedDate;
	}

	public void setLastupdatedDate(String lastupdatedDate) {
		this.lastupdatedDate = lastupdatedDate;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "MarqueeMasterDto [marquee_id=" + marquee_id + ", marquee_message=" + marquee_message + ", companyId="
				+ companyId + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", createdDate=" + createdDate + ", lastupdatedDate=" + lastupdatedDate + ", markForDelete="
				+ markForDelete + "]";
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getColor_Id() {
		return color_Id;
	}

	public void setColor_Id(String color_Id) {
		this.color_Id = color_Id;
	}
	
	
}