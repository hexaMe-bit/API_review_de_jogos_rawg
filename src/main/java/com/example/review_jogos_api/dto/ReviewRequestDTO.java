package com.example.review_jogos_api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public record ReviewRequestDTO(@JsonProperty("titulo_review") String tituloReview,@JsonProperty("conteudo_review") String conteudoReview, @JsonProperty("nome_jogo") String nomeJogo, Integer nota) {}
