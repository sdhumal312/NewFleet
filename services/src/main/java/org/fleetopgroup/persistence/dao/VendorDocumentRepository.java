package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.VendorDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VendorDocumentRepository extends JpaRepository<VendorDocument, Long> {

	/**
	 * @param vENDOR_ID
	 * @return
	 */
	@Query("FROM VendorDocument VT where VT.VENDORID = ?1 AND VT.COMPANY_ID = ?2 AND VT.markForDelete = 0")
	public List<VendorDocument> list_VendorDocument_IN_VendorId(Integer vENDOR_ID, Integer companyId);

	/**
	 * @param driver_documentid
	 * @return
	 */
	@Query("FROM VendorDocument VT where VT.VDID = ?1 AND VT.COMPANY_ID = ?2")
	public VendorDocument getVendorDocuemnt(Long vDID, Integer companyId);

	/**
	 * @param vDID
	 */
	@Modifying
	@Query("UPDATE VendorDocument SET markForDelete = 1 WHERE VDID = ?1 AND COMPANY_ID = ?2")
	public void deleteVendorDocument(Long vDID, Integer companyId);

	@Query("SELECT MAX(VDID) FROM VendorDocument")
	public long getVendorDocumentMaxId() throws Exception;
	
	@Query("FROM VendorDocument where VDID > ?1 AND VDID <= ?2")
	public List<VendorDocument> getVendorDocumentList(Long startLimit, Long endLimit) throws Exception;	
}
