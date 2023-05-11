package com.prueba.consultorio.service;

import com.prueba.consultorio.model.Doctor;
import com.prueba.consultorio.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DoctorService {


        @Autowired
        private DoctorRepository doctorRepository;

        public List<Doctor> getAllDoctors() {
            return doctorRepository.findAll();
        }

        public Doctor getDoctorById(Long id) {
            Optional<Doctor> doctor = doctorRepository.findById(id);
            if (doctor.isPresent()) {
                return doctor.get();
            } else {
                return null;
            }
        }

        public boolean addDoctor(Doctor doctor) {
            if (doctorRepository.findBySpecialtyAndName(doctor.getSpecialty(), doctor.getName()) == null) {
                doctorRepository.save(doctor);
                return true;
            } else {
                return false;
            }
        }

        public boolean updateDoctor(Doctor doctor) {
            if (doctorRepository.findById(doctor.getId()).isPresent()) {
                doctorRepository.save(doctor);
                return true;
            } else {
                return false;
            }
        }

        public boolean deleteDoctor(Doctor doctor) {
            if (doctorRepository.findById(doctor.getId()).isPresent()) {
                doctorRepository.delete(doctor);
                return true;
            } else {
                return false;
            }
        }

    }

