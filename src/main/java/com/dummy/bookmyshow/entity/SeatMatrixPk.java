package com.dummy.bookmyshow.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SeatMatrixPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "movie_id")
	private String movieId;

	@Column(name = "theater_id")
	private String theaterId;

	@Column(name = "screen_starts_at")
	private String screenStartsAt;
	
	@Column(name = "seat_number", nullable = false)
	private String seatNumber;

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(String theaterId) {
		this.theaterId = theaterId;
	}

	public String getScreenStartsAt() {
		return screenStartsAt;
	}

	public void setScreenStartsAt(String screenStartsAt) {
		this.screenStartsAt = screenStartsAt;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	@Override
	public String toString() {
		return "SeatMatrixPk [movieId=" + movieId + ", theaterId=" + theaterId + ", screenStartsAt=" + screenStartsAt
				+ ", seatNumber=" + seatNumber + "]";
	}

}
