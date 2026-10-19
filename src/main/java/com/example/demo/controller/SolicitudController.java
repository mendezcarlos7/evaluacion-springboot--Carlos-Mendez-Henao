package com.example.demo.controller;

import com.example.demo.entity.Solicitud;
import com.example.demo.repository.SolicitudRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudRepository solicitudRepository;

    public SolicitudController(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Solicitud crearSolicitud(@Valid @RequestBody Solicitud solicitud) {
        return solicitudRepository.save(solicitud);
    }

    @GetMapping
    public List<Solicitud> listarSolicitudes() {
        return solicitudRepository.findAll();
    }

    @GetMapping("/{id}")
    public Solicitud buscarSolicitud(@PathVariable Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "No existe una solicitud con el ID " + id));
    }
}
