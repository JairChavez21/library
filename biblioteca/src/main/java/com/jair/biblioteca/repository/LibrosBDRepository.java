package com.jair.biblioteca.repository;
import com.jair.biblioteca.models.Idiomas;
import com.jair.biblioteca.models.LibrosBD;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LibrosBDRepository extends JpaRepository<LibrosBD, Long> {
    Optional<LibrosBD> findByTituloContainsIgnoreCase(String titulo);
    List<LibrosBD> findTop10ByOrderByDescargasTotalesDesc();
    List<LibrosBD> findByIdiomas(Idiomas idiomas);
}
