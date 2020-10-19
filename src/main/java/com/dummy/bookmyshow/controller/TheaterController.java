package com.dummy.bookmyshow.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dummy.bookmyshow.entity.Theater;
import com.dummy.bookmyshow.entity.User;
import com.dummy.bookmyshow.repository.TheaterRepository;
import com.dummy.bookmyshow.repository.UserRepository;
import com.dummy.bookmyshow.util.ResponseParser;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class TheaterController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TheaterRepository theaterRepo;
	@Autowired
	private ResponseParser responseParser;
	@Autowired
	private UserRepository userRepo;

	/**
	 * register a theater
	 * @param theater
	 * @return
	 */
	@RequestMapping(value = "/registerTheater", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> registerMovie(@RequestBody Theater theater) {
		try {
			this.LOGGER.info("registerTheater() called with input: " + theater.toString());
			validateInput(theater);
			Theater newTheater = theater;
			String theaterId = newTheater.getName().replaceAll("\\s", "");
			newTheater.setTheaterId(theaterId);
			newTheater.setCreatedOn(LocalDateTime.now());
			this.LOGGER.info("registerTheater() stting theater id as  " + theaterId);
			this.theaterRepo.save(newTheater);
			this.LOGGER.info("registerTheater() successfully saved theater with id:  " + theaterId);
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.CREATED.value(),
					"Successfully registered new theater with thaterId: " + theaterId,
					"Successfully registered new theater with theaterId: " + theaterId), HttpStatus.CREATED);

		} catch (IllegalArgumentException e) {
			this.LOGGER.error("Error registering theater object " + e.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);

		} catch (Exception ex) {
			this.LOGGER.error("Error registering theater object " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	private void validateInput(Theater theater) {
		try {
			Assert.notNull(theater, "User object must not be null");
			Assert.hasLength(theater.getName(), "theater name must not be null or empty");
			Assert.hasLength(theater.getAddress(), "theater address must not be null or empty");
			Assert.hasLength(theater.getCity(), "theater city must not be null or empty");
			Assert.hasLength(theater.getCountry(), "theater country must not be null or empty");
			Assert.hasLength(theater.getLanguages(), "theater language must not be null or empty");
			Assert.hasLength(theater.getUserName(), "theater user name must not be null or empty");
			User existingUser = this.userRepo.findUserByUserName(theater.getUserName());
			Assert.isTrue(existingUser != null,
					"Invalid user name passed, no user found with id:  " + theater.getUserName());
			Assert.isTrue((existingUser.getUserType().toString().equals("ADMIN")) , "User is not allowed to add the theater, Only admin user can add a theater");

		} catch (IllegalArgumentException e) {
			this.LOGGER.error(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());

		}
	}

}
