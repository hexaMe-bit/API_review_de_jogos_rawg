package com.example.review_jogos_api.controller;

import com.example.review_jogos_api.Client.RawgClient;
import com.example.review_jogos_api.dto.GameResultDTO;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/rawg")
public class RawgController {

    private final RawgClient rawgClient;

    public RawgController(RawgClient rawgClient) {
        this.rawgClient = rawgClient;
    }

    @RateLimiter(name= "rawgApiLimiter")
    @GetMapping("/jogos")
    public Mono<ResponseEntity<GameResultDTO>> buscarJogos(@RequestParam String nome,
                                                           @RequestParam(defaultValue = "1") int page,
                                                           @RequestParam(defaultValue = "20") int size) {

        return rawgClient.buscarJogos(nome, page, size)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.status(503).build());
        }



}
