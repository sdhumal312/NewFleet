package org.fleetopgroup.test;

import java.util.Arrays;
import java.util.List;

import org.fleetopgroup.persistence.dao.UserRepository;
import org.fleetopgroup.persistence.dto.User;
import org.fleetopgroup.persistence.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class MokitoTest {

	 @Mock private UserRepository userRepository;
	 @InjectMocks private UserService userService;
	 
	 @Test
	 public void calTest() {
		 
		 User user = new User(12, "Manish", 28, 35000);
		 
		 List<User>  userList	= Arrays.asList(user);
		 

		 
	 }
	
}
