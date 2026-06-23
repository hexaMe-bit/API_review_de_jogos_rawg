package com.example.review_jogos_api.controller;

import com.example.review_jogos_api.dto.ReviewRequestDTO;
import com.example.review_jogos_api.dto.ReviewResponseDTO;
import com.example.review_jogos_api.service.GameService;
import com.example.review_jogos_api.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class ReviewController {
    private final ReviewService reviewService;
    private final GameService gameService;

    public ReviewController(ReviewService reviewService, GameService gameService) {
        this.reviewService = reviewService;
        this.gameService = gameService;
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseDTO> createReviews(@RequestBody ReviewRequestDTO reviews) {
        String idJogo = gameService.navegarJogosPorNome(reviews.nomeJogo());

        ReviewResponseDTO saved = reviewService.createReview(reviews, idJogo );

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/reviews")
    public List<ReviewRequestDTO> getAll() {
        return  reviewService.getAllReviews();
    }

    @GetMapping("/reviews/{id}")
    public ReviewRequestDTO getById(@PathVariable UUID id) {
        return reviewService.getReviewById(id);
    }

    @PatchMapping("/reviews/{id}")
    public ResponseEntity<ReviewRequestDTO> updateById(@PathVariable UUID id, @RequestBody ReviewRequestDTO dto) {
        ReviewRequestDTO updatedReview = reviewService.updateReview(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedReview);

    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);

        return ResponseEntity.noContent().build();
    }

}
