package com.prueba.consultorio.repository;


import com.prueba.consultorio.model.Appointment;
import com.prueba.consultorio.model.Consultorio;
import com.prueba.consultorio.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByConsultorio(Consultorio consultorio);

    List<Appointment> findByConsultorioAndAppointmentTime(Consultorio consultorio, String appointmentTime);
    List<Appointment> findByDoctorAndAppointmentTimeBetween(Doctor doctor, String s, String s1);
    List<Appointment> findByPatientNameAndAppointmentTimeBetween(String patientName, String s, String s1);
}




