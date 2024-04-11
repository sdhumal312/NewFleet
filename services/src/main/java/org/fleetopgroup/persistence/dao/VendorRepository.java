package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {


	public List<Vendor> findAll();

	@Query("FROM Vendor VT where VT.vendorId = ?1 AND VT.companyId = ?2 AND VT.markForDelete = 0")
	public Vendor getVendor(int vendorId, Integer companyId);

	@Modifying
	@Query("UPDATE Vendor SET markForDelete = 1 WHERE vendorId = ?1 AND companyId = ?2")
	public void deleteVendor(Integer vendor, Integer companyId);

	@Query("from Vendor where vendorName= ?1 AND companyId = ?2 AND markForDelete = 0 AND autoVendor = 0")
	public List<Vendor> getVendorNametoall(String vendorname, Integer companyId);


	@Query("from Vendor where vendorName= ?1 ")
	public List<Vendor> GetAllVendorListToFuelImportVendorID(String importVendorName) throws Exception;

	@Query("SELECT p.vendorId From Vendor as p where p.vendorTypeId= ?1 AND p.companyId = ?2 AND p.markForDelete = 0 AND p.autoVendor = 0")
	public Page<Vendor> getDeployment_Page_Vendor(Long vendorTypeId, Integer companyId, Pageable pageable);
	
	@Query("from Vendor where companyId = ?1 AND markForDelete = 0 ")
	public List<Vendor> GetAllVendorListByCompanyId(Integer companyId) throws Exception;
	
	@Query("Select count(v) FROM Vendor v where v.companyId = ?1 AND v.markForDelete = 0 ")
	public long countVendorByID(Integer companyId) throws Exception;
	
	@Query("From Vendor V where V.vendorName =?1 AND V.companyId = ?2 AND V.markForDelete = 0 ")
	public Vendor findByName(String vendorName , Integer companyId) throws Exception;
	
	@Query("From Vendor V where V.vendorId =?1 AND V.companyId = ?2 AND V.markForDelete = 0 ")
	public Vendor findById(Integer vendorId , Integer companyId) throws Exception;
	
	@Query("From Vendor V where V.vendorName =?1 AND V.companyId = ?2 AND V.markForDelete = 0 ")
	public List<Vendor> findByNameList(String vendorName , Integer companyId) throws Exception;

	@Query("From Vendor V where V.companyId =?1 AND V.markForDelete = 0 ")
	public List<Vendor> findByCompanyId(Integer companyId) throws Exception;

	@Query("from Vendor where vendorPanNO= ?1 AND companyId = ?2 AND markForDelete = 0 AND autoVendor = 0")
	public List<Vendor> getVendorPanNo(String vendorPanNo, Integer companyId);
	
	@Query("From Vendor V where V.vendorName =?1 AND V.companyId = ?2 AND V.markForDelete = 0 ")
	public Vendor getVendorByName(String vendorName , Integer companyId) throws Exception;
	
}
