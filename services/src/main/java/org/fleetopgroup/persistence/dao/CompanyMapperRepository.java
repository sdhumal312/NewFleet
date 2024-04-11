
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.CompanyMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyMapperRepository extends JpaRepository<CompanyMapper, Integer> {
	
	@Query("FROM CompanyMapper WHERE ivGroupId =?1 AND markForDelete = 0")
	public CompanyMapper getFleetopCompanyIdByIvGroupId(int ivGroupId);
	
	@Query("FROM CompanyMapper where  ivGroupId = ?1 AND markForDelete = 0")
	public CompanyMapper findByAccountGroupId(Integer groupId) throws  Exception;

	
	@Query("FROM CompanyMapper where fleetCompanyId = ?1 AND markForDelete = 0")
	public CompanyMapper findGroupIdByFleetCompanyId(Integer companyId) throws  Exception;
	

}
