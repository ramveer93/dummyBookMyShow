package com.dummy.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dummy.bookmyshow.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
