package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.BranchMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BranchMapperRepository extends CrudRepository<BranchMapper, Long> {
	
	@Query("FROM BranchMapper where ivBranchId = ?1 AND markForDelete = 0")
	public BranchMapper findBranchMapperByIvBranchId(Long ivBranchId) throws Exception;

}
