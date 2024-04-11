package org.fleetopgroup.persistence.service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.OTPRequiredTypeConstant;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.dao.TwoFactorAuthenticationDetailsRepository;
import org.fleetopgroup.persistence.dao.UserProfileDocumentRepository;
import org.fleetopgroup.persistence.dao.UserProfilePhotoRepository;
import org.fleetopgroup.persistence.dao.UserProfileRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.TwoFactorAuthenticationDetails;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.persistence.model.UserProfileDocument;
import org.fleetopgroup.persistence.model.UserProfilePhoto;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.validation.EmailExistsException;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Transactional
public class UserProfileService implements IUserProfileService {

	SimpleDateFormat dateFormat 	 = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateTimeFormat  = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired private MongoTemplate	mongoTemplate;
	@Autowired private ISequenceCounterService	sequenceCounterService;

	@Autowired
	private UserProfilePhotoRepository UserProfilePhotoRepository;
	
	@Autowired private UserProfileDocumentRepository	userProfileDocumentRepository;
	
	@Autowired private UserProfilePhotoRepository	userProfilePhotoRepository;
	@Autowired private TwoFactorAuthenticationDetailsRepository	authenticationDetailsRepository;

	private static final int PAGE_SIZE = 10;
	
	private static final int  DAYS_IN_WEEK	= 7;
	
