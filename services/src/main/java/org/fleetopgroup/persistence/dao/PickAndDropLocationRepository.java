package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.PickAndDropLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PickAndDropLocationRepository extends JpaRepository<PickAndDropLocation, Long> {

	@Query("From PickAndDropLocation PDL where PDL.companyId = ?1 and PDL.markForDelete = 0 ")
	List<PickAndDropLocation> findAllByCompanyId(Integer companyId);

	@Query("From PickAndDropLocation PDL where PDL.locationName =?1 AND PDL.companyId = ?2 AND PDL.markForDelete = 0 ")
	public PickAndDropLocation findByName(String locationName, Integer companyId);
	
	@Query("From PickAndDropLocation PDL where PDL.pickAndDropLocationId = ?1 AND PDL.companyId = ?2 AND PDL.markForDelete = 0 ")
	public PickAndDropLocation findByPickAndDropLocationId(Long pickAndDropLocationId , Integer companyId);

	@Modifying
	@Query("UPDATE PickAndDropLocation PDL SET PDL.locationName =?2, PDL.description =?3 where PDL.pickAndDropLocationId = ?1 AND PDL.companyId = ?4 AND PDL.markForDelete = 0 ")
	public void updatePickAndDropLocationById(Long pickAndDropLocationId, String TyreExpense_Name, String description, Integer company_id);
	
	@Modifying
	@Query("UPDATE PickAndDropLocation PDL SET PDL.markForDelete = 1  where PDL.pickAndDropLocationId = ?1 AND PDL.companyId = ?2 ")
	public void deleteTyreExpenseByExpenseId(Long pickAndDropLocationId,  Integer company_id);
}

