package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.ReasonForRepairType;
import org.springframework.data.domain.Page;


public interface IReasonRepairTypeService {
	
	public ReasonForRepairType validateReasonRepairType(String Reason_Type, Integer companyId) throws Exception;

	public void addResonRepairType(ReasonForRepairType reasonType) throws Exception;
	
	public List<ReasonForRepairType> listReasonTypeByCompanyId(Integer companyId) throws Exception;
	
	public ReasonForRepairType getReasonType(int dtid, Integer companyId) throws Exception;
	
	public ReasonForRepairType validateReasonType(String Reason_Type, Integer companyId) throws Exception;

	public void updateReasonType(ReasonForRepairType DocType) throws Exception;
	
	public void deleteReasonType(Integer DocType, Integer companyid) throws Exception;
	
	public List<ReasonForRepairType> SearchOnlyReasonType(String term, Integer companyId) throws Exception;


}