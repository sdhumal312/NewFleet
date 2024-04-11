package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.fleetopgroup.persistence.dto.DepartmentDto;
import org.fleetopgroup.persistence.model.Department;
import org.fleetopgroup.persistence.model.DepartmentHistory;


public class DepartmentBL {

	public DepartmentBL() {
	}

	// Save the driver Document settings types
	public Department prepareDepatmentSave(DepartmentDto department) {

		// create obj on Department
		Department Dto = new Department();


		Dto.setDepart_name(department.getDepart_name());
		Dto.setDepart_code(department.getDepart_code());
		Dto.setDepart_hod(department.getDepart_hod());
		Dto.setDepart_description(department.getDepart_description());

		return Dto;
	}
	
	// Save the driver Document settings types
		public Department prepareDepatmentUpdate(DepartmentDto department, long lastModifiedById) {

			// create obj on Department
			Department Dto = new Department();

			Dto.setDepart_id(department.getDepart_id());
			Dto.setDepart_name(department.getDepart_name());
			Dto.setDepart_code(department.getDepart_code());
			Dto.setDepart_hod(department.getDepart_hod());
			Dto.setDepart_description(department.getDepart_description());
			Dto.setLastModifiedById(lastModifiedById);
			Dto.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));

			return Dto;
		}

	// would show the driver List of Document Driver
	public List<DepartmentDto> DriDocTypeListofDto(List<Department> Department) {

		List<DepartmentDto> Dtos = null;
		if (Department != null && !Department.isEmpty()) {
			Dtos = new ArrayList<DepartmentDto>();

			DepartmentDto Dto = null;
			for (Department department : Department) {
				Dto = new DepartmentDto();
				Dto.setDepart_id(department.getDepart_id());
				Dto.setDepart_name(department.getDepart_name());
				Dto.setDepart_code(department.getDepart_code());
				Dto.setDepart_hod(department.getDepart_hod());
				Dto.setDepart_description(department.getDepart_description());
				//Dto.setCompany_id(department.getCompany_id());
				//Dto.setCompany_name(department.getCompany_name());
				
				Dtos.add(Dto);
			}
		}
		return Dtos;
	}

	// edit the Driver document type
	public DepartmentDto prepareDepartmentDto(Department department) {
		DepartmentDto Dto = new DepartmentDto();

		Dto.setDepart_id(department.getDepart_id());
		Dto.setDepart_name(department.getDepart_name());
		Dto.setDepart_code(department.getDepart_code());
		Dto.setDepart_hod(department.getDepart_hod());
		Dto.setDepart_description(department.getDepart_description());
		Dto.setCompany_id(department.getCompany_id());
		//Dto.setCompany_name(department.getCompany_name());
		return Dto;
	}

	
	public Department prepareMaster_Company_DepatmentModel_add(DepartmentDto department) {

		// create obj on Department
		Department Dto = new Department();

		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decodedByteArray = decoder.decode(department.getCompany_id_encode());
		Integer Company_id = Integer.parseInt(new String(decodedByteArray));
		
		Dto.setCompany_id(Company_id);
		
		Dto.setDepart_name(department.getDepart_name());
		Dto.setDepart_code(department.getDepart_code());
		Dto.setDepart_hod(department.getDepart_hod());
		Dto.setDepart_description(department.getDepart_description());
		//Dto.setCompany_name(department.getCompany_name());

		return Dto;
	}

	public DepartmentHistory prepareDepartmentHistoryDTO(Department department) {

		// create obj on Department History
		DepartmentHistory departmentHistory = new DepartmentHistory();

		departmentHistory.setDepart_id(department.getDepart_id());
		departmentHistory.setDepart_name(department.getDepart_name());
		departmentHistory.setDepart_code(department.getDepart_code());
		departmentHistory.setDepart_hod(department.getDepart_hod());
		departmentHistory.setDepart_description(department.getDepart_description());
		departmentHistory.setCompany_id(department.getCompany_id());
		departmentHistory.setLastModifiedById(department.getLastModifiedById());
		departmentHistory.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
		departmentHistory.setMarkForDelete(department.isMarkForDelete());

		return departmentHistory;
	}
}
