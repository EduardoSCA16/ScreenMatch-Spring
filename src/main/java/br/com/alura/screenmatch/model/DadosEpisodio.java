package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String Titulo,
                            @JsonAlias("Episode") Integer NumeroEpisodio,
                            @JsonAlias("imdbRating") String Avaliacao,
                            @JsonAlias("Released") String DataLancamento) {
}
