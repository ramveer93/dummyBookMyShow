package com.dummy.bookmyshow.util;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * 
 * @author sramveer
 * This class will parse the rest response to custom response JSON
 *
 */

@Component
public class ResponseParser implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private int statusCode;
	private String developerMessage;
	private String userMessage;
	/**
	 * Method to build custom response
	 * @param statusCode
	 * @param developerMessage
	 * @param userMessage
	 * @return
	 */
	public ResponseParser build(int statusCode, String developerMessage, String userMessage) {
		this.statusCode = statusCode;
		this.developerMessage = developerMessage;
		this.userMessage = userMessage;
		return this;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	@Override
	public String toString() {
		return "ResponseHelper [statusCode=" + statusCode + ", developerMessage=" + developerMessage + ", userMessage="
				+ userMessage + "]";
	}
}
