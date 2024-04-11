package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.RenewalTypeDto;
import org.fleetopgroup.persistence.model.RenewalType;
import org.fleetopgroup.web.util.ValueObject;

public interface IRenewalTypeService {

    RenewalType registerNewRenewalType(RenewalType accountDto) throws Exception;
    
    void updateRenewalType(RenewalType DocType) throws Exception;
    
    List<RenewalType> findAll();
    
    List<RenewalType> getRenewalType(String verificationToken, Integer companyId);

    void deleteRenewalType(Integer renewal_id, Integer companyId);

    RenewalType getRenewalTypeByID(Integer renewal_id);

    long count();

	/**
	 * @return
	 */
	List<RenewalType> findAll_VehicleMandatory_Renewal_Type(Integer companyId);
	
	List<RenewalType> findAllByCompanyId(Integer companyId) throws Exception;
	
	List<RenewalTypeDto> findAllListByCompanyId(Integer companyId) throws Exception;
	
	public List<RenewalType> mobileRenewalTypeList(ValueObject object) throws Exception;
	
	List<RenewalType> findAllRenewalForComparision(Integer companyId) throws Exception;
	
	public RenewalTypeDto getRenewalTypeByID(Integer renewal_id, Integer companyId) throws Exception;
	
	public ValueObject getRenewalTypeList(ValueObject object) throws Exception;
	
	public ValueObject saveRenewalTypeList(ValueObject object) throws Exception;
	
	public ValueObject getRenewalTypeById(ValueObject object) throws Exception;
	
	public ValueObject updateRenewalType(ValueObject object) throws Exception;
	
	public ValueObject deleteRenewalTypeById(ValueObject object) throws Exception;
	
}
