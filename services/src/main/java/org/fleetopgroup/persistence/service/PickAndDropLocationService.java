package org.fleetopgroup.persistence.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.TripsheetPickAndDropConstant;
import org.fleetopgroup.constant.VehicleEmiPaymentStatus;
import org.fleetopgroup.persistence.bl.PickAndDropLocationBL;
import org.fleetopgroup.persistence.dao.PickAndDropLocationRepository;
import org.fleetopgroup.persistence.dao.TripsheetPickAndDropInvoiceRepository;
import org.fleetopgroup.persistence.dao.TripsheetPickAndDropInvoiceSummaryRepository;
import org.fleetopgroup.persistence.dao.TripsheetPickAndDropPaymentRepository;
import org.fleetopgroup.persistence.dao.TripsheetPickAndDropRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripsheetPickAndDropDto;
import org.fleetopgroup.persistence.dto.TripsheetPickAndDropInvoiceSummaryDto;
import org.fleetopgroup.persistence.dto.TripsheetPickAndDropPaymentDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.PickAndDropLocation;
import org.fleetopgroup.persistence.model.TripsheetPickAndDrop;
import org.fleetopgroup.persistence.model.TripsheetPickAndDropInvoice;
import org.fleetopgroup.persistence.model.TripsheetPickAndDropInvoiceSequenceCounter;
import org.fleetopgroup.persistence.model.TripsheetPickAndDropInvoiceSummary;
import org.fleetopgroup.persistence.model.TripsheetPickAndDropPayment;
import org.fleetopgroup.persistence.model.TripsheetPickAndDropSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IPickAndDropLocationService;
import org.fleetopgroup.persistence.serviceImpl.ITripsheetPickAndDropInvoiceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ITripsheetPickAndDropSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



@Service("PickAndDropLocationService")
@Transactional
public class PickAndDropLocationService implements IPickAndDropLocationService {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final int PAGE_SIZE 					= 10;
	
	SimpleDateFormat 					dateFormat		 		= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 					localDateFormat 		= new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat 					dateFormat2		 		= new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
	SimpleDateFormat 					CreatedDateTime 		= new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	
	@Autowired	private PickAndDropLocationRepository 					pickAndDropLocationRepository;
	@Autowired  private TripsheetPickAndDropRepository 					TripsheetPickAndDropRepository;
	@Autowired  private ITripsheetPickAndDropSequenceService 			TripsheetPickAndDropSequenceService;
	@Autowired  private ITripsheetPickAndDropInvoiceSequenceService 	TripsheetPickAndDropInvoiceSequenceService;
	@Autowired  private TripsheetPickAndDropInvoiceRepository 			TripsheetPickAndDropInvoiceRepository;
	@Autowired  private TripsheetPickAndDropPaymentRepository 			TripsheetPickAndDropPaymentRepository;
	@Autowired  private TripsheetPickAndDropInvoiceSummaryRepository 	TripsheetPickAndDropInvoiceSummaryRepository;
	@Autowired  private	IUserProfileService								userProfileService;
	@Autowired  private	IDriverService									driverService;
	
	

	PickAndDropLocationBL pickAndDropLocationBL = new PickAndDropLocationBL();

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public ValueObject getPickAndDropLocationAutoComplete(String search) throws Exception {
		CustomUserDetails userDetails = null;
		List<PickAndDropLocation> locationList = null;
		PickAndDropLocation location = null;
		ValueObject valueObject = null;
		TypedQuery<Object[]> query = null;
		List<Object[]> results = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject = new ValueObject();
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			query = entityManager
					.createQuery("SELECT PDL.pickAndDropLocationId, PDL.locationName FROM PickAndDropLocation AS PDL "
							+ "WHERE lower(PDL.locationName) Like ('%" + search + "%') AND PDL.companyId = "
							+ userDetails.getCompany_id() + " AND PDL.markForDelete = 0", Object[].class);
			query.setFirstResult(0);
			query.setMaxResults(20);

			results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				locationList = new ArrayList<PickAndDropLocation>();

				for (Object[] result : results) {
					location = new PickAndDropLocation();
					location.setPickAndDropLocationId((Long) result[0]);
					location.setLocationName((String) result[1]);
					locationList.add(location);
				}
			}

