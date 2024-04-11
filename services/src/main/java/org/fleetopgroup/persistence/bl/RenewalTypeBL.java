package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.RenewalSubTypeDto;
import org.fleetopgroup.persistence.dto.RenewalTypeDto;
import org.fleetopgroup.persistence.model.RenewalSubType;
import org.fleetopgroup.persistence.model.RenewalSubTypeHistory;
import org.fleetopgroup.persistence.model.RenewalType;
import org.fleetopgroup.persistence.model.RenewalTypeHistory;


public class RenewalTypeBL {

	public RenewalTypeBL() {
	}

	// Save the Renewal settings types
	public RenewalType prepareRenewalTypeModel(RenewalTypeDto renewalTypeDto) {

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		// create obj on driverDoctype
		RenewalType DocType = new RenewalType();
		
		DocType.setRenewal_id(renewalTypeDto.getRenewal_id());
		DocType.setRenewal_Type(renewalTypeDto.getRenewal_Type());
		DocType.setExpenseId(renewalTypeDto.getExpenseId());
		DocType.setCreatedOn(toDate);
		DocType.setLastModifiedOn(toDate);

		return DocType;
	}

	// would show the driver List of Document Driver
	public List<RenewalTypeDto> RenewalListofDto(List<RenewalType> renewalType) {

		List<RenewalTypeDto> Dtos = null;
		if (renewalType != null && !renewalType.isEmpty()) {
			Dtos = new ArrayList<RenewalTypeDto>();

			RenewalTypeDto Dto = null;
			for (RenewalType DriverDocType : renewalType) {
				Dto = new RenewalTypeDto();
				Dto.setRenewal_Type(DriverDocType.getRenewal_Type());
				Dto.setRenewal_id(DriverDocType.getRenewal_id());

				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the Driver document type
	public RenewalTypeDto prepareRenewalTypeDto(RenewalType DocType) {

		RenewalTypeDto Dto = new RenewalTypeDto();

		Dto.setRenewal_id(DocType.getRenewal_id());
		Dto.setRenewal_Type(DocType.getRenewal_Type());
		return Dto;
	}

	/***************************************************************************************************************	
	*/
	// Save the RenewalSub settings types
	public RenewalSubType prepareRenewalSubTypeModel(RenewalSubTypeDto renewalSubTypeDto) {
		

		// create obj on driverDoctype
		RenewalSubType DocType = new RenewalSubType();

		DocType.setRenewal_Subid(renewalSubTypeDto.getRenewal_Subid());
		DocType.setRenewal_SubType(renewalSubTypeDto.getRenewal_SubType());
		//DocType.setRenewal_Type(renewalSubTypeDto.getRenewal_Type());
		DocType.setRenewal_id(renewalSubTypeDto.getRenewal_id());
		
		return DocType;
	}

	// would show the Renewal Sub List of Document Driver
	public List<RenewalSubTypeDto> RenewalSubListofDto(List<RenewalSubTypeDto> renewalType) {

		List<RenewalSubTypeDto> Dtos = null;
		if (renewalType != null && !renewalType.isEmpty()) {
			Dtos = new ArrayList<RenewalSubTypeDto>();

			RenewalSubTypeDto Dto = null;

			for (RenewalSubTypeDto DriverDocType : renewalType) {

				Dto = new RenewalSubTypeDto();

				Dto.setRenewal_SubType(DriverDocType.getRenewal_SubType());
				Dto.setRenewal_Subid(DriverDocType.getRenewal_Subid());
				Dto.setRenewal_Type(DriverDocType.getRenewal_Type());
				Dto.setRenewal_id(DriverDocType.getRenewal_id());
				Dto.setCompanyId(DriverDocType.getCompanyId());
				
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the Driver document type
	public RenewalSubTypeDto prepareRenewalSubTypeDto(RenewalSubTypeDto DocType) {

		RenewalSubTypeDto Dto = new RenewalSubTypeDto();

		Dto.setRenewal_Subid(DocType.getRenewal_Subid());
		Dto.setRenewal_SubType(DocType.getRenewal_SubType());
		Dto.setRenewal_Type(DocType.getRenewal_Type());
		Dto.setRenewal_id(DocType.getRenewal_id());
		Dto.setMandatory(DocType.isMandatory());

		return Dto;
	}

	public RenewalTypeHistory prepareHistoryModel(RenewalType renewalType) {
		RenewalTypeHistory		renewalTypeHistory		= null;
		try {
			renewalTypeHistory	= new RenewalTypeHistory();
			
			renewalTypeHistory.setCompanyId(renewalType.getCompanyId());
			renewalTypeHistory.setLastModifiedById(renewalType.getLastModifiedById());
			renewalTypeHistory.setLastModifiedOn(renewalType.getLastModifiedOn());
			renewalTypeHistory.setMarkForDelete(renewalType.isMarkForDelete());
			renewalTypeHistory.setRenewal_id(renewalType.getRenewal_id());
			renewalTypeHistory.setRenewal_Type(renewalType.getRenewal_Type());
			renewalTypeHistory.setAllowToAvoid(renewalType.isAllowToAvoid());
			
			return renewalTypeHistory;
		} catch (Exception e) {
			throw e;
		} finally {
			renewalTypeHistory		= null;
		}
	}

	public RenewalSubTypeHistory prepareRenewalSubTypeHistoryModel(RenewalSubType renewalSubType) {
		RenewalSubTypeHistory		renewalSubTypeHistory		= null;
		try {
			renewalSubTypeHistory		= new RenewalSubTypeHistory();
			
			renewalSubTypeHistory.setCompanyId(renewalSubType.getCompanyId());
			renewalSubTypeHistory.setLastModifiedById(renewalSubType.getLastModifiedById());
			renewalSubTypeHistory.setLastModifiedOn(renewalSubType.getLastModifiedOn());
			renewalSubTypeHistory.setMarkForDelete(renewalSubType.isMarkForDelete());
			renewalSubTypeHistory.setRenewal_id(renewalSubType.getRenewal_id());
			renewalSubTypeHistory.setRenewal_Subid(renewalSubType.getRenewal_Subid());
			renewalSubTypeHistory.setRenewal_SubType(renewalSubType.getRenewal_SubType());
			renewalSubTypeHistory.setMandatory(renewalSubType.isMandatory());
			
			return renewalSubTypeHistory;
		} catch (Exception e) {
			throw e;
		} finally {
			renewalSubTypeHistory		= null;
		}
	}
}
