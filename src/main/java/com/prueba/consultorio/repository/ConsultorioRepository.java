package com.prueba.consultorio.repository;

import com.prueba.consultorio.model.Consultorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultorioRepository extends JpaRepository<Consultorio, Long> {

    Optional<Consultorio> findByNumeroConsultorio(String numeroConsultorio);

}

