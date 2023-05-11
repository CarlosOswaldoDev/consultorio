package com.prueba.consultorio.controller;

import com.prueba.consultorio.model.Appointment;
import com.prueba.consultorio.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    //Crear
    @PostMapping("/post")
    public Appointment createAppointment(@Valid @RequestBody Appointment appointment) {
        return appointmentService.createAppointment(appointment);
    }

    //Obetener por ID
    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    @GetMapping("/consultation/{consultationId}")
    public List<Appointment> getAppointmentsByConsultation(@PathVariable Long consultationId) {
        return appointmentService.getAppointmentsByConsultation(consultationId);
    }

    //Borrar
    @DeleteMapping("/{id}")
    public void cancelAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

    //Actualizar
    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @Valid @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(id, appointment);
    }

}
