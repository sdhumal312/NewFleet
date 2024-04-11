package org.fleetopgroup.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RESPONSE")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RESPONSE {
	
	private String LINEERROR;
	
	private Integer CREATED;
	
	private Integer ALTERED;
	
	private Integer DELETED;
	
	private Integer LASTVCHID;
	
	private Integer LASTMID;
	
	private Integer COMBINED;
	
	private Integer IGNORED;
	
	private Integer ERRORS;
	
	private Integer CANCELLED;

	public String getLINEERROR() {
		return LINEERROR;
	}

	public void setLINEERROR(String lINEERROR) {
		
		if(LINEERROR != null) {
		LINEERROR += "\n"+ lINEERROR;
		} else {
		LINEERROR = lINEERROR;
		}
		
	}

	public Integer getCREATED() {
		return CREATED;
	}

	public void setCREATED(Integer cREATED) {
		CREATED = cREATED;
	}

	public Integer getALTERED() {
		return ALTERED;
	}

	public void setALTERED(Integer aLTERED) {
		ALTERED = aLTERED;
	}

	public Integer getDELETED() {
		return DELETED;
	}

	public void setDELETED(Integer dELETED) {
		DELETED = dELETED;
	}

	public Integer getLASTVCHID() {
		return LASTVCHID;
	}

	public void setLASTVCHID(Integer lASTVCHID) {
		LASTVCHID = lASTVCHID;
	}

	public Integer getLASTMID() {
		return LASTMID;
	}

	public void setLASTMID(Integer lASTMID) {
		LASTMID = lASTMID;
	}

	public Integer getCOMBINED() {
		return COMBINED;
	}

	public void setCOMBINED(Integer cOMBINED) {
		COMBINED = cOMBINED;
	}

	public Integer getIGNORED() {
		return IGNORED;
	}

	public void setIGNORED(Integer iGNORED) {
		IGNORED = iGNORED;
	}

	public Integer getERRORS() {
		return ERRORS;
	}

	public void setERRORS(Integer eRRORS) {
		ERRORS = eRRORS;
	}

	public Integer getCANCELLED() {
		return CANCELLED;
	}

	public void setCANCELLED(Integer cANCELLED) {
		CANCELLED = cANCELLED;
	}

	@Override
	public String toString() {
		return "RESPONSE [LINEERROR=" + LINEERROR + ", CREATED=" + CREATED + ", ALTERED=" + ALTERED + ", DELETED="
				+ DELETED + ", LASTVCHID=" + LASTVCHID + ", LASTMID=" + LASTMID + ", COMBINED=" + COMBINED
				+ ", IGNORED=" + IGNORED + ", ERRORS=" + ERRORS + ", CANCELLED=" + CANCELLED + "]";
	}
	
	
	
	
}
