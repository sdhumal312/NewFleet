package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.web.util.ValueObject;


public class JobSubTypeBL {

	public JobSubTypeBL() {
	}

	/***************************************************************************************************************	
	*/
	// Save the JobSub settings types
	public JobSubType prepareJobSubTypeModel(JobSubTypeDto JobSubTypeDto) {

		// create obj on driverDoctype
		JobSubType DocType = new JobSubType();

		DocType.setJob_Subid(JobSubTypeDto.getJob_Subid());
		DocType.setJob_Type(JobSubTypeDto.getJob_Type());
		DocType.setJob_ROT(JobSubTypeDto.getJob_ROT());
		DocType.setJob_ROT_number(JobSubTypeDto.getJob_ROT_number());
		DocType.setJob_ROT_hour(JobSubTypeDto.getJob_ROT_hour());
		DocType.setJob_ROT_amount(JobSubTypeDto.getJob_ROT_amount());

		return DocType;
	}

	// would show the Job Sub List of Document Driver
	public List<JobSubType> JobSubListofDto(List<JobSubType> JobType) {

		List<JobSubType> Dtos = null;
		if (JobType != null && !JobType.isEmpty()) {
			Dtos = new ArrayList<JobSubType>();

			JobSubType Dto = null;

			for (JobSubType DriverDocType : JobType) {

				Dto = new JobSubType();

				Dto.setJob_Subid(DriverDocType.getJob_Subid());
				Dto.setJob_Type(DriverDocType.getJob_Type());
				Dto.setJob_ROT(DriverDocType.getJob_ROT());
				Dto.setJob_ROT_number(DriverDocType.getJob_ROT_number());
				Dto.setJob_ROT_hour(DriverDocType.getJob_ROT_hour());
				Dto.setJob_ROT_amount(DriverDocType.getJob_ROT_amount());

				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the Driver document type
	public JobSubTypeDto prepareJobSubTypeDto(JobSubType DocType) {

		JobSubTypeDto Dto = new JobSubTypeDto();

		Dto.setJob_Subid(DocType.getJob_Subid());
		Dto.setJob_Type(DocType.getJob_Type());
		Dto.setJob_ROT(DocType.getJob_ROT());
		Dto.setJob_ROT_number(DocType.getJob_ROT_number());
		Dto.setJob_ROT_hour(DocType.getJob_ROT_hour());
		Dto.setJob_ROT_amount(DocType.getJob_ROT_amount());

		return Dto;
	}

	public JobSubType prepareNewJobSubTypeFromIssueServiceEntries(Integer job_id, String job_Type, ValueObject object, CustomUserDetails userDetails )throws Exception {
		JobSubType jobSubType = null;
		try {
			jobSubType  = new JobSubType();
			
			jobSubType.setJob_TypeId(job_id);// JobTyoeID
			jobSubType.setJob_Type(job_Type);// JobTypeName
			jobSubType.setJob_ROT("" + object.getString("service_ROT"));	// SubType	
			jobSubType.setJob_ROT_number("" + object.getString("service_ROT_number"));	// SubTypeNumber
			jobSubType.setJob_ROT_hour("" + 0);
			jobSubType.setJob_ROT_amount("" + 0);
			jobSubType.setCompanyId(userDetails.getCompany_id());
			jobSubType.setCreatedBy(userDetails.getEmail());
			jobSubType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			jobSubType.setCreatedById(userDetails.getId());
			jobSubType.setJob_ROT_Service_Reminder(false);
			
			return jobSubType;
		} catch (Exception e) {
			throw e;
			
		}
		
		
		
		
	}
}
