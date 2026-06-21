package com.example.review_jogos_api.Client;

import com.example.review_jogos_api.dto.GameResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class RawgClient {
    private final WebClient webClient;
    private final RestClient restClient;

    @Value("${rawg.api.url}")
    private String url;

    @Value("${rawg.api.key}")
    private String apiKey;

    public RawgClient(WebClient rawgClient, RestClient.Builder builder) {
        this.webClient = rawgClient;
        this.restClient = builder.baseUrl(url).build();

    }
    public Mono<GameResultDTO> buscarJogos(String termo, int pagina, int tamanho) {
        return webClient.get()
                .uri(builder -> builder
                        .path("/games")
                        .queryParam("search", termo)
                        .queryParam("page", pagina)
                        .queryParam("page_size", Math.min(tamanho, 40))
                        .build()
                )
                .retrieve()
                .bodyToMono(GameResultDTO.class);

    }

    public Long buscarJogos(String nome) {
        Map<String, Object> response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/games")
                        .queryParam("Key", apiKey)
                        .queryParam("page_size", 1)
                        .build())
                .retrieve()
                .body(Map.class);

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

        if (results != null && !results.isEmpty()) {
            return (Long) results.get(0).get("id");
        }

        return null;
    }

    public String pegarNome(String nomeDoJogo) {
        Map<String, Object> response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/games")
                        .queryParam("search", nomeDoJogo)
                        .build()
                )
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response != null || response.containsKey("results")) {
            List<Map<String, Object> > results = (List<Map<String, Object>>) response.get("results");

            if (!results.isEmpty()) {
                Map<String, Object> primeiroJogo = results.get(0);
                return (String) primeiroJogo.get("name");
            }
        }

        return null;

    }

}
