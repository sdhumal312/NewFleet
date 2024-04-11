package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.ClothInventoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClothInventoryDetailsRepository extends JpaRepository<ClothInventoryDetails, Long>{

	@Modifying
	@Query("UPDATE ClothInventoryDetails SET markForDelete = 1 where  clothInventoryDetailsId = ?1")
	public void deleteClothInventoryDetails(Long clothInventoryDetailsId) throws Exception;
	
	@Query("FROM ClothInventoryDetails where clothInvoiceId = ?1 AND markForDelete = 0")
	public List<ClothInventoryDetails> getClothInventoryDetailsList(Long	clothInvoiceId) throws Exception;
	
	@Query("SELECT I FROM ClothInventoryDetails as I INNER JOIN ClothInvoice as C ON I.clothInvoiceId=C.clothInvoiceId"
			+ " where I.wareHouseLocation = ?2 AND I.clothTypesId =?1 AND C.clothTypeId=?3 AND I.quantity > 0.0 AND I.markForDelete = 0")
	public List<ClothInventoryDetails> getClothInventoryList(Long clothTypesId ,int location,short stockType) throws Exception;
	
//	@Modifying
//	@Query("UPDATE ClothInventoryDetails SET markForDelete = 1 where  clothInventoryDetailsId = ?1")
//	public void updateQuantity(Long clothInventoryDetailsId) throws Exception;
//	
//	@Modifying
//	@Query("UPDATE ClothInventoryDetails SET markForDelete = 1 where  clothInventoryDetailsId = ?1")
//	public void updateTransferQuantity(Long clothInventoryDetailsId) throws Exception;
}
