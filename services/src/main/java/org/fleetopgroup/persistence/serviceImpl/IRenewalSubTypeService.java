package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.RenewalSubTypeDto;
import org.fleetopgroup.persistence.model.RenewalSubType;
import org.fleetopgroup.web.util.ValueObject;

public interface IRenewalSubTypeService {

    RenewalSubType registerNewRenewalSubType(RenewalSubType accountDto) throws Exception;
    
    void updateRenewalSubType( String renewal_SubType, Integer renewal_Subid,Long modifiedBy, Timestamp modifiedOn, Integer companyId, Integer renewal_id) throws Exception;
    
    List<RenewalSubType> findAll();
    
    RenewalSubType getRenewalSubType(String verificationToken, Integer companyId);

    RenewalSubType getRenewalSubTypeById(Integer renewal_Subid, Integer companyId);

    void deleteRenewalSubType(Integer renewal_Subid, Integer companyId);

    RenewalSubTypeDto getRenewalSubTypeByID(Integer renewal_Subid, Integer companyId);

    long count();

  //  public List<RenewalSubType> listRenewalSubTypeSearch(String renewal_Type, Integer companyId) throws Exception;
    
    public List<RenewalSubType> listRenewalSubTypeSearch(Integer renewal_Type, Integer companyId) throws Exception;

	/**
	 * @return
	 */
	List<RenewalSubTypeDto> findAll_VehicleMandatory_Renewal_Sub_Type(Integer companyId);
	
	List<RenewalSubTypeDto> findAllByCompanyId(Integer companyId) throws Exception;
	
	//void updateRenewalType(String renewal_type, Integer renewal_id ,Integer companyId);
	
	public List<RenewalSubType> listRenewalSubTypeSearch(String term, Integer companyId) throws Exception;
	
	public ValueObject mobileRenewalSubTypeList(ValueObject object) throws Exception;
	
	public ValueObject saveRenewalSubTypeDetails(ValueObject object) throws Exception;
	
	public ValueObject getRenewalSubTypeList(ValueObject object) throws Exception;
	
	public ValueObject getRenewalSubTypeById(ValueObject object) throws Exception;
	
	public ValueObject updateRenewalSubTypeDetails(ValueObject object) throws Exception;
	
	public ValueObject deleteRenewalSubTypeById(ValueObject object) throws Exception;
}
