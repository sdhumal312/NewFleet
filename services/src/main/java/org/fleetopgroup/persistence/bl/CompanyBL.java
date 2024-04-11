package org.fleetopgroup.persistence.bl;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CompanybankDto;
import org.fleetopgroup.persistence.dto.CompanydirectorDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.model.CompanyFixedAllowance;
import org.fleetopgroup.persistence.model.Companybank;
import org.fleetopgroup.persistence.model.Companydirector;
import org.springframework.security.core.context.SecurityContextHolder;

public class CompanyBL {

	public final Integer FIXED_ALLOWNCE_DAYS = 1;

	public CompanyBL() {
	}

	// Save the driver Document settings types
	public Company prepareCompanyModel(CompanyDto CompanyDto) {

		// create obj on Company
		Company Dto = new Company();
		Dto.setCompany_id(CompanyDto.getCompany_id());
		Dto.setCompany_name(CompanyDto.getCompany_name());
		Dto.setCompanyCode(CompanyDto.getCompanyCode());
		Dto.setCompany_address(CompanyDto.getCompany_address());
		Dto.setCompany_city(CompanyDto.getCompany_city());
		Dto.setCompany_state(CompanyDto.getCompany_state());
		Dto.setCompany_country(CompanyDto.getCompany_country());
		Dto.setCompany_pincode(CompanyDto.getCompany_pincode());
		Dto.setCompany_website(CompanyDto.getCompany_website());
		Dto.setCompany_email(CompanyDto.getCompany_email());
		Dto.setCompany_mobilenumber(CompanyDto.getCompany_mobilenumber());
		Dto.setCompany_type(CompanyDto.getCompany_type());
		Dto.setCompany_pan_no(CompanyDto.getCompany_pan_no());
		Dto.setCompany_tan_no(CompanyDto.getCompany_tan_no());
		Dto.setCompany_tax_no(CompanyDto.getCompany_tax_no());
		Dto.setCompany_tin_no(CompanyDto.getCompany_tin_no());
		Dto.setCompany_cin_no(CompanyDto.getCompany_cin_no());
		Dto.setCompany_abount(CompanyDto.getCompany_abount());
		Dto.setCompany_parentName(CompanyDto.getCompany_parentName());
		Dto.setCompany_parent_id(CompanyDto.getCompany_parent_id());

		Dto.setCompany_esi_pf_days(CompanyDto.getCompany_esi_pf_days());
		Dto.setCompany_esi_pf_disable(CompanyDto.getCompany_esi_pf_disable());

		Dto.setCompany_status(CompanyDto.getCompany_status());

		Dto.setCreatedBy(CompanyDto.getCreatedBy());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Dto.setCreated(toDate);
		Dto.setLastupdated(toDate);
		Dto.setMarkForDelete(false);

		return Dto;
	}
	
	public Company prepareCompanyModel_Update(CompanyDto CompanyDto) {

		// create obj on Company
		Company Dto = new Company();
		
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decodedByteArray = decoder.decode(CompanyDto.getCompany_id_encode());
		Integer Company_id = Integer.parseInt(new String(decodedByteArray));
		Dto.setCompany_id(Company_id);
		Dto.setCompany_name(CompanyDto.getCompany_name());
		Dto.setCompanyCode(CompanyDto.getCompanyCode());
		
		Dto.setCompany_address(CompanyDto.getCompany_address());
		Dto.setCompany_city(CompanyDto.getCompany_city());
		Dto.setCompany_state(CompanyDto.getCompany_state());
		Dto.setCompany_country(CompanyDto.getCompany_country());
		Dto.setCompany_pincode(CompanyDto.getCompany_pincode());
		Dto.setCompany_website(CompanyDto.getCompany_website());
		Dto.setCompany_email(CompanyDto.getCompany_email());
		Dto.setCompany_mobilenumber(CompanyDto.getCompany_mobilenumber());
		Dto.setCompany_type(CompanyDto.getCompany_type());
		Dto.setCompany_pan_no(CompanyDto.getCompany_pan_no());
		Dto.setCompany_tan_no(CompanyDto.getCompany_tan_no());
		Dto.setCompany_tax_no(CompanyDto.getCompany_tax_no());
		Dto.setCompany_tin_no(CompanyDto.getCompany_tin_no());
		Dto.setCompany_cin_no(CompanyDto.getCompany_cin_no());
		Dto.setCompany_abount(CompanyDto.getCompany_abount());
		Dto.setCompany_parentName(CompanyDto.getCompany_parentName());
		Dto.setCompany_parent_id(CompanyDto.getCompany_parent_id());

		Dto.setCompany_esi_pf_days(CompanyDto.getCompany_esi_pf_days());
		Dto.setCompany_esi_pf_disable(CompanyDto.getCompany_esi_pf_disable());

		Dto.setCompany_status(CompanyDto.getCompany_status());
		Dto.setCompanyCode(CompanyDto.getCompanyCode());
		Dto.setCreatedBy(CompanyDto.getCreatedBy());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Dto.setCreated(toDate);
		Dto.setLastupdated(toDate);
		Dto.setMarkForDelete(false);

		return Dto;
	}

