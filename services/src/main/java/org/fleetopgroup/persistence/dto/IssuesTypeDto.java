package org.fleetopgroup.persistence.dto;

public class IssuesTypeDto {
	
	private	short 		issueTypeId;
	private String 		issueTypeName;
	
	
	public IssuesTypeDto() {
		super();
	}
	
	public IssuesTypeDto(short issueTypeId, String issueTypeName ) {
		this.issueTypeId	 = issueTypeId;
		this.issueTypeName   = issueTypeName;
	}
	
	public short getIssueTypeId() {
		return issueTypeId;
	}
	public void setIssueTypeId(short issueTypeId) {
		this.issueTypeId = issueTypeId;
	}
	public String getIssueTypeName() {
		return issueTypeName;
	}
	public void setIssueTypeName(String issueTypeName) {
		this.issueTypeName = issueTypeName;
	}
	
	@Override
	public String toString() {
		return "IssuesTypeDto [issueTypeId=" + issueTypeId + ", issueTypeName=" + issueTypeName + "]";
	}
	
	
}
