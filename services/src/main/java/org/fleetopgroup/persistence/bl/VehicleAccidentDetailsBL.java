package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fleetopgroup.persistence.model.ServeyorDetails;
import org.fleetopgroup.persistence.model.SpotSurveyorDetails;
import org.fleetopgroup.persistence.model.VehicleAccidentAdvance;
import org.fleetopgroup.persistence.model.VehicleAccidentDetails;
import org.fleetopgroup.persistence.model.VehicleAccidentPersonDetails;
import org.fleetopgroup.persistence.model.VehicleAccidentTypeDetails;
import org.fleetopgroup.persistence.model.VehicleExpenses;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;

public class VehicleAccidentDetailsBL {
	
	public VehicleAccidentDetailsBL () {
		super();
	}
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	public static VehicleAccidentDetails	getVehicleAccidentDetailsModel(ValueObject	valueObject,VehicleAccidentDetails		accidentDetails) throws Exception{
		
		try {
			
			java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("accidentDate"), valueObject.getString("accidentTime"));
			if(accidentDetails == null)
				accidentDetails	= new VehicleAccidentDetails();
			
			accidentDetails.setVid(valueObject.getInt("vid",0));
			accidentDetails.setAccidentDateTime(date);
			accidentDetails.setCompanyId(valueObject.getInt("companyId",0));
			accidentDetails.setCreatedById(valueObject.getLong("userId",0));
			accidentDetails.setCreatedOn(new Date());
			accidentDetails.setLastUpdateById(valueObject.getLong("userId",0));
			accidentDetails.setLastUpdatedOn(new Date());
			accidentDetails.setDescription(valueObject.getString("description","").trim());
			accidentDetails.setDriverId(valueObject.getInt("driverId",0));
			accidentDetails.setLocation(valueObject.getString("incidentlocation"));
			accidentDetails.setTripSheetId(valueObject.getLong("tripSheetId",0));
			
			accidentDetails.setAccidentWithVehicle(valueObject.getString("otherVehicle","").trim());
			accidentDetails.setAccidentWithDriver(valueObject.getString("otherDriver","").trim());
			accidentDetails.setAccidentWithDriverrMobile(valueObject.getString("otherDriverMob","").trim());
			accidentDetails.setAccidentWithOwner(valueObject.getString("otherVehicleOwner","").trim());
			accidentDetails.setAccidentWithOwnerMobile(valueObject.getString("otherVehicleOwnerMob","").trim());
			accidentDetails.setAccidentWithOtherDetails(valueObject.getString("otherVehicleDetails","").trim());
			
			accidentDetails.setFirNumber(valueObject.getString("firNumber","").trim());
			accidentDetails.setFirPoliceStation(valueObject.getString("firPoliceStation","").trim());
			accidentDetails.setFirBy(valueObject.getString("firBy","").trim());
			accidentDetails.setFirRemark(valueObject.getString("firRemark","").trim());
			
			if(accidentDetails.getDescription() != null) {
				accidentDetails.setDescription(accidentDetails.getDescription().trim());
			}
			if(accidentDetails.getFirRemark() != null) {
				accidentDetails.setFirRemark(accidentDetails.getFirRemark().trim());
			}
			accidentDetails.setRouteId(valueObject.getInt("routeId",0) );
			accidentDetails.setApproxDamageAmount(valueObject.getDouble("approxDamageAmount",0) );
			accidentDetails.setDamageAmount(valueObject.getDouble("damageAmount",0) );
			accidentDetails.setDamageAmountStatusId(valueObject.getShort("damageAmountStatusId",(short)0));
			accidentDetails.setClaim(valueObject.getBoolean("isClaim",false));
			accidentDetails.setClaimRemark(valueObject.getString("claimRemark",""));
			
			return accidentDetails;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static ServeyorDetails getServeyorDetailsDTO(ValueObject	valueObject, ServeyorDetails details) throws Exception {
		if(details == null) {
			details	= new ServeyorDetails();
		}
		details.setAccidentId(Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId"))));
		details.setServeyorCompany(valueObject.getString("serveyorCompany"));
		details.setServeyorMobile(valueObject.getString("serveyorMobile"));
		details.setServeyorName(valueObject.getString("serveyorName"));
		details.setRemark(valueObject.getString("remark"));
		details.setCompanyId(valueObject.getInt("companyId",0));
		details.setCreated(new Date());
		details.setLastUpdated(new Date());
		details.setLastUpdatedById(valueObject.getLong("userId",0));
		details.setCreatedById(valueObject.getLong("userId",0));
		

		if (valueObject.getString("spotSurveyorDate",null) != null) {
			String start_time = "00:00";
			if(valueObject.getString("spotSurveyorTime",null) != null && valueObject.getString("spotSurveyorTime") != "") {
				start_time	= valueObject.getString("spotSurveyorTime");
			}
			java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("spotSurveyorDate"), start_time);
			java.sql.Date start_date = new java.sql.Date(date.getTime());
			details.setServeyInformDate(start_date);
		}else{
			details.setServeyInformDate(null);
		}
		
		return details;
	}
	
	public static VehicleAccidentAdvance getVehicleAccidentAdvanceObj(ValueObject	valueObject) throws Exception{
		SimpleDateFormat	dateFormat	= new SimpleDateFormat("dd-MM-yyyy");
		VehicleAccidentAdvance	accidentAdvance	= new VehicleAccidentAdvance();
		accidentAdvance.setAccidentId(Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId"))));
		accidentAdvance.setAdvanceAmount(valueObject.getDouble("advanceAmount",0));
		accidentAdvance.setAdvanceDate(dateFormat.parse(valueObject.getString("advanceDate")));
		accidentAdvance.setRemark(valueObject.getString("advanceRemark"));
		accidentAdvance.setCompanyId(valueObject.getInt("companyId",0));
		accidentAdvance.setCreatedOn(new Date());
		accidentAdvance.setLastUpdatedOn(new Date());
		accidentAdvance.setLastUpdatedById(valueObject.getLong("userId",0));
		accidentAdvance.setCreatedById(valueObject.getLong("userId",0));
		
		return accidentAdvance;
	}
	
	public static VehicleExpenses getVehicleExpensesObj(ValueObject	valueObject) throws Exception{
		SimpleDateFormat	dateFormat	= new SimpleDateFormat("dd-MM-yyyy");
		VehicleExpenses	accidentAdvance	= new VehicleExpenses();
		accidentAdvance.setAccidentId(Long.parseLong(AESEncryptDecrypt.decryptBase64(valueObject.getString("accidentId"))));
		accidentAdvance.setExpenseAmount(valueObject.getDouble("expenseAmount",0));
		accidentAdvance.setExpenseDate(dateFormat.parse(valueObject.getString("expenseDate")));
		accidentAdvance.setVid(valueObject.getInt("vid",0));
		accidentAdvance.setRemark(valueObject.getString("expenseRemark"));
		accidentAdvance.setExpenseType(valueObject.getString("expenseType"));
		accidentAdvance.setCompanyId(valueObject.getInt("companyId",0));
		accidentAdvance.setCreatedOn(new Date());
		accidentAdvance.setLastUpdatedOn(new Date());
		accidentAdvance.setLastUpdatedById(valueObject.getLong("userId",0));
		accidentAdvance.setCreatedById(valueObject.getLong("userId",0));
		
		return accidentAdvance;
	}
	
	public VehicleAccidentTypeDetails prepareVehicleAccidentTypeDetails(ValueObject valueObject) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			VehicleAccidentTypeDetails vehicleAccidentTypeDetails = new VehicleAccidentTypeDetails();
			vehicleAccidentTypeDetails.setVehicleAccidentTypeDetailsId(valueObject.getLong("vehicleAccidentTypeDetailsId",0));
			vehicleAccidentTypeDetails.setAccidentDetailsId(valueObject.getLong("accidentId",0));
			vehicleAccidentTypeDetails.setVehicleAccidentTypeId(valueObject.getShort("vehicleAccidentTypeId",(short)0));
			vehicleAccidentTypeDetails.setNatureOfOwnDamage(valueObject.getString("natureOfOwnDamage",""));
			vehicleAccidentTypeDetails.setApproxOwnDamgeCost(valueObject.getDouble("approxOwnDamgeCost",0));
			vehicleAccidentTypeDetails.setNatureOfTPDamage(valueObject.getString("natureOfTPDamage",""));
			vehicleAccidentTypeDetails.setApproxTPDamgeCost(valueObject.getDouble("approxTPDamgeCost",0));
			vehicleAccidentTypeDetails.setDescription(valueObject.getString("description",""));
			vehicleAccidentTypeDetails.setCreatedById(valueObject.getLong("userId"));
			vehicleAccidentTypeDetails.setLastUpdatedBy(valueObject.getLong("userId"));
			vehicleAccidentTypeDetails.setCreationDate(toDate);
			vehicleAccidentTypeDetails.setLastUpdatedOn(toDate);
			vehicleAccidentTypeDetails.setCompanyId(valueObject.getInt("companyId"));
			vehicleAccidentTypeDetails.setMarkForDelete(false);

			return vehicleAccidentTypeDetails;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public VehicleAccidentPersonDetails prepareVehicleAccidentPersonDetails(ValueObject object,ValueObject valueObject) throws Exception {
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		                              
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());    

			VehicleAccidentPersonDetails vehicleAccidentPersonDetails = new VehicleAccidentPersonDetails();
			vehicleAccidentPersonDetails.setVehicleAccidentPersonDetailsId(object.getLong("vehicleAccidentPersonDetailsId",0));
			vehicleAccidentPersonDetails.setAccidentDetailsId(valueObject.getLong("accidentId",0));
			vehicleAccidentPersonDetails.setVehicleAccidentPersonTypeId(object.getShort("vehicleAccidentPersonTypeId",(short)1));
			vehicleAccidentPersonDetails.setVehicleAccidentPersonStatusId(object.getShort("vehicleAccidentPersonStatusId",(short)1));
			vehicleAccidentPersonDetails.setName(object.getString("name",""));
			vehicleAccidentPersonDetails.setAge(object.getInt("age",0));
			vehicleAccidentPersonDetails.setDescription(object.getString("description",""));
			vehicleAccidentPersonDetails.setCreatedById(valueObject.getLong("userId"));
			vehicleAccidentPersonDetails.setLastUpdatedBy(valueObject.getLong("userId"));
			vehicleAccidentPersonDetails.setCreationDate(toDate);
			vehicleAccidentPersonDetails.setLastUpdatedOn(toDate);
			vehicleAccidentPersonDetails.setCompanyId(valueObject.getInt("companyId"));
			vehicleAccidentPersonDetails.setMarkForDelete(false);

			return vehicleAccidentPersonDetails;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		

	}

	public SpotSurveyorDetails prepareSpotSurveyorDetails(ValueObject valueObject) throws Exception {
		SpotSurveyorDetails	spotSurveyorDetails = null;
		Date 		currentDateUpdate  = null;
		Timestamp 	toDate 				= null;	
		try {
			currentDateUpdate 	= new Date();                                             
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());  
			spotSurveyorDetails = new SpotSurveyorDetails();
			spotSurveyorDetails.setSpotSurveyorDetailsId(valueObject.getLong("spotSurveyorDetailsId",0));
			spotSurveyorDetails.setAccidentId(valueObject.getLong("accidentId"));
			spotSurveyorDetails.setSpotSurveyorName(valueObject.getString("spotSurveyorName"));
			spotSurveyorDetails.setSpotSurveyorMobile(valueObject.getString("spotSurveyorMobile"));
			spotSurveyorDetails.setSpotSurveyorCompany(valueObject.getString("spotSurveyorCompany"));
			spotSurveyorDetails.setSpotSurveyorRemark(valueObject.getString("spotSurveyorRemark"));
			
			if (valueObject.getString("spotSurveyorDate",null) != null) {
				String start_time = "00:00";
				if(valueObject.getString("spotSurveyorTime",null) != null && valueObject.getString("spotSurveyorTime") != "") {
					start_time	= valueObject.getString("spotSurveyorTime");
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("spotSurveyorDate"), start_time);
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				spotSurveyorDetails.setSpotSurveyorDate(start_date);
			}else{
				spotSurveyorDetails.setSpotSurveyorDate(null);
			}
			
			if (valueObject.getString("spotSurveyorCompletionDate",null) != null) {
				String start_time = "00:00";
				if(valueObject.getString("spotSurveyorCompletionTime",null) != null && valueObject.getString("spotSurveyorCompletionTime") != "") {
					start_time	= valueObject.getString("spotSurveyorCompletionTime");
				}
				java.util.Date date = DateTimeUtility.getDateTimeFromDateTimeString(valueObject.getString("spotSurveyorCompletionDate"), start_time);
				java.sql.Date start_date = new java.sql.Date(date.getTime());
				spotSurveyorDetails.setSpotSurveyorCompletionDate(start_date);
			}else{
				spotSurveyorDetails.setSpotSurveyorCompletionDate(null);
			}
			spotSurveyorDetails.setSpotSurveyorCompletionRemark(valueObject.getString("spotSurveyorCompletionRemark"));
			spotSurveyorDetails.setCreatedById(valueObject.getLong("userId"));
			spotSurveyorDetails.setLastUpdatedById(valueObject.getLong("userId"));
			spotSurveyorDetails.setCreated(toDate);
			spotSurveyorDetails.setLastUpdated(toDate);
			spotSurveyorDetails.setCompanyId(valueObject.getInt("companyId"));
			spotSurveyorDetails.setMarkForDelete(false);
			return spotSurveyorDetails;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
}
