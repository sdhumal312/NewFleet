package org.fleetopgroup.persistence.serviceImpl;

import java.util.Collection;
import java.util.List;

import org.fleetopgroup.persistence.dto.RoleCountDto;
import org.fleetopgroup.persistence.model.Privilege;
import org.fleetopgroup.persistence.model.Role;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.web.util.ValueObject;


public interface IRoleService {

    Role registerNewRoleAccount(final String name, final Collection<Privilege> privileges, Integer company_Id, String createdBy) throws Exception;

    
    Role UpdateRoleAccount(final Role role, final Collection<Privilege> privileges, Integer company_Id, String createdBy) throws Exception;

    Role getRole(String verificationToken, Integer companyId) throws Exception;

    void saveRegisteredRole(Role Role);

    void deleteRole(Long Role_id, Integer companyId);
    
    Role findRole(Long id);
    
    Role findByName(String name);

    void createVerificationTokenForRole(Role Role, String token);

    Role getRoleByID(long id);

    List<Role> getRoleByID_Privilage(long id);
    
    List<Role> findAll();
    List<Role> findAllRolesOfCompany(Integer company_id) throws Exception;
    
	long count();

	/**
	 * @return
	 */
	List<RoleCountDto> Find_ALL_ROLE_AND_COUNT_USER_ASSIGN();


	public Role Find_Role_Master_Company(String name, Integer Company_ID);


	List<UserProfile> findUserCount(Integer company_id) throws Exception;


	public ValueObject getRolePermissionUserInfoList(ValueObject object,Integer company_id) throws Exception;


	/*List<UserProfile> getRolePermissionUserInfoList(Integer company_id) throws Exception;*/


	
   
}
