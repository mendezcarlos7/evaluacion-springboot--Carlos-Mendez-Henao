package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "administrador")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Administrador extends Usuario {

    private Integer nivelAcceso;

    @Override
    public String obtenerDescripcion() {
        return "Administrador con nivel de acceso: " + nivelAcceso;
    }
}
