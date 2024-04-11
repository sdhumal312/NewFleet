/**
 * 
 */
package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 */
public class TyreExpensesDto {

	private Integer tyreExpenseId;

	private String tyreExpenseName;

	private String description;
	
	private String createdById;

	private String lastUpdatedBy;

	boolean markForDelete;

	private String creationDate;

	private String lastUpdatedOn;

	public TyreExpensesDto() {
		super();
		
	}

	public TyreExpensesDto(Integer tyreExpenseId, String tyreExpenseName, String description, String createdById,
			String lastUpdatedBy, boolean markForDelete, String creationDate, String lastUpdatedOn) {
		super();
		this.tyreExpenseId = tyreExpenseId;
		this.tyreExpenseName = tyreExpenseName;
		this.description = description;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.markForDelete = markForDelete;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Integer getTyreExpenseId() {
		return tyreExpenseId;
	}

	public void setTyreExpenseId(Integer tyreExpenseId) {
		this.tyreExpenseId = tyreExpenseId;
	}

	public String getTyreExpenseName() {
		return tyreExpenseName;
	}

	public void setTyreExpenseName(String tyreExpenseName) {
		this.tyreExpenseName = tyreExpenseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	@Override
	public String toString() {
		return "TyreExpensesDto [tyreExpenseId=" + tyreExpenseId + ", tyreExpenseName=" + tyreExpenseName
				+ ", description=" + description + ", createdById=" + createdById + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", markForDelete=" + markForDelete + ", creationDate=" + creationDate + ", lastUpdatedOn="
				+ lastUpdatedOn + "]";
	}

	
	
}
