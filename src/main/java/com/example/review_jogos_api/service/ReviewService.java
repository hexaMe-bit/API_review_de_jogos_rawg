package com.example.review_jogos_api.service;

import com.example.review_jogos_api.Client.RawgClient;
import com.example.review_jogos_api.dto.ReviewRequestDTO;
import com.example.review_jogos_api.dto.ReviewResponseDTO;
import com.example.review_jogos_api.entity.ReviewEntity;
import com.example.review_jogos_api.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public ReviewRequestDTO createReview(ReviewRequestDTO review, String id) {

        ReviewEntity novaReview = new ReviewEntity();
        novaReview.setTituloReview(review.tituloReview());
        novaReview.setConteudoReview(review.conteudoReview());
        novaReview.setNota(review.nota());
        novaReview.setCreatedAt(LocalDateTime.now());
        novaReview.setRawgGameId(id);

        ReviewEntity savedReview = reviewRepository.save(novaReview);
        String nomeDoJogoFormat = gameService.navegarJogoPeloId(savedReview.getRawgGameId());

        return new ReviewRequestDTO(savedReview.getTituloReview(), savedReview.getConteudoReview(), nomeDoJogoFormat ,savedReview.getNota());

    }

    public ReviewRequestDTO getReviewById(UUID id) {
        ReviewEntity review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("não encontrado!"));
        String nomeJogo = gameService.navegarJogoPeloId(review.getRawgGameId());
        return new ReviewRequestDTO(review.getTituloReview(), review.getConteudoReview(), nomeJogo ,review.getNota());
    }

    public void deleteReview(UUID id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        }
        throw new RuntimeException("review com id: " + id + "não encontrada!");
    }

    public ReviewRequestDTO updateReview(UUID id, ReviewRequestDTO reviewAtualizada) {
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
        String nomeJogo = gameService.navegarJogoPeloId(updatedReview.getRawgGameId());
        return new ReviewRequestDTO(updatedReview.getTituloReview(), updatedReview.getConteudoReview(), nomeJogo ,updatedReview.getNota());

    }

    public List<ReviewRequestDTO> getAllReviews() {
        List<ReviewEntity> reviews = reviewRepository.findAll();

        return reviews.stream().map(r -> new ReviewRequestDTO(r.getTituloReview(), r.getConteudoReview(),gameService.navegarJogoPeloId(r.getRawgGameId()) ,r.getNota())).toList();
    }


}
