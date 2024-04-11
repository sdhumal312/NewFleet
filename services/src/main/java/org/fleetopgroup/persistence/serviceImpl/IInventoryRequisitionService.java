package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.InventoryRequisitionDto;
import org.fleetopgroup.persistence.dto.InventoryRequisitionPartsDto;
import org.fleetopgroup.persistence.model.InventoryRequisition;
import org.fleetopgroup.persistence.model.InventoryRequisitionParts;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IInventoryRequisitionService {

	// save Inventory Requisition
	public void add_InventoryRequisition(InventoryRequisition Requisition) throws Exception;

	
	// save Inventory Requisition
	public void add_InventoryRequisitionPart(InventoryRequisitionParts Requisition) throws Exception;


	public Page<InventoryRequisition> getDeployment_Page_InventoryRequisition(short Status,Integer pageNumber, Integer CompanyID);


	public List<InventoryRequisitionDto> list_Of_All_Inventory_Requisition(short Status,Integer pageNumber, Integer CompanyID);

	public List<InventoryRequisitionDto> Search_Of_All_Inventory_Requisition(Long INVRID_NUMBER, Integer CompanyID);


	public InventoryRequisitionDto GET_Inventory_Requisition_ID(Long iNVRID, Integer Company_id);


	public InventoryRequisitionParts GET_Inventory_Requisition_Part_ID(Long iNVRID, Integer Company_id);


	public List<InventoryRequisitionPartsDto> LIST_Inventory_Requisition_Part_ID(Long iNVRID, Integer Company_id);


	public void DELETE_InventoryRequisitionParts_MARK(Long iNVRID, Integer Company_id);


	public void Update_InventoryRequisition_SENT(short Status,  String remarks, Long updateBY, Timestamp update_Date,
			Long iNVRID, Integer Company_id);


	public InventoryRequisitionPartsDto GET_Inventory_Requisition_ID_AND_Inventory_Requisition_PART(Long rEQ_PART_ID, Integer Company_id);


	public void UPDATE_TRANSFER_QTY_IN_PART(Double updateTransferQuantity, Long invrpartid, Integer Company_id);


	public void DELETE_InventoryRequisition_MARK(Long deleted_byEmail, Timestamp update_Date, Long iNVRID, Integer Company_id);
	
	public ValueObject getSearchPartRequisition(ValueObject valueObject) throws Exception;
	
	public List<InventoryRequisitionDto> getSearchPartRequisition_List(String Query, Integer companyId) throws Exception;


	public List<InventoryRequisitionDto> list_Of_All_InventoryParts_Requisition(long INVRID, Integer companyId)throws Exception;


	public ValueObject getPartRequisitionDetailsByINVRID(ValueObject object)throws Exception;


	public ValueObject rejectParRequisitionByInventory_transfer_id(ValueObject valueInObject)throws Exception;
	
}
