package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.ClothInventoryDetailsDto;
import org.fleetopgroup.persistence.dto.ClothInventoryStockTypeDetailsDto;
import org.fleetopgroup.persistence.dto.InventoryDamageAndLostHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryUpholsteryTransferDto;
import org.fleetopgroup.persistence.dto.LaundryClothReceiveHistoryDto;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryHistoryDto;
import org.fleetopgroup.persistence.model.VehicleClothInventoryHistory;
import org.fleetopgroup.persistence.dto.UpholsterySendLaundryInvoiceDto;


public interface IUpholsteryReportDao {
	
	public List<ClothInventoryDetailsDto> Upholstery_Purchase_Invoice_Report_List(String Query, Integer companyId) throws Exception;

	public List<LaundryClothReceiveHistoryDto> getLaundry_Upholstery_Receive_Report(String query, Integer company_id) throws Exception;

	public List<ClothInventoryStockTypeDetailsDto> getUpholstery_Stock_Report(String query, Integer company_id) throws Exception;

	public List<VehicleClothInventoryHistoryDto> getUpholstery_Assignment_Report(String query, Integer company_id) throws Exception;

	public List<InventoryUpholsteryTransferDto> getUpholstery_Stock_Transfer_Report(String query, Integer company_id) throws Exception;

	public List<UpholsterySendLaundryInvoiceDto> getUpholstery_Sent_ToLaundryReport(String query, Integer company_id) throws Exception;

	public List<InventoryDamageAndLostHistoryDto> getUpholsteryLossReport(String query, Integer company_id) throws Exception; 
	
}