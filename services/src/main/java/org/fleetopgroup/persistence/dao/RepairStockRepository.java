package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.RepairStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RepairStockRepository  extends JpaRepository<RepairStock, Long>{

	@Transactional
	@Modifying
	@Query("UPDATE RepairStock SET  sentRemark = ?1 , repairStatusId = 2  where repairStockId =?2 AND companyId = ?3")
	void updatSentToRepair(String string, long long1,int companyId);

	@Query("select RS.repairStockId FROM RepairStock as RS Where RS.companyId = ?1 AND RS.markForDelete = 0 ORDER BY RS.repairStockId DESC ")
	public Page<RepairStock> getDeploymentPageRepairStock(Integer comapanyId, Pageable pageable);

	@Query("select RS.repairStockId FROM RepairStock as RS Where  RS.repairStatusId =?1 AND  RS.companyId = ?2 AND RS.markForDelete = 0 ORDER BY RS.repairStockId DESC ")
	public Page<RepairStock> getDeploymentPageRepairStock(short statusId, Integer comapanyId, Pageable pageable);

	@Query("FROM RepairStock  Where repairStockId =?1 AND companyId = ?2 AND markForDelete = 0 ")
	public RepairStock getRepairInvoiceByRepairStockId(Long repairStockId, Integer companyId);

	@Transactional
	@Modifying
	@Query("UPDATE RepairStock SET markForDelete = 1 where repairStockId =?1 AND companyId = ?2")
	public void deleteRepairInvoice(long repairStockId, int companyId);

	@Transactional
	@Modifying
	@Query("UPDATE RepairStock SET labourDiscountTaxTypeId = ?1, partDiscountTaxTypeId = ?1 where repairStockId =?2 AND companyId = ?3 ")
	public void updateDiscountTaxTypeId(short defaultDisTaxTypeId, long repairStockId, int companyId);

	
	@Transactional
	@Modifying
	@Query("UPDATE RepairStock SET partDiscountTaxTypeId = ?1  where repairStockId =?2 AND companyId = ?3 ")
	public void updatePartDiscountTaxTypeId(short defaultDisTaxTypeId, long repairStockId, int companyId);
	
	@Transactional
	@Modifying
	@Query("UPDATE RepairStock SET labourDiscountTaxTypeId = ?1  where repairStockId =?2 AND companyId = ?3 ")
	public void updateLabourDiscountTaxTypeId(short defaultDisTaxTypeId, long repairStockId, int companyId);

	@Transactional
	@Modifying
	@Query("UPDATE RepairStock SET dateOfSent = null, sentRemark = null , repairStatusId = 1  where repairStockId =?1 AND companyId = ?2")
	public void updatStatusToCreated(long repairStockId, int companyId);
	
	@Transactional
	@Modifying
	@Query("UPDATE RepairStock SET completedDate = ?1, receivedRemark = ?2, repairStatusId = 3  where repairStockId =?3 AND companyId = ?4")
	public void updatStatusToCompleted(Date parse, String string, long repairStockId, int companyId);
	
	@Transactional
	@Modifying
	@Query("UPDATE RepairStock SET completedDate = null, receivedRemark = null, repairStatusId = 2  where repairStockId =?1 AND companyId = ?2")
	public void updatStatusToSentToRepair(long repairStockId, int companyId);
	
	@Query("FROM RepairStock Where repairNumber =?1 AND companyId = ?2 AND markForDelete = 0 ")
	public RepairStock searchRepairByNumber(long repairNumber, int companyId);

	
}
