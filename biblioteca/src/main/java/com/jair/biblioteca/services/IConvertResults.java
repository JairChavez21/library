package com.jair.biblioteca.services;

public interface IConvertResults {
    <T> T obtenerDatos(String json, Class<T> classes);
}
