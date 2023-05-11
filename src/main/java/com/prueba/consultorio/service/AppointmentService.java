package com.prueba.consultorio.service;

import com.prueba.consultorio.exception.BusinessException;
import com.prueba.consultorio.model.Appointment;
import com.prueba.consultorio.model.Consultorio;
import com.prueba.consultorio.model.Doctor;
import com.prueba.consultorio.repository.AppointmentRepository;
import com.prueba.consultorio.repository.ConsultorioRepository;
import com.prueba.consultorio.repository.DoctorRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultorioRepository consultorioRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + appointmentId));
    }

    public Appointment createAppointment(Appointment appointment) {
        // Validaciones de negocio
        validateAppointment(appointment);

        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long appointmentId, Appointment appointmentDetails) {
        Appointment appointment = getAppointmentById(appointmentId);

        // Validaciones de negocio
        validateAppointment(appointmentDetails);

        appointment.setConsultorio(appointmentDetails.getConsultorio());
        appointment.setDoctor(appointmentDetails.getDoctor());
        appointment.setAppointmentTime(appointmentDetails.getAppointmentTime());
        appointment.setPatientName(appointmentDetails.getPatientName());

        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        appointmentRepository.delete(appointment);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        return appointmentRepository.findByDoctor(doctor);
    }

    public List<Appointment> getAppointmentsByConsultation(Long consultationId) {
        Consultorio consultorio = new Consultorio();
        consultorio.setId(consultationId);
        return appointmentRepository.findByConsultorio(consultorio);
    }

    // Métodos privados
    private void validateAppointment(Appointment appointment) {
        // Validar que no exista otra cita en el mismo consultorio y hora
        List<Appointment> appointments = appointmentRepository
                .findByConsultorioAndAppointmentTime(appointment.getConsultorio(), appointment.getAppointmentTime());
        if (!appointments.isEmpty()) {
            throw new BusinessException("Ya existe una cita agendada en el mismo consultorio y hora");
        }

        // Validar que el doctor tenga menos de 8 citas en el día
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id " + appointment.getDoctor().getId()));
        List<Appointment> appointmentsByDoctor = appointmentRepository.findByDoctorAndAppointmentTimeBetween(doctor,
                appointment.getAppointmentTime() + ":00", appointment.getAppointmentTime() + ":59");
        if (appointmentsByDoctor.size() >= 8) {
            throw new BusinessException("El doctor " + doctor.getFullName() + " tiene el máximo de citas permitidas en el día");
        }

        // Validar que no haya otra cita para el mismo paciente en el mismo día y menos de 2 horas después
        List<Appointment> appointmentsByPatient = appointmentRepository.findByPatientNameAndAppointmentTimeBetween(
                appointment.getPatientName(), appointment.getAppointmentTime() + ":00", appointment.getAppointmentTime() + ":59");
        if (appointmentsByPatient.stream().anyMatch(a -> a.getId() != appointment.getId())) {
            throw new BusinessException("El paciente " + appointment.getPatientName() + " ya tiene una cita agendada en el mismo día");
        }
        for (Appointment a : appointmentsByPatient) {
            LocalDateTime appointmentTime = LocalDateTime.parse(appointment.getAppointmentTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime aTime = LocalDateTime.parse(a.getAppointmentTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (Duration.between(appointmentTime, aTime).toHours() < 2) {
                throw new BusinessException("El paciente " + appointment.getPatientName() + " tiene otra cita en menos de 2 horas");
            }
        }
    }

}
