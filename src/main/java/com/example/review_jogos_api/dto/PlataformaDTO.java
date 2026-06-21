package com.example.review_jogos_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlataformaDTO(Long id,
                            String name,
                            String slug,
                            @JsonProperty("games_count") Integer gamesCount,
                            String image,
                            @JsonProperty("image_background") String imageBackground) {

}
