package com.jair.biblioteca.principal;

import com.jair.biblioteca.models.*;
import com.jair.biblioteca.repository.LibrosBDRepository;
import com.jair.biblioteca.repository.PersonsBDRepository;
import com.jair.biblioteca.services.ConvertResults;
import com.jair.biblioteca.services.InitialistSearch;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private InitialistSearch buscar = new InitialistSearch();
    private ConvertResults convertir = new ConvertResults();
    private static final String DIRECCION = "https://gutendex.com/books/";
    private Scanner teclado = new Scanner(System.in);
    private List<LibrosBD> libros;
    private List<PersonsBD> autores;
    private static final String MENU = """
            Elija la opción que desee:
            
            1 - Buscar libro nuevo por titulo
            2 - Top 10 libros más descargados
            3 - Mostrar libros por idioma
            4 - Mostrar todos los libros buscados
            5 - Buscar Autor por nombre
            6 - Buscar Autor por fecha
            7 - Mostrar todos los Autores buscados
            
            
            
            0 - Salir
            """;
    private LibrosBDRepository repositorio;
    private PersonsBDRepository repositorioP;

    public Principal(LibrosBDRepository repository, PersonsBDRepository repositoryP) {
        this.repositorio = repository;
        this.repositorioP = repositoryP;
    }

    public void iniciarApp(){
        System.out.println("Iniciando App");
        var opcion = -1;

        while (opcion != 0){
            System.out.println(MENU);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    adicionarLibroBuscado();
                    break;
                case 2:
                    topLibrosDescargados();
                    break;
                case 3:
                    buscarLibroIdioma();
                    break;
                case 4:
                    mostrarLibrosBuscados();
                    break;
                case 5:
                    mostrarAutorPorNombre();
                    break;
                case 6:
                    mostrarAutorPorFecha();
                    break;
                case 7:
                    mostrarListaDeAutores();
                    break;
                case 0:
                    System.out.println("Finalizando app");
                    break;
                default:
                    System.out.println("Opcion no valida, intentalo de nuevo: ");
                    teclado.nextLine();
                    break;
            }
        }
    }

    private void mostrarAutorPorNombre() {
        System.out.println("Escriba el nombre del autor que desea buscar: ");
        var nombreAutor = teclado.nextLine();

        List<PersonsBD> autoresEncontrados = repositorioP.findByNombreIgnoreCaseContaining(nombreAutor);
        if (autoresEncontrados.isEmpty()) {
            System.out.println("No se encontraron autores con el nombre " + nombreAutor);
        } else {
            System.out.println("Autores encontrados con el nombre " + nombreAutor + ":");
            autoresEncontrados.forEach(System.out::println);
        }
    }

    private void mostrarListaDeAutores() {
        autores = repositorioP.findAll();
        autores.forEach(System.out::println);
    }

    private void mostrarAutorPorFecha() {
        System.out.println("Introduce el año que deseas buscar: ");
        int anio = teclado.nextInt();
        teclado.nextLine();

        List<PersonsBD> autoresVivos = repositorioP.findByAnio(anio);

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores que estuvieran vivos en el año " + anio);
        } else {
            System.out.println("Autores vivos en el año " + anio + ":");
            autoresVivos.forEach(System.out::println);
        }

    }

    private void buscarLibroIdioma() {
        System.out.println("Escriba el idioma del que desea buscar libros (Epsanol o Ingles): ");
        var idioma = teclado.nextLine();
        var disponibilidad = Idiomas.fromEspanol(idioma);
        List<LibrosBD> librosPorIdioma = repositorio.findByIdiomas(disponibilidad);
        System.out.println("Los libros disponibles en el idioma "+disponibilidad+" son:");
        librosPorIdioma.forEach(System.out::println);
    }

    private void mostrarLibrosBuscados() {
        libros = repositorio.findAll();
        libros.forEach(System.out::println);
    }

    private void topLibrosDescargados() {
        var json = buscar.obtenerBusquda(DIRECCION);
        var resultado = convertir.obtenerDatos(json, Results.class);
        System.out.println("Top 10 de libros más descargados:");
        resultado.resultado().stream()
                .sorted(Comparator.comparing(Libros::descargasTotales).reversed())
                .limit(10)
                .map(libros -> libros.titulo().toUpperCase())
                .forEach(System.out::println);
    }

    private Libros buscarLibroNuevo() {
        System.out.println("Escribe el titulo del libro que deseas buscar: ");
        var titulo = teclado.nextLine();
        var json = buscar.obtenerBusquda(DIRECCION+"?search=" + titulo.replace(" ", "+"));
        var busqueda = convertir.obtenerDatos(json, Results.class);
        Optional<Libros> libroEncontrado = busqueda.resultado().stream()
                .filter(libros -> libros.titulo().toUpperCase().contains(titulo.toUpperCase()))
                .findFirst();
        if (libroEncontrado.isPresent()){
            System.out.println("Libro encontrado");
            return libroEncontrado.get();
        } else {
            return null;
        }
    }

    private void adicionarLibroBuscado(){
        Libros nuevoLibro = buscarLibroNuevo();
        if (nuevoLibro == null){
            System.out.println("Libro no encontrado");
        } else {
            autores = nuevoLibro.autores().stream()
                    .map(this::obtenerOCrearAutor)
                    .collect(Collectors.toList());

            LibrosBD adicionar = new LibrosBD(nuevoLibro);
            adicionar.setAutores(autores);

            for (PersonsBD autor : autores){
                autor.getLibros().add(adicionar); // Usa el metodo del libro para sincronizar la app
            }

            repositorio.save(adicionar); // Guarda el libro
            System.out.println("Libro añadido con éxito: ");
            System.out.println(adicionar);
        }
    }

    private PersonsBD obtenerOCrearAutor(Persons autor) {
        Optional<PersonsBD> autorExistente = repositorioP.findByNombre(autor.nombre());
        if (autorExistente.isPresent()) {
            return autorExistente.get();
        } else {
            System.out.println("Creando nuevo autor: " + autor.nombre());
            PersonsBD nuevoActor = new PersonsBD(autor);
            nuevoActor.setLibros(new ArrayList<>());
            repositorioP.save(nuevoActor);
            return nuevoActor;
        }
    }
}
