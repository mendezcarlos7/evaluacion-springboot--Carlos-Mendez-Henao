
package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "empleados")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empleado extends Usuario {

    private String cargo;

    @Positive(message = "El salario debe ser mayor que cero")
    private Double salario;

    @Override
    public String obtenerDescripcion() {
        return "Empleado con cargo: " + cargo + " y salario: " + salario;
    }
}

