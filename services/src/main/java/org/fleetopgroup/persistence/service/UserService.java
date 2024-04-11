package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.PartLocationsType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.UserProfileBL;
import org.fleetopgroup.persistence.dao.BranchMapperRepository;
import org.fleetopgroup.persistence.dao.CompanyMapperRepository;
import org.fleetopgroup.persistence.dao.DepartmentRepository;
import org.fleetopgroup.persistence.dao.ExecutiveMapperTxnCheckerRepository;
import org.fleetopgroup.persistence.dao.IVFleetExecutiveMappingRepository;
import org.fleetopgroup.persistence.dao.PasswordResetTokenRepository;
import org.fleetopgroup.persistence.dao.RoleRepository;
import org.fleetopgroup.persistence.dao.UserRepository;
import org.fleetopgroup.persistence.dao.VerificationTokenRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.BranchMapper;
import org.fleetopgroup.persistence.model.CompanyMapper;
import org.fleetopgroup.persistence.model.Department;
import org.fleetopgroup.persistence.model.ExecutiveMapperTxnChecker;
import org.fleetopgroup.persistence.model.IVFleetExecutiveMapping;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.model.PasswordResetToken;
import org.fleetopgroup.persistence.model.Role;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.persistence.model.VerificationToken;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IRoleService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IUserService;
import org.fleetopgroup.validation.EmailExistsException;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements IUserService {
	@Autowired private UserRepository 				repository;
	@Autowired private VerificationTokenRepository 	tokenRepository;
	@Autowired private PasswordResetTokenRepository passwordTokenRepository;
	@Autowired private PasswordEncoder 				passwordEncoder; 
	@Autowired private RoleRepository 				roleRepository;
	@PersistenceContext EntityManager 				entityManager;
	@Autowired UserRepository						userRepository;
	@Autowired IUserProfileService 					userProfileService;
	@Autowired  private IUserService 				userService;
	@Autowired	IRoleService						roleService;
	@Autowired	private CompanyMapperRepository		companyMapperRepository;
	@Autowired	private IVFleetExecutiveMappingRepository	executiveMappingRepository;
	@Autowired	private ExecutiveMapperTxnCheckerRepository		txnCheckerRepository;
	@Autowired	private DepartmentRepository			departmentRepository;
	@Autowired	private BranchMapperRepository			branchMapperRepository;
	
	UserProfileBL userProfileBl= new UserProfileBL();

	// API

	@Transactional
	public User registerNewUserAccount(final UserDto accountDto) throws EmailExistsException {
		if (emailExist(accountDto.getEmail())) {
			throw new EmailExistsException("There is an account with that email adress: " + accountDto.getEmail());
		}
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		final User user = new User();

		user.setFirstName(accountDto.getFirstName());
		user.setLastName(accountDto.getLastName());
		user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
		user.setEmail(accountDto.getEmail());


		user.setRoles(Arrays.asList(roleRepository.findByNameAndCompanyId("ROLE_USER", userDetails.getCompany_id())));
		return repository.save(user);
	}

	@Transactional
	public User registerNewUserAccount(final User accountDto) throws EmailExistsException {

		return repository.save(accountDto);
	}

	@Transactional
	public User updateUserAccount(final User user) throws Exception {


		entityManager.merge(user);

		return user;
	}

	@Transactional
	public User getUser(final String verificationToken) {
		final User user = tokenRepository.findByToken(verificationToken).getUser();
		return user;
	}

	@Transactional
	public VerificationToken getVerificationToken(final String VerificationToken) {
		return tokenRepository.findByToken(VerificationToken);
	}

	@Transactional
	public void saveRegisteredUser(final User user) {
		repository.save(user);
	}

	@Transactional
	public void deleteUser(final User user) {
		repository.delete(user);
	}

	@Transactional
	public void createVerificationTokenForUser(final User user, final String token) {
		final VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}

	@Transactional
	public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
		VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = tokenRepository.save(vToken);
		return vToken;
	}

	@Transactional
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken);
	}

	@Transactional
	public User findUserByEmail(String email, String companyCode) {
		return repository.findUser(email, companyCode);
	}

	@Transactional
	public User findByEmail(final String email) {
		return repository.findByEmail(email);
	}

	@Transactional
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordTokenRepository.findByToken(token);
	}

	@Transactional
	public User getUserByPasswordResetToken(final String token) {
		return passwordTokenRepository.findByToken(token).getUser();
	}

	@Transactional
	public User getUserByID(final long id) {
		return repository.findById(id);
	}

	@Transactional
	public void changeUserPassword(final User user, final String password) {
		user.setPassword(passwordEncoder.encode(password));
		repository.save(user);
	}

	@Transactional
	public void changeUserActive(final User user) {
		user.setEnabled(true);
		repository.save(user);
	}

	@Transactional
	public void changeUserInActive(final User user) {
		user.setEnabled(false);
		repository.save(user);
	}

	@Transactional
	public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
		return passwordEncoder.matches(oldPassword, user.getPassword());
	}

	private boolean emailExist(final String email) {
		final User user = repository.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Transactional
	public List<User> findAllUser() {

		return repository.findAll();
	}

	@Transactional
	public long count() {

		return repository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IUserService#findUser(java.lang.Long)
	 */
	@Transactional
	public User findUser(Long id) {

		User parent = this.entityManager.find(User.class, id);
		parent.getRoles().size();
		return parent;
	}



	@Override
	public User findByUserNameAndCompanyCode(String username, String companyCode) {

		return userRepository.findUser(username, companyCode);
	}

	@Override
	public User findUserByFirstName(String username, Integer companyCode) {

		return userRepository.findUserByFirstName(username, companyCode);
	}

	@Override
	public User findUserByemailId(String username, Integer companyId) {

		return userRepository.findUserbyEmailId(username, companyId);
	}



	@Override
	public HashMap<Long, User> getUserListHM(Integer companyId) throws Exception {
		HashMap<Long, User>	userHM		= null;
		try {
			userHM	= new HashMap<>();
			List<User>	userList	= repository.getUserList(companyId);
			for(User user : userList) {
				userHM.put(user.getId(), user);
			}
			return userHM;
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public ValueObject editUserProfileFromIVCargo(ValueObject valueObject) throws Exception {
		UserProfileDto		userProfileDto		= null;
		try {
			userProfileDto	= (UserProfileDto) valueObject.get("userProfile");
			User validate= findUserByemailId(userProfileDto.getCompanyEmail(), userProfileDto.getCompany_id());

			if (validate != null) {
				UserProfile  existingUserProfile = userProfileService.getUserProfileByUser_id(validate.getId(), userProfileDto.getCompany_id());
				UserProfile saveUSERProfiel = userProfileBl.prepareUpdateUserProfile(userProfileDto);
				saveUSERProfiel.setCreated(existingUserProfile.getCreated());
				saveUSERProfiel.setCreatedBy(existingUserProfile.getCreatedBy());
				saveUSERProfiel.setRole_id(1L);
				try {
					userProfileService.registerNewUserProfile(saveUSERProfiel);
				} catch (EmailExistsException e) {
					e.printStackTrace();
				}
				valueObject.put("updateUserProfile", true);
			}else {
				valueObject.put("UserNotfound", true);
			}
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}

	public User prepareModel(ValueObject valueObject) throws Exception {
		User user = new User();

		try {
			user.setFirstName(valueObject.getString("firstName"));
			user.setLastName(valueObject.getString("lastName")); 
			user.setPassword(passwordEncoder.encode(valueObject.getString("password")));
			user.setEmail(valueObject.getString("email"));
			user.setEnabled(true);
		} catch (Exception e) {
			throw e;
		}

		return user;
	}
	

	@Override
	public ValueObject registerUserAccount(ValueObject valueObject) throws EmailExistsException {
		User 			validate 			= null;
		UserProfile  	saveUSERProfiel		= null;
		BranchMapper	branchMapper		= null;
		Department		department			= null;
		try {
			CompanyMapper	companyMapper	= companyMapperRepository.findByAccountGroupId(valueObject.getInt("company_id"));
			
			if(companyMapper == null) {
				valueObject.put("companyMapperNotFound", true);
				return valueObject;
			}
			
			IVFleetExecutiveMapping	preExecutive = executiveMappingRepository.findByIVExecutiveId(valueObject.getLong("executiveId",0));
									branchMapper = branchMapperRepository.findBranchMapperByIvBranchId(valueObject.getLong("branchId",0));
									department	 = departmentRepository.SearchDepartmentLisrCompanyID(companyMapper.getFleetCompanyId()).get(0);
			validate 	= userService.findUserByemailId(valueObject.getString("email", null), companyMapper.getFleetCompanyId());
			
			if (validate == null || valueObject.getBoolean("fromEdit", false)) {
				UserProfileBL userProfileBL	= new UserProfileBL();
				final User registered = prepareModel(valueObject);
				
				registered.setCompany_id(companyMapper.getFleetCompanyId());
				if(preExecutive != null) {
					registered.setId(preExecutive.getFleetExecutiveId());
					saveUSERProfiel	= userProfileService.getUserProfileByUser_id(preExecutive.getFleetExecutiveId(), companyMapper.getFleetCompanyId());
				}
				
				UserProfileDto userProfileDto	= new UserProfileDto();
				
				userProfileDto.setCompany_id(registered.getCompany_id());
				userProfileDto.setRole_name(valueObject.getString("role_name", null));
				
				
				saveUSERProfiel = userProfileBL.prepareUpdateUserProfile_Master_User(userProfileDto, new UserDto(), saveUSERProfiel);

				List<Role> roles = new ArrayList<Role>();

				roles.add(roleRepository.findByNameAndCompanyId(userProfileDto.getRole_name(), userProfileDto.getCompany_id()));

				registered.setRoles(roles);
				registered.setCompany_id(userProfileDto.getCompany_id());

				User user = userService.registerNewUserAccount(registered);
				saveUSERProfiel.setUser_id(user.getId());
				saveUSERProfiel.setCompany_id(userProfileDto.getCompany_id());
				saveUSERProfiel.setRole_id(roles.get(0).getId());
				saveUSERProfiel.setMobile_number(valueObject.getString("mobileNumber"));
				saveUSERProfiel.setBranch_id(Integer.parseInt(branchMapper.getFleetBranchId()+""));
				saveUSERProfiel.setDepartment_id(department.getDepart_id());
				
				userProfileService.registerNewUserProfile(saveUSERProfiel);
				
				valueObject.put("success", true);
				valueObject.put("ivFleetUserId", user.getId());
				
				if(preExecutive == null) {
					preExecutive	=	saveIVFleetExecutiveMapping(user, valueObject);
					ExecutiveMapperTxnChecker	checker	= new ExecutiveMapperTxnChecker();
					checker.setMarkForDelete(false);
					checker.setIVCargoStatusUpdated(false);
					checker.setExecutiveMapperId(preExecutive.getIvFleetExecutiveMappingId());
					txnCheckerRepository.save(checker);
					
					valueObject.put("txnCheckerId", checker.getExecutiveMapperTxnCheckerId());
					valueObject.put("executiveMapping", preExecutive);
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		return valueObject;
	}
	
	@Override
	@Transactional
	public ValueObject updateIVCargoTxnChecherStatus(ValueObject valueObject) throws Exception {
		try {
			txnCheckerRepository.updateIVCargoTxnChecherStatus(valueObject.getLong("txnCheckerId"), true);
			
			valueObject.put("success", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject updateUserStatusFromIVCargo(ValueObject valueObject) throws Exception {
		try {
			
			IVFleetExecutiveMapping	executive 		= executiveMappingRepository.findByIVExecutiveId(valueObject.getLong("executiveId",0));
			CompanyMapper			companyMapper	= companyMapperRepository.findByAccountGroupId(valueObject.getInt("accountGroupId",0));
			
			final User user = userService.getUserByID(executive.getFleetExecutiveId());
			
			if(!valueObject.getBoolean("status"))
				userService.changeUserInActive(user);
			else
				userService.changeUserActive(user);
			
			userProfileService.changeUserInActive(user.getId(), !valueObject.getBoolean("status"), companyMapper.getFleetCompanyId());
			
			valueObject.put("success", true);
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private IVFleetExecutiveMapping saveIVFleetExecutiveMapping(User user, ValueObject	valueObject) throws Exception{
		IVFleetExecutiveMapping		executiveMapping		= null;
		try {
			executiveMapping	= new IVFleetExecutiveMapping();
			
			executiveMapping.setFleetExecutiveId(user.getId());
			executiveMapping.setIvExecutiveId(valueObject.getLong("executiveId",0));
			executiveMapping.setStatus(false);
			executiveMapping.setMarkForDelete(false);
			
			executiveMappingRepository.save(executiveMapping);
			
			return executiveMapping;
			
		} catch (Exception e) {
			throw e;
		}
	}
}
