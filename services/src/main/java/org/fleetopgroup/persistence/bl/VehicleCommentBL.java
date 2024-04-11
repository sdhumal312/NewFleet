package org.fleetopgroup.persistence.bl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleCommentDto;
import org.fleetopgroup.web.util.DateDiffrentConvert;

public class VehicleCommentBL {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("EEE, d MMM yyyy");
	SimpleDateFormat dateFormatonlyDateTime = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");

	DateDiffrentConvert dataconvert = new DateDiffrentConvert();

	public VehicleCommentBL() {
	}

	/** Showing VehicleComment Details to be in VehicleCommentDto */
	public List<VehicleCommentDto> prepare_VehicleComment_ListofDto(List<VehicleCommentDto> vehicleComment) {
		List<VehicleCommentDto> Dtos = null;
		if (vehicleComment != null && !vehicleComment.isEmpty()) {
			Dtos = new ArrayList<VehicleCommentDto>();
			VehicleCommentDto issueCom = null;
			for (VehicleCommentDto issuesCOMDto : vehicleComment) {
				issueCom = new VehicleCommentDto();

				issueCom.setVEHICLE_COMMENTID(issuesCOMDto.getVEHICLE_COMMENTID());
				issueCom.setVEHICLE_ID(issuesCOMDto.getVEHICLE_ID());
				issueCom.setVEHICLE_TITLE(issuesCOMDto.getVEHICLE_TITLE());
				issueCom.setVEHICLE_COMMENT(issuesCOMDto.getVEHICLE_COMMENT());

				issueCom.setCREATEDBY(issuesCOMDto.getCREATEDBY());
				issueCom.setCREATED_EMAIL(issuesCOMDto.getCREATED_EMAIL());
				issueCom.setMarkForDelete(false);
				/** Set Created Current Date */
				if (issuesCOMDto.getCREATED_DATE_ON() != null) {
					issueCom.setCREATED_DATE(dateFormatonlyDateTime.format(issuesCOMDto.getCREATED_DATE_ON()));
				}

				// System.out.println( diffenceDays);
				try {
					issueCom.setCREATED_DATE_DIFFERENT(dataconvert.DifferentDate(issuesCOMDto.getCREATED_DATE_ON()));
				} catch (Exception e) {
					e.printStackTrace();
				}

				Dtos.add(issueCom);
			}
		}
		return Dtos;
	}
}
