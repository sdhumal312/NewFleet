package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Modifying
	@Query("update Department DDT set DDT.markForDelete = 1 where DDT.depart_id = ?1 AND DDT.company_id = ?2")
	void deleteDepartment(Integer department_id, Integer companyId);
    
    @Query("FROM Department DDT where DDT.depart_name = ?1 and DDT.company_id = ?2 and DDT.markForDelete = 0")
    List<Department> validateDepartmentName(String departmentName,Integer company_Id);
    
    @Query("FROM Department DDT where DDT.depart_id = ?1 AND DDT.company_id = ?2 and DDT.markForDelete = 0")
    Department getDepartmentByID(Integer department_id, Integer companyId);
    
    @Query("FROM Department DDT where DDT.company_id = ?1 and DDT.markForDelete = 0")
    List<Department> SearchDepartmentLisrCompanyID(Integer Company_ID);
    
    long count();
    
    
    @Modifying
   	@Query("update Department DDT set DDT.depart_code  = ?1, DDT.depart_hod = ?2 , DDT.depart_description = ?3, DDT.lastModifiedById = ?4, DDT.lastModifiedOn = ?5 where DDT.depart_id = ?6 AND DDT.company_id = ?7")
   	void updateDepartment(String depart_code, String depart_hod, String depart_description,Long modifiedById, Timestamp modifiedOn ,Integer department_id, Integer companyId);
}
