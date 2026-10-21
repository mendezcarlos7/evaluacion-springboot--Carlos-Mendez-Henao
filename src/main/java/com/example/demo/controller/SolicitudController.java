package com.example.demo.controller;

import com.example.demo.entity.Solicitud;
import com.example.demo.repository.SolicitudRepository;
import com.example.demo.service.GestorSolicitudes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final GestorSolicitudes gestorSolicitudes;
    private final SolicitudRepository solicitudRepository;

    public SolicitudController(
            GestorSolicitudes gestorSolicitudes,
            SolicitudRepository solicitudRepository) {
        this.gestorSolicitudes = gestorSolicitudes;
        this.solicitudRepository = solicitudRepository;
    }

    @PostMapping
    public String registrarSolicitud(
            @RequestBody Solicitud solicitud) {
        return gestorSolicitudes.registrarSolicitud(solicitud);
    }

    @GetMapping
    public List<Solicitud> listarSolicitudes() {
        return gestorSolicitudes.listarSolicitudes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> buscarSolicitud(
            @PathVariable Long id) {

        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No existe una solicitud con el ID " + id
                ));

        return ResponseEntity.ok(solicitud);
    }

    @PostMapping("/procesar")
    public String procesarSolicitudes() {
        gestorSolicitudes.procesarSolicitudes();
        return "Procesamiento de solicitudes finalizado.";
    }

    @GetMapping("/historial")
    public String mostrarHistorial() {
        gestorSolicitudes.mostrarHistorial();
        return "Consulta del historial finalizada. Revisa la consola.";
    }

    @PostMapping("/opcion/{opcion}")
    public String ejecutarOpcion(@PathVariable int opcion) {
        gestorSolicitudes.ejecutarOpcion(opcion);
        return "Operación ejecutada.";
    }
}
