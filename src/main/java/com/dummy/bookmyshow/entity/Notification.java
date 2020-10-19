package com.dummy.bookmyshow.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.dummy.bookmyshow.enums.NotificationType;
import com.dummy.bookmyshow.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "notification")
public class Notification implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "notification_id" , columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(name = "booking_id")
	private long bookingId;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "receiver_email")
	private String receiverEmail;
	
	@Column(name = "receiver_mobile")
	private String receiverMobileNo;
	
	@Column(name = "receiver_type")
	@Enumerated(EnumType.STRING)
	private NotificationType receiverType;
	
	@Column(name = "sender_email")
	private String senderEmail;
	
	@Column(name = "tiny_url")
	private String tinyUrl;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "send_timie")
	private LocalDateTime sendTime;
	
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_on")
	private LocalDateTime createdOn = LocalDateTime.now();
	
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "updated_on")
	private LocalDateTime updatedOn = LocalDateTime.now();


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public long getBookingId() {
		return bookingId;
	}


	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public String getReceiverEmail() {
		return receiverEmail;
	}


	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}


	public String getReceiverMobileNo() {
		return receiverMobileNo;
	}


	public void setReceiverMobileNo(String receiverMobileNo) {
		this.receiverMobileNo = receiverMobileNo;
	}


	public NotificationType getReceiverType() {
		return receiverType;
	}


	public void setReceiverType(NotificationType receiverType) {
		this.receiverType = receiverType;
	}


	public String getSenderEmail() {
		return senderEmail;
	}


	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}


	public String getTinyUrl() {
		return tinyUrl;
	}


	public void setTinyUrl(String tinyUrl) {
		this.tinyUrl = tinyUrl;
	}


	public LocalDateTime getSendTime() {
		return sendTime;
	}


	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
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


	@Override
	public String toString() {
		return "Notification [id=" + id + ", bookingId=" + bookingId + ", status=" + status + ", receiverEmail="
				+ receiverEmail + ", receiverMobileNo=" + receiverMobileNo + ", receiverType=" + receiverType
				+ ", senderEmail=" + senderEmail + ", tinyUrl=" + tinyUrl + ", sendTime=" + sendTime + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + "]";
	}
	
	
}
