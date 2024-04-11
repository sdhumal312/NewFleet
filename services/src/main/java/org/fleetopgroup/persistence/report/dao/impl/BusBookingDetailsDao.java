package org.fleetopgroup.persistence.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.BusBookingDetailsRepository;
import org.fleetopgroup.persistence.dto.BusBookingDetailsDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.model.BusBookingDetails;
import org.fleetopgroup.persistence.report.dao.IBusBookingDetailsDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class BusBookingDetailsDao implements IBusBookingDetailsDao {

	@PersistenceContext
	public EntityManager entityManager;
	
	@Autowired private BusBookingDetailsRepository		busBookingDetailsRepository;
	
	private static final int PAGE_SIZE = 10;

	SimpleDateFormat	format	= new SimpleDateFormat(DateTimeUtility.DD_MM_YY_HH_MM_SS);
	
	SimpleDateFormat	dateFormat	= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
	
	@Override
	public Page<BusBookingDetails> getDeployment_Page_BusBookingDetails(Integer pageNumber, Integer companyId) {
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "busBookingDetailsId");
		return busBookingDetailsRepository.getDeployment_Page_BusBookingDetails(companyId, pageable);
	
	}
	@Override
	public List<BusBookingDetailsDto> getPageWiseBusBookingDetails(Integer pageNumber, Integer companyId)
			throws Exception {
		TypedQuery<Object[]> 								typedQuery 				= null;
		List<Object[]> 										resultList 				= null; 
		List<BusBookingDetailsDto> 							bookingList				= null;
		try {
			typedQuery = entityManager.createQuery("SELECT VP.busBookingNumber, VP.bookingRefNumber, VP.partyId, VP.tripStartDateTime, VP.tripEndDateTime,"
					+ " VP.placeOfVisit, VP.partyMobileNumber, VP.partyGSTNo, CA.corporateName, VP.pickUpAddress, VP.busBookingDetailsId,"
					+ " T.tripSheetNumber, VP.tripSheetId "
					+ " FROM BusBookingDetails  AS VP"
					+ " INNER JOIN CorporateAccount CA ON CA.corporateAccountId = VP.partyId "
					+ " LEFT JOIN TripSheet T ON T.tripSheetID = VP.tripSheetId"
					+ " WHERE VP.companyId ="+companyId+" AND VP.markForDelete = 0 ORDER BY VP.busBookingDetailsId DESC", Object[].class);
			 
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				bookingList = new ArrayList<>();
				for (Object[] result : resultList) {
					BusBookingDetailsDto  bookingDetailsDto = new BusBookingDetailsDto(); 
					
					bookingDetailsDto.setBusBookingNumber((Long) result[0]);
					bookingDetailsDto.setBookingRefNumber((String) result[1]);
					bookingDetailsDto.setPartyId((Long) result[2]);
					bookingDetailsDto.setTripStartDateTime((Date) result[3]);
					bookingDetailsDto.setTripEndDateTime((Date) result[4]);
					bookingDetailsDto.setPlaceOfVisit((String) result[5]);
					bookingDetailsDto.setPartyMobileNumber((String) result[6]);
					bookingDetailsDto.setPartyGSTNo((String) result[7]);
					bookingDetailsDto.setCorporateName((String) result[8]);
					bookingDetailsDto.setPickUpAddress((String) result[9]);
					bookingDetailsDto.setBusBookingDetailsId((Long) result[10]);
					bookingDetailsDto.setTripSheetNumber((Long) result[11]);
					bookingDetailsDto.setTripSheetId((Long) result[12]);
					
					
					if(bookingDetailsDto.getTripStartDateTime() != null) {
						bookingDetailsDto.setTripStartDateStr(format.format(bookingDetailsDto.getTripStartDateTime()));
					}
					if(bookingDetailsDto.getTripEndDateTime() != null) {
						bookingDetailsDto.setTripEndDateStr(format.format(bookingDetailsDto.getTripEndDateTime()));
					}
					
					bookingList.add(bookingDetailsDto);
				}
			}
			return bookingList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			bookingList				= null;
		}
	}
	
	@Override
	public BusBookingDetailsDto getBusBookingDetails(Long busBookingDetailsId) throws Exception {
		BusBookingDetailsDto		busBookingDetailsDto		= null;
		try {
			
			Query query = entityManager.createQuery(
					"SELECT BB.busBookingDetailsId, BB.busBookingNumber, BB.bookingRefNumber, BB.busBookingDate, BB.nameOfParty, BB.partyGSTNo,"
							+ " BB.partyId, BB.partyMobileNumber, BB.partyAddress, BB.reportToName, BB.reportToMobileNumber,"
							+ " BB.billingAddress, BB.vehicleTypeId, BB.tripStartDateTime, BB.tripEndDateTime, BB.placeOfVisit, BB.pickUpAddress,"
							+ " BB.dropAddress, BB.rate, BB.hireAmount, BB.remark,CA.corporateName, VT.vtype, T.tripSheetNumber, U.firstName, U2.firstName,"
							+ " BB.createdOn, BB.lastModifiedOn, BB.companyId, BB.createdById, BB.lastModifiedById, V.vehicle_registration "
							+ " FROM BusBookingDetails BB "
							+ " INNER JOIN CorporateAccount CA ON CA.corporateAccountId = BB.partyId"
							+ " LEFT JOIN VehicleType VT ON VT.tid = BB.vehicleTypeId "
							+ " LEFT JOIN TripSheet T ON T.tripSheetID = BB.tripSheetId AND T.markForDelete = 0 "
							+ " LEFT JOIN Vehicle V on V.vid = T.vid"
							+ " LEFT JOIN User U on U.id = BB.createdById"
							+ " LEFT JOIN User U2 on U2.id = BB.lastModifiedById"
							+ " WHERE BB.busBookingDetailsId = "+busBookingDetailsId+" AND BB.markForDelete = 0");

			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			if (result != null) {
				busBookingDetailsDto = new BusBookingDetailsDto();
				
				busBookingDetailsDto.setBusBookingDetailsId((Long) result[0]);
				busBookingDetailsDto.setBusBookingNumber((Long) result[1]);
				busBookingDetailsDto.setBookingRefNumber((String) result[2]);
				busBookingDetailsDto.setBusBookingDate((Date) result[3]);
				busBookingDetailsDto.setNameOfParty((String) result[4]);
				busBookingDetailsDto.setPartyGSTNo((String) result[5]);
				busBookingDetailsDto.setPartyId((Long) result[6]);
				busBookingDetailsDto.setPartyMobileNumber((String) result[7]);
				busBookingDetailsDto.setPartyAddress((String) result[8]);
				busBookingDetailsDto.setReportToName((String) result[9]);
				busBookingDetailsDto.setReportToMobileNumber((String) result[10]);
				busBookingDetailsDto.setBillingAddress((String) result[11]);
				busBookingDetailsDto.setVehicleTypeId((Long) result[12]);
				busBookingDetailsDto.setTripStartDateTime((Date) result[13]);
				busBookingDetailsDto.setTripEndDateTime((Date) result[14]);
				busBookingDetailsDto.setPlaceOfVisit((String) result[15]);
				busBookingDetailsDto.setPickUpAddress((String) result[16]);
				busBookingDetailsDto.setDropAddress((String) result[17]);
				busBookingDetailsDto.setRate((Double) result[18]);
				busBookingDetailsDto.setHireAmount((Double) result[19]);
				busBookingDetailsDto.setRemark((String) result[20]);
				busBookingDetailsDto.setCorporateName((String) result[21]);
				busBookingDetailsDto.setVehicleType((String) result[22]);
				busBookingDetailsDto.setTripSheetNumber((Long) result[23]);
				busBookingDetailsDto.setCreatedBy((String) result[24]);
				busBookingDetailsDto.setLastModifiedBy((String) result[25]);
				busBookingDetailsDto.setCreatedOn((Date) result[26]);
				busBookingDetailsDto.setLastModifiedOn((Date) result[27]);
				busBookingDetailsDto.setCompanyId((Integer) result[28]);
				busBookingDetailsDto.setCreatedById((Long) result[29]);
				busBookingDetailsDto.setLastModifiedById((Long) result[30]);
				busBookingDetailsDto.setVehicle_registration((String) result[31]);
				
				if(busBookingDetailsDto.getTripStartDateTime() != null) {
					busBookingDetailsDto.setTripStartDateStr(format.format(busBookingDetailsDto.getTripStartDateTime()));
				}
				if(busBookingDetailsDto.getTripEndDateTime() != null) {
					busBookingDetailsDto.setTripEndDateStr(format.format(busBookingDetailsDto.getTripEndDateTime()));
				}
				if(busBookingDetailsDto.getCreatedOn() != null) {
					busBookingDetailsDto.setCreatedOnStr(format.format(busBookingDetailsDto.getCreatedOn()));
				}
				if(busBookingDetailsDto.getLastModifiedOn() != null) {
					busBookingDetailsDto.setLastModifiedOnStr(format.format(busBookingDetailsDto.getLastModifiedOn()));
				}
				
				if(busBookingDetailsDto.getBusBookingDate() != null) {
					busBookingDetailsDto.setBookingDateStr(dateFormat.format(busBookingDetailsDto.getBusBookingDate()));
				}
				
			} else {
				return null;
			}

			return busBookingDetailsDto;
		} catch (Exception e) {
			throw e;
		}finally {
			busBookingDetailsDto		= null;
		}
	}
	
	@Override
	public BusBookingDetailsDto getBusBookingDetailsByTripSheetId(Long tripSheetId) throws Exception {
		BusBookingDetailsDto		busBookingDetailsDto		= null;
		try {
			
			Query query = entityManager.createQuery(
					"SELECT BB.busBookingDetailsId, BB.busBookingNumber, BB.bookingRefNumber, BB.busBookingDate, BB.nameOfParty, BB.partyGSTNo,"
							+ " BB.partyId, BB.partyMobileNumber, BB.partyAddress, BB.reportToName, BB.reportToMobileNumber,"
							+ " BB.billingAddress, BB.vehicleTypeId, BB.tripStartDateTime, BB.tripEndDateTime, BB.placeOfVisit, BB.pickUpAddress,"
							+ " BB.dropAddress, BB.rate, BB.hireAmount, BB.remark,CA.corporateName, VT.vtype, T.tripSheetNumber, U.firstName, U2.firstName,"
							+ " BB.createdOn, BB.lastModifiedOn, BB.companyId, BB.createdById, BB.lastModifiedById "
							+ " FROM BusBookingDetails BB "
							+ " INNER JOIN CorporateAccount CA ON CA.corporateAccountId = BB.partyId"
							+ " LEFT JOIN VehicleType VT ON VT.tid = BB.vehicleTypeId "
							+ " LEFT JOIN TripSheet T ON T.tripSheetID = BB.tripSheetId  AND T.markForDelete = 0"
							+ " LEFT JOIN User U on U.id = BB.createdById"
							+ " LEFT JOIN User U2 on U2.id = BB.lastModifiedById"
							+ " WHERE BB.tripSheetId = "+tripSheetId+" AND BB.markForDelete = 0");

			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			if (result != null) {
				busBookingDetailsDto = new BusBookingDetailsDto();
				
				busBookingDetailsDto.setBusBookingDetailsId((Long) result[0]);
				busBookingDetailsDto.setBusBookingNumber((Long) result[1]);
				busBookingDetailsDto.setBookingRefNumber((String) result[2]);
				busBookingDetailsDto.setBusBookingDate((Date) result[3]);
				busBookingDetailsDto.setNameOfParty((String) result[4]);
				busBookingDetailsDto.setPartyGSTNo((String) result[5]);
				busBookingDetailsDto.setPartyId((Long) result[6]);
				busBookingDetailsDto.setPartyMobileNumber((String) result[7]);
				busBookingDetailsDto.setPartyAddress((String) result[8]);
				busBookingDetailsDto.setReportToName((String) result[9]);
				busBookingDetailsDto.setReportToMobileNumber((String) result[10]);
				busBookingDetailsDto.setBillingAddress((String) result[11]);
				busBookingDetailsDto.setVehicleTypeId((Long) result[12]);
				busBookingDetailsDto.setTripStartDateTime((Date) result[13]);
				busBookingDetailsDto.setTripEndDateTime((Date) result[14]);
				busBookingDetailsDto.setPlaceOfVisit((String) result[15]);
				busBookingDetailsDto.setPickUpAddress((String) result[16]);
				busBookingDetailsDto.setDropAddress((String) result[17]);
				busBookingDetailsDto.setRate((Double) result[18]);
				busBookingDetailsDto.setHireAmount((Double) result[19]);
				busBookingDetailsDto.setRemark((String) result[20]);
				busBookingDetailsDto.setCorporateName((String) result[21]);
				busBookingDetailsDto.setVehicleType((String) result[22]);
				busBookingDetailsDto.setTripSheetNumber((Long) result[23]);
				busBookingDetailsDto.setCreatedBy((String) result[24]);
				busBookingDetailsDto.setLastModifiedBy((String) result[25]);
				busBookingDetailsDto.setCreatedOn((Date) result[26]);
				busBookingDetailsDto.setLastModifiedOn((Date) result[27]);
				busBookingDetailsDto.setCompanyId((Integer) result[28]);
				busBookingDetailsDto.setCreatedById((Long) result[29]);
				busBookingDetailsDto.setLastModifiedById((Long) result[30]);
				
				if(busBookingDetailsDto.getTripStartDateTime() != null) {
					busBookingDetailsDto.setTripStartDateStr(format.format(busBookingDetailsDto.getTripStartDateTime()));
				}
				if(busBookingDetailsDto.getTripEndDateTime() != null) {
					busBookingDetailsDto.setTripEndDateStr(format.format(busBookingDetailsDto.getTripEndDateTime()));
				}
				if(busBookingDetailsDto.getCreatedOn() != null) {
					busBookingDetailsDto.setCreatedOnStr(format.format(busBookingDetailsDto.getCreatedOn()));
				}
				if(busBookingDetailsDto.getLastModifiedOn() != null) {
					busBookingDetailsDto.setLastModifiedOnStr(format.format(busBookingDetailsDto.getLastModifiedOn()));
				}
				
			} else {
				return null;
			}

			return busBookingDetailsDto;
		} catch (Exception e) {
			throw e;
		}finally {
			busBookingDetailsDto		= null;
		}
		
	}
		
		
		@Override
		public List<BusBookingDetailsDto> getBusBookingCalendarDetails(BusBookingDetailsDto busBookingDetailsDto) throws Exception {
			
			TypedQuery<Object[]> query =	null;
			try {
				
				query = entityManager.createQuery("SELECT BB.busBookingDetailsId,BB.busBookingNumber,BB.tripStartDateTime,BB.tripEndDateTime,"
						+ "BB.nameOfParty,BB.partyMobileNumber,BB.vehicleTypeId,BB.pickUpAddress,BB.dropAddress, BB.rate, VT.vtype ,CO.corporateName"
						+ " FROM BusBookingDetails AS BB"
						+" INNER JOIN CorporateAccount CO ON CO.corporateAccountId=BB.partyId"
						+" LEFT JOIN VehicleType VT ON VT.tid = BB.vehicleTypeId "
						+ "WHERE BB.tripStartDateTime between '"+busBookingDetailsDto.getFromDate() +"' AND '"+busBookingDetailsDto.getToDate()+"'"+
						" AND BB.companyId ="+busBookingDetailsDto.getCompanyId()+" AND BB.markForDelete = 0",Object[].class);
				
				List<Object[]> results =query.getResultList();
				
				List<BusBookingDetailsDto> dtos= new ArrayList<>();
				
				if(results != null && !results.isEmpty()) {
					BusBookingDetailsDto list=null;
					for(Object[] result : results) {
						list=new BusBookingDetailsDto();
						list.setBusBookingDetailsId((long) result[0]);
						list.setBusBookingNumber((long)result[1]);
						list.setTripStartDateTime((Date)result[2]);
						list.setTripStartDateStr(format.format(result[2]));
						list.setTripEndDateTime((Date)result[3]);
						list.setTripEndDateStr(format.format(result[3]));
						list.setNameOfParty((String)result[4]);
						list.setPartyMobileNumber((String)result[5]);
						list.setVehicleTypeId((Long)result[6]);
						list.setPickUpAddress((String) result[7]);
						list.setDropAddress((String) result[8]);
						list.setRate((Double)result[9]);
						list.setVehicleType((String) result[10]);
						if(list.getNameOfParty()==null || list.getNameOfParty().trim().isEmpty()) {
							list.setNameOfParty((String)result[11]);
						}
						dtos.add(list);
					}
				}
				return dtos;
			} catch (Exception e) {
				throw e;
			}finally {
				query =	null;
			}
		}
		
		@Override
		public List<BusBookingDetailsDto> getVehicleTypeWiseList (BusBookingDetailsDto busBookingDetailsDto) throws Exception{
			
			TypedQuery<Object[]> query=null;
			
			try {
					query=entityManager.createQuery("SELECT BB.busBookingDetailsId,BB.busBookingNumber,BB.tripStartDateTime,BB.tripEndDateTime,"
							+ "BB.nameOfParty,BB.partyMobileNumber,BB.vehicleTypeId,BB.pickUpAddress,BB.dropAddress, BB.rate, VT.vtype ,CO.corporateName"
							+ " FROM BusBookingDetails AS BB"
							+" INNER JOIN CorporateAccount CO ON CO.corporateAccountId=BB.partyId"
							+" LEFT JOIN VehicleType VT ON VT.tid = BB.vehicleTypeId "
							+ "WHERE BB.tripStartDateTime between '"+busBookingDetailsDto.getFromDate() +"' AND '"+busBookingDetailsDto.getToDate()+"'"+
							" AND BB.companyId ="+busBookingDetailsDto.getCompanyId()+"AND BB.vehicleTypeId = '" +busBookingDetailsDto.getVehicleTypeId()+ "' AND BB.markForDelete = 0 " ,Object[].class);
					
					
					List<Object[]>results=query.getResultList();
					
					List<BusBookingDetailsDto> dtos= new ArrayList<>();
					
					if(results != null && !results.isEmpty()) {
						BusBookingDetailsDto list=null;
						for(Object[] result : results) {
							list=new BusBookingDetailsDto();
							list.setBusBookingDetailsId((long) result[0]);
							list.setBusBookingNumber((long)result[1]);
							list.setTripStartDateTime((Date)result[2]);
							list.setTripStartDateStr(format.format(result[2]));
							list.setTripEndDateTime((Date)result[3]);
							list.setTripEndDateStr(format.format(result[3]));
							list.setNameOfParty((String)result[4]);
							list.setPartyMobileNumber((String)result[5]);
							list.setVehicleTypeId((Long)result[6]);
							list.setPickUpAddress((String) result[7]);
							list.setDropAddress((String) result[8]);
							list.setRate((Double)result[9]);
							list.setVehicleType((String) result[10]);
							if(list.getNameOfParty()==null || list.getNameOfParty().trim().isEmpty()) {
								list.setNameOfParty((String)result[11]);
							}
							dtos.add(list);
						}
					}
					return dtos;
					
			
			}catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<BusBookingDetailsDto> getDateWiseCount(BusBookingDetailsDto busBookingDetailsDto) throws Exception {
			
			TypedQuery<Object[]> query= null;
			
			try {

				query=entityManager.createQuery("select count(*), tripStartDateTime from BusBookingDetails where tripStartDateTime between '"+busBookingDetailsDto.getFromDate()+"' and '"+busBookingDetailsDto.getToDate() +"' "
						+ ""
						+ "AND companyId='"+busBookingDetailsDto.getCompanyId() +"' and markFordelete = 0 group by date(tripStartDateTime)",Object[].class);
				
				List<Object[]> result=query.getResultList();
				
				List<BusBookingDetailsDto> list=new ArrayList<>();
				
				if(result != null && !result.isEmpty()) {
					BusBookingDetailsDto obj= null;
						for(Object[] results :result) {
							obj= new BusBookingDetailsDto();
							if(results[0] != null) {
							obj.setDateWiseCount((Long)results[0]);
							}
							if(results[1] != null) {
							obj.setTripStartDateStr(format.format(results[1]));
							obj.setTripStartDateTime((Date)results[1]);
							}
							list.add(obj);
						}
					}
				
				return list;	
			
			}catch (Exception e) {
				throw e;
			}finally {
				query =	null;
			}
		}
		
		@Override
		public List<BusBookingDetailsDto> getVehicleTypeWiseCount(BusBookingDetailsDto busBookingDetailsDto) throws Exception{
			TypedQuery<Object[]> query=null;
			
			try {
				
				query=entityManager.createQuery("SELECT count(*), VT.vtype , BB.vehicleTypeId FROM BusBookingDetails AS BB "
						+ "LEFT JOIN VehicleType VT ON VT.tid = BB.vehicleTypeId WHERE "
						+ "BB.tripStartDateTime between '"+ busBookingDetailsDto.getFromDate()+"' AND '"+ busBookingDetailsDto.getToDate()+"' and BB.markForDelete = 0 "
						+ "group by (VT.vtype)",Object[].class);
				
				List<Object[]> results=query.getResultList();
				System.out.println("result "+results);
				List<BusBookingDetailsDto> list = new ArrayList<BusBookingDetailsDto>();
				
				if(results != null && !results.isEmpty()) {
					BusBookingDetailsDto busBookingDto=null;
					
					for(Object[] result : results) {
						busBookingDto = new BusBookingDetailsDto();
						
						busBookingDto.setDateWiseCount((long)result[0]);
						
						busBookingDto.setVehicleType((String)result[1]);
						
						busBookingDto.setVehicleTypeId((long) result[2]);
						list.add(busBookingDto);
					}
				}
				return list;
			}catch (Exception e) {
				throw e;
				
			}
		}
	}

