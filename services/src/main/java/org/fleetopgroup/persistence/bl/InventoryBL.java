package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.constant.InventoryRequisitionStatus;
import org.fleetopgroup.constant.InventoryTransferStatus;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryAllDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryLocationDto;
import org.fleetopgroup.persistence.dto.InventoryRequisitionDto;
import org.fleetopgroup.persistence.dto.InventoryTransferDto;
import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToPartsDto;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryAll;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.InventoryRequisition;
import org.fleetopgroup.persistence.model.InventoryTransfer;
import org.fleetopgroup.persistence.model.PartMeasurementUnit;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;

public class InventoryBL {
	public InventoryBL() {
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat SQLdateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat dateName = new SimpleDateFormat("dd-MMM-yyyy");
	public static Long DEFAULT_PURCHAGE_ORDER_VALUE = (long) 0;
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	java.util.Date currentDate = new java.util.Date();
	DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	// save the InventoryAll
	public InventoryAll prepareInventoryAll(Long Part_ID) {
		InventoryAll part = new InventoryAll();

		part.setPartid(Part_ID);

		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		part.setCreatedById(userDetails.getId());
		part.setLastModifiedById(userDetails.getId());
		part.setCompanyId(userDetails.getCompany_id());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		part.setCreated(toDate);
		part.setLastupdated(toDate);

		part.setMarkForDelete(false);
		return part;
	}

	// save the InventoryLocation Model
	public InventoryLocation prepareInventoryLocation(InventoryDto InventoryDto, Long MasterPart_ID) {
		InventoryLocation part = new InventoryLocation();

		part.setInventory_location_id(InventoryDto.getInventory_id());
		part.setPartid(MasterPart_ID);
		// part.setPartnumber(Master.getPartnumber());
		// part.setPartname(Master.getPartname());
		// part.setParttype(Master.getParttype());
		// part.setPartTypeId(Master.getPartTypeId());
		// part.setPart_photoid(Master.getPart_photoid());
		// part.setCategory(Master.getCategory());
		// part.setLocation(InventoryDto.getLocation());
		part.setLocationId(InventoryDto.getLocationId());
		// part.setLocation_quantity(InventoryDto.getQuantity());

		return part;
	}
	
	public InventoryLocation updateInventoryLocation(Long inventory_id, Long MasterPart_ID, Integer locationId) {
		InventoryLocation part = new InventoryLocation();

		part.setInventory_location_id(inventory_id);
		part.setPartid(MasterPart_ID);
		part.setLocationId(locationId);
		// part.setPartnumber(Master.getPartnumber());
		// part.setPartname(Master.getPartname());
		// part.setParttype(Master.getParttype());
		// part.setPartTypeId(Master.getPartTypeId());
		// part.setPart_photoid(Master.getPart_photoid());
		// part.setCategory(Master.getCategory());
		// part.setLocation(InventoryDto.getLocation());
		// part.setLocation_quantity(InventoryDto.getQuantity());

		return part;
	}

	// show the List Of Vehicle part
	// public List<InventoryDto> prepareListofDto(List<Inventory> Inventory) {
	// List<InventoryDto> Dtos = null;
	// if (Inventory != null && !Inventory.isEmpty()) {
	// Dtos = new ArrayList<InventoryDto>();
	// InventoryDto Dto = null;
	// for (Inventory Parts : Inventory) {
	// Dto = new InventoryDto();
	// Dto.setInventory_id(Parts.getInventory_id());
	// Dto.setInventory_Number(Parts.getInventory_Number());
	// Dto.setPartid(Parts.getPartid());
	// Dto.setPartnumber(Parts.getPartnumber());
	// Dto.setPartname(Parts.getPartname());
	// Dto.setParttype(Parts.getParttype());
	// Dto.setCategory(Parts.getCategory());
	// Dto.setMake(Parts.getMake());
	// Dto.setLocation(Parts.getLocation());
	// if (Parts.getQuantity() != null) {
	// Dto.setQuantity(Parts.getQuantity());
	// }
	//
	// if (Parts.getHistory_quantity() != null) {
	// Dto.setHistory_quantity(Parts.getHistory_quantity());
	// }
	//
	// if (Parts.getUnitprice() != null) {
	// Dto.setUnitprice(round(Parts.getUnitprice(), 2));
	// }
	// Dto.setPurchaseorder_id(Parts.getPurchaseorder_id());
	//
	// Dto.setInvoice_number(Parts.getInvoice_number());
	// Dto.setInvoice_amount(Parts.getInvoice_amount());
	// if (Parts.getInvoice_date() != null) {
	// Dto.setInvoice_date(dateName.format(Parts.getInvoice_date()));
	// }
	// Dto.setVendor_id(Parts.getVendor_id());
	// Dto.setVendor_name(Parts.getVendor_name());
	// Dto.setVendor_location(Parts.getVendor_location());
	// Dto.setUnittype(Parts.getUnittype());
	// Dto.setDiscount(Parts.getDiscount());
	// Dto.setTax(Parts.getTax());
	// if (Parts.getTotal() != null) {
	// Dto.setTotal(round(Parts.getTotal(), 2));
	// }
	// Dto.setDescription(Parts.getDescription());
	//
	// Dto.setInventory_location_id(Parts.getInventory_location_id());
	// Dto.setInventory_all_id(Parts.getInventory_all_id());
	//
	// Dtos.add(Dto);
	// }
	// }
	// return Dtos;
	// }

	// save the InventoryAll To Via TransferInventory
	public InventoryAll prepareInventoryAll_To_TransferInventory(InventoryDto Master) {
		InventoryAll part = new InventoryAll();

		part.setInventory_all_id(Master.getInventory_all_id());
		part.setPartid(Master.getPartid());
		// part.setPartnumber(Master.getPartnumber());
		// part.setPartname(Master.getPartname());
		// part.setParttype(Master.getParttype());
		// part.setPartTypeId(Master.getPartTypeId());
		// part.setPart_photoid(Master.getPart_photoid());
		// part.setCategory(Master.getCategory());

		// part.setAll_quantity(Master.getQuantity());

		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails auth = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		part.setCreatedById(auth.getId());
		part.setLastModifiedById(auth.getId());
		part.setCompanyId(auth.getCompany_id());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		part.setCreated(toDate);
		part.setLastupdated(toDate);

		part.setMarkForDelete(false);
		return part;
	}

	// save the InventoryLocation Model To Via TransferInventory
	public InventoryLocation prepareInventoryLocation_To_TransferInventory(InventoryDto Master, InventoryAll inventoryAll) {
		InventoryLocation part = new InventoryLocation();

		// part.setInventory_location_id(InventoryDto.getInventory_id());
		part.setPartid(Master.getPartid());
		part.setCompanyId(inventoryAll.getCompanyId());
		// part.setPartnumber(Master.getPartnumber());
		// part.setPartname(Master.getPartname());
		// part.setParttype(Master.getParttype());
		// part.setPartTypeId(Master.getPartTypeId());
		// part.setPart_photoid(Master.getPart_photoid());
		// part.setCategory(Master.getCategory());
		// part.setLocation(InventoryDto.getLocation());
		// part.setLocation_quantity(InventoryDto.getQuantity());

		return part;
	}

	// save the Inventory Model To Via TransferInventory
	public Inventory prepareModel_To_TransferInventory(InventoryDto MasterTransfer) throws ParseException {
		Inventory part = new Inventory();

		// part.setInventory_id(MasterTransfer.getInventory_id());

		// get search data to get part details
		part.setPartid(MasterTransfer.getPartid());
		// part.setPartnumber(MasterTransfer.getPartnumber());
		// part.setPartname(MasterTransfer.getPartname());
		// part.setParttype(MasterTransfer.getParttype());
		// part.setPartTypeId(MasterTransfer.getPartTypeId());
		// part.setUnittype(MasterTransfer.getUnittype());
		// part.setCategory(MasterTransfer.getCategory());
		// part.setPart_photoid(MasterTransfer.getPart_photoid());
		// part.setUnittype(MasterTransfer.getUnittype());
		part.setInventory_Number(MasterTransfer.getInventory_Number());

		if (MasterTransfer.getMake() != null) {
			part.setMake(MasterTransfer.getMake());
		}
		// part.setLocation(MasterTransfer.getLocation());

		// part.setQuantity(InventoryDto.getQuantity());

		part.setUnitprice(MasterTransfer.getUnitprice());

		part.setDiscount(MasterTransfer.getDiscount());
		part.setTax(MasterTransfer.getTax());

		part.setPurchaseorder_id(MasterTransfer.getPurchaseorder_id());

		part.setInvoice_number(MasterTransfer.getInvoice_number());
		part.setInvoice_amount(MasterTransfer.getInvoice_amount());
		
		try {

			if (MasterTransfer.getInvoice_date() != null) {
				java.util.Date date = SQLdateFormat.parse(MasterTransfer.getInvoice_date());
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				part.setInvoice_date(start_date);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		part.setVendor_id(MasterTransfer.getVendor_id());

		part.setDescription(MasterTransfer.getDescription());

		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		part.setCreatedById(userDetails.getId());
		part.setLastModifiedById(userDetails.getId());
		part.setCompanyId(userDetails.getCompany_id());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		part.setCreated(toDate);
		part.setLastupdated(toDate);

		part.setMarkForDelete(false);
		return part;
	}

	// save the Inventory Model To Via TransferInventory
	public InventoryTransfer prepareCreateInventory_To_TransferInventory(InventoryDto MasterTransferInventory,
			InventoryTransferDto TransferCreate_UI) throws ParseException {
		InventoryTransfer part_transfer = new InventoryTransfer();

		part_transfer.setInventory_transfer_id(TransferCreate_UI.getInventory_transfer_id());

		// get search data to get part details
		part_transfer.setTransfer_partid(MasterTransferInventory.getPartid());
		part_transfer.setTransfer_from_locationId(MasterTransferInventory.getLocationId());

		part_transfer.setTransfer_to_locationId(TransferCreate_UI.getTransfer_to_locationId());
		part_transfer.setTransfer_quantity(TransferCreate_UI.getTransfer_quantity());
		//part_transfer.setTransfer_by(TransferCreate_UI.getTransfer_by());
		part_transfer.setTransfer_via_ID(TransferCreate_UI.getTransfer_via_ID());

		// get Current days
		java.util.Date currentDate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

		part_transfer.setTransfer_date(toDate);

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		part_transfer.setTransfer_receiveddate(null);
		part_transfer.setTransfer_description(TransferCreate_UI.getTransfer_description());

		part_transfer.setTransfer_inventory_id(MasterTransferInventory.getInventory_id());
		part_transfer.setTransfer_inventory_location_id(MasterTransferInventory.getInventory_location_id());
		part_transfer.setTransfer_inventory_all_id(MasterTransferInventory.getInventory_all_id());

		part_transfer.setMarkForDelete(false);
		part_transfer.setCREATED_DATE(toDate);
		part_transfer.setLASTUPDATED_DATE(toDate);

		//part_transfer.setCREATEDBY(userDetails.getEmail());
		part_transfer.setCreatedById(userDetails.getId());
		//part_transfer.setLASTMODIFIEDBY(userDetails.getEmail());
		part_transfer.setLastModifiedById(userDetails.getId());
		part_transfer.setCompanyId(userDetails.getCompany_id());

		return part_transfer;
	}

	// public List<InventoryDto> prepareReport_List_InventoryDto(List<Inventory>
	// Inventory) {
	// List<InventoryDto> Dtos = null;
	// if (Inventory != null && !Inventory.isEmpty()) {
	// Dtos = new ArrayList<InventoryDto>();
	// InventoryDto Dto = null;
	// for (Inventory Parts : Inventory) {
	// Dto = new InventoryDto();
	// Dto.setInventory_id(Parts.getInventory_id());
	// Dto.setInventory_Number(Parts.getInventory_Number());
	// Dto.setPartid(Parts.getPartid());
	// Dto.setPartnumber(Parts.getPartnumber());
	// Dto.setPartname(Parts.getPartname());
	// Dto.setParttype(Parts.getParttype());
	// Dto.setCategory(Parts.getCategory());
	// Dto.setMake(Parts.getMake());
	// Dto.setLocation(Parts.getLocation());
	// Dto.setQuantity(Parts.getQuantity());
	//
	// Dto.setUnitprice(Parts.getUnitprice());
	//
	// Dto.setPurchaseorder_id(Parts.getPurchaseorder_id());
	//
	// Dto.setInvoice_number(Parts.getInvoice_number());
	// Dto.setInvoice_amount(Parts.getInvoice_amount());
	// if (Parts.getInvoice_date() != null) {
	// Dto.setInvoice_date(dateName.format(Parts.getInvoice_date()));
	// }
	// Dto.setVendor_id(Parts.getVendor_id());
	// Dto.setVendor_name(Parts.getVendor_name());
	// Dto.setVendor_location(Parts.getVendor_location());
	// Dto.setUnittype(Parts.getUnittype());
	// Dto.setDiscount(Parts.getDiscount());
	// Dto.setTax(Parts.getTax());
	// Dto.setTotal(Parts.getTotal());
	// Dto.setDescription(Parts.getDescription());
	//
	// Dto.setInventory_location_id(Parts.getInventory_location_id());
	// Dto.setInventory_all_id(Parts.getInventory_all_id());
	//
	// Dtos.add(Dto);
	// }
	// }
	// return Dtos;
	// }

	// show the List Of Vehicle part Location
	// public List<Inventory> prepareListofDto_Location(List<Inventory> Inventory) {
	// List<Inventory> Dtos = null;
	// if (Inventory != null && !Inventory.isEmpty()) {
	// Dtos = new ArrayList<Inventory>();
	// Inventory Dto = null;
	// for (Inventory Parts : Inventory) {
	// Dto = new Inventory();
	// Dto.setInventory_id(Parts.getInventory_id());
	// Dto.setPartid(Parts.getPartid());
	// Dto.setPartnumber(Parts.getPartnumber());
	// Dto.setPartname(Parts.getPartname());
	// Dto.setParttype(Parts.getParttype());
	// Dto.setCategory(Parts.getCategory());
	// Dto.setMake(Parts.getMake());
	// Dto.setLocation(Parts.getLocation());
	// Dto.setQuantity(Parts.getQuantity());
	// Dto.setUnitprice(Parts.getUnitprice());
	// Dto.setPurchaseorder_id(Parts.getPurchaseorder_id());
	// Dto.setInvoice_number(Parts.getInvoice_number());
	// Dto.setVendor_id(Parts.getVendor_id());
	// Dto.setVendor_name(Parts.getVendor_name());
	// Dto.setVendor_location(Parts.getVendor_location());
	// Dto.setUnittype(Parts.getUnittype());
	// Dto.setDescription(Parts.getDescription());
	//
	// Dto.setCreated(Parts.getCreated());
	// Dto.setLastupdated(Parts.getLastupdated());
	// Dtos.add(Dto);
	// }
	// }
	// return Dtos;
	// }

	// edit Show InventoryDto
	public InventoryDto prepareInventoryDto(InventoryDto Parts) {
		InventoryDto Dto = new InventoryDto();

		Dto.setInventory_id(Parts.getInventory_id());
		Dto.setInventory_Number(Parts.getInventory_Number());
		Dto.setPartid(Parts.getPartid());
		Dto.setPartnumber(Parts.getPartnumber());
		Dto.setPartname(Parts.getPartname());
		Dto.setParttype(Parts.getParttype());
		Dto.setPartTypeId(Parts.getPartTypeId());
		Dto.setPart_photoid(Parts.getPart_photoid());
		Dto.setMake(Parts.getMake());
		Dto.setCategory(Parts.getCategory());
		Dto.setLocation(Parts.getLocation());
		Dto.setLocationId(Parts.getLocationId());
		Dto.setQuantity(Parts.getQuantity());
		Dto.setUnitprice(Parts.getUnitprice());
		Dto.setPurchaseorder_id(Parts.getPurchaseorder_id());
		Dto.setInvoice_number(Parts.getInvoice_number());
		if (Parts.getInvoice_date() != null) {
			
			Dto.setInvoice_date(Parts.getInvoice_date());
			
		}
		Dto.setInvoice_amount(Parts.getInvoice_amount());

		Dto.setVendor_id(Parts.getVendor_id());
		Dto.setVendor_name(Parts.getVendor_name());
		Dto.setVendor_location(Parts.getVendor_location());
		Dto.setUnittype(Parts.getUnittype());
		Dto.setDiscount(Parts.getDiscount());
		Dto.setTax(Parts.getTax());
		Dto.setTotal(Parts.getTotal());
		Dto.setDescription(Parts.getDescription());
		Dto.setPartInvoiceId(Parts.getPartInvoiceId());
		Dto.setInventory_all_id(Parts.getInventory_all_id());
		Dto.setInventory_location_id(Parts.getInventory_location_id());

		return Dto;
	}

	/**
	 * @param inventory
	 * @return
	 */
	// public InventoryDto Show_InventoryDetails(Inventory Parts) {
	//
	// InventoryDto Dto = new InventoryDto();
	//
	// Dto.setInventory_id(Parts.getInventory_id());
	// Dto.setPartid(Parts.getPartid());
	// Dto.setPartnumber(Parts.getPartnumber());
	// Dto.setPartname(Parts.getPartname());
	// Dto.setParttype(Parts.getParttype());
	// Dto.setPart_photoid(Parts.getPart_photoid());
	// Dto.setMake(Parts.getMake());
	// Dto.setCategory(Parts.getCategory());
	// Dto.setLocation(Parts.getLocation());
	// Dto.setQuantity(Parts.getQuantity());
	// Dto.setUnitprice(Parts.getUnitprice());
	// Dto.setPurchaseorder_id(Parts.getPurchaseorder_id());
	// Dto.setInvoice_number(Parts.getInvoice_number());
	// if (Parts.getInvoice_date() != null) {
	// Dto.setInvoice_date(dateFormat.format(Parts.getInvoice_date()));
	// }
	// Dto.setInvoice_amount(Parts.getInvoice_amount());
	//
	// Dto.setVendor_id(Parts.getVendor_id());
	// Dto.setVendor_name(Parts.getVendor_name());
	// Dto.setVendor_location(Parts.getVendor_location());
	// Dto.setUnittype(Parts.getUnittype());
	// Dto.setDiscount(Parts.getDiscount());
	// Dto.setTax(Parts.getTax());
	// Dto.setTotal(Parts.getTotal());
	// Dto.setDescription(Parts.getDescription());
	//
	// Dto.setInventory_all_id(Parts.getInventory_all_id());
	// Dto.setInventory_location_id(Parts.getInventory_location_id());
	//
	// // Create and Last updated Display
	// Dto.setCreatedBy(Parts.getCreatedBy());
	// if (Parts.getCreated() != null) {
	// Dto.setCreated(CreatedDateTime.format(Parts.getCreated()));
	// }
	// Dto.setLastModifiedBy(Parts.getLastModifiedBy());
	// if (Parts.getLastupdated() != null) {
	// Dto.setLastupdated(CreatedDateTime.format(Parts.getLastupdated()));
	// }
	// return Dto;
	// }

	/**
	 * @param show_Location
	 * @return
	 */
//	public List<InventoryTransferDto> prepareListofDto_Inventory_Transfer(List<InventoryTransfer> show_Location) {
//
//		List<InventoryTransferDto> Dtos = null;
//		if (show_Location != null && !show_Location.isEmpty()) {
//			Dtos = new ArrayList<InventoryTransferDto>();
//			InventoryTransferDto part_transfer = null;
//			for (InventoryTransfer Parts : show_Location) {
//
//				part_transfer = new InventoryTransferDto();
//
//				part_transfer.setInventory_transfer_id(Parts.getInventory_transfer_id());
//
//				// get search data to get part details
//				part_transfer.setTransfer_partid(Parts.getTransfer_partid());
//				// part_transfer.setTransfer_partname(Parts.getTransfer_partname());
//				// part_transfer.setTransfer_partnumber(Parts.getTransfer_partnumber());
//				// part_transfer.setTransfer_from_location(Parts.getTransfer_from_location());
//				//
//				// part_transfer.setTransfer_to_location(Parts.getTransfer_to_location());
//				part_transfer.setTransfer_quantity(Parts.getTransfer_quantity());
//				part_transfer.setTransfer_by(Parts.getTransfer_by());
//				part_transfer.setTransfer_via(Parts.getTransfer_via());
//
//				if (Parts.getTransfer_date() != null) {
//					part_transfer.setTransfer_date(CreatedDateTime.format(Parts.getTransfer_date()));
//				}
//
//				part_transfer.setTransfer_receivedby(Parts.getTransfer_receivedby());
//				part_transfer.setTransfer_receivedReason(Parts.getTransfer_receivedReason());
//				part_transfer.setTransfer_receivedbyEmail(Parts.getTransfer_receivedbyEmail());
//				if (Parts.getTransfer_receiveddate() != null) {
//					part_transfer.setTransfer_receiveddate(CreatedDateTime.format(Parts.getTransfer_receiveddate()));
//				}
//				part_transfer.setTransfer_description(Parts.getTransfer_description());
//
//				part_transfer.setTransfer_inventory_id(Parts.getTransfer_inventory_id());
//				part_transfer.setTransfer_inventory_location_id(Parts.getTransfer_inventory_location_id());
//				part_transfer.setTransfer_inventory_all_id(Parts.getTransfer_inventory_all_id());
//
//				// part_transfer.setTRANSFER_STATUS(Parts.getTRANSFER_STATUS());
//				part_transfer.setMarkForDelete(Parts.isMarkForDelete());
//				if (Parts.getCREATED_DATE() != null) {
//					part_transfer.setCREATED_DATE(CreatedDateTime.format(Parts.getCREATED_DATE()));
//				}
//				if (Parts.getLASTUPDATED_DATE() != null) {
//					part_transfer.setLASTUPDATED_DATE(CreatedDateTime.format(Parts.getLASTUPDATED_DATE()));
//				}
//				part_transfer.setCREATEDBY(Parts.getCREATEDBY());
//				part_transfer.setLASTMODIFIEDBY(Parts.getLASTMODIFIEDBY());
//
//				Dtos.add(part_transfer);
//			}
//
//		}
//		return Dtos;
//	}

	/*public List<InventoryRequisitionDto> prepareInventoryRequisition_List(List<InventoryRequisition> pageList) {

		List<InventoryRequisitionDto> Dtos = null;
		if (pageList != null && !pageList.isEmpty()) {
			Dtos = new ArrayList<InventoryRequisitionDto>();
			InventoryRequisitionDto partRequisition = null;
			for (InventoryRequisition Parts : pageList) {

				partRequisition = new InventoryRequisitionDto();

				partRequisition.setINVRID(Parts.getINVRID());

				partRequisition.setINVRID_NUMBER(Parts.getINVRID_NUMBER());

				//partRequisition.setREQUISITION_RECEIVEDNAME(Parts.getREQUISITION_RECEIVEDNAME());

				partRequisition.setREQUISITION_STATUS_ID(Parts.getREQUISITION_STATUS_ID());

				partRequisition.setREQUISITION_STATUS(
						InventoryRequisitionStatus.getInventoryRequisitionName(Parts.getREQUISITION_STATUS_ID()));

				try {

					if (Parts.getREQUITED_DATE() != null) {
						partRequisition.setREQUITED_DATE(dateName.format(Parts.getREQUITED_DATE()));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				partRequisition.setREQUITED_NUMBER(Parts.getREQUITED_NUMBER());

				//partRequisition.setREQUITED_LOCATION(Parts.getREQUITED_LOCATION());
				partRequisition.setREQUITED_LOCATION_ID(Parts.getREQUITED_LOCATION_ID());

				partRequisition.setREQUITED_REMARK(Parts.getREQUITED_REMARK());

				partRequisition.setREQUITED_TOTALQTY(Parts.getREQUITED_TOTALQTY());

				//partRequisition.setREQUITED_SENDNAME(Parts.getREQUITED_SENDNAME());

				partRequisition.setCREATEDBY(Parts.getCREATEDBY());
				partRequisition.setLASTMODIFIEDBY(Parts.getCREATEDBY());

				Dtos.add(partRequisition);
			}

		}
		return Dtos;
	}
*/
	public InventoryRequisition prepareInventoryRequisition(InventoryRequisitionDto inventory) {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		InventoryRequisition partRequisition = new InventoryRequisition();

		partRequisition.setCOMPANY_ID(userDetails.getCompany_id());

		partRequisition.setMarkForDelete(false);

		partRequisition.setREQUISITION_RECEIVER_ID(inventory.getREQUISITION_RECEIVER_ID());

		//partRequisition.setREQUISITION_STATUS("OPEN");
		partRequisition.setREQUISITION_STATUS_ID(InventoryRequisitionStatus.OPEN);

		try {

			if (inventory.getREQUITED_DATE() != null) {
				java.util.Date date = dateFormat.parse(inventory.getREQUITED_DATE());
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				partRequisition.setREQUITED_DATE(start_date);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		partRequisition.setREQUITED_NUMBER(inventory.getREQUITED_NUMBER());

		partRequisition.setREQUITED_LOCATION_ID(inventory.getREQUITED_LOCATION_ID());

		//partRequisition.setREQUITED_LOCATION("" + inventory.getREQUITED_LOCATION_ID());

		partRequisition.setREQUITED_REMARK(inventory.getREQUITED_REMARK());

		partRequisition.setREQUITED_TOTALQTY(0.0);

		
		partRequisition.setREQUITED_SENDER_ID(userDetails.getId());

		partRequisition.setCREATEDBYID(userDetails.getId());
		partRequisition.setLASTMODIFIEDBYID(userDetails.getId());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		partRequisition.setCREATED_DATE(toDate);
		partRequisition.setLASTUPDATED_DATE(toDate);
		partRequisition.setVID(inventory.getVID());

		//partRequisition.setmarkForDelete(false);

		return partRequisition;
	}

	public boolean isFilled_GET_Permission(long value, List<PartLocationPermissionDto> list) {
		if (list != null && !list.isEmpty()) {
			for (PartLocationPermissionDto Parts : list) {
				if (list != null && Parts.getPartLocationId() == value) {
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}

	public List<InventoryRequisitionDto> listOfAllPartRequisition(InventoryRequisitionDto dto,List<InventoryRequisitionDto> partPageList) {
		InventoryRequisitionDto 			inventoryRequisitionDto				= null;
		List<InventoryRequisitionDto> 		finalList							= null;
		try {
			finalList				= new ArrayList<InventoryRequisitionDto>();
			inventoryRequisitionDto = new InventoryRequisitionDto();

			inventoryRequisitionDto.setINVRID(dto.getINVRID());
			inventoryRequisitionDto.setINVRID_NUMBER(dto.getINVRID_NUMBER());
			inventoryRequisitionDto.setREQUITED_LOCATION(dto.getREQUITED_LOCATION());
			inventoryRequisitionDto.setREQUISITION_RECEIVEDNAME(dto.getREQUISITION_RECEIVEDNAME());
			inventoryRequisitionDto.setREQUITED_SENDNAME(dto.getREQUITED_SENDNAME());
			if (dto.getREQUITED_DATE() != null) {
				inventoryRequisitionDto.setREQUITED_DATE(dto.getREQUITED_DATE());
			}
			inventoryRequisitionDto.setREQUISITION_STATUS(dto.getREQUISITION_STATUS());
			inventoryRequisitionDto.setREQUITED_LOCATION_ID(dto.getREQUITED_LOCATION_ID());
			if(partPageList != null) {
				for(InventoryRequisitionDto partDto : partPageList) {
					if(partDto != null) {
							if(inventoryRequisitionDto.getPART_NAME() == null || inventoryRequisitionDto.getPART_NAME().isEmpty()) {
								inventoryRequisitionDto.setPART_NAME(partDto.getPART_NAME());
							} else {
								inventoryRequisitionDto.setPART_NAME(inventoryRequisitionDto.getPART_NAME()+","+partDto.getPART_NAME());
							}
							if(inventoryRequisitionDto.getPART_REQUITED_QTY() == null || inventoryRequisitionDto.getPART_REQUITED_QTY() <= 0) {
								inventoryRequisitionDto.setPART_REQUITED_QTY(partDto.getPART_REQUITED_QTY());
							}else {
								inventoryRequisitionDto.setPART_REQUITED_QTY(inventoryRequisitionDto.getPART_REQUITED_QTY()+partDto.getPART_REQUITED_QTY());
							}
							
					}
				}
				if(inventoryRequisitionDto.getPART_NAME().length() > 40) {
					String partName= inventoryRequisitionDto.getPART_NAME().substring(0,35);
					inventoryRequisitionDto.setPART_NAME(partName.concat(".."));
				}else {
					inventoryRequisitionDto.setPART_NAME(inventoryRequisitionDto.getPART_NAME());
				}
			}
				finalList.add(inventoryRequisitionDto);
				return finalList;
			
		} catch (Exception e) {
			System.err.println("EE"+e);
			throw e;
			
		}
	}
	
	public List<InventoryAllDto> getFinalInventoryDetailDto(List<InventoryAllDto>		inventoryDtos, Map<Integer, PartMeasurementUnit>  measurementUnitHM) {
		if(inventoryDtos != null && !inventoryDtos.isEmpty()) {
			Integer	unitId	= 0;
			PartMeasurementUnit	measurementUnit	= null;
			PartMeasurementUnit	conversionUnit	= null;
			for (InventoryAllDto inventoryAllDto : inventoryDtos) {
				unitId	= Integer.parseInt(inventoryAllDto.getUnitTypeId()+"");
				if(unitId != null && unitId > 0) {
					measurementUnit	= measurementUnitHM.get(unitId);
					if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
						conversionUnit	= measurementUnitHM.get(measurementUnit.getConvertTo());
						if(conversionUnit != null) {
							inventoryAllDto.setConvertToStr("( "+(Utility.round((inventoryAllDto.getAll_quantity() / measurementUnit.getConversionRate()), 3)) 
															+" "+ measurementUnit.getPmuName()+" )");
						}
					}
				}
			}
		}
		return inventoryDtos;
	}
	
	public List<InventoryLocationDto>  getFinalInventoryLocationDto(List<InventoryLocationDto> inventoryDtos, Map<Integer, PartMeasurementUnit>  measurementUnitHM) {
		if(inventoryDtos != null && !inventoryDtos.isEmpty()) {
			Integer	unitId	= 0;
			PartMeasurementUnit	measurementUnit	= null;
			PartMeasurementUnit	conversionUnit	= null;
			for (InventoryLocationDto inventoryAllDto : inventoryDtos) {
				unitId	= Integer.parseInt(inventoryAllDto.getUnitTypeId()+"");
				if(unitId != null && unitId > 0) {
					measurementUnit	= measurementUnitHM.get(unitId);
					if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
						conversionUnit	= measurementUnitHM.get(measurementUnit.getConvertTo());
						if(conversionUnit != null) {
							inventoryAllDto.setConvertToStr("( "+(Utility.round((inventoryAllDto.getLocation_quantity() / measurementUnit.getConversionRate()), 3)) 
															+" "+ measurementUnit.getPmuName()+" )");
						}
					}
				}
			}
		}
		return inventoryDtos;
	}
	
	public List<InventoryLocationDto>  getFinalInventoryForWO(List<InventoryLocationDto> inventoryDtos, Map<Integer, PartMeasurementUnit>  measurementUnitHM) {
		if(inventoryDtos != null && !inventoryDtos.isEmpty()) {
			Integer	unitId	= 0;
			PartMeasurementUnit	measurementUnit	= null;
			PartMeasurementUnit	conversionUnit	= null;
			for (InventoryLocationDto inventoryAllDto : inventoryDtos) {
				unitId	= Integer.parseInt(inventoryAllDto.getUnitTypeId()+"");
				if(unitId != null && unitId > 0) {
					measurementUnit	= measurementUnitHM.get(unitId);
					if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
						conversionUnit	= measurementUnitHM.get(measurementUnit.getConvertTo());
						if(conversionUnit != null) {
							inventoryAllDto.setConvertToStr(" "+inventoryAllDto.getLocation_quantity()+" "+measurementUnit.getPmuName()+"( "+(Utility.round((inventoryAllDto.getLocation_quantity() / measurementUnit.getConversionRate()), 3)) 
															+" "+ measurementUnit.getPmuName()+" )");
							inventoryAllDto.setLocation_quantity(Utility.round((inventoryAllDto.getLocation_quantity() / measurementUnit.getConversionRate()), 3));
						}
					}
				}
			}
		}
		return inventoryDtos;
	}

	public List<InventoryDto>  getFinalInventoryDto(List<InventoryDto> inventoryDtos, Map<Integer, PartMeasurementUnit>  measurementUnitHM) {
		if(inventoryDtos != null && !inventoryDtos.isEmpty()) {
			Integer	unitId	= 0;
			PartMeasurementUnit	measurementUnit	= null;
			PartMeasurementUnit	conversionUnit	= null;
			for (InventoryDto inventoryAllDto : inventoryDtos) {
				unitId	= Integer.parseInt(inventoryAllDto.getUnitTypeId()+"");
				if(unitId != null && unitId > 0) {
					measurementUnit	= measurementUnitHM.get(unitId);
					if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
						conversionUnit	= measurementUnitHM.get(measurementUnit.getConvertTo());
						if(conversionUnit != null) {
							inventoryAllDto.setConvertToStr("( "+(Utility.round((inventoryAllDto.getQuantity() / measurementUnit.getConversionRate()), 3)) 
															+" "+ measurementUnit.getPmuName()+" )");
							inventoryAllDto.setUnittype(conversionUnit.getPmuName());
						}
					}
				}
			}
		}
		return inventoryDtos;
	}
	
	public List<InventoryDto>  getFinalInventoryForWOQty(List<InventoryDto> inventoryDtos, Map<Integer, PartMeasurementUnit>  measurementUnitHM) {
		if(inventoryDtos != null && !inventoryDtos.isEmpty()) {
			Integer	unitId	= 0;
			PartMeasurementUnit	measurementUnit	= null;
			PartMeasurementUnit	conversionUnit	= null;
			for (InventoryDto inventoryAllDto : inventoryDtos) {
				unitId	= Integer.parseInt(inventoryAllDto.getUnitTypeId()+"");
				if(unitId != null && unitId > 0) {
					measurementUnit	= measurementUnitHM.get(unitId);
					if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
						conversionUnit	= measurementUnitHM.get(measurementUnit.getConvertTo());
						if(conversionUnit != null) {
							inventoryAllDto.setConvertToStr(" "+inventoryAllDto.getQuantity()+" "+measurementUnit.getPmuName()+"( "+(Utility.round((inventoryAllDto.getQuantity() / measurementUnit.getConversionRate()), 3)) 
									+" "+ measurementUnit.getPmuName()+" )");
							inventoryAllDto.setUnittype(conversionUnit.getPmuName());
						}
					}
				}
			}
		}
		return inventoryDtos;
	}
	
	public InventoryAllDto getFinalInventoryDetailDto(InventoryAllDto		inventoryAllDto, Map<Integer, PartMeasurementUnit>  measurementUnitHM) {
		if(inventoryAllDto != null) {
			Integer	unitId	= 0;
			PartMeasurementUnit	measurementUnit	= null;
			PartMeasurementUnit	conversionUnit	= null;
				unitId	= Integer.parseInt(inventoryAllDto.getUnitTypeId()+"");
				if(unitId != null && unitId > 0) {
					measurementUnit	= measurementUnitHM.get(unitId);
					if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
						conversionUnit	= measurementUnitHM.get(measurementUnit.getConvertTo());
						if(conversionUnit != null) {
							inventoryAllDto.setConvertToStr("( "+(Utility.round((inventoryAllDto.getAll_quantity() / measurementUnit.getConversionRate()), 3)) 
															+" "+ measurementUnit.getPmuName()+" )");
						}
					}
				}
		}
		return inventoryAllDto;
	}
	
	public InventoryDto  getFinalInventoryDto(InventoryDto inventoryAllDto, Map<Integer, PartMeasurementUnit>  measurementUnitHM) {
		if(inventoryAllDto != null) {
			Integer	unitId	= 0;
			PartMeasurementUnit	measurementUnit	= null;
			PartMeasurementUnit	conversionUnit	= null;
				unitId	= Integer.parseInt(inventoryAllDto.getUnitTypeId()+"");
				if(unitId != null && unitId > 0) {
					measurementUnit	= measurementUnitHM.get(unitId);
					if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
						conversionUnit	= measurementUnitHM.get(measurementUnit.getConvertTo());
						if(conversionUnit != null) {
							inventoryAllDto.setConvertToStr("( "+(Utility.round((inventoryAllDto.getHistory_quantity() / measurementUnit.getConversionRate()), 3)) 
															+" "+ measurementUnit.getPmuName()+" )");
							inventoryAllDto.setUnittype(conversionUnit.getPmuName());
							inventoryAllDto.setQuantity(Utility.round((inventoryAllDto.getQuantity() / measurementUnit.getConversionRate()), 3));
							inventoryAllDto.setUnitprice(inventoryAllDto.getMainUnitprice());
						}
					}
				}
		}
		return inventoryAllDto;
	}
	
	public List<InventoryDto>  getFinalInventoryFordit(List<InventoryDto> inventoryDtos, Map<Integer, PartMeasurementUnit>  measurementUnitHM) {
		if(inventoryDtos != null && !inventoryDtos.isEmpty()) {
			Integer	unitId	= 0;
			PartMeasurementUnit	measurementUnit	= null;
			PartMeasurementUnit	conversionUnit	= null;
			for (InventoryDto inventoryAllDto : inventoryDtos) {
				unitId	= Integer.parseInt(inventoryAllDto.getUnitTypeId()+"");
				if(unitId != null && unitId > 0) {
					measurementUnit	= measurementUnitHM.get(unitId);
					if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
						conversionUnit	= measurementUnitHM.get(measurementUnit.getConvertTo());
						if(conversionUnit != null) {
							inventoryAllDto.setConvertToStr(" "+inventoryAllDto.getQuantity()+" "+measurementUnit.getPmuName()+"( "+(Utility.round((inventoryAllDto.getQuantity() / measurementUnit.getConversionRate()), 3)) 
									+" "+ measurementUnit.getPmuName()+" )");
							inventoryAllDto.setUnittype(conversionUnit.getPmuName());
							inventoryAllDto.setHistory_quantity(inventoryAllDto.getMainQuantity());
							inventoryAllDto.setUnitprice(inventoryAllDto.getMainUnitprice());
							
							
						}
					}
				}
			}
		}
		return inventoryDtos;
	}
	
