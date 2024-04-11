package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleEmiPaymentStatus;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleEmiBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.dao.VehicleEmiDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleEmiPaymentDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleEmiDetailDto;
import org.fleetopgroup.persistence.dto.VehicleEmiPaymentDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.MonthWiseVehicleExpense;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleEmiDetails;
import org.fleetopgroup.persistence.model.VehicleEmiPaymentDetails;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.report.dao.VehicleEmiDetailsDao;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleEmiDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleEmiDetailsService implements IVehicleEmiDetailsService {
	
	@PersistenceContext EntityManager entityManager;
	
	@Autowired private VehicleEmiDetailsDao							vehicleEmiDetailsDao;
	@Autowired private VehicleEmiDetailsRepository					vehicleEmiDetailsRepository;
	@Autowired private VehicleEmiPaymentDetailsRepository			vehicleEmiPaymentDetailsRepository;
	@Autowired private IVehicleProfitAndLossService					vehicleProfitAndLossService;
	@Autowired private VehicleProfitAndLossTxnCheckerService		vehicleProfitAndLossTxnCheckerService;
	@Autowired private IVehicleService								vehicleService;
	@Autowired private	IVehicleAgentTxnCheckerService				vehicleAgentTxnCheckerService;
	@Autowired private	IVehicleAgentIncomeExpenseDetailsService	vehicleAgentIncomeExpenseDetailsService;

	
	VehicleEmiBL     					VehicleEmiBL   		= 	new VehicleEmiBL();
	VehicleProfitAndLossTxnCheckerBL	txnCheckerBL 		= 	new VehicleProfitAndLossTxnCheckerBL();
	VehicleAgentTxnCheckerBL			agentTxnCheckerBL	= new VehicleAgentTxnCheckerBL();
	
	SimpleDateFormat 					CreatedDateTime = 	new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat 					format 			= 	new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 					format1 		= 	new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	SimpleDateFormat 					format2 		= 	new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	@Override
	public ValueObject getVehicleEmiDetails(ValueObject object) throws Exception {
		CustomUserDetails			userDetails				= null;
		List<VehicleEmiDetailDto>	vehicleEmiDetailList	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicleEmiDetailList = vehicleEmiDetailsDao.getVehicleEmiDetailsList(object.getInt("vid", 0), userDetails.getCompany_id());
			if(vehicleEmiDetailList != null)
			object.put("vehicleEmiDetailDto", vehicleEmiDetailList);
			
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails	= null;
		}
	}
	
	@Override
	public ValueObject saveVehicleEmiDetails(ValueObject object) throws Exception {
		CustomUserDetails			userDetails					= null;
		VehicleEmiDetails			vehicleEmiDetails			= null;
		boolean						vehicleMonthlyEMIPayment	= false;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicleEmiDetails = VehicleEmiBL.getVehicleEmmiDetailsDto(object);
			vehicleEmiDetailsRepository.save(vehicleEmiDetails);
			
			vehicleMonthlyEMIPayment = object.getBoolean("vehicleMonthlyEMIPayment", false);
			
			if(vehicleMonthlyEMIPayment == true) {
				ValueObject		profitLossObject	= new ValueObject();
				
				profitLossObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, vehicleEmiDetails.getVehicleEmiDetailsId());
				profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
				profitLossObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
				profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, vehicleEmiDetails.getVid());
				profitLossObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, vehicleEmiDetails.getVehicleEmiDetailsId());
				
				VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(profitLossObject);
				vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
				
				object.put("success", "Data Saved Successfully !");
				
				if(profitAndLossTxnChecker != null) {
					
					ValueObject		valueInObject	= new ValueObject();
					valueInObject.put("vehicleEmiDetails", vehicleEmiDetails);
					valueInObject.put("userDetails", userDetails);
					valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueInObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
					valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					
					new Thread() {
						public void run() {
							try {
								vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueInObject);
								vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueInObject);
								vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueInObject);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}		
					}.start();
				}
			}	
			
			return object;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleEmiDetails		= null;
		}
	}
	
	@Override
	public ValueObject getVehicleEmiDetailsById(ValueObject object) throws Exception {
		CustomUserDetails			userDetails				= null;
		VehicleEmiDetailDto			vehicleEmiDetailDto		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicleEmiDetailDto = vehicleEmiDetailsDao.getVehicleEmiDetailsById(object.getLong("vehicleEmiDetailsId", 0), userDetails.getCompany_id());
			if(vehicleEmiDetailDto != null)
			object.put("vehicleEmiDetailDto", vehicleEmiDetailDto);
			
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails	= null;
		}
	}
	
	
	@Override
	public ValueObject updateVehicleEmiDetails(ValueObject object) throws Exception {
		VehicleEmiDetails				vehicleEmiDetails			= null;
		VehicleEmiDetails				oldEmiDetails				= null;
		List<VehicleEmiPaymentDetails>	paidEmiDetails				= null;
		CustomUserDetails				userDetails					= null;
		boolean							vehicleMonthlyEMIPayment	= false;
		boolean							isEMIEdit					= false;
		try {
				isEMIEdit = object.getBoolean("isEMIEdit",false);
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				oldEmiDetails = vehicleEmiDetailsRepository.getVehicleEmiDetailsById(object.getLong("vehicleEmiDetailsId"), 
						userDetails.getCompany_id());
			
				paidEmiDetails = vehicleEmiPaymentDetailsRepository.getVehicleEmiPaymentByEMIDetailsId(object.getLong("vehicleEmiDetailsId"), 
						userDetails.getCompany_id());
				
				if(paidEmiDetails != null && !paidEmiDetails.isEmpty()) {
					object.put("EMIPaymentStarted", true);
				} else {
					vehicleEmiDetails 	 = VehicleEmiBL.getVehicleEmmiDetailsUpdateDto(object);
					vehicleEmiDetailsRepository.save(vehicleEmiDetails);
					
					object.put("success", "Data Updated Successfully !");
					
					vehicleMonthlyEMIPayment = object.getBoolean("vehicleMonthlyEMIPayment",false);
					
					if( vehicleMonthlyEMIPayment == true && oldEmiDetails != null && !oldEmiDetails.getDownPaymentAmount().equals(vehicleEmiDetails.getDownPaymentAmount())) {
						
						ValueObject		profitLossObject	= new ValueObject();
						
						profitLossObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, vehicleEmiDetails.getVehicleEmiDetailsId());
						profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
						profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
						profitLossObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, vehicleEmiDetails.getVid());
						profitLossObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, vehicleEmiDetails.getVehicleEmiDetailsId());
						
						VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(profitLossObject);
						
						vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
						
						if(profitAndLossTxnChecker != null) {
							
							ValueObject		valueInObject	= new ValueObject();
							valueInObject.put("vehicleEmiDetails", vehicleEmiDetails);
							valueInObject.put("loanStartDateEdit",object.getString("loanStartDate"));  
							valueInObject.put("userDetails", userDetails);
							valueInObject.put("isEMIEdit", isEMIEdit);
							valueInObject.put("preDownpaymentAmount", oldEmiDetails.getDownPaymentAmount());
							valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
							valueInObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
							valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
							
							new Thread() {
								public void run() {
									try {
										vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueInObject);
										vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueInObject);
										vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueInObject);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}		
							}.start();
						}
						
					}
					
					
				}
			
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleEmiDetails		= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject deleteVehicleEmiDetailsById(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		MonthWiseVehicleExpense	monthWiseVehicleExpense = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valueObject.put("companyId", userDetails.getCompany_id());
			monthWiseVehicleExpense = vehicleProfitAndLossService.getMonthWiseVehicleExpenseByExpenseIdAndVid(valueObject);
			
			if(monthWiseVehicleExpense != null) {
				double expenseAmount =monthWiseVehicleExpense.getExpenseAmount()- valueObject.getDouble("downpaymentAmount",0);
				valueObject.put("expenseAmount", expenseAmount);
				vehicleProfitAndLossService.updateMonthWiseVehicleExpenseAmount(valueObject);
			}
			
			
			vehicleEmiDetailsRepository.deleteVehicleEmiDetailsById(valueObject.getLong("vehicleEmiDetailsId"));
			vehicleEmiPaymentDetailsRepository.deleteVehicleEmiPaymentDetailsById(valueObject.getLong("vehicleEmiDetailsId"));
			
			valueObject.put("deleted", true);
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public List<VehicleEmiDetailDto> getVehicleEMIForMonth(Timestamp fromDate, Timestamp toDate, Integer vid) throws Exception {
		
		TypedQuery<Object[]>	queryt = entityManager.createQuery(
					"SELECT R.vehicleEmiDetailsId, R.vid, R.loanStartDate, R.loanEndDate, R.monthlyEmiAmount, R.downPaymentAmount "
							+ " FROM VehicleEmiDetails AS R "
							+ " INNER JOIN Vehicle V ON V.vid = R.vid "
							+ " where R.vid = "+vid+" AND R.loanStartDate BETWEEN '"+fromDate+"' AND '"+toDate+"' "
							+ " AND R.markForDelete = 0",
					Object[].class);
		
		List<Object[]> results = queryt.getResultList();

		List<VehicleEmiDetailDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleEmiDetailDto>();
			VehicleEmiDetailDto emiDetails = null;
			for (Object[] result : results) {
				emiDetails = new VehicleEmiDetailDto();
				
				emiDetails.setVehicleEmiDetailsId((Long) result[0]);
				emiDetails.setVid((Integer) result[1]);
				emiDetails.setLoanStartDate((Timestamp) result[2]);
				emiDetails.setLoanEndDate((Timestamp) result[3]);
				emiDetails.setMonthlyEmiAmount((Double) result[4]);
				emiDetails.setDownPaymentAmount((Double) result[5]);
				
				if(emiDetails.getLoanStartDate() != null)
					emiDetails.setLoanStartDateStr(DateTimeUtility.getDateFromTimeStamp(emiDetails.getLoanStartDate(), DateTimeUtility.DD_MM_YYYY));
				
				Dtos.add(emiDetails);
			}
		}
		return Dtos;

	}
	
	@Override
	public List<VehicleEmiDetails> getVehicleEMIForMonth(Timestamp startDate, Integer vid) throws Exception {

		TypedQuery<Object[]>	queryt = entityManager.createQuery(
					"SELECT R.vehicleEmiDetailsId, R.vid, R.loanStartDate, R.loanEndDate, R.monthlyEmiAmount "
							+ " FROM VehicleEmiDetails AS R "
							+ " INNER JOIN Vehicle V ON V.vid = R.vid "
							+ " where R.vid = "+vid+" AND R.loanEndDate >= '"+startDate+"' AND R.loanStartDate <= '"+startDate+"'"
								+ " AND R.markForDelete = 0",
					Object[].class);
		
		List<Object[]> results = queryt.getResultList();

		List<VehicleEmiDetails> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleEmiDetails>();
			VehicleEmiDetails emiDetails = null;
			for (Object[] result : results) {
				emiDetails = new VehicleEmiDetails();
				
				emiDetails.setVehicleEmiDetailsId((Long) result[0]);
				emiDetails.setVid((Integer) result[1]);
				emiDetails.setLoanStartDate((Timestamp) result[2]);
				emiDetails.setLoanEndDate((Timestamp) result[3]);
				emiDetails.setMonthlyEmiAmount((Double) result[4]);
				
				
				Dtos.add(emiDetails);
			}
		}
		return Dtos;

	}
	
	@Override
	public List<VehicleEmiPaymentDetailsDto> getVehicleEMIPaymentDetailsForMonth(Timestamp fromDate, Timestamp toDate, Integer vid) throws Exception {
	   try {
			
		TypedQuery<Object[]> queryt = null;
			queryt = entityManager.createQuery(
					" SELECT VEP.vehicleEmiPaymentDetailsId, VEP.vehicleEmiDetailsId, VED.loanAmount, VEP.monthlyEmiAmount, "
							+ " VED.downPaymentAmount, VEP.emiPaidDate, VEP.emiPaidAmount, VEP.emiPaidRemark, VEP.paymentTypeId,  "
							+ " VEP.vid, BA.name, BA.accountNumber, B.bankName, B.abbreviation, VEP.emiLoanDate  "
							+ " FROM VehicleEmiPaymentDetails AS VEP "
							+ " INNER JOIN VehicleEmiDetails VED ON VED.vehicleEmiDetailsId = VEP.vehicleEmiDetailsId "
							+ " INNER JOIN BankAccount BA ON BA.bankAccountId = VED.bankAccountId "
							+ " INNER JOIN BankMaster B ON B.bankId = BA.bankId"
							+ " INNER JOIN User U ON U.id = VED.createdById"
							+ " INNER JOIN User U ON U.id = VED.lastModifiedById"
							+ " WHERE VEP.emiPaidDate BETWEEN '"+fromDate+"' AND '"+toDate+"' AND VEP.vid = "+vid+" "
							+ " AND VEP.markForDelete = 0 ",
						Object[].class);
			
				List<Object[]> result = queryt.getResultList();

				List<VehicleEmiPaymentDetailsDto> Dtos = null;
				if (result != null && !result.isEmpty()) {
					Dtos = new ArrayList<VehicleEmiPaymentDetailsDto>();
					VehicleEmiPaymentDetailsDto vehicleEmiPaymentDetailDto = null;
					
						for (Object[] results : result) {
							vehicleEmiPaymentDetailDto = new VehicleEmiPaymentDetailsDto();
							
							vehicleEmiPaymentDetailDto.setVehicleEmiPaymentDetailsId((long) results[0]);
							vehicleEmiPaymentDetailDto.setVehicleEmiDetailsId((long) results[1]);
							vehicleEmiPaymentDetailDto.setLoanAmount((double) results[2]);
							vehicleEmiPaymentDetailDto.setMonthlyEmiAmount((double) results[3]);
							vehicleEmiPaymentDetailDto.setDownPaymentAmount((double) results[4]);
							vehicleEmiPaymentDetailDto.setEmiPaidDateStr(format.format((Date) results[5]));
							vehicleEmiPaymentDetailDto.setEmiPaidAmount((double) results[6]);
							vehicleEmiPaymentDetailDto.setEmiPaidRemark((String) results[7]);
							vehicleEmiPaymentDetailDto.setPaymentTypeName(VehicleEmiPaymentStatus.getPaymentModeStr((short) results[8]));
							vehicleEmiPaymentDetailDto.setVid((int) results[9]);
							vehicleEmiPaymentDetailDto.setBranchName((String) results[10]);
							vehicleEmiPaymentDetailDto.setAccountNumber((String) results[11]);
							vehicleEmiPaymentDetailDto.setBankName((String) results[12]);
							vehicleEmiPaymentDetailDto.setAbbreviation((String) results[13]);
							if(results[14] != null)
							vehicleEmiPaymentDetailDto.setEmiLoanDateStr(format.format((Date) results[14]));
							
							Dtos.add(vehicleEmiPaymentDetailDto);
						}
				}
					return Dtos;
			
		}catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public List<VehicleEmiPaymentDetailsDto> getGroupVehicleEMIForMonth(Timestamp startDate,Timestamp toDate, long vehicleGroupId)
			throws Exception {

		TypedQuery<Object[]>	queryt = entityManager.createQuery(
					"SELECT R.vehicleEmiDetailsId, V.vid, R.loanStartDate, R.loanEndDate, R.monthlyEmiAmount,R.lastModifiedOn, R.downPaymentAmount, VEP.emiPaidAmount "
							+ " FROM VehicleEmiDetails AS R "
							+ " INNER JOIN Vehicle V ON V.vid = R.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
							+ " LEFT JOIN VehicleEmiPaymentDetails VEP ON VEP.vehicleEmiDetailsId = R.vehicleEmiDetailsId AND VEP.emiPaidDate between '"+startDate+"' AND '"+toDate+"' "
							+ " where VG.gid = "+vehicleGroupId+" AND R.loanEndDate >= '"+startDate+"' AND R.loanStartDate <= '"+startDate+"'"
								+ " AND R.markForDelete = 0",
					Object[].class);
		
		List<Object[]> results = queryt.getResultList();

		List<VehicleEmiPaymentDetailsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleEmiPaymentDetailsDto>();
			VehicleEmiPaymentDetailsDto emiDetails = null;
			for (Object[] result : results) {
				emiDetails = new VehicleEmiPaymentDetailsDto();
				
				emiDetails.setVehicleEmiDetailsId((Long) result[0]);
				emiDetails.setVid((Integer) result[1]);
				emiDetails.setLoanStartDate((Timestamp) result[2]);
				emiDetails.setLoanEndDate((Timestamp) result[3]);
				emiDetails.setMonthlyEmiAmount((Double) result[4]);
				emiDetails.setLastModifiedOn((Timestamp) result[5]);
				boolean downpaymentDate = DateTimeUtility.checkDateExistsBetweenDates(DateTimeUtility.getPreviousEndOfDateFromSelectedDate(format1.format(startDate)),DateTimeUtility.getNextEndOfDateFromSelectedDate(format1.format(toDate)), emiDetails.getLoanStartDate());
				if(result[6] != null) {
					if(downpaymentDate) {
						emiDetails.setDownPaymentAmount((Double) result[6]);
					}else {
						emiDetails.setDownPaymentAmount(0.0);
					}
				}else {
					emiDetails.setDownPaymentAmount(0.0);
				}
				if(result[7] != null) {
					emiDetails.setEmiPaidAmount((Double) result[7]);
				}else {
					emiDetails.setEmiPaidAmount(0.0);
				}
				
				
				Dtos.add(emiDetails);
			}
		}
		return Dtos;

	}
	
	@Override
	public List<VehicleEmiPaymentDetailsDto> getGroupVehicleEMIPaymentDetailsForMonth(Timestamp fromDate, Timestamp toDate, long vehicleGroupId) throws Exception {
	   try {
			
		TypedQuery<Object[]> queryt = null;
			queryt = entityManager.createQuery(
					" SELECT VEP.vehicleEmiPaymentDetailsId, VEP.vehicleEmiDetailsId, VED.loanAmount, VEP.monthlyEmiAmount, "
							+ " VED.downPaymentAmount, VEP.emiPaidDate, VEP.emiPaidAmount, VEP.emiPaidRemark, VEP.paymentTypeId,  "
							+ " VEP.vid, BA.name, BA.accountNumber, B.bankName, B.abbreviation, VEP.emiLoanDate  "
							+ " FROM VehicleEmiPaymentDetails AS VEP "
							+ " INNER JOIN VehicleEmiDetails VED ON VED.vehicleEmiDetailsId = VEP.vehicleEmiDetailsId "
							+ " INNER JOIN BankAccount BA ON BA.bankAccountId = VED.bankAccountId "
							+ " INNER JOIN BankMaster B ON B.bankId = BA.bankId"
							+ " INNER JOIN User U ON U.id = VED.createdById"
							+ " INNER JOIN User U ON U.id = VED.lastModifiedById"
							+ " INNER JOIN Vehicle V ON V.vid = VEP.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
							+ " WHERE VEP.emiPaidDate BETWEEN '"+fromDate+"' AND '"+toDate+"' AND VG.gid = "+vehicleGroupId+" "
							+ " AND VEP.markForDelete = 0 ",
						Object[].class);
			
				List<Object[]> result = queryt.getResultList();

				List<VehicleEmiPaymentDetailsDto> Dtos = null;
				if (result != null && !result.isEmpty()) {
					Dtos = new ArrayList<VehicleEmiPaymentDetailsDto>();
					VehicleEmiPaymentDetailsDto vehicleEmiPaymentDetailDto = null;
					
						for (Object[] results : result) {
							vehicleEmiPaymentDetailDto = new VehicleEmiPaymentDetailsDto();
							
							vehicleEmiPaymentDetailDto.setVehicleEmiPaymentDetailsId((long) results[0]);
							vehicleEmiPaymentDetailDto.setVehicleEmiDetailsId((long) results[1]);
							vehicleEmiPaymentDetailDto.setLoanAmount((double) results[2]);
							vehicleEmiPaymentDetailDto.setMonthlyEmiAmount((double) results[3]);
							vehicleEmiPaymentDetailDto.setDownPaymentAmount((double) results[4]);
							vehicleEmiPaymentDetailDto.setEmiPaidDateStr(format.format((Date) results[5]));
							vehicleEmiPaymentDetailDto.setEmiPaidAmount((double) results[6]);
							vehicleEmiPaymentDetailDto.setEmiPaidRemark((String) results[7]);
							vehicleEmiPaymentDetailDto.setPaymentTypeName(VehicleEmiPaymentStatus.getPaymentModeStr((short) results[8]));
							vehicleEmiPaymentDetailDto.setVid((int) results[9]);
							vehicleEmiPaymentDetailDto.setBranchName((String) results[10]);
							vehicleEmiPaymentDetailDto.setAccountNumber((String) results[11]);
							vehicleEmiPaymentDetailDto.setBankName((String) results[12]);
							vehicleEmiPaymentDetailDto.setAbbreviation((String) results[13]);
							if(results[14] != null)
							vehicleEmiPaymentDetailDto.setEmiLoanDateStr(format.format((Date) results[14]));
							
							Dtos.add(vehicleEmiPaymentDetailDto);
						}
				}
					return Dtos;
			
		}catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleEmiPaymentDetailsDto> getVehicleEMIForDateRange(String	startDate, String toDate, Integer vid) throws Exception {
		TypedQuery<Object[]>	queryt = entityManager.createQuery(
					"SELECT R.vehicleEmiDetailsId, R.vid, R.loanStartDate, R.loanEndDate, R.monthlyEmiAmount, R.loanStartDate, R.downPaymentAmount, VEP.emiPaidAmount "
							+ " FROM VehicleEmiDetails AS R "
							+ " INNER JOIN Vehicle V ON V.vid = R.vid "
							+ " LEFT JOIN VehicleEmiPaymentDetails VEP ON VEP.vehicleEmiDetailsId = R.vehicleEmiDetailsId AND VEP.emiPaidDate between '"+startDate+"' AND '"+toDate+"' "
							+ " where (R.vid = "+vid+" AND  '" + startDate +"' between R.loanStartDate AND R.loanEndDate OR R.vid=" + vid + " "
							+ " AND '"+toDate+"' between R.loanStartDate AND R.loanEndDate ) AND R.markForDelete = 0",
					Object[].class);
		
		List<Object[]> results = queryt.getResultList();

		List<VehicleEmiPaymentDetailsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleEmiPaymentDetailsDto>();
			VehicleEmiPaymentDetailsDto emiDetails = null;
			for (Object[] result : results) {
				emiDetails = new VehicleEmiPaymentDetailsDto();
				
				emiDetails.setVehicleEmiDetailsId((Long) result[0]);
				emiDetails.setVid((Integer) result[1]);
				emiDetails.setLoanStartDate((Timestamp) result[2]);
				emiDetails.setLoanEndDate((Timestamp) result[3]);
				emiDetails.setMonthlyEmiAmount((Double) result[4]);
				emiDetails.setLoanStartDate((Timestamp) result[5]);
				boolean downpaymentDate = DateTimeUtility.checkDateExistsBetweenDates(DateTimeUtility.getPreviousEndOfDateFromSelectedDate(format1.format(format2.parse(startDate))),DateTimeUtility.getNextEndOfDateFromSelectedDate(format1.format(format2.parse(toDate))), emiDetails.getLoanStartDate());
				if(result[6] != null) {
					if(downpaymentDate) {
						emiDetails.setDownPaymentAmount((Double) result[6]);
					}else {
						emiDetails.setDownPaymentAmount(0.0);
					}
				}else {
					emiDetails.setDownPaymentAmount(0.0);
				}
				if(result[7] != null) {
					emiDetails.setEmiPaidAmount((Double) result[7]);
				}else {
					emiDetails.setEmiPaidAmount(0.0);
				}
				
				Dtos.add(emiDetails);
			}
		}
		return Dtos;

	}
	
	@Override
	public ValueObject getVehicleWiseEmiPaymentPendingList(ValueObject object) throws Exception {
		CustomUserDetails					userDetails						= null;
		VehicleEmiDetailDto					vehicleEmiDetail				= null;
		List<VehicleEmiDetailDto>			finalList						= null;
		List<VehicleEmiDetailDto>			emiLoanDateList					= null;
		List<VehicleEmiPaymentDetailsDto>	paymentList						= null;
		List<String>						paymentFinalList				= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicleEmiDetail = vehicleEmiDetailsDao.getVehicleEmiDetailsById(object.getLong("vehEmiDetailsId"),
					userDetails.getCompany_id());
			
			if(vehicleEmiDetail != null) {
				
				emiLoanDateList = new ArrayList<VehicleEmiDetailDto>();
				for(int j = 0; j< vehicleEmiDetail.getTenure(); j++) {
					
					VehicleEmiDetailDto vehicleEmiDate = (VehicleEmiDetailDto) vehicleEmiDetail.clone();
					vehicleEmiDate.setLoanEmiDate(DateTimeUtility.getNextDateByMonth(vehicleEmiDate.getLoanStartDate(), j));
					
					emiLoanDateList.add(vehicleEmiDate);
				}
				
				finalList 		= new ArrayList<VehicleEmiDetailDto>();
				
				if(vehicleEmiDetail.getEmiStatus() == VehicleEmiPaymentStatus.EMI_STATUS_IN_PROCESS) {
					
					paymentList = vehicleEmiDetailsDao.getVehicleEmiPaymentPaidList(vehicleEmiDetail.getVehicleEmiDetailsId(),userDetails.getCompany_id());
					
					paymentFinalList = new ArrayList<String>();
					if(paymentList != null) {
						for(VehicleEmiPaymentDetailsDto paid : paymentList) {
							paymentFinalList.add(paid.getEmiLoanDateStr());
						}
					}
					
					for(VehicleEmiDetailDto emiDetailsList : emiLoanDateList) {
						
						if(paymentList == null) {
							finalList.add(emiDetailsList);
						} else if (!paymentFinalList.contains(emiDetailsList.getLoanEmiDate())) {
							finalList.add(emiDetailsList);
						}
						
					}
					
				}
				object.put("finalList", finalList);
			}
			
			object.put("paymentTypeList", VehicleEmiPaymentStatus.getPaymentTypeList());
			
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails	= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject saveVehicleEmiPaymentDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails								userDetails							= null;
		ArrayList<ValueObject> 							dataArrayObjColl 					= null;
		VehicleEmiPaymentDetails						vehicleEmiPaymentDetails			= null;
		VehicleEmiDetailDto								vehicleEmiDetail					= null;
		long											emiPaidCount						= 0;
		HashMap<Long, VehicleProfitAndLossTxnChecker>	emiPaymentTxnCheckerHashMap			= null;
		HashMap<Long, VehicleEmiPaymentDetails> 		emiPaymentHM				    	= null;
		ValueObject										ownerShipObject						= null;
		HashMap<Long, ValueObject> 						ownerShipObjectHM					= null;
		try {
			userDetails				 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			emiPaymentTxnCheckerHashMap = new HashMap<>();
			emiPaymentHM			 	= new HashMap<>();
			
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("emiPaymentDetails");
			
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
				ownerShipObjectHM	= new HashMap<Long, ValueObject>();
				
				for (ValueObject object : dataArrayObjColl) {
					
					vehicleEmiPaymentDetails = VehicleEmiBL.setVehicleEmiPayment(object,valueObject);
					vehicleEmiPaymentDetailsRepository.save(vehicleEmiPaymentDetails);
					
					ValueObject		profitLossobject	= new ValueObject();
					
					profitLossobject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
					profitLossobject.put(VehicleProfitAndLossDto.TRANSACTION_ID, vehicleEmiPaymentDetails.getVehicleEmiDetailsId());
					profitLossobject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					profitLossobject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
					profitLossobject.put(VehicleProfitAndLossDto.TRANSACTION_VID, vehicleEmiPaymentDetails.getVid());
					profitLossobject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, vehicleEmiPaymentDetails.getVehicleEmiPaymentDetailsId());
					
					VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(profitLossobject);
					vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
					
					
					emiPaymentTxnCheckerHashMap.put(profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossTxnChecker);
					emiPaymentHM.put(vehicleEmiPaymentDetails.getVehicleEmiPaymentDetailsId(), vehicleEmiPaymentDetails);
					
					VehicleDto	vehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(vehicleEmiPaymentDetails.getVid(), vehicleEmiPaymentDetails.getCompanyId());
					if(vehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED && vehicleEmiPaymentDetails.getEmiPaidAmount() != null && vehicleEmiPaymentDetails.getEmiPaidAmount() > 0){
						
						ownerShipObject	= new ValueObject();
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, vehicleEmiPaymentDetails.getVehicleEmiPaymentDetailsId());
						ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, vehicleEmiPaymentDetails.getVid());
						ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, vehicleEmiPaymentDetails.getEmiPaidAmount());
						ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
						ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_VEHICLE_EMI);
						ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, vehicleEmiPaymentDetails.getCompanyId());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, vehicleEmiPaymentDetails.getEmiPaidDate());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Vehicle EMI Entry");
						ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Vehicle EMI For Vehicle ");
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
						ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, vehicleEmiPaymentDetails.getCreatedById());
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -vehicleEmiPaymentDetails.getEmiPaidAmount());
						
						VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
						vehicleAgentTxnCheckerService.save(agentTxnChecker);
						
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
						
						ownerShipObjectHM.put(agentTxnChecker.getVehicleAgentTxnCheckerId(), ownerShipObject);
						
					}
					
				}
				
				vehicleEmiDetail = vehicleEmiDetailsDao.getVehicleEmiDetailsById(vehicleEmiPaymentDetails.getVehicleEmiDetailsId(),
						userDetails.getCompany_id());
				
				emiPaidCount = vehicleEmiPaymentDetailsRepository.getVehicleEmiPaymentCount(vehicleEmiPaymentDetails.getVehicleEmiDetailsId(), 
						userDetails.getCompany_id());
				
				if(vehicleEmiDetail.getTenure() == emiPaidCount) {
					vehicleEmiDetailsRepository.updateEmiStatus(VehicleEmiPaymentStatus.EMI_STATUS_COMPLETE, 
							vehicleEmiPaymentDetails.getVehicleEmiDetailsId(), 	userDetails.getCompany_id());
				}
				
				valueObject.put("emiPaidSuccessfully", true);
			}
			
			if(emiPaymentHM != null && emiPaymentHM.size() > 0) {

				ValueObject	valueOutObject	= new ValueObject();
				valueOutObject.put("userDetails", userDetails);
				valueOutObject.put("emiPaymentHM", emiPaymentHM);
				valueOutObject.put("emiPaymentTxnCheckerHashMap", emiPaymentTxnCheckerHashMap);

				vehicleProfitAndLossService.runThreadForEMIPaymentDetails(valueOutObject);
			}
			if(ownerShipObjectHM != null && ownerShipObjectHM.size() > 0) {
				vehicleAgentIncomeExpenseDetailsService.processVehicleAgentIncomeExpense(ownerShipObjectHM);
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			ownerShipObject						= null;
			userDetails							= null;
			dataArrayObjColl 					= null;
			vehicleEmiPaymentDetails			= null;
			vehicleEmiDetail					= null;
			emiPaidCount						= 0;
			emiPaymentTxnCheckerHashMap			= null;
			emiPaymentHM				    	= null;
			ownerShipObjectHM					= null;
		}
	}
	
	@Override
	public ValueObject getVehicleWiseEmiPaidList(ValueObject object) throws Exception {
		CustomUserDetails					userDetails						= null;
		List<VehicleEmiPaymentDetailsDto>	vehicleEmiPaymentDetailList		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicleEmiPaymentDetailList = vehicleEmiDetailsDao.getVehicleEmiPaymentPaidList(object.getLong("vehEmiDetailsId"), userDetails.getCompany_id());
			if(vehicleEmiPaymentDetailList != null)
			object.put("vehicleEmiPaymentDetailList", vehicleEmiPaymentDetailList);
			
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails	= null;
		}
	}
	
	@Override
	public ValueObject getPreEmiSettlementDetail(ValueObject object) throws Exception {
		CustomUserDetails			userDetails				= null;
		VehicleEmiDetailDto			vehicleEmiDetail		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicleEmiDetail = vehicleEmiDetailsDao.getVehicleEmiDetailsById(object.getLong("vehEmiDetailsId"),
					userDetails.getCompany_id());
			
			if(vehicleEmiDetail != null) 
				object.put("vehicleEmiDetail", vehicleEmiDetail);
			
				object.put("paymentTypeList", VehicleEmiPaymentStatus.getPaymentTypeList());
			
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails	= null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject savePreEmiSettlementDetails(ValueObject object) throws Exception {
		CustomUserDetails			userDetails					= null;
		VehicleEmiPaymentDetails	vehicleEmiPaymentDetails	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicleEmiPaymentDetails = VehicleEmiBL.setPreEMISettlementPayment(object);
			vehicleEmiPaymentDetailsRepository.save(vehicleEmiPaymentDetails);
			
			ValueObject		profitLossObject	= new ValueObject();
			
			profitLossObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
			profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, vehicleEmiPaymentDetails.getVehicleEmiDetailsId());
			profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
			profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
			profitLossObject.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
			profitLossObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, vehicleEmiPaymentDetails.getVid());
			profitLossObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, vehicleEmiPaymentDetails.getVehicleEmiPaymentDetailsId());
			
			VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(profitLossObject);
			vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
			
			object.put("success", "Data Saved Successfully !");
			
			vehicleEmiDetailsRepository.updateEmiStatus(VehicleEmiPaymentStatus.EMI_STATUS_SETTLEMENT, 
					vehicleEmiPaymentDetails.getVehicleEmiDetailsId(), 	userDetails.getCompany_id());
			
			if(profitAndLossTxnChecker != null) {
				
				ValueObject		valueInObject	= new ValueObject();
				valueInObject.put("vehicleEmiPaymentDetails", vehicleEmiPaymentDetails);
				valueInObject.put("userDetails", userDetails);
				valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
				valueInObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
				valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				
				new Thread() {
					public void run() {
						try {
							vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueInObject);
							vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueInObject);
							vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueInObject);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}		
				}.start();
			}
			
			return object;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleEmiPaymentDetails		= null;
		}
	}
	
}
