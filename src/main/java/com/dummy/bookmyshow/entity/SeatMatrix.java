package com.dummy.bookmyshow.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.dummy.bookmyshow.enums.SeatType;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "seat_matrix")
public class SeatMatrix implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private SeatMatrixPk primaryKey;


	@Column(name = "booked")
	private boolean booked;

	@Column(name = "seat_type")
	@Enumerated(EnumType.STRING)
	private SeatType seatType;

	@Column(name = "created_on")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdOn =  LocalDateTime.now();

	@Column(name = "updated_on")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedOn;

	@Column(name = "deleted")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime deleted;

	@Column(name = "seat_price")
	private int price;

	public SeatMatrixPk getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(SeatMatrixPk primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	public SeatType getSeatType() {
		return seatType;
	}

	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "SeatMatrix [primaryKey=" + primaryKey + ", booked=" + booked + ", seatType=" + seatType + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + ", deleted=" + deleted + ", price=" + price + "]";
	}

}
