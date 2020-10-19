package com.dummy.bookmyshow.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.dummy.bookmyshow.enums.Language;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "movie")
public class Movie implements Serializable {
	private static final long serialVersionUID = -2343243243242432341L;
	@Id
	private String movieId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "language")
	@Enumerated(EnumType.STRING)
	private Language language;
	
	@Column(name = "theater_id")
	private String theaterId;
	
	@Column(name = "rating")
	private String rating;
	
	@Column(name = "cast_id")
	private String castId;
	
	@Column(name = "release_year")
	private String releaseYear;
	
	@Column(name = "active_date_start")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime activeDateStart;
	
	@Column(name = "active_date_end")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime activeDateEnd;
	
	@Column(name = "trailer_url")
	private String trailerUrl;
	
	@Column(name = "poster_url")
	private String posterUrl;
	
	@Column(name = "duration")
	private String duration;
	
	
	@Column(name = "created_on")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn = LocalDateTime.now();
	
	@Column(name = "updated_on")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedOn;
	
	@Column(name = "deleted")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime deleted;
	
	@Column(name = "director")
	private String director;
	
	@Column(name = "plot" ,  length=512)
	private String plot;

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(String theaterId) {
		this.theaterId = theaterId;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getCastId() {
		return castId;
	}

	public void setCastId(String castId) {
		this.castId = castId;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public LocalDateTime getActiveDateStart() {
		return activeDateStart;
	}

	public void setActiveDateStart(LocalDateTime activeDateStart) {
		this.activeDateStart = activeDateStart;
	}

	public LocalDateTime getActiveDateEnd() {
		return activeDateEnd;
	}

	public void setActiveDateEnd(LocalDateTime activeDateEnd) {
		this.activeDateEnd = activeDateEnd;
	}

	public String getTrailerUrl() {
		return trailerUrl;
	}

	public void setTrailerUrl(String trailerUrl) {
		this.trailerUrl = trailerUrl;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public LocalDateTime getDeleted() {
		return deleted;
	}

	public void setDeleted(LocalDateTime deleted) {
		this.deleted = deleted;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	@Override
	public String toString() {
		return "Movie [movieId=" + movieId + ", name=" + name + ", type=" + type + ", language=" + language
				+ ", theaterId=" + theaterId + ", rating=" + rating + ", castId=" + castId + ", releaseYear="
				+ releaseYear + ", activeDateStart=" + activeDateStart + ", activeDateEnd=" + activeDateEnd
				+ ", trailerUrl=" + trailerUrl + ", posterUrl=" + posterUrl + ", duration=" + duration + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + ", deleted=" + deleted + ", director=" + director + ", plot="
				+ plot + "]";
	}

	

	
	

	
	
}
