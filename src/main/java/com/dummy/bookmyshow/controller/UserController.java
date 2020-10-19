package com.dummy.bookmyshow.controller;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dummy.bookmyshow.entity.User;
import com.dummy.bookmyshow.enums.UserType;
import com.dummy.bookmyshow.repository.UserRepository;
import com.dummy.bookmyshow.security.AuthRequest;
import com.dummy.bookmyshow.security.JwtUtils;
import com.dummy.bookmyshow.util.ResponseParser;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class UserController {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Autowired
	private ResponseParser responseParser;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepository userReposiitory;

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * get the user details
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/getUserDetails", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getUserDetails(@RequestParam("userName") String userName) {
		this.LOGGER.info(" getUser () with input params:  " + userName);
		try {
			User user = this.userReposiitory.findUserByUserName(userName);
			this.LOGGER.info(" getUser () found the user with input username " + user.toString());
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonUser = mapper.writeValueAsString(user);
			this.LOGGER.info(" getUser () status success! ");
			return new ResponseEntity<>(jsonUser, HttpStatus.OK);

		} catch (Exception ex) {
			this.LOGGER.info(" getUser () Error occured:  " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * SignUp of a user
	 * This API should not be authenticated
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addUser(@RequestBody User user) {
		try {
			this.LOGGER.info("addUser() called with input:  " + user.toString());
			User newUser = user;
			validateInput(newUser);
			String userName = getUserName(user).replaceAll("\\s", "");
			newUser.setUserName(userName);
			newUser.setCreatedOn(LocalDateTime.now());
			this.LOGGER.info("addUser() setting username as   " + newUser.getUserName());
			this.userReposiitory.save(newUser);
			this.LOGGER.info("successfully saved user object " + newUser.toString());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.CREATED.value(), "Successfully saved user with user name: "+newUser.getUserName(),
					"Successfully saved user with user name: "+newUser.getUserName()), HttpStatus.CREATED);
		} catch (IllegalArgumentException ex) {
			this.LOGGER.error("Error Saving user object " + ex.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			this.LOGGER.error("Error Saving user object " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	/**
	 * 
	 * @param authRequest
	 * @return
	 */
	@RequestMapping(value = "/token", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> getToken(@RequestBody AuthRequest authRequest) {
		JSONObject result = new JSONObject();
		try {
			this.LOGGER.info(
					"getToken() called with username as : " + authRequest.getUsername() + " and password as : *****");
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			String token = jwtUtils.generateToken(authRequest.getUsername());
			this.LOGGER.info("getToken() successfully got the jwt token " + token);
			result.put("token", token);
			result.put("status", "success");
			this.LOGGER.info("getToken() returning token with status 200 ");
			return new ResponseEntity<>(result.toString(), HttpStatus.OK);
		} catch (Exception e) {
			this.LOGGER.info("getToken() Error creating jwt token :  " + e.getMessage());
			result.put("message", e.getMessage() + " cause: " + e.getCause());
			result.put("status", "failed");
			e.printStackTrace();
			return new ResponseEntity<>(result.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * 
	 * @param user
	 */
	private void validateInput(User user) {
		try {
			Assert.notNull(user, "User object must not be null");
			Assert.hasLength(user.getFirstName(), "User first name must not be null or empty");
			Assert.hasLength(user.getLastName(), "User last name must not be null or empty");
			Assert.hasLength(user.getMobileNumber(), "User mobile number must not be empty");
			Assert.hasLength(user.getUserType().toString(), "User type must not be null or empty");
			Assert.hasLength(user.getEmail(), "Email must not be null or empty");
			Assert.hasLength(user.getAuthentication(), "password must not be null or empty");
			Assert.isTrue(user.getMobileNumber().length() == 10, "Invalid mobile number");
			switch (user.getUserType().toString().toUpperCase()) {
			case "ADMIN":
				user.setUserType(UserType.ADMIN);
				this.LOGGER.debug("validateInput(user), User type found as ADMIN");
				break;
			case "NORMAL":
				user.setUserType(UserType.NORMAL);
				this.LOGGER.debug("validateInput(user), User type found as NORMAL");
				break;
			default:
				Assert.isTrue(false, "Only values allowd for user type is : Admin or Normal");
			}
			Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
			Assert.isTrue(matcher.find(), "Invalid email id");
			Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
			Matcher m = p.matcher(user.getMobileNumber());
			Assert.isTrue((m.find() && m.group().equals(user.getMobileNumber())), "Invalid mobile number");
		} catch (IllegalArgumentException e) {
			this.LOGGER.error("input error ", e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	/**
	 * @param user
	 * @return
	 */
	private String getUserName(User user) {
		return (user.getLastName() + user.getFirstName() + user.getMobileNumber());

	}

}
