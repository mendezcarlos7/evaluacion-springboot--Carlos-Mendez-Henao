package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empleado")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empleado extends Usuario {

    private String cargo;
    private Double salario;

    @Override
    public String obtenerDescripcion() {
        return "Empleado con cargo: " + cargo + " y salario: " + salario;
    }
}
