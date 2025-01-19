package com.jair.biblioteca.models;

public enum Idiomas {
    INGLES("en", "Ingles"),
    ESPANOL("es", "Espanol");

    private final String idiomaIngles;
    private final String idiomaEspanol;

    Idiomas (String idiomaIngles, String idiomaEspanol){
        this.idiomaIngles = idiomaIngles;
        this.idiomaEspanol = idiomaEspanol;
    }

    public static Idiomas fromString(String text){
        for (Idiomas idiomas : Idiomas.values()){
            if (idiomas.idiomaIngles.equalsIgnoreCase(text)){
                return idiomas;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }

    public static Idiomas fromEspanol(String text){
        for (Idiomas idiomas : Idiomas.values()){
            if (idiomas.idiomaEspanol.equalsIgnoreCase(text)){
                return idiomas;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }

    public String getIdiomaIngles() {
        return idiomaIngles;
    }

    public String getIdiomaEspanol() {
        return idiomaEspanol;
    }
}

