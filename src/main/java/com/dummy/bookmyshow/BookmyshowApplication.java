package com.dummy.bookmyshow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.dummy.bookmyshow.entity.User;
import com.dummy.bookmyshow.enums.UserType;
import com.dummy.bookmyshow.repository.UserRepository;



@SpringBootApplication
@Configuration
public class BookmyshowApplication {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BookmyshowApplication.class, args);
	}
	
	/**
	 * Initialize the admin users
	 */
	@PostConstruct
	public void initUsers() {
		this.LOGGER.info("creating admin and service users.....");
		User adminUser = new User();
		adminUser.setUserName("admin");
		adminUser.setAuthentication("admin");
		adminUser.setFirstName("admin");
		adminUser.setLastName("user");
		adminUser.setEmail("admin@bms.com");
		adminUser.setMobileNumber("9801234589");
		adminUser.setUserType(UserType.ADMIN);
		
		User serviceUser = new User();
		serviceUser.setUserName("service");
		serviceUser.setAuthentication("service");
		serviceUser.setFirstName("service");
		serviceUser.setLastName("user");
		serviceUser.setEmail("service@bms.com");
		serviceUser.setMobileNumber("9801234589");
		serviceUser.setUserType(UserType.ADMIN);
		
		List<User> users = new ArrayList<>();
		users.add(serviceUser);
		users.add(adminUser);
		userRepository.saveAll(users);
		this.LOGGER.info("successfully created admin user with name : admin  , password: ****** and service user with name: service and password: ***** ");
	}

}
