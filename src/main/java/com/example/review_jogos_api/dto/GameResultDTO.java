package com.example.review_jogos_api.dto;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Cacheable(value = "jogos_rawg")
@RateLimiter(name = "rawgApiLimiter")
public record GameResultDTO(Long id, String name, String released,
                            double rating, String backgroundImage, List<PlataformaDTO> plataformas) {
}
