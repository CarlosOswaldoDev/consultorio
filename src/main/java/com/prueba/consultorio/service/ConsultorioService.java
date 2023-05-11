package com.prueba.consultorio.service;

import com.prueba.consultorio.model.Consultorio;
import com.prueba.consultorio.repository.ConsultorioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class ConsultorioService {

        private final ConsultorioRepository consultorioRepository;

        public ConsultorioService(ConsultorioRepository consultorioRepository) {
            this.consultorioRepository = consultorioRepository;
        }

        public Consultorio guardarConsultorio(Consultorio consultorio) {
            return consultorioRepository.save(consultorio);
        }

        public List<Consultorio> buscarTodosLosConsultorios() {
            return consultorioRepository.findAll();
        }

        public Consultorio buscarConsultorioPorId(Long id) {
            return consultorioRepository.findById(id).orElse(null);
        }

        public Consultorio buscarConsultorioPorNumero(String numeroConsultorio) {
            return consultorioRepository.findByNumeroConsultorio(numeroConsultorio).orElse(null);
        }

        public void eliminarConsultorio(Consultorio consultorio) {
            consultorioRepository.delete(consultorio);
        }

    }

