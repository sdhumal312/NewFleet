package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.TyreUsageHistoryDto;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface ITyreUsageHistoryService {

	public ValueObject saveTyreMountUsageHistory(ValueObject valueObject,ValueObject object) throws Exception;

	public ValueObject saveTyreDismountUsageHistory(ValueObject valueObject, ValueObject object) throws Exception;

	public ValueObject saveTyreRotateFromUsageHistory(ValueObject valueObject, ValueObject object)throws Exception;

	public ValueObject saveTyreRotateToUsageHistory(ValueObject valueObject, ValueObject object)throws Exception;

	public void saveTripTyreUsageHistory(ValueObject object)throws Exception;

	public void saveFuelTyreUsageHistory(ValueObject valueObject)throws Exception;

	public void saveDSETyreUsageHistory(ValueObject valueObject)throws Exception;

	public void saveUreaTyreUsageHistory(ValueObject valueObject)throws Exception;

	public void saveIssueTyreUsageHistory(ValueObject valueObject) throws Exception;

	public void saveWOTyreUsageHistory(ValueObject valueObject) throws Exception;

	public List<TyreUsageHistoryDto> getTyreUsageHistory(Long tyre_id, Integer company_id) throws Exception;


	
}
