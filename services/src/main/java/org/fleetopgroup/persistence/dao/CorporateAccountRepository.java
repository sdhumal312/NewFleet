package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.CorporateAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CorporateAccountRepository extends JpaRepository<CorporateAccount, Long>{

	@Query("FROM CorporateAccount where corporateName like %?1% AND companyId = ?2 and markForDelete = 0 ")
	public List<CorporateAccount> getPartyListByName(String term, Integer companyId) throws Exception ;
	
	@Query("FROM CorporateAccount where corporateName = ?1 AND companyId = ?2 and markForDelete = 0 ")
	public CorporateAccount findByName(String corporateName, Integer companyId)throws Exception ;

	@Query("FROM CorporateAccount where companyId = ?1 and markForDelete = 0 ")
	public List<CorporateAccount> findByCmpanyId(Integer company_id)throws Exception ;

	@Query("FROM CorporateAccount where corporateAccountId = ?1 AND companyId =?2 and markForDelete = 0 ")
	public CorporateAccount findByCorporateAccountId(Long corporateAccountId, Integer company_id)throws Exception ;

	@Modifying
	@Query("UPDATE CorporateAccount  SET corporateName =?2, address =?3, mobileNumber =?4, alternateMobileNumber =?5, gstNumber =?6, perKMRate =?7 where corporateAccountId = ?1 AND companyId = ?8 AND markForDelete = 0 ")
	public void updatePartyMasterByCorporateAccountId(Long corporateAccountId, String corporateName, String address, String mobileNumber,
			String alternateMobileNumber, String gstNumber, double perKMRate, Integer companyId)throws Exception ;

	@Modifying
	@Query("UPDATE CorporateAccount  SET markForDelete = 1  where corporateAccountId = ?1 AND companyId = ?2 ")
	public void deletePartyMasterByCorporateAccountId(Long corporateAccountId, Integer company_id)throws Exception ;
}
