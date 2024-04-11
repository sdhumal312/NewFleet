package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.CashBookNameDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CashBookName;


public interface ICashBookNameService {

	public void add_CashBookName(CashBookName status) throws Exception;

	public void update_CashBookName(CashBookName status) throws Exception;

	public List<CashBookName> list_CashBookName() throws Exception;

	public CashBookName get_CashBookName(int nameID) throws Exception;
	
	public CashBookNameDto get_CashBookName(int nameID, Integer companyId) throws Exception;

	public void delete_CashBookName(Integer status, Integer companyId) throws Exception;

	public List<CashBookName> listOnly_CashBookName() throws Exception;
	
	public CashBookName Validate_CashBookName(String cashNAME, String THREEWORKD, Integer companyId, long vehicleGroup) throws Exception;

	public List<CashBookName> SearchCashBookName_DropDown(String cashNAME) throws Exception;
	
	public List<CashBookNameDto> list_CashBookName(Integer companyId) throws Exception;
	
	public List<CashBookName> list_CashBookNameByPermission(CustomUserDetails userDetails) throws Exception;
	
	public CashBookName getCashBookByName(String cashNAME, Integer companyId) throws Exception;
	
	public CashBookName getCashBookByVehicleGroupId(long vehicleGroupID, Integer companyId) throws Exception;
	
	List<CashBookName> list_cashBookByCompanyId(Integer companyId)throws Exception;

	public CashBookName get_CashBookName2(int cash_BOOK_ID, Integer company_ID) throws Exception;
}