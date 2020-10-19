package com.dummy.bookmyshow.service.impl;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dummy.bookmyshow.entity.Cast;
import com.dummy.bookmyshow.repository.CastRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CastServiceImpl {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CastRepository castRepo;

	/**
	 * The service method will return the casts for a movie, it will query the DB for movieId
	 * @param movieId
	 * @return
	 * @throws JsonProcessingException
	 */
	public JSONArray findByMovieId(String movieId) throws JsonProcessingException {
		this.LOGGER.info("findByMovieId() cast service called with input " + movieId);
		List<Cast> casts = this.castRepo.getCastByMovieId(movieId);
		this.LOGGER.info("findByMovieId() Found  " + casts.size() + " no of casts for movie " + movieId);
		JSONArray result = new JSONArray();
		for (Cast cast : casts) {
			ObjectMapper mapper = new ObjectMapper();
			String castString = mapper.writeValueAsString(cast);
			JSONObject object = new JSONObject(castString);
			result.put(object);
		}
		this.LOGGER.info("findByMovieId() Successfully returned the casts for the movie " + movieId);
		return result;
	}

}
