package com.prueba.consultorio.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "doctor")
public class Doctor {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        private String name;

        @NotBlank
        @Column(name = "last_name")
        private String lastName;

        @NotBlank
        @Column(name = "second_last_name")
        private String secondLastName;

        public String getFullName() {
                return name + " " + lastName + " " + secondLastName;
        }

        @NotBlank
        private String specialty;

        // getters y setters


        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getSecondLastName() {
                return secondLastName;
        }

        public void setSecondLastName(String secondLastName) {
                this.secondLastName = secondLastName;
        }

        public String getSpecialty() {
                return specialty;
        }

        public void setSpecialty(String specialty) {
                this.specialty = specialty;
        }
}

