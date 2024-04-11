package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.ClothInventoryStockTypeDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClothInventoryStockTypeDetailsRepository extends JpaRepository<ClothInventoryStockTypeDetails, Long>{

	@Query("FROM ClothInventoryStockTypeDetails where clothTypesId = ?1 AND wareHouseLocationId = ?2 AND markForDelete = 0 ")
	public ClothInventoryStockTypeDetails validateClothInventoryStockTypeDetails(Long clothTypeId, Integer location) throws Exception;
	
	@Query("FROM ClothInventoryStockTypeDetails where clothInventoryStockTypeDetailsId = ?1 AND  markForDelete = 0 ")
	public ClothInventoryStockTypeDetails getClothInventoryStockTypeDetails(Long clothStockDetailsId) throws Exception;
	
	@Query("FROM ClothInventoryStockTypeDetails cs where  cs.wareHouseLocationId = ?1 AND cs.markForDelete = 0 ")
	public Page<ClothInventoryStockTypeDetails> getDeploymentLog_Location(Integer location, Pageable pageable) throws Exception;
	
	@Modifying
	@Query("UPDATE from ClothInventoryStockTypeDetails AS v SET v.usedStockQuantity = v.usedStockQuantity - ?2, v.inServiceQuantity = inServiceQuantity + ?2"
			+ " Where v.wareHouseLocationId = ?1 AND v.clothTypesId = ?3")
	public void updateUsedStockDetails(Integer locationId, Double quantity, Long clothTypesId);
	
	@Modifying
	@Query("UPDATE from ClothInventoryStockTypeDetails AS v SET v.newStockQuantity = v.newStockQuantity - ?2, v.inServiceQuantity = inServiceQuantity + ?2 "
			+ "  Where v.wareHouseLocationId = ?1 AND v.clothTypesId = ?3")
	public void updateNewStockDetails(Integer locationId, Double quantity, Long clothTypesId);
}