	// would show the driver List of Document Driver
	public List<CompanyDto> CompanyListofDto(List<Company> Company) {

		List<CompanyDto> Dtos = null;
		if (Company != null && !Company.isEmpty()) {
			Dtos = new ArrayList<CompanyDto>();

			CompanyDto Dto = null;
			for (Company CompanyDto : Company) {
				Dto = new CompanyDto();
				Dto.setCompany_name(CompanyDto.getCompany_name());
				Dto.setCompany_address(CompanyDto.getCompany_address());
				Dto.setCompany_city(CompanyDto.getCompany_city());
				Dto.setCompany_state(CompanyDto.getCompany_state());
				Dto.setCompany_country(CompanyDto.getCompany_country());
				Dto.setCompany_pincode(CompanyDto.getCompany_pincode());
				Dto.setCompany_website(CompanyDto.getCompany_website());
				Dto.setCompany_email(CompanyDto.getCompany_email());
				Dto.setCompany_mobilenumber(CompanyDto.getCompany_mobilenumber());
				Dto.setCompany_type(CompanyDto.getCompany_type());
				Dto.setCompany_pan_no(CompanyDto.getCompany_pan_no());
				Dto.setCompany_tan_no(CompanyDto.getCompany_tan_no());
				Dto.setCompany_tax_no(CompanyDto.getCompany_tax_no());
				Dto.setCompany_tin_no(CompanyDto.getCompany_tin_no());
				Dto.setCompany_cin_no(CompanyDto.getCompany_cin_no());
				Dto.setCompany_abount(CompanyDto.getCompany_abount());
				Dto.setCompany_parentName(CompanyDto.getCompany_parentName());
				Dto.setCompany_parent_id(CompanyDto.getCompany_parent_id());
				Dto.setCompany_status(CompanyDto.getCompany_status());

				Dto.setCreatedBy(CompanyDto.getCreatedBy());

				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the Driver document type
	public CompanyDto prepareCompanyDto(Company CompanyDto) {
		CompanyDto Dto = new CompanyDto();

		Base64.Encoder encoder = Base64.getEncoder();
		String normalString = "" + CompanyDto.getCompany_id();
		String encodedString = encoder.encodeToString(normalString.getBytes(StandardCharsets.UTF_8));

		Dto.setCompany_id(CompanyDto.getCompany_id());
		Dto.setCompany_id_encode(encodedString);
		
		Dto.setCompany_name(CompanyDto.getCompany_name());
		Dto.setCompanyCode(CompanyDto.getCompanyCode());
		
		Dto.setCompany_address(CompanyDto.getCompany_address());
		Dto.setCompany_city(CompanyDto.getCompany_city());
		Dto.setCompany_state(CompanyDto.getCompany_state());
		Dto.setCompany_country(CompanyDto.getCompany_country());
		Dto.setCompany_pincode(CompanyDto.getCompany_pincode());
		Dto.setCompany_website(CompanyDto.getCompany_website());
		Dto.setCompany_email(CompanyDto.getCompany_email());
		Dto.setCompany_mobilenumber(CompanyDto.getCompany_mobilenumber());
		Dto.setCompany_type(CompanyDto.getCompany_type());
		Dto.setCompany_pan_no(CompanyDto.getCompany_pan_no());
		Dto.setCompany_tan_no(CompanyDto.getCompany_tan_no());
		Dto.setCompany_tax_no(CompanyDto.getCompany_tax_no());
		Dto.setCompany_tin_no(CompanyDto.getCompany_tin_no());
		Dto.setCompany_cin_no(CompanyDto.getCompany_cin_no());
		Dto.setCompany_abount(CompanyDto.getCompany_abount());
		Dto.setCompany_parentName(CompanyDto.getCompany_parentName());
		Dto.setCompany_parent_id(CompanyDto.getCompany_parent_id());

		Dto.setCompany_esi_pf_days(CompanyDto.getCompany_esi_pf_days());
		Dto.setCompany_esi_pf_disable(CompanyDto.getCompany_esi_pf_disable());

		Dto.setCompany_status(CompanyDto.getCompany_status());

		Dto.setCreatedBy(CompanyDto.getCreatedBy());
		Dto.setCompanyCode(CompanyDto.getCompanyCode());
		return Dto;
	}

	// Save the driver Document settings types
	public Companydirector prepareCompanydirector(CompanydirectorDto CompanyDto) {

		// create obj on Company
		Companydirector Dto = new Companydirector();

		Dto.setCom_directors_id(CompanyDto.getCom_directors_id());
		Dto.setCompany_id(CompanyDto.getCompany_id());
		Dto.setCom_directors_name(CompanyDto.getCom_directors_name());
		Dto.setCom_designation(CompanyDto.getCom_designation());
		Dto.setCom_directors_mobile(CompanyDto.getCom_directors_mobile());
		Dto.setCom_directors_email(CompanyDto.getCom_directors_email());
		Dto.setCom_directors_status(CompanyDto.getCom_directors_status());

		Dto.setCreatedBy(CompanyDto.getCreatedBy());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Dto.setCreated(toDate);
		Dto.setLastupdated(toDate);
		Dto.setMarkForDelete(false);

		return Dto;
	}

	public Companydirector updateCompanydirector(CompanydirectorDto CompanyDto) {

		// create obj on Company
		Companydirector Dto = new Companydirector();

		Dto.setCom_directors_id(CompanyDto.getCom_directors_id());
		Dto.setCompany_id(CompanyDto.getCompany_id());
		Dto.setCom_directors_name(CompanyDto.getCom_directors_name());
		Dto.setCom_designation(CompanyDto.getCom_designation());
		Dto.setCom_directors_mobile(CompanyDto.getCom_directors_mobile());
		Dto.setCom_directors_email(CompanyDto.getCom_directors_email());
		Dto.setCom_directors_status(CompanyDto.getCom_directors_status());

		Dto.setCreatedBy(CompanyDto.getCreatedBy());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Dto.setLastupdated(toDate);
		Dto.setMarkForDelete(false);

		return Dto;
	}

	// Save the driver Document settings types
	public Companybank prepareCompanybank(CompanybankDto CompanyDto) {

		// create obj on Company
		Companybank Dto = new Companybank();

		Dto.setCom_bank_id(CompanyDto.getCom_bank_id());
		Dto.setCompany_id(CompanyDto.getCompany_id());
		Dto.setCom_bank_name(CompanyDto.getCom_bank_name());
		Dto.setCom_bank_account(CompanyDto.getCom_bank_account());
		Dto.setCom_bank_actype(CompanyDto.getCom_bank_actype());
		Dto.setCom_bank_ifsc(CompanyDto.getCom_bank_ifsc());
		Dto.setCom_bank_bsr(CompanyDto.getCom_bank_bsr());
		Dto.setCom_bank_branch(CompanyDto.getCom_bank_branch());
		Dto.setCom_bank_address(CompanyDto.getCom_bank_address());
		Dto.setCom_bank_city(CompanyDto.getCom_bank_city());
		Dto.setCom_bank_state(CompanyDto.getCom_bank_state());
		Dto.setCom_bank_country(CompanyDto.getCom_bank_country());
		Dto.setCom_bank_pincode(CompanyDto.getCom_bank_pincode());
		Dto.setCom_bank_status(CompanyDto.getCom_bank_status());

		Dto.setCreatedBy(CompanyDto.getCreatedBy());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Dto.setCreated(toDate);
		Dto.setLastupdated(toDate);
		Dto.setMarkForDelete(false);

		return Dto;
	}

	public Companybank UpdateCompanybankDetails(CompanybankDto CompanyDto) {

		// create obj on Company
		Companybank Dto = new Companybank();

		Dto.setCom_bank_id(CompanyDto.getCom_bank_id());
		Dto.setCompany_id(CompanyDto.getCompany_id());
		Dto.setCom_bank_name(CompanyDto.getCom_bank_name());
		Dto.setCom_bank_account(CompanyDto.getCom_bank_account());
		Dto.setCom_bank_actype(CompanyDto.getCom_bank_actype());
		Dto.setCom_bank_ifsc(CompanyDto.getCom_bank_ifsc());
		Dto.setCom_bank_bsr(CompanyDto.getCom_bank_bsr());
		Dto.setCom_bank_branch(CompanyDto.getCom_bank_branch());
		Dto.setCom_bank_address(CompanyDto.getCom_bank_address());
		Dto.setCom_bank_city(CompanyDto.getCom_bank_city());
		Dto.setCom_bank_state(CompanyDto.getCom_bank_state());
		Dto.setCom_bank_country(CompanyDto.getCom_bank_country());
		Dto.setCom_bank_pincode(CompanyDto.getCom_bank_pincode());
		Dto.setCom_bank_status(CompanyDto.getCom_bank_status());

		Dto.setCreatedBy(CompanyDto.getCreatedBy());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Dto.setLastupdated(toDate);
		Dto.setMarkForDelete(false);

		return Dto;
	}

	// Save the driver Document settings types
	public Company updateSubCompanyDetails(CompanyDto CompanyDto) {

		// create obj on Company
		Company Dto = new Company();
		Dto.setCompany_id(CompanyDto.getCompany_id());
		Dto.setCompany_name(CompanyDto.getCompany_name());
		Dto.setCompany_address(CompanyDto.getCompany_address());
		Dto.setCompany_city(CompanyDto.getCompany_city());
		Dto.setCompany_state(CompanyDto.getCompany_state());
		Dto.setCompany_country(CompanyDto.getCompany_country());
		Dto.setCompany_pincode(CompanyDto.getCompany_pincode());
		Dto.setCompany_website(CompanyDto.getCompany_website());
		Dto.setCompany_email(CompanyDto.getCompany_email());
		Dto.setCompany_mobilenumber(CompanyDto.getCompany_mobilenumber());
		Dto.setCompany_type(CompanyDto.getCompany_type());
		Dto.setCompany_pan_no(CompanyDto.getCompany_pan_no());
		Dto.setCompany_tan_no(CompanyDto.getCompany_tan_no());
		Dto.setCompany_tax_no(CompanyDto.getCompany_tax_no());
		Dto.setCompany_tin_no(CompanyDto.getCompany_tin_no());
		Dto.setCompany_cin_no(CompanyDto.getCompany_cin_no());
		Dto.setCompany_abount(CompanyDto.getCompany_abount());
		Dto.setCompany_parentName(CompanyDto.getCompany_parentName());
		Dto.setCompany_parent_id(CompanyDto.getCompany_parent_id());
		Dto.setCompany_status(CompanyDto.getCompany_status());

		Dto.setCreatedBy(CompanyDto.getCreatedBy());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		Dto.setLastupdated(toDate);
		Dto.setMarkForDelete(false);

		return Dto;
	}

	// sub company Id Encode after decode value

	// would show the driver List of Document Driver
	public List<CompanyDto> SubCompanyListofDto(List<Company> Company) {

		List<CompanyDto> Dtos = null;
		if (Company != null && !Company.isEmpty()) {
			Dtos = new ArrayList<CompanyDto>();

			CompanyDto Dto = null;
			for (Company CompanyDto : Company) {
				Dto = new CompanyDto();

				Base64.Encoder encoder = Base64.getEncoder();
				String normalString = "" + CompanyDto.getCompany_id();
				String encodedString = encoder.encodeToString(normalString.getBytes(StandardCharsets.UTF_8));

				// System.out.println(encodedString);
				Dto.setCompany_id_encode(encodedString);

				Dto.setCompany_name(CompanyDto.getCompany_name());
				Dto.setCompany_address(CompanyDto.getCompany_address());
				Dto.setCompany_city(CompanyDto.getCompany_city());
				Dto.setCompany_state(CompanyDto.getCompany_state());
				Dto.setCompany_country(CompanyDto.getCompany_country());
				Dto.setCompany_pincode(CompanyDto.getCompany_pincode());
				Dto.setCompany_website(CompanyDto.getCompany_website());
				Dto.setCompany_email(CompanyDto.getCompany_email());
				Dto.setCompany_mobilenumber(CompanyDto.getCompany_mobilenumber());
				Dto.setCompany_type(CompanyDto.getCompany_type());
				Dto.setCompany_pan_no(CompanyDto.getCompany_pan_no());
				Dto.setCompany_tan_no(CompanyDto.getCompany_tan_no());
				Dto.setCompany_tax_no(CompanyDto.getCompany_tax_no());
				Dto.setCompany_tin_no(CompanyDto.getCompany_tin_no());
				Dto.setCompany_cin_no(CompanyDto.getCompany_cin_no());
				Dto.setCompany_abount(CompanyDto.getCompany_abount());
				Dto.setCompany_parentName(CompanyDto.getCompany_parentName());
				Dto.setCompany_parent_id(CompanyDto.getCompany_parent_id());
				Dto.setCompany_status(CompanyDto.getCompany_status());

				Dto.setCreatedBy(CompanyDto.getCreatedBy());

				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// Save the driver Document settings types
	public CompanyDto ShowCompanyID_Encode(Company CompanyDto) {

		// create obj on Company
		CompanyDto Dto = new CompanyDto();

		Base64.Encoder encoder = Base64.getEncoder();
		String normalString = "" + CompanyDto.getCompany_id();
		String encodedString = encoder.encodeToString(normalString.getBytes(StandardCharsets.UTF_8));

		Dto.setCompany_id_encode(encodedString);
		Dto.setCompanyCode(CompanyDto.getCompanyCode());
		
		Dto.setCompany_name(CompanyDto.getCompany_name());
		Dto.setCompany_address(CompanyDto.getCompany_address());
		Dto.setCompany_city(CompanyDto.getCompany_city());
		Dto.setCompany_state(CompanyDto.getCompany_state());
		Dto.setCompany_country(CompanyDto.getCompany_country());
		Dto.setCompany_pincode(CompanyDto.getCompany_pincode());
		Dto.setCompany_website(CompanyDto.getCompany_website());
		Dto.setCompany_email(CompanyDto.getCompany_email());
		Dto.setCompany_mobilenumber(CompanyDto.getCompany_mobilenumber());
		Dto.setCompany_type(CompanyDto.getCompany_type());
		Dto.setCompany_pan_no(CompanyDto.getCompany_pan_no());
		Dto.setCompany_tan_no(CompanyDto.getCompany_tan_no());
		Dto.setCompany_tax_no(CompanyDto.getCompany_tax_no());
		Dto.setCompany_tin_no(CompanyDto.getCompany_tin_no());
		Dto.setCompany_cin_no(CompanyDto.getCompany_cin_no());
		Dto.setCompany_abount(CompanyDto.getCompany_abount());
		Dto.setCompany_parentName(CompanyDto.getCompany_parentName());
		Dto.setCompany_parent_id(CompanyDto.getCompany_parent_id());
		Dto.setCompany_status(CompanyDto.getCompany_status());

		Dto.setCreatedBy(CompanyDto.getCreatedBy());

		return Dto;
	}

	public CompanyFixedAllowance prepare_CompanyFixedAllowance(CompanyFixedAllowance fixedAllowance) {

		CompanyFixedAllowance Allowance = null;
		try {
			Allowance = new CompanyFixedAllowance();

			
			Allowance.setVEHICLEGROUP_ID(fixedAllowance.getVEHICLEGROUP_ID());
			Allowance.setDRIVER_JOBTYPE_ID(fixedAllowance.getDRIVER_JOBTYPE_ID());

			Allowance.setFIX_PERDAY_ALLOWANCE(FIXED_ALLOWNCE_DAYS);
			Allowance.setFIX_PERDAY_ALLOWANCE_AMOUNT(fixedAllowance.getFIX_PERDAY_ALLOWANCE_AMOUNT());

			Allowance.setFIX_EXTRA_DAYS(fixedAllowance.getFIX_EXTRA_DAYS());
			Allowance.setFIX_EXTRA_DAYS_AMOUNT(fixedAllowance.getFIX_EXTRA_DAYS_AMOUNT());

			Allowance.setMarkForDelete(false);

			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			Allowance.setCREATEDBY(userDetails.getEmail());
			Allowance.setLASTMODIFIEDBY(userDetails.getEmail());
			Allowance.setCOMPANY_ID(userDetails.getCompany_id());

			Date currentDateUpdate = new Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			Allowance.setCREATED(toDate);
			Allowance.setLASTUPDATED(toDate);

		} catch (Exception e) {
			Allowance = null;
		}

		return Allowance;
	}

	public List<CompanyDto> Show_MasterCompany_Encode_Details(List<Company> companyList) {

		List<CompanyDto> com_encode = null;
		if (companyList != null && !companyList.isEmpty()) {
			com_encode = new ArrayList<CompanyDto>();
			CompanyDto Dto = null;
			for (Company com_nonEncode : companyList) {
				Dto = new CompanyDto();

				Base64.Encoder encoder = Base64.getEncoder();
				String normalString = "" + com_nonEncode.getCompany_id();
				String encodedString = encoder.encodeToString(normalString.getBytes(StandardCharsets.UTF_8));

				Dto.setCompany_id_encode(encodedString);

				Dto.setCompany_name(com_nonEncode.getCompany_name());
				Dto.setCompany_address(com_nonEncode.getCompany_address());
				Dto.setCompany_city(com_nonEncode.getCompany_city());
				Dto.setCompany_state(com_nonEncode.getCompany_state());
				Dto.setCompany_country(com_nonEncode.getCompany_country());
				Dto.setCompany_pincode(com_nonEncode.getCompany_pincode());
				Dto.setCompany_website(com_nonEncode.getCompany_website());
				Dto.setCompany_email(com_nonEncode.getCompany_email());
				Dto.setCompany_mobilenumber(com_nonEncode.getCompany_mobilenumber());
				Dto.setCompany_type(com_nonEncode.getCompany_type());
				Dto.setCompany_pan_no(com_nonEncode.getCompany_pan_no());
				Dto.setCompany_tan_no(com_nonEncode.getCompany_tan_no());
				Dto.setCompany_tax_no(com_nonEncode.getCompany_tax_no());
				Dto.setCompany_tin_no(com_nonEncode.getCompany_tin_no());
				Dto.setCompany_cin_no(com_nonEncode.getCompany_cin_no());
				Dto.setCompany_abount(com_nonEncode.getCompany_abount());
				Dto.setCompany_parentName(com_nonEncode.getCompany_parentName());
				Dto.setCompany_parent_id(com_nonEncode.getCompany_parent_id());
				Dto.setCompany_status(com_nonEncode.getCompany_status());
				Dto.setCreatedBy(com_nonEncode.getCreatedBy());
				
			}
		}
		return com_encode;
	}
}
