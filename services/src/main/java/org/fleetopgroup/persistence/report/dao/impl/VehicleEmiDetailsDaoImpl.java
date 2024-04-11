package org.fleetopgroup.persistence.report.dao.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.EmiTenureType;
import org.fleetopgroup.constant.VehicleEmiPaymentStatus;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleEmiDetailDto;
import org.fleetopgroup.persistence.dto.VehicleEmiPaymentDetailsDto;
import org.fleetopgroup.persistence.report.dao.VehicleEmiDetailsDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleEmiDetailsDaoImpl implements VehicleEmiDetailsDao {
	
	@PersistenceContext
	EntityManager entityManager;
	
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	SimpleDateFormat format 		 = new SimpleDateFormat("dd-MM-yyyy");
	DecimalFormat  toFixedTwo = new DecimalFormat("#.##");
	
	@Override
	public VehicleEmiDetailDto getVehicleEmiDetails(Integer vid, Integer companyId) throws Exception {
		try {

			Query query = entityManager
					.createQuery(" SELECT VED.vehicleEmiDetailsId, VED.bankAccountId, VED.loanAmount, VED.monthlyEmiAmount, VED.tenure,"
							+ "VED.tenureType, VED.loanStartDate, VED.loanEndDate, VED.interestRate, VED.vid, BA.name, BA.accountNumber, B.bankName,"
							+ " B.abbreviation, VED.createdOn, VED.lastModifiedOn, VED.remark "
							+ " FROM VehicleEmiDetails AS VED "
							+ " INNER JOIN BankAccount BA ON BA.bankAccountId = VED.bankAccountId "
							+ " INNER JOIN BankMaster B ON B.bankId = BA.bankId"
							+ " INNER JOIN User U ON U.id = VED.createdById"
							+ " INNER JOIN User U ON U.id = VED.lastModifiedById"
							+ " WHERE VED.vid = "+vid+" AND VED.companyId = "+companyId+" AND VED.markForDelete = 0");
			
			Object[] results = null;
			try {
				results = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			VehicleEmiDetailDto 	vehicleEmiDetailDto = null;
			if (results != null) {
				vehicleEmiDetailDto = new VehicleEmiDetailDto();

				vehicleEmiDetailDto.setVehicleEmiDetailsId((Long) results[0]);
				vehicleEmiDetailDto.setBankAccountId((Long) results[1]);
				vehicleEmiDetailDto.setLoanAmount((Double) results[2]);
				vehicleEmiDetailDto.setMonthlyEmiAmount((Double) results[3]);
				vehicleEmiDetailDto.setTenure((Integer) results[4]);
				vehicleEmiDetailDto.setTenureType((Short) results[5]);
				vehicleEmiDetailDto.setLoanStartDate((Timestamp) results[6]);
				vehicleEmiDetailDto.setLoanEndDate((Timestamp) results[7]);
				vehicleEmiDetailDto.setInterestRate((Double) results[8]);
				vehicleEmiDetailDto.setVid((Integer) results[9]);
				vehicleEmiDetailDto.setBranchName((String) results[10]);
				vehicleEmiDetailDto.setAccountNumber((String) results[11]);
				vehicleEmiDetailDto.setBankName((String) results[12]);
				vehicleEmiDetailDto.setAbbreviation((String) results[13]);
				vehicleEmiDetailDto.setCreatedOn((Timestamp) results[14]);
				vehicleEmiDetailDto.setLastModifiedOn((Timestamp) results[15]);
				vehicleEmiDetailDto.setRemark((String) results[16]);


				}
			return vehicleEmiDetailDto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public VehicleEmiDetailDto getVehicleEmiDetailsById(long vehicleEmiDetailsId, Integer companyId) throws Exception {
		try {

			Query query = entityManager
					.createQuery(" SELECT VED.vehicleEmiDetailsId, VED.bankAccountId, VED.loanAmount, VED.monthlyEmiAmount, VED.tenure,"
							+ "VED.tenureType, VED.loanStartDate, VED.loanEndDate, VED.interestRate, VED.vid, BA.name, BA.accountNumber, B.bankName,"
							+ " B.abbreviation, VED.createdOn, VED.lastModifiedOn, VED.remark, VED.emiStatus, VED.downPaymentAmount "
							+ " FROM VehicleEmiDetails AS VED "
							+ " INNER JOIN BankAccount BA ON BA.bankAccountId = VED.bankAccountId "
							+ " INNER JOIN BankMaster B ON B.bankId = BA.bankId"
							+ " INNER JOIN User U ON U.id = VED.createdById"
							+ " INNER JOIN User U ON U.id = VED.lastModifiedById"
							+ " WHERE VED.vehicleEmiDetailsId = "+vehicleEmiDetailsId+" AND VED.companyId = "+companyId+" AND VED.markForDelete = 0");
			
			Object[] results = null;
			try {
				results = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			VehicleEmiDetailDto 	vehicleEmiDetailDto = null;
			if (results != null) {
				vehicleEmiDetailDto = new VehicleEmiDetailDto();

				vehicleEmiDetailDto.setVehicleEmiDetailsId((Long) results[0]);
				vehicleEmiDetailDto.setBankAccountId((Long) results[1]);
				if(results[2] != null)
				vehicleEmiDetailDto.setLoanAmount(Double.parseDouble(toFixedTwo.format(results[2])));
				if(results[3] != null)
				vehicleEmiDetailDto.setMonthlyEmiAmount(Double.parseDouble(toFixedTwo.format(results[3])));
				vehicleEmiDetailDto.setTenure((Integer) results[4]);
				vehicleEmiDetailDto.setTenureType((Short) results[5]);
				vehicleEmiDetailDto.setLoanStartDate((Timestamp) results[6]);
				vehicleEmiDetailDto.setLoanEndDate((Timestamp) results[7]);
				if(results[8] != null)
				vehicleEmiDetailDto.setInterestRate(Double.parseDouble(toFixedTwo.format(results[8])));
				vehicleEmiDetailDto.setVid((Integer) results[9]);
				vehicleEmiDetailDto.setBranchName((String) results[10]);
				vehicleEmiDetailDto.setAccountNumber((String) results[11]);
				vehicleEmiDetailDto.setBankName((String) results[12]);
				vehicleEmiDetailDto.setAbbreviation((String) results[13]);
				vehicleEmiDetailDto.setCreatedOn((Timestamp) results[14]);
				vehicleEmiDetailDto.setLastModifiedOn((Timestamp) results[15]);
				vehicleEmiDetailDto.setRemark((String) results[16]);
				if(results[17] != null) {
				vehicleEmiDetailDto.setEmiStatus((short) results[17]);
				}
				if(results[18] != null) {
				vehicleEmiDetailDto.setDownPaymentAmount(Double.parseDouble(toFixedTwo.format(results[18])));
				}
				
				vehicleEmiDetailDto.setCreatedOnStr(CreatedDateTime.format(vehicleEmiDetailDto.getCreatedOn()));
				vehicleEmiDetailDto.setLastModifiedOnStr(CreatedDateTime.format(vehicleEmiDetailDto.getLastModifiedOn()));
				vehicleEmiDetailDto.setTenureTypeStr(EmiTenureType.getName(vehicleEmiDetailDto.getTenureType()));
				
				if(vehicleEmiDetailDto.getLoanStartDate() != null)
					vehicleEmiDetailDto.setLoanStartDateStr(DateTimeUtility.getDateFromTimeStamp(vehicleEmiDetailDto.getLoanStartDate(), DateTimeUtility.DD_MM_YYYY));
				if(vehicleEmiDetailDto.getLoanEndDate() != null)
					vehicleEmiDetailDto.setLoanEndDateStr(DateTimeUtility.getDateFromTimeStamp(vehicleEmiDetailDto.getLoanEndDate(), DateTimeUtility.DD_MM_YYYY));
				
				}
			return vehicleEmiDetailDto;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleEmiDetailDto> getVehicleEmiDetailsList(Integer vid, Integer companyId) throws Exception {
	   try {
			
		TypedQuery<Object[]> queryt = null;
			queryt = entityManager.createQuery(
					" SELECT VED.vehicleEmiDetailsId, VED.bankAccountId, VED.loanAmount, VED.monthlyEmiAmount, VED.tenure,"
							+ "VED.tenureType, VED.loanStartDate, VED.loanEndDate, VED.interestRate, VED.vid, BA.name, BA.accountNumber, B.bankName,"
							+ " B.abbreviation, VED.createdOn, VED.lastModifiedOn, VED.remark, VED.downPaymentAmount, VED.interestRate, VED.emiStatus "
							+ " FROM VehicleEmiDetails AS VED "
							+ " INNER JOIN BankAccount BA ON BA.bankAccountId = VED.bankAccountId "
							+ " INNER JOIN BankMaster B ON B.bankId = BA.bankId"
							+ " INNER JOIN User U ON U.id = VED.createdById"
							+ " INNER JOIN User U ON U.id = VED.lastModifiedById"
							+ " WHERE VED.vid = "+vid+" AND VED.companyId = "+companyId+" AND VED.markForDelete = 0 ",
						Object[].class);
			
				List<Object[]> result = queryt.getResultList();

				List<VehicleEmiDetailDto> Dtos = null;
				if (result != null && !result.isEmpty()) {
					Dtos = new ArrayList<VehicleEmiDetailDto>();
					VehicleEmiDetailDto vehicleEmiDetailDto = null;
					
						for (Object[] results : result) {
							vehicleEmiDetailDto = new VehicleEmiDetailDto();
					
							vehicleEmiDetailDto.setVehicleEmiDetailsId((Long) results[0]);
							vehicleEmiDetailDto.setBankAccountId((Long) results[1]);
							if(results[2] != null)
							vehicleEmiDetailDto.setLoanAmount(Double.parseDouble(toFixedTwo.format(results[2])));
							if(results[3] != null)
							vehicleEmiDetailDto.setMonthlyEmiAmount(Double.parseDouble(toFixedTwo.format(results[3])));
							vehicleEmiDetailDto.setTenure((Integer) results[4]);
							vehicleEmiDetailDto.setTenureType((Short) results[5]);
							vehicleEmiDetailDto.setLoanStartDate((Timestamp) results[6]);
							vehicleEmiDetailDto.setLoanEndDate((Timestamp) results[7]);
							if(results[8] != null)
							vehicleEmiDetailDto.setInterestRate(Double.parseDouble(toFixedTwo.format(results[8])));
							vehicleEmiDetailDto.setVid((Integer) results[9]);
							vehicleEmiDetailDto.setBranchName((String) results[10]);
							vehicleEmiDetailDto.setAccountNumber((String) results[11]);
							vehicleEmiDetailDto.setBankName((String) results[12]);
							vehicleEmiDetailDto.setAbbreviation((String) results[13]);
							vehicleEmiDetailDto.setCreatedOn((Timestamp) results[14]);
							vehicleEmiDetailDto.setLastModifiedOn((Timestamp) results[15]);
							vehicleEmiDetailDto.setRemark((String) results[16]);
							if(results[17] != null)
							vehicleEmiDetailDto.setDownPaymentAmount(Double.parseDouble(toFixedTwo.format(results[17])));
							vehicleEmiDetailDto.setCreatedOnStr(CreatedDateTime.format(vehicleEmiDetailDto.getCreatedOn()));
							vehicleEmiDetailDto.setLastModifiedOnStr(CreatedDateTime.format(vehicleEmiDetailDto.getLastModifiedOn()));
							vehicleEmiDetailDto.setTenureTypeStr(EmiTenureType.getName(vehicleEmiDetailDto.getTenureType()));
							if(results[18] != null)
							vehicleEmiDetailDto.setInterestRate(Double.parseDouble(toFixedTwo.format(results[18])));
							if(vehicleEmiDetailDto.getLoanStartDate() != null)
								vehicleEmiDetailDto.setLoanStartDateStr(DateTimeUtility.getDateFromTimeStamp(vehicleEmiDetailDto.getLoanStartDate(), DateTimeUtility.DD_MM_YYYY));
							if(vehicleEmiDetailDto.getLoanEndDate() != null)
								vehicleEmiDetailDto.setLoanEndDateStr(DateTimeUtility.getDateFromTimeStamp(vehicleEmiDetailDto.getLoanEndDate(), DateTimeUtility.DD_MM_YYYY));
							if(results[19] != null)
							vehicleEmiDetailDto.setEmiStatus((short) results[19]);
							Dtos.add(vehicleEmiDetailDto);
						}
				}
					return Dtos;
			
		}catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public List<VehicleEmiPaymentDetailsDto> getVehicleEmiPaymentPaidList(long vehEmiDetailsId, Integer companyId) throws Exception {
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
							+ " WHERE VEP.vehicleEmiDetailsId = "+vehEmiDetailsId+" AND VEP.companyId = "+companyId+" "
							+ " AND VEP.markForDelete = 0 ORDER BY VEP.vehicleEmiPaymentDetailsId ASC ",
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
							vehicleEmiPaymentDetailDto.setLoanEmiDate(format.format((Date) results[14]));// this is used for report
							vehicleEmiPaymentDetailDto.setPaymentStatus("PAID");
							Dtos.add(vehicleEmiPaymentDetailDto);
						}
				}
					return Dtos;
			
		}catch (Exception e) {
			throw e;
		}
	}
	@Override
	public List<VehicleEmiDetailDto>  getBankWiseVehicleEmiDetailsReport(String query) throws Exception {
		try {
			CustomUserDetails userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = entityManager
					.createQuery(" SELECT VED.vehicleEmiDetailsId, VED.bankAccountId, VED.loanAmount, VED.monthlyEmiAmount, VED.tenure,"
							+ "VED.tenureType, VED.loanStartDate, VED.loanEndDate, VED.interestRate, VED.vid, BA.name, BA.accountNumber, B.bankName,"
							+ " B.abbreviation, VED.createdOn, VED.lastModifiedOn, VED.remark, V.vehicle_registration, VED.downPaymentAmount "
							+ " FROM VehicleEmiDetails AS VED "
							+ " INNER JOIN BankAccount BA ON BA.bankAccountId = VED.bankAccountId "
							+ " INNER JOIN BankMaster B ON B.bankId = BA.bankId "
							+ " INNER JOIN Vehicle V ON V.vid = VED.vid " 
							+ " WHERE VED.companyId = "+userDetails.getCompany_id()+" "+query+" AND VED.markForDelete = 0",
							Object[].class);
			
			List<Object[]> result = queryt.getResultList();

			List<VehicleEmiDetailDto> Dtos = null;
			if (result != null && !result.isEmpty()) {
				Dtos = new ArrayList<VehicleEmiDetailDto>();
				VehicleEmiDetailDto vehicleEmiDetailDto = null;
				
					for (Object[] results : result) {
						vehicleEmiDetailDto = new VehicleEmiDetailDto();
						
						vehicleEmiDetailDto.setVehicleEmiDetailsId((Long) results[0]);
						vehicleEmiDetailDto.setBankAccountId((Long) results[1]);
						vehicleEmiDetailDto.setLoanAmount((Double) results[2]);
						vehicleEmiDetailDto.setMonthlyEmiAmount((Double) results[3]);
						vehicleEmiDetailDto.setTenure((Integer) results[4]);
						vehicleEmiDetailDto.setTenureType((Short) results[5]);
						vehicleEmiDetailDto.setTenureTypeStr(VehicleEmiPaymentStatus.getEmiStatus((Short) results[5]));
						vehicleEmiDetailDto.setLoanStartDateStr(format.format((Timestamp) results[6]));
						vehicleEmiDetailDto.setLoanEndDateStr(format.format((Timestamp) results[7]));
						vehicleEmiDetailDto.setInterestRate((Double) results[8]);
						vehicleEmiDetailDto.setVid((Integer) results[9]);
						vehicleEmiDetailDto.setBranchName((String) results[10]);
						vehicleEmiDetailDto.setAccountNumber((String) results[11]);
						vehicleEmiDetailDto.setBankName((String) results[12]);
						vehicleEmiDetailDto.setAbbreviation((String) results[13]);
						vehicleEmiDetailDto.setCreatedOn((Timestamp) results[14]);
						vehicleEmiDetailDto.setLastModifiedOn((Timestamp) results[15]);
						vehicleEmiDetailDto.setRemark((String) results[16]);
						vehicleEmiDetailDto.setVehicle_registration((String) results[17]);
						vehicleEmiDetailDto.setDownPaymentAmount((Double) results[18]);
						
						Dtos.add(vehicleEmiDetailDto);
					}
			}
				return Dtos;
		
	}catch (Exception e) {
		throw e;
	}
	}
	
}
