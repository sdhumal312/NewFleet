package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.RepairStockDto;
import org.fleetopgroup.persistence.dto.RepairStockToPartDetailsDto;
import org.fleetopgroup.persistence.dto.RepairToLabourDetailsDto;
import org.fleetopgroup.persistence.dto.RepairToStockDetailsDto;
import org.fleetopgroup.persistence.model.RepairStock;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IRepairService {

	public ValueObject saveRepairStockInvoice(ValueObject valueOutObject)throws Exception;

	public ValueObject getRepairInvoice(ValueObject valueOutObject)throws Exception;

	public RepairStockDto getRepairInvoiceById(ValueObject valueObject) throws Exception;

	public List<RepairToStockDetailsDto> getRepairToStockDetailsById(ValueObject valueObject) throws Exception;

	public ValueObject getDismountTyreDetails(ValueObject valueOutObject) throws Exception;

	public ValueObject getDismountBatteryDetails(ValueObject valueObject) throws Exception;

	public ValueObject saveRepairToStockDetails(ValueObject valueObject)throws Exception;

	public ValueObject getRepairToStockDetails(ValueObject valueObject) throws Exception;

	public ValueObject getRepairStockList(ValueObject valueOutObject)throws Exception;

	public List<RepairStockDto> getRepairListByStatusId(ValueObject valueObject) throws Exception;

	public RepairStock getRepairInvoiceByRepairStockId(Long repairStockId, Integer companyId)throws Exception;

	public ValueObject deleteRepairInvoice(ValueObject valueOutObject)throws Exception;

	public ValueObject saveRepairStockToPartDetails(ValueObject valueObject)throws Exception;

	public ValueObject saveRepairToLabourDetails(ValueObject valueObject) throws Exception;

	public ValueObject getRepairStockToPartDetails(ValueObject valueObject) throws Exception;
	
	public List<RepairStockToPartDetailsDto> getRepairStockToPartDetailsList(ValueObject valueObject) throws Exception;

	public ValueObject getRepairStockToLabourDetails(ValueObject valueObject) throws Exception;
	
	public List<RepairToLabourDetailsDto> getRepairStockToLabourDetailsList(ValueObject valueObject) throws Exception;

	public ValueObject getRepairStockToPartAndLabourDetails(ValueObject valueObject) throws Exception;

	public ValueObject removeAdditionalPart(ValueObject valueOutObject)throws Exception;

	public ValueObject removeLabour(ValueObject valueOutObject)throws Exception;

	public ValueObject getDefaultRepairStockToLabourDetails(ValueObject valueObject) throws Exception;

	public List<RepairToLabourDetailsDto> getDefaultRepairStockToLabourDetailsList(ValueObject valueObject) throws Exception;

	public ValueObject moveToCreatedRepairInvoice(ValueObject valueOutObject)throws Exception;

	public ValueObject completeRepair(ValueObject valueObject) throws Exception;

	public ValueObject reopenRepair(ValueObject valueObject) throws Exception;

	public ValueObject rejectStock(ValueObject valueOutObject)throws Exception;

	public ValueObject searchRepairByNumber(ValueObject valueObject) throws Exception;

	public ValueObject tyreMoveToScrap(ValueObject valueObject) throws Exception;

	public ValueObject tyreMoveToRetread(ValueObject valueObject) throws Exception;

	public ValueObject batteryMoveToScrap(ValueObject valueObject) throws Exception;

	public ValueObject getAssetNumber(ValueObject valueOutObject)throws Exception;

	public ValueObject sentAssetNumber(ValueObject valueOutObject)throws Exception;

	public ValueObject removeAsset(ValueObject valueObject)throws Exception;

	public ValueObject getAssetNumberForAdditionalPart(ValueObject valueObject)throws Exception;

	public ValueObject saveAdditionalAsset(ValueObject valueObject)throws Exception;

	public ValueObject removeAdditionalAsset(ValueObject valueObject)throws Exception;

	public ValueObject getRepairStockReportData(ValueObject valueObject)throws Exception;

	public ValueObject getAdditionalPartDetails(ValueObject valueObject)throws Exception;

	public ValueObject getAdditionalLabourDetails(ValueObject valueObject)throws Exception;


	
}
