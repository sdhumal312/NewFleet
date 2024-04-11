package org.fleetopgroup.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.fleetopgroup.persistence.service.UserDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserDto user = (UserDto) obj;
        
        if(user == null)
        	return false;
        
        System.err.println(" user "+user);
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
