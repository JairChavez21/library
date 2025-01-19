package com.jair.biblioteca.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Autores")
public class PersonsBD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double fechaDeNacimiento;
    private Double fechaDeMuerte;
    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<LibrosBD> libros = new ArrayList<>();

    public PersonsBD(){}

    public PersonsBD(Persons datosDePersonas){
        this.nombre = datosDePersonas.nombre();
        this.fechaDeNacimiento = datosDePersonas.fechaDeNacimeinto();
        this.fechaDeMuerte = datosDePersonas.dechaDeMuerte();
    }

    public void addLibro(LibrosBD libro) {
        if (libros == null) {
            libros = new ArrayList<>();
        }
        if (!libros.contains(libro)) {
            libros.add(libro);

            // Asegurar la sincronización bidireccional
            if (!libro.getAutores().contains(this)) {
                libro.getAutores().add(this);
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Double fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public List<LibrosBD> getLibros() {
        return libros;
    }

    public void setLibros(List<LibrosBD> libros) {
        this.libros = libros;
    }

    public Double getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(Double fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    @Override
    public String toString() {
        return
                "\n ========================================================="+
                "\n Nombre del Autor(a): " + nombre +
                "\n Fecha de nacimiento del autor: " + fechaDeNacimiento +
                "\n Fecha de muerte del autor: " + fechaDeMuerte +
                "\n Libros del autor: " + libros.stream().map(LibrosBD::getTitulo).toList();
    }
}
