package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

    @Override
    void delete(User user);

	User findById(long id);
	
	@Query("SELECT u From User u "
			+ " INNER JOIN Company co ON co.company_id = u.company_id"
			+ " where u.email = ?1 and co.companyCode = ?2")
     User findUser(String username, String domain);
	
	@Query("From User u where u.company_id = ?1")
	List<User>  getUserList(Integer companyId);
	
	
	@Query("SELECT u From User u "
			+ " INNER JOIN Company co ON co.company_id = u.company_id"
			+ " where u.firstName = ?1 and co.company_id = ?2 ")
     User findUserByFirstName(String username, Integer companyId);
	
	@Query("SELECT u.id ,u.firstName ,u.lastName,u.email From User u "
			+ " INNER JOIN Company co ON co.company_id = u.company_id"
			+ " where u.email = ?1 and u.company_id = ?2")
     User findUserbyEmailId(String username, Integer companyId);
	
	@Query("SELECT U FROM User U WHERE U.firstName = ?1 AND U.company_id=?2")
	User  findUserByUserNameAndCompanyId(String username, Integer companyId);
}
