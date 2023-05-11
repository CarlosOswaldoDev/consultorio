package com.prueba.consultorio.repository;

import com.prueba.consultorio.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

    @Repository
    public interface DoctorRepository extends JpaRepository<Doctor, Long> {

        @Query("SELECT d FROM Doctor d WHERE d.specialty = :specialty AND d.name = :name")
        Doctor findBySpecialtyAndName(@Param("specialty") String specialty, @Param("name") String name);

    }
