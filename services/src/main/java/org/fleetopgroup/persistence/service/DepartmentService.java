package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.DepartmentRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Department;
import org.fleetopgroup.persistence.serviceImpl.IDepartmentService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DepartmentService implements IDepartmentService {
	
	@PersistenceContext
	EntityManager entityManager;
	
    @Autowired
    private DepartmentRepository departmentRepository;

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#registerNewDepartment(org.fleetop.persistence.model.Department)
	 */
	@Transactional
	public Department registerNewDepartment(Department departmentDto) throws Exception {
		
		return departmentRepository.save(departmentDto);
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#updateDepartment(java.lang.String, long)
	 */
	@Transactional
	public void updateDepartment(Department department) throws Exception {
		
		  departmentRepository.updateDepartment(department.getDepart_code(), department.getDepart_hod(), department.getDepart_description(),department.getLastModifiedById(),department.getLastModifiedOn(), department.getDepart_id(), department.getCompany_id());
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#findAll()
	 */
	@Transactional
	public List<Department> findAll() {
		
		return departmentRepository.findAll();
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#getDepartment(java.lang.String)
	 */
	@Transactional
	public Department getDepartment(String verificationToken) {
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#deleteDepartment(long)
	 */
	@Transactional
	public void deleteDepartment(Integer department_id, Integer companyId) {
		
		departmentRepository.deleteDepartment(department_id, companyId);
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#getDepartmentByID(long)
	 */
	@Transactional
	public Department getDepartmentByID(Integer department_id) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return departmentRepository.getDepartmentByID(department_id, userDetails.getCompany_id());
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#validateDriDocType(java.lang.String)
	 */
	@Transactional
	public List<Department> validateDepartmentName(String departmentName,Integer company_Id) {
		
		return departmentRepository.validateDepartmentName(departmentName, company_Id);
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#count()
	 */
	@Transactional
	public long count() {
		
		return departmentRepository.count();
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#SearchDepartmentLisrCompanyID(java.lang.Integer)
	 */
	@Transactional
	public List<Department> SearchDepartmentLisrCompanyID(Integer Company_ID) {
		try {
			return departmentRepository.SearchDepartmentLisrCompanyID(Company_ID);
		} catch (Exception e) {
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#Search_Only_Department_name(java.lang.String)
	 */
	@Transactional
	public List<Department> Search_Only_Department_name(String Search, CustomUserDetails userDetails) {
		
		TypedQuery<Object[]> queryt = entityManager
				.createQuery(
						"SELECT d.depart_id, d.depart_name, d.depart_code  FROM Department AS d where (lower(d.depart_name) Like ('%"
								+ Search + "%') OR  lower(d.depart_code) Like ('%" + Search + "%')) AND d.company_id = "+userDetails.getCompany_id()+" AND d.markForDelete = 0  ",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Department> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Department>();
			Department dropdown = null;
			for (Object[] result : results) {
				dropdown = new Department();

				dropdown.setDepart_id((Integer) result[0]);
				dropdown.setDepart_name((String) result[1]);
				dropdown.setDepart_code((String) result[2]);
				
				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}
	
	@Transactional
	public List<Department> SearchOnlyDepartmentNameMobile(String Search, int companyId) {
		
		TypedQuery<Object[]> queryt = entityManager
				.createQuery(
						"SELECT d.depart_id, d.depart_name, d.depart_code  FROM Department AS d where (lower(d.depart_name) Like ('%"
								+ Search + "%') OR  lower(d.depart_code) Like ('%" + Search + "%')) AND d.company_id = "+companyId+" AND d.markForDelete = 0  ",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Department> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Department>();
			Department dropdown = null;
			for (Object[] result : results) {
				dropdown = new Department();

				dropdown.setDepart_id((Integer) result[0]);
				dropdown.setDepart_name((String) result[1]);
				dropdown.setDepart_code((String) result[2]);
				
				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IDepartmentService#get_Issues_DepartmentByID_to_DepartmentName(java.lang.Integer)
	 */
	@Transactional
	public Department get_Issues_DepartmentByID_to_DepartmentName(Integer department_id) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Query query = entityManager.createQuery(
				"SELECT f.depart_id, f.depart_name from Department AS f where f.depart_id = :id AND f.company_id = "+userDetails.getCompany_id()+" AND f.markForDelete = 0");

		query.setParameter("id", department_id);
		Object[] branch = (Object[]) query.getSingleResult();

		Department select = new Department();
		select.setDepart_id((Integer) branch[0]);
		select.setDepart_name((String) branch[1]);
		return select;
	}
	
	@Override
	public ValueObject searhByDepartmentNameInMobile(ValueObject object) throws Exception {
		List<Department>   departmentList 				= null;
		List<Department>   department					= null;
		try {
			departmentList 	= new ArrayList<Department>();
			department 		= SearchOnlyDepartmentNameMobile(object.getString("term"), object.getInt("companyId"));
			
			if(department != null && !department.isEmpty()) {
				for(Department depar : department) {
					
					Department dto = new Department();
					dto.setDepart_id(depar.getDepart_id());
					dto.setDepart_name(depar.getDepart_name());
					dto.setDepart_code(depar.getDepart_code());
					
					departmentList.add(dto);
				}
			}

			object.put("DepartmentList", departmentList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			department 		 = null;
			departmentList 	 = null;
			object  	 	 = null;
		}
	}
   

}