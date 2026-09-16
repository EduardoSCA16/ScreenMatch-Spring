package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.cglib.core.Local;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Double avaliacao;
    private LocalDate dataLancamento;

    // Constructor
    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.Titulo();
        this.numeroEpisodio = dadosEpisodio.NumeroEpisodio();
        try {
            this.avaliacao = Double.valueOf(dadosEpisodio.Avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodio.DataLancamento());
        } catch (DateTimeException e) {
            this.dataLancamento = null;
        }
    }

    // toString
    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo=" + titulo + '\'' +
                ", numeroEpisodio=" + numeroEpisodio +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento;
    }

    // Getters e Setters
    public Integer getTemporada() {
        return temporada;
    }
    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }
    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }
    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }
}
