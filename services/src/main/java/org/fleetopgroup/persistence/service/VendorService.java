package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.VendorGSTRegistered;
import org.fleetopgroup.persistence.dao.VendorDocumentRepository;
import org.fleetopgroup.persistence.dao.VendorFixedFuelPriceRepository;
import org.fleetopgroup.persistence.dao.VendorFixedPartPriceRepository;
import org.fleetopgroup.persistence.dao.VendorRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.dto.VendorFixedFuelPriceDto;
import org.fleetopgroup.persistence.dto.VendorFixedPartPriceDto;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorDocument;
import org.fleetopgroup.persistence.model.VendorFixedFuelPrice;
import org.fleetopgroup.persistence.model.VendorFixedPartPrice;
import org.fleetopgroup.persistence.model.VendorSequenceCounter;
import org.fleetopgroup.persistence.model.VendorType;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("VendorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VendorService implements IVendorService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private VendorRepository vendorDao;

	@Autowired
	private VendorFixedPartPriceRepository vendorFixedPartPriceDao;

	@Autowired private MongoTemplate	mongoTemplate;
	@Autowired private ISequenceCounterService	sequenceCounterService;
	
	@Autowired
	private VendorFixedFuelPriceRepository vendorFixedFuelPriceDao;
	
	@Autowired private VendorDocumentRepository	vendorDocumentRepository;
	
	@Autowired IVendorTypeService 		vendorTypeService;
	
	@Autowired 
	IVendorSequenceService 				vendorSequenceService;
	

	private static final int PAGE_SIZE = 10;
	SimpleDateFormat dateFormatName = new SimpleDateFormat("dd-MMM-yyyy");

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addVendor(Vendor vendor) {

		vendorDao.save(vendor);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateVendor(Vendor vendor) {

		vendorDao.save(vendor);
	}

	/** This Page get Vendor table to get pagination values */
	@Transactional
	public Page<Vendor> getDeployment_Page_Vendor(Long vendorType_Id, Integer pageNumber, Integer companyId) {
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "vendorId");
		return vendorDao.getDeployment_Page_Vendor(vendorType_Id, companyId, pageable);
	}

	/** This List get Vendor table to get pagination last 10 entries values */
	@Transactional
	public List<VendorDto> listVendor(Long vendorType_Id, Integer pageNumber, Integer companyId) {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorNumber, v.vendorName, v.vendorLocation, VT.vendor_TypeName, v.vendorPhone, v.vendorWebsite, v.vendorAddress1,"
				+ "v.vendorAddress2, v.vendorCity, v.vendorState, v.vendorCountry, v.vendorPincode, v.vendorRemarks, v.vendorTermId, v.vendorPanNO,"
				+ "v.vendorGSTNO, v.vendorGSTRegisteredId, v.vendorCreditLimit, v.vendorAdvancePaid, v.vendorFirName, v.vendorFirPhone,"
				+ "v.vendorFirEmail, v.vendorSecName, v.vendorSecPhone, v.vendorSecEmail, v.vendorBankName, v.vendorBankBranch, v.vendorBankAccno, "
				+ "v.vendorBankIfsc, v.vendorTypeId"
				+ " From Vendor as v "
				+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " WHERE v.vendorTypeId=" + vendorType_Id + " AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0 ORDER BY v.vendorName asc", Object[].class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		
		List<Object[]> results = query.getResultList();
		
		List<VendorDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorDto>();
			VendorDto vendor = null;
			for (Object[] result : results) {
				vendor = new VendorDto();
				vendor.setVendorId((Integer) result[0]);
				vendor.setVendorNumber((Integer) result[1]);
				vendor.setVendorName((String) result[2]);
				vendor.setVendorLocation((String) result[3]);
				vendor.setVendorType((String) result[4]);
				vendor.setVendorPhone((String) result[5]);
				vendor.setVendorWebsite((String) result[6]);
				vendor.setVendorAddress1((String) result[7]);
				vendor.setVendorAddress2((String) result[8]);
				vendor.setVendorCity((String) result[9]);
				vendor.setVendorState((String) result[10]);
				vendor.setVendorCountry((String) result[11]);
				vendor.setVendorPincode((String) result[12]);
				vendor.setVendorRemarks((String) result[13]);
				vendor.setVendorTermId((short) result[14]);
				vendor.setVendorPanNO((String) result[15]);
				vendor.setVendorGSTNO((String) result[16]);
				vendor.setVendorGSTRegisteredId((short) result[17]);
				vendor.setVendorCreditLimit((String) result[18]);
				vendor.setVendorAdvancePaid((String) result[19]);
				vendor.setVendorFirName((String) result[20]);
				vendor.setVendorFirPhone((String) result[21]);
				vendor.setVendorFirEmail((String) result[22]);
				vendor.setVendorSecName((String) result[23]);
				vendor.setVendorSecPhone((String) result[24]);
				vendor.setVendorSecEmail((String) result[25]);
				vendor.setVendorBankName((String) result[26]);
				vendor.setVendorBankBranch((String) result[27]);
				vendor.setVendorBankAccno((String) result[28]);
				vendor.setVendorBankIfsc((String) result[29]);
				vendor.setVendorTypeId((Long) result[30]);


				Dtos.add(vendor);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<VendorDto> findAllVendorList(Integer companyid) throws Exception {
		
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorNumber, v.vendorName, v.vendorLocation, VT.vendor_TypeName, v.vendorPhone, v.vendorWebsite, v.vendorAddress1,"
				+ "v.vendorAddress2, v.vendorCity, v.vendorState, v.vendorCountry, v.vendorPincode, v.vendorRemarks, v.vendorTermId, v.vendorPanNO,"
				+ "v.vendorGSTNO, v.vendorGSTRegisteredId, v.vendorCreditLimit, v.vendorAdvancePaid, v.vendorFirName, v.vendorFirPhone,"
				+ "v.vendorFirEmail, v.vendorSecName, v.vendorSecPhone, v.vendorSecEmail, v.vendorBankName, v.vendorBankBranch, v.vendorBankAccno, "
				+ "v.vendorBankIfsc, v.vendorTypeId"
				+ " From Vendor as v "
				+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where v.companyId = "+companyid+" AND v.markForDelete = 0 AND v.autoVendor = 0 ORDER BY v.vendorId desc", Object[].class);
		
		List<Object[]> results = query.getResultList();
		
		List<VendorDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorDto>();
			VendorDto vendor = null;
			for (Object[] result : results) {
				vendor = new VendorDto();
				vendor.setVendorId((Integer) result[0]);
				vendor.setVendorNumber((Integer) result[1]);
				vendor.setVendorName((String) result[2]);
				vendor.setVendorLocation((String) result[3]);
				vendor.setVendorType((String) result[4]);
				vendor.setVendorPhone((String) result[5]);
				vendor.setVendorWebsite((String) result[6]);
				vendor.setVendorAddress1((String) result[7]);
				vendor.setVendorAddress2((String) result[8]);
				vendor.setVendorCity((String) result[9]);
				vendor.setVendorState((String) result[10]);
				vendor.setVendorCountry((String) result[11]);
				vendor.setVendorPincode((String) result[12]);
				vendor.setVendorRemarks((String) result[13]);
				vendor.setVendorTermId((short) result[14]);
				vendor.setVendorPanNO((String) result[15]);
				vendor.setVendorGSTNO((String) result[16]);
				vendor.setVendorGSTRegisteredId((short) result[17]);
				vendor.setVendorCreditLimit((String) result[18]);
				vendor.setVendorAdvancePaid((String) result[19]);
				vendor.setVendorFirName((String) result[20]);
				vendor.setVendorFirPhone((String) result[21]);
				vendor.setVendorFirEmail((String) result[22]);
				vendor.setVendorSecName((String) result[23]);
				vendor.setVendorSecPhone((String) result[24]);
				vendor.setVendorSecEmail((String) result[25]);
				vendor.setVendorBankName((String) result[26]);
				vendor.setVendorBankBranch((String) result[27]);
				vendor.setVendorBankAccno((String) result[28]);
				vendor.setVendorBankIfsc((String) result[29]);
				vendor.setVendorTypeId((Long) result[30]);


				Dtos.add(vendor);
			}
		}
		return Dtos;
	
	
	}

	@Transactional
	public List<VendorDto> listVendor(String qurey, Integer companyId) {
		/*TypedQuery<Vendor> query = entityManager.createQuery("from Vendor where (" + qurey + ") AND companyId = "+companyId+" AND markForDelete = 0 ", Vendor.class);
		return query.getResultList();*/
		


		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorNumber, v.vendorName, v.vendorLocation, VT.vendor_TypeName, v.vendorPhone, v.vendorWebsite, v.vendorAddress1,"
				+ "v.vendorAddress2, v.vendorCity, v.vendorState, v.vendorCountry, v.vendorPincode, v.vendorRemarks, v.vendorTermId, v.vendorPanNO,"
				+ "v.vendorGSTNO, v.vendorGSTRegisteredId, v.vendorCreditLimit, v.vendorAdvancePaid, v.vendorFirName, v.vendorFirPhone,"
				+ "v.vendorFirEmail, v.vendorSecName, v.vendorSecPhone, v.vendorSecEmail, v.vendorBankName, v.vendorBankBranch, v.vendorBankAccno, "
				+ "v.vendorBankIfsc, v.vendorTypeId"
				+ " From Vendor as v "
				+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where (" + qurey + ") AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0 ORDER BY v.vendorId desc", Object[].class);
		
		List<Object[]> results = query.getResultList();
		
		List<VendorDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorDto>();
			VendorDto vendor = null;
			for (Object[] result : results) {
				vendor = new VendorDto();
				vendor.setVendorId((Integer) result[0]);
				vendor.setVendorNumber((Integer) result[1]);
				vendor.setVendorName((String) result[2]);
				vendor.setVendorLocation((String) result[3]);
				vendor.setVendorType((String) result[4]);
				vendor.setVendorPhone((String) result[5]);
				vendor.setVendorWebsite((String) result[6]);
				vendor.setVendorAddress1((String) result[7]);
				vendor.setVendorAddress2((String) result[8]);
				vendor.setVendorCity((String) result[9]);
				vendor.setVendorState((String) result[10]);
				vendor.setVendorCountry((String) result[11]);
				vendor.setVendorPincode((String) result[12]);
				vendor.setVendorRemarks((String) result[13]);
				vendor.setVendorTermId((short) result[14]);
				vendor.setVendorPanNO((String) result[15]);
				vendor.setVendorGSTNO((String) result[16]);
				vendor.setVendorGSTRegisteredId((short) result[17]);
				vendor.setVendorCreditLimit((String) result[18]);
				vendor.setVendorAdvancePaid((String) result[19]);
				vendor.setVendorFirName((String) result[20]);
				vendor.setVendorFirPhone((String) result[21]);
				vendor.setVendorFirEmail((String) result[22]);
				vendor.setVendorSecName((String) result[23]);
				vendor.setVendorSecPhone((String) result[24]);
				vendor.setVendorSecEmail((String) result[25]);
				vendor.setVendorBankName((String) result[26]);
				vendor.setVendorBankBranch((String) result[27]);
				vendor.setVendorBankAccno((String) result[28]);
				vendor.setVendorBankIfsc((String) result[29]);
				vendor.setVendorTypeId((Long) result[30]);


				Dtos.add(vendor);
			}
		}
		return Dtos;
	
	}

	@Transactional
	public Vendor getVendor(int vendor_id) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return vendorDao.getVendor(vendor_id, userDetails.getCompany_id());
	}

	@Override
	public VendorDto getVendorDetails(int vendor_id, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorNumber, v.vendorName, v.vendorLocation, VT.vendor_TypeName, v.vendorPhone, v.vendorWebsite, v.vendorAddress1,"
				+ "v.vendorAddress2, v.vendorCity, v.vendorState, v.vendorCountry, v.vendorPincode, v.vendorRemarks, v.vendorTermId, v.vendorPanNO,"
				+ "v.vendorGSTNO, v.vendorGSTRegisteredId, v.vendorCreditLimit, v.vendorAdvancePaid, v.vendorFirName, v.vendorFirPhone,"
				+ "v.vendorFirEmail, v.vendorSecName, v.vendorSecPhone, v.vendorSecEmail, v.vendorBankName, v.vendorBankBranch, v.vendorBankAccno, "
				+ "v.vendorBankIfsc, v.vendorTypeId, v.created, v.lastupdated, U.email, U2.email, v.createdById, v.vendorId, v.ownPetrolPump, v.vendorTDSPercent"
				+ " From Vendor as v "
				+ " LEFT JOIN User U ON U.id = v.createdById"
				+ " LEFT JOIN User U2 ON U2.id = v.lastModifiedById"
				+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " WHERE v.vendorId=" + vendor_id + " AND v.companyId = "+companyId+" AND v.markForDelete = 0  AND v.autoVendor = 0", Object[].class);
		
		List<Object[]> results = query.getResultList();
		
		List<VendorDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorDto>();
			VendorDto vendor = null;
			for (Object[] result : results) {
				vendor = new VendorDto();
				vendor.setVendorId((Integer) result[0]);
				vendor.setVendorNumber((Integer) result[1]);
				vendor.setVendorName((String) result[2]);
				vendor.setVendorLocation((String) result[3]);
				vendor.setVendorType((String) result[4]);
				vendor.setVendorPhone((String) result[5]);
				vendor.setVendorWebsite((String) result[6]);
				vendor.setVendorAddress1((String) result[7]);
				vendor.setVendorAddress2((String) result[8]);
				vendor.setVendorCity((String) result[9]);
				vendor.setVendorState((String) result[10]);
				vendor.setVendorCountry((String) result[11]);
				vendor.setVendorPincode((String) result[12]);
				vendor.setVendorRemarks((String) result[13]);
				vendor.setVendorTermId((short) result[14]);
				vendor.setVendorPanNO((String) result[15]);
				vendor.setVendorGSTNO((String) result[16]);
				vendor.setVendorGSTRegisteredId((short) result[17]);
				vendor.setVendorCreditLimit((String) result[18]);
				vendor.setVendorAdvancePaid((String) result[19]);
				vendor.setVendorFirName((String) result[20]);
				vendor.setVendorFirPhone((String) result[21]);
				vendor.setVendorFirEmail((String) result[22]);
				vendor.setVendorSecName((String) result[23]);
				vendor.setVendorSecPhone((String) result[24]);
				vendor.setVendorSecEmail((String) result[25]);
				vendor.setVendorBankName((String) result[26]);
				vendor.setVendorBankBranch((String) result[27]);
				vendor.setVendorBankAccno((String) result[28]);
				vendor.setVendorBankIfsc((String) result[29]);
				vendor.setVendorTypeId((Long) result[30]);
				vendor.setCreatedOn((Date) result[31]);
				vendor.setLastupdatedOn((Date) result[32]);
				vendor.setCreatedBy((String) result[33]);
				vendor.setLastModifiedBy((String) result[34]);
				vendor.setCreatedById((Long) result[35]);
				vendor.setVendorId((Integer) result[36]);
				vendor.setOwnPetrolPump((short) result[37]);
				vendor.setVendorTDSPercent((Double) result[38]);

				Dtos.add(vendor);
			}
		}
		return Dtos.get(0);
	}
	
	@Transactional
	public Vendor getVendor_Details_Fuel_entries(int vendor_id) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Query query = entityManager.createQuery(
				"SELECT f.vendorId, f.vendorName, f.vendorLocation from Vendor AS f where f.vendorId = :id AND f.companyId = "+userDetails.getCompany_id()+" AND f.markForDelete = 0 AND f.autoVendor = 0 ");

		query.setParameter("id", vendor_id);
		Object[] vehicle = (Object[]) query.getSingleResult();

		Vendor select = new Vendor();
		if (vehicle != null) {
			select.setVendorId((Integer) vehicle[0]);
			select.setVendorName((String) vehicle[1]);
			select.setVendorLocation((String) vehicle[2]);
		}
		return select;
	}

	@Transactional
	public void deleteVendor(Integer vendor_id, Integer companyId) {

		vendorDao.deleteVendor(vendor_id, companyId);
	}

	@Transactional
	public List<Vendor> getVendorNametoall(String vendorname, Integer companyId) {
		return vendorDao.getVendorNametoall(vendorname, companyId);
	}

	@Transactional
	public Long countVendor(Integer companyId) throws Exception {

		return vendorDao.countVendorByID(companyId);
	}

	@Transactional
	public List<VendorDto> SearchVendor(String Search, Integer companyId) throws Exception {

		List<VendorDto> Dtos = null;
		/*TypedQuery<Vendor> query = entityManager.createQuery(
				"from Vendor where (lower(vendorNumber) Like ('%" + Search + "%') OR lower(vendorName) Like ('%" + Search
						+ "%') OR lower(vendorPhone) Like ('%" + Search + "%') OR lower(vendorFirPhone) Like ('%"
						+ Search + "%') OR lower(vendorSecPhone) Like ('%" + Search + "%')) AND companyId = "+companyId+" AND markForDelete = 0 ",
				Vendor.class);*/

		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorNumber, v.vendorName, v.vendorLocation, VT.vendor_TypeName, v.vendorPhone, v.vendorWebsite, v.vendorAddress1,"
				+ "v.vendorAddress2, v.vendorCity, v.vendorState, v.vendorCountry, v.vendorPincode, v.vendorRemarks, v.vendorTermId, v.vendorPanNO,"
				+ "v.vendorGSTNO, v.vendorGSTRegisteredId, v.vendorCreditLimit, v.vendorAdvancePaid, v.vendorFirName, v.vendorFirPhone,"
				+ "v.vendorFirEmail, v.vendorSecName, v.vendorSecPhone, v.vendorSecEmail, v.vendorBankName, v.vendorBankBranch, v.vendorBankAccno, "
				+ "v.vendorBankIfsc, v.vendorTypeId"
				+ " From Vendor as v "
				+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " WHERE (lower(v.vendorNumber) Like ('%" + Search + "%') OR lower(v.vendorName) Like ('%" + Search
				+ "%') OR lower(v.vendorPhone) Like ('%" + Search + "%') OR lower(v.vendorFirPhone) Like ('%"
				+ Search + "%') OR lower(v.vendorSecPhone) Like ('%" + Search + "%')) AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0", Object[].class);
		
		List<Object[]> results = query.getResultList();
		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorDto>();
			VendorDto vendor = null;
			for (Object[] result : results) {
				vendor = new VendorDto();
				vendor.setVendorId((Integer) result[0]);
				vendor.setVendorNumber((Integer) result[1]);
				vendor.setVendorName((String) result[2]);
				vendor.setVendorLocation((String) result[3]);
				vendor.setVendorType((String) result[4]);
				vendor.setVendorPhone((String) result[5]);
				vendor.setVendorWebsite((String) result[6]);
				vendor.setVendorAddress1((String) result[7]);
				vendor.setVendorAddress2((String) result[8]);
				vendor.setVendorCity((String) result[9]);
				vendor.setVendorState((String) result[10]);
				vendor.setVendorCountry((String) result[11]);
				vendor.setVendorPincode((String) result[12]);
				vendor.setVendorRemarks((String) result[13]);
				vendor.setVendorTermId((short) result[14]);
				vendor.setVendorPanNO((String) result[15]);
				vendor.setVendorGSTNO((String) result[16]);
				vendor.setVendorGSTRegisteredId((short) result[17]);
				vendor.setVendorCreditLimit((String) result[18]);
				vendor.setVendorAdvancePaid((String) result[19]);
				vendor.setVendorFirName((String) result[20]);
				vendor.setVendorFirPhone((String) result[21]);
				vendor.setVendorFirEmail((String) result[22]);
				vendor.setVendorSecName((String) result[23]);
				vendor.setVendorSecPhone((String) result[24]);
				vendor.setVendorSecEmail((String) result[25]);
				vendor.setVendorBankName((String) result[26]);
				vendor.setVendorBankBranch((String) result[27]);
				vendor.setVendorBankAccno((String) result[28]);
				vendor.setVendorBankIfsc((String) result[29]);
				vendor.setVendorTypeId((Long) result[30]);


				Dtos.add(vendor);
			}
		}
		}
		return Dtos;
	
	}

	@Transactional
	public List<Vendor> SearchOnlyFuelVendorName(String term, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation, v.ownPetrolPump"
				+ " FROM Vendor AS v "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where lower(v.vendorName) Like (:term)  AND  lower(VT.vendor_TypeName) Like ('%FUEL%') AND v.companyId = "+companyId+" "
						+ " AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		
		queryt.setParameter("term", "%"+term+"%");
		
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);
				dropdown.setOwnPetrolPump((short) result[3]);

				Dtos.add(dropdown);
			}
		}
		}
		return Dtos;

	}

	@Transactional
	public List<Vendor> SearchOnly_PART_VendorName(String term, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v where (lower(v.vendorName) Like ('%"
						+ term + "%')) AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);

				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		}
		return Dtos;

	}
	
	@Override
	public List<Vendor> SearchOnly_Other_VendorName(String term, Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where lower(v.vendorName) Like ('%"
						+ term + "%')  AND  lower(VT.vendor_TypeName) Like ('%OTHER%')" + " AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Vendor> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);

				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		return Dtos;

	}

	@Transactional
	public List<Vendor> SearchOnly_TYRE_VendorName(String term, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where lower(v.vendorName) Like ('%"
						+ term + "%')  AND  lower(VT.vendor_TypeName) Like ('%TYRE%')" + " AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);

				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		}
		return Dtos;

	}
	
	@Override
	public List<Vendor> SearchOnly_Battery_VendorName(String term, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where lower(v.vendorName) Like ('%"
						+ term + "%')  AND  lower(VT.vendor_TypeName) Like ('%BAT%')" + " AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);

				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		}
		return Dtos;

	}
	
	@Override
	public List<Vendor> SearchOnly_All_VendorName(String term, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where lower(v.vendorName) Like ('%"
						+ term + "%')  AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);

				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		}
		return Dtos;

	}
	
	@Override
	public List<Vendor> SearchOnly_Cloth_VendorName(String term, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where lower(v.vendorName) Like ('%"
						+ term + "%')  AND  lower(VT.vendor_TypeName) Like ('%UPHOLSTERY%')" + " AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);
				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		}
		return Dtos;

	}
	
	@Override
	public List<Vendor> SearchOnly_Urea_VendorName(String term, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where lower(v.vendorName) Like ('%"
						+ term + "%')  AND  lower(VT.vendor_TypeName) Like ('%UREA%')" + " AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);
				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		}
		return Dtos;

	}
	
	@Override
	public List<Vendor> SearchOnly_Laundry_VendorName(String term, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where lower(v.vendorName) Like ('%"
						+ term + "%')  AND  lower(VT.vendor_TypeName) Like ('%LAUNDRY%')" + " AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);

				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		}
		return Dtos;

	}

	@Transactional
	public List<Vendor> SearchAll_TYRE_Vendor() throws Exception {
		
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v "
						+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
						+ " where lower(VT.vendor_TypeName) Like ('%TYRE%')" + "AND v.markForDelete = 0 AND v.autoVendor = 0",
						Object[].class);
		List<Object[]> results = queryt.getResultList();
		
		List<Vendor> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();
				
				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);
				
				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		return Dtos;
		
	}

	@Transactional
	public Vendor GetAllVendorListToFuelImportVendorID(String importVendorName, String importLocation, Integer companyId) throws Exception {

		
		Query query = entityManager.createQuery(
				"SELECT f.vendorId, f.vendorName, f.vendorLocation from Vendor AS f where f.vendorName = :name AND f.vendorLocation =:location AND f.companyId = "+companyId+" AND f.markForDelete = 0 AND f.autoVendor = 0");

		query.setParameter("name", importVendorName);
		query.setParameter("location", importLocation);

		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		Vendor select = new Vendor();
		if (vehicle != null) {
			select.setVendorId((Integer) vehicle[0]);
			select.setVendorName((String) vehicle[1]);
			select.setVendorLocation((String) vehicle[2]);
		} else {
			return null;
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVendorService#
	 * Get_UserEmailId_To_vendor_Details(java.lang.String)
	 */
	@Transactional
	public Vendor Get_UserEmailId_To_vendor_Details(Long id) {

		Query query = entityManager.createQuery(
				"SELECT f.vendorId, f.vendorName, f.vendorLocation from Vendor AS f where f.vendorId IN (SELECT u.vendorId FROM UserProfile AS u WHERE u.user_id="
						+ id + " )");

		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		Vendor select = new Vendor();
		if (vehicle != null) {
			select.setVendorId((Integer) vehicle[0]);
			select.setVendorName((String) vehicle[1]);
			select.setVendorLocation((String) vehicle[2]);
		} else {
			return null;
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVendorService#
	 * ADD_VendorFixedPartPrice_IN_DB(org.fleetop.persistence.model.
	 * VendorFixedPartPrice)
	 */
	@Transactional
	public void ADD_VendorFixedPartPrice_IN_DB(VendorFixedPartPrice validate) {
		// Note: Add vendor Fixed Price Save details

		vendorFixedPartPriceDao.save(validate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVendorService#
	 * Validate_Vendor_Fixed_Part_value(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public VendorFixedPartPrice Validate_Vendor_Fixed_Part_value(long parseLong, Integer vENDOR_ID, Integer companyId) {
		// Validate Fixed Price Validate

		return vendorFixedPartPriceDao.Validate_Vendor_Fixed_Part_value(parseLong, vENDOR_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVendorService#
	 * Get_Deployment_Page_VendorFixedPartPrice(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Transactional
	public Page<VendorFixedPartPrice> Get_Deployment_Page_VendorFixedPartPrice(Integer vehicleID, Integer pageNumber, Integer companyId) {

		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return vendorFixedPartPriceDao.Get_Deployment_Page_VendorFixedPartPrice(vehicleID, companyId, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVendorService#
	 * list_VendorFixedPartPrice_VehicleID(java.lang.Integer, java.lang.Integer)
	 */
	@Transactional
	public List<VendorFixedPartPriceDto> list_VendorFixedPartPrice_VehicleID(Integer vehicleID, Integer pageNumber, Integer companyId) {
		// Note: this VendorFixed Price List

		/* this only Select column */
		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.VPPID, R.PARTID, R.VENDORID, R.PARTQUANTITY, R.PARTEACHCOST, R.PARTDISCOUNT, "
						+ "R.PARTGST, R.PARTTOTAL, P.partnumber, P.partname"
						+ " FROM VendorFixedPartPrice AS R"
						+ " LEFT JOIN MasterParts AS P ON P.partid = R.PARTID "
						+ " WHERE R.VENDORID="
						+ vehicleID + " AND R.markForDelete = 0 AND P.markForDelete = 0 AND R.COMPANY_ID = "+companyId+" ORDER BY R.VPPID desc", Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<VendorFixedPartPriceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorFixedPartPriceDto>();
			VendorFixedPartPriceDto list = null;
			for (Object[] result : results) {
				list = new VendorFixedPartPriceDto();

				list.setVPPID((Long) result[0]);
				list.setPARTID((Long) result[1]);
				list.setVENDORID((Integer) result[2]);
				list.setPARTQUANTITY((Double) result[3]);
				list.setPARTEACHCOST((Double) result[4]);
				list.setPARTDISCOUNT((Double) result[5]);
				list.setPARTGST((Double) result[6]);
				list.setPARTTOTAL((Double) result[7]);
				list.setCREATEDBY((String) result[8]);
				list.setLASTMODIFIEDBY((String) result[9]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVendorService#
	 * Delete_VendorFixedPartPrice_ID(java.lang.Long)
	 */
	@Transactional
	public void Delete_VendorFixedPartPrice_ID(Long vPID, Integer companyId) {
		// Note: This Delete Vendor Fixed Price

		vendorFixedPartPriceDao.Delete_VendorFixedPartPrice_ID(vPID, companyId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVendorService#
	 * Get_VendorFixedPrice_Validate_VendorID_PartId(java.lang.Long,
	 * java.lang.Integer)
	 */
	@Transactional
	public VendorFixedPartPrice Get_VendorFixedPrice_Validate_VendorID_PartId(Long pART_ID, Integer vENDOTID, Integer companyId) {
		// Get Value Off ice the Vendor and PartID Details
		return vendorFixedPartPriceDao.Get_VendorFixedPrice_Validate_VendorID_PartId(pART_ID, vENDOTID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVendorService#
	 * list_VendorDocument_IN_VendorId(java.lang.Integer)
	 */
	@Transactional
	public List<org.fleetopgroup.persistence.document.VendorDocument> list_VendorDocument_IN_VendorId(Integer vENDOR_ID, Integer companyId) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("VENDORID").is(vENDOR_ID).and("COMPANY_ID").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.VendorDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVendorService#saveVendorDocument(org
	 * .fleetop.persistence.model.VendorDocument)
	 */
	@Transactional
	public void saveVendorDocument(org.fleetopgroup.persistence.document.VendorDocument vendorDoc) {
		// Nore: save Docoumet ID
		vendorDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_VENDOR_DOCUMENT));
		mongoTemplate.save(vendorDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVendorService#getVendorDocuemnt(java
	 * .lang.Integer)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.VendorDocument getVendorDocuemnt(Long vDID, Integer companyId) {
		// Note: Vendor Document Id
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(vDID).and("COMPANY_ID").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.VendorDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVendorService#deleteVendorDocument(
	 * java.lang.Long)
	 */
	@Transactional
	public void deleteVendorDocument(Long vDID, Integer companyId) {
		// Delete Vendor Document
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query(Criteria.where("_id").is(vDID).and("COMPANY_ID").is(companyId));
		Update update = new Update();
		update.set("markForDelete", true);
		mongoTemplate.updateFirst(query, update, org.fleetopgroup.persistence.document.VendorDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVendorService#
	 * GET_VENDOR_CREDIT_AMOUNT_CountTotal_Cost_NotPaid(java.lang.String)
	 */
	@Transactional
	public Object[] GET_VENDOR_CREDIT_AMOUNT_CountTotal_Cost_NotPaid(Integer vendor_id) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
				short credit			= PaymentTypeConstant.PAYMENT_TYPE_CREDIT;
				short notPaid			= FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID;
				short approved			= FuelVendorPaymentMode.PAYMENT_MODE_APPROVED;
				short partiallyPaid 	= FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID;
				short negotiablePaid 	= FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID;
		
		Query queryt = entityManager.createQuery("SELECT sum(F.balanceAmount), "
				+ " (SELECT  SUM(SE.balanceAmount) FROM ServiceEntries AS SE where  SE.vendor_id =:vendorID AND SE.service_paymentTypeId="+credit+" AND SE.service_vendor_paymodeId IN ( "+notPaid+" ,"+approved+","+partiallyPaid+","+negotiablePaid+") AND SE.markForDelete = 0 AND SE.companyId = "+userDetails.getCompany_id()+"), "
				+ " (SELECT  SUM(PO.balanceAmount) FROM PurchaseOrders AS PO where  PO.purchaseorder_vendor_id =:vendorID AND PO.purchaseorder_termsId= "+credit+" AND PO.purchaseorder_vendor_paymodeId IN ("+notPaid+","+approved+","+partiallyPaid+","+negotiablePaid+" ) AND PO.markForDelete = 0  AND PO.companyId = "+userDetails.getCompany_id()+"), "
				+ " (SELECT  SUM(BI.balanceAmount) FROM BatteryInvoice AS BI where  BI.vendorId =:vendorID AND BI.paymentTypeId= "+credit+" AND BI.vendorPaymentStatus in ("+notPaid+","+approved+","+partiallyPaid+","+negotiablePaid+") AND BI.markForDelete = 0  AND BI.companyId = "+userDetails.getCompany_id()+") ,"
				+ " (SELECT  SUM(ITI.balanceAmount) FROM InventoryTyreInvoice AS ITI where ITI.VENDOR_ID =:vendorID AND ITI.PAYMENT_TYPE_ID= "+credit+" AND ITI.VENDOR_PAYMODE_STATUS_ID in ("+notPaid+","+approved+","+partiallyPaid+","+negotiablePaid+") AND ITI.markForDelete = 0  AND ITI.COMPANY_ID = "+userDetails.getCompany_id()+"), "
				+ " (SELECT  SUM(PI.balanceAmount) FROM PartInvoice AS PI where PI.vendorId =:vendorID AND PI.paymentTypeId= "+credit+" AND PI.vendorPaymentStatus in ("+notPaid+","+approved+","+partiallyPaid+","+negotiablePaid+") AND PI.markForDelete = 0  AND PI.companyId = "+userDetails.getCompany_id()+"), "
				+ " (SELECT  SUM(CI.balanceAmount) FROM ClothInvoice AS CI where CI.vendorId =:vendorID AND CI.paymentTypeId= "+credit+" AND CI.vendorPaymentStatus in ("+notPaid+","+approved+","+partiallyPaid+","+negotiablePaid+") AND CI.markForDelete = 0  AND CI.companyId = "+userDetails.getCompany_id()+") "
				+ " from Fuel AS F where F.vendor_id=:vendorID AND F.paymentTypeId="+credit+" AND F.fuel_vendor_paymodeId IN("+notPaid+","+approved+","+partiallyPaid+","+negotiablePaid+" ) AND F.markForDelete = 0 AND F.companyId = "+userDetails.getCompany_id()+"");
		queryt.setParameter("vendorID", vendor_id);
		Object[] count = (Object[]) queryt.getSingleResult();
		return count;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public Page<VendorFixedFuelPrice> Get_Deployment_Page_VendorFixedFuelPrice(Integer vehicleID, Integer pageNumber, Integer companyId) {

		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return vendorFixedFuelPriceDao.Get_Deployment_Page_VendorFixedFuelPrice(vehicleID, companyId, pageable);
	}

	@Override
	@Transactional
	public List<VendorFixedFuelPriceDto> list_VendorFixedFuelPrice_VehicleID(Integer vehicleID, Integer pageNumber, Integer companyId) {

		/* this only Select column */
		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT R.VFFID, R.FID, VFP.vFuel, R.FUEL_FIXED_DATE, R.FUEL_PERDAY_COST"
						+ " FROM VendorFixedFuelPrice AS R"
						+ " INNER JOIN VehicleFuel AS VFP ON VFP.fid = R.FID WHERE R.markForDelete = 0 AND R.VENDORID=" + vehicleID
						+ " AND R.COMPANY_ID = "+companyId+"  ORDER BY R.FUEL_FIXED_DATE desc", Object[].class);
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<VendorFixedFuelPriceDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VendorFixedFuelPriceDto>();
			VendorFixedFuelPriceDto list = null;
			for (Object[] result : results) {
				list = new VendorFixedFuelPriceDto();

				list.setVFFID((Long) result[0]);
				list.setFID((Long) result[1]);
				list.setFUEL_NAME((String) result[2]);
				if ((Date) result[3] != null) {
					list.setFUEL_FIXED_DATE(dateFormatName.format((Date) result[3]));
				}
				list.setFUEL_PERDAY_COST((Double) result[4]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Override
	@Transactional
	public VendorFixedFuelPrice Validate_Vendor_Fixed_Fuel_value(Long fuel_ID, Date fuel_FIXED_DATE,
			Integer vendorid, Integer companyId) {

		try {
			return vendorFixedFuelPriceDao.Validate_Vendor_Fixed_Fuel_value(fuel_ID, fuel_FIXED_DATE, vendorid, companyId);
		} catch (Exception e) {
			
			return null;
		}
	}

	@Override
	@Transactional
	public void ADD_VendorFixed_FuelPrice_IN_DB(VendorFixedFuelPrice validateFuel) {

		vendorFixedFuelPriceDao.save(validateFuel);
	}

	@Override
	@Transactional
	public void Delete_VendorFixed_FuelPrice_ID(Long vPID) {
		
		vendorFixedFuelPriceDao.Delete_VendorFixed_FuelPrice_ID(vPID);
	}

	@Override
	@Transactional
	public VendorFixedFuelPrice GET_VENDOR_DROPDOWN_FUEL_ADD_FIXED_DETAILS(Integer vendor_id, Long fuel_ID,
			java.sql.Date fuelDate, Integer companyId) {
		
		return vendorFixedFuelPriceDao.GET_VENDOR_DROPDOWN_FUEL_ADD_FIXED_DETAILS(vendor_id, fuel_ID, fuelDate, companyId);
	}

	@Override
	public void transferVendorDocument(List<VendorDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.VendorDocument				vendorDocument		= null;
		List<org.fleetopgroup.persistence.document.VendorDocument> 		vendorDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				vendorDocumentList	= new ArrayList<>();
				for(VendorDocument	document : list) {
					vendorDocument	= new org.fleetopgroup.persistence.document.VendorDocument();
					
					vendorDocument.set_id(document.getVDID());
					vendorDocument.setVENDOR_DOCNAME(document.getVENDOR_DOCNAME());
					vendorDocument.setVENDOR_FILENAME(document.getVENDOR_FILENAME());
					vendorDocument.setVENDORID(document.getVENDORID());
					vendorDocument.setVENDOR_CONTENT(document.getVENDOR_CONTENT());
					vendorDocument.setVENDOR_CONTENTTYPE(document.getVENDOR_CONTENTTYPE());
					vendorDocument.setCOMPANY_ID(document.getCOMPANY_ID());
					vendorDocument.setCREATEDBYID(document.getCREATEDBYID());
					vendorDocument.setLASTMODIFIEDBYID(document.getLASTMODIFIEDBYID());
					vendorDocument.setMarkForDelete(document.isMarkForDelete());
					vendorDocument.setCREATED_DATE(document.getCREATED_DATE());
					vendorDocument.setLASTUPDATED_DATE(document.getLASTUPDATED_DATE());
					
					vendorDocumentList.add(vendorDocument);
				}
				mongoTemplate.insert(vendorDocumentList, org.fleetopgroup.persistence.document.VendorDocument.class);
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
	
	@Override
	public long getVendorDocumentMaxId() throws Exception {
		try {
			return vendorDocumentRepository.getVendorDocumentMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public Vendor getLorryHireVendor(Integer vendorId) throws Exception {
		Query query = entityManager.createQuery(
				"SELECT f.vendorId, f.vendorName, f.vendorLocation from Vendor AS f "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = f.vendorTypeId"
				+ " where f.vendorId = "+vendorId+" AND lower(VT.vendor_TypeName) Like ('%LORRY%')   ");

		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}
		
		Vendor select = null;
		if (vehicle != null) {
			select	= new Vendor();
			select.setVendorId((Integer) vehicle[0]);
			select.setVendorName((String) vehicle[1]);
			select.setVendorLocation((String) vehicle[2]);
		}
		return select;
	}
	
	public Vendor getVendorIdFromNew(String vendorName, Integer companyId, String vendorType, String module) throws Exception{

		Vendor vendor = new Vendor();
		vendor.setVendorTermId(PaymentTypeConstant.PAYMENT_TYPE_CASH);
		VendorSequenceCounter	sequenceCounter = vendorSequenceService.findNextVendorNumber(companyId);
		vendor.setVendorNumber((int) sequenceCounter.getNextVal());
		vendor.setVendorGSTRegisteredId(VendorGSTRegistered.VENDOR_GST_NOT_REGISTERED);
		vendor.setCompanyId(companyId);
		vendor.setCreated(new Date());
		vendor.setLastupdated(new Date());
		vendor.setVendorRemarks("This Create "+module+" To Vendor");
		vendor.setVendorName(vendorName);
		vendor.setVendorLocation("New");
		vendor.setAutoVendor(true);
		
		VendorType VenType = vendorTypeService.getVendorType(vendorType, companyId);
		if(VenType != null) {
			vendor.setVendorTypeId(VenType.getVendor_Typeid());
		}else {
			vendor.setVendorTypeId(0);
		}
		addVendor(vendor);
		
		return vendor;
	}
	
	@Override
	public List<Vendor> SearchOnly_All_VendorNameOnTripExpense(String term, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT distinct  v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " INNER JOIN TripSheetExpense TSE ON TSE.vendorId = v.vendorId "
				+ " where lower(v.vendorName) Like ('%"
						+ term + "%')  AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);

				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		}
		return Dtos;

	}
	
	@Override
	public ValueObject getOtherVendorSearchListForMobile(ValueObject object) throws Exception {
		List<Vendor>   otherVendorList 				= null;
		List<Vendor>   loadTypes					= null;
		try {
			otherVendorList 	= new ArrayList<Vendor>();
			loadTypes 		    = SearchOnly_Other_VendorName(object.getString("term"), object.getInt("companyId"));
			
			if(loadTypes != null && !loadTypes.isEmpty()) {
				for(Vendor load : loadTypes) {
					otherVendorList.add(load);
				}
			}

			object.put("otherVendorList", otherVendorList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			loadTypes 		     = null;
			otherVendorList 	 = null;
			object  	 	     = null;
		}
	}
	
	@Transactional
	public List<Vendor> searchVendorByNameAndType(String term, String vendorTypeId, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation FROM Vendor AS v where (lower(v.vendorName) Like ('%"
						+ term + "%')) AND v.vendorTypeId IN("+vendorTypeId+") AND v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);

				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		}
		return Dtos;

	}
	
	@Override
	public List<Vendor> getOwnPetrolPumpList(Integer companyId) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation, v.ownPetrolPump FROM Vendor AS v where "
				+ " v.companyId = "+companyId+" AND v.markForDelete = 0 AND v.autoVendor = 0 AND v.ownPetrolPump = 1",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<Vendor> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);
				dropdown.setOwnPetrolPump((short) result[3]);

				Dtos.add(dropdown);
			}
		} else {
			return null;
		}
		return Dtos;

	}
	
	@Override
	public List<Vendor> SearchOnlyPetrolPumpVendorName(String term, Integer companyId) throws Exception {
		List<Vendor> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT v.vendorId, v.vendorName, v.vendorLocation, v.ownPetrolPump"
				+ " FROM Vendor AS v "
				+ " INNER JOIN VendorType VT ON VT.vendor_Typeid = v.vendorTypeId"
				+ " where lower(v.vendorName) Like ('%"
						+ term + "%')  AND  lower(VT.vendor_TypeName) Like ('%FUEL%') AND v.companyId = "+companyId+" AND v.markForDelete = 0 "
								+ " AND v.autoVendor = 0 AND v.ownPetrolPump = 1",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Vendor>();
			Vendor dropdown = null;
			for (Object[] result : results) {
				dropdown = new Vendor();

				dropdown.setVendorId((Integer) result[0]);
				dropdown.setVendorName((String) result[1]);
				dropdown.setVendorLocation((String) result[2]);
				dropdown.setOwnPetrolPump((short) result[3]);

				Dtos.add(dropdown);
			}
		}
		}
		return Dtos;

	
	}
	
	@Transactional
	public List<Vendor> getVendorPanNo(String vendorPanNo, Integer companyId) {
		return vendorDao.getVendorPanNo(vendorPanNo, companyId);
	}
	
	@Transactional
	public Vendor  getVendorByName(String vendorPanNo, Integer companyId) throws Exception {
		return vendorDao.getVendorByName(vendorPanNo, companyId);
	}
	
}