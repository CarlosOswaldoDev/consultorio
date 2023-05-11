package com.prueba.consultorio.controller;

import com.prueba.consultorio.model.Doctor;
import com.prueba.consultorio.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/doctors")
public class DoctorController {

        @Autowired
        private DoctorService doctorService;

        @GetMapping("/")
        public List<Doctor> getAllDoctors() {
            return doctorService.getAllDoctors();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") Long id) {
            Doctor doctor = doctorService.getDoctorById(id);
            if (doctor == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(doctor, HttpStatus.OK);
            }
        }

        @PostMapping("/")
        public ResponseEntity<Doctor> addDoctor(@RequestBody Doctor doctor) {
            if (doctorService.addDoctor(doctor)) {
                return new ResponseEntity<>(doctor, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        @PutMapping("/{id}")
        public ResponseEntity<Doctor> updateDoctor(@PathVariable("id") Long id, @RequestBody Doctor doctor) {
            Doctor existingDoctor = doctorService.getDoctorById(id);
            if (existingDoctor == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                doctor.setId(id);
                if (doctorService.updateDoctor(doctor)) {
                    return new ResponseEntity<>(doctor, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteDoctor(@PathVariable("id") Long id) {
            Doctor existingDoctor = doctorService.getDoctorById(id);
            if (existingDoctor == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                if (doctorService.deleteDoctor(existingDoctor)) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        }

    }
