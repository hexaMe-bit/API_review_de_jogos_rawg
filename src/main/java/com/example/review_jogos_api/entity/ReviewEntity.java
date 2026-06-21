package com.example.review_jogos_api.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "review")
public class ReviewEntity {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @Column(name = "titulo_review", nullable = false, length = 100)
    private String tituloReview;

    @Column(columnDefinition = "TEXT", name = "conteudo_review", nullable = false)
    private String conteudoReview;

    @Column(nullable = false)
    private Integer nota;

    @Column(name = "rawg_game_id", nullable = false)
    private Long rawgGameId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
