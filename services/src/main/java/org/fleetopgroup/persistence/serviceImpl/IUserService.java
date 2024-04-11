package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.model.PasswordResetToken;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.VerificationToken;
import org.fleetopgroup.persistence.service.UserDto;
import org.fleetopgroup.validation.EmailExistsException;
import org.fleetopgroup.web.util.GenericResponse;
import org.fleetopgroup.web.util.ValueObject;

public interface IUserService {

    User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;
    
    User registerNewUserAccount(User accountDto) throws EmailExistsException;
    
    User updateUserAccount(final User user) throws Exception;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email, String companyCode);
    
    User findByEmail(String email);
    
    User findUser(Long id);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

    User getUserByID(long id);
    

    void changeUserPassword(User user, String password);
    
    void changeUserActive(User user);
    
    void changeUserInActive(User user);

    boolean checkIfValidOldPassword(User user, String password);
    
    List<User> findAllUser();
    
    long count();
    
	User findByUserNameAndCompanyCode(String username, String companyCode);
	
	HashMap<Long, User>  getUserListHM(Integer companyId) throws Exception;
	
	public User findUserByFirstName(String username, Integer companyCode) ;
	
	public User findUserByemailId(String username, Integer companyId);
	
	ValueObject  editUserProfileFromIVCargo(ValueObject	valueObject) throws Exception;
	
	public ValueObject registerUserAccount(ValueObject valueObject) throws EmailExistsException ;


	public ValueObject	updateIVCargoTxnChecherStatus(ValueObject valueObject) throws Exception;
	
	public ValueObject updateUserStatusFromIVCargo(ValueObject valueObject) throws Exception ;
	
}
