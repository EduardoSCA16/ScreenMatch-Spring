package br.com.alura.screenmatch.service;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {
    // ObjectMapper: tradutor entre JSON e Java
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    //<T> T: classe genérica
    public <T> T converterDados(String json, Class<T> classe) {
        try {
            // readValue: converter JSON para objeto Java
            return mapper.readValue(json, classe);
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}
