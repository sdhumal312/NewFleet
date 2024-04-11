package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.BatteryTransferDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.BatteryTransfer;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IBatteryTransferService {

	public void registerTransfer(BatteryTransfer batteryTransfer);
	
	public List<BatteryTransferDto>  getBatteryTransferListToReceive(CustomUserDetails	userDetails) throws Exception;
	
	BatteryTransfer	getBatteryTransfer(Long transferId, Integer companyId) throws Exception;
	
	public void Update_BatteryTransfer_ReceivedBy_Details(Timestamp transfer_receiveddate,
			String transfer_receivedReason, Long lASTMODIFIEDBY, Timestamp transfer_receiveddate2, Long ittid, Integer companyid);

	/*
	 * Get Transfered Battery Details
	 */
	public ValueObject getBatteryTransferReport(ValueObject valueObject) throws Exception;
	
}
