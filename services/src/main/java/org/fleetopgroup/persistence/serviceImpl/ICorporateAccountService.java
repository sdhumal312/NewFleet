package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.CorporateAccount;
import org.fleetopgroup.web.util.ValueObject;

public interface ICorporateAccountService {

	List<CorporateAccount>  getPartyListByName(String term, Integer	companyId) throws Exception;

	public ValueObject savePartyMaster(ValueObject valueInObject)throws Exception;

	public ValueObject getAllPartyMaster()throws Exception;

	public ValueObject getPartyMasterByCorporateAccountId(ValueObject valueInObject)throws Exception;

	public ValueObject updatePartyMaster(ValueObject valueInObject)throws Exception;

	public ValueObject deletePartyMaster(ValueObject valueInObject)throws Exception;
	
	public ValueObject mobilePartyNameList(ValueObject object) throws Exception;

}
