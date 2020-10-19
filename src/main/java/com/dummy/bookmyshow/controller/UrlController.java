package com.dummy.bookmyshow.controller;

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

import com.dummy.bookmyshow.entity.Url;
import com.dummy.bookmyshow.service.impl.UrlServiceImpl;
import com.dummy.bookmyshow.util.ResponseParser;

@RestController
@RequestMapping(value = "/v1")
@Component
@Configuration
public class UrlController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private ResponseParser responseParser;

	@Autowired
	private UrlServiceImpl service;

	/**
	 * convert from long to short url
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "/longToShort", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Object> convertLongUrlToShort(@RequestBody Url url) {
		try {
			String shortUrl = this.service.convertToShortUrl(url);
			JSONObject obj = new JSONObject();
			obj.put("longUrl", url.getOriginalUrl());
			obj.put("shortUrl", "https://"+shortUrl);
			return new ResponseEntity<>(obj.toString(), HttpStatus.OK);
		} catch (Exception ex) {
			this.LOGGER.error("Error registering theater object " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	/**
	 * convert from short to long
	 * @param shortUrl
	 * @return
	 */
	@RequestMapping(value = "/shortToLong", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> convertshortToLong(@RequestParam("shortUrl")String shortUrl)
	{
		
		try {
			shortUrl = shortUrl.substring(8,shortUrl.length());//remove the https:// part
			String originalUrl = this.service.getOriginalUrl(shortUrl);
			JSONObject obj = new JSONObject();
			obj.put("longUrl", originalUrl);
			obj.put("shortUrl", shortUrl);
			return new ResponseEntity<>(obj.toString(), HttpStatus.OK);
		} catch (Exception ex) {
			this.LOGGER.error("Error in shortToLong " + ex.getMessage());
			return new ResponseEntity<>(this.responseParser.build(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ex.getMessage(), ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
