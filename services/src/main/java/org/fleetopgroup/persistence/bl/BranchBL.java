package org.fleetopgroup.persistence.bl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.fleetopgroup.persistence.dto.BranchDocumentDto;
import org.fleetopgroup.persistence.dto.BranchDto;
import org.fleetopgroup.persistence.model.Branch;
import org.fleetopgroup.persistence.model.BranchHistory;
import org.fleetopgroup.persistence.model.BranchPhoto;
import org.springframework.stereotype.Controller;

/**
 * @author fleetop
 *
 *
 *
 */

@Controller
public class BranchBL {

	public BranchBL() {
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	// Save the Branch Document settings types
	public Branch prepareBranchModel(BranchDto branchDto) {

		// create obj on Company
		Branch branch = new Branch();
        
		branch.setBranch_name(branchDto.getBranch_name());
		branch.setBranch_code(branchDto.getBranch_code());
		branch.setBranch_address(branchDto.getBranch_address());
		branch.setBranch_city(branchDto.getBranch_city());
		branch.setBranch_state(branchDto.getBranch_state());
		branch.setBranch_country(branchDto.getBranch_country());
		branch.setBranch_pincode(branchDto.getBranch_pincode());


		branch.setBranch_email(branchDto.getBranch_email());
		branch.setBranch_mobilenumber(branchDto.getBranch_mobilenumber());
		branch.setBranch_phonenumber(branchDto.getBranch_phonenumber());
		branch.setBranch_incharge(branchDto.getBranch_incharge());
		branch.setBranch_incharge_phone(branchDto.getBranch_incharge_phone());
		branch.setBranch_time_from(branchDto.getBranch_time_from());
		branch.setBranch_time_to(branchDto.getBranch_time_to());
		branch.setBranch_landmark(branchDto.getBranch_landmark());
		branch.setBranch_ownership_type(branchDto.getBranch_ownership_type());
		branch.setOwner1_name(branchDto.getOwner1_name());
		branch.setOwner1_pan(branchDto.getOwner1_pan());
		branch.setOwner1_address(branchDto.getOwner1_address());
		branch.setOwner1_mobile(branchDto.getOwner1_mobile());
		branch.setOwner2_name(branchDto.getOwner2_name());
		branch.setOwner2_pan(branchDto.getOwner2_pan());
		branch.setOwner2_address(branchDto.getOwner2_address());
		branch.setOwner2_mobile(branchDto.getOwner2_mobile());
		branch.setAnnual_increment(branchDto.getAnnual_increment());
		branch.setAdvance_paid(branchDto.getAdvance_paid());
		branch.setLease_amount(branchDto.getLease_amount());
		branch.setMonthly_rent(branchDto.getMonthly_rent());
		branch.setBranch_status("ACTIVE");
		branch.setBranch_electricity_no(branchDto.getBranch_electricity_no());
		branch.setBranch_serviceTax_no(branchDto.getBranch_serviceTax_no());
		try {
			// fuel date converted String to date Format
			if(branchDto.getMonthly_rent_date() != null) {
				java.util.Date date = dateFormat.parse(branchDto.getMonthly_rent_date());
				java.sql.Date rentDate = new Date(date.getTime());
				branch.setMonthly_rent_date(rentDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		branch.setCreated(toDate);
		branch.setLastupdated(toDate);
		branch.setMarkForDelete(false);

		return branch;
	}

	// Get The branch details to show Edit page Branch
	public BranchDto GetBranchDetailsto(Branch branchDto) {

		// create obj on Company
		BranchDto branch = new BranchDto();

		branch.setBranch_id(branchDto.getBranch_id());
		branch.setBranch_name(branchDto.getBranch_name());
		branch.setBranch_code(branchDto.getBranch_code());
		branch.setBranch_address(branchDto.getBranch_address());
		branch.setBranch_city(branchDto.getBranch_city());
		branch.setBranch_state(branchDto.getBranch_state());
		branch.setBranch_country(branchDto.getBranch_country());
		branch.setBranch_pincode(branchDto.getBranch_pincode());

		branch.setCompany_id(branchDto.getCompany_id());
		//branch.setCompany_name(branchDto.getCompany_name());

		branch.setBranch_email(branchDto.getBranch_email());
		branch.setBranch_mobilenumber(branchDto.getBranch_mobilenumber());
		branch.setBranch_phonenumber(branchDto.getBranch_phonenumber());
		branch.setBranch_incharge(branchDto.getBranch_incharge());
		branch.setBranch_incharge_phone(branchDto.getBranch_incharge_phone());
		branch.setBranch_time_from(branchDto.getBranch_time_from());
		branch.setBranch_time_to(branchDto.getBranch_time_to());
		branch.setBranch_landmark(branchDto.getBranch_landmark());
		branch.setBranch_ownership_type(branchDto.getBranch_ownership_type());
		branch.setOwner1_name(branchDto.getOwner1_name());
		branch.setOwner1_pan(branchDto.getOwner1_pan());
		branch.setOwner1_address(branchDto.getOwner1_address());
		branch.setOwner1_mobile(branchDto.getOwner1_mobile());
		branch.setOwner2_name(branchDto.getOwner2_name());
		branch.setOwner2_pan(branchDto.getOwner2_pan());
		branch.setOwner2_address(branchDto.getOwner2_address());
		branch.setOwner2_mobile(branchDto.getOwner2_mobile());
		branch.setAnnual_increment(branchDto.getAnnual_increment());
		branch.setAdvance_paid(branchDto.getAdvance_paid());
		branch.setLease_amount(branchDto.getLease_amount());
		branch.setMonthly_rent(branchDto.getMonthly_rent());
		branch.setBranch_status("ACTIVE");
		branch.setBranch_electricity_no(branchDto.getBranch_electricity_no());
		branch.setBranch_serviceTax_no(branchDto.getBranch_serviceTax_no());

		if (branchDto.getMonthly_rent_date() != null) {
			// DB to get date Change UI Format
			branch.setMonthly_rent_date(dateFormat.format(branchDto.getMonthly_rent_date()));
		}

		// Create and Last updated Display
		branch.setCreatedById(branchDto.getCreatedById());
		branch.setCreatedBy(branchDto.getCreatedBy());
		if (branchDto.getCreated() != null) {
			branch.setCreated(CreatedDateTime.format(branchDto.getCreated()));
		}
		branch.setLastModifiedBy(branchDto.getLastModifiedBy());
		branch.setLastModifiedById(branchDto.getLastModifiedById());
		if (branchDto.getLastupdated() != null) {
			branch.setLastupdated(CreatedDateTime.format(branchDto.getLastupdated()));
		}
		branch.setMarkForDelete(false);

		return branch;
	}

	// Update the Branch Document settings types
	public Branch UpdateprepareBranchModel(BranchDto branchDto) {

		// create obj on Company

		Branch branch = new Branch();
		branch.setBranch_id(branchDto.getBranch_id());
		branch.setBranch_name(branchDto.getBranch_name());
		branch.setBranch_code(branchDto.getBranch_code());
		branch.setBranch_address(branchDto.getBranch_address());
		branch.setBranch_city(branchDto.getBranch_city());
		branch.setBranch_state(branchDto.getBranch_state());
		branch.setBranch_country(branchDto.getBranch_country());
		branch.setBranch_pincode(branchDto.getBranch_pincode());

		branch.setCompany_id(branchDto.getCompany_id());
		/* branch.setCompany_name(branchDto.getCompany_name()); */

		branch.setBranch_email(branchDto.getBranch_email());
		branch.setBranch_mobilenumber(branchDto.getBranch_mobilenumber());
		branch.setBranch_phonenumber(branchDto.getBranch_phonenumber());
		branch.setBranch_incharge(branchDto.getBranch_incharge());
		branch.setBranch_incharge_phone(branchDto.getBranch_incharge_phone());
		branch.setBranch_time_from(branchDto.getBranch_time_from());
		branch.setBranch_time_to(branchDto.getBranch_time_to());
		branch.setBranch_landmark(branchDto.getBranch_landmark());
		branch.setBranch_ownership_type(branchDto.getBranch_ownership_type());
		branch.setOwner1_name(branchDto.getOwner1_name());
		branch.setOwner1_pan(branchDto.getOwner1_pan());
		branch.setOwner1_address(branchDto.getOwner1_address());
		branch.setOwner1_mobile(branchDto.getOwner1_mobile());
		branch.setOwner2_name(branchDto.getOwner2_name());
		branch.setOwner2_pan(branchDto.getOwner2_pan());
		branch.setOwner2_address(branchDto.getOwner2_address());
		branch.setOwner2_mobile(branchDto.getOwner2_mobile());
		branch.setAnnual_increment(branchDto.getAnnual_increment());
		branch.setAdvance_paid(branchDto.getAdvance_paid());
		branch.setLease_amount(branchDto.getLease_amount());
		branch.setMonthly_rent(branchDto.getMonthly_rent());
		branch.setBranch_status("ACTIVE");
		branch.setBranch_electricity_no(branchDto.getBranch_electricity_no());
		branch.setBranch_serviceTax_no(branchDto.getBranch_serviceTax_no());
		try {
			// fuel date converted String to date Format
			if(branchDto.getMonthly_rent_date() != null) {
				java.util.Date date = dateFormat.parse(branchDto.getMonthly_rent_date());
				java.sql.Date rentDate = new Date(date.getTime());
				branch.setMonthly_rent_date(rentDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		branch.setCreated(toDate);
		branch.setLastupdated(toDate);

		branch.setMarkForDelete(false);

		return branch;
	}

	// Create Branch History DTO 
	public BranchHistory createBranchHistoryDto(Branch branchDto) throws Exception {
		
		BranchHistory 		branchHistory 		= null;
		
		try {
			
			branchHistory = new BranchHistory();
			
			branchHistory.setBranch_id(branchDto.getBranch_id());
			branchHistory.setBranch_name(branchDto.getBranch_name());
			branchHistory.setBranch_code(branchDto.getBranch_code());
			branchHistory.setBranch_address(branchDto.getBranch_address());
			branchHistory.setBranch_city(branchDto.getBranch_city());
			branchHistory.setBranch_state(branchDto.getBranch_state());
			branchHistory.setBranch_country(branchDto.getBranch_country());
			branchHistory.setBranch_pincode(branchDto.getBranch_pincode());
			branchHistory.setBranch_email(branchDto.getBranch_email());
			branchHistory.setBranch_mobilenumber(branchDto.getBranch_mobilenumber());
			branchHistory.setBranch_phonenumber(branchDto.getBranch_phonenumber());
			branchHistory.setBranch_incharge(branchDto.getBranch_incharge());
			branchHistory.setBranch_incharge_phone(branchDto.getBranch_incharge_phone());
			branchHistory.setBranch_time_from(branchDto.getBranch_time_from());
			branchHistory.setBranch_time_to(branchDto.getBranch_time_to());
			branchHistory.setBranch_landmark(branchDto.getBranch_landmark());
			branchHistory.setBranch_ownership_type(branchDto.getBranch_ownership_type());
			branchHistory.setOwner1_name(branchDto.getOwner1_name());
			branchHistory.setOwner1_pan(branchDto.getOwner1_pan());
			branchHistory.setOwner1_address(branchDto.getOwner1_address());
			branchHistory.setOwner1_mobile(branchDto.getOwner1_mobile());
			branchHistory.setOwner2_name(branchDto.getOwner2_name());
			branchHistory.setOwner2_pan(branchDto.getOwner2_pan());
			branchHistory.setOwner2_address(branchDto.getOwner2_address());
			branchHistory.setOwner2_mobile(branchDto.getOwner2_mobile());
			branchHistory.setAnnual_increment(branchDto.getAnnual_increment());
			branchHistory.setAdvance_paid(branchDto.getAdvance_paid());
			branchHistory.setLease_amount(branchDto.getLease_amount());
			branchHistory.setMonthly_rent(branchDto.getMonthly_rent());
			branchHistory.setBranch_status(branchDto.getBranch_status());
			branchHistory.setBranch_electricity_no(branchDto.getBranch_electricity_no());
			branchHistory.setBranch_serviceTax_no(branchDto.getBranch_serviceTax_no());
			if(branchDto.getMonthly_rent_date() != null)
				branchHistory.setMonthly_rent_date(branchDto.getMonthly_rent_date());
			if(branchDto.getLastModifiedById() != null)
				branchHistory.setLastModifiedById(branchDto.getLastModifiedById());
			if(branchDto.getLastupdated() != null)
				branchHistory.setLastupdated(branchDto.getLastupdated());
			branchHistory.setMarkForDelete(branchDto.isMarkForDelete());
			
			return branchHistory;
		} catch (Exception e) {
			throw e;
		} finally {
			branchHistory 		= null;
		}
	}

	/* Branch Document List */
	/* List of showing branchDocument details */
	public List<BranchDocumentDto> prepareListbranchDocument(List<org.fleetopgroup.persistence.document.BranchDocument> branchDocument) {
		List<BranchDocumentDto> beans = null;
		if (branchDocument != null && !branchDocument.isEmpty()) {
			beans = new ArrayList<BranchDocumentDto>();

			BranchDocumentDto branch = null;

			for (org.fleetopgroup.persistence.document.BranchDocument branchBean : branchDocument) {
				branch = new BranchDocumentDto();

				branch.setBranch_id(branchBean.getBranch_id());
				branch.setBranch_documentid(branchBean.get_id());

				branch.setBranch_documentname(branchBean.getBranch_documentname());

				branch.setBranch_uploaddate(branchBean.getBranch_uploaddate());

				branch.setBranch_docFrom(dateFormat.format(branchBean.getBranch_docFrom()));
				branch.setBranch_docTo(dateFormat.format(branchBean.getBranch_docTo()));

				beans.add(branch);
			}
		}
		return beans;
	}

	/* List of showing branch Photo details */
	public List<BranchPhoto> prepareListbranchPhoto(List<org.fleetopgroup.persistence.document.BranchPhoto> branchPhoto) {
		List<BranchPhoto> beans = null;
		if (branchPhoto != null && !branchPhoto.isEmpty()) {
			beans = new ArrayList<BranchPhoto>();
			BranchPhoto branch = null;

			for (org.fleetopgroup.persistence.document.BranchPhoto branchBean : branchPhoto) {
				branch = new BranchPhoto();

				branch.setBranch_id(branchBean.getBranch_id());
				branch.setBranch_photoid(branchBean.get_id());

				branch.setBranch_photoname(branchBean.getBranch_photoname());
				branch.setBranch_filename(branchBean.getBranch_filename());

				branch.setBranch_uploaddate(branchBean.getBranch_uploaddate());

				beans.add(branch);
			}
		}
		return beans;
	}
	
	// this test commit
	
	
	// Save the Branch prepareMaster_Company_BranchModel_add settings types
	public Branch prepareMaster_Company_BranchModel_add(BranchDto branchDto) {

			// create obj on Company
			Branch branch = new Branch();

			Base64.Decoder decoder = Base64.getDecoder();
			byte[] decodedByteArray = decoder.decode(branchDto.getCompany_id_encode());
			Integer Company_id = Integer.parseInt(new String(decodedByteArray));
			
			branch.setCompany_id(Company_id);
			
			branch.setBranch_name(branchDto.getBranch_name());
			branch.setBranch_code(branchDto.getBranch_code());
			branch.setBranch_address(branchDto.getBranch_address());
			branch.setBranch_city(branchDto.getBranch_city());
			branch.setBranch_state(branchDto.getBranch_state());
			branch.setBranch_country(branchDto.getBranch_country());
			branch.setBranch_pincode(branchDto.getBranch_pincode());

			branch.setBranch_email(branchDto.getBranch_email());
			branch.setBranch_mobilenumber(branchDto.getBranch_mobilenumber());
			branch.setBranch_phonenumber(branchDto.getBranch_phonenumber());
			branch.setBranch_incharge(branchDto.getBranch_incharge());
			branch.setBranch_incharge_phone(branchDto.getBranch_incharge_phone());
			branch.setBranch_time_from(branchDto.getBranch_time_from());
			branch.setBranch_time_to(branchDto.getBranch_time_to());
			branch.setBranch_landmark(branchDto.getBranch_landmark());
			branch.setBranch_ownership_type(branchDto.getBranch_ownership_type());
			branch.setOwner1_name(branchDto.getOwner1_name());
			branch.setOwner1_pan(branchDto.getOwner1_pan());
			branch.setOwner1_address(branchDto.getOwner1_address());
			branch.setOwner1_mobile(branchDto.getOwner1_mobile());
			branch.setOwner2_name(branchDto.getOwner2_name());
			branch.setOwner2_pan(branchDto.getOwner2_pan());
			branch.setOwner2_address(branchDto.getOwner2_address());
			branch.setOwner2_mobile(branchDto.getOwner2_mobile());
			branch.setAnnual_increment(branchDto.getAnnual_increment());
			branch.setAdvance_paid(branchDto.getAdvance_paid());
			branch.setLease_amount(branchDto.getLease_amount());
			branch.setMonthly_rent(branchDto.getMonthly_rent());
			branch.setBranch_status("ACTIVE");
			branch.setBranch_electricity_no(branchDto.getBranch_electricity_no());
			branch.setBranch_serviceTax_no(branchDto.getBranch_serviceTax_no());
			try {
				// fuel date converted String to date Format
				if(branchDto.getMonthly_rent_date() != null && !branchDto.getMonthly_rent_date().trim().equals("")) {
					java.util.Date date = dateFormat.parse(branchDto.getMonthly_rent_date());
					java.sql.Date rentDate = new Date(date.getTime());
					branch.setMonthly_rent_date(rentDate);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			branch.setCreated(toDate);
			branch.setLastupdated(toDate);
			branch.setMarkForDelete(false);

			return branch;
		}
	
}