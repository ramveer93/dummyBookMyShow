package com.dummy.bookmyshow.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dummy.bookmyshow.entity.Cast;
import com.dummy.bookmyshow.entity.Movie;
import com.dummy.bookmyshow.repository.CastRepository;
import com.dummy.bookmyshow.repository.MovieRepository;
import com.dummy.bookmyshow.service.impl.CastServiceImpl;
import com.dummy.bookmyshow.util.ResponseParser;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class CastController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ResponseParser responseParser;

	@Autowired
	private CastRepository castRepo;

	@Autowired
	private MovieRepository movieRepo;

	@Autowired
	private CastServiceImpl castService;

	/**
	 * The end point will add the cast for the movie , there is one to many mapping
	 * between movie to cast, i.e one movie can have multiple casts TODO : Only
	 * ADMIN user is allowed to use this API
	 * 
	 * @param cast
	 * @return the Response entity
	 */
	@RequestMapping(value = "/addCast", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addCast(@RequestBody List<Cast> casts) {
		try {
			this.LOGGER.info("addCast() called with " + casts.size() + " casts");
			for (Cast cast : casts) {
				validateInput(cast);
				this.LOGGER.info("addCast() saving cast for movie id as  " + cast.getMovieId());
				this.castRepo.save(cast);
				// update movie table with this movie id and this cast id
				Optional<Movie> existingMovie = this.movieRepo.findById(cast.getMovieId());
				Movie movie = existingMovie.get();
				if (movie != null) {
					this.LOGGER.info("addCast() trying to update cast id in movie ");
					movie.setCastId(cast.getMovieId());
					movie.setUpdatedOn(LocalDateTime.now());
					this.movieRepo.save(movie);
					this.LOGGER.info("addCast() successfully updated cast id " + cast.getMovieId() + " in movie: "
							+ movie.getName());
				}
				this.LOGGER.info("addCast() saved cast for movie   id   " + cast.getMovieId() + " in DB");
			}

			return new ResponseEntity<>(this.responseParser.build(HttpStatus.CREATED.value(),
					"Successfully saved cast for movies ", "Successfully saved cast for movies "), HttpStatus.CREATED);

		} catch (IllegalArgumentException e) {
			this.LOGGER.error("Error creating cast  " + e.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);

		} catch (Exception ex) {
			this.LOGGER.error("Error creating cast object " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * This end point will retrieve the cast details for a movie
	 * 
	 * @param movieId
	 * @return the ResponseEntity
	 */
	@RequestMapping(value = "/getCast", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addCast(@RequestParam("movieId") String movieId) {
		try {
			this.LOGGER.info("getCast() called with input: " + movieId);
			JSONArray casts = this.castService.findByMovieId(movieId);
			this.LOGGER.info("getCast() successfully fetched cast   " + casts.toString());
			return new ResponseEntity<>(casts.toString(), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			this.LOGGER.error("Error getCast   " + e.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			this.LOGGER.error("Error creating cast object " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method will validate the input for cast object
	 * 
	 * @param cast
	 */
	private void validateInput(Cast cast) {
		try {
			Assert.notNull(cast, "User object must not be null");
			Assert.hasLength(cast.getCastDetails(), "cast details  must not be null or empty");
			Assert.hasLength(cast.getCharacterName(), "Cast character must not be null or empty");
			Assert.hasLength(cast.getCharacterOccupation(), "Cast character occupation must not be null or empty");
			Assert.hasLength(cast.getMovieId(), "Movie duration must not be null or empty");
			Optional<Movie> existingTheater = this.movieRepo.findById(cast.getMovieId());
			Assert.isTrue(existingTheater.get() != null, "Invalid movie id  passed, no movie found with this id ");
		} catch (IllegalArgumentException e) {
			this.LOGGER.error(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
