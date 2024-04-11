package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.SentLaundryClothDetailsDto;
import org.fleetopgroup.web.util.ValueObject;

public interface ISentLaundryClothDetailsService {

	List<SentLaundryClothDetailsDto>  getSentLaundryClothDetailsDto(Long invoiceId, Integer companyId) throws Exception;
	
	public void updateSentLaundryDetailsToReceive(ValueObject valueObject) throws Exception;
	
	public void updateSentLaundryDetailsToDamage(ValueObject valueObject) throws Exception;
	
	public void updateSentLaundryDetailsToLost(ValueObject valueObject) throws Exception;
	
	public SentLaundryClothDetailsDto getDamageWashingQtyDetails(Long clothTypesId, Integer companyId) throws Exception;
}
