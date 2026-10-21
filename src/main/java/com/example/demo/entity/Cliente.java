package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Usuario {

    private Integer puntosFidelidad;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Solicitud> solicitudes = new ArrayList<>();

    @Override
    public String obtenerDescripcion() {
        return "Cliente con puntos de fidelidad: " + puntosFidelidad;
    }
}