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
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.JobSubTypeHistory;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.JobTypeHistory;


public class JobTypeBL {

	public JobTypeBL() {
	}

	// Save the Job settings types
	public JobType prepareJobTypeModel(JobTypeDto JobTypeDto) {

		// create obj on driverDoctype
		JobType DocType = new JobType();
		DocType.setJob_id(JobTypeDto.getJob_id());
		DocType.setJob_Type(JobTypeDto.getJob_Type());

		return DocType;
	}

	// would show the driver List of Document Driver
	public List<JobTypeDto> JobListofDto(List<JobType> JobType) {

		List<JobTypeDto> Dtos = null;
		if (JobType != null && !JobType.isEmpty()) {
			Dtos = new ArrayList<JobTypeDto>();

			JobTypeDto Dto = null;
			for (JobType DriverDocType : JobType) {
				Dto = new JobTypeDto();
				Dto.setJob_Type(DriverDocType.getJob_Type());
				Dto.setJob_id(DriverDocType.getJob_id());
				Dto.setCompanyId(DriverDocType.getCompanyId());

				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the Driver document type
	public JobTypeDto prepareJobTypeDto(JobType DocType) {

		JobTypeDto Dto = new JobTypeDto();

		Dto.setJob_id(DocType.getJob_id());
		Dto.setJob_Type(DocType.getJob_Type());
		return Dto;
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
			DocType.setJob_TypeId(JobSubTypeDto.getJob_TypeId());
			
			return DocType;
		}

		// would show the Job Sub List of Document Driver
		public List<JobSubType> JobSubListofDto(List<JobSubTypeDto> JobType) {

			List<JobSubType> Dtos = null;
			if (JobType != null && !JobType.isEmpty()) {
				Dtos = new ArrayList<JobSubType>();

				JobSubType Dto = null;

				for (JobSubTypeDto DriverDocType : JobType) {

					Dto = new JobSubType();

					Dto.setJob_Subid(DriverDocType.getJob_Subid());
					Dto.setJob_Type(DriverDocType.getJob_Type());
					Dto.setJob_TypeId(DriverDocType.getJob_TypeId());
					Dto.setJob_ROT(DriverDocType.getJob_ROT());
					Dto.setJob_ROT_number(DriverDocType.getJob_ROT_number());
					Dto.setJob_ROT_hour(DriverDocType.getJob_ROT_hour());
					Dto.setJob_ROT_amount(DriverDocType.getJob_ROT_amount());

					Dtos.add(Dto);
				}
			}
			return Dtos;
		}

		public List<JobSubType> getJobSubList(List<JobSubTypeDto> JobType) {

			List<JobSubType> Dtos = null;
			if (JobType != null && !JobType.isEmpty()) {
				Dtos = new ArrayList<JobSubType>();

				JobSubType Dto = null;

				for (JobSubTypeDto DriverDocType : JobType) {

					Dto = new JobSubType();

					Dto.setJob_Subid(DriverDocType.getJob_Subid());
					Dto.setJob_Type(DriverDocType.getJob_Type());
					Dto.setJob_TypeId(DriverDocType.getJob_TypeId());
					Dto.setJob_ROT(DriverDocType.getJob_ROT());
					Dto.setJob_ROT_number(DriverDocType.getJob_ROT_number());
					Dto.setJob_ROT_hour(DriverDocType.getJob_ROT_hour());
					Dto.setJob_ROT_amount(DriverDocType.getJob_ROT_amount());
					Dto.setCompanyId(DriverDocType.getCompanyId());

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

		public JobSubTypeDto prepareJobSubTypeDto(JobSubTypeDto DocType) {

			JobSubTypeDto Dto = new JobSubTypeDto();

			Dto.setJob_Subid(DocType.getJob_Subid());
			Dto.setJob_Type(DocType.getJob_Type());
			Dto.setJob_TypeId(DocType.getJob_TypeId());
			Dto.setJob_ROT(DocType.getJob_ROT());
			Dto.setJob_ROT_number(DocType.getJob_ROT_number());
			Dto.setJob_ROT_hour(DocType.getJob_ROT_hour());
			Dto.setJob_ROT_amount(DocType.getJob_ROT_amount());
			if(DocType.getROT_Service_Reminder() != null) {
			Dto.setROT_Service_Reminder(DocType.getROT_Service_Reminder());
			}
			
			return Dto;
		}

		public JobTypeHistory prepareHistoryModel(JobType jobType) {
			JobTypeHistory		jobTypeHistory	= new JobTypeHistory();
			
			jobTypeHistory.setCompanyId(jobType.getCompanyId());
			jobTypeHistory.setJob_id(jobType.getJob_id());
			jobTypeHistory.setJob_Type(jobType.getJob_Type());
			jobTypeHistory.setLastModifiedById(jobType.getLastModifiedById());
			jobTypeHistory.setLastModifiedOn(jobType.getLastModifiedOn());
			jobTypeHistory.setMarkForDelete(jobType.isMarkForDelete());
			
			return jobTypeHistory;
		}

		public JobSubTypeHistory prepareJobSubTypeHistoryModel(JobSubType jobSubType) {
			JobSubTypeHistory 	jobSubTypeHistory  = new JobSubTypeHistory();
			
			jobSubTypeHistory.setCompanyId(jobSubType.getCompanyId());
			jobSubTypeHistory.setJob_ROT(jobSubType.getJob_ROT());
			jobSubTypeHistory.setJob_ROT_amount(jobSubType.getJob_ROT_amount());
			jobSubTypeHistory.setJob_ROT_hour(jobSubType.getJob_ROT_hour());
			jobSubTypeHistory.setJob_ROT_number(jobSubType.getJob_ROT_number());
			jobSubTypeHistory.setJob_Subid(jobSubType.getJob_Subid());
			jobSubTypeHistory.setJob_Type(jobSubType.getJob_Type());
			jobSubTypeHistory.setJob_TypeId(jobSubType.getJob_TypeId());
			jobSubTypeHistory.setLastModifiedById(jobSubType.getLastModifiedById());
			jobSubTypeHistory.setLastModifiedOn(jobSubType.getLastModifiedOn());
			jobSubTypeHistory.setMarkForDelete(jobSubType.isMarkForDelete());
			jobSubTypeHistory.setJob_ROT_Service_Reminder(jobSubType.isJob_ROT_Service_Reminder());
			
			return jobSubTypeHistory;
		}

}