			valueObject.put("locationList", locationList);
			}
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
			locationList = null;
			location = null;
			valueObject = null;
			query = null;
			results = null;
		}
	}

	@Override
	public ValueObject locationNameList(ValueObject object) throws Exception {
		List<PickAndDropLocation> locationNameList = null;
		List<PickAndDropLocation> loadTypes = null;
		try {

			locationNameList = new ArrayList<PickAndDropLocation>();
			loadTypes = getlocationNameByName(object.getString("term"), object.getInt("companyId"));

			if (loadTypes != null && !loadTypes.isEmpty()) {
				for (PickAndDropLocation load : loadTypes) {
					locationNameList.add(load);
				}
			}

			object.put("locationNameList", locationNameList);

			return object;

		} catch (Exception e) {
			throw e;
		} finally {
			loadTypes = null;
			locationNameList = null;
			object = null;
		}
	}
	
	
	public List<PickAndDropLocation> getlocationNameByName(String search, int companyId) throws Exception {
		List<PickAndDropLocation> locationList = null;
		PickAndDropLocation location = null;
		TypedQuery<Object[]> query = null;
		List<Object[]> results = null;
		try {
			if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
			query = entityManager
					.createQuery("SELECT PDL.pickAndDropLocationId, PDL.locationName FROM PickAndDropLocation AS PDL "
							+ "WHERE lower(PDL.locationName) Like ('%" + search + "%') AND PDL.companyId = "
							+ companyId + " AND PDL.markForDelete = 0", Object[].class);
			query.setFirstResult(0);
			query.setMaxResults(20);

			results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				locationList = new ArrayList<PickAndDropLocation>();

				for (Object[] result : results) {
					location = new PickAndDropLocation();
					location.setPickAndDropLocationId((Long) result[0]);
					location.setLocationName((String) result[1]);
					locationList.add(location);
				}
			}
			}
			return locationList;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			locationList = null;
			location = null;
			query = null;
			results = null;
		}
	}

	/*
	 * @Override public List<PickAndDropLocation> getlocationNameByName(String term,
	 * Integer companyId) throws Exception { try { TypedQuery<PickAndDropLocation>
	 * queryt = entityManager .createQuery(
	 * "SELECT PDL.pickAndDropLocationId, PDL.locationName FROM PickAndDropLocation AS PDL"
	 * + " WHERE lower(PDL.locationName) Like ('%" + term +
	 * "%') AND PDL.companyId = " + companyId + " AND  PDL.markForDelete = 0 ",
	 * PickAndDropLocation.class); return queryt.getResultList();
	 * 
	 * } catch (Exception e) { throw e; } }
	 */

	/*
	 * @Override public List<PickAndDropLocation> getlocationNameByName(String term,
	 * Integer companyId) throws Exception {
	 * 
	 * 
	 * TypedQuery<PickAndDropLocation> queryt = null; queryt =
	 * entityManager.createQuery(
	 * "SELECT PDL.pickAndDropLocationId, PDL.locationName FROM PickAndDropLocation AS PDL "
	 * + " WHERE lower(PDL.locationName) Like ('%" + term +
	 * "%') AND PDL.companyId = "+companyId+" AND PDL.markForDelete = 0 ",
	 * PickAndDropLocation.class);
	 * 
	 * TypedQuery<Object[]> query = null; query = entityManager.createQuery(
	 * "SELECT PDL.pickAndDropLocationId, PDL.locationName FROM PickAndDropLocation AS PDL "
	 * + "WHERE lower(PDL.locationName) Like ('%" + term +
	 * "%') AND PDL.companyId = "+companyId+" AND PDL.markForDelete = 0",
	 * Object[].class); query.setFirstResult(0); query.setMaxResults(20);
	 * 
	 * return query.getResultList(); }
	 */

	@Override
	public ValueObject getAllPickAndDropLocation() throws Exception {
		CustomUserDetails userDetails = null;
		List<PickAndDropLocation> locationList = null;
		ValueObject valueObject = null;
		try {
			valueObject = new ValueObject();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			locationList = pickAndDropLocationRepository.findAllByCompanyId(userDetails.getCompany_id());

			valueObject.put("locationList", locationList);

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
			locationList = null;
			valueObject = null;
		}
	}

	@Transactional
	public PickAndDropLocation validatePickAndDropLocation(String tyreExpenseName, Integer companyId) throws Exception {
		try {
			return pickAndDropLocationRepository.findByName(tyreExpenseName, companyId);
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		}

	}

	@Override
	public ValueObject savePickAndDropLocation(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		PickAndDropLocation PickAndDropLocation = null;
		PickAndDropLocation validatePickAndDropLocation = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			validatePickAndDropLocation = validatePickAndDropLocation(valueObject.getString("locationName"),
					userDetails.getCompany_id());

			if (validatePickAndDropLocation != null) {
				valueObject.put("alreadyExist", true);
			} else {
				PickAndDropLocation = pickAndDropLocationBL.preparePickAndDropLocation(valueObject, userDetails);
				pickAndDropLocationRepository.save(PickAndDropLocation);
				valueObject.put("save", true);
			}

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
			PickAndDropLocation = null;
			validatePickAndDropLocation = null;
		}
	}

	@Override
	public ValueObject getPickAndDropLocationById(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		PickAndDropLocation pickAndDropLocation = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pickAndDropLocation = pickAndDropLocationRepository.findByPickAndDropLocationId(
					valueObject.getLong("pickAndDropLocationId"), userDetails.getCompany_id());
			valueObject.put("location", pickAndDropLocation);

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
			pickAndDropLocation = null;
		}
	}

	@Override
	@Transactional
	public ValueObject updatePickAndDropLocation(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		PickAndDropLocation validatePickAndDropLocation = null;
		PickAndDropLocation pickAndDropLocation = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pickAndDropLocation = pickAndDropLocationRepository.findByPickAndDropLocationId(
					valueObject.getLong("pickAndDropLocationId"), userDetails.getCompany_id());

			if (valueObject.getString("editLocationName")
					.equalsIgnoreCase(pickAndDropLocation.getLocationName().trim())) {
				pickAndDropLocationRepository.updatePickAndDropLocationById(
						valueObject.getLong("pickAndDropLocationId"), pickAndDropLocation.getLocationName(),
						valueObject.getString("editDescription"), userDetails.getCompany_id());
			} else {
				validatePickAndDropLocation = validatePickAndDropLocation(valueObject.getString("editLocationName"),
						userDetails.getCompany_id());
				if (validatePickAndDropLocation != null) {
					valueObject.put("alreadyExist", true);
				} else {
					pickAndDropLocationRepository.updatePickAndDropLocationById(
							valueObject.getLong("pickAndDropLocationId"), valueObject.getString("editLocationName"),
							valueObject.getString("editDescription"), userDetails.getCompany_id());
				}
			}

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
			validatePickAndDropLocation = null;
			pickAndDropLocation = null;
		}
	}

	@Transactional
	@Override
	public ValueObject deletePickAndDropLocation(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pickAndDropLocationRepository.deleteTyreExpenseByExpenseId(valueObject.getLong("pickAndDropLocationId"),
					userDetails.getCompany_id());

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Override
	public ValueObject searchTripsheetNumber(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		TripsheetPickAndDrop pickAndDrop = null;
		try {
			userDetails = (CustomUserDetails) valueObject.get("userDetails");
			pickAndDrop = TripsheetPickAndDropRepository.getTripsheetPickAndDropByNumber(
					valueObject.getLong("tripsheetNumber"), userDetails.getCompany_id());

			if (pickAndDrop != null) {
				valueObject.put("tripsheetPickAndDropId", pickAndDrop.getTripsheetPickAndDropId());
				valueObject.put("tripsheetNoFound", true);
			} else {
				valueObject.put("tripsheetNoFound", false);
			}

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Override
	public ValueObject getTripSheetPickAndDropDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		Integer pageNumber = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber = valueObject.getInt("pageNumber", 0);
			Page<TripsheetPickAndDrop> page = getDeployment_Page_PickAndDropDispatchDetails(pageNumber,
					userDetails.getCompany_id());

			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());

			List<TripsheetPickAndDropDto> pageList = getTripsheetPickDropDispatchDetails(pageNumber,
					userDetails.getCompany_id());

			valueObject.put("PickOrDrop", pageList);
			valueObject.put("SelectPage", pageNumber);

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}

	public Page<TripsheetPickAndDrop> getDeployment_Page_PickAndDropDispatchDetails(Integer pageNumber,
			Integer companyId) {
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC,
				"tripsheetPickAndDropId");
		return TripsheetPickAndDropRepository.getDeployment_Page_TripsheetPickAndDrop(companyId, pageable);
	}

	public List<TripsheetPickAndDropDto> getTripsheetPickDropDispatchDetails(Integer pageNumber, Integer companyId)
			throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			List<Object[]> resultList = null;
			List<TripsheetPickAndDropDto> pickDropList = null;
			TripsheetPickAndDropDto list = null;

			typedQuery = entityManager.createQuery(
					"SELECT TPD.tripsheetPickAndDropId, TPD.tripSheetNumber, TPD.vid, TPD.journeyDate, TPD.tripFristDriverID, "
							+ " TPD.vendorId, TPD.pickOrDropStatus, TPD.pickOrDropId, TPD.tripUsageKM, TPD.tripTotalAdvance, "
							+ " TPD.remark, V.vehicle_registration, D.driver_firstname, CA.corporateName, PL.locationName, "
							+ " TPD.rate, TPD.amount, TPD.newVendorName, TPD.newPickOrDropLocationName " 
							+ " FROM TripsheetPickAndDrop AS TPD "
							+ " INNER JOIN Vehicle AS V ON V.vid = TPD.vid "
							+ " INNER JOIN Driver D ON D.driver_id = TPD.tripFristDriverID "
							+ " LEFT JOIN CorporateAccount CA ON CA.corporateAccountId = TPD.vendorId "
							+ " LEFT JOIN PickAndDropLocation PL ON PL.pickAndDropLocationId = TPD.pickOrDropId "
							+ " WHERE TPD.markForDelete = 0 AND TPD.companyId = " + companyId + " "
							+ " ORDER BY TPD.tripsheetPickAndDropId DESC ",
					Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				pickDropList = new ArrayList<>();

				for (Object[] result : resultList) {
					list = new TripsheetPickAndDropDto();

					list.setTripsheetPickAndDropId((long) result[0]);
					list.setTripSheetNumber((long) result[1]);
					list.setVid((int) result[2]);
					list.setJourneyDateStr(dateFormat.format((java.util.Date) result[3]));
					list.setJourneyDateStr2(dateFormat2.format((java.util.Date) result[3]));
					list.setTripFristDriverID((int) result[4]);
					list.setVendorId((int) result[5]);
					list.setPickOrDropStatus((short) result[6]);
					list.setPickOrDropStatusStr(
							TripsheetPickAndDropConstant.getPickAndDrop(list.getPickOrDropStatus()));
					list.setPickOrDropId((long) result[7]);
					list.setTripUsageKM((int) result[8]);
					list.setTripTotalAdvance((double) result[9]);
					list.setRemark((String) result[10]);
					list.setVehicleRegistration((String) result[11]);
					list.setDriverName((String) result[12]);
					list.setVendorName((String) result[13]);
					list.setLocationName((String) result[14]);
					list.setRate((double) result[15]);
					list.setAmount((double) result[16]);
					list.setNewVendorName((String) result[17]);
					list.setNewPickOrDropLocationName((String) result[18]);

					pickDropList.add(list);
				}
			}

			return pickDropList;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleWiseTripSheetPickAndDropDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		Integer 		  pageNumber  = null;
		Integer			  vid		  = 0;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			pageNumber  = valueObject.getInt("pageNumber", 0);
			vid 	    = valueObject.getInt("vid", 0);
			
			Page<TripsheetPickAndDrop> page = getVehicleWiseDeployment_Page_PickAndDropDispatchDetails(pageNumber, vid,	userDetails.getCompany_id());

			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);
			valueObject.put("invoiceCount", page.getTotalElements());

			List<TripsheetPickAndDropDto> pageList = getVehicleWiseTripsheetPickDropDispatchDetails(pageNumber, vid, userDetails.getCompany_id());

			valueObject.put("PickOrDrop", pageList);
			valueObject.put("SelectPage", pageNumber);

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}
	
	public Page<TripsheetPickAndDrop> getVehicleWiseDeployment_Page_PickAndDropDispatchDetails(Integer pageNumber, Integer vid, Integer companyId) {
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC,
				"tripsheetPickAndDropId");
		return TripsheetPickAndDropRepository.getVehicleWiseDeployment_Page_TripsheetPickAndDrop(companyId, vid, pageable);
	}
	
	public List<TripsheetPickAndDropDto> getVehicleWiseTripsheetPickDropDispatchDetails(Integer pageNumber, Integer vid, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			List<Object[]> resultList = null;
			List<TripsheetPickAndDropDto> pickDropList = null;
			TripsheetPickAndDropDto list = null;

			typedQuery = entityManager.createQuery(
					"SELECT TPD.tripsheetPickAndDropId, TPD.tripSheetNumber, TPD.vid, TPD.journeyDate, TPD.tripFristDriverID, "
							+ " TPD.vendorId, TPD.pickOrDropStatus, TPD.pickOrDropId, TPD.tripUsageKM, TPD.tripTotalAdvance, "
							+ " TPD.remark, V.vehicle_registration, D.driver_firstname, CA.corporateName, PL.locationName, "
							+ " TPD.rate, TPD.amount, TPD.newVendorName, TPD.newPickOrDropLocationName " 
							+ " FROM TripsheetPickAndDrop AS TPD "
							+ " INNER JOIN Vehicle AS V ON V.vid = TPD.vid "
							+ " INNER JOIN Driver D ON D.driver_id = TPD.tripFristDriverID "
							+ " LEFT JOIN CorporateAccount CA ON CA.corporateAccountId = TPD.vendorId "
							+ " LEFT JOIN PickAndDropLocation PL ON PL.pickAndDropLocationId = TPD.pickOrDropId "
							+ " WHERE TPD.markForDelete = 0 AND TPD.companyId = " + companyId + " "
							+ " AND TPD.vid = "+vid+" ORDER BY TPD.tripsheetPickAndDropId DESC ",
					Object[].class);

			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				pickDropList = new ArrayList<>();

				for (Object[] result : resultList) {
					list = new TripsheetPickAndDropDto();

					list.setTripsheetPickAndDropId((long) result[0]);
					list.setTripSheetNumber((long) result[1]);
					list.setVid((int) result[2]);
					list.setJourneyDateStr(dateFormat.format((java.util.Date) result[3]));
					list.setJourneyDateStr2(dateFormat2.format((java.util.Date) result[3]));
					list.setTripFristDriverID((int) result[4]);
					list.setVendorId((int) result[5]);
					list.setPickOrDropStatus((short) result[6]);
					list.setPickOrDropStatusStr(
							TripsheetPickAndDropConstant.getPickAndDrop(list.getPickOrDropStatus()));
					list.setPickOrDropId((long) result[7]);
					list.setTripUsageKM((int) result[8]);
					list.setTripTotalAdvance((double) result[9]);
					list.setRemark((String) result[10]);
					list.setVehicleRegistration((String) result[11]);
					list.setDriverName((String) result[12]);
					list.setVendorName((String) result[13]);
					list.setLocationName((String) result[14]);
					list.setRate((double) result[15]);
					list.setAmount((double) result[16]);
					list.setNewVendorName((String) result[17]);
					list.setNewPickOrDropLocationName((String) result[18]);

					pickDropList.add(list);
				}
			}

			return pickDropList;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	@Override
	public ValueObject dispatchPickAndDropTrip(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		TripsheetPickAndDropSequenceCounter sequenceCounter = null;
		TripsheetPickAndDropDto					tripsheetPickAndDropDto		= null;
		TripsheetPickAndDrop 					savedTripsheetPickAndDrop	= null;
		String									content						= "";
		String									tripStatus					= "";	
		String									journeyDate					= "";
		String 									driverMobileNo				= "";
		String 									driverName					= "";
		String 									location					= "";
		String 									vehicleNo					= "";
		String									pickAndDropTripNo			= "";
		TripsheetPickAndDrop 		 			dispatchPickAndDrop 		= null;
		
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			sequenceCounter = TripsheetPickAndDropSequenceService.findNextInvoiceNumber(userDetails.getCompany_id());

			if (sequenceCounter == null) {
				valueObject.put("sequenceNotFound", true);
				return valueObject;
			}
			
			dispatchPickAndDrop = pickAndDropLocationBL.dispatchPickAndDropTrip(valueObject, userDetails);

			dispatchPickAndDrop.setTripSheetNumber(sequenceCounter.getNextVal());
			
			savedTripsheetPickAndDrop = TripsheetPickAndDropRepository.save(dispatchPickAndDrop);

			/*Send Sms To the Driver */
			tripsheetPickAndDropDto 	= getTripsheetPickDropDispatchDetailsById(savedTripsheetPickAndDrop.getTripsheetPickAndDropId(),userDetails.getCompany_id());
			
			driverMobileNo 				= tripsheetPickAndDropDto.getDriverMobileNumber();
			driverName					= tripsheetPickAndDropDto.getDriverName();
			tripStatus					= tripsheetPickAndDropDto.getPickOrDropStatusStr();
			journeyDate					= tripsheetPickAndDropDto.getJourneyDateStr();
			location					= tripsheetPickAndDropDto.getLocationName();
			vehicleNo					= tripsheetPickAndDropDto.getVehicleRegistration();
			pickAndDropTripNo			= "TS-"+tripsheetPickAndDropDto.getTripSheetNumber();	
			content = ""+driverName+" you're requested for next "+tripStatus+"Trip and the trip Details are as follows."+ 
					" Trip NO: "+pickAndDropTripNo+", Trip Date and Time: "+journeyDate+
					", "+tripStatus+" Location: "+location+""+ 
					", Vehicle No: "+vehicleNo+" ";
			

			sendTripDetailsBySMS(driverMobileNo,content);
			valueObject.put("dispatchPickAndDropId", dispatchPickAndDrop.getTripsheetPickAndDropId());

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Override
	public ValueObject getTripsheetPickDropDispatchDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		TripsheetPickAndDropDto pickOrDropDetails = null;
		try {
			userDetails = (CustomUserDetails) valueObject.get("userDetails");
			pickOrDropDetails = getTripsheetPickDropDispatchDetailsById(valueObject.getLong("tripsheetPickAndDropId"),
					userDetails.getCompany_id());

			valueObject.put("pickOrDropDetails", pickOrDropDetails);

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
		}
	}

	public TripsheetPickAndDropDto getTripsheetPickDropDispatchDetailsById(long tripsheetPickAndDropId,
			Integer companyId) throws Exception {
		Query query = null;

		query = entityManager.createQuery(
				"SELECT TPD.tripsheetPickAndDropId, TPD.tripSheetNumber, TPD.vid, TPD.journeyDate, TPD.tripFristDriverID, "
						+ " TPD.vendorId, TPD.pickOrDropStatus, TPD.pickOrDropId, TPD.tripUsageKM, TPD.tripTotalAdvance, "
						+ " TPD.remark, V.vehicle_registration, D.driver_firstname,D.driver_mobnumber,  CA.corporateName, PL.locationName, "
						+ " TPD.rate, TPD.amount, TPD.newVendorName, TPD.newPickOrDropLocationName " 
						+ " FROM TripsheetPickAndDrop AS TPD "
						+ " INNER JOIN Vehicle AS V ON V.vid = TPD.vid "
						+ " INNER JOIN Driver D ON D.driver_id = TPD.tripFristDriverID "
						+ " LEFT JOIN CorporateAccount CA ON CA.corporateAccountId = TPD.vendorId "
						+ " LEFT JOIN PickAndDropLocation PL ON PL.pickAndDropLocationId = TPD.pickOrDropId "
						+ " WHERE TPD.markForDelete = 0 AND TPD.companyId = " + companyId + " "
						+ " AND TPD.tripsheetPickAndDropId = " + tripsheetPickAndDropId + " ");

		Object[] result = (Object[]) query.getSingleResult();

		TripsheetPickAndDropDto list = null;
		if (result != null) {
				list = new TripsheetPickAndDropDto();
				
				list.setTripsheetPickAndDropId((long) result[0]);
				list.setTripSheetNumber((long) result[1]);
				list.setVid((int) result[2]);
				list.setJourneyDateStr(CreatedDateTime.format((java.util.Date) result[3]));
				list.setJourneyDateStr2(dateFormat.format((java.util.Date) result[3]));
		        list.setJourneyTimeStr(localDateFormat.format((java.util.Date) result[3]));
				list.setTripFristDriverID((int) result[4]);
				list.setVendorId((int) result[5]);
				list.setPickOrDropStatus((short)result[6]);
				list.setPickOrDropStatusStr(TripsheetPickAndDropConstant.getPickAndDrop(list.getPickOrDropStatus()));
				list.setPickOrDropId((long) result[7]);
				list.setTripUsageKM((int) result[8]);
				list.setTripTotalAdvance((double) result[9]);
				list.setRemark((String) result[10]);
				list.setVehicleRegistration((String) result[11]);
				list.setDriverName((String) result[12]);
				list.setDriverMobileNumber((String) result[13]);
				list.setVendorName((String) result[14]);
				list.setLocationName((String) result[15]);
				list.setRate((double) result[16]);
				list.setAmount((double) result[17]);
				list.setNewVendorName((String) result[18]);
				list.setNewPickOrDropLocationName((String) result[19]);
			}
		
		return list;
	}

	@Transactional
	@Override
	public ValueObject updatePickAndDropTrip(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		TripsheetPickAndDropDto		tripsheetPickAndDropDto			= null;
		String						tripStatus						= "";	
		String						journeyDate						= "";
		String 						currentDriverMobileNo			= "";
		String 						oldDriverMobileNo				= "";
		String 						driverMobileNoStr				= "";
		List<String>				mobileNo						= null;
		String 						driverName						= "";
		String 						oldDriverName					= "";
		String 						location						= "";
		String 						vehicleNo						= "";
		String						pickAndDropTripNo				= "";
		String 						content 						= "";
		TripsheetPickAndDrop 		oldData 						= null;
		Driver 						oldDriverDetails				= null;
		TripsheetPickAndDrop 		updatedTripsheetPickAndDrop		= null;
		TripsheetPickAndDrop  		dispatchPickAndDrop				= null;
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			oldData = TripsheetPickAndDropRepository.getTripsheetPickAndDropById(valueObject.getLong("tripsheetPickAndDropId"), 
												userDetails.getCompany_id());
			oldDriverDetails 	= driverService.getDriver(oldData.getTripFristDriverID(), userDetails);
			

			if (oldData.getInvoiceStatus() == TripsheetPickAndDropConstant.INVOICE_MADE) {
				valueObject.put("InvoiceMade", true);
				return valueObject;
			}

			valueObject.put("oldData", oldData);
			
			dispatchPickAndDrop 		= pickAndDropLocationBL.updatePickAndDropTrip(valueObject, userDetails);
			updatedTripsheetPickAndDrop = TripsheetPickAndDropRepository.save(dispatchPickAndDrop);
			tripsheetPickAndDropDto 	= getTripsheetPickDropDispatchDetailsById(updatedTripsheetPickAndDrop.getTripsheetPickAndDropId(),userDetails.getCompany_id());
			
			
			currentDriverMobileNo 		= tripsheetPickAndDropDto.getDriverMobileNumber();
			oldDriverMobileNo			= oldDriverDetails.getDriver_mobnumber();
			driverMobileNoStr			= ""+currentDriverMobileNo+","+oldDriverMobileNo+"";
			
			driverName					= tripsheetPickAndDropDto.getDriverName();
			oldDriverName				= oldDriverDetails.getDriver_firstname();
			tripStatus					= tripsheetPickAndDropDto.getPickOrDropStatusStr();
			journeyDate					= tripsheetPickAndDropDto.getJourneyDateStr();
			location					= tripsheetPickAndDropDto.getLocationName();
			vehicleNo					= tripsheetPickAndDropDto.getVehicleRegistration();
			pickAndDropTripNo			= "TS-"+tripsheetPickAndDropDto.getTripSheetNumber();	
			
			if(oldDriverDetails.getDriver_id() == valueObject.getInt("tripFristDriverID")) {
				content = ""+driverName+" there are Some Changes in trip "
						+ " you're requested for next "+tripStatus+"Trip and the trip Details are as follows."+ 
						" Trip NO: "+pickAndDropTripNo+", Trip Date and Time: "+journeyDate+
						", "+tripStatus+" Location: "+location+""+ 
						", Vehicle No: "+vehicleNo+" ";
				
				sendTripDetailsBySMS(currentDriverMobileNo,content);
			}else {
				
				mobileNo =	Arrays.asList(driverMobileNoStr.split(","));// both aold and current driver mobile no
				
				for(String mobileNumber : mobileNo) {
					if(currentDriverMobileNo.equals(mobileNumber.trim()))	{ // for current driver
						content = ""+driverName+" you're requested for next "+tripStatus+"Trip and the trip Details are as follows."+ 
								" Trip NO: "+pickAndDropTripNo+", Trip Date and Time: "+journeyDate+
								", "+tripStatus+" Location: "+location+""+ 
								", Vehicle No: "+vehicleNo+" ";
					}else {// for old driver
						content = ""+oldDriverName+" The trip has been cancelled. The Next trip detail will keep you informed soon " ;
					}
					
					sendTripDetailsBySMS(mobileNumber,content);
				}
				
			}
			
			valueObject.put("TripsheetPickAndDropId", dispatchPickAndDrop.getTripsheetPickAndDropId());
			valueObject.put("updateDone", true);

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Transactional
	@Override
	public ValueObject deleteTripsheetPickAndDrop(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		TripsheetPickAndDropDto		tripsheetPickAndDropDto			= null;
		String						content							= "";
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			tripsheetPickAndDropDto 	= getTripsheetPickDropDispatchDetailsById(valueObject.getLong("tripsheetPickAndDropId"),userDetails.getCompany_id());
			content = ""+tripsheetPickAndDropDto.getDriverName()+" The trip has been cancelled. The Next trip detail will keep you informed soon " ;
			sendTripDetailsBySMS(tripsheetPickAndDropDto.getDriverMobileNumber(),content);
			TripsheetPickAndDropRepository.deleteTripsheetPickAndDrop(valueObject.getLong("tripsheetPickAndDropId"),
					userDetails.getCompany_id());


			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Override
	public ValueObject getPartyWiseTripReport(ValueObject valueObject) throws Exception {
		ValueObject dateRange = null;
		CustomUserDetails userDetails = null;
		List<TripsheetPickAndDropDto> trip = null;
		ValueObject tableConfig = null;
		int vid = 0;
		int partyId = 0;
		String dateRangeFrom = "";
		String dateRangeTo = "";
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vid = valueObject.getInt("vid", 0);
			partyId = valueObject.getInt("partyId", 0);
			dateRange = DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			dateRangeFrom = dateRange.getString(DateTimeUtility.FROM_DATE);
			dateRangeTo = dateRange.getString(DateTimeUtility.TO_DATE);

			if (dateRange != null) {

				String vehicleName = "", partyName = "", Date = "";

				if (vid > 0) {
					vehicleName = " AND TPD.vid = " + vid + " ";
				}

				if (partyId > 0) {
					partyName = " AND TPD.vendorId = " + partyId + " ";
				}

				Date = "  AND (( TPD.journeyDate between '" + dateRangeFrom + "' AND '" + dateRangeTo + "')) ";

				String query = " " + vehicleName + " " + partyName + " " + Date + " ";

				tableConfig = new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH,
						ModuleFilePathConstant.PARTY_WISE_TRIP_TABLE_DATA_FILE_PATH);
				tableConfig = JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig = JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				trip = getPartyWiseTripReportList(query, userDetails.getCompany_id());
			}

			valueObject.put("PickAndDropList", trip);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			tableConfig = null;
			trip = null;
			userDetails = null;
			dateRange = null;
		}
	}

	public List<TripsheetPickAndDropDto> getPartyWiseTripReportList(String query, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			List<Object[]> resultList = null;
			List<TripsheetPickAndDropDto> pickDropList = null;
			TripsheetPickAndDropDto list = null;

			typedQuery = entityManager.createQuery(
					"SELECT TPD.tripsheetPickAndDropId, TPD.tripSheetNumber, TPD.vid, TPD.journeyDate, TPD.tripFristDriverID, "
							+ " TPD.vendorId, TPD.pickOrDropStatus, TPD.pickOrDropId, TPD.tripUsageKM, TPD.tripTotalAdvance, "
							+ " TPD.remark, V.vehicle_registration, D.driver_firstname, CA.corporateName, PL.locationName, "
							+ " TPD.rate, TPD.amount, TPD.newVendorName, TPD.newPickOrDropLocationName " 
							+ " FROM TripsheetPickAndDrop AS TPD "
							+ " INNER JOIN Vehicle AS V ON V.vid = TPD.vid "
							+ " INNER JOIN Driver D ON D.driver_id = TPD.tripFristDriverID "
							+ " LEFT JOIN CorporateAccount CA ON CA.corporateAccountId = TPD.vendorId "
							+ " LEFT JOIN PickAndDropLocation PL ON PL.pickAndDropLocationId = TPD.pickOrDropId "
							+ " WHERE TPD.markForDelete = 0 AND TPD.companyId = " + companyId + " " + " " + query
							+ " ORDER BY TPD.tripsheetPickAndDropId DESC ",
					Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				pickDropList = new ArrayList<>();

				for (Object[] result : resultList) {
					list = new TripsheetPickAndDropDto();

					list.setTripsheetPickAndDropId((long) result[0]);
					list.setTripSheetNumber((long) result[1]);
					list.setVid((int) result[2]);
					list.setJourneyDateStr(dateFormat.format((java.util.Date) result[3]));
					list.setJourneyDateStr2(dateFormat2.format((java.util.Date) result[3]));
					list.setTripFristDriverID((int) result[4]);
					list.setVendorId((int) result[5]);
					list.setPickOrDropStatus((short) result[6]);
					list.setPickOrDropStatusStr(
							TripsheetPickAndDropConstant.getPickAndDrop(list.getPickOrDropStatus()));
					list.setPickOrDropId((long) result[7]);
					list.setTripUsageKM((int) result[8]);
					list.setTripTotalAdvance((double) result[9]);
					list.setRemark((String) result[10]);
					list.setVehicleRegistration((String) result[11]);
					list.setDriverName((String) result[12]);
					list.setVendorName((String) result[13]);
					list.setLocationName((String) result[14]);
					list.setRate((double) result[15]);
					list.setAmount((double) result[16]);
					list.setNewVendorName((String) result[17]);
					list.setNewPickOrDropLocationName((String) result[18]);

					pickDropList.add(list);
				}
			}

			return pickDropList;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ValueObject getInvoiceList(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		List<TripsheetPickAndDropDto> trip = null;
		int partyId = 0;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			partyId = valueObject.getInt("partyId", 0);

			trip = createInvoiceList(partyId, userDetails.getCompany_id());
			valueObject.put("invoiceList", trip);

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			trip = null;
			userDetails = null;
		}
	}

	public List<TripsheetPickAndDropDto> createInvoiceList(Integer partyId, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			List<Object[]> resultList = null;
			List<TripsheetPickAndDropDto> pickDropList = null;
			TripsheetPickAndDropDto list = null;

			typedQuery = entityManager.createQuery(
					"SELECT TPD.tripsheetPickAndDropId, TPD.tripSheetNumber, TPD.vid, TPD.journeyDate, TPD.tripFristDriverID, "
							+ " TPD.vendorId, TPD.pickOrDropStatus, TPD.pickOrDropId, TPD.tripUsageKM, TPD.tripTotalAdvance, "
							+ " TPD.remark, V.vehicle_registration, D.driver_firstname, CA.corporateName, PL.locationName, "
							+ " TPD.rate, TPD.amount, TPD.newVendorName, TPD.newPickOrDropLocationName " 
							+ " FROM TripsheetPickAndDrop AS TPD "
							+ " INNER JOIN Vehicle AS V ON V.vid = TPD.vid "
							+ " INNER JOIN Driver D ON D.driver_id = TPD.tripFristDriverID "
							+ " LEFT JOIN CorporateAccount CA ON CA.corporateAccountId = TPD.vendorId "
							+ " LEFT JOIN PickAndDropLocation PL ON PL.pickAndDropLocationId = TPD.pickOrDropId "
							+ " WHERE TPD.markForDelete = 0 AND TPD.companyId = " + companyId + " "
							+ " AND TPD.vendorId = " + partyId + " " + " AND TPD.invoiceStatus = "
							+ TripsheetPickAndDropConstant.INVOICE_NOT_MADE + " "
							+ " ORDER BY TPD.tripsheetPickAndDropId DESC ",
					Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				pickDropList = new ArrayList<>();

				for (Object[] result : resultList) {
					list = new TripsheetPickAndDropDto();

					list.setTripsheetPickAndDropId((long) result[0]);
					list.setTripSheetNumber((long) result[1]);
					list.setVid((int) result[2]);
					list.setJourneyDateStr(dateFormat.format((java.util.Date) result[3]));
					list.setJourneyDateStr2(dateFormat2.format((java.util.Date) result[3]));
					list.setTripFristDriverID((int) result[4]);
					list.setVendorId((int) result[5]);
					list.setPickOrDropStatus((short) result[6]);
					list.setPickOrDropStatusStr(
							TripsheetPickAndDropConstant.getPickAndDrop(list.getPickOrDropStatus()));
					list.setPickOrDropId((long) result[7]);
					list.setTripUsageKM((int) result[8]);
					list.setTripTotalAdvance((double) result[9]);
					list.setRemark((String) result[10]);
					list.setVehicleRegistration((String) result[11]);
					list.setDriverName((String) result[12]);
					list.setVendorName((String) result[13]);
					list.setLocationName((String) result[14]);
					list.setRate((double) result[15]);
					list.setAmount((double) result[16]);
					list.setNewVendorName((String) result[17]);
					list.setNewPickOrDropLocationName((String) result[18]);

					pickDropList.add(list);
				}
			}

			return pickDropList;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject savePickOrDropInvoice(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		ArrayList<ValueObject> dataArrayObjColl = null;
		TripsheetPickAndDropInvoice pickOrDropInvoice = null;
		TripsheetPickAndDropInvoiceSummary invoiceSummary = null;
		int vendorId = 0;
		int totalKm = 0;
		double totalAmount = 0;
		double totalAdvance = 0;
		TripsheetPickAndDropInvoiceSequenceCounter sequenceCounter = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dataArrayObjColl = (ArrayList<ValueObject>) valueObject.get("invoiceDetails");

			sequenceCounter = TripsheetPickAndDropInvoiceSequenceService
					.findNextInvoiceNumber(userDetails.getCompany_id());

			if (sequenceCounter == null) {
				valueObject.put("sequenceNotFound", true);
				return valueObject;
			}

			if (dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {

				vendorId = dataArrayObjColl.get(0).getInt("partyId");

				for (ValueObject object : dataArrayObjColl) {
					totalKm += object.getInt("usageKm");
					totalAmount += object.getDouble("amount");
					totalAdvance += object.getDouble("advance");
				}
			}

			valueObject.put("vendorId", vendorId);
			valueObject.put("totalKm", totalKm);
			valueObject.put("totalAmount", totalAmount);
			valueObject.put("totalAdvance", totalAdvance);

			invoiceSummary = pickAndDropLocationBL.saveInvoiceSummary(valueObject, userDetails);
			invoiceSummary.setInvoiceNumber(sequenceCounter.getNextVal());
			TripsheetPickAndDropInvoiceSummaryRepository.save(invoiceSummary);

			if (dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {

				for (ValueObject object : dataArrayObjColl) {

					pickOrDropInvoice = pickAndDropLocationBL.saveInvoice(object, userDetails);
					pickOrDropInvoice.setTripsheetPickAndDropInvoiceSummaryId(
							invoiceSummary.getTripsheetPickAndDropInvoiceSummaryId());
					TripsheetPickAndDropInvoiceRepository.save(pickOrDropInvoice);

					entityManager.createQuery(" UPDATE TripsheetPickAndDrop " + " SET invoiceStatus = "
							+ TripsheetPickAndDropConstant.INVOICE_MADE + " " + " WHERE tripsheetPickAndDropId = "
							+ object.getLong("tripsheetPickAndDropId") + "  ").executeUpdate();
				}
			}

			valueObject.put("invoiceCreated", true);
			valueObject.put("invoiceSummaryId", invoiceSummary.getTripsheetPickAndDropInvoiceSummaryId());

			return valueObject;

		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Override
	public ValueObject getInvoiceDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		List<TripsheetPickAndDropDto> invoiceData = null;
		long invoiceSummaryId = 0;
		long invoiceNumber = 0;
		double rate = 0;
		int km = 0;
		double amount = 0.0;
		double advance = 0.0;
		String invoiceDate = null;
		String partyName = null;
		String mobileNo = null;
		String address = null;
		String gstNo = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			invoiceSummaryId = valueObject.getInt("invoiceSummaryId", 0);

			invoiceData = getInvoiceData(invoiceSummaryId, userDetails.getCompany_id());
			valueObject.put("invoiceData", invoiceData);

			if (invoiceData != null) {

				invoiceDate = invoiceData.get(0).getInvoiceDateStr();
				partyName = invoiceData.get(0).getVendorName();
				invoiceNumber = invoiceData.get(0).getInvoiceNumber();
				mobileNo = invoiceData.get(0).getMobileNumber();
				address = invoiceData.get(0).getAddress();
				gstNo = invoiceData.get(0).getGstNumber();

				for (TripsheetPickAndDropDto total : invoiceData) {
					rate += total.getRate();
					km += total.getTripUsageKM();
					amount += total.getAmount();
					advance += total.getTripTotalAdvance();
				}

				valueObject.put("rate", rate);
				valueObject.put("km", km);
				valueObject.put("amount", amount);
				valueObject.put("advance", advance);
				valueObject.put("invoiceDate", invoiceDate);
				valueObject.put("partyName", partyName);
				valueObject.put("invoiceNumber", invoiceNumber);
				valueObject.put("mobileNo", mobileNo);
				valueObject.put("address", address);
				valueObject.put("gstNo", gstNo);

			}

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			invoiceData = null;
			userDetails = null;
		}
	}

	public List<TripsheetPickAndDropDto> getInvoiceData(long invoiceSummaryId, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			List<Object[]> resultList = null;
			List<TripsheetPickAndDropDto> pickDropList = null;
			TripsheetPickAndDropDto list = null;

			typedQuery = entityManager.createQuery(
					"SELECT TPD.tripsheetPickAndDropId, TPD.tripSheetNumber, TPD.vid, TPD.journeyDate, TPD.tripFristDriverID, "
							+ " TPD.vendorId, TPD.pickOrDropStatus, TPD.pickOrDropId, TPD.tripUsageKM, TPD.tripTotalAdvance, "
							+ " TPD.remark, V.vehicle_registration, D.driver_firstname, CA.corporateName, PL.locationName, "
							+ " TPD.rate, TPD.amount, TPDIS.invoiceNumber, CA.address, CA.mobileNumber, CA.gstNumber, "
							+ " TPDIS.invoiceDate, TPD.newVendorName, TPD.newPickOrDropLocationName "
							+ " FROM TripsheetPickAndDropInvoiceSummary AS TPDIS "
							+ " INNER JOIN TripsheetPickAndDropInvoice AS TPDI ON TPDI.tripsheetPickAndDropInvoiceSummaryId = TPDIS.tripsheetPickAndDropInvoiceSummaryId "
							+ " INNER JOIN TripsheetPickAndDrop AS TPD ON TPD.tripsheetPickAndDropId = TPDI.tripsheetPickAndDropId "
							+ " INNER JOIN Vehicle AS V ON V.vid = TPD.vid "
							+ " INNER JOIN Driver D ON D.driver_id = TPD.tripFristDriverID "
							+ " LEFT JOIN CorporateAccount CA ON CA.corporateAccountId = TPD.vendorId "
							+ " LEFT JOIN PickAndDropLocation PL ON PL.pickAndDropLocationId = TPD.pickOrDropId "
							+ " WHERE TPDIS.markForDelete = 0 AND TPDIS.companyId = " + companyId + " "
							+ " AND TPDIS.tripsheetPickAndDropInvoiceSummaryId = " + invoiceSummaryId + " "
							+ " AND TPD.invoiceStatus = " + TripsheetPickAndDropConstant.INVOICE_MADE + " ",
					Object[].class);
			/* + " ORDER BY TPDI.tripsheetPickAndDropInvoiceId ", Object[].class); */

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				pickDropList = new ArrayList<>();

				for (Object[] result : resultList) {
					list = new TripsheetPickAndDropDto();

					list.setTripsheetPickAndDropId((long) result[0]);
					list.setTripSheetNumber((long) result[1]);
					list.setVid((int) result[2]);
					list.setJourneyDateStr2(dateFormat2.format((java.util.Date) result[3]));
					list.setTripFristDriverID((int) result[4]);
					list.setVendorId((int) result[5]);
					list.setPickOrDropStatus((short) result[6]);
					list.setPickOrDropStatusStr(
							TripsheetPickAndDropConstant.getPickAndDrop(list.getPickOrDropStatus()));
					list.setPickOrDropId((long) result[7]);
					list.setTripUsageKM((int) result[8]);
					list.setTripTotalAdvance((double) result[9]);
					list.setRemark((String) result[10]);
					list.setVehicleRegistration((String) result[11]);
					list.setDriverName((String) result[12]);
					list.setVendorName((String) result[13]);
					list.setLocationName((String) result[14]);
					list.setRate((double) result[15]);
					list.setAmount((double) result[16]);
					list.setInvoiceNumber((long) result[17]);

					if (result[18] != null) {
						list.setAddress((String) result[18]);
					} else {
						list.setAddress("--");
					}

					if (result[19] != null) {
						list.setMobileNumber((String) result[19]);
					} else {
						list.setMobileNumber("--");
					}

					if (result[20] != null) {
						list.setGstNumber((String) result[20]);
					} else {
						list.setGstNumber("--");
					}

					list.setInvoiceDateStr(dateFormat.format((java.util.Date) result[21]));
					list.setNewVendorName((String) result[22]);
					list.setNewPickOrDropLocationName((String) result[23]);

					pickDropList.add(list);
				}
			}

			return pickDropList;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ValueObject getInvoicePaymentList(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		List<TripsheetPickAndDropInvoiceSummaryDto> trip = null;
		int partyId = 0;
		ArrayList<TripsheetPickAndDropInvoiceSummaryDto> paymentMode = new ArrayList<>();
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			partyId = valueObject.getInt("partyId", 0);

			paymentMode.add(
					new TripsheetPickAndDropInvoiceSummaryDto(TripsheetPickAndDropConstant.PAYMENT_MODE_PARTIALLY_PAID,
							TripsheetPickAndDropConstant.PAYMENT_MODE_PARTIALLY_PAID_NAME));
			paymentMode.add(
					new TripsheetPickAndDropInvoiceSummaryDto(TripsheetPickAndDropConstant.PAYMENT_MODE_NEGOTIABLE_PAID,
							TripsheetPickAndDropConstant.PAYMENT_MODE_NEGOTIABLE_PAID_NAME));
			paymentMode.add(
					new TripsheetPickAndDropInvoiceSummaryDto(TripsheetPickAndDropConstant.PAYMENT_MODE_CLEAR_PAYMENT,
							TripsheetPickAndDropConstant.PAYMENT_MODE_CLEAR_PAYMENT_NAME));

			trip = createInvoicePaymentList(partyId, userDetails.getCompany_id());
			valueObject.put("invoicePaymentList", trip);
			valueObject.put("paymentMode", paymentMode);
			valueObject.put("paymentTypeList", VehicleEmiPaymentStatus.getPaymentTypeList());

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			trip = null;
			userDetails = null;
		}
	}

	public List<TripsheetPickAndDropInvoiceSummaryDto> createInvoicePaymentList(Integer partyId, Integer companyId)
			throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			List<Object[]> resultList = null;
			List<TripsheetPickAndDropInvoiceSummaryDto> paymentList = null;
			TripsheetPickAndDropInvoiceSummaryDto list = null;

			typedQuery = entityManager.createQuery(
					"SELECT TPDIS.tripsheetPickAndDropInvoiceSummaryId, TPDIS.invoiceNumber, TPDIS.vendorId, TPDIS.totalKm, "
							+ " TPDIS.totalAmount, TPDIS.totalAdvance, TPDIS.invoiceDate, CA.corporateName, TPDIS.balanceAmount "
							+ " FROM TripsheetPickAndDropInvoiceSummary AS TPDIS "
							+ " INNER JOIN CorporateAccount CA ON CA.corporateAccountId = TPDIS.vendorId "
							+ " WHERE TPDIS.markForDelete = 0 AND TPDIS.companyId = " + companyId + " "
							+ " AND TPDIS.vendorId = " + partyId + " " + " AND TPDIS.paymentStatus = "
							+ TripsheetPickAndDropConstant.INVOICE_PAYMENT_NOT_MADE + " "
							+ " ORDER BY TPDIS.tripsheetPickAndDropInvoiceSummaryId DESC ",
					Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				paymentList = new ArrayList<>();

				for (Object[] result : resultList) {
					list = new TripsheetPickAndDropInvoiceSummaryDto();

					list.setTripsheetPickAndDropInvoiceSummaryId((long) result[0]);
					list.setInvoiceNumber((long) result[1]);
					list.setVendorId((int) result[2]);
					list.setTotalKm((int) result[3]);
					list.setTotalAmount((double) result[4]);
					list.setTotalAdvance((double) result[5]);
					list.setInvoiceDateStr(dateFormat.format((java.util.Date) result[6]));
					list.setPartyName((String) result[7]);
					list.setBalanceAmount((double) result[8]);

					paymentList.add(list);
				}
			}

			return paymentList;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject savePickOrDropInvoicePayment(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		ArrayList<ValueObject> dataArrayObjColl = null;
		TripsheetPickAndDropPayment invoicePayment = null;
		TripsheetPickAndDropInvoiceSummary invoiceSummary = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dataArrayObjColl = (ArrayList<ValueObject>) valueObject.get("invoicePaymentDetails");

			if (dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {

				for (ValueObject object : dataArrayObjColl) {

					invoiceSummary = TripsheetPickAndDropInvoiceSummaryRepository
							.getInvoiceById(object.getLong("invoiceSummaryId"), userDetails.getCompany_id());

					if (invoiceSummary.getPaymentStatus() == TripsheetPickAndDropConstant.INVOICE_PAYMENT_MADE) {
						valueObject.put("paymentAlreadyDone", true);
						return valueObject;
					}

					if (object.getDouble("paidAmount") > invoiceSummary.getBalanceAmount()) {
						valueObject.put("balanceIsZero", true);
						return valueObject;
					}

					if (object.getShort("paymentMode") == TripsheetPickAndDropConstant.PAYMENT_MODE_PARTIALLY_PAID
							&& object.getDouble("paidAmount") == invoiceSummary.getBalanceAmount()) {
						valueObject.put("changePayTypeToFull", true);
						return valueObject;
					}

					if (object.getShort("paymentMode") == TripsheetPickAndDropConstant.PAYMENT_MODE_CLEAR_PAYMENT
							&& object.getDouble("paidAmount") != invoiceSummary.getBalanceAmount()) {
						valueObject.put("changePayTypeToPartialOrNegotiate", true);
						return valueObject;
					}

					invoicePayment = pickAndDropLocationBL.saveInvoicePayment(object, userDetails);
					TripsheetPickAndDropPaymentRepository.save(invoicePayment);

					entityManager.createQuery(" UPDATE TripsheetPickAndDropInvoiceSummary "
							+ " SET balanceAmount = balanceAmount - " + object.getDouble("paidAmount") + " "
							+ " WHERE tripsheetPickAndDropInvoiceSummaryId = " + object.getLong("invoiceSummaryId")
							+ "  ").executeUpdate();

					if (object.getShort("paymentMode") == TripsheetPickAndDropConstant.PAYMENT_MODE_NEGOTIABLE_PAID
							|| object.getShort(
									"paymentMode") == TripsheetPickAndDropConstant.PAYMENT_MODE_CLEAR_PAYMENT) {

						entityManager.createQuery(" UPDATE TripsheetPickAndDropInvoiceSummary "
								+ " SET paymentStatus = " + TripsheetPickAndDropConstant.INVOICE_PAYMENT_MADE + " "
								+ " WHERE tripsheetPickAndDropInvoiceSummaryId = " + object.getLong("invoiceSummaryId")
								+ "  ").executeUpdate();

					}

					valueObject.put("InvoicePayment", true);

				}
			}

			return valueObject;

		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Override
	public ValueObject getPartyWiseInvoicePaymentReport(ValueObject valueObject) throws Exception {
		ValueObject dateRange = null;
		CustomUserDetails userDetails = null;
		List<TripsheetPickAndDropPaymentDto> trip = null;
		ValueObject tableConfig = null;
		int partyId = 0;
		String dateRangeFrom = "";
		String dateRangeTo = "";
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			partyId = valueObject.getInt("partyId", 0);
			dateRange = DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			dateRangeFrom = dateRange.getString(DateTimeUtility.FROM_DATE);
			dateRangeTo = dateRange.getString(DateTimeUtility.TO_DATE);

			if (dateRange != null) {

				String partyName = "", Date = "";

				if (partyId > 0) {
					partyName = " AND TPDIS.vendorId = " + partyId + " ";
				}

				Date = "  AND (( TPDP.paidDate between '" + dateRangeFrom + "' AND '" + dateRangeTo + "')) ";

				String query = " " + partyName + " " + Date + " ";

				tableConfig = new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH,
						ModuleFilePathConstant.PARTY_WISE_INVOICE_PAYMENT_TABLE_DATA_FILE_PATH);
				tableConfig = JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig = JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				trip = getPartyWiseInvoicePaymentList(query, userDetails.getCompany_id());
			}

			valueObject.put("InvoicePaymentList", trip);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			tableConfig = null;
			trip = null;
			userDetails = null;
			dateRange = null;
		}
	}

	public List<TripsheetPickAndDropPaymentDto> getPartyWiseInvoicePaymentList(String query, Integer companyId)
			throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			List<Object[]> resultList = null;
			List<TripsheetPickAndDropPaymentDto> paymentList = null;
			TripsheetPickAndDropPaymentDto list = null;

			typedQuery = entityManager.createQuery(
					"SELECT TPDIS.tripsheetPickAndDropInvoiceSummaryId, TPDIS.invoiceNumber, TPDIS.vendorId, TPDIS.totalAmount, "
							+ " TPDIS.invoiceDate, CA.corporateName, TPDP.paymentType, TPDP.paymentMode, TPDP.paidDate, TPDP.paidAmount "
							+ " FROM TripsheetPickAndDropPayment AS TPDP "
							+ " INNER JOIN TripsheetPickAndDropInvoiceSummary TPDIS ON TPDIS.tripsheetPickAndDropInvoiceSummaryId = TPDP.tripsheetPickAndDropInvoiceSummaryId "
							+ " INNER JOIN CorporateAccount CA ON CA.corporateAccountId = TPDIS.vendorId "
							+ " WHERE TPDP.markForDelete = 0 AND TPDP.companyId = " + companyId + " " + query + "  "
							+ " ORDER BY TPDP.tripsheetPickAndDropPaymentId DESC ",
					Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				paymentList = new ArrayList<>();

				for (Object[] result : resultList) {
					list = new TripsheetPickAndDropPaymentDto();

					list.setTripsheetPickAndDropInvoiceSummaryId((long) result[0]);
					list.setInvoiceNumber((long) result[1]);
					list.setInvoiceNumStr("IN-" + list.getInvoiceNumber());
					list.setVendorId((int) result[2]);
					list.setTotalAmount((double) result[3]);
					list.setInvoiceDateStr(dateFormat.format((java.util.Date) result[4]));
					list.setVendorName((String) result[5]);
					list.setPaymentTypeStr(VehicleEmiPaymentStatus.getPaymentModeStr((short) result[6]));
					list.setPaymentModeStr(TripsheetPickAndDropConstant.getPaymentMode((short) result[7]));
					list.setPaidDateStr(dateFormat.format((java.util.Date) result[8]));
					list.setPaidAmount((double) result[9]);

					paymentList.add(list);
				}
			}

			return paymentList;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	@Transactional
	public ValueObject deleteInvoice(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		long invoiceSummaryId = 0;
		List<TripsheetPickAndDropPayment> invoicePayment = null;
		List<TripsheetPickAndDropInvoice> invoice = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			invoiceSummaryId = valueObject.getLong("invoiceSummaryId", 0);

			invoicePayment = TripsheetPickAndDropPaymentRepository.getInvoicePaymentBySummaryId(invoiceSummaryId,
					userDetails.getCompany_id());

			if (invoicePayment != null && !invoicePayment.isEmpty()) {
				valueObject.put("paymentDone", true);
				return valueObject;

			} else {

				TripsheetPickAndDropInvoiceSummaryRepository.deleteInvoiceById(invoiceSummaryId,
						userDetails.getCompany_id());

				invoice = TripsheetPickAndDropInvoiceRepository.getInvoiceById(invoiceSummaryId,
						userDetails.getCompany_id());

				for (TripsheetPickAndDropInvoice invoiceList : invoice) {

					entityManager
							.createQuery(" UPDATE TripsheetPickAndDropInvoice " + " SET markForDelete = 1 "
									+ " WHERE tripsheetPickAndDropInvoiceSummaryId = " + invoiceSummaryId + "  ")
							.executeUpdate();

					entityManager.createQuery(" UPDATE TripsheetPickAndDrop " + " SET invoiceStatus = "
							+ TripsheetPickAndDropConstant.INVOICE_NOT_MADE + " " + " WHERE tripsheetPickAndDropId = "
							+ invoiceList.getTripsheetPickAndDropId() + "  ").executeUpdate();

				}

				valueObject.put("invoiceDeleted", true);

			}

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			invoicePayment = null;
			invoice = null;
		}
	}

	@Transactional
	@Override
	public ValueObject savePickOrDropDispatchMobileData(ValueObject valueObject) throws Exception {
		int companyId = 0;
		long userId = 0;
		CustomUserDetails userDetails = null;
		TripsheetPickAndDropSequenceCounter sequenceCounter = null;
		try {
			companyId = valueObject.getInt("companyId");
			userId = valueObject.getLong("userId");
			userDetails = new CustomUserDetails(companyId, userId);
			valueObject.put("userDetails", userDetails);

			sequenceCounter = TripsheetPickAndDropSequenceService.findNextInvoiceNumber(userDetails.getCompany_id());

			if (sequenceCounter == null) {
				valueObject.put("sequenceNotFound", true);
				return valueObject;
			}

			TripsheetPickAndDrop dispatchPickAndDrop = pickAndDropLocationBL.dispatchPickAndDropTripFromMobile(valueObject, userDetails);
			dispatchPickAndDrop.setTripSheetNumber(sequenceCounter.getNextVal());
			TripsheetPickAndDropRepository.save(dispatchPickAndDrop);
			
		    valueObject.put("dispatchPickAndDropId", dispatchPickAndDrop.getTripsheetPickAndDropId());

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
		}
	}
	
	// this is also for(if trip edited and change the driver so this msg also notify the previous driver that trip is cancel)
		@Override
		public ValueObject sendTripDetailsBySMS(String mobileNo , String content) throws Exception {
			ValueObject						valueObject						= null;
			try {
				
			valueObject = new ValueObject();
			HttpURLConnection urlconnection	= null;
			String retval = "";
			String postData = "";
			String content1 = "Dear," +content+"";
		//	String content1 = "hi,"+content+""; fixed
			String msgUrl  = "http://api-alerts.solutionsinfini.com/v3/?method=sms&api_key=A2936335d5bf59f706e47b96f2bdcd82f&to="+mobileNo.trim()+"&sender=IVCRGO&message= "+content1+"";
			postData += URLEncoder.encode(msgUrl,"UTF-8");
			URL url = new URL(msgUrl);
			urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("POST");
			urlconnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			urlconnection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(urlconnection.getOutputStream());
			out.write(postData);
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				retval += decodedString;
			}
			in.close();
			valueObject.put("sendSms", true);
			return valueObject;
		} catch (Exception e) {
				LOGGER.error("Err"+e);
				throw e;
			}finally {
				content 	= "";
				mobileNo	= "";
			}
		}
	
	@Override
	public ValueObject editTripsheetPickDropDispatchDetailsFromMobile(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		TripsheetPickAndDropDto pickOrDropDetails = null;
		try {
			userDetails = (CustomUserDetails) valueObject.get("userDetails");
			pickOrDropDetails = getTripsheetPickDropDispatchDetailsById(valueObject.getLong("tripsheetPickAndDropId"),
					userDetails.getCompany_id());

			valueObject.put("pickOrDropDetails", pickOrDropDetails);

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
		}
	}
	
	@Transactional
	@Override
	public ValueObject updateTripsheetPickDropDispatchDetailsFromMobile(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) valueObject.get("userDetails");

			TripsheetPickAndDrop oldData = TripsheetPickAndDropRepository.getTripsheetPickAndDropById(
					valueObject.getLong("tripsheetPickAndDropId"), userDetails.getCompany_id());

			if (oldData.getInvoiceStatus() == TripsheetPickAndDropConstant.INVOICE_MADE) {
				valueObject.put("InvoiceMade", true);
				return valueObject;
			}

			valueObject.put("oldData", oldData);

			TripsheetPickAndDrop dispatchPickAndDrop = pickAndDropLocationBL.updatePickAndDropTripFromMobile(valueObject,
					userDetails);
			TripsheetPickAndDropRepository.save(dispatchPickAndDrop);

			valueObject.put("TripsheetPickAndDropId", dispatchPickAndDrop.getTripsheetPickAndDropId());
			valueObject.put("updateDone", true);

			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err" + e);
			throw e;
		} finally {
			userDetails = null;
		}
	}
	
	@Override
	public ValueObject searchPickAndDropTripByVehicle(ValueObject object) throws Exception {
		int 								companyId 				= 0;
		long 								userId 					= 0;
		int 								vid 					= 0;
		CustomUserDetails 					userDetails 			= null;
		List<TripsheetPickAndDropDto> 		trip 					= null;
		String 								vehicleName 			= "";
		try {
			companyId 					= object.getInt("companyId");
			userId 						= object.getLong("userId");
			vid 						= object.getInt("vid", 0);
			
			userDetails 	= new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			if (vid > 0) {
				vehicleName = " AND TPD.vid = " + vid + " ";
			}
			
			String query = " "+vehicleName+" ";
			
			trip = getPartyWiseTripReportList(query, userDetails.getCompany_id());
			
			if(trip != null) {
				object.put("tripFound", true);
				object.put("trip", trip);
			} else {
				object.put("tripNotFound", true);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			trip 			= null;
			userDetails 	= null;
		}
	}
	
	@Override
	public HashMap<Integer, Long> pickAndDropCreatedBetweenDates(String startDate, String endDate) throws Exception {
		TypedQuery<Object[]> 	typedQuery = null;
		HashMap<Integer, Long>	map		   = null;
		
		typedQuery = entityManager.createQuery(
				"SELECT COUNT(WO.tripsheetPickAndDropId), WO.companyId "
						+ " From TripsheetPickAndDrop as WO "
						+ " LEFT JOIN Vehicle V ON V.vid = WO.vid "
						+ " WHERE WO.journeyDate between '"+startDate+"' AND '"+endDate+"' "
						+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4 GROUP BY WO.companyId ",
						Object[].class);
		
		List<Object[]> results = typedQuery.getResultList();

		if (results != null && !results.isEmpty()) {
			
			map	= new HashMap<>();
			
			for (Object[] result : results) {
				
				map.put((Integer)result[1], (Long)result[0]);
				
			}
		}
		
		return map;
	}
	
	@Override
	public long pickAndDropCreatedBetweenDatesByCompanyId(String startDate, String endDate, int companyId) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT COUNT(WO.tripsheetPickAndDropId) "
						+ " From TripsheetPickAndDrop as WO "
						+ " INNER JOIN Vehicle V ON V.vid = WO.vid "
						+ " WHERE WO.journeyDate between '"+startDate+"' AND '"+endDate+"' "
						+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4 AND WO.companyId = "+companyId+" ",
						Object[].class);
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}
	
	@Override
	public long vehiclesWithPickAndDropBetweenDates(String startDate, String endDate, int companyId) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT COUNT(DISTINCT WO.vid) "
						+ " From TripsheetPickAndDrop as WO "
						+ " INNER JOIN Vehicle V ON V.vid = WO.vid "
						+ " WHERE WO.journeyDate between '"+startDate+"' AND '"+endDate+"' "
						+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4 AND WO.companyId = "+companyId+" ",
						Object[].class);
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}
	
	@Override
	public double pickAndDropTotalAmountBetweenDates(String startDate, String endDate, int companyId) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT SUM(WO.amount) "
						+ " From TripsheetPickAndDrop as WO "
						+ " WHERE WO.journeyDate between '"+startDate+"' AND '"+endDate+"' "
						+ " AND WO.markForDelete = 0 AND WO.companyId = "+companyId+" ",
						Object[].class);
		
		Double count = (double) 0;
		try {
			
			if((Double) queryt.getSingleResult() != null) {
			count = (Double) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}
	
	@Override
	public long totalPickUpsBetweenDates(String startDate, String endDate, int companyId) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT COUNT(WO.tripsheetPickAndDropId) "
						+ " From TripsheetPickAndDrop as WO "
						+ " WHERE WO.journeyDate between '"+startDate+"' AND '"+endDate+"' "
						+ " AND WO.markForDelete = 0 AND WO.companyId = "+companyId+" "
						+ " AND WO.pickOrDropStatus = "+TripsheetPickAndDropConstant.PICKUP+" ",
						Object[].class);
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}
	
	@Override
	public long totalDropsBetweenDates(String startDate, String endDate, int companyId) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT COUNT(WO.tripsheetPickAndDropId) "
						+ " From TripsheetPickAndDrop as WO "
						+ " WHERE WO.journeyDate between '"+startDate+"' AND '"+endDate+"' "
						+ " AND WO.markForDelete = 0 AND WO.companyId = "+companyId+" "
						+ " AND WO.pickOrDropStatus = "+TripsheetPickAndDropConstant.DROP+" ",
						Object[].class);
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}
	
	@Override
	public List<TripsheetPickAndDropDto> pickAndDropCreatedBetweenDatesList(Integer companyID, String issueStatusQuery) throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			List<Object[]> resultList = null;
			List<TripsheetPickAndDropDto> pickDropList = null;
			TripsheetPickAndDropDto list = null;

			typedQuery = entityManager.createQuery(
					"SELECT TPD.tripsheetPickAndDropId, TPD.tripSheetNumber, TPD.vid, TPD.journeyDate, TPD.tripFristDriverID, "
							+ " TPD.vendorId, TPD.pickOrDropStatus, TPD.pickOrDropId, TPD.tripUsageKM, TPD.tripTotalAdvance, "
							+ " TPD.remark, V.vehicle_registration, D.driver_firstname, CA.corporateName, PL.locationName, "
							+ " TPD.rate, TPD.amount, TPD.newVendorName, TPD.newPickOrDropLocationName " 
							+ " FROM TripsheetPickAndDrop AS TPD "
							+ " INNER JOIN Vehicle AS V ON V.vid = TPD.vid "
							+ " INNER JOIN Driver D ON D.driver_id = TPD.tripFristDriverID "
							+ " LEFT JOIN CorporateAccount CA ON CA.corporateAccountId = TPD.vendorId "
							+ " LEFT JOIN PickAndDropLocation PL ON PL.pickAndDropLocationId = TPD.pickOrDropId "
							+ " WHERE "+issueStatusQuery+" AND "
							+ " TPD.markForDelete = 0 AND TPD.companyId = "+companyID+" "
							+ " ORDER BY TPD.tripsheetPickAndDropId DESC ",
					Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				pickDropList = new ArrayList<>();

				for (Object[] result : resultList) {
					list = new TripsheetPickAndDropDto();

					list.setTripsheetPickAndDropId((long) result[0]);
					list.setTripSheetNumber((long) result[1]);
					list.setVid((int) result[2]);
					list.setJourneyDateStr(dateFormat.format((java.util.Date) result[3]));
					list.setJourneyDateStr2(dateFormat2.format((java.util.Date) result[3]));
					list.setTripFristDriverID((int) result[4]);
					list.setVendorId((int) result[5]);
					list.setPickOrDropStatus((short) result[6]);
					list.setPickOrDropStatusStr(TripsheetPickAndDropConstant.getPickAndDrop(list.getPickOrDropStatus()));
					list.setPickOrDropId((long) result[7]);
					list.setTripUsageKM((int) result[8]);
					list.setTripTotalAdvance((double) result[9]);
					list.setRemark((String) result[10]);
					list.setVehicleRegistration((String) result[11]);
					list.setDriverName((String) result[12]);
					list.setVendorName((String) result[13]);
					list.setLocationName((String) result[14]);
					list.setRate((double) result[15]);
					list.setAmount((double) result[16]);
					list.setNewVendorName((String) result[17]);
					list.setNewPickOrDropLocationName((String) result[18]);

					pickDropList.add(list);
				}
			}

			return pickDropList;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<TripsheetPickAndDropDto> vehiclesWithPickAndDropBetweenDatesList(Integer companyID, String issueStatusQuery) throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			List<Object[]> resultList = null;
			List<TripsheetPickAndDropDto> pickDropList = null;
			TripsheetPickAndDropDto list = null;

			typedQuery = entityManager.createQuery(
					"SELECT V.vehicle_registration, TPD.vid, COUNT(TPD.vid) "
							+ " FROM TripsheetPickAndDrop AS TPD "
							+ " INNER JOIN Vehicle AS V ON V.vid = TPD.vid "
							+ " WHERE "+issueStatusQuery+" AND "
							+ " TPD.markForDelete = 0 AND TPD.companyId = "+companyID+" "
							+ " GROUP BY TPD.vid"
							+ " ORDER BY TPD.tripsheetPickAndDropId DESC ",
					Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				pickDropList = new ArrayList<>();

				for (Object[] result : resultList) {
					list = new TripsheetPickAndDropDto();

					list.setVehicleRegistration((String) result[0]);
					list.setVid((int) result[1]);
					list.setTripsheetPickAndDropId((long) result[2]);

					pickDropList.add(list);
				}
			}

			return pickDropList;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public List<TripsheetPickAndDropDto> vehiclesWithPickAndDropBetweenDatesWithUniqueVehicleId(String startDate, String endDate, int companyId) throws Exception {
		
		TypedQuery<Object[]> typedQuery = null;
		List<Object[]> resultList = null;
		List<TripsheetPickAndDropDto> pickDropList = null;
		TripsheetPickAndDropDto list = null;
		
		typedQuery = entityManager.createQuery(
				"SELECT DISTINCT(WO.vid), V.vehicle_registration "
						+ " From TripsheetPickAndDrop as WO "
						+ " INNER JOIN Vehicle V ON V.vid = WO.vid "
						+ " WHERE WO.journeyDate between '"+startDate+"' AND '"+endDate+"' "
						+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4 AND WO.companyId = "+companyId+" ",
						Object[].class);
		
		resultList = typedQuery.getResultList();

		if (resultList != null && !resultList.isEmpty()) {
			pickDropList = new ArrayList<>();

			for (Object[] result : resultList) {
				list = new TripsheetPickAndDropDto();

				list.setVid((int) result[0]);
				list.setVehicleRegistration((String) result[1]);

				pickDropList.add(list);
			}
		}

		return pickDropList;
	}
}
