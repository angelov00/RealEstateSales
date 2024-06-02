package com.softuni.angelovestates.repository;

import com.softuni.angelovestates.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT AVG(r.rating) FROM Review r")
    Optional<Double> findAverageRating();

    //@Query("SELECT r FROM Review r LIMIT 3") Limit is not supported in HQL
    @Query(value = "SELECT * FROM reviews ORDER BY RAND() LIMIT 3", nativeQuery = true)
    List<Review> findRandomReviews();
}
