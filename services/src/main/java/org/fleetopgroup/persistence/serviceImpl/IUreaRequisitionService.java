package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.UreaRequisitionDto;
import org.fleetopgroup.persistence.dto.UreaTransferDetailsDto;
import org.fleetopgroup.web.util.ValueObject;

public interface IUreaRequisitionService {

	public ValueObject sendUreaRequisition(ValueObject valueInObject) throws Exception;

	public ValueObject getRequisitionAndTransferListByStatus(ValueObject valueInObject)throws Exception;

	public List<UreaRequisitionDto> getRequisitionList(ValueObject valueInObject) throws Exception;

	public ValueObject getYourRequisitionAndTransferListByStatus(ValueObject valueInObject)throws Exception;

	public ValueObject getRequisitionDetailById(ValueObject valueInObject)throws Exception;

	public ValueObject approveRequisition(ValueObject valueInObject)throws Exception;

	public ValueObject saveTransfer(ValueObject valueInObject)throws Exception;

	public ValueObject getUreaTransferByRequisitionId(ValueObject valueInObject)throws Exception;

	public ValueObject getUreaTransferDetails(ValueObject valueInObject)throws Exception;

	public List<UreaTransferDetailsDto> getUreaTransferDetailsByTransferId(ValueObject valueInObject) throws Exception;

	public ValueObject removeTransfer(ValueObject valueInObject)throws Exception;

	public ValueObject completeTransfer(ValueObject valueInObject)throws Exception;

	public ValueObject received_Reject_Urea(ValueObject valueInObject)throws Exception;

	public ValueObject getUreaTransferByTransferId(ValueObject valueInObject)throws Exception;

	public ValueObject deleteUreaRequisition(ValueObject valueInObject)throws Exception;

	public ValueObject validateSendUreaRequisition(ValueObject valueInObject) throws Exception;

	public ValueObject validateApprovedRequisition(ValueObject valueObject) throws Exception;

	public ValueObject validateSaveTransfer(ValueObject valueObject) throws Exception;

	public ValueObject validateCompleteTransfer(ValueObject valueObject) throws Exception;

	public ValueObject validateReceive_RejectUrea(ValueObject valueObject) throws Exception;

//	public List<UreaTransferDto> getRequisitionListByStatus(ValueObject valueInObject) throws Exception;


}
