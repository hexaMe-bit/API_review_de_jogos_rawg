package com.example.review_jogos_api.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
@Configuration
public class WebClientConfiguration {

    @Value("${rawg.api.key}")
    private String apiKey;

    @Value("${rawg.api.url}")
    private String url;


    @Bean
    public WebClient rawgWebClient() {

        return WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .filter(addApiKeyFilter()).build();
    }

    private ExchangeFilterFunction addApiKeyFilter() {
        return ((request, next) -> {
            ClientRequest newRequest = ClientRequest.from(request).url(java.net.URI.create(
                    request.url().toString() + (request.url().getQuery() == null ? "?": "&")
                    + "key=" + apiKey
            ))
                    .build();
            return next.exchange(newRequest);
        });

    }
    @Bean
    public RestClient restClient(RestClient.Builder builder) {

        return builder.baseUrl(url).build();

    }
}
