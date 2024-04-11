package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VehicleCheckingDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleCheckingDetailsRepository extends JpaRepository<VehicleCheckingDetails, Long>{

	@Query(" SELECT v.checkingId FROM VehicleCheckingDetails as v  "
			+ " Where v.companyId =?1 AND v.markForDelete = 0 ORDER BY v.checkingId desc ")
	public Page<VehicleCheckingDetails> getDeployment_Page_Checking(Integer companyId, Pageable pageable);

	

	@Query(" SELECT VC.checkingId FROM VehicleCheckingDetails as VC "
			+ " INNER JOIN Vehicle AS V ON V.vid = VC.vid"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?2 "
			+ " Where VC.companyId =?1 AND VC.markForDelete = 0 ORDER BY VC.checkingId desc ")
	public Page<VehicleCheckingDetails> getDeployment_Page_Checking(Integer companyId, Long id, Pageable pageable);

}
