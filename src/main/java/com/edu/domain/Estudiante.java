package com.edu.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Estudiante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private LocalDate nacimiento;

    public Estudiante() { super(); }

    public Estudiante initialize(String nombre, LocalDate nacimiento) {
        setNombre(nombre);
        setNacimiento(nacimiento);
        return this;
    }

    public Estudiante(String nombre, LocalDate nacimiento) {
        initialize(nombre, nacimiento);
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    
}
