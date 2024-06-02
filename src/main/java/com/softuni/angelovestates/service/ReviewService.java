package com.softuni.angelovestates.service;

import com.softuni.angelovestates.model.DAO.ReviewDAO;
import com.softuni.angelovestates.model.DTO.ReviewAddDTO;
import com.softuni.angelovestates.model.entity.Review;
import com.softuni.angelovestates.model.entity.User;
import com.softuni.angelovestates.repository.ReviewRepository;
import com.softuni.angelovestates.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Review addReview(ReviewAddDTO reviewAddDTO, String email) {
        Optional<User> user = this.userRepository.findUserByEmail(email);
        Review review = modelMapper.map(reviewAddDTO, Review.class).setAuthor(user.get());
        this.reviewRepository.save(review);
        return review;
    }

    public List<ReviewDAO> getRandomReviews() {

        List<Review> reviews = reviewRepository.findRandomReviews();

        return reviews.stream().map(review -> new ReviewDAO()
                .setAuthor(review.getAuthor().toString())
                .setRating(review.getRating())
                .setComment(review.getComment())
        ).collect(Collectors.toList());
    }

    public double getAverageRate() {
        return this.reviewRepository.findAverageRating().orElse(0.0);
    }
}
