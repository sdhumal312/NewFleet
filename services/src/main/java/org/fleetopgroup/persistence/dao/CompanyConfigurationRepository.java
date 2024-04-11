package org.fleetopgroup.persistence.dao;

import java.sql.Date;
import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.CompanyConfiguration;
import org.fleetopgroup.persistence.model.TripSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompanyConfigurationRepository extends JpaRepository<CompanyConfiguration, Long> {
	
	@Query("FROM CompanyConfiguration CF WHERE CF.moduleId = ?2 AND CF.companyId = ?1 AND CF.markForDelete = 0")
	public List<CompanyConfiguration> getConfigurationByCompanyIdAndModuleId(Integer companyId, Integer filter);

	@Modifying
	@Query("UPDATE CompanyConfiguration SET value = ?2 WHERE companyConfigurationId= ?1 AND moduleId = ?3 AND companyId =?4 AND markForDelete= 0")
	public void updateCompanyConfigurationPropertyValue(Long configId, String value, Integer moduleId, Integer companyId);

	@Query("FROM CompanyConfiguration  WHERE moduleId = ?1 AND  companyId = ?2  AND name =?3 AND markForDelete= 0")
	public CompanyConfiguration companyConfigurationAlreadyExits(Integer moduleId, Integer compnayId, String string);
	
}
