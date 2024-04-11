package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VendorType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorTypeRepository extends JpaRepository<VendorType, Long> {

	@Modifying
	@Query("update VendorType VT set VT.vendor_TypeName = ?1 where VT.vendor_Typeid = ?2")
	void updateVendorType(String vendor_TypeName, long vendor_Typeid);

	List<VendorType> findAll(Sort sort);

	@Override
	void delete(VendorType role);

	@Modifying
	@Query("UPDATE VendorType VT SET VT.markForDelete = 1 where VT.vendor_Typeid = ?1 and VT.companyId = ?2")
	void deleteVendorType(long vendor_Typeid, Integer companyId);

	
	@Query("FROM VendorType VT where VT.vendor_Typeid = ?1 AND markForDelete = 0")
	VendorType getVendorTypeByID(Long vendor_Typeid);
	
	long count();

	@Query("FROM VendorType VT where VT.vendor_TypeName = ?1 and VT.companyId = ?2 and VT.markForDelete = 0")
	VendorType getVendorType(String verificationToken, Integer companyId);
	
	@Query("FROM VendorType VT where VT.companyId = ?1 and VT.isCommonMaster = ?2 and VT.markForDelete = 0")
	List<VendorType> findAllByCompanyId(Integer companyId, Boolean isCommonMaster) throws Exception;
	
	@Query(nativeQuery = true, value = "SELECT * FROM vendortype VT where VT.companyId = ?1 AND VT.markForDelete = 0 ORDER BY VT.vendor_Typeid ASC LIMIT 1")
	VendorType	getTopVendor(Integer companyId) throws Exception;
	
	@Query("SELECT COUNT(VT) FROM VendorType VT where VT.companyId = ?1 and VT.markForDelete = 0")
	public long countByCompanyId(Integer companyId) throws Exception ;

}
