package com.dummy.bookmyshow.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dummy.bookmyshow.entity.SeatMatrix;
import com.dummy.bookmyshow.service.impl.GenericServiceImpl;
import com.dummy.bookmyshow.util.ResponseParser;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class GenericController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ResponseParser responseParser;

	@Autowired
	private GenericServiceImpl genericService;

	/**
	 * The end point will get the list of supported cities by the application
	 * 
	 * @return the ResponseEntity
	 */
	@RequestMapping(value = "/getSupportedCities", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getSupportedCities() {
		try {
			this.LOGGER.info("getSupportedCities() called ");
			List<String> supportedCities = this.genericService.getSupportedCities();
			JSONArray result = new JSONArray();
			
			for (String city : supportedCities) {
				JSONObject obj = new JSONObject();
				obj.put("name", city);
				result.put(obj);
			}
			this.LOGGER.info("getSupportedCities() supported cities:  " + result.toString());
			return new ResponseEntity<>(result.toString(), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			this.LOGGER.error("Error in getSupportedCities() " + e.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			this.LOGGER.error("Error in getSupportedCities() " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * This end point will return the movies now showing in a city
	 * 
	 * @param city
	 * @return the response entity
	 */
	@RequestMapping(value = "/getMoviesByCity", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getMoviesByCity(@RequestParam("City") String city) {
		try {
			this.LOGGER.info("getMoviesByCity() called with city name:  " + city);
			JSONArray availableMovies = this.genericService.getAvailableMovies(city);
			this.LOGGER.debug("getMoviesByCity() movies in the city:  " + availableMovies.toString());
			return new ResponseEntity<>(availableMovies.toString(), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			this.LOGGER.error("Error in getMoviesByCity() " + e.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			this.LOGGER.error("Error in getMoviesByCity() " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This end point will return screens which are showing the movies in same city
	 * 
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/getScreensShowingMovie", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getScreensShowingMovie(@RequestParam("theaterId") String theaterId,
			@RequestParam("movieId") String movieId, @RequestParam("city") String city) {
		try {
			this.LOGGER.info("getScreensShowingMovie() called with input: theaterId " + theaterId + " city: " + city
					+ " movieId: " + movieId);
			JSONObject screensShowingMovie = this.genericService.getScreensShowingMovie(theaterId, movieId, city);
			this.LOGGER.info("getScreensShowingMovie() screens showing  " + movieId + " in : " + city + " are: "
					+ screensShowingMovie.toString());
			return new ResponseEntity<>(screensShowingMovie.toString(), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			this.LOGGER.error("Error in getScreensShowingMovie() " + e.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			this.LOGGER.error("Error in getScreensShowingMovie() " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This end point will gives the details about availability of seats for a movie
	 * , in a city for a user and based on start time
	 * 
	 * @param movieId
	 * @param theaterId
	 * @param screenStartsAt
	 * @return
	 */
	@RequestMapping(value = "/getAvailabilityOnAScreen", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getAvailabilityOnAScreen(@RequestParam("movieId") String movieId,
			@RequestParam("theaterId") String theaterId, @RequestParam("screenStartsAt") String screenStartsAt) {
		try {
			this.LOGGER.info("getAvailabilityOnAScreen() called with input: movieId: " + movieId + " theaterid: "
					+ theaterId + ": screenStartsAt :" + screenStartsAt);
			JSONArray seatsMatrix = this.genericService.getSeatMatrix(movieId, theaterId, screenStartsAt);
			return new ResponseEntity<>(seatsMatrix.toString(), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			this.LOGGER.error("Error getAvailabilityOnAScreen() " + e.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			this.LOGGER.error("Error in getAvailabilityOnAScreen()" + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This end point will book the seats for a movie , in a theater and for a user
	 * The end point will first check if the seat is already booked in same theater
	 * for a movie, if not then book it
	 * 
	 * @param seatsToBook
	 * @param userName
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/bookSeats", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> bookSeats(@RequestBody List<SeatMatrix> seatsToBook,
			@RequestParam("userName") String userName) {
		try {
			this.LOGGER.info("bookSeats() called with input: " + seatsToBook.toString() + " user: " + userName);
			JSONObject bookingDetails = this.genericService.bookSeats(seatsToBook, userName);
			this.LOGGER.info("bookSeats() booking details:  " + bookingDetails);
			return new ResponseEntity<>(bookingDetails.toString(), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			this.LOGGER.error("Error in bookSeats() " + e.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			this.LOGGER.error("Error in bookSeats() " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
