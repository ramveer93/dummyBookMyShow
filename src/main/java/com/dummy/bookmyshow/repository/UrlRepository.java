package com.dummy.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dummy.bookmyshow.entity.Url;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{

}
