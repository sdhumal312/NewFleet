/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

	//public void add_EmployeeDetails(EmployeeDetails empDetails) throws Exception;

	@Query("FROM EmployeeDetails WHERE EMP_NUMBER=?1 AND EMP_EMAIL=?2 ")
	public List<EmployeeDetails> validate_EmployeeDetails(String employeeID, String EmailID) throws Exception;

}
