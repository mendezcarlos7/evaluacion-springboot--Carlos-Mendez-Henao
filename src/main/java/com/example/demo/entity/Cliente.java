package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Usuario {

    private Integer puntosFidelidad;

    @Override
    public String obtenerDescripcion() {
        return "Cliente con puntos de fidelidad: " + puntosFidelidad;
    }
}
