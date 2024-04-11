package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.InventoryUpholsteryTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryUpholsteryTransferRepository extends JpaRepository<InventoryUpholsteryTransfer, Long>{
	
	@Query("SELECT BI.upholsteryTransferId From InventoryUpholsteryTransfer as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<InventoryUpholsteryTransfer> getDeployment_Page_UpholsteryTransfer(Integer companyId, Pageable pageable);
	
}