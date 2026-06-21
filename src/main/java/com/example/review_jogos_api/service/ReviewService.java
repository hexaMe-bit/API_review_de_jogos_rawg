package com.example.review_jogos_api.service;

import com.example.review_jogos_api.Client.RawgClient;
import com.example.review_jogos_api.dto.ReviewRequestDTO;
import com.example.review_jogos_api.dto.ReviewResponseDTO;
import com.example.review_jogos_api.entity.ReviewEntity;
import com.example.review_jogos_api.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final GameService gameService;

    public ReviewService(ReviewRepository reviewRepository, GameService gc) {

        this.reviewRepository = reviewRepository;
        this.gameService = gc;

    }

    public ReviewResponseDTO createReview(ReviewRequestDTO review, Long id) {

        ReviewEntity novaReview = new ReviewEntity();
        novaReview.setTituloReview(review.tituloReview());
        novaReview.setConteudoReview(review.conteudoReview());
        novaReview.setNota(review.nota());
        novaReview.setCreatedAt(LocalDateTime.now());
        novaReview.setRawgGameId(id);



        ReviewEntity savedReview = reviewRepository.save(novaReview);

        return new ReviewResponseDTO(savedReview.getTituloReview(), savedReview.getConteudoReview(), savedReview.getNota(), savedReview.getRawgGameId());

    }

    public ReviewResponseDTO getReviewById(UUID id) {
        ReviewEntity review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("não encontrado!"));
        return new ReviewResponseDTO(review.getTituloReview(), review.getConteudoReview(), review.getNota(), review.getRawgGameId());
    }

    public void deleteReview(UUID id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("review não encontrada com ID: " + id);
        }

        reviewRepository.deleteById(id);
    }

    public ReviewResponseDTO updateReview(UUID id, ReviewResponseDTO reviewAtualizada) {
        ReviewEntity review = reviewRepository.findById(id).orElseThrow(()-> new RuntimeException("review não encontrada!"));

        if (reviewAtualizada.tituloReview() != null) {
            review.setTituloReview(reviewAtualizada.tituloReview());
        }

        if (reviewAtualizada.conteudoReview() != null) {
            review.setConteudoReview(reviewAtualizada.conteudoReview());
        }

        if (reviewAtualizada.nota() != null) {
            review.setNota(reviewAtualizada.nota());
        }

        ReviewEntity updatedReview = reviewRepository.save(review);

        return new ReviewResponseDTO(updatedReview.getTituloReview(), updatedReview.getConteudoReview(), updatedReview.getNota(), updatedReview.getRawgGameId());

    }

    public List<ReviewResponseDTO> getAllReviews() {
        List<ReviewEntity> reviews = reviewRepository.findAll();

        return reviews.stream().map(r -> new ReviewResponseDTO(r.getTituloReview(), r.getConteudoReview(), r.getNota(), r.getRawgGameId())).toList();
    }


}
