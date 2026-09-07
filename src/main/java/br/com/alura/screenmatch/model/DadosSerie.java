package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

// @JsonAlias é uma anotação do Jackson usada para apelidar
// @JsonProperty é uma anotação do Jackson que define o nome exato
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao) {
}
