package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.PartLocations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface PartLocationsRepository  extends JpaRepository<PartLocations, Integer> {
	
	
	public List<PartLocations> findAll();

	@Query("From PartLocations p where p.partlocation_id = ?1 AND p.partLocationType = 1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.isAutoCreated = 0")
	public PartLocations getPartLocations(int sid, Integer companyId) ;

	@Modifying
	@Query("UPDATE PartLocations T set T.markForDelete = 1 where T.partlocation_id = ?1 and T.companyId = ?2 ")
	public void deletePartLocations(Integer partlocID, Integer companyId);
	
	@Query("From PartLocations p where p.partlocation_id = ?1 and  p.partLocationType = 1  and p.companyId = ?2 and p.markForDelete = 0 AND p.isAutoCreated = 0")
	public PartLocations validatePartLocations(Integer locationName, Integer companyId);
	
	
	@Query("SELECT p From PartLocations p "
			+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = p.partlocation_id AND PLP.user_Id = ?2"
			+ " where p.companyId = ?1 and p.partLocationType = 1 and p.markForDelete = 0 AND p.isAutoCreated = 0")
	public List<PartLocations> listPartLocationsByCompanyId(Integer companyId, Long id);
	
	@Query("From PartLocations p where p.companyId = ?1 and p.partLocationType = 1 and p.markForDelete = 0 AND p.isAutoCreated = 0")
	public List<PartLocations> listPartLocationsByCompanyId(Integer companyId);
	
	@Query("From PartLocations p where p.companyId = ?1 and p.partLocationType = 1 and p.markForDelete = 0 ")
	public List<PartLocations> PartLocationsByCompanyId(Integer companyId);
	
	
	@Query("From PartLocations p where p.partlocation_name = ?1 and p.companyId = ?2 and p.markForDelete = 0 AND p.isAutoCreated = 0")
	public PartLocations validatePartLocations(String locationName, Integer companyId);

	@Query("SELECT p From PartLocations p "
			+ " INNER JOIN PartLocationPermission PLP ON PLP.partLocationId = p.partlocation_id AND PLP.user_Id = ?3"
			+ " where p.companyId = ?2 and p.partLocationType = ?1 and p.markForDelete = 0 AND p.isAutoCreated = 0")
	public List<PartLocations> getPartLocationListByType(short partLocationType, Integer companyId, long id);
	
	@Query("From PartLocations p where  p.partLocationType = ?1 and p.companyId = ?2  and p.markForDelete = 0 AND p.isAutoCreated = 0")
	public List<PartLocations> getPartLocationListByType(short partLocationType, Integer companyId);

	@Query("From PartLocations p where  p.mainPartLocationId = ?1 and p.companyId = ?2 and p.partLocationType = 2 and p.markForDelete = 0 ")
	public List<PartLocations> getSubLocationByMainLocationId(int mainPartLocationId, Integer companyId);
	
	@Query("From PartLocations p where p.partlocation_id = ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.isAutoCreated = 0")
	public PartLocations validatePartLocation(int sid, Integer companyId) ;// for delete 

	@Query("From PartLocations p where p.companyId = ?1 AND p.markForDelete = 0 AND p.isAutoCreated = 0")//partLocationViewList
	public List<PartLocations> listOfAllTypePartLocation(Integer companyId);

	/*@Query("From PartLocations p where p.companyId = ?1 and p.markForDelete = 0 AND p.isAutoCreated = 0")//partLocationViewList
	public List<PartLocations> listOfAllTypePartLocation(Integer companyId);*/
}