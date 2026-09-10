package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @JsonAlias é uma anotação do Jackson usada para apelidar
// @JsonProperty é uma anotação do Jackson que define o nome exato
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String Titulo,
                         @JsonAlias("totalSeasons") Integer TotalTemporadas,
                         @JsonAlias("imdbRating") String Avaliacao) {
}
