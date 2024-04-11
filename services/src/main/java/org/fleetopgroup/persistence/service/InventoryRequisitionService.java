package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.data.domain.Sort;

import org.fleetopgroup.constant.InventoryRequisitionStatus;
import org.fleetopgroup.constant.InventoryTransferStatus;
import org.fleetopgroup.persistence.dao.InventoryRequisitionPartRepository;
import org.fleetopgroup.persistence.dao.InventoryRequisitionRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryRequisitionDto;
import org.fleetopgroup.persistence.dto.InventoryRequisitionPartsDto;
import org.fleetopgroup.persistence.model.InventoryRequisition;
import org.fleetopgroup.persistence.model.InventoryRequisitionParts;
import org.fleetopgroup.persistence.serviceImpl.IInventoryRequisitionService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("InventoryRequisitionService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class InventoryRequisitionService implements IInventoryRequisitionService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private InventoryRequisitionRepository InventoryRequisitionDao;

	@Autowired
	private InventoryRequisitionPartRepository InventoryRequisitionPartDao;

	@Autowired
	private org.fleetopgroup.persistence.dao.InventoryTransferRepository InventoryTransferRepository;

	private static final int PAGE_SIZE = 10;

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	SimpleDateFormat dateName = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void add_InventoryRequisition(InventoryRequisition Requisition) throws Exception {

		InventoryRequisitionDao.save(Requisition);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void add_InventoryRequisitionPart(InventoryRequisitionParts Requisition) throws Exception {

		InventoryRequisitionPartDao.save(Requisition);
	}

	@Transactional
	public Page<InventoryRequisition> getDeployment_Page_InventoryRequisition(short Status, Integer pageNumber,
			Integer CompanyID) {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);

		return InventoryRequisitionDao.getDeployment_Page_InventoryRequisition(Status, CompanyID, request);
	}

	@Transactional
	public List<InventoryRequisitionDto> list_Of_All_Inventory_Requisition(short Status, Integer pageNumber,
			Integer CompanyID) {

		
		try {
			TypedQuery<Object[]> queryt = null;
			queryt = entityManager.createQuery(
					"SELECT IR.INVRID, IR.INVRID_NUMBER, PL.partlocation_name, UR.email, US.email, "
							+ " IR.REQUITED_DATE, IR.REQUISITION_STATUS_ID, IR.REQUITED_LOCATION_ID FROM InventoryRequisition as IR "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IR.REQUITED_LOCATION_ID "
							+ " INNER JOIN User UR ON UR.id = IR.REQUISITION_RECEIVER_ID "
							+ " INNER JOIN User US ON US.id = IR.REQUITED_SENDER_ID "
							+ " WHERE IR.REQUISITION_STATUS_ID =" + Status + " AND IR.COMPANY_ID =" + CompanyID
							+ " AND  IR.markForDelete=0  ORDER BY IR.INVRID desc",
					Object[].class);
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<InventoryRequisitionDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryRequisitionDto>();
				InventoryRequisitionDto renewal = null;
				for (Object[] result : results) {
					renewal = new InventoryRequisitionDto();

					renewal.setINVRID((Long) result[0]);
					renewal.setINVRID_NUMBER((Long) result[1]);
					renewal.setREQUITED_LOCATION((String) result[2]);
					renewal.setREQUISITION_RECEIVEDNAME((String) result[3]);
					renewal.setREQUITED_SENDNAME((String) result[4]);
					if ((Date) result[5] != null) {
						renewal.setREQUITED_DATE(dateName.format((Date) result[5]));
					}
					renewal.setREQUISITION_STATUS(InventoryRequisitionStatus.getInventoryRequisitionName((short) result[6]));
					renewal.setREQUITED_LOCATION_ID((Integer) result[7]);
					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	public List<InventoryRequisitionDto> Search_Of_All_Inventory_Requisition(Long INVRID_NUMBER, 
			Integer CompanyID) {
		
		try {
			TypedQuery<Object[]> queryt = null;
			queryt = entityManager.createQuery(
					"SELECT IR.INVRID, IR.INVRID_NUMBER, PL.partlocation_name, UR.email, US.email, "
							+ " IR.REQUITED_DATE, IR.REQUISITION_STATUS_ID, IR.REQUITED_LOCATION_ID FROM InventoryRequisition as IR "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IR.REQUITED_LOCATION_ID "
							+ " INNER JOIN User UR ON UR.id = IR.REQUISITION_RECEIVER_ID "
							+ " INNER JOIN User US ON US.id = IR.REQUITED_SENDER_ID "
							+ " WHERE IR.INVRID_NUMBER =" + INVRID_NUMBER + " AND IR.COMPANY_ID =" + CompanyID
							+ " AND  IR.markForDelete=0  ORDER BY IR.INVRID desc",
					Object[].class);

			
			List<Object[]> results = queryt.getResultList();

			List<InventoryRequisitionDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryRequisitionDto>();
				InventoryRequisitionDto renewal = null;
				for (Object[] result : results) {
					renewal = new InventoryRequisitionDto();

					renewal.setINVRID((Long) result[0]);
					renewal.setINVRID_NUMBER((Long) result[1]);
					renewal.setREQUITED_LOCATION((String) result[2]);
					renewal.setREQUISITION_RECEIVEDNAME((String) result[3]);
					renewal.setREQUITED_SENDNAME((String) result[4]);
					if ((Date) result[5] != null) {
						renewal.setREQUITED_DATE(dateName.format((Date) result[5]));
					}
					renewal.setREQUISITION_STATUS(InventoryRequisitionStatus.getInventoryRequisitionName((short) result[6]));
					renewal.setREQUITED_LOCATION_ID((Integer) result[7]);
					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}

	}
	
	@Transactional
	public InventoryRequisitionDto GET_Inventory_Requisition_ID(Long iNVRID, Integer Company_id) {

		// return InventoryRequisitionDao.GET_Inventory_Requisition_ID(iNVRID,
		// Company_id);

		Query query = entityManager.createQuery(
				"SELECT f.INVRID, f.INVRID_NUMBER, UR.email, f.REQUISITION_STATUS_ID, f.REQUITED_DATE, f.REQUITED_NUMBER, f.REQUITED_LOCATION_ID, "
						+ " PL.partlocation_name, f.REQUITED_REMARK, f.REQUITED_TOTALQTY, US.email, UT.email,"
						+ " f.CREATED_DATE, UU.email, f.LASTUPDATED_DATE, f.VID, V.vehicle_registration, f.REQUISITION_RECEIVER_ID "
						+ " from InventoryRequisition AS f "
						+ " INNER JOIN User UR ON UR.id = f.REQUISITION_RECEIVER_ID "
						+ " INNER JOIN User US ON US.id = f.REQUITED_SENDER_ID "
						+ " INNER JOIN User UT ON UT.id = f.CREATEDBYID "
						+ " INNER JOIN User UU ON UU.id = f.LASTMODIFIEDBYID "
						+ " LEFT JOIN Vehicle V ON V.vid = f.VID "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = f.REQUITED_LOCATION_ID"
						+ " where  f.INVRID = :invrID AND f.COMPANY_ID = :compID AND f.markForDelete = 0");

		query.setParameter("invrID", iNVRID);
		query.setParameter("compID", Company_id);
		Object[] inventory = null;
		try {
			inventory = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		InventoryRequisitionDto partRequisition;
		if (inventory != null) {

			partRequisition = new InventoryRequisitionDto();

			partRequisition.setINVRID((Long) inventory[0]);

			partRequisition.setINVRID_NUMBER((Long) inventory[1]);

			partRequisition.setREQUISITION_RECEIVEDNAME((String) inventory[2]);

			partRequisition.setREQUISITION_STATUS_ID((short) inventory[3]);

			partRequisition.setREQUISITION_STATUS(
					InventoryRequisitionStatus.getInventoryRequisitionName((short) inventory[3]));

			try {

				if (inventory[4] != null) {
					partRequisition.setREQUITED_DATE(dateName.format((Date) inventory[4]));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			partRequisition.setREQUITED_NUMBER((String) inventory[5]);

			partRequisition.setREQUITED_LOCATION_ID((Integer) inventory[6]);
			partRequisition.setREQUITED_LOCATION((String) inventory[7]);

			partRequisition.setREQUITED_REMARK((String) inventory[8]);

			partRequisition.setREQUITED_TOTALQTY((Double) inventory[9]);

			partRequisition.setREQUITED_SENDNAME((String) inventory[10]);

			partRequisition.setCREATEDBY((String) inventory[11]);
			if (inventory[12] != null) {
				partRequisition.setCREATED_DATE(dateFormatTime.format((Date) inventory[12]));
			}
			partRequisition.setLASTMODIFIEDBY((String) inventory[13]);
			if (inventory[14] != null) {
				partRequisition.setLASTUPDATED_DATE(dateFormatTime.format((Date) inventory[14]));
			}
			if (inventory[15] != null) 
			partRequisition.setVID((Integer) inventory[15]);
			if (inventory[16] != null) 
			partRequisition.setVehicle_registration((String) inventory[16]);
			partRequisition.setREQUISITION_RECEIVER_ID((Long) inventory[17]);

		} else {
			return null;
		}
		return partRequisition;
	}

	@Transactional
	public InventoryRequisitionParts GET_Inventory_Requisition_Part_ID(Long iNVRID, Integer Company_id) {

		return InventoryRequisitionPartDao.GET_Inventory_Requisition_Part_ID(iNVRID, Company_id);
	}

	@Transactional
	public List<InventoryRequisitionPartsDto> LIST_Inventory_Requisition_Part_ID(Long iNVRID, Integer Company_id) {

		/* this only Select column */
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.INVRPARTID, R.PART_ID, M.partname, M.partnumber, R.PART_REQUITED_QTY, R.PART_TRANSFER_QTY, R.INVRID "
						+ " FROM InventoryRequisitionParts AS R LEFT JOIN MasterParts AS M ON R.PART_ID=M.partid WHERE R.markForDelete=0 AND R.INVRID="
						+ iNVRID + " AND R.COMPANY_ID =" + Company_id + " ORDER BY R.INVRPARTID desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<InventoryRequisitionPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<InventoryRequisitionPartsDto>();
			InventoryRequisitionPartsDto list = null;
			for (Object[] result : results) {
				list = new InventoryRequisitionPartsDto();

				list.setINVRPARTID((Long) result[0]);
				list.setPART_ID((Long) result[1]);
				list.setPartname((String) result[2]);
				list.setPartnumber((String) result[3]);
				Double PART_REQUITED_QTY = 0.0, PART_TRANSFER_QTY = 0.0, PART_PENDING_QTY = 0.0;
				if (result[4] != null) {
					PART_REQUITED_QTY = (Double) result[4];
				}
				if (result[5] != null) {
					PART_TRANSFER_QTY = (Double) result[5];
				}
				PART_PENDING_QTY = PART_REQUITED_QTY - PART_TRANSFER_QTY;
				list.setPART_REQUITED_QTY(PART_REQUITED_QTY);
				list.setPART_TRANSFER_QTY(PART_TRANSFER_QTY);
				list.setPART_PENDING_QTY(PART_PENDING_QTY);
				list.setINVRID((Long) result[6]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public void DELETE_InventoryRequisitionParts_MARK(Long iNVRID, Integer Company_id) {
		try {
			InventoryRequisitionPartDao.DELETE_InventoryRequisitionParts_MARK(iNVRID, Company_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void Update_InventoryRequisition_SENT(short Status, String remarks, Long updateBY, Timestamp update_Date,
			Long iNVRID, Integer Company_id) {
		try {
			InventoryRequisitionDao.Update_InventoryRequisition_SENT(Status, remarks, updateBY, update_Date, iNVRID,
					Company_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public InventoryRequisitionPartsDto GET_Inventory_Requisition_ID_AND_Inventory_Requisition_PART(Long rEQ_PART_ID,
			Integer Company_id) {

		Query query = entityManager.createQuery(
				"SELECT f.INVRPARTID, f.PART_REQUITED_QTY, f.PART_TRANSFER_QTY,  f.PART_ID, M.INVRID, M.REQUITED_LOCATION_ID, "
						+ " M.REQUITED_SENDER_ID, UR.email, M.REQUISITION_RECEIVER_ID, PL.partlocation_name from InventoryRequisitionParts AS f "
						+ " INNER JOIN InventoryRequisition AS M ON f.INVRID=M.INVRID AND M.markForDelete=0 "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = M.REQUITED_LOCATION_ID "
						+ " INNER JOIN User UR ON UR.id = M.REQUISITION_RECEIVER_ID "
						+ " WHERE f.markForDelete=0 AND f.INVRPARTID= :id AND f.COMPANY_ID = :Company_id ");

		query.setParameter("id", rEQ_PART_ID);
		query.setParameter("Company_id", Company_id);
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		InventoryRequisitionPartsDto select;
		if (vehicle != null) {
			select = new InventoryRequisitionPartsDto();
			select.setINVRPARTID((Long) vehicle[0]);

			Double PART_REQUITED_QTY = 0.0, PART_TRANSFER_QTY = 0.0;
			if ((Double) vehicle[1] != null) {
				PART_REQUITED_QTY = (Double) vehicle[1];
			}
			if ((Double) vehicle[2] != null) {
				PART_TRANSFER_QTY = (Double) vehicle[2];
			}
			select.setPART_REQUITED_QTY(PART_REQUITED_QTY);
			select.setPART_ID((Long) vehicle[3]);
			select.setPART_TRANSFER_QTY(PART_TRANSFER_QTY);
			select.setINVRID((Long) vehicle[4]);
			select.setREQUITED_LOCATION_ID((Integer) vehicle[5]);
			select.setREQUITED_SENDER_ID((Long) vehicle[6]);
			select.setREQUITED_SENDNAME((String) vehicle[7]);
			select.setREQUISITION_RECEIVER_ID((Long) vehicle[8]);
			select.setREQUITED_LOCATION((String) vehicle[9]);

		} else {
			return null;
		}
		return select;
	}

	@Transactional
	public void UPDATE_TRANSFER_QTY_IN_PART(Double updateTransferQuantity, Long invrpartid, Integer Company_id) {

		try {
			InventoryRequisitionPartDao.UPDATE_TRANSFER_QTY_IN_PART(updateTransferQuantity, invrpartid, Company_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void DELETE_InventoryRequisition_MARK(Long deleted_byEmail, Timestamp update_Date, Long iNVRID,
			Integer Company_id) {

		try {
			InventoryRequisitionDao.DELETE_InventoryRequisition_MARK(deleted_byEmail, update_Date, iNVRID, Company_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ValueObject getSearchPartRequisition(ValueObject valueObject) throws Exception {
		String										dateRange				 = null;
		int											requisitionlocationId	 = 0;
		long										requisitionAssignedToId	 = 0;
		CustomUserDetails							userDetails				 = null;
		List<InventoryRequisitionDto> 				SearchPartRequisition	 = null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			requisitionlocationId		= valueObject.getInt("requisitionlocation", 0);
			requisitionAssignedToId		= valueObject.getLong("requisitionAssignedTo", 0);
			dateRange					= valueObject.getString("dateRange");
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = From_TO_DateRange[0];
				dateRangeTo = From_TO_DateRange[1]+" "+DateTimeUtility.DAY_END_TIME;
				
				String requisitionlocationName = "", requisitionAssignedToName = "", Date = "";
				
				
				if(requisitionlocationId > 0)
				{
					requisitionlocationName = " AND IR.REQUITED_LOCATION_ID = "+ requisitionlocationId +" ";
				}
				
				if(requisitionAssignedToId > 0 )
				{
					requisitionAssignedToName = " AND IR.REQUISITION_RECEIVER_ID = "+ requisitionAssignedToId +" ";
				}
				
				Date =  " AND IR.REQUITED_DATE between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
				String query = " " + requisitionlocationName + " " + requisitionAssignedToName + " " + Date + " ";
				
				
				SearchPartRequisition = getSearchPartRequisition_List(query, userDetails.getCompany_id());
			}
			
			valueObject.put("SearchPartRequisition", SearchPartRequisition);
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			SearchPartRequisition	= null;
			userDetails				= null;
			dateRange				= null;
		}
	}
	
	@Transactional
	public List<InventoryRequisitionDto> getSearchPartRequisition_List(String Query, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			queryt = entityManager.createQuery(
					"SELECT IR.INVRID, IR.INVRID_NUMBER, PL.partlocation_name, UR.email, US.email, "
							+ " IR.REQUITED_DATE, IR.REQUISITION_STATUS_ID, IR.REQUITED_LOCATION_ID FROM InventoryRequisition as IR "
							+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IR.REQUITED_LOCATION_ID "
							+ " INNER JOIN User UR ON UR.id = IR.REQUISITION_RECEIVER_ID "
							+ " INNER JOIN User US ON US.id = IR.REQUITED_SENDER_ID "
							+ " WHERE IR.COMPANY_ID = " + companyId +" "
							+ " AND IR.markForDelete = 0 " + Query + "  ORDER BY IR.INVRID desc ",
					Object[].class);
			
			List<Object[]> results = queryt.getResultList();

			List<InventoryRequisitionDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InventoryRequisitionDto>();
				InventoryRequisitionDto renewal = null;
				for (Object[] result : results) {
					renewal = new InventoryRequisitionDto();

					renewal.setINVRID((Long) result[0]);
					renewal.setINVRID_NUMBER((Long) result[1]);
					renewal.setREQUITED_LOCATION((String) result[2]);
					renewal.setREQUISITION_RECEIVEDNAME((String) result[3]);
					renewal.setREQUITED_SENDNAME((String) result[4]);
					if ((Date) result[5] != null) {
						renewal.setREQUITED_DATE(dateName.format((Date) result[5]));
					}
					renewal.setREQUISITION_STATUS_ID((short) result[6]);
					renewal.setREQUISITION_STATUS(InventoryRequisitionStatus.getInventoryRequisitionName((short) result[6]));
					renewal.setREQUITED_LOCATION_ID((Integer) result[7]);
					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
		
	}
	@Transactional
	public List<InventoryRequisitionDto> list_Of_All_InventoryParts_Requisition(long INVRID , Integer companyID)throws Exception {
		List<InventoryRequisitionDto> 	reqisitionPartList	= null;
		TypedQuery<Object[]> 			queryt 				= null;
		InventoryRequisitionDto 		renewal 			= null;
		try {
			queryt = entityManager.createQuery(
				"SELECT IR.INVRPARTID, IR.PART_ID, IR.PART_REQUITED_QTY, IR.PART_TRANSFER_QTY, IR.INVRID,M.partname "
						+ " FROM InventoryRequisitionParts as IR "
						+ " INNER JOIN MasterParts AS M ON M.partid = IR.PART_ID "
						+ " WHERE IR.INVRID = "+ INVRID +" AND IR.COMPANY_ID= "+companyID+" "
						+ " AND IR.markForDelete = 0 ORDER BY IR.INVRID desc ",
				Object[].class);
			
			List<Object[]> results = queryt.getResultList();
			if (results != null && !results.isEmpty()) {
				reqisitionPartList = new ArrayList<InventoryRequisitionDto>();
				
				for (Object[] result : results) {
					renewal = new InventoryRequisitionDto();

					renewal.setINVRPARTID((Long) result[0]);
					renewal.setPART_ID((Long) result[1]);
					renewal.setPART_REQUITED_QTY((Double) result[2]);
					renewal.setPART_TRANSFER_QTY((Double) result[3]);
					renewal.setINVRID((Long) result[4]);
					renewal.setPART_NAME((String) result[5]);
					reqisitionPartList.add(renewal);
				}
			}
			return reqisitionPartList;
			
		} catch (Exception e) {
			System.err.println("ERR"+e);
			throw e;
		}
	}
	
	public Page<InventoryRequisitionParts> getDeployment_Page_ShowInPartRequisitionDetailsByINVRID(Integer pageNumber,Long INVRID,Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "INVRPARTID");
		return InventoryRequisitionPartDao.getDeployment_Page_ShowInPartRequisitionDetailsByINVRID(companyId,INVRID,pageable);
	}
	
	@Transactional
	public ValueObject getPartRequisitionDetailsByINVRID(ValueObject valueObject)throws Exception {
		CustomUserDetails 				userDetails			= null;
		Integer							pageNumber			= 0;
		List<InventoryRequisitionDto> 	reqisitionPartList	= null;
		TypedQuery<Object[]> 			queryt 				= null;
		InventoryRequisitionDto 		renewal 			= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber	 		= valueObject.getInt("pageNumber",0);
			Page<InventoryRequisitionParts> page = getDeployment_Page_ShowInPartRequisitionDetailsByINVRID(pageNumber, valueObject.getLong("INVRID"),userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			valueObject.put("SelectPage", pageNumber);
			
			
			queryt = entityManager.createQuery(
					"SELECT IR.INVRPARTID, IR.PART_ID, IR.PART_REQUITED_QTY, IR.PART_TRANSFER_QTY, IR.INVRID,M.partname "
							+ " FROM InventoryRequisitionParts as IR "
							+ " INNER JOIN MasterParts AS M ON M.partid = IR.PART_ID "
							+ " WHERE IR.INVRID = "+ valueObject.getLong("INVRID") +" AND IR.COMPANY_ID= "+userDetails.getCompany_id()+" "
							+ " AND IR.markForDelete = 0 ORDER BY IR.INVRID desc ",
					Object[].class);
			
				queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
				queryt.setMaxResults(PAGE_SIZE);
				
				List<Object[]> results = queryt.getResultList();
				if (results != null && !results.isEmpty()) {
					reqisitionPartList = new ArrayList<InventoryRequisitionDto>();
					
					for (Object[] result : results) {
						renewal = new InventoryRequisitionDto();

						renewal.setINVRPARTID((Long) result[0]);
						renewal.setPART_ID((Long) result[1]);
						renewal.setPART_REQUITED_QTY((Double) result[2]);
						renewal.setPART_TRANSFER_QTY((Double) result[3]);
						renewal.setINVRID((Long) result[4]);
						renewal.setPART_NAME((String) result[5]);
						reqisitionPartList.add(renewal);
					}
				}
			valueObject.put("partRequisitionList", reqisitionPartList);
			return valueObject;
		} catch (Exception e) {
			System.err.println("ERR"+e);
			throw e;
		}
	}
	@Transactional
	public ValueObject rejectParRequisitionByInventory_transfer_id(ValueObject valueObject)throws Exception {
		CustomUserDetails 				userDetails			= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			java.util.Date currentDate = new java.util.Date();
			Timestamp rejectedDate = new java.sql.Timestamp(currentDate.getTime());
			InventoryTransferRepository.Reject_InventoryTransfer_History_Delete_By_ID(valueObject.getLong("TransferID"), userDetails.getCompany_id(),InventoryTransferStatus.REJECTED,valueObject.getString("rejectRemark"),rejectedDate );
			return valueObject;
			} catch (Exception e) {
			System.err.println("ERR"+e);
			throw e;
		}
	}
	
	

}
