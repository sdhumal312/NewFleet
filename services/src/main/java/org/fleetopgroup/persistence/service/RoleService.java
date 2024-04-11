package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.RoleRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RoleCountDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.Privilege;
import org.fleetopgroup.persistence.model.Role;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.persistence.serviceImpl.IRoleService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleService implements IRoleService {

	@Autowired
	private RoleRepository roleRepository;

	@PersistenceContext
	EntityManager entityManager;

	/**
	 * @param em
	 */
	/*
	 * public RoleService(EntityManager em) { this.entityManager=em; }
	 */

	@Transactional
	public Role findRole(Long id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		/*
		 * Role parent = this.entityManager.find(Role.class, id);
		 * parent.getPrivileges().size(); return parent;
		 */
		return roleRepository.getRoleByID(id, userDetails.getCompany_id());
	}

	@Transactional
	public Role registerNewRoleAccount(final String name, final Collection<Privilege> privileges, Integer company_Id,
			String createdBy) throws Exception {

		Role role = roleRepository.findByNameAndCompanyId(name, company_Id);
		if (role == null) {
			role = new Role(name);
			role.setCompany_Id(company_Id);
			role.setCreatedBy(createdBy);
			role.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			role.setPrivileges(privileges);
			
			roleRepository.save(role);
		}
		return role;
	}

	public Role UpdateRoleAccount(final Role role, final Collection<Privilege> privileges, Integer company_Id,
			String lastModifiedBy) throws Exception {

		// Role roleupdate = oleRepository.findByName(role.getName());
		Role roleupdate = new Role(role.getId(), role.getName());
		{
			roleupdate.setPrivileges(privileges);
			roleupdate.setCompany_Id(company_Id);
			roleupdate.setLastModifiedBy(lastModifiedBy);
			roleupdate.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));

			entityManager.merge(roleupdate);
			// roleRepository.save(role);
		}
		return role;
	}

	@Transactional
	public Role getRole(String verificationToken, Integer companyId) throws Exception {
		return roleRepository.findByNameAndCompanyId(verificationToken, companyId);
	}

	@Transactional
	public void saveRegisteredRole(Role Role) {

	}

	@Transactional
	public void deleteRole(Long Role_id, Integer companyId) {
		roleRepository.delete(Role_id, companyId);
	}

	@Transactional
	public void createVerificationTokenForRole(Role Role, String token) {

	}

	@Transactional
	public Role getRoleByID(long id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return roleRepository.getRoleByID(id, userDetails.getCompany_id());
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Role> getRoleByID_Privilage(long id) {

		String hql = "select c1 from Role c1 join c1.privileges c2 where c2.id in :dealerships OR c1.id in :dealerships group by c1 ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("dealerships", id);
		return (List<Role>) query.getResultList();

		// return (List<Object>) roleRepository.getRoleByID_Privilage(id);

	}

	@Transactional
	public List<Role> findAll() {

		TypedQuery<Role> query = entityManager.createQuery("FROM Role", Role.class);
		query.setFirstResult(4);
		query.setMaxResults(200);
		return query.getResultList();
	}

	@Transactional
	public long count() {

		return roleRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRoleService#findByName(java.lang.
	 * String)
	 */
	@Transactional
	public Role findByName(String name) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return roleRepository.findByNameAndCompanyId(name, userDetails.getCompany_id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRoleService#
	 * Find_ALL_ROLE_AND_COUNT_USER_ASSIGN()
	 */
	@Transactional
	public List<RoleCountDto> Find_ALL_ROLE_AND_COUNT_USER_ASSIGN() {
		// Note Show All Role name and Role Count

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.id , R.name, COUNT(U.id) AS count_user FROM User U "
				+ " LEFT JOIN Role R ON U.id = R.id GROUP BY R ",
				Object[].class);
		/*
		 * queryt.setFirstResult(1); queryt.setMaxResults(200);
		 */
		List<Object[]> results = queryt.getResultList();

		List<RoleCountDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RoleCountDto>();
			RoleCountDto rolecount = null;
			for (Object[] result : results) {
				rolecount = new RoleCountDto();

				rolecount.setId((Long) result[0]);
				rolecount.setName((String) result[1]);
				rolecount.setCount_user((Long) result[2]);

				Dtos.add(rolecount);
			}
		}
		return Dtos;
	}

	@Override
	public List<Role> findAllRolesOfCompany(Integer company_id) throws Exception {
				
		/*TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT COUNT(UR.role_id) , U.id , U.email , U.firstName ,U.lastName, UR.role_id,R.id , R.name FROM User as U "+		
				" INNER JOIN users_roles as UR  on UR.user_id = U.id " +
				" INNER JOIN Role as R on R.id = UR.role_id " +
				" WHERE R.company_id= " + company_id + " and R.markForDelete = 0 "+
				" GROUP BY UR.role_id ",
				Object[].class);
		 		*/

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.id , R.name FROM Role R WHERE R.company_Id = " + company_id + " and R.markForDelete = 0 ",				
				Object[].class);
		
		
		
		/*
		 * queryt.setFirstResult(1); queryt.setMaxResults(200);
		 */
		List<Object[]> results = queryt.getResultList();

		List<Role> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Role>();
			Role rolecount = null;
			for (Object[] result : results) {
				rolecount = new Role();

				rolecount.setId((Long) result[0]);
				rolecount.setName((String) result[1]);			
				//rolecount.setCounter((Int)result[2]);
				Dtos.add(rolecount);
			}
		}
		return Dtos;
	}
	
	
	//Count logic By Dev Y Start 
	
	@Override
	public List<UserProfile> findUserCount(Integer companyId) throws Exception {
		
		
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT  count(UP.user_id), UP.role_id,R.company_Id from UserProfile AS UP " + 	
				" INNER JOIN Role AS R ON R.id = UP.role_id " +
				" WHERE R.company_Id= " + companyId + " and R.markForDelete = 0 and lower(UP.firstName) <> 'admin' "+
				" and UP.markForDelete = 0"+
				" GROUP BY role_id ",
				Object[].class);	
		
		//queryt.setFirstResult(1); queryt.setMaxResults(200);		 
		List<Object[]> results = queryt.getResultList();
		
		
		List<UserProfile> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<UserProfile>();
			UserProfile count = null;
			for (Object[] result : results) {
				count = new UserProfile();				
				count.setUser_id((Long) result[0]);
				count.setRole_id((Long) result[1]);
				count.setCompany_id((Integer) result[2]);//Doubtful				
				Dtos.add(count);
			}
		}
		
		return Dtos;
	}
	//Count logic By Dev Y End 


	@Override
	@Transactional
	public Role Find_Role_Master_Company(String name, Integer Company_ID) {
		
		return roleRepository.findByNameAndCompanyId(name, Company_ID);
	}
	
	
	//UI Adjustment for User Details   Role Permission By Dev Y Start
	
	
	@Override
	public ValueObject getRolePermissionUserInfoList(ValueObject object, Integer  company_id) throws Exception {
		ValueObject    					valOutObject			= null;//y
		Integer 						ROUTE_ID				= 0;
		ROUTE_ID		= object.getInt("roleId",0);
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				" SELECT UP.user_id , U.firstName, UP.mobile_number, U.lastName, UP.personal_email, UP.role_id from UserProfile as UP " + 
				" INNER JOIN User as U on U.id = UP.user_id "+
				" WHERE UP.company_id= " + company_id + " and UP.role_id= "+ ROUTE_ID + " and UP.markForDelete = 0 ",
				Object[].class);
		
		
		//queryt.setFirstResult(1); queryt.setMaxResults(200);		 
		List<Object[]> results = queryt.getResultList();
		
		
		List<UserProfileDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<UserProfileDto>();
			UserProfileDto count = null;
			for (Object[] result : results) {
				count = new UserProfileDto();	
				count.setUser_id((Long) result[0]);			
				count.setFirstName((String) result[1]);				
				count.setMobile_number((String) result[2]);				
				count.setLastName((String) result[3]);				
				count.setPersonal_email((String) result[4]);
				if(!count.getFirstName().equalsIgnoreCase("admin")) {
					Dtos.add(count);
				}
			}
		}
		
		valOutObject	= new ValueObject();//y	
		valOutObject.put("RoleUserInfo", Dtos);//y	
		
		
		return	valOutObject;
		
	}

	//UI Adjustment for User Details   Role Permission By Dev Y Start

}