package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.RenewalSubType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RenewalSubTypeRepository extends JpaRepository<RenewalSubType, Integer> {

	@Modifying
	@Query("update RenewalSubType RST set  RST.renewal_SubType = ?1, RST.lastModifiedById = ?2, RST.lastModifiedOn = ?3, RST.renewal_id = ?6 where RST.renewal_Subid = ?4 AND RST.companyId IN (?5, 0)")
	void updateRenewalSubType( String renewal_SubType, Long modifiedBy, Timestamp modifiedOn, Integer renewal_Subid, Integer companyId, Integer renewal_id);
	
	@Modifying
	@Query("update RenewalSubType RST set  RST.renewal_SubType = ?1, RST.lastModifiedById = ?2, RST.lastModifiedOn = ?3, RST.renewal_id = ?6, RST.isMandatory = ?7 where RST.renewal_Subid = ?4 AND RST.companyId IN (?5, 0)")
	void updateRenewalSubTypeDetails( String renewal_SubType, Long modifiedBy, Timestamp modifiedOn, Integer renewal_Subid, Integer companyId, Integer renewal_id, boolean isMandatory);

	List<RenewalSubType> findAll(Sort sort);

	@Override
	void delete(RenewalSubType role);

	@Modifying
	@Query("update RenewalSubType RST set RST.markForDelete = 1 where RST.renewal_Subid = ?1 AND RST.companyId IN (?2, 0)")
	void deleteRenewalSubType(Integer renewal_Subid, Integer companyId);

	long count();

	@Query("From RenewalSubType RST where RST.renewal_SubType = ?1 and RST.companyId IN (?2, 0) and RST.markForDelete = 0")
	RenewalSubType getRenewalSubType(String verificationToken, Integer companyId);

	@Query("From RenewalSubType RST where RST.renewal_Subid = ?1 and RST.companyId IN (?2, 0) and RST.markForDelete = 0")
	RenewalSubType getRenewalSubTypeById(Integer renewal_Subid, Integer companyId);

	/*@Query("From RenewalSubType RST where RST.renewal_Type = ?1 AND RST.companyId = ?2 AND RST.markForDelete = 0")
	public List<RenewalSubType> listRenewalSubTypeSearch(String renewal_Type, Integer companyId) throws Exception;
*/
	@Query("From RenewalSubType RST where RST.renewal_id = ?1 AND RST.companyId IN (?2, 0) AND RST.markForDelete = 0")
	public List<RenewalSubType> listRenewalSubTypeSearch(Integer renewal_Type, Integer companyId) throws Exception;

	
	/*@Query("From RenewalSubType AS RST where RST.companyId = ?1 and RST.markForDelete = 0 ORDER BY RST.renewal_Type, RST.renewal_SubType ASC")
	List<RenewalSubType> findAll_VehicleMandatory_Renewal_Sub_Type(Integer companyId);
*/
	@Query("From RenewalSubType RST where RST.renewal_Subid = ?1 AND RST.companyId IN (?2, 0)")
	RenewalSubType getRenewalSubTypeByID(Integer renewal_Subid, Integer companyId);
	
	@Query("From RenewalSubType RST where RST.companyId IN (?1, 0) and RST.markForDelete = 0")
	List<RenewalSubType> findAllByCompanyId(Integer companyId);
	
	/*@Modifying
	@Query("update RenewalSubType RST set RST.renewal_Type = ?1 where RST.renewal_id = ?2 AND RST.companyId = ?3")
	void updateRenewalType(String renewal_type, Integer renewal_id, Integer companyId);
*/
	
	@Query("select count(RST.renewal_Subid) from RenewalSubType RST where RST.companyId IN (?1, 0) and RST.markForDelete = 0 AND RST.isMandatory = true ")
	public Long totalMandatorySubTypeRenewals(Integer companyId) throws Exception;
	
	@Query("From RenewalSubType RST where RST.renewal_id = ?1 AND RST.renewal_SubType = ?2 AND RST.renewal_Subid != ?3  AND RST.companyId IN (?4, 0) AND RST.markForDelete = 0")
	RenewalSubType getRenewalSubTypeDupliacte(Integer renewalId, String verificationToken, Integer renewalSubId, Integer companyId);
}
