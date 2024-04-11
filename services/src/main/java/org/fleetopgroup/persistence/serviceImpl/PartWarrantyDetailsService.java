package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.DealerServiceEntriesConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.bl.PartWarrantyBL;
import org.fleetopgroup.persistence.bl.PartWarrantyDetailsBL;
import org.fleetopgroup.persistence.dao.PartWarrantyDetailsHistoryRepository;
import org.fleetopgroup.persistence.dao.PartWarrantyDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.PartWarrantyDetailsDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.PartWarrantyDetails;
import org.fleetopgroup.persistence.model.PartWarrantyDetailsHistory;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToParts;
import org.fleetopgroup.persistence.report.dao.IPartReportDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartWarrantyDetailsService implements IPartWarrantyDetailsService {

	@Autowired  PartWarrantyDetailsRepository			partWarrantyDetailsDao;
	@Autowired 	IInventoryService						inventoryService;
	@Autowired 	IWorkOrdersService						workOrdersService;
	@Autowired 	PartWarrantyDetailsHistoryRepository	historyRepository;
	@Autowired 	IMasterPartsService						masterPartsService;
	@Autowired  IPartReportDao							partReportDao;
	@Autowired	IUserProfileService						userProfileService;
	
	@PersistenceContext EntityManager entityManager;
	
	@Override
	public ValueObject getPartWarrantyDetails(ValueObject valueObject) throws Exception {
		
		valueObject.put("partWarranty", partWarrantyDetailsDao.getPartWarrantyDetailsList(valueObject.getLong("inventoryId",0)));
		
		return valueObject;
	}
	
	@Override
	public ValueObject getWarrantyDetailsList(ValueObject valueObject) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("partWarranty", partWarrantyDetailsDao.getPartWarrantyDetailsListByPartId(
					     valueObject.getLong("partId",0), userDetails.getCompany_id(), valueObject.getInt("locationId",0)));
			
			valueObject.put("assignedParts", getAssignedPartList(valueObject, userDetails));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private List<PartWarrantyDetailsDto>  getAssignedPartList(ValueObject valueObject, CustomUserDetails	userDetails) throws Exception{
		TypedQuery<Object[]> 				query 							= null;
		List<PartWarrantyDetailsDto>		partWarrantyDetailsList			= null;
		PartWarrantyDetailsDto				partWarrantyDetailsDto			= null;
		
		try {
			query = entityManager.createQuery(
					  " SELECT P.partWarrantyDetailsId, P.partAsignDate, P.warrantyEndDate, "
					+ " P.partSerialNumber, P.serviceId, P.replacePartWarrantyDetailsId, PW.partSerialNumber, "
					+ " P.replaceInServiceId, P.isOldPartReceived, P.partId "
					+ " FROM PartWarrantyDetails AS P "
					+ " LEFT JOIN PartWarrantyDetails AS PW ON PW.partWarrantyDetailsId = P.replacePartWarrantyDetailsId "
					+ " where  P.servicePartId = "+valueObject.getLong("servicePartId",0)+" AND P.companyId = "+userDetails.getCompany_id()+" "
					+ " AND P.assignedFrom = 2 AND P.markForDelete = 0 ",
					Object[].class);
			List<Object[]> results =  null;
			try {
				results = query.getResultList();
			} catch (Exception e) {
				
			}
		

			if (results != null && !results.isEmpty()) {
				partWarrantyDetailsList = new ArrayList<PartWarrantyDetailsDto>();
				
				for (Object[] result : results) {
					partWarrantyDetailsDto = new PartWarrantyDetailsDto();

					partWarrantyDetailsDto.setPartWarrantyDetailsId((Long) result[0]);
					partWarrantyDetailsDto.setPartAsignDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[1], DateTimeUtility.DD_MM_YYYY));
					partWarrantyDetailsDto.setWarrantyEndDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[2], DateTimeUtility.DD_MM_YYYY));
					partWarrantyDetailsDto.setPartSerialNumber((String) result[3]);
					partWarrantyDetailsDto.setServiceId((Long) result[4]);
					if(result[5] != null) {
						partWarrantyDetailsDto.setReplacePartWarrantyDetailsId((Long) result[5]);
					}
					if(result[6] != null) {
						partWarrantyDetailsDto.setReplacePartSerialNumber((String) result[6]);
					}else {
						partWarrantyDetailsDto.setReplacePartSerialNumber("--");
					}
					if(result[7] != null && (Long) result[7] > 0) {
						partWarrantyDetailsDto.setReplaceInServiceId((Long) result[7]);
					}
					if(result[8] != null) {
						partWarrantyDetailsDto.setOldPartReceived((boolean) result[8]);
						if(partWarrantyDetailsDto.isOldPartReceived()) {
							partWarrantyDetailsDto.setIsOldPartReceivedStr("Yes");
						}else {
							partWarrantyDetailsDto.setIsOldPartReceivedStr("No");
						}
					}
					partWarrantyDetailsDto.setPartId((Long) result[9]);
					
					partWarrantyDetailsList.add(partWarrantyDetailsDto);
				}
			}
			
			return partWarrantyDetailsList;
		
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveWarrantySerialNumber(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject> 			dataArrayObjColl 			= null;
		List<PartWarrantyDetails> 		partWarrantyDetailsList		= null;
		CustomUserDetails				userDetails					= null;
		MasterParts						masterParts					= null;
		List<String>                    addedSerialNumbers			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("serialDetails");
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				addedSerialNumbers		= new ArrayList<String>();
				partWarrantyDetailsList	= new ArrayList<>();
				for (ValueObject object : dataArrayObjColl) {
					if(!object.getString("partSerialNumber", "").trim().equalsIgnoreCase("")) {
						
						if(!addedSerialNumbers.contains(object.getString("partSerialNumber", "").trim())) {
							addedSerialNumbers.add(object.getString("partSerialNumber", "").trim());
							List<PartWarrantyDetails>  validate = partWarrantyDetailsDao
									.getPartWarrantyDetailsList(object.getString("partSerialNumber", "").trim(), userDetails.getCompany_id());
							if(validate == null || validate.isEmpty()) {
								masterParts	= masterPartsService.getMasterParts(valueObject.getLong("partId",0));
								partWarrantyDetailsList.add(PartWarrantyDetailsBL.getPartWarrantyDetails(valueObject, object, userDetails, masterParts));
							}
						}
						
					}
				}
				if(partWarrantyDetailsList != null && !partWarrantyDetailsList.isEmpty()) {
					partWarrantyDetailsDao.saveAll(partWarrantyDetailsList);
					
					inventoryService.updateSerialNoAddedForParts(valueObject.getLong("inventoryId",0), userDetails.getCompany_id(), partWarrantyDetailsList.size());
				}
				
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getWarrantyPartDetailsByDseId(ValueObject valueObject) throws Exception {
		List<PartWarrantyDetailsDto>		partWarrantyDetailsList			= null;
		List<PartWarrantyDetailsDto>		assignedPartWarrantyDetailsList			= null;
		String								serviceIdQuery						= "";
		String								statusIdQuery						= "";
		String								assignStatusIdQuery						= "";
		
		try {
			serviceIdQuery = "AND P.serviceId ="+valueObject.getLong("serviceId")+"";
			statusIdQuery = "AND P.partWarrantyStatusId ="+valueObject.getShort("partWarrantyStatusId")+" AND P.servicePartId = "+valueObject.getLong("dealerServiceEntiresPartId")+" ";
			valueObject.put("serviceIdQuery", serviceIdQuery);
			valueObject.put("statusIdQuery", statusIdQuery);
			partWarrantyDetailsList = getWarrantyPartDetailsLsit(valueObject);
			
			
			assignStatusIdQuery = "AND P.partWarrantyStatusId ="+PartWarrantyDetailsBL.ASSIGNED+" AND P.servicePartId = "+valueObject.getLong("dealerServiceEntiresPartId")+" ";
			valueObject.put("statusIdQuery", assignStatusIdQuery);
			assignedPartWarrantyDetailsList = getWarrantyPartDetailsLsit(valueObject);
			valueObject.put("assignedPartWarrantyDetailsList", assignedPartWarrantyDetailsList);
			valueObject.put("partWarrantyDetailsList", partWarrantyDetailsList);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject underWarrantyPartDetailsList(String term ,ValueObject valueObject) throws Exception {
		List<PartWarrantyDetailsDto>		partWarrantyDetailsList			= null;
		String								serviceIdQuery						= "";
		String								invoiceDateQuery				= "";
		String								statusIdQuery						= "";
		try {
			invoiceDateQuery = " AND P.warrantyEndDate > '"+valueObject.getString("invoiceDate")+"'";
			serviceIdQuery = "AND P.serviceId <>"+valueObject.getLong("serviceId")+"";
			statusIdQuery = "AND P.partWarrantyStatusId ="+valueObject.getShort("partWarrantyStatusId")+"";
			valueObject.put("invoiceDateQuery", invoiceDateQuery);
			valueObject.put("serviceIdQuery", serviceIdQuery);
			valueObject.put("statusIdQuery", statusIdQuery);
			partWarrantyDetailsList = getWarrantyPartDetailsLsit(valueObject);
			valueObject.put("partWarrantyDetailsList", partWarrantyDetailsList);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	 @Override
	public List<PartWarrantyDetailsDto> getAlreadyAsignedPartsByVid(String term, ValueObject valueObject) throws Exception {
			TypedQuery<Object[]> 				query 							= null;
			List<PartWarrantyDetailsDto>		partWarrantyDetailsList			= null;
			PartWarrantyDetailsDto				partWarrantyDetailsDto			= null;
			
			try {
				query = entityManager.createQuery(
						  " SELECT P.partWarrantyDetailsId, P.partSerialNumber, P.partAsignDate, "
						+ " P.warrantyEndDate, P.serviceId, WO.workorders_Number"
						+ " FROM PartWarrantyDetails AS P "
						+ " INNER JOIN WorkOrders WO ON WO.workorders_id = P.serviceId AND WO.workorders_statusId = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+" "
						+ " where  lower(P.partSerialNumber) Like (:term)  AND P.companyId = "+valueObject.getInt("companyId")+" AND P.vid = "+valueObject.getInt("vid")+" "
						+ " AND P.partId = "+valueObject.getLong("partId",0)+" AND P.serviceId <> "+valueObject.getLong("serviceId",0)+" "
						+ " AND  P.markForDelete = 0 and P.status = "+PartWarrantyDetailsBL.ASSIGNED+" ",
						Object[].class);
				
				query.setParameter("term", "%"+term+"%");
				
				List<Object[]> results = query.getResultList();

				if (results != null && !results.isEmpty()) {
					partWarrantyDetailsList = new ArrayList<PartWarrantyDetailsDto>();
					
					for (Object[] result : results) {
						partWarrantyDetailsDto = new PartWarrantyDetailsDto();

						partWarrantyDetailsDto.setPartWarrantyDetailsId((Long) result[0]);
						partWarrantyDetailsDto.setPartSerialNumber((String) result[1]);
						partWarrantyDetailsDto.setPartAsignDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[2], DateTimeUtility.DD_MM_YYYY));
						if(result[3] != null)
						partWarrantyDetailsDto.setWarrantyEndDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[3], DateTimeUtility.DD_MM_YYYY));
						partWarrantyDetailsDto.setPartId(valueObject.getLong("partId",0));
						partWarrantyDetailsDto.setServiceId((Long) result[4]);
						partWarrantyDetailsDto.setServiceNumber("WO-"+result[5]);
						
						partWarrantyDetailsList.add(partWarrantyDetailsDto);
					}
				}
				
				return partWarrantyDetailsList;
			
				
			} catch (Exception e) {
				throw e;
			}
		}
	
	@Override
	public List<PartWarrantyDetailsDto> getWarrantyPartDetailsLsit(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 				query 							= null;
		List<PartWarrantyDetailsDto>		partWarrantyDetailsList			= null;
		PartWarrantyDetailsDto				partWarrantyDetailsDto			= null;
		
		try {
			query = entityManager.createQuery(
					  " SELECT P.partWarrantyDetailsId, M.partname, M.partnumber, P.partAsignDate, P.warrantyEndDate, "
					+ " DSE.dealerServiceEntriesNumber,P.partSerialNumber, P.serviceId, P.replacePartWarrantyDetailsId, PW.partSerialNumber, "
					+ " P.replaceInServiceId, DSE1.dealerServiceEntriesNumber, P.isOldPartReceived, P.partId FROM PartWarrantyDetails AS P "
					+ " INNER JOIN MasterParts AS M ON M.partid = P.partId "
					+ " LEFT JOIN DealerServiceEntries AS DSE ON DSE.dealerServiceEntriesId = P.serviceId AND DSE.markForDelete = 0 "
					+ " LEFT JOIN PartWarrantyDetails AS PW ON PW.partWarrantyDetailsId = P.replacePartWarrantyDetailsId "
					+ " LEFT JOIN DealerServiceEntries AS DSE1 ON DSE1.dealerServiceEntriesId = P.replaceInServiceId AND DSE1.markForDelete = 0 "
					+ " where P.companyId = "+valueObject.getInt("companyId")+" AND P.vid = "+valueObject.getInt("vid")+" AND P.partId = "+valueObject.getLong("partId")+" "
					+ " AND  P.markForDelete = 0  "+valueObject.getString("serviceIdQuery","")+" "+valueObject.getString("invoiceDateQuery","")+"  "+valueObject.getString("statusIdQuery","")+"",
					Object[].class);
			
			
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				partWarrantyDetailsList = new ArrayList<PartWarrantyDetailsDto>();
				
				for (Object[] result : results) {
					partWarrantyDetailsDto = new PartWarrantyDetailsDto();

					partWarrantyDetailsDto.setPartWarrantyDetailsId((Long) result[0]);
					partWarrantyDetailsDto.setPartName((String) result[1]);
					partWarrantyDetailsDto.setPartNumber((String) result[2]);
					partWarrantyDetailsDto.setPartAsignDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[3], DateTimeUtility.DD_MM_YYYY));
					partWarrantyDetailsDto.setWarrantyEndDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[4], DateTimeUtility.DD_MM_YYYY));
					partWarrantyDetailsDto.setServiceNumber("DSE-"+(Long) result[5]);
					partWarrantyDetailsDto.setPartSerialNumber((String) result[6]);
					partWarrantyDetailsDto.setServiceId((Long) result[7]);
					if(result[8] != null) {
						partWarrantyDetailsDto.setReplacePartWarrantyDetailsId((Long) result[8]);
					}
					if(result[9] != null) {
						partWarrantyDetailsDto.setReplacePartSerialNumber((String) result[9]);
					}else {
						partWarrantyDetailsDto.setReplacePartSerialNumber("--");
					}
					if(result[10] != null && (Long) result[10] > 0) {
						partWarrantyDetailsDto.setReplaceInServiceId((Long) result[10]);
						partWarrantyDetailsDto.setReplaceInServiceNumber("DSE-"+(Long) result[11]);
					}else {
						partWarrantyDetailsDto.setReplaceInServiceNumber("--");
					}
					if(result[12] != null) {
						partWarrantyDetailsDto.setOldPartReceived((boolean) result[12]);
						if(partWarrantyDetailsDto.isOldPartReceived()) {
							partWarrantyDetailsDto.setIsOldPartReceivedStr("Yes");
						}else {
							partWarrantyDetailsDto.setIsOldPartReceivedStr("No");
							
						}
					}
					partWarrantyDetailsDto.setPartId((Long) result[13]);
					partWarrantyDetailsList.add(partWarrantyDetailsDto);
				}
			}
			
			return partWarrantyDetailsList;
		
			
		} catch (Exception e) {
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ValueObject updatePartWarrantyStatus(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject>					partWarrantyArrayObj						= null;
		PartWarrantyDetailsDto					partWarrantyDetails							= null;
		boolean									validateSerialNumber						= false;
		String									validateMessage								= "";
		try {
			if(valueObject.getShort("dseStatusId") == DealerServiceEntriesConstant.DSE_PAYMENT_PENDING_STATUS_ID || valueObject.getShort("dseStatusId") == DealerServiceEntriesConstant.DSE_ACCOUNT_CLOSE_STATUS_ID) {
				valueObject.put("dseCompleted", true);
				return valueObject;	
			}
			
			partWarrantyArrayObj 					= new ArrayList<>();
			partWarrantyArrayObj	= (ArrayList<ValueObject>) valueObject.get("partWarrantyDetails");
			
		for (ValueObject object : partWarrantyArrayObj) {
			if(object.getString("serialNumber","").equals("")) {
				continue;
			}
			 
			partWarrantyDetails = validatePartWarrantyDetils(object,valueObject);
			 
			if(partWarrantyDetails != null) {
				validateSerialNumber = true;
				validateMessage = " "+object.getString("serialNumber")+"  , "+validateMessage+" ";
				continue;
			}
			entityManager.createQuery(
					"Update PartWarrantyDetails SET partWarrantyStatusId ="+object.getShort("prePartWarrantyStatusId")+""
							+ "WHERE partWarrantyDetailsId = " + object.getLong("prePartWarrantyDetailsId") + "  AND companyId = " + valueObject.getInt("companyId")+ " ").executeUpdate();
			entityManager.createQuery(
				"Update PartWarrantyDetails SET partSerialNumber = '"+object.getString("serialNumber","")+"', partWarrantyStatusId ="+object.getShort("partWarrantyStatusId")+","
						+ " isOldPartReceived = "+object.getBoolean("isOldPartReceived",false)+",replacePartWarrantyDetailsId ="+object.getLong("prePartWarrantyDetailsId")+","
						+ " replaceInServiceId ="+object.getLong("preServiceId")+" , assignedFrom ="+PartWarrantyDetailsBL.ASSIGNED_FROM_DSE+" "
						+ " WHERE partWarrantyDetailsId = " + object.getLong("partWarrantyDetailsId") + " "
						+ " AND companyId = " + valueObject.getInt("companyId")+ " ").executeUpdate();
		}
		
		valueObject.put("validateSerialNumber", validateSerialNumber);
		if(!validateMessage.equals("")) {
			valueObject.put("validateMessage", validateMessage);
		}
			return valueObject;	
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject showAllWarrantyPart(ValueObject valueObject) throws Exception {
		List<PartWarrantyDetailsDto>		partWarrantyDetailsList			= null;
		String								serviceIdQuery						= "";
		String								invoiceDateQuery				= "";
		String								statusIdQuery						= "";
		
		try {
			invoiceDateQuery = " AND P.warrantyEndDate > '"+valueObject.getString("invoiceDate")+"'";
			serviceIdQuery = "AND P.serviceId <>"+valueObject.getLong("serviceId")+"";
			statusIdQuery = "AND P.partWarrantyStatusId ="+valueObject.getShort("partWarrantyStatusId")+"";
			valueObject.put("invoiceDateQuery", invoiceDateQuery);
			valueObject.put("serviceIdQuery", serviceIdQuery);
			valueObject.put("statusIdQuery", statusIdQuery);
			partWarrantyDetailsList = getWarrantyPartDetailsLsit(valueObject);
			valueObject.put("partWarrantyDetailsList", partWarrantyDetailsList);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Transactional
	@Override
	public ValueObject removeAssignPartWarrantyDetails(ValueObject valueObject) throws Exception {
		PartWarrantyDetails partWarrantyDetails = null;
		try {
			
			partWarrantyDetails = 	partWarrantyDetailsDao.getPartWarrantyDetailsById(valueObject.getLong("partWarrantyDetailsId"),valueObject.getInt("companyId"));
			
			if(partWarrantyDetails != null) {
				if(partWarrantyDetails.getReplaceInServiceId() > 0) {
					entityManager.createQuery(
							"Update PartWarrantyDetails SET partWarrantyStatusId ="+PartWarrantyDetailsBL.ASSIGNED+" "
									+ "WHERE partWarrantyDetailsId = " + partWarrantyDetails.getReplacePartWarrantyDetailsId() + "  AND companyId = " + valueObject.getInt("companyId")+ " ").executeUpdate();
				}
				
				
				entityManager.createQuery(
				"Update PartWarrantyDetails SET partWarrantyStatusId ="+PartWarrantyDetailsBL.UNASIGNED+", partSerialNumber = '', "
						+ " isOldPartReceived = 0, replaceInServiceId = 0, replacePartWarrantyDetailsId = 0 "
						+ "WHERE partWarrantyDetailsId = " + valueObject.getLong("partWarrantyDetailsId") + "  AND companyId = " + valueObject.getInt("companyId")+ " ").executeUpdate();
			}
			
			
			
			
			
			return valueObject;	
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public PartWarrantyDetailsDto validatePartWarrantyDetils(ValueObject object,ValueObject valueObject) throws Exception {
		try {
			Query query  = entityManager
			.createQuery("SELECT partWarrantyDetailsId, partSerialNumber FROM PartWarrantyDetails "
					+ " where partSerialNumber = '"+object.getString("serialNumber","")+"' AND "
					+ " companyId = "+valueObject.getInt("companyId")+" AND markForDelete = 0 ORDER BY partWarrantyDetailsId");
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			PartWarrantyDetailsDto	partWarrantyDetailsDto = null;
			
				if (result != null) {
					partWarrantyDetailsDto = new PartWarrantyDetailsDto();
					
					partWarrantyDetailsDto.setPartWarrantyDetailsId((Long) result[0]);
					partWarrantyDetailsDto.setServiceNumber((String) result[1]);
				}
				return partWarrantyDetailsDto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject savePartWithWarrantyDetails(ValueObject valueObject) throws Exception {
			//PartWarrantyDetails			partWarrantyDetails			= null;
			ArrayList<ValueObject> 			dataArrayObjColl 			= null;
			CustomUserDetails				userDetails					= null;
			PartWarrantyDetailsHistory		detailsHistory				= null;		
			try {
				
				userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				valueObject.put("userId", userDetails.getCompany_id());
				valueObject.put("companyId", userDetails.getId());
				
				dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("partWarrantyDetails");
				
				if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					WorkOrdersTasksToParts	validate =	workOrdersService.validateAssignedNoOfParts(valueObject.getLong("taskId",0));
					PartWarrantyDetails	prePartDetails = null;
					
					if(validate != null && validate.getQuantity() >= (dataArrayObjColl.size() + validate.getAssignedNoPart())) {
						for (ValueObject object : dataArrayObjColl) {
							
							PartWarrantyDetails	warrantyDetails	=			partWarrantyDetailsDao.getPartWarrantyDetailsById(object.getLong("partWarrantyDetailsId"), userDetails.getCompany_id());
							
							if(warrantyDetails != null && warrantyDetails.getStatus() == PartWarrantyDetailsBL.UNASIGNED) {
								entityManager.createQuery(
										"Update PartWarrantyDetails SET partWarrantyStatusId = "+PartWarrantyDetailsBL.ASSIGNED+", "
												+ " status = "+PartWarrantyDetailsBL.ASSIGNED+", vid = "+valueObject.getInt("vid",0)+","
												+ "serviceId = "+valueObject.getLong("workorders_id",0)+", assignedFrom = 2 , partAsignDate = '"+DateTimeUtility.getCurrentDateInString()+"' "
												+ " , servicePartId = "+valueObject.getLong("taskId",0)+" , replacePartWarrantyDetailsId = "+object.getLong("prePartWarrantyDetailsId",0)+" "
												+ " WHERE partWarrantyDetailsId = " + object.getLong("partWarrantyDetailsId") + "  "
												+ " AND companyId = " + userDetails.getCompany_id()+ " ").executeUpdate();
							
								if(object.getLong("prePartWarrantyDetailsId",0) > 0) {
									
									
									PartWarrantyDetails	preWarrantyDetails	=			partWarrantyDetailsDao.getPartWarrantyDetailsById(object.getLong("prePartWarrantyDetailsId"), userDetails.getCompany_id());
									short status = 0;
									if(preWarrantyDetails.getWarrantyEndDate() != null && preWarrantyDetails.getWarrantyEndDate().after(DateTimeUtility.getCurrentDate())) {
										status	= PartWarrantyDetailsBL.REPLACE_UNDER_WARRANTY;
									}else {
										status	= PartWarrantyDetailsBL.REPLACE_AFTER_WARRANTY;
									}
									
									detailsHistory	= PartWarrantyBL.getPartWarrantyHistoryDto(userDetails.getId(), preWarrantyDetails);
									detailsHistory.setHistoryTypeId(PartWarrantyBL.PART_WARRANTY_HISTORY_TYPE_UNASSIGN);
									
									historyRepository.save(detailsHistory);
									
									entityManager.createQuery(
											"Update PartWarrantyDetails SET partWarrantyStatusId = "+status+""
													+ " , status = "+status+" "
													+ ", replaceInServiceId = "+valueObject.getLong("workorders_id",0)+" "
													+ " WHERE partWarrantyDetailsId = " + object.getLong("prePartWarrantyDetailsId") + "  "
													+ " AND companyId = " + userDetails.getCompany_id()+ " ").executeUpdate();
								
								}
								
								detailsHistory	= PartWarrantyBL.getPartWarrantyHistoryDto(userDetails.getId(), warrantyDetails);	
								detailsHistory.setHistoryTypeId(PartWarrantyBL.PART_WARRANTY_HISTORY_TYPE_ASSIGN);
							
								historyRepository.save(detailsHistory);
								
							}else {
								valueObject.put("validationFailed", true);
							}
						}
						
						workOrdersService.updateAssignedNoOfPart(valueObject.getLong("taskId",0), dataArrayObjColl.size());
						valueObject.put("success", true);
					}else {
						valueObject.put("validationFailed", true);
					}
				}
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
	}
	
	@Override
	public List<PartWarrantyDetails> getPartWarrantyDetailsByServiceId(long servicePartId, short type) {
		
		return partWarrantyDetailsDao.getPartWarrantyDetailsByServiceId(servicePartId, type);
	}
	
	@Override
	@Transactional
	public void deleteWarrantyPartByInventoryId(Long inventoryId) throws Exception {
		
		partWarrantyDetailsDao.deleteWarrantyPartByInventoryId(inventoryId);
	}
	
	@Override
	@Transactional
	public List<PartWarrantyDetailsDto> getAssetNumberByPartIdAndInventoryId(long partId, long inventoryId, int companayId) throws Exception {
		TypedQuery<Object[]> queryt =  null;
			queryt = entityManager.createQuery(
					"SELECT PWD.partWarrantyDetailsId, PWD.partId, PWD.inventoryId, PWD.partSerialNumber, MP.partnumber, MP.partname, PWD.repairStatusId "
							+ " FROM PartWarrantyDetails AS PWD "
							+ " INNER JOIN MasterParts AS MP ON MP.partid = PWD.partId "
							+ " WHERE PWD.partId ="+partId+" AND PWD.repairStatusId = 0 AND PWD.inventoryId = "+inventoryId+" AND PWD.companyId = "+companayId+" "
							+ " AND PWD.markForDelete = 0 ",
					Object[].class);
			
			
		List<Object[]> results = queryt.getResultList();

		List<PartWarrantyDetailsDto> partWarrantyDetailsDtoList = null;
		if (results != null && !results.isEmpty()) {
			partWarrantyDetailsDtoList = new ArrayList<>();
			PartWarrantyDetailsDto partWarrantyDetailsDto = null;
			for (Object[] result : results) {
				partWarrantyDetailsDto = new PartWarrantyDetailsDto();
				partWarrantyDetailsDto.setPartWarrantyDetailsId((Long) result[0]);
				partWarrantyDetailsDto.setPartId((Long) result[1]);
				partWarrantyDetailsDto.setInventoryId((Long) result[2]);
				partWarrantyDetailsDto.setPartSerialNumber((String) result[3]);
				partWarrantyDetailsDto.setPartNumber((String) result[4]);
				partWarrantyDetailsDto.setPartName((String) result[5]);
				partWarrantyDetailsDto.setRepairStatusId((short) result[6]);
				partWarrantyDetailsDto.setRepairStatus(PartWarrantyDetailsBL.getRepairStatus((short) result[6]));
				partWarrantyDetailsDtoList.add(partWarrantyDetailsDto);
			}
		}
		
		return partWarrantyDetailsDtoList;
		
	}
	
	public void updateRepairStatus(Long assetId, short statusId, Long repairToStockDetailsId) {
		 
		try {
			partWarrantyDetailsDao.updateRepairStatus(assetId, statusId, repairToStockDetailsId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public void transferAssetNumber(long repairStockId, int locationId) {
		
		try {
			partWarrantyDetailsDao.transferAssetNumber(repairStockId, locationId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
public void updateRepairStatusByrepairToStockDetailsId(short statusId, Long repairToStockDetailsId)throws Exception {
		
		try {
			partWarrantyDetailsDao.updateRepairStatusByrepairToStockDetailsId(statusId, repairToStockDetailsId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

public ValueObject getWarrantyPartDataDetails(ValueObject valueObject)throws Exception {
	ExecutorService ex = Executors.newFixedThreadPool(2);
	try {
		CustomUserDetails userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ex.execute(()->{
			try {
				short									serviceTypeId				= 0;
				Integer									vid							= 0;
				List<WorkOrdersTasksToPartsDto>			tasksToPartsDtos			= null;
				String dataArrayObj													= valueObject.getString("partId");
				String 									query						= "";
				StringBuilder    						dseQuery					= new StringBuilder();
				serviceTypeId			= valueObject.getShort("serviceTypeId", (short) 0);
				vid						= valueObject.getInt("vid", 0);
				if(!dataArrayObj.trim().equals("")) {
					query 			=" WTP.partid IN ("+dataArrayObj+") ";
					dseQuery.append("WTP.partId IN ("+dataArrayObj+") "); 
				}
				if(vid != null && vid > 0) {
					query	+= " AND V.vid = "+vid+" ";
					dseQuery.append("AND V.vid = "+vid+" ");
				}
				if(serviceTypeId == 2) {
					tasksToPartsDtos	=	partReportDao.getWorkOrderWarrantyPartDetails(query, userDetails.getCompany_id());
				}
				else if(serviceTypeId == 3) {
					tasksToPartsDtos =partReportDao.getDSEPartDetails(dseQuery.toString(), userDetails.getCompany_id());
				}
				valueObject.put("tasksToPartsDtos", tasksToPartsDtos);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		ex.execute(()->{
			ValueObject								tableConfig				= new ValueObject();
			try {
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.WARRANTY_PART_REPORT);
				tableConfig			= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig			= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				valueObject.put("tableConfig", tableConfig.getHtData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});		
		ex.shutdown();
		ex.awaitTermination(17, TimeUnit.SECONDS);
		
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}finally {
		ex.shutdownNow();
	}
}


@Override
public ValueObject getWarrantyDetailsListDetails(ValueObject valueObject) {
	ValueObject valueOutObject = new ValueObject();
	try {
		valueOutObject.put("assignedParts", getAssignedPartList(valueObject));
	} catch (Exception e) {
		e.printStackTrace();
	}
	return valueOutObject;
}


public List<PartWarrantyDetailsDto>  getAssignedPartList(ValueObject valueObject) throws Exception{
	TypedQuery<Object[]> 				query 							= null;
	List<PartWarrantyDetailsDto>		partWarrantyDetailsList			= null;
	PartWarrantyDetailsDto				partWarrantyDetailsDto			= null;
	
	try {
		query = entityManager.createQuery(
				  " SELECT P.partWarrantyDetailsId, P.partAsignDate, P.warrantyEndDate, "
				+ " P.partSerialNumber, P.serviceId, P.replacePartWarrantyDetailsId, P.partSerialNumber, "
				+ " P.replaceInServiceId, P.isOldPartReceived, P.partId "
				+ " FROM PartWarrantyDetails AS P "
				+ " where  P.servicePartId = "+valueObject.getLong("servicePartId",0)+" AND P.companyId = "+valueObject.getLong("companyId",0)+" "
				+ " AND P.assignedFrom = "+valueObject.getShort("serviceType", PartWarrantyDetailsBL.ASSIGNED_FROM_WO)+" AND P.markForDelete = 0 ",
				Object[].class);
		
		List<Object[]> results =  null;
		try {
			results = query.getResultList();
		} catch (Exception e) {
			
		}
	

		if (results != null && !results.isEmpty()) {
			partWarrantyDetailsList = new ArrayList<PartWarrantyDetailsDto>();
			
			for (Object[] result : results) {
				partWarrantyDetailsDto = new PartWarrantyDetailsDto();

				partWarrantyDetailsDto.setPartWarrantyDetailsId((Long) result[0]);
				partWarrantyDetailsDto.setPartAsignDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[1], DateTimeUtility.DD_MM_YYYY));
				partWarrantyDetailsDto.setWarrantyEndDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[2], DateTimeUtility.DD_MM_YYYY));
				partWarrantyDetailsDto.setPartSerialNumber((String) result[3]);
				partWarrantyDetailsDto.setServiceId((Long) result[4]);
				if(result[5] != null) {
					partWarrantyDetailsDto.setReplacePartWarrantyDetailsId((Long) result[5]);
				}
				if(result[6] != null) {
					partWarrantyDetailsDto.setReplacePartSerialNumber((String) result[6]);
				}else {
					partWarrantyDetailsDto.setReplacePartSerialNumber("--");
				}
				if(result[7] != null && (Long) result[7] > 0) {
					partWarrantyDetailsDto.setReplaceInServiceId((Long) result[7]);
				}
				if(result[8] != null) {
					partWarrantyDetailsDto.setOldPartReceived((boolean) result[8]);
					if(partWarrantyDetailsDto.isOldPartReceived()) {
						partWarrantyDetailsDto.setIsOldPartReceivedStr("Yes");
					}else {
						partWarrantyDetailsDto.setIsOldPartReceivedStr("No");
					}
				}
				partWarrantyDetailsDto.setPartId((Long) result[9]);
				
				partWarrantyDetailsList.add(partWarrantyDetailsDto);
			}
		}
		
		return partWarrantyDetailsList;
	
		
	} catch (Exception e) {
		throw e;
	}
}


}
