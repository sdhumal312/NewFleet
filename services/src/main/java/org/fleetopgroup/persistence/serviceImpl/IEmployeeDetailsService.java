/**
 * 
 */
package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.EmployeeDetails;

/**
 * @author fleetop
 *
 */
public interface IEmployeeDetailsService {

	public void add_EmployeeDetails(EmployeeDetails empDetails) throws Exception;
	
	public List<EmployeeDetails> validate_EmployeeDetails(String employeeID, String EmailID) throws Exception;
	
}