	private static final int  DAYS_IN_MONTH	= 30;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * registerNewUserProfile(org.fleetop.persistence.model.UserProfile)
	 */
	@Transactional
	public UserProfile registerNewUserProfile(UserProfile accountDto) throws EmailExistsException {

		return userProfileRepository.save(accountDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IUserProfileService#UpdateUserProfile
	 * (org.fleetop.persistence.model.UserProfile)
	 */
	@Transactional
	public UserProfile UpdateUserProfile(UserProfile accountDto) throws EmailExistsException {

		return userProfileRepository.save(accountDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * findUserProfileByUser_email(java.lang.String)
	 */
	@Transactional
	public UserProfileDto findUserProfileByUser_email(String email) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query query = entityManager.createQuery(
				"SELECT f.userprofile_id, U.email, U.firstName, U.lastName, f.photo_id, "
						+ " f.designation, D.depart_name, B.branch_name, f.company_id, f.user_id, C.company_name,"
						+ " C.company_address, C.company_email from UserProfile AS f "
						+ " INNER JOIN User AS U ON U.id = f.user_id"
						+ " INNER JOIN Company AS C ON C.company_id = f.company_id"
						+ " LEFT JOIN Department AS D ON D.depart_id = f.department_id"
						+ " INNER JOIN Branch AS B ON B.branch_id = f.branch_id where U.email = :email AND f.company_id = :company");

		query.setParameter("email", email);
		query.setParameter("company", userDetails.getCompany_id());
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto select = new UserProfileDto();
		if (user != null) {
			select.setUserprofile_id((Long) user[0]);
			select.setUser_email((String) user[1]);
			select.setFirstName((String) user[2]);
			select.setLastName((String) user[3]);
			select.setPhoto_id((Long) user[4]);
			select.setDesignation((String) user[5]);
			select.setDepartment_name((String) user[6]);
			select.setBranch_name((String) user[7]);

			Integer Company_ID = (Integer) user[8];
			select.setCompany_id(Company_ID);
			try {
				Base64.Encoder encoder = Base64.getEncoder();
				String normalString = "" + Company_ID;
				String encodedString = encoder.encodeToString(normalString.getBytes(StandardCharsets.UTF_8));
				select.setCompany_id_encode(encodedString);
			} catch (Exception e) {
				e.printStackTrace();
			}

			select.setUser_id((Long) user[9]);
			select.setCompany_name((String) user[10]);
			select.setCompany_address((String) user[11]);
			select.setCompanyEmail((String) user[12]);
		}
		
		
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * getUserProfileByID(long)
	 */
	@Transactional
	public UserProfile getUserProfileByID(long id, Integer companyId) {

		return userProfileRepository.getUserProfileByID(id, companyId);
	}
	@Transactional
	public UserProfile getUserProfileByUser_id(long id, Integer companyId) {
		
		return userProfileRepository.getUserProfileByUser_id(id, companyId);
	}
	
	@Override
	public boolean isTwoFactorAuthEnabled(Long id) throws Exception {
		
		return userProfileRepository.isTwoFactorAuthEnabled(id);
	}
	
	@Override
	public boolean isOTPValidationRequired(Long id) throws Exception {
		TwoFactorAuthenticationDetails		authenticationDetails		= null;
		Date								currentDate					= null;
		boolean								isOTPValidationRequired		= false;
		try {
			currentDate	= new Date();
			
			SimpleDateFormat	format = new SimpleDateFormat("yyyy-MM-dd");
			if(isTwoFactorAuthEnabled(id)) {
				authenticationDetails	= authenticationDetailsRepository.getTwoFactorAuthenticationDetails(id);
				if(authenticationDetails != null && authenticationDetails.getOtpValidatedOn() != null) {
					
					long diff = DateTimeUtility.getDayDiffBetweenTwoDatesWithABS(new Timestamp(currentDate.getTime()), new Timestamp(authenticationDetails.getOtpValidatedOn().getTime()));
					
					if(authenticationDetails.getOtpRequiredType() == OTPRequiredTypeConstant.OTP_REQUIRED_TYPE_EVERY_LOGIN) {
						isOTPValidationRequired	= true;
					}else if(authenticationDetails.getOtpRequiredType() == OTPRequiredTypeConstant.OTP_REQUIRED_TYPE_ONCE_A_DAY) {
						if(!format.format(currentDate).equalsIgnoreCase(format.format(authenticationDetails.getOtpValidatedOn()))) {
							isOTPValidationRequired	= true;
						}
					}else if(authenticationDetails.getOtpRequiredType() == OTPRequiredTypeConstant.OTP_REQUIRED_TYPE_ONCE_A_WEEK) {
						if((int)diff > DAYS_IN_WEEK) {
							isOTPValidationRequired	= true;
						}
					}else if(authenticationDetails.getOtpRequiredType() == OTPRequiredTypeConstant.OTP_REQUIRED_TYPE_ONCE_A_MONTH) {
						if((int)diff > DAYS_IN_MONTH) {
							isOTPValidationRequired	= true;
						}
					}
				}else {
					isOTPValidationRequired	= true;
				}
			}
			return isOTPValidationRequired;
		} catch (Exception e) {
			throw e;
		}finally {
			authenticationDetails		= null;
			currentDate					= null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * getUserProfileByID(long)
	 */
	@Transactional
	public UserProfileDto getUserProfileByUser_id(long id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		//return userProfileRepository.getUserProfileByUser_id(id, userDetails.getCompany_id());
		 
		
		//Og Code Start
		Query query = entityManager.createQuery("SELECT f.userprofile_id, U.email, U.id, U.firstName, U.lastName,"
				+ " f.personal_email, f.sex, f.dateofbirth, f.designation, f.employes_id, f.department_id, DP.depart_name,"
				+ " CO.company_name, f.company_id, f.branch_id, B.branch_name, f.home_number, f.mobile_number, f.work_number,"
				+ " f.address_line1, f.city, f.state, f.country, f.pincode, f.emergency_person, f.emergency_number, f.esi_number,"
				+ " f.pf_number, f.insurance_number, f.working_time_from, f.working_time_to, f.subscribe, f.photo_id, f.vendorId,"
				+ " VN.vendorName, f.createdBy, f.lastModifiedBy, f.created, f.lastupdated,f.role_id, f.isTwoFactorLogin, f.lastLoginDate,"
				+ " f.lastLoginIP, f.rfidCardNo"
				+ " from UserProfile AS f "
				+ " INNER JOIN User AS U ON U.id = f.user_id "
				+ " INNER JOIN Branch B ON B.branch_id = f.branch_id"
				+ " INNER JOIN Company CO ON CO.company_id = f.company_id"
				+ " LEFT JOIN Department DP ON DP.depart_id = f.department_id"
				+ " LEFT JOIN Vendor VN ON VN.vendorId = f.vendorId"
				+ "  where U.id = :ID AND f.company_id = :company_id AND f.markForDelete = 0");
		//Og Code End
		
		
		
		
		query.setParameter("ID", id);
		query.setParameter("company_id", userDetails.getCompany_id());
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto select = new UserProfileDto();
		if (user != null) {
			select.setUserprofile_id((Long) user[0]);
			select.setUser_email((String) user[1]);
			select.setUser_id((Long) user[2]);
			select.setFirstName((String) user[3]);
			select.setLastName((String) user[4]);
			select.setPersonal_email((String) user[5]);
			select.setSex((String) user[6]);
			if(user[7] != null)
			 select.setDateofbirth(dateFormat.format(user[7]));
			select.setDesignation((String) user[8]);
			select.setEmployes_id((String) user[9]);
			select.setDepartment_id((Integer) user[10]);
			select.setDepartment_name((String) user[11]);
			select.setCompany_name((String) user[12]);
			select.setCompany_id((Integer) user[13]);
			select.setBranch_id((Integer) user[14]);
			select.setBranch_name((String) user[15]);
			select.setHome_number((String) user[16]);
			select.setMobile_number((String) user[17]);
			select.setWork_number((String) user[18]);
			select.setAddress_line1((String) user[19]);
			select.setCity((String) user[20]);
			select.setState((String) user[21]);
			select.setCountry((String) user[22]);
			select.setPincode((Integer) user[23]);
			select.setEmergency_person((String) user[24]);
			select.setEmergency_number((String) user[25]);
			select.setEsi_number((String) user[26]);
			select.setPf_number((String) user[27]);
			select.setInsurance_number((String) user[28]);
			select.setWorking_time_from((String) user[29]);
			select.setWorking_time_to((String) user[30]);
			select.setSubscribe((String) user[31]);
			select.setPhoto_id((Long) user[32]);
			select.setVendorId((Integer) user[33]);
			select.setVendorName((String) user[34]);
			select.setCreatedBy((String) user[35]);
			select.setLastModifiedBy((String) user[36]);
			if(user[37] != null)
			 select.setCreated(CreatedDateTime.format(user[37]));
			if(user[38] != null)
			 select.setLastupdated(CreatedDateTime.format(user[38]));
			if(user[39] != null)
			select.setRole_id((Long) user[39]);
			select.setTwoFactorLogin((boolean) user[40]);
			select.setLastLoginDate((Date) user[41]);
			
			if(user[42] != null)
				select.setLastLoginIP((String) user[42]);
			
			if(select.getLastLoginDate() != null) {
				select.setLastLoginDateStr(dateTimeFormat.format(select.getLastLoginDate()));	
			}else {
				select.setLastLoginDateStr("--");
			}
			if(user[43] != null) {
				select.setRfidCardNo((String) user[43]);
			}
			
		}
		return select;
	}

	
	@Transactional
	public UserProfileDto get_editUserProfileByUser_id(long id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		Query query = entityManager.createQuery("SELECT f.userprofile_id, U.email, U.firstName, U.lastName, f.user_id,"
				+ " f.company_id, f.department_id, f.branch_id ,f.designation, f.sex, f.personal_email, f.home_number,"
				+ " f.mobile_number, f.work_number, f.address_line1, f.country, f.state, f.city, f.pincode, f.emergency_person, f.emergency_number,"
				+ " f.employes_id, f.working_time_from, f.working_time_to, f.esi_number, f.pf_number, f.insurance_number, f.vendorId,"
				+ " f.subscribe, f.photo_id, f.dateofbirth from UserProfile AS f "
				+ " INNER JOIN User AS U ON U.id = f.user_id"
				+ "  where f.user_id = :ID AND f.company_id = :company_id AND f.markForDelete = 0");
		query.setParameter("ID", id);
		query.setParameter("company_id", userDetails.getCompany_id());
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto userProfile = new UserProfileDto();
		
		if (user != null) {
			
			userProfile.setUserprofile_id((Long) user[0]);
			userProfile.setUser_email((String) user[1]);
			userProfile.setFirstName((String) user[2]);
			userProfile.setLastName((String) user[3]);
			userProfile.setUser_id((Long) user[4]);
			userProfile.setCompany_id((Integer) user[5]);
			userProfile.setDepartment_id((Integer) user[6]);
			userProfile.setBranch_id((Integer) user[7]);
			userProfile.setDesignation((String) user[8]);
			userProfile.setSex((String) user[9]);
			userProfile.setPersonal_email((String) user[10]);
			userProfile.setHome_number((String) user[11]);
			userProfile.setMobile_number((String) user[12]);
			userProfile.setWork_number((String) user[13]);
			userProfile.setAddress_line1((String) user[14]);
			userProfile.setCountry((String) user[15]);
			userProfile.setState((String) user[16]);
			userProfile.setCity((String) user[17]);
			userProfile.setPincode((Integer) user[18]);

			userProfile.setEmergency_person((String) user[19]);
			userProfile.setEmergency_number((String) user[20]);

			userProfile.setEmployes_id((String) user[21]);
			userProfile.setWorking_time_from((String) user[22]);
			userProfile.setWorking_time_to((String) user[23]);
			userProfile.setEsi_number((String) user[24]);
			userProfile.setPf_number((String) user[25]);
			userProfile.setInsurance_number((String) user[26]);
			userProfile.setVendorId((Integer) user[27]);
			userProfile.setSubscribe((String) user[28]);
			userProfile.setPhoto_id((Long) user[29]);

			if ((Date) user[30] != null) {
				try {
					userProfile.setDateofbirth(dateFormat.format((Date) user[30]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		}
		return userProfile;
	}
	
	
	@Transactional
	public UserProfileDto get_ShowUserProfileByUser_id(long id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		Query query = entityManager.createQuery("SELECT f.userprofile_id, U.email, U.firstName, U.lastName, f.user_id,"
				+ " f.company_id, f.department_id, f.branch_id ,f.designation, f.sex, f.personal_email, f.home_number,"
				+ " f.mobile_number, f.work_number, f.address_line1, f.country, f.state, f.city, f.pincode, f.emergency_person, f.emergency_number,"
				+ " f.employes_id, f.working_time_from, f.working_time_to, f.esi_number, f.pf_number, f.insurance_number, f.vendorId,"
				+ " f.subscribe, f.photo_id, f.dateofbirth, B.branch_name, D.depart_name, C.company_name  from UserProfile AS f "
				+ " INNER JOIN User AS U ON U.id = f.user_id"
				+ " INNER JOIN Branch AS B ON B.branch_id = f.branch_id "
				+ " INNER JOIN Company AS C ON C.company_id = f.company_id "
				+ " LEFT JOIN Department D ON D.depart_id = f.department_id "
				+ "  where f.user_id = :ID AND f.company_id = :company_id AND f.markForDelete = 0");
		query.setParameter("ID", id);
		query.setParameter("company_id", userDetails.getCompany_id());
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto userProfile = new UserProfileDto();
		
		if (user != null) {
			
			userProfile.setUserprofile_id((Long) user[0]);
			userProfile.setUser_email((String) user[1]);
			userProfile.setFirstName((String) user[2]);
			userProfile.setLastName((String) user[3]);
			userProfile.setUser_id((Long) user[4]);
			userProfile.setCompany_id((Integer) user[5]);
			userProfile.setDepartment_id((Integer) user[6]);
			userProfile.setBranch_id((Integer) user[7]);
			userProfile.setDesignation((String) user[8]);
			userProfile.setSex((String) user[9]);
			userProfile.setPersonal_email((String) user[10]);
			userProfile.setHome_number((String) user[11]);
			userProfile.setMobile_number((String) user[12]);
			userProfile.setWork_number((String) user[13]);
			userProfile.setAddress_line1((String) user[14]);
			userProfile.setCountry((String) user[15]);
			userProfile.setState((String) user[16]);
			userProfile.setCity((String) user[17]);
			userProfile.setPincode((Integer) user[18]);

			userProfile.setEmergency_person((String) user[19]);
			userProfile.setEmergency_number((String) user[20]);

			userProfile.setEmployes_id((String) user[21]);
			userProfile.setWorking_time_from((String) user[22]);
			userProfile.setWorking_time_to((String) user[23]);
			userProfile.setEsi_number((String) user[24]);
			userProfile.setPf_number((String) user[25]);
			userProfile.setInsurance_number((String) user[26]);
			userProfile.setVendorId((Integer) user[27]);
			userProfile.setSubscribe((String) user[28]);
			userProfile.setPhoto_id((Long) user[29]);

			if ((Date) user[30] != null) {
				try {
					userProfile.setDateofbirth(dateFormat.format((Date) user[30]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			userProfile.setBranch_name((String) user[31]);
			userProfile.setDepartment_name((String) user[32]);
			userProfile.setCompany_name((String) user[33]);
			
		
		}
		return userProfile;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * findAllUserProfile()
	 */
	@Transactional
	public List<UserProfile> findAllUserProfile() {

		return userProfileRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#count()
	 */
	@Transactional
	public long count() {

		return userProfileRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IUserProfileService#changeUserActive(
	 * long, java.lang.Integer)
	 */
	@Transactional
	public void changeUserActive(long id, boolean status, Integer company_id) {

		userProfileRepository.changeUserActive(id, status, company_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * changeUserInActive(long, java.lang.Integer)
	 */
	@Transactional
	public void changeUserInActive(long id, boolean status, Integer company_id) {

		userProfileRepository.changeUserINActive(id, status, company_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * saveUserProfileDocument(org.fleetop.persistence.model. UserProfileDocument)
	 */
	@Transactional
	public void saveUserProfileDocument(org.fleetopgroup.persistence.document.UserProfileDocument UserProfileDocument) {

		UserProfileDocument.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_USER_PROFILE_DOCUMENT));
		mongoTemplate.save(UserProfileDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * updateUserProfileDocument(org.fleetop.persistence.model. UserProfileDocument)
	 */
	@Transactional
	public void updateUserProfileDocument(org.fleetopgroup.persistence.document.UserProfileDocument UserProfileDocument) {
		mongoTemplate.save(UserProfileDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * listUserProfileDocument(int)
	 */
	@Transactional
	public List<org.fleetopgroup.persistence.document.UserProfileDocument> listUserProfileDocument(Long UserProfile_id, Integer companyId) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("userprofile_id").is(UserProfile_id).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.UserProfileDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * getUserProfileDocuemnt(int)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.UserProfileDocument getUserProfileDocuemnt(Long UserProfile_docid, Integer companyId) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(UserProfile_docid).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.UserProfileDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * deleteUserProfileDocument(java.lang.Integer)
	 */
	@Transactional
	public void deleteUserProfileDocument(Long documentid, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query(Criteria.where("_id").is(documentid).and("companyId").is(companyId));
		Update update = new Update();
		update.set("markForDelete", true);
		mongoTemplate.updateFirst(query, update, org.fleetopgroup.persistence.document.UserProfileDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * getUserProfileDocumentCount(int)
	 */
	@Transactional
	public Long getUserProfileDocumentCount(Long UserProfile_docid) {
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * addUserProfilePhoto(org.fleetop.persistence.model.UserProfilePhoto)
	 */
	@Transactional
	public void addUserProfilePhoto(org.fleetopgroup.persistence.document.UserProfilePhoto diverPhoto) {

		diverPhoto.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_USER_PROFILE_PHOTO));
		mongoTemplate.save(diverPhoto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * updateUserProfilePhoto(org.fleetop.persistence.model.UserProfilePhoto)
	 */
	@Transactional
	public void updateUserProfilePhoto(org.fleetopgroup.persistence.document.UserProfilePhoto diverPhoto) {
		mongoTemplate.save(diverPhoto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * getUserProfilePhoto(int)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.UserProfilePhoto getUserProfilePhoto(Long UserProfile_photoid) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(UserProfile_photoid));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.UserProfilePhoto.class);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#getPhotoCount(
	 * int)
	 */
	@Transactional
	public Long getPhotoCount(Long UserProfile_docid) {

		return UserProfilePhotoRepository.getPhotoCount(UserProfile_docid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * findUserProfilePhotoByUserProfile_id(java.lang.Long)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.UserProfilePhoto findUserProfilePhotoByUserProfile_id(Long profile_id) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("userprofile_id").is(profile_id));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.UserProfilePhoto.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * UpdateUserProfile_photo_id(long, java.lang.Long)
	 */
	@Transactional
	public void UpdateUserProfile_photo_id(long id, Long photo_id, Integer companyId) {

		userProfileRepository.UpdateUserProfile_photo_id(id, photo_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * SearchUserEmail_id_and_Name(java.lang.String)
	 */
	@Transactional
	public List<UserProfileDto> SearchUserEmail_id_and_Name(String term, Integer companyId) {

		List<UserProfileDto> Dtos = null;
		if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT f.userprofile_id, U.email, U.firstName, U.lastName, f.user_id from UserProfile as f "
						+ " INNER JOIN User AS U ON U.id = f.user_id" + " where f.company_id = " + companyId
						+ " AND f.markForDelete = 0 AND ( lower(U.email) Like (:term)  OR  lower(U.firstName) Like (:term)) ",
				Object[].class);
		
		
		query.setParameter("term", "%"+term+"%");
		
		List<Object[]> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<UserProfileDto>();
			UserProfileDto user = null;
			for (Object[] result : results) {
				user = new UserProfileDto();

				user.setUserprofile_id((Long) result[0]);
				user.setUser_email((String) result[1]);
				user.setFirstName((String) result[2]);
				if(result[3] != null)
					user.setLastName((String) result[3]);
				else
					user.setLastName("");
				
				user.setUser_id((Long) result[4]);

				Dtos.add(user);
			}
		}
		}
		return Dtos;
	}
	
	@Override
	public List<UserProfileDto> SearchUserEmail_id_and_Name(Integer branchId, Integer companyId) {

		List<UserProfileDto> Dtos = null;
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT f.userprofile_id, U.email, U.firstName, U.lastName, f.user_id from UserProfile as f "
						+ " INNER JOIN User AS U ON U.id = f.user_id" + " where f.company_id = " + companyId
						+ " AND f.markForDelete = 0 AND f.branch_id = :branchId ",
				Object[].class);
		
		
		query.setParameter("branchId", branchId);
		
		List<Object[]> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<UserProfileDto>();
			UserProfileDto user = null;
			for (Object[] result : results) {
				user = new UserProfileDto();

				user.setUserprofile_id((Long) result[0]);
				user.setUser_email((String) result[1]);
				user.setFirstName((String) result[2]);
				if(result[3] != null)
					user.setLastName((String) result[3]);
				else
					user.setLastName("");
				
				user.setUser_id((Long) result[4]);

				Dtos.add(user);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * getUser_email_to_subscribe_Detils(java.lang.String)
	 */
	@Transactional
	public UserProfileDto getUser_email_to_subscribe_Detils(String email) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query query = entityManager.createQuery("SELECT f.userprofile_id, U.email, f.subscribe from UserProfile AS f "
				+ " INNER JOIN User AS U ON U.id = f.user_id"
				+ "  where U.email = :email AND f.company_id = :company_id AND f.markForDelete = 0");

		query.setParameter("email", email);
		query.setParameter("company_id", userDetails.getCompany_id());
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto select = new UserProfileDto();
		if (user != null) {
			select.setUserprofile_id((Long) user[0]);
			select.setUser_email((String) user[1]);
			select.setSubscribe((String) user[2]);
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * get_UserProfile_Issues_Details(java.lang.String)
	 */
	@Transactional
	public UserProfileDto get_UserProfile_Issues_Details(String email) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Object[] user = null;
		Query query = entityManager.createQuery("SELECT UP.userprofile_id, U.email, U.firstName, U.id ,U.lastName from UserProfile AS UP"
				+ " INNER JOIN User AS U ON U.id = UP.user_id"
				+ " where U.email = :email AND UP.company_id = :company_id AND UP.markForDelete = 0");

		query.setParameter("email", email);
		query.setParameter("company_id", userDetails.getCompany_id());
		try {
			 user = (Object[]) query.getSingleResult();
		} catch (NoResultException e) {
			
		}

		UserProfileDto select = new UserProfileDto();
		if (user != null) {
			select.setUserprofile_id((Long) user[0]);
			select.setUser_email((String) user[1]);
			select.setFirstName((String) user[2]);
			select.setUser_id((Long) user[3]);
			select.setLastName((String) user[4]);
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * get_UserProfile_Issues_Details(java.lang.String)
	 */
	@Transactional
	public UserProfileDto get_UserProfile_Show_All_Details(String email) {

		Query query = entityManager.createQuery(
				"SELECT f.userprofile_id, U.email, U.firstName, U.lastName, f.photo_id from UserProfile AS f"
						+ " INNER JOIN User AS U ON U.id = UP.user_id"
						+ " where U.email = :email");

		query.setParameter("email", email);
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto select = new UserProfileDto();
		if (user != null) {
			select.setUserprofile_id((Long) user[0]);
			select.setUser_email((String) user[1]);
			select.setFirstName((String) user[2]);
			select.setLastName((String) user[3]);
			select.setPhoto_id((Long) user[4]);
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * get_UserProfile_Issues_Details(java.lang.String)
	 */
	@Transactional
	public UserProfileDto get_UserProfile_WORKORDER_LOCATION_Details(String email) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query query = entityManager.createQuery(
				"SELECT f.userprofile_id, U.email, U.firstName, f.branch_id, B.branch_name from UserProfile AS f "
						+ " INNER JOIN User AS U ON U.id = f.user_id"
						+ " INNER JOIN Branch AS B ON B.branch_id = f.branch_id "
						+ " where U.email = :email AND f.company_id = :company_id AND f.markForDelete = 0");

		query.setParameter("email", email);
		query.setParameter("company_id", userDetails.getCompany_id());
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto select = new UserProfileDto();
		if (user != null) {
			select.setUserprofile_id((Long) user[0]);
			select.setUser_email((String) user[1]);
			select.setFirstName((String) user[2]);
			select.setBranch_id((Integer) user[3]);
			select.setBranch_name((String) user[4]);
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * show_user_profile_picture(java.lang.String)
	 */
	@Transactional
	public UserProfilePhoto show_user_profile_picture(String User_emailId) {

		Query query = entityManager.createQuery(
				"FROM UserProfilePhoto AS p WHERE p.photoid IN (SELECT f.photo_id from UserProfile AS f where f.user_email = :email) ");
		query.setParameter("email", User_emailId);
		UserProfilePhoto photo = (UserProfilePhoto) query.getSingleResult();

		return photo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IUserProfileService#list_UserProfile(
	 * java.lang.Integer)
	 */
	@Transactional
	public List<UserProfileDto> list_UserProfile(Integer pageNumber) throws Exception {
		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager
					.createQuery("SELECT R.user_id, U.email, U.firstName, U.lastName, C.company_name, D.depart_name, "
							+ " B.branch_name, R.home_number, R.mobile_number, R.work_number, R.markForDelete, R.lastLoginDate, R.lastLoginIP FROM UserProfile AS R"
							+ " INNER JOIN User AS U ON U.id = R.user_id"
							+ " INNER JOIN Company C ON C.company_id = R.company_id"
							+ " LEFT JOIN Department D ON D.depart_id = R.department_id "
							+ " INNER JOIN Branch B ON B.branch_id = R.branch_id  WHERE R.company_id = "
							+ currentUser.getCompany_id() + "  ORDER BY R.userprofile_id desc", Object[].class);

			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<UserProfileDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<UserProfileDto>();
				UserProfileDto list = null;
				for (Object[] result : results) {
					list = new UserProfileDto();

					list.setUser_id((Long) result[0]);
					list.setUser_email((String) result[1]);
					list.setFirstName((String) result[2]);
					list.setLastName((String) result[3]);
					list.setCompany_name((String) result[4]);
					list.setDepartment_name((String) result[5]);
					list.setBranch_name((String) result[6]);
					list.setHome_number((String) result[7]);
					list.setMobile_number((String) result[8]);
					list.setWork_number((String) result[9]);
					list.setMarkForDelete((boolean) result[10]);
					list.setLastLoginDate((Date) result[11]);
					if(result[12] != null)
						list.setLastLoginIP((String) result[12]);
					
					if(list.getLastLoginDate() != null) {
						list.setLastLoginDateStr(dateTimeFormat.format(list.getLastLoginDate()));	
					}else {
						
						list.setLastLoginDateStr("--");
					}
					if(!list.getFirstName().equalsIgnoreCase("admin") || currentUser.getFirstName().equalsIgnoreCase("admin")) {
						Dtos.add(list);
					}
				}
			}
			return Dtos;
		} catch (

		Exception e) {
			throw e;
		} finally {
			queryt = null;
		}

	}

	@Transactional
	public List<UserProfileDto> list_UserProfile_Master_User(Integer Comapny_ID) throws Exception {
		/* this only Select column */
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT f.userprofile_id, U.email, U.id, U.firstName, U.lastName,"
						+ " f.personal_email, f.sex, f.dateofbirth, f.designation, f.employes_id, f.department_id, DP.depart_name,"
						+ " CO.company_name, f.company_id, f.branch_id, B.branch_name, f.home_number, f.mobile_number, f.work_number,"
						+ " f.address_line1, f.city, f.state, f.country, f.pincode, f.emergency_person, f.emergency_number, f.esi_number,"
						+ " f.pf_number, f.insurance_number, f.working_time_from, f.working_time_to, f.subscribe, f.photo_id, f.vendorId,"
						+ " VN.vendorName, f.createdBy, f.lastModifiedBy, f.created, f.lastupdated"
						+ " from UserProfile AS f "
						+ " INNER JOIN User AS U ON U.id = f.user_id "
						+ " INNER JOIN Branch B ON B.branch_id = f.branch_id"
						+ " INNER JOIN Company CO ON CO.company_id = f.company_id"
						+ " LEFT JOIN Department DP ON DP.depart_id = f.department_id"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = f.vendorId"
						+ "  where f.company_id = "+Comapny_ID+" AND f.markForDelete = 0",
						Object[].class);
		
		//queryt.setParameter("company_id", Comapny_ID);
		List<Object[]> results = queryt.getResultList();

		List<UserProfileDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<UserProfileDto>();
			UserProfileDto select = null;
			for (Object[] user : results) {
				
				select	= new UserProfileDto();
				select.setUserprofile_id((Long) user[0]);
				select.setUser_email((String) user[1]);
				select.setUser_id((Long) user[2]);
				select.setFirstName((String) user[3]);
				select.setLastName((String) user[4]);
				select.setPersonal_email((String) user[5]);
				select.setSex((String) user[6]);
				if(user[7] != null)
				 select.setDateofbirth(dateFormat.format(user[7]));
				select.setDesignation((String) user[8]);
				select.setEmployes_id((String) user[9]);
				select.setDepartment_id((Integer) user[10]);
				select.setDepartment_name((String) user[11]);
				select.setCompany_name((String) user[12]);
				select.setCompany_id((Integer) user[13]);
				select.setBranch_id((Integer) user[14]);
				select.setBranch_name((String) user[15]);
				select.setHome_number((String) user[16]);
				select.setMobile_number((String) user[17]);
				select.setWork_number((String) user[18]);
				select.setAddress_line1((String) user[19]);
				select.setCity((String) user[20]);
				select.setState((String) user[21]);
				select.setCountry((String) user[22]);
				select.setPincode((Integer) user[23]);
				select.setEmergency_person((String) user[24]);
				select.setEmergency_number((String) user[25]);
				select.setEsi_number((String) user[26]);
				select.setPf_number((String) user[27]);
				select.setInsurance_number((String) user[28]);
				select.setWorking_time_from((String) user[29]);
				select.setWorking_time_to((String) user[30]);
				select.setSubscribe((String) user[31]);
				select.setPhoto_id((Long) user[32]);
				select.setVendorId((Integer) user[33]);
				select.setVendorName((String) user[34]);
				select.setCreatedBy((String) user[35]);
				select.setLastModifiedBy((String) user[36]);
				if(user[37] != null)
				 select.setCreated(CreatedDateTime.format(user[37]));
				if(user[38] != null)
				 select.setLastupdated(CreatedDateTime.format(user[38]));
				
				Dtos.add(select);
			}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * getDeployment_Page_UserProfile(java.lang.Integer)
	 */
	@Transactional
	public Page<UserProfile> getDeployment_Page_UserProfile(Integer pageNumber) {

		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "created");

		return userProfileRepository.findAllByCompanyId(currentUser.getCompany_id(), request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * Search_list_UserProfile(java.lang.String)
	 */
	@Transactional
	public List<UserProfileDto> Search_list_UserProfile(String SearchQuery, Integer company_Id) throws Exception {

		CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		TypedQuery<Object[]> queryt = null;
		List<UserProfileDto> Dtos = null;
		try {
			/* this only Select column */
			if(SearchQuery != null && !SearchQuery.trim().equalsIgnoreCase("") && SearchQuery.indexOf('\'') != 0 ) {
			queryt = entityManager.createQuery(
					"SELECT R.user_id, U.email, U.firstName, U.lastName, C.company_name, D.depart_name, "
							+ " B.branch_name, R.home_number, R.mobile_number, R.work_number, R.markForDelete FROM UserProfile AS R"
							+ " INNER JOIN Company C ON C.company_id = R.company_id"
							+ " INNER JOIN Branch B ON B.branch_id = R.branch_id "
							+ " INNER JOIN User U ON U.id = R.user_id "
							+ " LEFT JOIN Department D ON D.depart_id = R.department_id "
							+" WHERE ( lower(U.email) Like ('"
							+ SearchQuery + "%')  OR  lower(U.firstName) Like ('" + SearchQuery
							+ "%')) AND R.company_id = " + currentUser.getCompany_id()
							+ "  ORDER BY R.userprofile_id desc",
					Object[].class);

			queryt.setMaxResults(50);
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<UserProfileDto>();
				UserProfileDto list = null;
				for (Object[] result : results) {
					list = new UserProfileDto();

					list.setUser_id((Long) result[0]);
					list.setUser_email((String) result[1]);
					list.setFirstName((String) result[2]);
					list.setLastName((String) result[3]);
					list.setCompany_name((String) result[4]);
					list.setDepartment_name((String) result[5]);
					list.setBranch_name((String) result[6]);
					list.setHome_number((String) result[7]);
					list.setMobile_number((String) result[8]);
					list.setWork_number((String) result[9]);
					list.setMarkForDelete((boolean) result[10]);
					if(!list.getFirstName().equalsIgnoreCase("admin") || currentUser.getFirstName().equalsIgnoreCase("admin")) {
						Dtos.add(list);
					}
				}
			}
			}
			return Dtos;
		} catch (

		Exception e) {
			throw e;
		} finally {
			queryt = null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * GET_UserProfile_Name_PlaceinBranchname_ByUseremail(java.lang.String)
	 */
	@Transactional
	public UserProfileDto GET_UserProfile_Name_PlaceinBranchname_ByUseremail(String name) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Query query = entityManager.createQuery(
				"SELECT f.userprofile_id, U.email, U.firstName, U.lastName, B.branch_name, f.branch_id from UserProfile AS f "
						+ " INNER JOIN User U ON U.id = f.user_id "
						+ " INNER JOIN Branch AS B ON B.branch_id = f.branch_id "
						+ " where U.email = :email AND f.company_id = :company_id "
						+ " AND f.markForDelete = 0");

		query.setParameter("email", name);
		query.setParameter("company_id", userDetails.getCompany_id());
		query.setMaxResults(1);
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto select = new UserProfileDto();
		if (user != null) {
			select.setUserprofile_id((Long) user[0]);
			select.setUser_email((String) user[1]);
			select.setFirstName((String) user[2]);
			select.setLastName((String) user[3]);
			select.setBranch_name((String) user[4]);
			select.setBranch_id((Integer) user[5]);
		}
		return select;
	}
	
	@Transactional
	@Override
	public UserProfileDto getUserDeatilsByEmail(String email, int compId) throws Exception {
		Query query = entityManager.createQuery(
				"SELECT f.userprofile_id, U.email, U.firstName, U.lastName, B.branch_name, f.branch_id from UserProfile AS f "
						+ " INNER JOIN User U ON U.id = f.user_id "
						+ " INNER JOIN Branch AS B ON B.branch_id = f.branch_id "
						+ " where U.email = :email AND f.company_id = :company_id "
						+ " AND f.markForDelete = 0");

		query.setParameter("email", email);
		query.setParameter("company_id", compId);
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto select = new UserProfileDto();
		if (user != null) {
			select.setUserprofile_id((Long) user[0]);
			select.setUser_email((String) user[1]);
			select.setFirstName((String) user[2]);
			select.setLastName((String) user[3]);
			select.setBranch_name((String) user[4]);
			select.setBranch_id((Integer) user[5]);
		}
		return select;
	}

	@Override
	public UserProfileDto GET_UserProfile_Name_PlaceinBranchname_ByUseremail(Long user_id) {
		Query query = entityManager.createQuery(
				"SELECT f.userprofile_id, U.email, U.firstName, U.lastName, B.branch_name, f.branch_id,f.subscribe from UserProfile AS f "
						+ " INNER JOIN User U ON U.id = f.user_id "
						+ " INNER JOIN Branch AS B ON B.branch_id = f.branch_id "
						+ " where f.user_id = :user_id AND f.markForDelete = 0");

		query.setParameter("user_id", user_id);
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto select = new UserProfileDto();
		if (user != null) {
			select.setUserprofile_id((Long) user[0]);
			select.setUser_email((String) user[1]);
			select.setFirstName((String) user[2]);
			select.setLastName((String) user[3]);
			select.setBranch_name((String) user[4]);
			select.setBranch_id((Integer) user[5]);
			select.setSubscribe((String) user[6]);
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IUserProfileService#
	 * Update_UserID_To_VendorLink_Details(java.lang.Integer, java.lang.String,
	 * java.lang.Long)
	 */
	@Transactional
	public void Update_UserID_To_VendorLink_Details(Integer vendorId, String vendorName, Long user_id,
			Integer companyId) {

		userProfileRepository.Update_UserID_To_VendorLink_Details(vendorId, vendorName, user_id, companyId);
	}

	@Override
	@Transactional
	public UserProfileDto findUserProfileByUser_email_Company_ESI_PF_DIABLE(Long user_id) {

		Query query = entityManager.createQuery(
				"SELECT R.userprofile_id, U.email, U.firstName, U.lastName, R.company_id, C.company_name, R.branch_id, B.branch_name, D.depart_name, C.company_esi_pf_days, C.company_esi_pf_disable "
				+ "from UserProfile AS R "
				+ " INNER JOIN Company AS C ON C.company_id = R.company_id"
				+ " INNER JOIN Branch AS B ON B.branch_id = R.branch_id "
				+ " INNER JOIN User AS U ON U.id = R.user_id "
				+ " LEFT JOIN Department D ON D.depart_id = R.department_id "
				+ " where R.user_id = :id");

		query.setParameter("id", user_id);
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto select = null;
		if (user != null) {
			select = new UserProfileDto();

			select.setUserprofile_id((Long) user[0]);
			
			select.setUser_email((String) user[1]);
			select.setFirstName((String) user[2]);
			select.setLastName((String) user[3]);
			Base64.Encoder encoder = Base64.getEncoder();
			String normalString = "" + (Integer) user[4];
			String encodedString = encoder.encodeToString(normalString.getBytes(StandardCharsets.UTF_8));
			select.setCompany_id_encode(encodedString);
			select.setCompany_name((String) user[5]);
			select.setBranch_id((Integer) user[6]);
			select.setBranch_name((String) user[7]);
			select.setDepartment_name((String) user[8]);
			select.setCompany_esi_pf_days((Long) user[9]);
			select.setCompany_esi_pf_disable((Integer) user[10]);
		}
		return select;
	}

	@Override
	public void transferUserProfileDocument(List<UserProfileDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.UserProfileDocument			userProfileDocument		= null;
		List<org.fleetopgroup.persistence.document.UserProfileDocument> 	userProfileDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				userProfileDocumentList	= new ArrayList<>();
				for(UserProfileDocument	document : list) {
					userProfileDocument	= new org.fleetopgroup.persistence.document.UserProfileDocument();
					
					userProfileDocument.set_id(document.getDocumentid());
					userProfileDocument.setDocumentname(document.getDocumentname());
					userProfileDocument.setUploaddate(document.getUploaddate());
					userProfileDocument.setUserprofile_filename(document.getUserprofile_filename());
					userProfileDocument.setUserprofile_id(document.getUserprofile_id());
					userProfileDocument.setUserprofile_content(document.getUserprofile_content());
					userProfileDocument.setUserprofile_contentType(document.getUserprofile_contentType());
					userProfileDocument.setCompanyId(document.getCompanyId());
					userProfileDocument.setMarkForDelete(document.isMarkForDelete());
					
					userProfileDocumentList.add(userProfileDocument);
				}
				System.err.println("Saving userProfileDocument ....");
				mongoTemplate.insert(userProfileDocumentList, org.fleetopgroup.persistence.document.UserProfileDocument.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}

	@Override
	public void transferUserProfilePhoto(List<UserProfilePhoto> list) throws Exception {
		org.fleetopgroup.persistence.document.UserProfilePhoto			userProfilePhoto		= null;
		List<org.fleetopgroup.persistence.document.UserProfilePhoto> 		userProfilePhotoList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				userProfilePhotoList	= new ArrayList<>();
				for(UserProfilePhoto	document : list) {
					userProfilePhoto	= new org.fleetopgroup.persistence.document.UserProfilePhoto();
					
					userProfilePhoto.set_id(document.getPhotoid());
					userProfilePhoto.setPhotoname(document.getPhotoname());
					userProfilePhoto.setUploaddate(document.getUploaddate());
					userProfilePhoto.setUserprofile_filename(document.getUserprofile_filename());
					userProfilePhoto.setUserprofile_id(document.getUserprofile_id());
					userProfilePhoto.setUserprofile_content(document.getUserprofile_content());
					userProfilePhoto.setUserprofile_contentType(document.getUserprofile_contentType());
					
					userProfilePhotoList.add(userProfilePhoto);
				}
				System.err.println("Saving userProfilePhoto ....");
				mongoTemplate.insert(userProfilePhotoList, org.fleetopgroup.persistence.document.UserProfilePhoto.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
	@Override
	public long getUserProfileDocumentMaxId() throws Exception {
		try {
			return userProfileDocumentRepository.getUserProfileDocumentMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public long getUserProfilePhotoMaxId() throws Exception {
		try {
			return userProfilePhotoRepository.getUserProfilePhotoMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public HashMap<Long ,UserProfileDto> getUserEmailIdFromUserId(String userId) throws Exception {
		try {
			
			TypedQuery<Object[]> queryt = null;
				queryt = entityManager.createQuery(
						"SELECT U.user_id, U.personal_email, U.mobile_number, us.firstName, us.email "
							+" FROM UserProfile as U "
							+ " INNER JOIN User us ON us.id = U.user_id" 
							+" WHERE U.user_id IN ("+userId+") ",
						Object[].class);
			
				List<Object[]> results = queryt.getResultList();

				HashMap<Long ,UserProfileDto> dtosMap = null;
				if (results != null && !results.isEmpty()) {
					dtosMap = new HashMap<Long ,UserProfileDto>();
					UserProfileDto list = null;
					for (Object[] result : results) {
						list = new UserProfileDto();
						
						list.setUser_id((long) result[0]);
						list.setPersonal_email((String) result[1]);
						list.setMobile_number((String) result[2]);
						list.setFirstName((String) result[3]);
						list.setUser_email((String) result[4]);
						

						dtosMap.put(list.getUser_id(),list);
					}
				}
				return dtosMap;
			
		}catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject saveTwoFactorEnableDisableState(ValueObject valueObject) throws Exception {
		try {
			userProfileRepository.updateTwoFactorEnableDisableState(valueObject.getBoolean("status"), valueObject.getLong("userprofile_id"));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateLoginDetails() throws Exception {
		try {

			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HttpServletRequest request = 
			        ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
			                .getRequest();
			userProfileRepository.updateLoginDetails(new Date(), Utility.getClientIpAddr(request), userDetails.getId());
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject checkIfPasswordResetRequired(Long id, HashMap<String, Object> configuration, Integer companyId) throws Exception {
		boolean passwordChangeRequired	= false;
		UserProfile	userProfile	= userProfileRepository.getUserProfileByUser_id(id, companyId);
		ValueObject	valueObject =	new ValueObject();
		if(userProfile != null) {
			if(userProfile.getLastPasswordResetOn() != null) {
				long diff = DateTimeUtility.getDayDiffBetweenTwoDatesWithABS(new Timestamp(new Date().getTime()), new Timestamp(userProfile.getLastPasswordResetOn().getTime()));
				if((int)diff > (int)configuration.get("noOfDaysToResetPassword")) {
					passwordChangeRequired	= true;
					valueObject.put("firstTimeForceReset", false);
				}
			}else {
				passwordChangeRequired	= true;
				valueObject.put("firstTimeForceReset", true);
			}
		}
		valueObject.put("passwordChangeRequired", passwordChangeRequired);
		return valueObject;
	}
	
	@Override
	@Transactional
	public void UpdateLAstPasswordReset(Date date, Long userId) throws Exception {
		
		userProfileRepository.UpdateLAstPasswordReset(date, userId);
	}
	
	@Override
	public ValueObject searchUserListInMobile(ValueObject object) throws Exception {
		List<UserProfileDto>   userList 			= null;
		List<UserProfileDto>   user					= null;
		try {
			userList 	= new ArrayList<UserProfileDto>();
			user 		= SearchUserEmail_id_and_Name(object.getString("term"), object.getInt("companyId"));
			
			if(user != null && !user.isEmpty()) {
				for(UserProfileDto add : user) {
					
					UserProfileDto wadd = new UserProfileDto();
					wadd.setUser_email(add.getUser_email());
					wadd.setFirstName(add.getFirstName());
					wadd.setLastName(add.getLastName());
					wadd.setUser_id(add.getUser_id());
					
					userList.add(wadd);
				}
			}

			object.put("UserList", userList);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			user 		 = null;
			userList 	 = null;
			object  	 = null;
		}
	}
	@Transactional
	public UserProfileDto get_UserProfile_Issues_Details(String email,Integer companyId) {
		Query query = entityManager.createQuery("SELECT UP.userprofile_id, U.email, U.firstName, U.id ,U.lastName from UserProfile AS UP"
				+ " INNER JOIN User AS U ON U.id = UP.user_id"
				+ " where U.email = :email AND UP.company_id = :company_id AND UP.markForDelete = 0");

		query.setParameter("email", email);
		query.setParameter("company_id", companyId);
		Object[] user = (Object[]) query.getSingleResult();

		UserProfileDto select = new UserProfileDto();
		if (user != null) {
			select.setUserprofile_id((Long) user[0]);
			select.setUser_email((String) user[1]);
			select.setFirstName((String) user[2]);
			select.setUser_id((Long) user[3]);
			select.setLastName((String) user[4]);
		}
		return select;
	}
	
	@Transactional
	public List<UserProfileDto> getAllUserListByCompanyId(String term,Integer companyId) {
		
		String searchQuery = "SELECT f.userprofile_id, U.email, U.firstName, U.lastName, f.user_id from UserProfile as f "
				+ " INNER JOIN User AS U ON U.id = f.user_id" + " where f.company_id = " + companyId
				+ " AND f.markForDelete = 0  ";
		if(!term.trim().equals("")) {
			searchQuery += " AND ( lower(U.email) Like (:term)  OR  lower(U.firstName) Like (:term)) ";
		}
		

		List<UserProfileDto> Dtos = null;
		TypedQuery<Object[]> query = entityManager.createQuery(searchQuery,Object[].class);
		if(!term.trim().equals("")) {
		query.setParameter("term", "%"+term+"%");
		}
		
		List<Object[]> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<>();
			UserProfileDto user = null;
			for (Object[] result : results) {
				user = new UserProfileDto();

				user.setUserprofile_id((Long) result[0]);
				user.setUser_email((String) result[1]);
				user.setFirstName((String) result[2]);
				user.setLastName((String) result[3]);
				user.setUser_id((Long) result[4]);

				Dtos.add(user);
			}
		}
		return Dtos;
	}
	@Transactional
	public List<UserProfileDto> getBranchWiseUserList(String term,Integer companyId,Long branchId) {
		List<UserProfileDto> Dtos = new ArrayList<>();

		try {
			
		
		String searchQuery = "SELECT f.userprofile_id, U.email, U.firstName, U.lastName, f.user_id from UserProfile as f "
				+ " INNER JOIN User AS U ON U.id = f.user_id" + " where f.company_id = " + companyId
				+ "  AND f.markForDelete = 0  ";
		if(!term.trim().equals("")) 
			searchQuery += " AND ( lower(U.email) Like (:term)  OR  lower(U.firstName) Like (:term)) ";
		
		if(branchId != null && branchId > 0)
			searchQuery +=" AND f.branch_id="+branchId+" ";
		TypedQuery<Object[]> query = entityManager.createQuery(searchQuery,Object[].class);
		if(!term.trim().equals("")) {
		query.setParameter("term", "%"+term+"%");
		}
		
		List<Object[]> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			
			UserProfileDto user = null;
			for (Object[] result : results) {
				user = new UserProfileDto();
				user.setUserprofile_id((Long) result[0]);
				user.setUser_email((String) result[1]);
				user.setFirstName((String) result[2]);
				user.setLastName((String) result[3]);
				user.setUser_id((Long) result[4]);
				Dtos.add(user);
			}
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		return Dtos;
	}
}