	public ValueObject getInventoryAllDtoForUI(InventoryAllDto	inventoryAllDto , List<InventoryAllDto>  tempList) throws Exception {
		ValueObject	valueObject	= new ValueObject();
		String inventoryAllIds	= "";
		if(tempList != null && !tempList.isEmpty()) {
			for (InventoryAllDto inventoryAll : tempList) {
				inventoryAllIds += inventoryAll.getInventory_all_id()+",";
				if(!inventoryAllDto.getInventory_all_id().equals(inventoryAll.getInventory_all_id())) {
					inventoryAllDto.setAll_quantity(inventoryAllDto.getAll_quantity() + inventoryAll.getAll_quantity());
					inventoryAllDto.setPartnumber(inventoryAllDto.getPartnumber()+" / "+inventoryAll.getPartnumber());
				}
			}
		}
		valueObject.put("inventoryAllDto", inventoryAllDto);
		valueObject.put("inventoryAllIds", Utility.removeLastComma(inventoryAllIds));
		
		return valueObject;
	}
	
	public List<InventoryLocationDto>  getLocationInventoryForUI(List<InventoryLocationDto>  locationList){
		HashMap<String, InventoryLocationDto>  tempHm	= new HashMap<String, InventoryLocationDto>();
		InventoryLocationDto		temp = null;
		for (InventoryLocationDto inventoryLocationDto : locationList) {
			temp	= tempHm.get(inventoryLocationDto.getLocation());
			if(temp == null) {
				temp = inventoryLocationDto;
			}else {
				temp.setLocation_quantity(inventoryLocationDto.getLocation_quantity() + temp.getLocation_quantity());
			}
			
			tempHm.put(inventoryLocationDto.getLocation(), temp);
		}
		
		locationList	= new ArrayList<InventoryLocationDto>(tempHm.values());
		
		return locationList;
	}
	
