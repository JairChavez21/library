package com.jair.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Libros(@JsonAlias("title") String titulo,
                     @JsonAlias("subjects") List<String> resumen,
                     @JsonAlias("authors") List<Persons> autores,
                     @JsonAlias("languages") List<String> idiomas,
                     @JsonAlias("download_count") Double descargasTotales) {
}
