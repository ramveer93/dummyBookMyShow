package com.dummy.bookmyshow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dummy.bookmyshow.entity.Cast;

@Repository
public interface CastRepository extends JpaRepository<Cast, Long>{
	@Query(value = "select * from cast where movie_id=:movieId", nativeQuery=true)
	List<Cast> getCastByMovieId(@Param("movieId") String movieId);

}
