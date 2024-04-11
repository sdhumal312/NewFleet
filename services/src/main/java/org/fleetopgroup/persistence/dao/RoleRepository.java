package org.fleetopgroup.persistence.dao;


import org.fleetopgroup.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

	@Override
	void delete(Role role);
	
	@Modifying
	@Query("UPDATE Role R set R.markForDelete = 1 where R.id = ?1 AND R.company_Id = ?2")
	void delete(Long Role_id, Integer companyId);
	
	@Query("From Role p where p.id = ?1 AND p.company_Id = ?2")
	Role getRoleByID(long id, Integer companyId);

	long count(); 
	
	@Query("From Role p where p.name = ?1 and p.company_Id = ?2 and p.markForDelete = 0")
	Role findByNameAndCompanyId(String name, Integer company_Id);
	
	

}
