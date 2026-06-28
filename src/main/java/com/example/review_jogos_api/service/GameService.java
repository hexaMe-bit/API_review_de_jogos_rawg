package com.example.review_jogos_api.service;

import com.example.review_jogos_api.Client.RawgClient;
import com.example.review_jogos_api.dto.GameResultDTO;
import org.springframework.stereotype.Service;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import reactor.core.publisher.Mono;


import java.util.Set;
import java.util.UUID;

@Service
public class GameService {

    private final RawgClient rawgClient;

    public GameService(RawgClient rawgClient) {
        this.rawgClient = rawgClient;

    }

    @RateLimiter(name = "rawgApiLimiter")
    public Mono<GameResultDTO> navegarJogosPorNome(String termo, int pagina, int tamanho) {
        if (pagina < 1) pagina = 1;
        if (termo == null || termo.isBlank()) {
            return Mono.error(new IllegalArgumentException("O nome do jogo é obrigatório"));
        }
        return rawgClient.buscarJogos(termo, pagina, tamanho);
    }

    public String navegarJogosPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return "";
        }
        return rawgClient.buscarJogos(nome);
    }

    public String navegarJogos(String nomeDeBusca) {
        if(nomeDeBusca.equals("")) {
            throw new RuntimeException("nome do jogo tem de ser expecificado!");
        }
        return rawgClient.pegarNome(nomeDeBusca);
    }

    public String navegarJogoPeloId(String ids) {
        if(ids.equals("")) {
            throw new RuntimeException("o id do jogo não pode ser um campo vazio!");
        }

        return rawgClient.getGameById(ids);
    }
}
