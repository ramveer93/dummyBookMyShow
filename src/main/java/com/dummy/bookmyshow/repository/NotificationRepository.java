package com.dummy.bookmyshow.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dummy.bookmyshow.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID>{

}
