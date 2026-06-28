package com.example.review_jogos_api.Client;

import com.example.review_jogos_api.config.WebClientConfiguration;
import com.example.review_jogos_api.dto.GameResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RawgClient {
    private final WebClient webClient;
    private final RestClient restClient;

    private String url;

    private String apiKey;

    public RawgClient(WebClient rawgClient, RestClient restClient,@Value("${rawg.api.url}") String url
    ,@Value("${rawg.api.key}") String apiKey) {
        this.webClient = rawgClient;
        this.restClient = restClient;

        this.apiKey = apiKey;
        this.url = url;

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

    public String buscarJogos(String nome) {

        System.out.println("Entrou no método");
        System.out.println("API Key: [" + apiKey + "]");

        Map<String, Object> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/games")
                        .queryParam("key", apiKey)
                        .queryParam("search", nome)
                        .queryParam("page_size", 1)
                        .build())
                .retrieve()
                .body(Map.class);
        if (response != null && response.containsKey("results")) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");

            if (results != null && !results.isEmpty()) {
                Integer id = (Integer) results.getFirst().get("id");
                return id.toString();
            }
        }

        throw new RuntimeException("não foi possivel encontrar o jogo");
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

        if (response != null && response.containsKey("results")) {
            List<Map<String, Object> > results = (List<Map<String, Object>>) response.get("results");
            System.out.println(results);

            if (results != null && !results.isEmpty()) {
                Map<String, Object> primeiroJogo = results.getFirst();
                return (String) primeiroJogo.get("name");
            }
        }

        return "";

    }

    public String getGameById(String id) {
        Map<String, Object> response = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/games/{id}")
                        .queryParam("key", apiKey)
                        .build(id))
                .retrieve()
                .body(Map.class);
        String nomeJogo = "";

        if (response != null || response.containsKey("name")) {
            Object results =  response.get("name");

            if (results == null ) {
                throw new RuntimeException("nao encontrou resultado para o jogo com este id");
            }
            String primeiroJogo = results.toString();
            return (String) primeiroJogo;
        }

        return nomeJogo;
    }
}
