/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TyreTypeConstant;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.bl.TyreExpenseDetailsBL;
import org.fleetopgroup.persistence.dao.InventoryTyreLifeHistoryRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreRetreadRepository;
import org.fleetopgroup.persistence.dao.TyreExpenseDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadDto;
import org.fleetopgroup.persistence.dto.TyreExpenseDetailsDto;
import org.fleetopgroup.persistence.model.InventoryTyreLifeHistory;
import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadAmount;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.TyreExpenseDetails;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ITyreExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author fleetop
 *
 */
@Service("TyreExpenseDetailsService")
@Transactional
public class TyreExpenseDetailsService implements ITyreExpenseDetailsService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext EntityManager 	entityManager;
	
	@Autowired
	private 	TyreExpenseDetailsRepository 			tyreExpenseDetailsRepository;
	
	@Autowired 
	private 	IUserProfileService						userProfileService;
	
	@Autowired
	private 	IInventoryTyreService 					iventoryTyreService;
	
	@Autowired
	private 	InventoryTyreLifeHistoryRepository 		inventoryTyreLifeHistoryRepository;
	
	TyreExpenseDetailsBL	tyreExpenseDetailsBL 		= new TyreExpenseDetailsBL();
	
	SimpleDateFormat		SQLdateFormat 				= new SimpleDateFormat("dd-MM-yyyy");
	
	@Autowired private ISequenceCounterService			sequenceCounterService;
	
	@Autowired private MongoTemplate					mongoTemplate;
	@Autowired private IPendingVendorPaymentService		pendingVendorPaymentService;
	@Autowired private InventoryTyreRetreadRepository	inventoryTyreRetreadRepository;
	
	
	@Override
	public ValueObject getAllTyreExpenseDetails() throws Exception {
		CustomUserDetails			userDetails						= null;
		List<TyreExpenseDetails>	tyreExpenseDetailsList				= null;
		ValueObject 				valueObject						= null;
		try {
			valueObject					= new ValueObject();
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreExpenseDetailsList 		= tyreExpenseDetailsRepository.findAllByCompanyId(userDetails.getCompany_id());
			
			valueObject.put("tyreExpensesDetailsList", tyreExpenseDetailsList);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveTyreExpenseDetails(ValueObject valueObject,MultipartFile[] file) throws Exception {
		CustomUserDetails			userDetails						= null;
		TyreExpenseDetails			setTyreExpenseDetails			= null;
		TyreExpenseDetails			tyreExpenseDetails				= null;		
		ArrayList<ValueObject>		dataArrayObjColl				= null;
		int 						i								= 0;
		ValueObject					valueOutObject					= null;
		try {
			valueOutObject 		= new ValueObject();
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dataArrayObjColl 				= new ArrayList<ValueObject>();
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("tyreExpenseDetailsList");
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				for (ValueObject object : dataArrayObjColl) {
					setTyreExpenseDetails 	= tyreExpenseDetailsBL.prepareTyreExpenseDetails(object, userDetails);
					tyreExpenseDetails		= tyreExpenseDetailsRepository.save(setTyreExpenseDetails);
					
					if(file.length > 0 && tyreExpenseDetails.isTyreExpenseDetailsDocument()) {
						addTyreExpenseDetailsDoucment(file[i],tyreExpenseDetails,valueObject);
						i++;
					}
				}
				valueOutObject.put("save", true);
			}
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Transactional
	public void addTyreExpenseDetailsDoucment(MultipartFile file, TyreExpenseDetails tyreExpenseDetails, ValueObject valueObject) throws Exception {
		try {
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			
			org.fleetopgroup.persistence.document.TyreExpenseDetailsDocument tyreExpenseDetailsDoc = new org.fleetopgroup.persistence.document.TyreExpenseDetailsDocument();
			tyreExpenseDetailsDoc.setTyreExpenseDetailsId(tyreExpenseDetails.getTyreExpenseDetailsId());
			if(file != null) {
				byte[] bytes = file.getBytes();
				tyreExpenseDetailsDoc.setTyreExpenseDetailsFileName(file.getOriginalFilename());
				tyreExpenseDetailsDoc.setTyreExpenseDetailsContent(bytes);
				tyreExpenseDetailsDoc.setTyreExpenseDetailsContentType(file.getContentType());
			} else {
				if (valueObject.getString("base64String",null) != null) {
					byte[] bytes = ByteConvertor.base64ToByte(valueObject.getString("base64String",null));
					
					tyreExpenseDetailsDoc.setTyreExpenseDetailsFileName(valueObject.getString("imageName",null));
					tyreExpenseDetailsDoc.setTyreExpenseDetailsContent(bytes);
					tyreExpenseDetailsDoc.setTyreExpenseDetailsContentType(valueObject.getString("imageExt",null));
				}
			}	
			tyreExpenseDetailsDoc.setMarkForDelete(false);
			tyreExpenseDetailsDoc.setCreatedById(tyreExpenseDetails.getCreatedById());
			tyreExpenseDetailsDoc.setLastModifiedById(tyreExpenseDetails.getCreatedById());
			tyreExpenseDetailsDoc.setCreated(toDate);
			tyreExpenseDetailsDoc.setLastupdated(toDate);
			tyreExpenseDetailsDoc.setCompanyId(tyreExpenseDetails.getCompanyId());

			
			saveTyreExpenseDetailsDocument(tyreExpenseDetailsDoc);
			updateTyreExpenseDetailsDocumentId(tyreExpenseDetailsDoc.get_id(), true, tyreExpenseDetailsDoc.getTyreExpenseDetailsId(), tyreExpenseDetailsDoc.getCompanyId());

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void saveTyreExpenseDetailsDocument(org.fleetopgroup.persistence.document.TyreExpenseDetailsDocument tyreExpenseDetailsDocument) {
		tyreExpenseDetailsDocument.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_TYRE_EXPENSE_DETAILS_DOCUMENT));
		mongoTemplate.save(tyreExpenseDetailsDocument);
	}
	
	@Transactional
	public void updateTyreExpenseDetailsDocumentId(Long tyreExpenseDetailsDocId, boolean tyreExpenseDetailsDocument, Long tyreExpenseDetailsId, Integer companyId) {
		tyreExpenseDetailsRepository.updateTyreExpenseDetailsDocumentId(tyreExpenseDetailsDocId, tyreExpenseDetailsDocument, tyreExpenseDetailsId, companyId);

	}
	
	/*@Transactional
	public TyreExpenses validateTyreExpense(String tyreExpenseName , Integer companyId) throws Exception {
		try {
			System.err.println("name"+tyreExpenseName);
			return tyreExpensesRepository.findByName(tyreExpenseName,companyId);
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
		
	}*/
	
	
	
	@Override
	public ValueObject getAllTyreExpenseDetailsByTyreTypeId(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails							= null;
		List<TyreExpenseDetails>			tyreExpenseDetails					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreExpenseDetails 			= tyreExpenseDetailsRepository.findByTyreTypeId(valueObject.getLong("tyreId"),valueObject.getShort("tyreTypeId"), userDetails.getCompany_id());
			valueObject.put("tyreExpenseDetails", tyreExpenseDetails);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	@Override
	public ValueObject getTyreExpenseDetailsByTyreExpenseDetailsId(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		TyreExpenseDetails			tyreExpenseDetails					= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreExpenseDetails 			= tyreExpenseDetailsRepository.findByTyreExpensDetailsId(valueObject.getInt("tyreExpenseDetailsId"), userDetails.getCompany_id());
			valueObject.put("tyreExpenseDetails", tyreExpenseDetails);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	@Override
	public ValueObject getTyreExpenseDetailsByTyreId(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		List<TyreExpenseDetailsDto>		tyreExpenseDetailsDtoList		= null;
		TyreExpenseDetailsDto			tyreExpenseDetailsDto			= null;
		TypedQuery<Object[]> 			query							= null;
		List<Object[]> 					results							= null;	
		String 							lifePeriod						= null;// get invoice amount from history table
		InventoryTyreLifeHistory 		InventoryTyreLifeHistory 		= null;
		List<TyreExpenseDetails>				tyreExpenseDetails				= null;
		try {
			
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			query = entityManager.createQuery(
					"SELECT TED.totalTyreExpenseAmount,TED.tyreTypeId, IT.TYRE_AMOUNT,TE.tyreExpenseName,"
							+ " TED.tyreExpenseDetailsId,TED.description, TED.vendorId, V.vendorName,TED.tyreExpenseDetailsDocument, TED.tyreExpenseDetailsDocumentId "
							+ " FROM TyreExpenseDetails AS TED "
							+ " INNER JOIN InventoryTyre AS IT ON IT.TYRE_ID = TED.tyreId  "
							+ " INNER JOIN TyreExpenses AS TE ON TE.tyreExpenseId = TED.tyreExpenseId AND TED.tyreTypeId = "+TyreTypeConstant.NEW_TYRE+"  "
							+ " LEFT JOIN Vendor AS V ON V.vendorId = TED.vendorId "
							+ " WHERE TED.companyId = "+userDetails.getCompany_id()+" AND TED.tyreId = "+valueObject.get("tyreId,0")+" AND TED.markForDelete = 0 ", Object[].class);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				tyreExpenseDetailsDtoList 	= new ArrayList<TyreExpenseDetailsDto>();
				
				for (Object[] result : results) {
					tyreExpenseDetailsDto 		= new TyreExpenseDetailsDto();
					
					tyreExpenseDetailsDto.setTotalTyreExpenseAmount((Double) result[0]);
					if(result[1] != null) {
						tyreExpenseDetailsDto.setTyreTypeId((short) result[1]);
					}
					tyreExpenseDetailsDto.setTyreInvoiceAmount((Double) result[2]);
					
					if(result[3] != null) {
					tyreExpenseDetailsDto.setTyreExpenseName((String) result[3]);
					}else{
						tyreExpenseDetailsDto.setTyreExpenseName(TyreTypeConstant.getTyreStatus(tyreExpenseDetailsDto.getTyreTypeId()));
					}
					
					tyreExpenseDetailsDto.setTyreExpenseDetailsId((Long) result[4]);
					tyreExpenseDetailsDto.setDescription((String) result[5]);
					if(result[6] != null) {
						tyreExpenseDetailsDto.setVendorId((Integer) result[6]);
						tyreExpenseDetailsDto.setVendorName((String) result[7]);
					}
					if(result[8] != null) {
						tyreExpenseDetailsDto.setTyreExpenseDetailsDocument((boolean) result[8]);
						tyreExpenseDetailsDto.setTyreExpenseDetailsDocumentId((Long) result[9]);
					}
					
					tyreExpenseDetailsDtoList.add(tyreExpenseDetailsDto);
				}
			}
			valueObject.put("tyreExpenseDetailsList", tyreExpenseDetailsDtoList);
			
			tyreExpenseDetails = tyreExpenseDetailsRepository.findByTyreTypeId(valueObject.getLong("tyreId"),TyreTypeConstant.RETREAD_TYRE, userDetails.getCompany_id());
			if(!tyreExpenseDetails.isEmpty() && tyreExpenseDetails != null) {
				lifePeriod = "NEW";
				InventoryTyreLifeHistory = inventoryTyreLifeHistoryRepository.getInventoryTyreLifeHistoryByLifePeriodAndTyreNumber(valueObject.getLong("tyreId"),lifePeriod,userDetails.getCompany_id());

				if(InventoryTyreLifeHistory != null)
					valueObject.put("invoiceAmount", InventoryTyreLifeHistory.getTYRE_LIFE_COST());
			}else {
				if(tyreExpenseDetailsDtoList != null)
				valueObject.put("invoiceAmount", tyreExpenseDetailsDtoList.get(0).getTyreInvoiceAmount());
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" , e);
			throw e;
		}finally {
			userDetails						= null;
			tyreExpenseDetailsDtoList		= null;
			tyreExpenseDetailsDto			= null;
			query							= null;
			results							= null;
		}
	}
	@Override
	@Transactional
	public ValueObject updateTyreExpenseDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreExpenseDetailsRepository.updateTyreExpenseDetailsByExpenseDetailsId(valueObject.getInt("editTyreExpenseDetailsId"),valueObject.getDouble("editTyreExpenseName"), valueObject.getLong("editDescription"),userDetails.getCompany_id());
			
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	@Transactional
	@Override
	public ValueObject deleteTyreExpenseDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		try {
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tyreExpenseDetailsRepository.deleteTyreExpenseByExpenseDetailsId(valueObject.getLong("tyreExpenseDetailsId"),userDetails.getCompany_id());
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	@Override
	public ValueObject getTyreExpenseDetailsReport(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		TyreExpenseDetailsDto		tyreExpenseDetailsDto			= null;
		List<TyreExpenseDetailsDto>	tyreExpenseDetailsList			= null;
		TypedQuery<Object[]> 		query							= null;
		List<Object[]> 				results							= null;	
		Integer						manufacturerId					= 0;
		Integer						vid								= 0;
		String						manufacturerStr					= "";
		String						vidStr							= "";
		ValueObject					tableConfig						= null;
		String						queryStr						= null;
		
		try {	
			manufacturerId 	= valueObject.getInt("manufacturer",0);
			vid				= valueObject.getInt("vid",0);
			
			if(manufacturerId > 0) {
				manufacturerStr = "AND IT.TYRE_MANUFACTURER_ID="+manufacturerId+"";
			}
			if(vid > 0 ) {
				vidStr	= " AND IT.VEHICLE_ID ="+vid+" ";
			}
			
			queryStr  = " "+manufacturerStr+" "+vidStr+" " ;
		
			userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			tableConfig					= new ValueObject();
			
			query = entityManager.createQuery(
					"SELECT TED.tyreExpenseDetailsId,TED.totalTyreExpenseAmount,TED.tyreTypeId,  IT.TYRE_NUMBER, IT.TYRE_AMOUNT, IT.TYRE_USEAGE,"
							+ " V.vehicle_Odometer,IT.OPEN_ODOMETER "
							+ " FROM InventoryTyre AS IT "
							+ " LEFT JOIN Vehicle AS V ON V.vid = IT.VEHICLE_ID "
							+ " LEFT JOIN TyreExpenseDetails AS TED ON TED.tyreId = IT.TYRE_ID  AND TED.markForDelete =0"
							+ " WHERE IT.COMPANY_ID = "+userDetails.getCompany_id()+" "+queryStr+" AND IT.markForDelete = 0", Object[].class);
			
			results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				tyreExpenseDetailsList = new ArrayList<TyreExpenseDetailsDto>();
				
				for (Object[] result : results) {
					tyreExpenseDetailsDto = new TyreExpenseDetailsDto();
					
					if(result[0] != null) {
						tyreExpenseDetailsDto.setTyreExpenseDetailsId((Long) result[0]);
					}
					
					if(result[1] != null) {
						
						if(result[2] != null) {
							tyreExpenseDetailsDto.setTyreTypeId((short) result[2]);
						
							if(tyreExpenseDetailsDto.getTyreTypeId() ==  TyreTypeConstant.NEW_TYRE) {
								tyreExpenseDetailsDto.setTotalTyreExpenseAmount((Double) result[1]);
								tyreExpenseDetailsDto.setRetreadTyreInvoiceAmount((Double)0.0);
							}else {
								tyreExpenseDetailsDto.setTotalTyreExpenseAmount((Double)0.0);
								tyreExpenseDetailsDto.setRetreadTyreInvoiceAmount((Double) result[1]);
							}
							
						}else {
							tyreExpenseDetailsDto.setTyreTypeId((short) 0);
							tyreExpenseDetailsDto.setTotalTyreExpenseAmount((Double)0.0);
							tyreExpenseDetailsDto.setRetreadTyreInvoiceAmount((Double)0.0);
						}
						
					}else {
						tyreExpenseDetailsDto.setTotalTyreExpenseAmount((Double)0.0);
						tyreExpenseDetailsDto.setRetreadTyreInvoiceAmount((Double)0.0);
					}
					
					tyreExpenseDetailsDto.setTyreNumber((String) result[3]);
					tyreExpenseDetailsDto.setTyreInvoiceAmount((Double) result[4]);
					tyreExpenseDetailsDto.setTyreUsage((Integer) result[5]);
					tyreExpenseDetailsDto.setTotalTyreExpenseCost((tyreExpenseDetailsDto.getTotalTyreExpenseAmount()+tyreExpenseDetailsDto.getTyreInvoiceAmount()+tyreExpenseDetailsDto.getRetreadTyreInvoiceAmount()));
					
					
					if((Integer) result[6] != null) {
						tyreExpenseDetailsDto.setVehicleOdomoter((Integer) result[6]);
						tyreExpenseDetailsDto.setOpenOdometer((Integer) result[7]);
						
						tyreExpenseDetailsDto.setTyreUsage(tyreExpenseDetailsDto.getTyreUsage() + (tyreExpenseDetailsDto.getVehicleOdomoter() - tyreExpenseDetailsDto.getOpenOdometer()));
					}
					
					tyreExpenseDetailsDto.setTyreCostPerKm((tyreExpenseDetailsDto.getTyreUsage()/tyreExpenseDetailsDto.getTotalTyreExpenseCost()));
					
					tyreExpenseDetailsList.add(tyreExpenseDetailsDto);
				}
			}
			
			
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.TYRE_EXPENSE_DETAILS_FILE_PATH);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
			
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			valueObject.put("tyreExpenseDetailsList", tyreExpenseDetailsList);
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;
			tyreExpenseDetailsDto			= null;
			tyreExpenseDetailsList			= null;
			query							= null;
			results							= null;
			manufacturerId					= 0;   
			vid								= 0;   
			manufacturerStr					= "";  
			vidStr							= "";  
			tableConfig						= null;
			queryStr						= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject saveRetreadTyreExpenseDetails(ValueObject valueObject,MultipartFile[] file ) throws Exception {
		CustomUserDetails			userDetails						= null;
		List<TyreExpenseDetails>	tyreExpensesDetailsFinalList	= null;
		ArrayList<ValueObject>		dataArrayObjColl				= null;
		Date 						currentDateUpdate				= null;
		Timestamp 					Lastupdated						= null;
		try {
			currentDateUpdate 	= new Date();
			Lastupdated 		= new java.sql.Timestamp(currentDateUpdate.getTime());
			
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			InventoryTyreRetread	inventoryTyreRetread = inventoryTyreRetreadRepository.Get_InventoryTyreRetread(valueObject.getLong("TRID",0), userDetails.getCompany_id());
			
			dataArrayObjColl 				= new ArrayList<ValueObject>();
			tyreExpensesDetailsFinalList 	= new ArrayList<TyreExpenseDetails>();
			
			dataArrayObjColl				= (ArrayList<ValueObject>) valueObject.get("tyreExpenseDetailsList");
			
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				for (ValueObject object : dataArrayObjColl) {
					tyreExpensesDetailsFinalList.add(tyreExpenseDetailsBL.prepareTyreExpenseDetails(object, userDetails));
				}
			tyreExpenseDetailsRepository.saveAll(tyreExpensesDetailsFinalList);
			
			java.util.Date 	date 			= SQLdateFormat.parse(valueObject.getString("expenseDate"));
			java.sql.Date 	INVOICE_DATE 	= new java.sql.Date(date.getTime());

			iventoryTyreService.Update_Completed_Inventory_ReTread_Status_and_Description(InventoryTyreRetreadDto.INVENTORY_TYRE_TR_STATUS_COMPLETED,
					valueObject.getString("tyreExpenseId_invoiceNum",""), INVOICE_DATE, valueObject.getString("description",""),
					userDetails.getId(), Lastupdated, valueObject.getLong("TRID",0), userDetails.getCompany_id());
			
			if(inventoryTyreRetread.getTR_PAYMENT_TYPE_ID() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && inventoryTyreRetread.getTR_AMOUNT() > 0) {
				PendingVendorPayment	payment = PendingVendorPaymentBL.createPendingVendorPaymentDTOForTRI(inventoryTyreRetread);
				payment.setTransactionDate(INVOICE_DATE);
				payment.setInvoiceNumber(valueObject.getString("tyreExpenseId_invoiceNum",""));
				pendingVendorPaymentService.savePendingVendorPayment(payment);
			}
			
			valueObject.put("save", true);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			userDetails						= null;  
			tyreExpensesDetailsFinalList	= null;  
			dataArrayObjColl				= null;  
			currentDateUpdate				= null;  
			Lastupdated						= null;  
		}
	}
	@Transactional
	@Override
	public void updateRetreadTyreExpenseDetails(InventoryTyreRetreadAmount TyreRetread ) throws Exception {
		CustomUserDetails			userDetails						= null;
		Date 						currentDateUpdate				= null;
		Timestamp 					Lastupdated						= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			currentDateUpdate 	= new Date();
			Lastupdated 		= new java.sql.Timestamp(currentDateUpdate.getTime());
			tyreExpenseDetailsRepository.updateRetreadTyreExpenseDetails(TyreRetread.getRETREAD_COST(),userDetails.getId(),Lastupdated,TyreRetread.getTYRE_ID(),userDetails.getCompany_id());
			
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails						= null;  
			currentDateUpdate				= null;  
			Lastupdated						= null;  
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject reOpenTyreRetreadAndDeleteTyreExpense(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails						= null;
		ArrayList<ValueObject>		dataArrayObjColl				= null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dataArrayObjColl 				= new ArrayList<ValueObject>();
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("tyreExpenseDetailsList");
			
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				for (ValueObject object : dataArrayObjColl) {
					/*here invoiceNumber in tyreExpenseId because at the time of Adding Retread Expense We store invoice number as tyreExpenseId*/
					tyreExpenseDetailsRepository.deleteRetreadTyreExpenseByTyreIdAndInvoiceNumber(object.getLong("tyreId"),object.getShort("tyreType"),userDetails.getCompany_id());
				}
			
			iventoryTyreService.Update_Completed_Inventory_ReTread_Status_and_Description(InventoryTyreRetreadDto.INVENTORY_TYRE_TR_STATUS_SENT_RETREAD,
					"COMPLETED Status To Reopen Status", valueObject.getLong("TRID"), userDetails.getCompany_id());
			
			
			pendingVendorPaymentService.deletePendingVendorPayment(valueObject.getLong("TRID"), PendingPaymentType.PAYMENT_TYPE_TYRE_RETREAD_INVOICE);
			
			valueObject.put("save", true);
			}
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails		= null;
		}
	}
	
	@Override
	public org.fleetopgroup.persistence.document.TyreExpenseDetailsDocument getTyreExpenseDetailsDocumentId(long tyreExpenseDetailsDocumentId, Integer company_id) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(tyreExpenseDetailsDocumentId).and("companyId").is(company_id).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.TyreExpenseDetailsDocument.class);
	}

}
