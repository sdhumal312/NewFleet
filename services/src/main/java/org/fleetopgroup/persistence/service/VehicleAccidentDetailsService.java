package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.AccidentStatus;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.VehicleAccidentDetailsBL;
import org.fleetopgroup.persistence.dao.AccidentQuotationDetailsRepository;
import org.fleetopgroup.persistence.dao.ServeyorDetailsRepository;
import org.fleetopgroup.persistence.dao.SpotSurveyorDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleAccidentAdvanceRepository;
import org.fleetopgroup.persistence.dao.VehicleAccidentDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleAccidentPersonDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleAccidentTypeDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleExpensesRepository;
import org.fleetopgroup.persistence.dao.mongo.VehicleAccidentRepository;
import org.fleetopgroup.persistence.document.VehicleAccidentDocument;
import org.fleetopgroup.persistence.document.VehicleExpenseDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServeyorDetailsDto;
import org.fleetopgroup.persistence.dto.SpotSurveyorDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleAccidentDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleAccidentPersonDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleAccidentTypeDetailsDto;
import org.fleetopgroup.persistence.model.AccidentQuotationDetails;
import org.fleetopgroup.persistence.model.ServeyorDetails;
import org.fleetopgroup.persistence.model.SpotSurveyorDetails;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAccidentDetails;
import org.fleetopgroup.persistence.model.VehicleAccidentPersonDetails;
import org.fleetopgroup.persistence.model.VehicleAccidentSequenceCounter;
import org.fleetopgroup.persistence.model.VehicleAccidentTypeDetails;
import org.fleetopgroup.persistence.model.VehicleExpenses;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VehicleAccidentDetailsService implements IVehicleAccidentDetailsService {

	@PersistenceContext EntityManager entityManager;
	
	@Autowired	private VehicleAccidentDetailsRepository		vehicleAccidentDetailsRepository;
	@Autowired	private	IVehicleService							vehicleService;
	@Autowired	private IVehicleAccidentSequenceCounterService	sequenceCounterService;
	@Autowired private  MongoTemplate							mongoTemplate;
	@Autowired private  ISequenceCounterService					docSequenceCounterService;
	@Autowired private	ServeyorDetailsRepository				serveyorDetailsRepository;
	@Autowired private	VehicleAccidentAdvanceRepository		accidentAdvanceRepository;
	@Autowired private	VehicleExpensesRepository				vehicleExpensesRepository;
	@Autowired private	VehicleAccidentRepository				vehicleAccidentRepository;
	@Autowired private	HttpServletRequest						request;
	@Autowired private	VehicleAccidentTypeDetailsRepository	accidentTypeRepository;
	@Autowired private	VehicleAccidentPersonDetailsRepository	accidentPersonRepository;
	@Autowired private	SpotSurveyorDetailsRepository	spotSurveyorDetailsRepository;
	@Autowired 	private	AccidentQuotationDetailsRepository        	accedentServiceDetailsRepo;
	
	VehicleAccidentDetailsBL vehicleAccidentDetailsBL = new VehicleAccidentDetailsBL();
	
	private static final int PAGE_SIZE = 10;
	
	SimpleDateFormat	format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat	format2 = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat	formatTime = new SimpleDateFormat("kk:mm");
	
	@Override
	@Transactional
	public ValueObject saveVehicleAccidentDetails(ValueObject valueObject) throws Exception {
		VehicleAccidentDetails				accidentDetails			= null;
		VehicleAccidentSequenceCounter		sequenceCounter			= null;
		try {
			if(validateAccidentSave(valueObject).containsKey("error")) {
				valueObject.put("accessToken", Utility.getUniqueToken(request));
				return valueObject;
			}
			sequenceCounter	= sequenceCounterService.findNextNumber(valueObject.getInt("companyId",0));
			if(sequenceCounter != null) {
				accidentDetails	= VehicleAccidentDetailsBL.getVehicleAccidentDetailsModel(valueObject, accidentDetails);
				accidentDetails.setAccidentDetailsNumber(sequenceCounter.getNextVal());
				accidentDetails.setStatus(AccidentStatus.ACCIDENT_STATUS_INCIDENT_CREATED);
				
				vehicleAccidentDetailsRepository.save(accidentDetails);
				 if(!accidentDetails.isClaim() && valueObject.getBoolean("accidentClaimConfig",false) ){
					vehicleService.updateVehicleStatus(VehicleStatusConstant.VEHICLE_STATUS_ACTIVE, accidentDetails.getVid(), accidentDetails.getCompanyId());
					vehicleAccidentDetailsRepository.updateAccidentStatusToDonePayment(accidentDetails.getAccidentDetailsId(),accidentDetails.getLastUpdatedOn(),accidentDetails.getLastUpdateById());
				 
				 }else {
					vehicleService.updateVehicleStatus(VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT, accidentDetails.getVid(), accidentDetails.getCompanyId());
				}
				valueObject.put("encreptedId", AESEncryptDecrypt.encryptBase64(accidentDetails.getAccidentDetailsId()+""));
				valueObject.put("success", true);
			}else {
				valueObject.put("sequenceNotFound", true);
			}
			
		} catch (Exception e) {
			valueObject.put("accessToken", Utility.getUniqueToken(request));
			valueObject.put("hasException", true);
			System.err.println("Exception : "+e);
		}finally {
			accidentDetails			= null;
		}
		return valueObject;
	}
	
	private ValueObject validateAccidentSave(ValueObject valueObject) throws Exception{
		if(valueObject.getInt("vid",0) <=0) {
			valueObject.put("error", "Please select Vehicle ! ");
			return valueObject; 
		}else if(valueObject.getString("accidentDate","").trim().equals("")) {
			valueObject.put("error", "Please select accident date !");
			return valueObject; 
		}else if(valueObject.getString("accidentTime","").trim().equals("")) {
			valueObject.put("error", "Please select accident time !");
			return valueObject; 
		}else if(valueObject.getInt("driverId",0) <=0) {
			valueObject.put("error", "Please select Driver !");
			return valueObject; 
		}else if(valueObject.getString("incidentlocation","").trim().equals("")) {
			valueObject.put("error", "Please enter accident location !");
			return valueObject; 
		}else if(valueObject.getString("description","").trim().equals("")) {
			valueObject.put("error", "Please enter accident description !");
			return valueObject; 
		}
		Vehicle vehicle = vehicleService.getVehicle_Details_WorkOrder_Entries(valueObject.getInt("vid"));
		if(vehicle.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE 
				|| vehicle.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER
				|| vehicle.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD
				|| vehicle.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP
				|| vehicle.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SEIZED
				|| vehicle.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT) {
			valueObject.put("error", "Cannot create accident as vehicle is in "+VehicleStatusConstant.getVehicleStatus(vehicle.getvStatusId())+" Status !");
			return valueObject; 
		
		}
		List<VehicleAccidentDetails> validate = validateVehicleAccidentDetails(valueObject.getInt("vid"));
		if(validate != null && !validate.isEmpty()){
			valueObject.put("error", "Accident Details On Vehicle is already created !");
			return valueObject; 
		}
		return valueObject;  
	}
	
	@Override
	public ValueObject updateVehicleAccidentDetails(ValueObject valueObject) throws Exception {
		
		VehicleAccidentDetails	accidentDetails	= vehicleAccidentDetailsRepository.findById(
				Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId")))).get();
		
		accidentDetails	= VehicleAccidentDetailsBL.getVehicleAccidentDetailsModel(valueObject, accidentDetails);
		accidentDetails.setAccidentDetailsId(Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId"))));
		vehicleAccidentDetailsRepository.save(accidentDetails);
		
		valueObject.put("encreptedId", AESEncryptDecrypt.encryptBase64(accidentDetails.getAccidentDetailsId()+""));
		valueObject.put("success", true);
		
		return valueObject;
	}
	
	@Override
	public ValueObject getPageWiseVehicleAccidentDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {
			
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber				= valueObject.getInt("pageNumber",0);
			Page<VehicleAccidentDetails> page  = getVehicleAccidentDetailsPageDetails(pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());
			
			List<VehicleAccidentDetailsDto> ureaEntriesList = getVehicleAccidentDetailsDtoList(pageNumber, userDetails.getCompany_id());
			
			valueObject.put("accidentList", ureaEntriesList);
			valueObject.put("SelectPage", pageNumber);
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
		}
	}

	@Override
	public Page<VehicleAccidentDetails> getVehicleAccidentDetailsPageDetails(Integer pageNumber, Integer companyId) {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "accidentDetailsId");
		return vehicleAccidentDetailsRepository.getVehicleAccidentDetailsDtoList(companyId, pageable);
	}
	
	@Override
	public List<VehicleAccidentDetailsDto> getVehicleAccidentDetailsDtoList(Integer pageNumber, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 						typedQuery 				= null;
		List<Object[]> 								resultList 				= null; 
		List<VehicleAccidentDetailsDto> 			ureaEntriesList 		= null;
		VehicleAccidentDetailsDto 					ureaEntriesDto			= null;

		try {
			typedQuery = entityManager.createQuery("SELECT UE.accidentDetailsId, UE.accidentDetailsNumber, UE.vid, UE.tripSheetId, UE.driverId,"
					+ " UE.location, UE.accidentDateTime, V.vehicle_registration, T.tripSheetNumber, D.driver_firstname, D.driver_Lastname, D.driver_fathername "
					+ " FROM VehicleAccidentDetails AS UE"
					+ " INNER JOIN Vehicle V ON V.vid = UE.vid "
					+ " INNER JOIN Driver D ON D.driver_id = UE.driverId"
					+ " LEFT JOIN TripSheet T ON T.tripSheetID = UE.tripSheetId"
					+ " WHERE UE.companyId ="+companyId+" AND UE.markForDelete = 0 ORDER BY UE.accidentDetailsId DESC", Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				ureaEntriesList = new ArrayList<>();

				for (Object[] result : resultList) {
					ureaEntriesDto = new VehicleAccidentDetailsDto();
					
					ureaEntriesDto.setAccidentDetailsId((Long) result[0]);
					ureaEntriesDto.setAccidentDetailsNumber((Long) result[1]);
					ureaEntriesDto.setVid((Integer) result[2]);
					ureaEntriesDto.setTripSheetId((Long) result[3]);
					ureaEntriesDto.setDriverId((Integer) result[4]);
					ureaEntriesDto.setLocation((String) result[5]);
					ureaEntriesDto.setAccidentDateTime((Date) result[6]);
					ureaEntriesDto.setVehicleNumber((String) result[7]);
					ureaEntriesDto.setTripSheetNumber((Long) result[8]);
					ureaEntriesDto.setDriverName((String) result[9]);
					
					if(result[10] != null) {
						ureaEntriesDto.setDriverName(ureaEntriesDto.getDriverName() +" "+ (String) result[10]);
					}
					if(result[11] != null) {
						ureaEntriesDto.setDriverName(ureaEntriesDto.getDriverName() +" - "+ (String) result[11]);
					}
					
					ureaEntriesDto.setAccidentDateTimeStr(format.format(ureaEntriesDto.getAccidentDateTime()));
					
					ureaEntriesDto.setEncriptedId(AESEncryptDecrypt.encryptBase64(ureaEntriesDto.getAccidentDetailsId()+"")); 
					
					ureaEntriesList.add(ureaEntriesDto);
					
				}
			}

			return ureaEntriesList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			ureaEntriesList 		= null;
			ureaEntriesDto			= null;
		}
	}
	
	@Override
	public ValueObject showVehicleAccidentDetails(ValueObject valueObject) throws Exception {
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long accidentId = Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId")));
			valueObject.put("accidentDetails", getVehicleAccidentDetailsById(accidentId, userDetails.getCompany_id(), userDetails.getId()));
			
			ServeyorDetails serveyorDetails =serveyorDetailsRepository.findByAccidentId(accidentId);
			valueObject.put("serveyorDetails", serveyorDetails);
			valueObject.put("incidentDocCount", getAccidentDocumentCount(accidentId));
			valueObject.put("advanceCount", getVehicleAccidentAdvanceCount(accidentId));
			valueObject.put("expenseCount", getVehicleAccidentExpenseCount(accidentId));
			
			if(serveyorDetails != null && serveyorDetails.getServeyInformDate() != null) {
				String date = DateTimeUtility.getStringDateFromDate(serveyorDetails.getServeyInformDate(), DateTimeUtility.DD_MM_YYYY_HH_MM_SS_AA);
				valueObject.put("serveyInformDate", date.split(" ")[0]);
				valueObject.put("serveyInformTime", date.split(" ")[1]);
			}
			
			if(valueObject.getBoolean("multipleQuotation",false)) 
				valueObject.put("serviceDetails",accedentServiceDetailsRepo.getQuotationDetailsList(accidentId, userDetails.getCompany_id()));
		
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleAccidentDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long accidentId = Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId")));
		valueObject.put("accidentDetails", getVehicleAccidentDetailsById(accidentId, userDetails.getCompany_id(), userDetails.getId()));
		
		return valueObject;
	}
	
	@Override
	public VehicleAccidentDetailsDto getVehicleAccidentDetailsById(Long id, Integer companyId, Long userId) throws Exception{
		Query	query = entityManager.createQuery("SELECT UE.accidentDetailsId, UE.accidentDetailsNumber, UE.vid, UE.tripSheetId, UE.driverId,"
					+ " UE.location, UE.accidentDateTime, V.vehicle_registration, T.tripSheetNumber, D.driver_firstname, D.driver_Lastname, UE.createdById,"
					+ " UE.createdOn, UE.lastUpdateById, UE.lastUpdatedOn, UE.description, UE.accidentWithVehicle, UE.vehicleType, UE.accidentWithOwner, "
					+ " UE.accidentWithOwnerMobile, UE.accidentWithDriver, UE.accidentWithDriverrMobile, UE.accidentWithOtherDetails, UE.firNumber,"
					+ " UE.firPoliceStation, UE.firBy, UE.firRemark, U.firstName, U.lastName, U2.firstName, U2.lastName, V.vehicle_Odometer, "
					+ " UE.serviceId, SE.serviceEntries_Number, WO.workorders_Number, UE.serviceType, UE.status, UE.paymentDate, UE.paymentRemark,"
					+ " UE.paymentAmount , D.driver_fathername, UE.approxDamageAmount, UE.damageAmountStatusId, UE.damageAmount, UE.isClaim, UE.claimRemark, "
					+ " UE.routeId, TR.routeName, WO.workorders_statusId,UE.queryRemark,SE.serviceEntries_statusId "
					+ " FROM VehicleAccidentDetails AS UE"
					+ " INNER JOIN Vehicle V ON V.vid = UE.vid "
					+ " INNER JOIN Driver D ON D.driver_id = UE.driverId "
					+ " INNER JOIN User U ON U.id = UE.createdById"
					+ " INNER JOIN User U2 ON U2.id = UE.lastUpdateById"
					+ " LEFT JOIN TripSheet T ON T.tripSheetID = UE.tripSheetId"
					+ " LEFT JOIN TripRoute TR ON TR.routeID = UE.routeId"
					+ " LEFT JOIN ServiceEntries SE ON SE.serviceEntries_id = UE.serviceId AND UE.serviceType = "+AccidentStatus.SERVICE_TYPE_SE+""
					+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = UE.serviceId AND UE.serviceType = "+AccidentStatus.SERVICE_TYPE_WO+""
					+ " WHERE UE.accidentDetailsId = "+id+" AND  UE.companyId ="+companyId+" AND UE.markForDelete = 0", Object[].class);
		
		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		
		VehicleAccidentDetailsDto select;
		if (result != null) {
			select = new VehicleAccidentDetailsDto();
			select.setAccidentDetailsId((Long) result[0]);
			select.setAccidentDetailsNumber((Long) result[1]);
			select.setVid((Integer) result[2]);
			select.setTripSheetId((Long) result[3]);
			select.setDriverId((Integer) result[4]);
			select.setLocation((String) result[5]);
			select.setAccidentDateTime((Date) result[6]);
			select.setVehicleNumber((String) result[7]);
			select.setTripSheetNumber((Long) result[8]);
			select.setDriverName((String) result[9]);
			if(result[10] != null) {
				select.setDriverName(select.getDriverName() +" "+ (String) result[10]);
			}
			select.setCreatedById((Long) result[11]);
			select.setCreatedOn((Date) result[12]);
			select.setLastUpdateById((Long) result[13]);
			select.setLastUpdatedOn((Date) result[14]);
			select.setDescription((String) result[15]);
			select.setAccidentWithVehicle((String) result[16]);
			select.setVehicleType((String) result[17]);
			select.setAccidentWithOwner((String) result[18]);
			select.setAccidentWithOwnerMobile((String) result[19]);
			select.setAccidentWithDriver((String) result[20]);
			select.setAccidentWithDriverrMobile((String) result[21]);
			select.setAccidentWithOtherDetails((String) result[22]);
			select.setFirNumber((String) result[23]);
			select.setFirPoliceStation((String) result[24]);
			select.setFirBy((String) result[25]);
			select.setFirRemark((String) result[26]);
			select.setCreatedBy((String) result[27]+"_"+(String) result[28]);
			select.setLastUpdatedBy((String) result[29]+"_"+(String) result[30]);
			select.setVehicleOdometer((Integer) result[31]);
			select.setServiceId((Long) result[32]);
			if(result[33] != null) {
				select.setServiceNumber("SE-"+(Long) result[33]);
			}
			if(result[34] != null) {
				select.setServiceNumber("WO-"+(Long) result[34]);
			}
			select.setServiceType((short) result[35]);
			select.setStatus((short) result[36]);
			select.setPaymentDate((Date) result[37]);
			select.setPaymentRemark((String) result[38]);
			select.setPaymentAmount((Double) result[39]);
			
			select.setCreatedOnStr(format.format(select.getCreatedOn()));
			select.setLastUpdatedOnStr(format.format(select.getLastUpdatedOn()));
			select.setAccidentDateTimeStr(format.format(select.getAccidentDateTime()));
			select.setStatuStr(AccidentStatus.getStatusName(select.getStatus()));
			if(select.getPaymentDate() != null) {
				select.setPaymentDateStr(format2.format(select.getPaymentDate()));
			}
			
			if(select.getDescription() != null) {
				select.setDescription(select.getDescription().trim());
			}
			select.setAccidentDate(format2.format(select.getAccidentDateTime()));
			select.setAccidentTime(formatTime.format(select.getAccidentDateTime()));
			
			if(result[40] != null) {
				select.setDriverName(select.getDriverName() +" - "+ (String) result[40]);
			}
			
			if(result[41] != null) {
				select.setApproxDamageAmount((Double) result[41]);
			}
			if(result[42] != null) {
				select.setDamageAmountStatusId((short) result[42]);
				select.setDamageAmountStatus(AccidentStatus.getDamageStatus((short) result[42]));
			}
			if(result[43] != null) {
				select.setDamageAmount((Double) result[43]);
			}
			if(result[44] != null) {
				select.setClaim((boolean) result[44]);
				if((boolean) result[44]) {
					select.setClaimStatus("Yes");
				}else {
					select.setClaimStatus("No");
				}
				select.setClaimRemark((String) result[45]);
			}
			if(result[46] != null) {
				select.setRouteId((Integer) result[46]);
				select.setRoute((String) result[47]);
			}
			if(result[48] != null) {
				select.setServiceStatusId((short) result[48]);
			}
			if(result[49] != null)
				select.setQueryRemark((String) result[49]);
			
			if(result[50] != null && (short)result[50] > 0) {
				if((short) result[50] == 3)
				select.setServiceStatusId((short)4); //value set to 4 cause in js Wo status complete 4 is checked
			}
		
		} else {
			return null;
		}

		return select;

	}
	
	@Override
	public ValueObject saveIncidentDocument(ValueObject valueObject, MultipartFile file) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		valueObject.put("userId", userDetails.getId());
		valueObject.put("companyId", userDetails.getCompany_id());
		if(file != null && !file.isEmpty()) {
			saveAccidentDocument(valueObject, file);
		}
		return valueObject;
	}
	
	public void saveAccidentDocument(ValueObject valueObject, MultipartFile file) throws Exception {
		try {
			VehicleAccidentDocument	document	= new VehicleAccidentDocument();
			document.setAccidentId(Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId"))));
			document.setDocumentType(valueObject.getString("documentType"));
			document.setDocumentTypeId(valueObject.getInt("documentTypeId",0));
			document.setDocumentStatusId(valueObject.getShort("documentStatusId",(short)0));
			byte[] bytes = file.getBytes();
			document.setFileName(file.getOriginalFilename());
			document.setContent(bytes);
			document.setContentType(file.getContentType());
			document.setMarkForDelete(false);
			document.setCreatedById(valueObject.getLong("userId",0));

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			document.setCreated(toDate);
			document.setCompanyId(valueObject.getInt("companyId",0));
			document.set_id(docSequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_VEHICLE_ACCIDENT_DOCUMENT));
			mongoTemplate.save(document);

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleAccidentDocument> getVehicleAccidentDocumentById(long accidentId, int companyId)
			throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.fields().include("_id").include("fileName").include("documentType").include("contentType").include("created").include("documentTypeId").include("documentStatusId");
		query.addCriteria(Criteria.where("accidentId").is(accidentId).and("companyId").is(companyId).and("markForDelete").is(false));
		
		return mongoTemplate.find(query, VehicleAccidentDocument.class);
	}
	
	@Override
	public ValueObject getIncidentDocumentList(ValueObject valueObject) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long accidentId = Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId")));
		valueObject.put("documentList", getVehicleAccidentDocumentById(accidentId, userDetails.getCompany_id()));
		return valueObject;
	}
	
	@Override
	@Transactional
	public ValueObject saveServeyorDetails(ValueObject valueObject) throws Exception {
		ServeyorDetails		details	=	null;
		try {
			VehicleAccidentDetails	accidentDetails	= getVehicleAccidentDetailsById(
					Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId"))));
			if(accidentDetails != null) {
				if(valueObject.getLong("serveyorDetailsId",0) > 0) {
					details = serveyorDetailsRepository.findById(valueObject.getLong("serveyorDetailsId",0)).get();
				}
				details	=	VehicleAccidentDetailsBL.getServeyorDetailsDTO(valueObject, details);
				serveyorDetailsRepository.save(details);
				
				if(accidentDetails.getStatus() < AccidentStatus.ACCIDENT_STATUS_SERVEYER_DETAILS_UPDATED) {
					updateAccidentDetailsStatus(details.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVEYER_DETAILS_UPDATED);
				}
				
				valueObject.put("saveSuccess", true);
			}
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveServeyCompletionDetails(ValueObject valueObject) throws Exception {
		ServeyorDetails		details	=	null;
		try {
			VehicleAccidentDetails	accidentDetails	= getVehicleAccidentDetailsById(
					Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId"))));
			details	=	serveyorDetailsRepository.findById(valueObject.getLong("serveyorDetailsId",0)).get();
			if(details != null && accidentDetails != null) {
				details.setCompletionDate(format2.parse(valueObject.getString("completionDate")));
				details.setCompletionRemark(valueObject.getString("completionRemark"));
				details.setLastUpdatedById(valueObject.getLong("userId",0));
				details.setLastUpdated(new Date());
				
				serveyorDetailsRepository.save(details);
				valueObject.put("saveSuccess", true);
				
				if(accidentDetails.getStatus() < AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED) {
					updateAccidentDetailsStatus(details.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
				}
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveFinalServeyForDamage(ValueObject valueObject) throws Exception {
		ServeyorDetails		details	=	null;
		try {
			VehicleAccidentDetails	accidentDetails	= getVehicleAccidentDetailsById(
					Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId"))));
			details	=	serveyorDetailsRepository.findById(valueObject.getLong("serveyorDetailsId",0)).get();
			if(details != null) {
				details.setFinalDamageServeyDate(format2.parse(valueObject.getString("finalSDate")));
				details.setFinalDamageServeyRemark(valueObject.getString("finalSRemark"));
				details.setLastUpdatedById(valueObject.getLong("userId",0));
				details.setLastUpdated(new Date());
				details.setFinalServeyorName(valueObject.getString("finalServeyorName"));
				details.setFinalServeyorMobile(valueObject.getString("finalServeyorMobile"));
				details.setFinalServeyorEmail(valueObject.getString("finalServeyorEmail"));
				details.setFinalServeyorDept(valueObject.getString("finalServeyorDept"));
				details.setFinalServeyorClaimNum(valueObject.getString("finalServeyorClaimNum"));
				//details.setSalvageAmount(valueObject.getDouble("salvageAmount",0));
				
				serveyorDetailsRepository.save(details);
				valueObject.put("saveSuccess", true);
				if(accidentDetails.getStatus() < AccidentStatus.ACCIDENT_STATUS_FINAL_SERVEY_FOR_DAMAGE) {
					updateAccidentDetailsStatus(details.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_FINAL_SERVEY_FOR_DAMAGE);
				}
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateAccidentDetailsStatus(Long accidentId, short status) throws Exception {
		entityManager.createQuery("UPDATE VehicleAccidentDetails  SET status = "+status+" "
				+ " where accidentDetailsId=" + accidentId+" ").executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateAccidentDetailsServiceDetails(Long serviceId, short type, Long accidentId) throws Exception {
		entityManager.createQuery("UPDATE VehicleAccidentDetails  SET serviceId = "+serviceId+", serviceType = "+type+" "
				+ " where accidentDetailsId=" + accidentId+" ").executeUpdate();
		
	}
	@Override
	@Transactional
	public ValueObject saveQuotationApprovalDetails(ValueObject valueObject) throws Exception {
		ServeyorDetails		details	=	null;
		try {
			details	=	serveyorDetailsRepository.findById(valueObject.getLong("serveyorDetailsId",0)).get();
			if(details != null) {
				details.setQuotationApprovalDate(format2.parse(valueObject.getString("approvalDate")));
				details.setQuotationApprovalRemark(valueObject.getString("approvalRemark"));
				details.setLastUpdatedById(valueObject.getLong("userId",0));
				details.setLastUpdated(new Date());
				
				serveyorDetailsRepository.save(details);
				
				updateAccidentDetailsStatus(details.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED);
				
				valueObject.put("saveSuccess", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public VehicleAccidentDocument getVehicleAccidentDocument(Integer documentId, Integer companyId) throws Exception {
		 org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		 query.addCriteria(Criteria.where("_id").is(documentId).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, VehicleAccidentDocument.class);
	}
	
	@Override
	@Transactional
	public ValueObject saveAdvanceDetails(ValueObject valueObject) throws Exception {
		try {
			accidentAdvanceRepository.save(VehicleAccidentDetailsBL.getVehicleAccidentAdvanceObj(valueObject));
			valueObject.put("saveSuccess", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getAdvanceListByAccidentId(ValueObject valueObject) throws Exception {
		try {
			valueObject.put("advanceList", accidentAdvanceRepository.findByAccidentId(Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId")))));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject saveExpenseDetails(ValueObject valueObject, MultipartFile file) throws Exception {
		VehicleExpenses		vehicleExpenses		= null;
		try {
			vehicleExpenses	= VehicleAccidentDetailsBL.getVehicleExpensesObj(valueObject);
			
			vehicleExpensesRepository.save(vehicleExpenses);
			if(file != null && !file.isEmpty()) {
				saveExpenseDocument(vehicleExpenses, file);
			}
			valueObject.put("saveSuccess", true);
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	
	}
	
	@Override
	public ValueObject getExpenseListByAccidentId(ValueObject valueObject) throws Exception {
		try {
			valueObject.put("expenseList", vehicleExpensesRepository.findByAccidentId(Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId")))));
			valueObject.put("documentMap", getVehicleExpenseDocumentById(Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId"))), valueObject.getInt("companyId",0)));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void saveExpenseDocument(VehicleExpenses vehicleExpenses, MultipartFile file) throws Exception {
		try {
			VehicleExpenseDocument	document	= new VehicleExpenseDocument();
			document.setAccidentId(vehicleExpenses.getAccidentId());
			document.setVehicleExpensesId(vehicleExpenses.getVehicleExpensesId());
			byte[] bytes = file.getBytes();
			document.setFilename(file.getOriginalFilename());
			document.setContent(bytes);
			document.setContentType(file.getContentType());
			document.setCompanyId(vehicleExpenses.getCompanyId());

			document.set_id(docSequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_VEHICLE_EXPENSE_DOCUMENT));
			mongoTemplate.save(document);

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Map<Long, VehicleExpenseDocument> getVehicleExpenseDocumentById(long accidentId, int companyId)
			throws Exception {
		List<VehicleExpenseDocument> 				documents = null;
		Map<Long, VehicleExpenseDocument>  map = null;
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.fields().include("_id").include("filename").include("contentType").include("vehicleExpensesId");
		query.addCriteria(Criteria.where("accidentId").is(accidentId).and("companyId").is(companyId));
		
		documents = mongoTemplate.find(query, VehicleExpenseDocument.class);
		
		map	=	documents.stream().collect(Collectors.toMap(VehicleExpenseDocument::getVehicleExpensesId, Function.identity()));
		
		return map;
	}
	
	@Override
	@Transactional
	public ValueObject saveFinalInspectionDetails(ValueObject valueObject) throws Exception {
		ServeyorDetails		details	=	null;
		try {
			details	=	serveyorDetailsRepository.findById(valueObject.getLong("serveyorDetailsId",0)).get();
			if(details != null) {
				details.setFinalInspectionDate(format2.parse(valueObject.getString("inspectionDate")));
				details.setFinalInspectionRemark(valueObject.getString("inspectionRemark","").trim());
				details.setLastUpdatedById(valueObject.getLong("userId",0));
				details.setLastUpdated(new Date());
				details.setSalvageAmount(valueObject.getDouble("salvageAmount",0));
				serveyorDetailsRepository.save(details);
				updateAccidentDetailsStatus(details.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_FINAL_SERVEY_DONE);
				VehicleAccidentDetails	accidentDetails	= getVehicleAccidentDetailsById(details.getAccidentId());
				vehicleService.Update_Vehicle_Status_TripSheetID((long) 0, accidentDetails.getVid(), 
						accidentDetails.getCompanyId(), VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
				
				valueObject.put("saveSuccess", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject savePaymentDetails(ValueObject valueObject) throws Exception {
		VehicleAccidentDetails		details	=	null;
		try {
			details	=	vehicleAccidentDetailsRepository.findById(Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId")))).get();
			if(details != null) {
				details.setPaymentDate(format2.parse(valueObject.getString("paymentDate")));
				details.setPaymentRemark(valueObject.getString("paymentRemark","".trim()));
				details.setPaymentAmount(valueObject.getDouble("paymentAmount",0));
				details.setLastUpdateById(valueObject.getLong("userId",0));
				details.setLastUpdatedOn(new Date());
				details.setQueryRemark(valueObject.getString("queryRemark",""));
				
				vehicleAccidentDetailsRepository.save(details);
				
				updateAccidentDetailsStatus(details.getAccidentDetailsId(), AccidentStatus.ACCIDENT_STATUS_PAYMENT_DONE);
				
				valueObject.put("saveSuccess", true);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject removeIncidentDoc(ValueObject valueObject) throws Exception {
		vehicleAccidentRepository.deleteById(valueObject.getLong("documentId",0));
		valueObject.put("saveSuccess", true);
		return valueObject;
	}
	
	@Override
	public long getAccidentDocumentCount(Long accidentId) throws Exception {
		 org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
			query.addCriteria(Criteria.where("accidentId").is(accidentId));
			return mongoTemplate.count(query, VehicleAccidentDocument.class);

	}
	
	@Override
	@Transactional
	public ValueObject removeAdvanceDetails(ValueObject valueObject) throws Exception {
		accidentAdvanceRepository.removeAdvance(valueObject.getLong("advanceId",0));
		valueObject.put("saveSuccess", true);
		return valueObject;
	}
	
	@Override
	@Transactional
	public ValueObject removeExpense(ValueObject valueObject) throws Exception {
		vehicleExpensesRepository.removeExpense(valueObject.getLong("vehicleExpensesId",0));
		valueObject.put("saveSuccess", true);
		return valueObject;
	}
	
	@Override
	public long getVehicleAccidentAdvanceCount(Long accidentId) throws Exception{
		
		return accidentAdvanceRepository.getAdvanceCountByAccidentId(accidentId);
	}
	
	@Override
	public long getVehicleAccidentExpenseCount(Long accidentId) throws Exception{
		
		return vehicleExpensesRepository.getExpenseCountByAccidentId(accidentId);
	}
	
	@Override
	@Transactional
	public ValueObject deleteAccidentDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long accidentId = valueObject.getLong("accidentId",0);
		VehicleAccidentDetails	accidentDetails	= vehicleAccidentDetailsRepository.findById(accidentId).get();
		if(accidentDetails != null){
			if(accidentDetails.getStatus() == AccidentStatus.ACCIDENT_STATUS_INCIDENT_CREATED) {
				vehicleAccidentDetailsRepository.deleteVehicleAccident(accidentId, new Date(), userDetails.getId());
				vehicleService.updateVehicleStatus(VehicleStatusConstant.VEHICLE_STATUS_ACTIVE, accidentDetails.getVid(), accidentDetails.getCompanyId());
				valueObject.put("saveSuccess", true);
			}else {
				valueObject.put("error", "Cannot Delete Accident Details Status is "+AccidentStatus.getStatusName(accidentDetails.getStatus()));
			}
		}
		return valueObject;
	}
	
	@Override
	public ValueObject searchAccidentByNumber(ValueObject valueObject) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		valueObject.put("accidentId", vehicleAccidentDetailsRepository.getAccidentByNumber(valueObject.getLong("accidentNumber",0), userDetails.getCompany_id()));
		valueObject.put("encreptedId", AESEncryptDecrypt.encryptBase64(valueObject.getLong("accidentId",0)+""));
		return valueObject;
	}
	
	@Override
	public VehicleAccidentDetails getVehicleAccidentDetailsById(Long accidentId) throws Exception {
		
		return vehicleAccidentDetailsRepository.findById(accidentId).get();
	}
	
	@Override
	public ValueObject checkForOldServeyDetails(ValueObject valueObject) throws Exception {
		valueObject.put("serveyDetails", findOldServeyDetails(valueObject.getString("mobileNumber"), valueObject.getInt("companyId",0)));
		return valueObject;
	}
	
	@Override
	public VehicleAccidentDetails getVehicleAccidentDetailsByTripSheetId(Long tripSheetId) throws Exception {
		return vehicleAccidentDetailsRepository.getVehicleAccidentDetailsByTripSheetId(tripSheetId);
	}
	
private  List<ServeyorDetailsDto> findOldServeyDetails(String term, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = entityManager
				.createQuery(
						"SELECT S.serveyorDetailsId, S.serveyorName, S.serveyorMobile, S.serveyorCompany, "
						+ " A.accidentDetailsId, A.accidentDetailsNumber "
						+ " FROM ServeyorDetails S "
						+ " INNER JOIN VehicleAccidentDetails A ON A.accidentDetailsId = S.accidentId"
						+ " WHERE S.serveyorMobile = '" + term+"' "
						 + " and S.markForDelete = 0 AND S.companyId = " + companyId + "",
						 Object[].class);
		
		List<Object[]> results = query.getResultList();
		
		List<ServeyorDetailsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServeyorDetailsDto>();
			ServeyorDetailsDto list = null;
			for (Object[] result : results) {
				list = new ServeyorDetailsDto();
				
				list.setServeyorDetailsId((Long) result[0]);
				list.setServeyorName((String) result[1]);
				list.setServeyorMobile((String) result[2]);
				list.setServeyorCompany((String) result[3]);
				list.setAccidentId((Long) result[4]);
				list.setAccidentNumber("AD-"+(Long) result[4]);
				list.setEncriptedId(AESEncryptDecrypt.encryptBase64(list.getAccidentId()+""));
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

@Override
public List<VehicleAccidentDetails> validateVehicleAccidentDetails(Integer vid) throws Exception {
	return vehicleAccidentDetailsRepository.validateVehicleAccidentDetails(vid);
}


@Override
public ValueObject saveUpdateAccidentTypeDetails(ValueObject valueObject) throws Exception {
	try {
		
		 List<VehicleAccidentPersonDetailsDto>	savedAccidentPersonDetailsList  	= getAccidentPersonDetails(valueObject);
		 if(valueObject.getShort("vehicleAccidentTypeId") != AccidentStatus.ACCIDENT_TYPE_TPI &&  valueObject.getShort("vehicleAccidentTypeId") != AccidentStatus.ACCIDENT_TYPE_OD_TPI &&  valueObject.getShort("vehicleAccidentTypeId") != AccidentStatus.ACCIDENT_TYPE_OD_TPI_TPPD ) {
				if(savedAccidentPersonDetailsList != null && !savedAccidentPersonDetailsList.isEmpty()) {
					valueObject.put("personExist", true);
					return valueObject;
				}
			}
		 saveAccidentTypeDetails(valueObject);
		 saveAccidentPersonDetails(valueObject);
		
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}


@Override
public void saveAccidentTypeDetails(ValueObject valueObject)throws Exception{
	VehicleAccidentTypeDetails vehicleAccidentTypeDetails = null;
	try {
		
		vehicleAccidentTypeDetails = 	vehicleAccidentDetailsBL.prepareVehicleAccidentTypeDetails(valueObject);
			accidentTypeRepository.save(vehicleAccidentTypeDetails);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
@Override
@SuppressWarnings("unchecked")
public void saveAccidentPersonDetails(ValueObject valueObject)throws Exception{
	ArrayList<ValueObject>					accidentPersonObj			= null;
	List<VehicleAccidentPersonDetails>		accidentPersonDetailsList 	= null;
	VehicleAccidentPersonDetails			accidentPersonDetails		= null;
	try {
		
		accidentPersonObj			= new ArrayList<>();
		accidentPersonDetailsList 	= new ArrayList<>();
		accidentPersonObj	= (ArrayList<ValueObject>) valueObject.get("accidentPersonDetails");
		for (ValueObject object : accidentPersonObj) {
			accidentPersonDetails = 	vehicleAccidentDetailsBL.prepareVehicleAccidentPersonDetails(object,valueObject);
			accidentPersonDetailsList.add(accidentPersonDetails);
		}
		accidentPersonRepository.saveAll(accidentPersonDetailsList);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}
@Transactional
@Override
public ValueObject removeAccidentPersonDetails(ValueObject valueObject)throws Exception{
	try {
		accidentPersonRepository.removeAccidentPerson(valueObject.getLong("accidentPersonDetailsId"), valueObject.getInt("companyId"));
	return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}


@Override
public ValueObject getAccidentTypeDetails(ValueObject valueObject) throws Exception {
	VehicleAccidentTypeDetailsDto			accidentTypeDetails 		= null;
	List<VehicleAccidentPersonDetailsDto>	accidentPersonDetailsList 	= null;
	try {
		accidentTypeDetails 		= new VehicleAccidentTypeDetailsDto();
		accidentPersonDetailsList 	= new ArrayList<>();
		accidentTypeDetails  		= getAccidentTypeAllDetails(valueObject);
		accidentPersonDetailsList  	= getAccidentPersonDetails(valueObject);
		
		valueObject.put("accidentTypeDetails", accidentTypeDetails);
		valueObject.put("accidentPersonDetailsList", accidentPersonDetailsList);
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}


private  VehicleAccidentTypeDetailsDto getAccidentTypeAllDetails(ValueObject valueObject) throws Exception {
	VehicleAccidentTypeDetailsDto			accidentTypeDetails 		= null;
	
	try {
		
		Query query = entityManager
				.createQuery(
						"SELECT S.vehicleAccidentTypeDetailsId, S.VehicleAccidentTypeId, S.approxOwnDamgeCost, S.approxTPDamgeCost, "
						+ " S.natureOfOwnDamage, S.natureOfTPDamage "
						+ " FROM VehicleAccidentTypeDetails S "
						+ " WHERE S.accidentDetailsId = " + valueObject.getLong("accidentId")+" "
						 + " AND S.markForDelete = 0 AND S.companyId = " + valueObject.getInt("companyId") + "",
						 Object[].class);
		
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			if (result != null) {
				accidentTypeDetails = new VehicleAccidentTypeDetailsDto();
				accidentTypeDetails.setVehicleAccidentTypeDetailsId((Long) result[0]);
				accidentTypeDetails.setVehicleAccidentTypeId((short) result[1]);
				accidentTypeDetails.setVehicleAccidentTypeName(AccidentStatus.getAccidentType((short) result[1]));
				if( result[2] != null) {
					accidentTypeDetails.setApproxOwnDamgeCost((Double) result[2]);
				}
				if( result[3] != null) {
					accidentTypeDetails.setApproxTPDamgeCost((Double) result[3]);
				}
				if( result[4] != null) {
					accidentTypeDetails.setNatureOfOwnDamage((String) result[4]);
				}
				if( result[5] != null) {
					accidentTypeDetails.setNatureOfTPDamage((String) result[5]);
				}
			}
			
			return accidentTypeDetails;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	
	}

private  List<VehicleAccidentPersonDetailsDto>	 getAccidentPersonDetails(ValueObject valueObject) throws Exception {
	List<VehicleAccidentPersonDetailsDto>	accidentPersonDetailsList 	= null;
	VehicleAccidentPersonDetailsDto			accidentPersonDetails		= null;
	try {
		
		TypedQuery<Object[]> query = entityManager
				.createQuery(
						"SELECT S.vehicleAccidentPersonDetailsId, S.vehicleAccidentPersonTypeId, S.vehicleAccidentPersonStatusId, S.name, "
						+ " S.age, S.description "
						+ " FROM VehicleAccidentPersonDetails S "
						+ " WHERE S.accidentDetailsId = " + valueObject.getLong("accidentId")+" "
						 + " AND S.markForDelete = 0 AND S.companyId = " + valueObject.getInt("companyId") + "",
						 Object[].class);
		
		
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				accidentPersonDetailsList = new ArrayList<VehicleAccidentPersonDetailsDto>();
				for (Object[] result : results) {
					accidentPersonDetails = new VehicleAccidentPersonDetailsDto();
					accidentPersonDetails.setVehicleAccidentPersonDetailsId((Long) result[0]);
					accidentPersonDetails.setVehicleAccidentPersonTypeId((short) result[1]);
					accidentPersonDetails.setVehicleAccidentPersonType(AccidentStatus.getPersonType((short) result[1]));
					accidentPersonDetails.setVehicleAccidentPersonStatusId((short) result[2]);
					accidentPersonDetails.setVehicleAccidentPersonStatus(AccidentStatus.getPersonStatus((short) result[2]));
					accidentPersonDetails.setName((String) result[3]);
					accidentPersonDetails.setAge((Integer) result[4]);
					accidentPersonDetails.setDescription((String) result[5]);
					accidentPersonDetailsList.add(accidentPersonDetails);
				}
			}
			return accidentPersonDetailsList;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
	}

@Override
public ValueObject saveUpdateSpotSurveyorDetails(ValueObject valueObject)throws Exception{
	SpotSurveyorDetails spotSurveyorDetails = null;
	try {
		spotSurveyorDetails = 	vehicleAccidentDetailsBL.prepareSpotSurveyorDetails(valueObject);
		spotSurveyorDetailsRepository.save(spotSurveyorDetails);
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

@Override
public ValueObject getSpotSurveyorDetails(ValueObject valueObject)throws Exception{
	SpotSurveyorDetailsDto spotSurveyorDetails = null;
	try {
		
		Query query = entityManager
				.createQuery(
						"SELECT S.spotSurveyorDetailsId, S.spotSurveyorName, S.spotSurveyorMobile, S.spotSurveyorCompany, "
						+ " S.spotSurveyorDate ,S.spotSurveyorRemark,S.spotSurveyorCompletionDate, S.spotSurveyorCompletionRemark "
						+ " FROM SpotSurveyorDetails S "
						+ " WHERE S.accidentId = " + valueObject.getLong("accidentId")+" "
						 + " AND S.markForDelete = 0 AND S.companyId = " + valueObject.getInt("companyId") + "",
						 Object[].class);
		
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			
			if (result != null) {
				spotSurveyorDetails = new SpotSurveyorDetailsDto();
				spotSurveyorDetails.setSpotSurveyorDetailsId((Long) result[0]);
				spotSurveyorDetails.setSpotSurveyorName((String) result[1]);
				spotSurveyorDetails.setSpotSurveyorMobile((String) result[2]);
				spotSurveyorDetails.setSpotSurveyorCompany((String) result[3]);
				
				String date = DateTimeUtility.getStringDateFromDate((Timestamp) result[4], DateTimeUtility.DD_MM_YYYY_HH_MM_SS_AA);
				spotSurveyorDetails.setSpotSurveyorDateStr(date.split(" ")[0]);
				spotSurveyorDetails.setSpotSurveyorTimeStr(date.split(" ")[1]);
				spotSurveyorDetails.setSpotSurveyorRemark((String) result[5]);
				if(result[6] != null) {
					String date1 = DateTimeUtility.getStringDateFromDate((Timestamp) result[6], DateTimeUtility.DD_MM_YYYY_HH_MM_SS_AA);
					spotSurveyorDetails.setSpotSurveyorCompletionDateStr(date1.split(" ")[0]);
					spotSurveyorDetails.setSpotSurveyorCompletionTimeStr(date1.split(" ")[1]);
					spotSurveyorDetails.setSpotSurveyorCompletionRemark((String) result[7]);
				}
			}
			
			valueObject.put("spotSurveyorDetails", spotSurveyorDetails);
			return valueObject;
		} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}


@Override
@Transactional
public ValueObject saveKeepOpenDetails(ValueObject valueObject) throws Exception {
	ServeyorDetails		details	=	null;
	try {
		details	=	serveyorDetailsRepository.findById(valueObject.getLong("serveyorDetailsId",0)).get();
		if(details != null) {
			details.setKeepOpenDate(format2.parse(valueObject.getString("keepOpenDate")));
			details.setKeepOpenRemark(valueObject.getString("keepOpenRemark"));
			details.setLastUpdatedById(valueObject.getLong("userId",0));
			details.setLastUpdated(new Date());
			
			serveyorDetailsRepository.save(details);
			
			updateAccidentDetailsStatus(details.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_KEEP_OPEN);
			
			valueObject.put("saveSuccess", true);
		}
		
		return valueObject;
	} catch (Exception e) {
		throw e;
	}
}

@Override
@Transactional
public ValueObject saveBeforeEstimate(ValueObject valueObject) throws Exception {
	ServeyorDetails		details	=	null;
	try {
		details	=	serveyorDetailsRepository.findById(valueObject.getLong("serveyorDetailsId",0)).get();
		if(details != null) {
			details.setInsuranceSubmitDate(format2.parse(valueObject.getString("insuranceSubmitDate")));
			details.setCallFinalSurveyorDate(format2.parse(valueObject.getString("callFinalSurveyorDate")));
			details.setLastUpdatedById(valueObject.getLong("userId",0));
			details.setLastUpdated(new Date());
			
			serveyorDetailsRepository.save(details);
			
			updateAccidentDetailsStatus(details.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_BEFORE_FINAL_APPROVAL);
			
			valueObject.put("saveSuccess", true);
		}
		
		return valueObject;
	} catch (Exception e) {
		throw e;
	}
}

@Override
public ValueObject checkDocumentExist(ValueObject object) {
	try {
		object.put("docExist", validateIfDocExist(object.getLong("accidentId",0), object.getShort("documentStatusId") ,object.getInt("companyId",0)));
	} catch (Exception e) {
		e.printStackTrace();
	}
	return object;
}

public boolean validateIfDocExist(long accidentId,short documentStatusId, int companyId) throws Exception {
	boolean retunBoolean = false;
	org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
	query.fields().include("_id");
	query.addCriteria(Criteria.where("accidentId").is(accidentId).and("documentStatusId").is(documentStatusId).and("companyId").is(companyId).and("markForDelete").is(false)).limit(1);
	VehicleAccidentDocument checkDoc = mongoTemplate.findOne((query) , VehicleAccidentDocument.class);
	if(checkDoc != null && checkDoc.get_id() != null) 
		retunBoolean =true;
	return retunBoolean;
}


@Override
@Transactional
public void saveAccedentServiceDetails(Long serviceId,Long serviceNum,Long accidentId,short type,int companyId) throws Exception{
	try {
		AccidentQuotationDetails serviceDetails = new AccidentQuotationDetails(); 
		serviceDetails.setServiceId(serviceId);
		serviceDetails.setServiceNum(serviceNum);
		serviceDetails.setAccidentDetailsId(accidentId);
		serviceDetails.setServiceType(type);
		serviceDetails.setCompanyId(companyId);
		accedentServiceDetailsRepo.save(serviceDetails);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

@Override
@Transactional
public void deleteQuotationDetailsByservice(short typeId,long serviceId,int companyId) throws Exception {
	accedentServiceDetailsRepo.deleteQuotationDetails(typeId, serviceId, companyId);
}

@Override
@Transactional
public List<AccidentQuotationDetails> getQuotationDetailsList(long accidentDetailsId,int companyId) throws Exception {
	return accedentServiceDetailsRepo.getQuotationDetailsList(accidentDetailsId, companyId);
}
@Override
@Transactional
public boolean checkAllServiceComplete(long accidentDetailsId,int companyId) throws Exception {
	boolean complete = false;
	List<AccidentQuotationDetails> incompleteWO = accedentServiceDetailsRepo.getincompleteWOList(accidentDetailsId, companyId);
	List<AccidentQuotationDetails> incompleteDSE =accedentServiceDetailsRepo.getincompleteDSEList(accidentDetailsId, companyId);
	if((incompleteWO == null || incompleteWO.isEmpty())&&(incompleteDSE == null || incompleteDSE.isEmpty())) {
		complete = true;
	}
	return complete;
}

@Override
public List<VehicleAccidentDetailsDto> getVehicleAccidentDetailsDtoList(String query) {

	TypedQuery<Object[]> 						typedQuery 				= null;
	List<Object[]> 								resultList 				= null; 
	List<VehicleAccidentDetailsDto> 			ureaEntriesList 		= null;
	VehicleAccidentDetailsDto 					ureaEntriesDto			= null;

	try {
		typedQuery = entityManager.createQuery("SELECT UE.accidentDetailsId, UE.accidentDetailsNumber, UE.vid, UE.tripSheetId, UE.driverId,"
				+ " UE.location, UE.accidentDateTime, V.vehicle_registration, T.tripSheetNumber, D.driver_firstname, D.driver_Lastname, D.driver_fathername "
				+ " FROM VehicleAccidentDetails AS UE"
				+ " INNER JOIN Vehicle V ON V.vid = UE.vid "
				+ " INNER JOIN Driver D ON D.driver_id = UE.driverId"
				+ " LEFT JOIN TripSheet T ON T.tripSheetID = UE.tripSheetId"
				+ " WHERE UE.markForDelete = 0 "+query+" ORDER BY UE.accidentDetailsId DESC", Object[].class);

		resultList = typedQuery.getResultList();

		if (resultList != null && !resultList.isEmpty()) {
			ureaEntriesList = new ArrayList<>();

			for (Object[] result : resultList) {
				ureaEntriesDto = new VehicleAccidentDetailsDto();
				
				ureaEntriesDto.setAccidentDetailsId((Long) result[0]);
				ureaEntriesDto.setAccidentDetailsNumber((Long) result[1]);
				ureaEntriesDto.setVid((Integer) result[2]);
				ureaEntriesDto.setTripSheetId((Long) result[3]);
				ureaEntriesDto.setDriverId((Integer) result[4]);
				ureaEntriesDto.setLocation((String) result[5]);
				ureaEntriesDto.setAccidentDateTime((Date) result[6]);
				ureaEntriesDto.setVehicleNumber((String) result[7]);
				ureaEntriesDto.setTripSheetNumber((Long) result[8]);
				ureaEntriesDto.setDriverName((String) result[9]);
				
				if(result[10] != null) {
					ureaEntriesDto.setDriverName(ureaEntriesDto.getDriverName() +" "+ (String) result[10]);
				}
				if(result[11] != null) {
					ureaEntriesDto.setDriverName(ureaEntriesDto.getDriverName() +" - "+ (String) result[11]);
				}
				
				ureaEntriesDto.setAccidentDateTimeStr(format.format(ureaEntriesDto.getAccidentDateTime()));
				
				ureaEntriesDto.setEncriptedId(AESEncryptDecrypt.encryptBase64(ureaEntriesDto.getAccidentDetailsId()+"")); 
				
				ureaEntriesList.add(ureaEntriesDto);
				
			}
		}

	} catch (Exception e) {
		e.printStackTrace();
	}
	return ureaEntriesList;
}
}
