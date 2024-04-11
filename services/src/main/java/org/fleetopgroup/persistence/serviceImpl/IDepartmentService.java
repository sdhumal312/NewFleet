package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Department;
import org.fleetopgroup.web.util.ValueObject;

public interface IDepartmentService {

    Department registerNewDepartment(Department departmentDto) throws Exception;
    
    void updateDepartment(Department departmentupdate) throws Exception;
    
    List<Department> findAll();
    
    Department getDepartment(String verificationToken);

    void deleteDepartment(Integer department_id, Integer companyId);

    Department getDepartmentByID(Integer department_id);
    
    List<Department> validateDepartmentName(String departmentName, Integer company_Id);
    
    List<Department> SearchDepartmentLisrCompanyID(Integer Company_ID) throws Exception;
    
    /**This value is search only Department name in Issues dropdown*/
    List<Department> Search_Only_Department_name(String search, CustomUserDetails userDetails);
    
    /**This value is get Issues to Department_id to get department name*/
    Department get_Issues_DepartmentByID_to_DepartmentName(Integer department_id);

    long count();

    public ValueObject searhByDepartmentNameInMobile(ValueObject object) throws Exception;
}
