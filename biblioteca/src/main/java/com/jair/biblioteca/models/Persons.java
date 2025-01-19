package com.jair.biblioteca.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Persons(@JsonAlias("birth_year") Double fechaDeNacimeinto,
                      @JsonAlias("death_year") Double dechaDeMuerte,
                      @JsonAlias("name") String nombre) {
}
