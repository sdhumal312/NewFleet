package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.CompanyMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyMapperRepository extends JpaRepository<CompanyMapper, Long> {
	
	@Query("FROM CompanyMapper where  ivGroupId = ?1 AND markForDelete = 0")
	public CompanyMapper findByAccountGroupId(Integer groupId) throws  Exception;
	
	@Query("FROM CompanyMapper where fleetCompanyId = ?1 AND markForDelete = 0")
	public CompanyMapper findGroupIdByFleetCompanyId(Integer companyId) throws  Exception;

	@Query("FROM CompanyMapper where ivGroupId = ?1 AND markForDelete = 0")
	public CompanyMapper getFleetopCompanyIdByIvGroupId(Integer companyId) throws  Exception;

}
