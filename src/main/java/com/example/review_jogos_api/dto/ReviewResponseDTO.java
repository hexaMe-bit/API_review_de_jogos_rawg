package com.example.review_jogos_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReviewResponseDTO(@JsonProperty("titulo_review") String tituloReview,@JsonProperty("conteudo_review") String conteudoReview, Integer nota, @JsonProperty("id_jogo") String rawgGameId) {
}
