package com.example.review_jogos_api.Client;

import com.example.review_jogos_api.dto.GameResultDTO;
import com.example.review_jogos_api.dto.PlataformaDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class PlatformClient {
    private final WebClient webClient;


    public PlatformClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<PlataformaDTO> listarTodasPlataformas() {
        return webClient.get()
                .uri(builder -> builder.path("/platforms").build())
                .retrieve()
                .bodyToFlux(PlataformaDTO.class);
    }

    public Flux<GameResultDTO> listarJogosPorPlataforma(Long plataformaId, int pagina) {
        return webClient.get()
                .uri(builder -> builder
                        .path("/platforms/" + plataformaId + "/games")
                        .queryParam("page", pagina)
                        .queryParam("page_size", 20)
                        .build()
                )
                .retrieve()
                .bodyToFlux(GameResultDTO.class);
    }
}
