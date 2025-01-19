package com.jair.biblioteca.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Libros")
public class LibrosBD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private List<String> resumen;

    @ElementCollection(targetClass = Idiomas.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Libro_Idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Enumerated(EnumType.STRING)
    private List<Idiomas> idiomas;

    private Double descargasTotales;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autores", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "libro_id"), // Clave foránea de LibrosBD
            inverseJoinColumns = @JoinColumn(name = "autor_id") // Clave foránea de PersonsBD
    )
    private List<PersonsBD> autores = new ArrayList<>();

    public LibrosBD(){}

    public LibrosBD(Libros datosDeLibro) {
        this.titulo = datosDeLibro.titulo();
        this.resumen = datosDeLibro.resumen();
        this.idiomas = datosDeLibro.idiomas().stream()
                .filter(this::isIdiomaValido)
                .map(Idiomas::fromString)
                .collect(Collectors.toList());
        if (this.idiomas.isEmpty()) {
            System.out.println("Advertencia: Ningún idioma válido encontrado para el libro: " + datosDeLibro.titulo());
        }
        this.descargasTotales = datosDeLibro.descargasTotales();
    }

    private boolean isIdiomaValido(String idioma){
        return Arrays.stream(Idiomas.values())
                .anyMatch(enumValue -> enumValue.getIdiomaIngles().equalsIgnoreCase(idioma) ||
                enumValue.getIdiomaEspanol().equalsIgnoreCase(idioma));
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getResumen() {
        return resumen;
    }

    public void setResumen(List<String> resumen) {
        this.resumen = resumen;
    }

    public List<Idiomas> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idiomas> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getDescargasTotales() {
        return descargasTotales;
    }

    public void setDescargasTotales(Double descargasTotales) {
        this.descargasTotales = descargasTotales;
    }

    public List<PersonsBD> getAutores() {
        return autores;
    }

    public void setAutores(List<PersonsBD> autores) {
        if (autores != null){
            for (PersonsBD autor : autores) {
                addAutor(autor);
            }
        }
        this.autores = autores;
    }

    private void addAutor(PersonsBD autor) {
        if (!autores.contains(autor)) {
            autores.add(autor);

            // Sincronización bidireccional
            if (!autor.getLibros().contains(this)) {
                autor.getLibros().add(this);
            }
        }
    }

    @Override
    public String toString() {
        return
                "\n ====================================================="+
                "\n Título: " + titulo +
                "\n Resumen: " + resumen +
                "\n Idiomas disponibles: " + idiomas +
                "\n Descargas totales: " + descargasTotales +
                "\n Autor(es): " + autores.stream().map(PersonsBD::getNombre).toList() +
                "\n ===================================================";
    }
}


