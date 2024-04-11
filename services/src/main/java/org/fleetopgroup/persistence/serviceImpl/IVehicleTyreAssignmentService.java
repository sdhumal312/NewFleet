package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleTyreAssignmentService {

	public ValueObject getVehicleTyreLayoutPosition(ValueObject object)throws Exception;

	public ValueObject tyreAssignToVehicle(ValueObject object)throws Exception;

	public ValueObject validateTyreAssignToVehicle(ValueObject valueObject, ValueObject object) throws Exception;

	public ValueObject getTyreAssignedToVehicleDetails(ValueObject object)throws Exception;

	public ValueObject getTyreDetailsOfPosition(ValueObject object)throws Exception;

	public ValueObject tyreRemoveFromVehicle(ValueObject valueObject,ValueObject object)throws Exception;

	public ValueObject tyreRotation(ValueObject object)throws Exception;

	public ValueObject ownSpareAssignment(ValueObject valueObject) throws Exception;

	public List<VehicleTyreLayoutPositionDto> getTyreAssignmentByTransactionTypeAndTransactionId(short transactionType, Long transactionId,
			Integer company_id)throws Exception;

	public VehicleTyreLayoutPositionDto getTyreModelByPositon(Integer vid, String position, Integer company_id)throws Exception;

	public ValueObject singleTyreRemoveFromVehicle(ValueObject valueObject, ValueObject object) throws Exception;

	public List<VehicleTyreLayoutPositionDto> getAssignedTyreOfVehicle(ValueObject valueObject) throws Exception;

	public VehicleTyreLayoutPositionDto getTyreModelByLP_ID(Integer vid, Long LP_ID, Integer companyId) throws Exception;

	public List<VehicleTyreLayoutPositionDto> getVehicleTyreLayoutPositionList(ValueObject valueObject) throws Exception;


	/*public ValueObject flipTyre(ValueObject object)throws Exception;*/

}
