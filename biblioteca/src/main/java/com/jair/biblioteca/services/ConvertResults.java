package com.jair.biblioteca.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertResults implements IConvertResults{
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> classes) {
        try {
            return mapper.readValue(json, classes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
