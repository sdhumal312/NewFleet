package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.dto.JobTypeDto;
import org.fleetopgroup.persistence.dto.ReasonForRepairDto;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.JobSubTypeHistory;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.JobTypeHistory;
import org.fleetopgroup.persistence.model.ReasonForRepairType;
import org.fleetopgroup.persistence.model.ReasonTypeHistory;



public class ReasonTypeBL {

	public ReasonTypeBL() {
	}
	
	public ReasonForRepairType prepareReasonForRepairType(ReasonForRepairDto reasonTypeBL) {
	
		ReasonForRepairType reasonForRepairType = new ReasonForRepairType();
		
		reasonForRepairType.setReason_Type(reasonTypeBL.getReason_Type());
		reasonForRepairType.setReason_id(reasonTypeBL.getReason_id());
	
		return reasonForRepairType;
	}
	
		public List<ReasonForRepairDto> ReasonListofDto(List<ReasonForRepairType> ReasonType) {

			List<ReasonForRepairDto> Dtos = null;
			if (ReasonType != null && !ReasonType.isEmpty()) {
				Dtos = new ArrayList<ReasonForRepairDto>();

				ReasonForRepairDto Dto = null;
				for (ReasonForRepairType ReasonDocType : ReasonType) {
					Dto = new ReasonForRepairDto();
					Dto.setReason_id(ReasonDocType.getReason_id());
					Dto.setReason_Type(ReasonDocType.getReason_Type());
					Dto.setCompanyId(ReasonDocType.getCompanyId());

					Dtos.add(Dto);
				}
			}
			return Dtos;
		}
		
		public ReasonForRepairDto prepareReasonTypeDto(ReasonForRepairType DocType) {

			ReasonForRepairDto Dto = new ReasonForRepairDto();
			
			Dto.setReason_id(DocType.getReason_id());
			Dto.setReason_Type(DocType.getReason_Type());

			return Dto;
		}

		public ReasonForRepairType prepareReasonTypeModel(ReasonForRepairDto ReasonTypeDto) {

			ReasonForRepairType DocType = new ReasonForRepairType();
			DocType.setReason_id(ReasonTypeDto.getReason_id());
			DocType.setReason_Type(ReasonTypeDto.getReason_Type());

			return DocType;
		}
		
		public ReasonTypeHistory prepareReasonHistoryModel(ReasonForRepairType reasonType) {
			ReasonTypeHistory		reasonTypeHistory	= new ReasonTypeHistory();
			
			reasonTypeHistory.setCompanyId(reasonType.getCompanyId());
			reasonTypeHistory.setReason_id(reasonType.getReason_id());
			reasonTypeHistory.setReason_Type(reasonType.getReason_Type());
			reasonTypeHistory.setLastModifiedById(reasonType.getLastModifiedById());
			reasonTypeHistory.setLastModifiedOn(reasonType.getLastModifiedOn());
			reasonTypeHistory.setMarkForDelete(reasonType.isMarkForDelete());
			
			return reasonTypeHistory;
		}

	
}