	public  static List<PurchaseOrdersToPartsDto>  getFinalPOList( List<PurchaseOrdersToPartsDto> inventoryDtos, Map<Integer, PartMeasurementUnit>  measurementUnitHM) {
		if(inventoryDtos != null && !inventoryDtos.isEmpty()) {
			
			Integer	unitId	= 0;
			PartMeasurementUnit	measurementUnit	= null;
			PartMeasurementUnit	conversionUnit	= null;
			for (PurchaseOrdersToPartsDto inventoryAllDto : inventoryDtos) {
				if(inventoryAllDto.getUnittypeId() != null) {
					unitId	= Integer.parseInt(inventoryAllDto.getUnittypeId()+"");
					if(unitId != null && unitId > 0) {
						measurementUnit	= measurementUnitHM.get(unitId);
						if(measurementUnit != null && measurementUnit.getConvertTo() != null && measurementUnit.getConvertTo() > 0) {
							conversionUnit	= measurementUnitHM.get(measurementUnit.getConvertTo());
							if(conversionUnit != null) {
								inventoryAllDto.setConvertToStr("( "+Utility.round((inventoryAllDto.getInventory_all_quantity() / measurementUnit.getConversionRate()), 3)+" "+ measurementUnit.getPmuName()+" )");
							}
						}
					}
				}
			}
		}
		return inventoryDtos;
	}
	
