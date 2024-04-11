package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripSheetOptions;
import org.springframework.stereotype.Service;

@Service
public interface ITripSheetOptionsService {

	public List<TripSheetOptions> findAllByCompanyId(Integer companyId) throws Exception;

	public TripSheetOptions findBytripsheetextraname(String tripsheetextraname, Integer companyId) throws Exception;

	public TripSheetOptions get_TripSheet_Options_Id(Long tripsheetoptionsId, Integer companyId) throws Exception;
	
	public TripSheetOptions registerNewTripSheetOpionstype(TripSheetOptions accountDto) throws Exception;
	
	public void update_TripSheet_Options_Name(String tripsheetextraname, String tripsheetextradescription,  Long lastModifiedById, Date lastupdated, Long tripsheetoptionsId, Integer companyId) throws Exception;

	public void delete_Tripsheet_Options(Long tripsheetoptionsId, Integer companyId) throws Exception;

	public List<TripSheetOptions> listTripExtraOptions(Integer companyId) throws Exception;
	

}
