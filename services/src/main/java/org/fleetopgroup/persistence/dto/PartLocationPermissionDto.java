package org.fleetopgroup.persistence.dto;

public class PartLocationPermissionDto {

	private Long	 id;
	
	private long 	partLocationId;
	
	private String 	partLocationName;
	
	private long		user_Id;
	
	private Integer	companyId;
	
	private boolean	markForDelete;
	
	
	public PartLocationPermissionDto() {
		super();
	}

	public PartLocationPermissionDto(long partLocationId, long user_Id, Integer companyId) {
		super();
		this.partLocationId = partLocationId;
		this.user_Id = user_Id;
		this.companyId = companyId;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getPartLocationId() {
		return partLocationId;
	}

	public void setPartLocationId(long partLocationId) {
		this.partLocationId = partLocationId;
	}
	
	

	public String getPartLocationName() {
		return partLocationName;
	}

	public void setPartLocationName(String partLocationName) {
		this.partLocationName = partLocationName;
	}

	public long getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(long user_Id) {
		this.user_Id = user_Id;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + (int) (partLocationId ^ (partLocationId >>> 32));
		result = prime * result + (int) (user_Id ^ (user_Id >>> 32));
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
		PartLocationPermissionDto other = (PartLocationPermissionDto) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (partLocationId != other.partLocationId)
			return false;
		if (user_Id != other.user_Id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PartLocationPermissionDto [id=");
		builder.append(id);
		builder.append(", partLocationId=");
		builder.append(partLocationId);
		builder.append(", partLocationName=");
		builder.append(partLocationName);
		builder.append(", user_Id=");
		builder.append(user_Id);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	
}
