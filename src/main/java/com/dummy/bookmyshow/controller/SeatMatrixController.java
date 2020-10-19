package com.dummy.bookmyshow.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.dummy.bookmyshow.entity.SeatMatrix;
import com.dummy.bookmyshow.entity.SeatMatrixPk;
import com.dummy.bookmyshow.enums.SeatType;
import com.dummy.bookmyshow.repository.SeatMatrixRepository;
import com.dummy.bookmyshow.util.ResponseParser;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class SeatMatrixController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private final int DEFAULT_SEAT_COUNT = 50;
	private final int DEFAULT_SEAT_PRICE = 100;

	@Autowired
	private ResponseParser responseParser;

	@Autowired
	private SeatMatrixRepository seatMatrixRepo;

	/**
	 * add custom seat matrix TODO : Only ADMIN user is allowed to use this API
	 * 
	 * @param seatMatrix
	 * @return
	 */
	@RequestMapping(value = "/addCustomSeatMatrix", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addSeatMatrix(@RequestBody List<SeatMatrix> seatMatrix) {
		try {
			this.LOGGER.info("addSeatMatrix() called");
			validateInput(seatMatrix);
			this.LOGGER.info("addSeatMatrix() saving cast for movie id as  ");
			this.seatMatrixRepo.saveAll(seatMatrix);
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.CREATED.value(),
					"Successfully saved seat matrix", "Successfully saved seat matrix"), HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			this.LOGGER.error("Error creating seat matrix  " + e.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);

		} catch (Exception ex) {
			this.LOGGER.error("Error creating seat matrix " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Add default seat matrix TODO : Only ADMIN user is allowed to use this API
	 * 
	 * @param seatMatrix
	 * @return
	 */
	@RequestMapping(value = "/addDefaultSeatMatrix", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addDefaultSeatMatrix(@RequestBody SeatMatrix seatMatrix) {
		try {
			this.LOGGER.info("addSeatMatrix() called");
			List<SeatMatrix> list = new ArrayList<>();
			list.add(seatMatrix);
			validateInput(list);

			for (int i = 0; i < DEFAULT_SEAT_COUNT; i++) {
				SeatMatrix newMatrix = new SeatMatrix();
				newMatrix.setBooked(false);
				newMatrix.setCreatedOn(LocalDateTime.now());
				newMatrix.setPrice(DEFAULT_SEAT_PRICE);
				newMatrix.setSeatType(SeatType.NORMAL);

				SeatMatrixPk matrixPk = new SeatMatrixPk();
				matrixPk.setMovieId(seatMatrix.getPrimaryKey().getMovieId());
				matrixPk.setScreenStartsAt(seatMatrix.getPrimaryKey().getScreenStartsAt());
				matrixPk.setTheaterId(seatMatrix.getPrimaryKey().getTheaterId());
				matrixPk.setSeatNumber(makeSeatNumber(i));

				newMatrix.setPrimaryKey(matrixPk);
				this.seatMatrixRepo.save(newMatrix);

			}

			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.CREATED.value(),
							"Successfully created detault seat matrix of " + DEFAULT_SEAT_COUNT + " seats",
							"Successfully created detault seat matrix of: " + DEFAULT_SEAT_COUNT + " seats"),
					HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			this.LOGGER.error("Error creating seat matrix  " + e.getMessage());
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);

		} catch (Exception ex) {
			this.LOGGER.error("Error creating seat matrix " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Create seat numbers A0-A9 B0-B9 and so on
	 * 
	 * @param i
	 * @return
	 */
	private String makeSeatNumber(int i) {
		StringBuffer sb = new StringBuffer();
		Map<Integer, Character> map = new HashMap<>();
		for (char c = 'A'; c <= 'Z'; c++) {
			int key = c - 'A';
			char value = c;
			map.put(key, value);
		}
		char ch = map.get(i / 10);
		int index = i % 10;
		sb.append("" + ch + index);
		return sb.toString();
	}

	/**
	 * 
	 * @param seatMatrix
	 */
	private void validateInput(List<SeatMatrix> seatMatrix) {
		for (SeatMatrix seat : seatMatrix) {
			try {
				Assert.notNull(seat, "seatMatrix object must not be null");
				Assert.hasLength(seat.getPrimaryKey().getMovieId(), "seatMatrix movie id  must not be null or empty");
				Assert.hasLength(seat.getPrimaryKey().getTheaterId(),
						"seatMatrix theater id  must not be null or empty");
				Assert.hasLength(seat.getPrimaryKey().getScreenStartsAt(),
						"seatMatrix screen starts at  must not be null or empty");
				Assert.hasLength(String.valueOf(seat.getPrice()), "seatMatrix price   must not be null or empty");
				Assert.hasLength(seat.getPrimaryKey().getSeatNumber(),
						"seatMatrix seat number   must not be null or empty");
				Assert.hasLength(seat.getSeatType().toString(), "seatMatrix seat number   must not be null or empty");
			} catch (IllegalArgumentException ex) {
				this.LOGGER.error(ex.getMessage());
				throw new IllegalArgumentException(ex.getMessage());

			}

		}

	}

}
