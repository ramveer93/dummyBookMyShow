package com.dummy.bookmyshow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dummy.bookmyshow.entity.Theater;
@Repository
public interface TheaterRepository extends JpaRepository<Theater, String> {

	@Query(value = "select distinct(city) from theater", nativeQuery = true)
	List<String> getSupportdCities();
	
	@Query(value = "select * from theater where theater_id = :theaterId and city = :city", nativeQuery = true)
	List<Theater> getScreensShowingMovie(@Param("theaterId")String theaterId, @Param("city")String city);

}
