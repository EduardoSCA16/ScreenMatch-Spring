package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.DadosSerie;

public interface IConverteDados {
    <T> T converterDados(String json, Class<T> classe);
}
