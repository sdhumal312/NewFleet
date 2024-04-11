/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.SubCompany;
import org.fleetopgroup.web.util.ValueObject;

/**
 * @author fleetop
 *
 */
public class SubCompanyBL {
	

	public SubCompanyBL() {
		super();
	}

	public SubCompany prepareSubCompany(ValueObject valueObject, CustomUserDetails userDetails ) throws Exception {
		try {
			Date 		currentDateUpdate 	= new Date();                                             
			Timestamp 	toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    
			SubCompany 	subCompany 			= new SubCompany();

			subCompany.setSubCompanyId(valueObject.getLong("subCompanyId",0));
			subCompany.setSubCompanyName(valueObject.getString("subCompanyName"));
			subCompany.setSubCompanyAddress(valueObject.getString("subCompanyAddress"));
			subCompany.setSubCompanyCity(valueObject.getString("subCompanyCity"));
			subCompany.setSubCompanyState(valueObject.getString("subCompanyState"));
			subCompany.setSubCompanyCountry(valueObject.getString("subCompanyCountry"));
			subCompany.setSubCompanyPinCode(valueObject.getString("subCompanyPinCode"));
			subCompany.setSubCompanyWebsite(valueObject.getString("subCompanyWebsite"));
			subCompany.setSubCompanyEmail(valueObject.getString("subCompanyEmail"));
			subCompany.setSubCompanyMobileNumber(valueObject.getString("subCompanyMobileNumber"));
			subCompany.setSubCompanyType(valueObject.getString("subCompanyType"));
			subCompany.setSubCompanyPanNo(valueObject.getString("subCompanyPanNo"));
			subCompany.setSubCompanyTanNo(valueObject.getString("subCompanyTanNo"));
			subCompany.setSubCompanyTaxNo(valueObject.getString("subCompanyTaxNo"));
			subCompany.setSubCompanyTinNo(valueObject.getString("subCompanyTinNo"));
			subCompany.setSubCompanyCinNo(valueObject.getString("subCompanyCinNo"));			
			subCompany.setSubCompanyAbout(valueObject.getString("subCompanyAbout"));
			subCompany.setCreatedById(userDetails.getId());
			subCompany.setLastUpdatedBy(userDetails.getId());
			subCompany.setCreationDate(toDate);
			subCompany.setLastUpdatedOn(toDate);
			subCompany.setCompanyId(userDetails.getCompany_id());
			subCompany.setMarkForDelete(false);

			return subCompany;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
}
