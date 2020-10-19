package com.dummy.bookmyshow.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ScreenPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "theater_id", nullable = false)
	private String theaterId;

	@Column(name = "movie_id", nullable = false)
	private String movieId;

	@Column(name = "starts_at", nullable = false)
	private String startsAt;

	public String getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(String theaterId) {
		this.theaterId = theaterId;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(String startsAt) {
		this.startsAt = startsAt;
	}

	

	

}
