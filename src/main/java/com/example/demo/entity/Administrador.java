package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.*;

@Entity
@Table(name = "administradores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Administrador extends Usuario {

    @Min(value = 1, message = "El nivel de acceso mínimo es 1")
    @Max(value = 5, message = "El nivel de acceso máximo es 5")
    private Integer nivelAcceso;

    @Override
    public String obtenerDescripcion() {
        return "Administrador con nivel de acceso: " + nivelAcceso;
    }
}


