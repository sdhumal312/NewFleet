package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.FuelInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelInvoiceRepository extends JpaRepository<FuelInvoice, Long>{

	@Modifying
	@Query("Update FuelInvoice set documentId = ?1  where fuelInvoiceId= ?2")
	public void updateDocumentId(Long documentId, Long fuelInvoiceId) throws Exception ;
	
	@Query("SELECT BI.fuelInvoiceId From FuelInvoice as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<FuelInvoice> getDeploymentFuelInvoice(Integer companyId, Pageable pageable);
	
	@Modifying
	@Query("Update FuelInvoice set markForDelete = 1  where fuelInvoiceId= ?1")
	public void deleteFuelInvoice(Long fuelInvoiceId) throws Exception ;
	
	@Query("FROM FuelInvoice where fuelInvoiceId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public FuelInvoice getFuelInvoiceById(Long fuelInvoiceId, Integer companyId) throws Exception;
	
	@Query(nativeQuery = true, value = "SELECT * FROM FuelInvoice where petrolPumpId = ?1 AND companyId = ?2 AND balanceStock > 0.01 AND markForDelete = 0 ORDER BY fuelInvoiceId ASC LIMIT 1")
	public FuelInvoice getFuelInvoiceBalanceStockDetails(Integer vendorId, Integer companyId) throws Exception;
	
	
	@Query("select sum(balanceStock) FROM FuelInvoice where petrolPumpId = ?1 AND companyId = ?2 AND balanceStock > 0.01 AND markForDelete = 0 ")
	public Double petrolPumpWiseBalanceStock(Integer vendorId, Integer companyId)throws Exception;
	
	@Query(nativeQuery = true, value = "SELECT * FROM FuelInvoice where petrolPumpId = ?1 AND companyId = ?2 AND balanceStock > 0.01 AND markForDelete = 0 ORDER BY fuelInvoiceId ASC ")
	public List<FuelInvoice> getFuelInvoiceListByBalanceStock(Integer vendorId, Integer companyId) throws Exception;
	

}