	public InventoryTransfer prepareTransferInventory(Inventory MasterTransferInventory,ValueObject object) throws ParseException {
		InventoryTransfer partTransfer = new InventoryTransfer();
		
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Timestamp toDate = DateTimeUtility.getCurrentTimeStamp();

		//partTransfer.setInventory_transfer_id(TransferCreate_UI.getInventory_transfer_id());

		partTransfer.setTransfer_partid(MasterTransferInventory.getPartid());
		partTransfer.setTransfer_from_locationId(MasterTransferInventory.getLocationId());

		partTransfer.setTransfer_to_locationId(object.getInt("toLocationId",0) );
		partTransfer.setTransfer_quantity(object.getDouble("quantity",0));
		partTransfer.setTransfer_via_ID((short) 0);
		partTransfer.setTransfer_by_ID(userDetails.getId());
		partTransfer.setTransfer_receivedby_ID((long) 0);

		partTransfer.setTransfer_date(toDate);

		

		partTransfer.setTransfer_receiveddate(null);
		//partTransfer.setTransfer_description(TransferCreate_UI.getTransfer_description());

		partTransfer.setTransfer_inventory_id(MasterTransferInventory.getInventory_id());
		partTransfer.setTransfer_inventory_location_id(MasterTransferInventory.getInventory_location_id());
		partTransfer.setTransfer_inventory_all_id(MasterTransferInventory.getInventory_all_id());

		partTransfer.setMarkForDelete(false);
		partTransfer.setCREATED_DATE(toDate);
		partTransfer.setLASTUPDATED_DATE(toDate);

		partTransfer.setCreatedById(userDetails.getId());
		partTransfer.setLastModifiedById(userDetails.getId());
		partTransfer.setCompanyId(userDetails.getCompany_id());
		partTransfer.setRequisitionApprovalId(object.getLong("approvalId",0));
		partTransfer.setTRANSFER_STATUS_ID(InventoryTransferStatus.TRANSFERED);

		return partTransfer;
	}
	public Inventory prepareInventoryRepairPart(ValueObject valueObject,InventoryDto inventoryDto) throws Exception {
		try {

			Inventory inventory = new Inventory();
			inventory.setPartid(inventoryDto.getPartid());
			inventory.setLocationId(valueObject.getInt("toLocationId"));
			inventory.setPurchaseorder_id(DEFAULT_PURCHAGE_ORDER_VALUE);
			inventory.setInvoice_number(inventoryDto.getInvoice_number());
			inventory.setInvoice_amount(valueObject.getString("totalCost"));
			inventory.setMainQuantity(valueObject.getDouble("quantity"));
			inventory.setMainUnitprice(valueObject.getDouble("unitPrice"));
			inventory.setInvoice_date(DateTimeUtility.getCurrentTimeStamp());
			inventory.setVendor_id(0);
			inventory.setDescription("Repair From "+valueObject.getString("fromLocation"));
			inventory.setCreatedById(valueObject.getLong("userId"));
			inventory.setLastModifiedById(valueObject.getLong("userId"));
			inventory.setCreated(DateTimeUtility.getCurrentTimeStamp());
			inventory.setLastupdated(DateTimeUtility.getCurrentTimeStamp());
			inventory.setMarkForDelete(false);
			inventory.setPartInvoiceId(inventoryDto.getPartInvoiceId());
			inventory.setCompanyId(valueObject.getInt("companyId"));
			inventory.setSubLocationId(0);
			inventory.setHistory_quantity(valueObject.getDouble("quantity"));
			inventory.setQuantity(valueObject.getDouble("quantity"));
			inventory.setUnitprice(valueObject.getDouble("unitPrice"));
			inventory.setDiscount(inventoryDto.getDiscount());
			inventory.setTax(inventoryDto.getTax());
			inventory.setMake(inventoryDto.getMake());
			inventory.setInventory_all_id(inventoryDto.getInventory_all_id());
			inventory.setInventory_location_id(inventoryDto.getInventory_location_id());
			inventory.setRepairStockId(valueObject.getLong("repairStockId"));
			if(valueObject.getDouble("quantity") > 1) {
				inventory.setSerialNoAddedForParts((int)valueObject.getDouble("quantity"));
			}else {
				inventory.setSerialNoAddedForParts(0);
			}

			return inventory;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
}
