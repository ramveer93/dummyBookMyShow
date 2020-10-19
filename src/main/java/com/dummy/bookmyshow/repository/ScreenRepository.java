package com.dummy.bookmyshow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dummy.bookmyshow.entity.Screen;
import com.dummy.bookmyshow.entity.ScreenPk;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, ScreenPk> {

	@Query(value = " select * from screen where theater_id=:theaterId and movie_id = :movieId", nativeQuery = true)
	List<Screen> findScreenByTheaterIdAndMovieId(@Param("theaterId") String theaterId, @Param("movieId") String movieId);

}
