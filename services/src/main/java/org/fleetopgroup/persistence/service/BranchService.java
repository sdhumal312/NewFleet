package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.bl.BranchBL;
import org.fleetopgroup.persistence.dao.BranchDocumentRepository;
import org.fleetopgroup.persistence.dao.BranchMapperRepository;
import org.fleetopgroup.persistence.dao.BranchPhotoRepository;
import org.fleetopgroup.persistence.dao.BranchRepository;
import org.fleetopgroup.persistence.dao.CompanyMapperRepository;
import org.fleetopgroup.persistence.dto.BranchDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Branch;
import org.fleetopgroup.persistence.model.BranchDocument;
import org.fleetopgroup.persistence.model.BranchHistory;
import org.fleetopgroup.persistence.model.BranchMapper;
import org.fleetopgroup.persistence.model.BranchPhoto;
import org.fleetopgroup.persistence.model.CompanyMapper;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IBranchHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IBranchService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BranchService implements IBranchService {
	

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private BranchRepository branchRepository;
	@Autowired IBranchHistoryService branchHistoryService;

	@Autowired
	private BranchDocumentRepository branchDocumentRepository;
	
	@Autowired
	private BranchPhotoRepository branchPhototRepository;
	
	@Autowired private MongoTemplate	mongoTemplate;
	@Autowired private ISequenceCounterService	sequenceCounterService;
	@Autowired private BranchPhotoRepository	branchPhotoRepository;
	@Autowired private CompanyMapperRepository	companyMapperRepository;
	@Autowired private BranchMapperRepository	branchMapperRepository;
	
	@Transactional
	public Branch registerNewBranch(Branch BranchDto) throws Exception {

		return branchRepository.save(BranchDto);
	}

	
	@Transactional
	public Branch updateBranch(Branch BranchDto) throws Exception {
		try {
			
			return branchRepository.save(BranchDto);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public List<Branch> findAll(Integer company_ID) throws Exception {

		return branchRepository.SearchBranchLisrCompanyID(company_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IBranchService#getBranchByID(java.
	 * lang.Integer)
	 */
	@Transactional
	public Branch getBranchByID(Integer dri_id,int companyId){
		try {
			//CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Query query = entityManager.createQuery("Select br.branch_id,br.advance_paid,br.annual_increment,br.branch_address,br.branch_city"
					+ ",br.branch_code,br.branch_country,br.branch_electricity_no,br.branch_email,br.branch_incharge"
					+ ",br.branch_incharge_phone,br.branch_landmark,br.branch_mobilenumber,br.branch_name,br.branch_ownership_type"
					+ ",br.branch_phonenumber,br.branch_pincode,br.branch_serviceTax_no,br.branch_state,br.branch_status"
					+ ",br.branch_time_from,br.branch_time_to,br.company_id,br.created,cu.email"
					+ ",lu.email,br.lastupdated,br.lease_amount,br.monthly_rent,br.monthly_rent_date"
					+ ",br.owner1_address,br.owner1_mobile,br.owner1_name,br.owner1_pan,br.owner2_address,br.owner2_mobile"
					+ ",br.owner2_name,br.owner2_pan,br.markForDelete"
					+ " FROM Branch AS br "
					+ " LEFT JOIN User cu ON cu.id = br.createdById"
					+ " LEFT JOIN User lu ON lu.id = br.lastModifiedById"
					+ " WHERE  br.branch_id = "+dri_id+" AND br.company_id = "+companyId+" AND br.markForDelete = 0");

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				System.out.println("Exception e : " + nre);
			}

			Branch branch;
			if (result != null) {
				branch = new Branch();
				branch.setBranch_id((Integer) result[0]);
				branch.setAdvance_paid((Double) result[1]);
				branch.setAnnual_increment((String) result[2]);
				branch.setBranch_address((String) result[3]);
				branch.setBranch_city((String) result[4]);
				branch.setBranch_code((String) result[5]);
				branch.setBranch_country((String) result[6]);
				branch.setBranch_electricity_no((String) result[7]);
				branch.setBranch_email((String) result[8]);
				branch.setBranch_incharge((String) result[9]);
				branch.setBranch_incharge_phone((String) result[10]);
				branch.setBranch_landmark((String) result[11]);
				branch.setBranch_mobilenumber((String) result[12]);
				branch.setBranch_name((String) result[13]);
				branch.setBranch_ownership_type((String) result[14]);
				branch.setBranch_phonenumber((String) result[15]);
				branch.setBranch_pincode((String) result[16]);
				branch.setBranch_serviceTax_no((String) result[17]);
				branch.setBranch_state((String) result[18]);
				branch.setBranch_status((String) result[19]);
				branch.setBranch_time_from((String) result[20]);
				branch.setBranch_time_to((String) result[21]);
				branch.setCompany_id((Integer) result[22]);
				branch.setCreated((Date) result[23]);
				branch.setCreatedBy((String) result[24]);
				branch.setLastModifiedBy((String) result[25]);
				branch.setLastupdated((Date) result[26]);
				branch.setLease_amount((Double) result[27]);
				branch.setMonthly_rent((Double) result[28]);
				branch.setMonthly_rent_date((Date) result[29]);
				branch.setOwner1_address((String) result[30]);
				branch.setOwner1_mobile((String) result[31]);
				branch.setOwner1_name((String) result[32]);
				branch.setOwner1_pan((String) result[33]);
				branch.setOwner2_address((String) result[34]);
				branch.setOwner2_mobile((String) result[35]);
				branch.setOwner2_name((String) result[36]);
				branch.setOwner2_pan((String) result[37]);
				branch.setMarkForDelete((boolean) result[38]);
			} else {
				return null;
			}

			return branch;
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IBranchService#validateBranchName(
	 * java.lang.String)
	 */
	@Transactional
	public List<Branch> validateBranchName(String Branch_name, Integer companyId) {

		return branchRepository.validateBranchName(Branch_name, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IBranchService#countTotalBranch()
	 */
	@Transactional
	public Long countTotalBranch(Integer company_Id) throws Exception {
		
		return branchRepository.countTotalBranch(company_Id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IBranchService#countActiveBranch()
	 */
	@Transactional
	public Long countActiveBranch(Integer company_Id) throws Exception {

		return branchRepository.countActiveBranch(company_Id);
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#deleteBranch_ID(java.lang.Integer)
	 */
	@Transactional
	public void deleteBranch_ID(Integer branch_id, Integer companyId) throws Exception {
		
		branchRepository.deleteBranch_ID(branch_id, companyId);
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#saveBranchDocument(org.fleetop.persistence.model.BranchDocument)
	 */
	@Transactional
	public void saveBranchDocument(org.fleetopgroup.persistence.document.BranchDocument branchDocument) {
		branchDocument.set_id((int) sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_BRANCH_DOCUMENT));
		mongoTemplate.save(branchDocument);
	}
	
	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#updateBranchDocument(org.fleetop.persistence.model.BranchDocument)
	 */
	@Transactional
	public BranchDocument updateBranchDocument(BranchDocument BranchDocument) {
		
		return branchDocumentRepository.save(BranchDocument);
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#listBranchDocument(int)
	 */
	@Transactional
	public List<org.fleetopgroup.persistence.document.BranchDocument> listBranchDocument(int Branch_id, Integer companyId) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("branch_id").is(Branch_id).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.BranchDocument.class);
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#getBranchDocuemnt(int)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.BranchDocument getBranchDocuemnt(int Branch_docid, Integer companyId) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(Branch_docid).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.BranchDocument.class);
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#deleteBranchDocument(int)
	 */
	@Transactional
	public void deleteBranchDocument(Integer Branch_documentid, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(Branch_documentid).and("companyId").is(companyId));
		mongoTemplate.remove(query, org.fleetopgroup.persistence.document.BranchDocument.class);
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#getBranchCount(int)
	 */
	@Transactional
	public Long getBranchDocumentCount(int Branch_docid) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("branch_id").is(Branch_docid).and("companyId").is(userDetails.getCompany_id()).and("markForDelete").is(false));
		return (long) mongoTemplate.find(query, org.fleetopgroup.persistence.document.BranchDocument.class).size();
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#addBranchPhoto(org.fleetop.persistence.model.BranchPhoto)
	 */
	@Transactional
	public void addBranchPhoto(org.fleetopgroup.persistence.document.BranchPhoto branchPhoto) {
		branchPhoto.set_id((int) sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_BRANCH_PHOTO));
		mongoTemplate.save(branchPhoto);
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#updateBranchPhoto(org.fleetop.persistence.model.BranchPhoto)
	 */
	@Transactional
	public BranchPhoto updateBranchPhoto(BranchPhoto diverPhoto) {
		
		return branchPhototRepository.save(diverPhoto);	
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#listBranchPhoto(int)
	 */
	@Transactional
	public List<org.fleetopgroup.persistence.document.BranchPhoto> listBranchPhoto(int diverPhoto, Integer companyId) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("branch_id").is(diverPhoto).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.BranchPhoto.class);
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#getBranchPhoto(int)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.BranchPhoto getBranchPhoto(int Branch_photoid, Integer companyId) {
		
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(Branch_photoid).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.BranchPhoto.class);
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#getPhotoCount(int)
	 */
	@Transactional
	public Long getPhotoCount(int Branch_docid) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("branch_id").is(Branch_docid).and("companyId").is(userDetails.getCompany_id()).and("markForDelete").is(false));
		return (long) mongoTemplate.find(query, org.fleetopgroup.persistence.document.BranchPhoto.class).size();
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#deleteBranchPhoto(java.lang.Integer)
	 */
	@Transactional
	public void deleteBranchPhoto(Integer Branch_Photoid, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(Branch_Photoid).and("companyId").is(companyId).and("markForDelete").is(false));
		mongoTemplate.remove(query, org.fleetopgroup.persistence.document.BranchPhoto.class);
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#SearchBranchList(java.lang.String)
	 */
	@Transactional
	public List<Branch> SearchBranchList(String Search_list, Integer companyId) throws Exception {
		if(Search_list != null && !Search_list.trim().equalsIgnoreCase("") && Search_list.indexOf('\'') != 0 ) {
		TypedQuery<Branch> query = entityManager.createQuery("from Branch where  (lower(branch_name) Like ('%"
				+ Search_list + "%') OR  lower(branch_code) Like ('%" + Search_list
				+ "%')) AND company_id = "+companyId+" AND markForDelete = 0", Branch.class);
		return query.getResultList();
		}else {
			return null;
		}
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#SearchBranchLisrCompanyID(java.lang.Integer)
	 */
	@Transactional
	public List<Branch> SearchBranchLisrCompanyID(Integer company_ID) throws Exception {
		try {
			return branchRepository.SearchBranchLisrCompanyID(company_ID);
		} catch (Exception e) {
			throw e;
		}
	}


	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#Search_Olny_Branch_name(java.lang.String)
	 */
	@Transactional
	public List<Branch> Search_Olny_Branch_name(String Search, CustomUserDetails userDetails) throws Exception {
		List<Branch> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager
				.createQuery(
						"SELECT d.branch_id, d.branch_name,d.branch_code  FROM Branch AS d where (lower(d.branch_name) Like ('%"
								+ Search + "%') AND d.company_id ="+userDetails.getCompany_id()+" AND d.markForDelete = 0  OR  lower(d.branch_code) Like ('%" + Search + "%')) AND d.company_id ="+userDetails.getCompany_id()+" AND d.markForDelete = 0 ",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Branch>();
			Branch dropdown = null;
			for (Object[] result : results) {
				dropdown = new Branch();

				dropdown.setBranch_id((Integer) result[0]);
				dropdown.setBranch_name((String) result[1]);
				dropdown.setBranch_code((String) result[2]);
				
				Dtos.add(dropdown);
			}
		}
		}
		return Dtos;
	}
	
	@Transactional
	public List<Branch> SearchOlnyBranchNameMobile(String Search, int companyId) throws Exception {
		
		List<Branch> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager
				.createQuery(
						"SELECT d.branch_id, d.branch_name,d.branch_code  FROM Branch AS d where (lower(d.branch_name) Like ('%"
								+ Search + "%') AND d.company_id ="+companyId+" AND d.markForDelete = 0  OR  lower(d.branch_code) Like ('%" + Search + "%')) AND d.company_id ="+companyId+" AND d.markForDelete = 0 ",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Branch>();
			Branch dropdown = null;
			for (Object[] result : results) {
				dropdown = new Branch();

				dropdown.setBranch_id((Integer) result[0]);
				dropdown.setBranch_name((String) result[1]);
				dropdown.setBranch_code((String) result[2]);
				
				Dtos.add(dropdown);
			}
		}
		}
		return Dtos;
		
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#get_Issues_BranchID_getBranchName(java.lang.Integer)
	 */
	@Transactional
	public Branch get_Issues_BranchID_getBranchName(Integer branch_id) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Query query = entityManager.createQuery(
				"SELECT f.branch_id, f.branch_name from Branch AS f where f.branch_id = :id AND f.company_id = "+userDetails.getCompany_id()+" AND f.markForDelete = 0");

		query.setParameter("id", branch_id);
		Object[] branch = (Object[]) query.getSingleResult();

		Branch select = new Branch();
		select.setBranch_id((Integer) branch[0]);
		select.setBranch_name((String) branch[1]);
		return select;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IBranchService#
	 * Search_Branch_Name_Json(java.lang.String)
	 */
	@Transactional
	public List<Branch> Search_Branch_Name_Json(String term, Integer companyId) {
		List<Branch> Dtos = null;
		if(term != null && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager.createQuery("SELECT f.branch_id, f.branch_name from Branch as f "
				+ "where lower(f.branch_name) Like ('%" + term + "%') AND f.company_id = "+companyId+" AND f.markForDelete = 0", Object[].class);
		List<Object[]> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Branch>();
			Branch user = null;
			for (Object[] result : results) {
				user = new Branch();

				user.setBranch_id((Integer) result[0]);
				user.setBranch_name((String) result[1]);

				Dtos.add(user);
			}
		}
		}
		return Dtos;
	}


	@Override
	public void transferBranchDocument(List<BranchDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.BranchDocument			branchDocument		= null;
		List<org.fleetopgroup.persistence.document.BranchDocument> 		branchDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				branchDocumentList	= new ArrayList<>();
				for(BranchDocument	document : list) {
					branchDocument	= new org.fleetopgroup.persistence.document.BranchDocument();
					
					branchDocument.set_id(document.getbranch_documentid());
					branchDocument.setBranch_documentname(document.getBranch_documentname());
					branchDocument.setBranch_docFrom(document.getBranch_docFrom());
					branchDocument.setBranch_docTo(document.getBranch_docTo());
					branchDocument.setBranch_uploaddate(document.getBranch_uploaddate());
					branchDocument.setBranch_filename(document.getBranch_filename());
					branchDocument.setBranch_id(document.getBranch_id());
					branchDocument.setCompanyId(document.getCompanyId());
					branchDocument.setBranch_content(document.getBranch_content());
					branchDocument.setBranch_contentType(document.getBranch_contentType());
					branchDocument.setMarkForDelete(document.isMarkForDelete());
					
					branchDocumentList.add(branchDocument);
				}
				mongoTemplate.insert(branchDocumentList, org.fleetopgroup.persistence.document.BranchDocument.class);
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}


	@Override
	public void transferBranchPhoto(List<BranchPhoto> list) throws Exception {
		org.fleetopgroup.persistence.document.BranchPhoto				branchPhoto		= null;
		List<org.fleetopgroup.persistence.document.BranchPhoto> 		branchPhotoList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				branchPhotoList	= new ArrayList<>();
				for(BranchPhoto	document : list) {
					branchPhoto	= new org.fleetopgroup.persistence.document.BranchPhoto();
					
					branchPhoto.set_id(document.getBranch_photoid());
					branchPhoto.setBranch_photoname(document.getBranch_photoname());
					branchPhoto.setBranch_uploaddate(document.getBranch_uploaddate());
					branchPhoto.setBranch_filename(document.getBranch_filename());
					branchPhoto.setBranch_id(document.getBranch_id());
					branchPhoto.setCompanyId(document.getCompanyId());
					branchPhoto.setBranch_content(document.getBranch_content());
					branchPhoto.setBranch_contentType(document.getBranch_contentType());
					branchPhoto.setMarkForDelete(document.isMarkForDelete());
					
					branchPhotoList.add(branchPhoto);
				}
				mongoTemplate.insert(branchPhotoList, org.fleetopgroup.persistence.document.BranchPhoto.class);
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
	
	@Override
	public long getBranchDocumentMaxId() throws Exception {
		
		try {
			return branchDocumentRepository.getBranchDocumentMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public long getBranchPhotoMaxId() throws Exception {
		try {
			return branchPhotoRepository.getBranchPhotoMaxId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	@Override
	public ValueObject searhByBranchNameInMobile(ValueObject object) throws Exception {
		List<Branch>   branchList 				= null;
		List<Branch>   branch					= null;
		try {
			branchList 	= new ArrayList<Branch>();
			branch 		= SearchOlnyBranchNameMobile(object.getString("term"), object.getInt("companyId"));
			
			if(branch != null && !branch.isEmpty()) {
				for(Branch brch : branch) {
					
					Branch dto = new Branch();
					dto.setBranch_id(brch.getBranch_id());
					dto.setBranch_name(brch.getBranch_name());
					dto.setBranch_code(brch.getBranch_code());
					
					branchList.add(dto);
				}
			}

			object.put("BranchList", branchList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			branch 		 = null;
			branchList 	 = null;
			object  	 = null;
		}
	}
	
	
	@Override
	public ValueObject saveBranch(BranchDto branchDto) {
		ValueObject valuesObject = new ValueObject();
		
		BranchBL bbl = new BranchBL();
		try {
			
			CompanyMapper	companyMapper	= companyMapperRepository.findByAccountGroupId(branchDto.getCompany_id());
			BranchMapper	oldMapper		= branchMapperRepository.findBranchMapperByIvBranchId(branchDto.getIvBranchId());
			
			if(companyMapper != null) {
				Branch branch = bbl.prepareBranchModel(branchDto);
				
				branch.setCompany_id(companyMapper.getFleetCompanyId());
				branch.setCreatedById(branchDto.getCreatedById());
				branch.setLastModifiedById(branchDto.getCreatedById());
				if(oldMapper != null) 
					branch.setBranch_id(oldMapper.getFleetBranchId());
				
					
				
				List<Branch> validate = validateBranchName(branchDto.getBranch_name(),companyMapper.getFleetCompanyId()); 
				
				if (validate.isEmpty() || validate == null || oldMapper != null) {
					registerNewBranch(branch);
					if(oldMapper == null)
						saveBranchMapper(branch, companyMapper, branchDto);
					valuesObject.put("saveBranch", true);
					valuesObject.put("fleetopBranchId", branch.getBranch_id());
				} else {
					valuesObject.put("already", true);
				}
			}else 
				valuesObject.put("companyMappingNotFound", true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return valuesObject;
	}
	
	private BranchMapper saveBranchMapper(Branch	branch, CompanyMapper	companyMapper, BranchDto	branchDto) throws Exception{
		BranchMapper	branchMapper	= new BranchMapper();
		try {
			branchMapper.setFleetBranchId(branch.getBranch_id());
			branchMapper.setIvBranchId(branchDto.getIvBranchId());
			branchMapper.setStatus(true);
			branchMapper.setMarkForDelete(false);
			
			branchMapperRepository.save(branchMapper);
			
			return branchMapper;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject updateBranch(BranchDto branchDto) throws Exception {
		ValueObject valuesObject = new ValueObject();
		try {
			BranchBL bbl = new BranchBL();
			
			
			Branch branch = bbl.UpdateprepareBranchModel(branchDto);

			branch.setCompany_id(branchDto.getCompany_id());
			branch.setLastModifiedById(branchDto.getCreatedById());
			Branch history=getBranchByID(branch.getBranch_id(),branchDto.getCompany_id());
			if(history != null) {
				BranchHistory  branchHistory	= bbl.createBranchHistoryDto(history);
				updateBranch(branch);
				
				
				branchHistoryService.registerNewBranchHistory(branchHistory);
			}else {
				valuesObject.put("notExist", "branch not found by branch id");
			}

			
			
			/**
			 * this line is for updating Branches List Cache
			 */
		//	cacheService.refreshCacheOfBranch(userDetails.getCompany_id());
			
			valuesObject.put("success", true);
			
			return valuesObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject updateBranchStatus(BranchDto branchDto) throws Exception {
		
		ValueObject	 	valueObject	= null;
		CompanyMapper	companyMapper	= companyMapperRepository.findByAccountGroupId(branchDto.getCompany_id());
		
		valueObject	= new ValueObject();
		if(companyMapper != null) {
			String status ="";
			if(branchDto.getStatus() == 0)
				status	= "Active";
			else
				status = "IN-Active";
				
			BranchMapper	oldMapper		= branchMapperRepository.findBranchMapperByIvBranchId(branchDto.getIvBranchId());
			if(oldMapper != null) {
				branchRepository.updateBranchStatus(oldMapper.getFleetBranchId(), companyMapper.getFleetCompanyId(), status);
				valueObject.put("success", true);
			}
			else
				valueObject.put("branchMappingNotFound", true);
				
		}else		
			valueObject.put("companyMappingNotFound", true);
		
		return valueObject;
	}

	@Transactional
	public List<Branch> SearchBranchListByCompanyId(String term, Integer companyId) throws Exception {
		List<Branch> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			
		TypedQuery<Object[]> queryt = entityManager.createQuery("SELECT b.branch_id, b.branch_name From Branch As b "
				+ " where (lower(b.branch_name) Like ('%"+ term + "%')) AND b.company_id = "+companyId+ " AND markForDelete = 0 " , Object[].class);
		//queryt.setParameter("term", "%"+term+"%");
		List<Object[]> results = queryt.getResultList();
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<Branch>();
			Branch dropdown = null;
			for (Object[] result : results) {
				dropdown = new Branch();
				dropdown.setBranch_id((Integer) result[0]);
				dropdown.setBranch_name((String) result[1]);
				Dtos.add(dropdown);
			}
		}
		}
		return Dtos;
	}
}
