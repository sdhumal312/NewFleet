package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.PartMeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PartMeasurementUnitRepository extends JpaRepository<PartMeasurementUnit, Integer> {

	@Modifying
	@Query("update PartMeasurementUnit T SET T.pmuName = ?1, T.pmuSymbol = ?2, T.pmudescription = ?3, T.needConversion = ?5, T.convertTo = ?6, T.conversionRate= ?7  where T.pmuid = ?4")
	public void updatePartMeasurementUnit(String pmuName, String pmuSymbol, String pmudescription, Integer pmuid, 
			boolean needConversion, Integer converTo, Double converionRate)
			throws Exception;
	@Override
	@Query("From PartMeasurementUnit p where p.markForDelete = 0")
	public List<PartMeasurementUnit> findAll();

	@Query("From PartMeasurementUnit p where p.pmuid = ?1 AND p.markForDelete = 0")
	public PartMeasurementUnit getPartMeasurementUnit(int sid) throws Exception;

	@Modifying
	@Query("UPDATE PartMeasurementUnit p SET p.markForDelete = 1 where p.pmuid = ?1")
	public void deletePartMeasurementUnit(Integer status) throws Exception;

	@Query("From PartMeasurementUnit p where p.pmuName = ?1 AND p.markForDelete = 0")
	public PartMeasurementUnit ValidatePartMeasurementUnit(String pmuName) throws Exception;
	
}