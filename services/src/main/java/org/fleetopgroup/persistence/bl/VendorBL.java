package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.VendorGSTRegistered;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.dto.VendorFixedFuelPriceDto;
import org.fleetopgroup.persistence.dto.VendorFixedPartPriceDto;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorFixedFuelPrice;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class VendorBL {
	
	public static final int	FUEL_TYPE_ID_STA	= 21;

	public VendorBL() {
	}
	
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	SimpleDateFormat dateFormatName = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	// get Save Vendor
	public Vendor prepareVendor(VendorDto vendorBean) throws Exception {
		Vendor vendor = null;
		CustomUserDetails	userDetails	= null;
		Date currentDateUpdate = null;
		Timestamp toDate = null;
		
		try {
			
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			currentDateUpdate 	= new Date();
			toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			vendor				= new Vendor();
			
			vendor.setVendorId(vendorBean.getVendorId());
			vendor.setVendorNumber(vendorBean.getVendorNumber());
			vendor.setVendorTypeId(vendorBean.getVendorTypeId());
			vendor.setVendorName(vendorBean.getVendorName());
			vendor.setVendorTypeId(vendorBean.getVendorTypeId());
			vendor.setVendorLocation(vendorBean.getVendorLocation());
			vendor.setVendorPhone(vendorBean.getVendorPhone());
			vendor.setVendorWebsite(vendorBean.getVendorWebsite());
			vendor.setVendorAddress1(vendorBean.getVendorAddress1());
			vendor.setVendorAddress2(vendorBean.getVendorAddress2());
			vendor.setVendorCity(vendorBean.getVendorCity());
			vendor.setVendorState(vendorBean.getVendorState());
			vendor.setVendorCountry(vendorBean.getVendorCountry());
			vendor.setVendorPincode(vendorBean.getVendorPincode());
			vendor.setVendorRemarks(vendorBean.getVendorRemarks());
			vendor.setVendorTermId(vendorBean.getVendorTermId());
			vendor.setVendorPanNO(vendorBean.getVendorPanNO());
			vendor.setVendorGSTNO(vendorBean.getVendorGSTNO());
			vendor.setVendorGSTRegisteredId(vendorBean.getVendorGSTRegisteredId());
			vendor.setVendorCreditLimit(vendorBean.getVendorCreditLimit());
			vendor.setVendorAdvancePaid(vendorBean.getVendorAdvancePaid());
			vendor.setVendorFirName(vendorBean.getVendorFirName());
			vendor.setVendorFirPhone(vendorBean.getVendorFirPhone());
			vendor.setVendorFirEmail(vendorBean.getVendorFirEmail());
			vendor.setVendorSecName(vendorBean.getVendorSecName());
			vendor.setVendorSecPhone(vendorBean.getVendorSecPhone());
			vendor.setVendorSecEmail(vendorBean.getVendorSecEmail());
			vendor.setVendorBankName(vendorBean.getVendorBankName());
			vendor.setVendorBankBranch(vendorBean.getVendorBankBranch());
			vendor.setVendorBankAccno(vendorBean.getVendorBankAccno());
			vendor.setVendorBankIfsc(vendorBean.getVendorBankIfsc());
			vendor.setCreatedById(userDetails.getId());
			vendor.setLastModifiedById(userDetails.getId());
			vendor.setCreated(toDate);
			vendor.setLastupdated(toDate);
			vendor.setMarkForDelete(false);
			vendor.setCompanyId(userDetails.getCompany_id());
			vendor.setOwnPetrolPump(vendorBean.getOwnPetrolPump());
			vendor.setVendorTDSPercent(vendorBean.getVendorTDSPercent());
			return vendor;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Vendor prepareVendorForEdit(VendorDto vendorBean, String vendorName) {

		Vendor vendor = new Vendor();

		vendor.setVendorId(vendorBean.getVendorId());
		vendor.setVendorNumber(vendorBean.getVendorNumber());
		vendor.setVendorTypeId(vendorBean.getVendorTypeId());

		vendor.setVendorName(vendorName);
		vendor.setVendorLocation(vendorBean.getVendorLocation());
	//	vendor.setVendorType(vendorBean.getVendorType());
		vendor.setVendorPhone(vendorBean.getVendorPhone());
		vendor.setVendorWebsite(vendorBean.getVendorWebsite());
		vendor.setVendorAddress1(vendorBean.getVendorAddress1());
		vendor.setVendorAddress2(vendorBean.getVendorAddress2());
		vendor.setVendorCity(vendorBean.getVendorCity());
		vendor.setVendorState(vendorBean.getVendorState());
		vendor.setVendorCountry(vendorBean.getVendorCountry());
		vendor.setVendorPincode(vendorBean.getVendorPincode());
		vendor.setVendorRemarks(vendorBean.getVendorRemarks());
		//vendor.setVendorTerm(vendorBean.getVendorTerm());
		vendor.setVendorTermId(vendorBean.getVendorTermId());
		vendor.setVendorPanNO(vendorBean.getVendorPanNO());
		vendor.setVendorGSTNO(vendorBean.getVendorGSTNO());
		//vendor.setVendorGSTRegistered(vendorBean.getVendorGSTRegistered());
		vendor.setVendorGSTRegisteredId(vendorBean.getVendorGSTRegisteredId());
		vendor.setVendorCreditLimit(vendorBean.getVendorCreditLimit());
		vendor.setVendorAdvancePaid(vendorBean.getVendorAdvancePaid());

		vendor.setVendorFirName(vendorBean.getVendorFirName());
		vendor.setVendorFirPhone(vendorBean.getVendorFirPhone());
		vendor.setVendorFirEmail(vendorBean.getVendorFirEmail());
		vendor.setVendorSecName(vendorBean.getVendorSecName());
		vendor.setVendorSecPhone(vendorBean.getVendorSecPhone());
		vendor.setVendorSecEmail(vendorBean.getVendorSecEmail());

		vendor.setVendorBankName(vendorBean.getVendorBankName());
		vendor.setVendorBankBranch(vendorBean.getVendorBankBranch());
		vendor.setVendorBankAccno(vendorBean.getVendorBankAccno());
		vendor.setVendorBankIfsc(vendorBean.getVendorBankIfsc());
		vendor.setOwnPetrolPump(vendorBean.getOwnPetrolPump());

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


		//vendor.setCreatedBy(vendorBean.getCreatedBy());
		//vendor.setLastModifiedBy(userDetails.getEmail());
		vendor.setCreatedById(vendorBean.getCreatedById());
		vendor.setLastModifiedById(userDetails.getId());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		vendor.setCreated(toDate);
		vendor.setLastupdated(toDate);
		vendor.setMarkForDelete(false);
		vendor.setCompanyId(userDetails.getCompany_id());

		vendor.setVendorTDSPercent(vendorBean.getVendorTDSPercent());
		
		return vendor;
	}

	// get Save Vendor
	public VendorDto prepareVendorBean(Vendor vendorBean) {

		VendorDto vendor = new VendorDto();

		vendor.setVendorId(vendorBean.getVendorId());

		vendor.setVendorName(vendorBean.getVendorName());
		vendor.setVendorLocation(vendorBean.getVendorLocation());
		//vendor.setVendorType(vendorBean.getVendorType());
		vendor.setVendorPhone(vendorBean.getVendorPhone());
		vendor.setVendorWebsite(vendorBean.getVendorWebsite());
		vendor.setVendorAddress1(vendorBean.getVendorAddress1());
		vendor.setVendorAddress2(vendorBean.getVendorAddress2());
		vendor.setVendorCity(vendorBean.getVendorCity());
		vendor.setVendorState(vendorBean.getVendorState());
		vendor.setVendorCountry(vendorBean.getVendorCountry());
		vendor.setVendorPincode(vendorBean.getVendorPincode());
		vendor.setVendorRemarks(vendorBean.getVendorRemarks());
		//vendor.setVendorTerm(vendorBean.getVendorTerm());
		vendor.setVendorPanNO(vendorBean.getVendorPanNO());
		vendor.setVendorGSTNO(vendorBean.getVendorGSTNO());
		//vendor.setVendorGSTRegistered(vendorBean.getVendorGSTRegistered());
		vendor.setVendorCreditLimit(vendorBean.getVendorCreditLimit());
		vendor.setVendorAdvancePaid(vendorBean.getVendorAdvancePaid());

		vendor.setVendorFirName(vendorBean.getVendorFirName());
		vendor.setVendorFirPhone(vendorBean.getVendorFirPhone());
		vendor.setVendorFirEmail(vendorBean.getVendorFirEmail());
		vendor.setVendorSecName(vendorBean.getVendorSecName());
		vendor.setVendorSecPhone(vendorBean.getVendorSecPhone());
		vendor.setVendorSecEmail(vendorBean.getVendorSecEmail());

		vendor.setVendorBankName(vendorBean.getVendorBankName());
		vendor.setVendorBankBranch(vendorBean.getVendorBankBranch());
		vendor.setVendorBankAccno(vendorBean.getVendorBankAccno());
		vendor.setVendorBankIfsc(vendorBean.getVendorBankIfsc());

		return vendor;
	}

	/*public List<VendorDto> prepareListofVendorBean(List<Vendor> vendorList) {
		List<VendorDto> beans = null;
		if (vendorList != null && !vendorList.isEmpty()) {
			beans = new ArrayList<VendorDto>();
			VendorDto vendor = null;
			for (Vendor vendorBean : vendorList) {
				vendor = new VendorDto();
				vendor.setVendorId(vendorBean.getVendorId());
				vendor.setVendorNumber(vendorBean.getVendorNumber());

				vendor.setVendorName(vendorBean.getVendorName());
				vendor.setVendorLocation(vendorBean.getVendorLocation());
				vendor.setVendorType(vendorBean.getVendorType());
				vendor.setVendorPhone(vendorBean.getVendorPhone());
				vendor.setVendorWebsite(vendorBean.getVendorWebsite());
				vendor.setVendorAddress1(vendorBean.getVendorAddress1());
				vendor.setVendorAddress2(vendorBean.getVendorAddress2());
				vendor.setVendorCity(vendorBean.getVendorCity());
				vendor.setVendorState(vendorBean.getVendorState());
				vendor.setVendorCountry(vendorBean.getVendorCountry());
				vendor.setVendorPincode(vendorBean.getVendorPincode());
				vendor.setVendorRemarks(vendorBean.getVendorRemarks());
				vendor.setVendorTerm(vendorBean.getVendorTerm());
				vendor.setVendorPanNO(vendorBean.getVendorPanNO());
				vendor.setVendorGSTNO(vendorBean.getVendorGSTNO());
				vendor.setVendorGSTRegistered(vendorBean.getVendorGSTRegistered());
				vendor.setVendorCreditLimit(vendorBean.getVendorCreditLimit());
				vendor.setVendorAdvancePaid(vendorBean.getVendorAdvancePaid());

				vendor.setVendorFirName(vendorBean.getVendorFirName());
				vendor.setVendorFirPhone(vendorBean.getVendorFirPhone());
				vendor.setVendorFirEmail(vendorBean.getVendorFirEmail());
				vendor.setVendorSecName(vendorBean.getVendorSecName());
				vendor.setVendorSecPhone(vendorBean.getVendorSecPhone());
				vendor.setVendorSecEmail(vendorBean.getVendorSecEmail());

				vendor.setVendorBankName(vendorBean.getVendorBankName());
				vendor.setVendorBankBranch(vendorBean.getVendorBankBranch());
				vendor.setVendorBankAccno(vendorBean.getVendorBankAccno());
				vendor.setVendorBankIfsc(vendorBean.getVendorBankIfsc());
				vendor.setVendorTypeId(vendorBean.getVendorTypeId());

				beans.add(vendor);
			}
		}
		return beans;
	}
*/
	public List<VendorDto> prepareListofVendor(List<VendorDto> vendorList) {
		List<VendorDto> beans = null;
		if (vendorList != null && !vendorList.isEmpty()) {
			beans = new ArrayList<VendorDto>();
			VendorDto vendor = null;
			for (VendorDto vendorBean : vendorList) {
				vendor = new VendorDto();
				vendor.setVendorId(vendorBean.getVendorId());
				vendor.setVendorNumber(vendorBean.getVendorNumber());

				vendor.setVendorName(vendorBean.getVendorName());
				vendor.setVendorLocation(vendorBean.getVendorLocation());
				vendor.setVendorType(vendorBean.getVendorType());
				vendor.setVendorPhone(vendorBean.getVendorPhone());
				vendor.setVendorWebsite(vendorBean.getVendorWebsite());
				vendor.setVendorAddress1(vendorBean.getVendorAddress1());
				vendor.setVendorAddress2(vendorBean.getVendorAddress2());
				vendor.setVendorCity(vendorBean.getVendorCity());
				vendor.setVendorState(vendorBean.getVendorState());
				vendor.setVendorCountry(vendorBean.getVendorCountry());
				vendor.setVendorPincode(vendorBean.getVendorPincode());
				vendor.setVendorRemarks(vendorBean.getVendorRemarks());
				vendor.setVendorTerm(PaymentTypeConstant.getPaymentTypeName(vendorBean.getVendorTermId()));
				vendor.setVendorTermId(vendorBean.getVendorTermId());
				vendor.setVendorPanNO(vendorBean.getVendorPanNO());
				vendor.setVendorGSTNO(vendorBean.getVendorGSTNO());
				vendor.setVendorGSTRegistered(VendorGSTRegistered.getVendorRegisterdName(vendorBean.getVendorGSTRegisteredId()));
				vendor.setVendorGSTRegisteredId(vendorBean.getVendorGSTRegisteredId());
				vendor.setVendorCreditLimit(vendorBean.getVendorCreditLimit());
				vendor.setVendorAdvancePaid(vendorBean.getVendorAdvancePaid());

				vendor.setVendorFirName(vendorBean.getVendorFirName());
				vendor.setVendorFirPhone(vendorBean.getVendorFirPhone());
				vendor.setVendorFirEmail(vendorBean.getVendorFirEmail());
				vendor.setVendorSecName(vendorBean.getVendorSecName());
				vendor.setVendorSecPhone(vendorBean.getVendorSecPhone());
				vendor.setVendorSecEmail(vendorBean.getVendorSecEmail());

				vendor.setVendorBankName(vendorBean.getVendorBankName());
				vendor.setVendorBankBranch(vendorBean.getVendorBankBranch());
				vendor.setVendorBankAccno(vendorBean.getVendorBankAccno());
				vendor.setVendorBankIfsc(vendorBean.getVendorBankIfsc());
				vendor.setVendorTypeId(vendorBean.getVendorTypeId());

				beans.add(vendor);
			}
		}
		return beans;
	}

	
	
	// get the vendor Details
	/*public VendorDto getVendor(Vendor vendorBean) {
		VendorDto vendor = new VendorDto();

		vendor.setVendorId(vendorBean.getVendorId());
		vendor.setVendorNumber(vendorBean.getVendorNumber());
		vendor.setVendorTypeId(vendorBean.getVendorTypeId());

		vendor.setVendorName(vendorBean.getVendorName());
		vendor.setVendorLocation(vendorBean.getVendorLocation());
		vendor.setVendorType(vendorBean.getVendorType());
		vendor.setVendorTypeId(vendorBean.getVendorTypeId());
		vendor.setVendorPhone(vendorBean.getVendorPhone());
		vendor.setVendorWebsite(vendorBean.getVendorWebsite());
		vendor.setVendorAddress1(vendorBean.getVendorAddress1());
		vendor.setVendorAddress2(vendorBean.getVendorAddress2());
		vendor.setVendorCity(vendorBean.getVendorCity());
		vendor.setVendorState(vendorBean.getVendorState());
		vendor.setVendorCountry(vendorBean.getVendorCountry());
		vendor.setVendorPincode(vendorBean.getVendorPincode());
		vendor.setVendorRemarks(vendorBean.getVendorRemarks());
		vendor.setVendorTerm(vendorBean.getVendorTerm());
		vendor.setVendorPanNO(vendorBean.getVendorPanNO());
		vendor.setVendorGSTNO(vendorBean.getVendorGSTNO());
		vendor.setVendorGSTRegistered(vendorBean.getVendorGSTRegistered());
		vendor.setVendorCreditLimit(vendorBean.getVendorCreditLimit());
		vendor.setVendorAdvancePaid(vendorBean.getVendorAdvancePaid());

		vendor.setVendorFirName(vendorBean.getVendorFirName());
		vendor.setVendorFirPhone(vendorBean.getVendorFirPhone());
		vendor.setVendorFirEmail(vendorBean.getVendorFirEmail());
		vendor.setVendorSecName(vendorBean.getVendorSecName());
		vendor.setVendorSecPhone(vendorBean.getVendorSecPhone());
		vendor.setVendorSecEmail(vendorBean.getVendorSecEmail());

		vendor.setVendorBankName(vendorBean.getVendorBankName());
		vendor.setVendorBankBranch(vendorBean.getVendorBankBranch());
		vendor.setVendorBankAccno(vendorBean.getVendorBankAccno());
		vendor.setVendorBankIfsc(vendorBean.getVendorBankIfsc());
		vendor.setCompanyId(vendorBean.getCompanyId());

		// Create and Last updated Display
		vendor.setCreatedBy(vendorBean.getCreatedBy());
		if (vendorBean.getCreated() != null) {
			vendor.setCreated(CreatedDateTime.format(vendorBean.getCreated()));
		}
		vendor.setLastModifiedBy(vendorBean.getLastModifiedBy());
		if (vendorBean.getLastupdated() != null) {
			vendor.setLastupdated(CreatedDateTime.format(vendorBean.getLastupdated()));
		}

		return vendor;
	}
*/	
	public VendorDto getVendorDetails(VendorDto vendorBean) {
		VendorDto vendor = new VendorDto();
		vendor.setVendorId(vendorBean.getVendorId());
		vendor.setVendorNumber(vendorBean.getVendorNumber());
		vendor.setVendorTypeId(vendorBean.getVendorTypeId());

		vendor.setVendorName(vendorBean.getVendorName());
		vendor.setVendorLocation(vendorBean.getVendorLocation());
		vendor.setVendorType(vendorBean.getVendorType());
		vendor.setVendorTypeId(vendorBean.getVendorTypeId());
		vendor.setVendorPhone(vendorBean.getVendorPhone());
		vendor.setVendorWebsite(vendorBean.getVendorWebsite());
		vendor.setVendorAddress1(vendorBean.getVendorAddress1());
		vendor.setVendorAddress2(vendorBean.getVendorAddress2());
		vendor.setVendorCity(vendorBean.getVendorCity());
		vendor.setVendorState(vendorBean.getVendorState());
		vendor.setVendorCountry(vendorBean.getVendorCountry());
		vendor.setVendorPincode(vendorBean.getVendorPincode());
		vendor.setVendorRemarks(vendorBean.getVendorRemarks());
		vendor.setVendorTermId(vendorBean.getVendorTermId());
		vendor.setVendorTerm(PaymentTypeConstant.getPaymentTypeName(vendorBean.getVendorTermId()));
		vendor.setVendorPanNO(vendorBean.getVendorPanNO());
		vendor.setVendorGSTNO(vendorBean.getVendorGSTNO());
		vendor.setVendorGSTRegistered(VendorGSTRegistered.getVendorRegisterdName(vendorBean.getVendorGSTRegisteredId()));
		vendor.setVendorGSTRegisteredId(vendorBean.getVendorGSTRegisteredId());
		vendor.setVendorCreditLimit(vendorBean.getVendorCreditLimit());
		vendor.setVendorAdvancePaid(vendorBean.getVendorAdvancePaid());

		vendor.setVendorFirName(vendorBean.getVendorFirName());
		vendor.setVendorFirPhone(vendorBean.getVendorFirPhone());
		vendor.setVendorFirEmail(vendorBean.getVendorFirEmail());
		vendor.setVendorSecName(vendorBean.getVendorSecName());
		vendor.setVendorSecPhone(vendorBean.getVendorSecPhone());
		vendor.setVendorSecEmail(vendorBean.getVendorSecEmail());

		vendor.setVendorBankName(vendorBean.getVendorBankName());
		vendor.setVendorBankBranch(vendorBean.getVendorBankBranch());
		vendor.setVendorBankAccno(vendorBean.getVendorBankAccno());
		vendor.setVendorBankIfsc(vendorBean.getVendorBankIfsc());
		vendor.setCompanyId(vendorBean.getCompanyId());
		vendor.setVendorId(vendorBean.getVendorId());
		vendor.setOwnPetrolPump(vendorBean.getOwnPetrolPump());
		if(vendorBean.getOwnPetrolPump() == 1) {
			vendor.setOwnPetrolPumpStr("Yes");
		}else {
			vendor.setOwnPetrolPumpStr("No");
		}

		// Create and Last updated Display
		vendor.setCreatedBy(vendorBean.getCreatedBy());
		vendor.setCreatedById(vendorBean.getCreatedById());
		if (vendorBean.getCreatedOn() != null) {
			vendor.setCreated(CreatedDateTime.format(vendorBean.getCreatedOn()));
		}
		vendor.setLastModifiedBy(vendorBean.getLastModifiedBy());
		if (vendorBean.getLastupdatedOn() != null) {
			vendor.setLastupdated(CreatedDateTime.format(vendorBean.getLastupdatedOn()));
		}
		vendor.setVendorTDSPercent(vendorBean.getVendorTDSPercent());
		return vendor;
	}

	/*// get Issues information in database
	public Vendor getVendorBean(Vendor vendorBean) {

		Vendor vendor = new Vendor();

		vendor.setVendorId(vendorBean.getVendorId());

		vendor.setVendorName(vendorBean.getVendorName());
		vendor.setVendorLocation(vendorBean.getVendorLocation());
		vendor.setVendorType(vendorBean.getVendorType());
		vendor.setVendorPhone(vendorBean.getVendorPhone());
		vendor.setVendorWebsite(vendorBean.getVendorWebsite());
		vendor.setVendorAddress1(vendorBean.getVendorAddress1());
		vendor.setVendorAddress2(vendorBean.getVendorAddress2());
		vendor.setVendorCity(vendorBean.getVendorCity());
		vendor.setVendorState(vendorBean.getVendorState());
		vendor.setVendorCountry(vendorBean.getVendorCountry());
		vendor.setVendorPincode(vendorBean.getVendorPincode());
		vendor.setVendorRemarks(vendorBean.getVendorRemarks());
		vendor.setVendorTerm(vendorBean.getVendorTerm());
		vendor.setVendorPanNO(vendorBean.getVendorPanNO());
		vendor.setVendorGSTNO(vendorBean.getVendorGSTNO());
		vendor.setVendorGSTRegistered(vendorBean.getVendorGSTRegistered());
		vendor.setVendorCreditLimit(vendorBean.getVendorCreditLimit());
		vendor.setVendorAdvancePaid(vendorBean.getVendorAdvancePaid());

		vendor.setVendorFirName(vendorBean.getVendorFirName());
		vendor.setVendorFirPhone(vendorBean.getVendorFirPhone());
		vendor.setVendorFirEmail(vendorBean.getVendorFirEmail());
		vendor.setVendorSecName(vendorBean.getVendorSecName());
		vendor.setVendorSecPhone(vendorBean.getVendorSecPhone());
		vendor.setVendorSecEmail(vendorBean.getVendorSecEmail());

		vendor.setVendorBankName(vendorBean.getVendorBankName());
		vendor.setVendorBankBranch(vendorBean.getVendorBankBranch());
		vendor.setVendorBankAccno(vendorBean.getVendorBankAccno());
		vendor.setVendorBankIfsc(vendorBean.getVendorBankIfsc());

		return vendor;
	}
*/
	/**
	 * @param list_VendorFixedPartPrice_VehicleID
	 * @return
	 */
	public List<VendorFixedPartPriceDto> prepare_Listof_VendorFixedPartPrice_Bean(
			List<VendorFixedPartPriceDto> VendorFixedPartPrice_List) {
		// Note Show Vendor Fixed Part Price

		List<VendorFixedPartPriceDto> beans = null;
		if (VendorFixedPartPrice_List != null && !VendorFixedPartPrice_List.isEmpty()) {
			beans = new ArrayList<VendorFixedPartPriceDto>();
			VendorFixedPartPriceDto list = null;
			for (VendorFixedPartPriceDto vendorBean : VendorFixedPartPrice_List) {
				list = new VendorFixedPartPriceDto();

				list.setVPPID(vendorBean.getVPPID());
				list.setPARTID(vendorBean.getPARTID());
				list.setVENDORID(vendorBean.getVENDORID());
				list.setPARTQUANTITY(vendorBean.getPARTQUANTITY());
				list.setPARTEACHCOST(vendorBean.getPARTEACHCOST());
				list.setPARTDISCOUNT(vendorBean.getPARTDISCOUNT());
				list.setPARTGST(vendorBean.getPARTGST());
				list.setPARTTOTAL(vendorBean.getPARTTOTAL());
				list.setPARTNAME(vendorBean.getCREATEDBY());
				list.setPARTNUMBER(vendorBean.getLASTMODIFIEDBY());

				beans.add(list);
			}
		}
		return beans;
	}
	
	public List<VendorFixedFuelPriceDto> prepare_Listof_VendorFixedFuelPrice_Bean(
			List<VendorFixedFuelPrice> list_VendorFixedFuelPrice_VehicleID) {

		// Note Show Vendor Fixed Part Price

		List<VendorFixedFuelPriceDto> beans = null;
		if (list_VendorFixedFuelPrice_VehicleID != null && !list_VendorFixedFuelPrice_VehicleID.isEmpty()) {
			beans = new ArrayList<VendorFixedFuelPriceDto>();
			VendorFixedFuelPriceDto list = null;
			for (VendorFixedFuelPrice vendorBean : list_VendorFixedFuelPrice_VehicleID) {
				list = new VendorFixedFuelPriceDto();

				list.setVFFID(vendorBean.getVFFID());
				 list.setFID(vendorBean.getFID());
				//list.setFUEL_NAME(vendorBean.getFUEL_NAME());
				if (vendorBean.getFUEL_FIXED_DATE() != null) {
					list.setFUEL_FIXED_DATE(dateFormatName.format(vendorBean.getFUEL_FIXED_DATE()));
				}
				list.setFUEL_PERDAY_COST(vendorBean.getFUEL_PERDAY_COST());

				beans.add(list);
			}
		}
		return beans;
	}
	
	public VendorFixedFuelPrice prepare_VendorFixedFuelPrice_Bean(VendorFixedFuelPriceDto vendorFuelPrice) {

		VendorFixedFuelPrice FuelPricer = null;
		try {
			FuelPricer = new VendorFixedFuelPrice();

			FuelPricer.setFID(vendorFuelPrice.getFID());
			//FuelPricer.setFUEL_NAME(vendorFuelPrice.getFUEL_NAME());

			try {
				if (vendorFuelPrice.getFUEL_FIXED_DATE() != null) {
					java.util.Date date = dateFormat.parse(vendorFuelPrice.getFUEL_FIXED_DATE());
					Date Reported_Date = new Date(date.getTime());
					FuelPricer.setFUEL_FIXED_DATE(Reported_Date);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			FuelPricer.setFUEL_PERDAY_COST(vendorFuelPrice.getFUEL_PERDAY_COST());
			FuelPricer.setVENDORID(vendorFuelPrice.getVENDORID());

			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			FuelPricer.setCREATEDBYID(userDetails.getId());
			FuelPricer.setLASTMODIFIEDBYID(userDetails.getId());

			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			FuelPricer.setCREATEDDATE(toDate);
			FuelPricer.setLASTUPDATED(toDate);

			FuelPricer.setMarkForDelete(false);

		} catch (Exception e) {
			
		}
		return FuelPricer;
	}
	
	
	
	public Vendor prepareFuelEntriesToCreateNewVendor(String vendorName, String VendorLocation,Integer companyId,Long id) {

		Vendor vendor = new Vendor();


		vendor.setVendorName(vendorName);
		vendor.setVendorLocation(VendorLocation);
		vendor.setVendorPhone(null);
		vendor.setVendorWebsite(null);
		vendor.setVendorAddress1(VendorLocation);
		vendor.setVendorAddress2(VendorLocation);
		vendor.setVendorCity(VendorLocation);
		vendor.setVendorState(null);
		vendor.setVendorCountry("india");
		vendor.setVendorPincode(null);
		vendor.setVendorRemarks("This Create Fuel Entries To Vendor");
		vendor.setAutoVendor(true);
		
		vendor.setVendorTermId(PaymentTypeConstant.PAYMENT_TYPE_CASH);
		vendor.setVendorPanNO(null);
		vendor.setVendorGSTNO(null);
		vendor.setVendorGSTRegisteredId(VendorGSTRegistered.VENDOR_GST_NOT_REGISTERED);
		vendor.setVendorCreditLimit(null);
		vendor.setVendorAdvancePaid(null);

		vendor.setVendorFirName(null);
		vendor.setVendorFirPhone(null);
		vendor.setVendorFirEmail(null);
		vendor.setVendorSecName(null);
		vendor.setVendorSecPhone(null);
		vendor.setVendorSecEmail(null);

		vendor.setVendorBankName(null);
		vendor.setVendorBankBranch(null);
		vendor.setVendorBankAccno(null);
		vendor.setVendorBankIfsc(null);

		//CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		vendor.setCreatedById(id);
		vendor.setLastModifiedById(id);

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		vendor.setCreated(toDate);
		vendor.setLastupdated(toDate);
		vendor.setMarkForDelete(false);
		vendor.setCompanyId(companyId);

		return vendor;
	}

	public Vendor prepareNewVendorFromIssueServiceEntries(String newVendorId, String newVendorLocationId,
			Integer company_id)throws Exception {
		Vendor vendor	= null;
		try {
			vendor = new Vendor();
			vendor.setVendorTermId(PaymentTypeConstant.PAYMENT_TYPE_CASH);
			vendor.setVendorGSTRegisteredId(VendorGSTRegistered.VENDOR_GST_NOT_REGISTERED);
			vendor.setCompanyId(company_id);
			vendor.setCreated(new Date());
			vendor.setLastupdated(new Date());
			vendor.setVendorRemarks("This Create Service Entries To Vendor");
			if(!newVendorId.isEmpty() || newVendorId != null ) {
				vendor.setVendorName(newVendorId);			//ServiceEntries.getVendor_name() Original Code Word
			}
			if(!newVendorLocationId.isEmpty() || newVendorLocationId != null ) {
				vendor.setVendorLocation(newVendorLocationId);	//ServiceEntries.getVendor_location() Original Code Word
			}
			vendor.setAutoVendor(true);
			
			
		}catch(Exception e) {
			System.err.println("err"+e);
			throw e;
		}
		return vendor;
		
	}

	public Vendor prepareVendorFormDse(ValueObject valueObject) throws Exception {
		Vendor vendor	= null;
		try {
			vendor = new Vendor();
			vendor.setVendorTermId(PaymentTypeConstant.PAYMENT_TYPE_CASH);
			vendor.setVendorGSTRegisteredId(VendorGSTRegistered.VENDOR_GST_NOT_REGISTERED);
			vendor.setVendorRemarks("Created From DSE");
			vendor.setVendorAddress1(valueObject.getString("vendorAddress",""));
			vendor.setVendorName(valueObject.getString("vendorName"));			
			vendor.setVendorLocation(valueObject.getString("vendorLocation"));	
			vendor.setVendorPhone(valueObject.getString("vendorPhoneNumber"));
			vendor.setVendorTypeId(valueObject.getLong("vendorTypeId"));
			vendor.setVendorFirEmail(valueObject.getString("vendorEmail"));
			vendor.setAutoVendor(false);
			vendor.setCompanyId(valueObject.getInt("companyId"));
			vendor.setCreated(new Date());
			vendor.setLastupdated(new Date());
			
			return vendor;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

}
