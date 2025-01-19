package com.jair.biblioteca.repository;
import com.jair.biblioteca.models.PersonsBD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonsBDRepository extends JpaRepository<PersonsBD, Long> {
    Optional<PersonsBD> findByNombre(String nombre);
    @Query("SELECT p FROM PersonsBD p WHERE p.fechaDeNacimiento <= :anio AND (p.fechaDeMuerte >= :anio OR p.fechaDeMuerte IS NULL)")
    List<PersonsBD> findByAnio(@Param("anio") int anio);
    List<PersonsBD> findByNombreIgnoreCaseContaining(String nombre);
}
