
package com.example.demo.controller;

import com.example.demo.entity.Solicitud;
import com.example.demo.service.GestorSolicitudes;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final GestorSolicitudes gestorSolicitudes;

    public SolicitudController(GestorSolicitudes gestorSolicitudes) {
        this.gestorSolicitudes = gestorSolicitudes;
    }

    @GetMapping
    public List<Solicitud> listarSolicitudes() {
        return gestorSolicitudes.listarSolicitudes();
    }

    @PostMapping("/registrar")
    public String registrarSolicitud(@RequestBody Solicitud solicitud) {
        return gestorSolicitudes.registrarSolicitud(solicitud);
    }

    @PostMapping("/procesar")
    public String procesarSolicitudes() {
        gestorSolicitudes.procesarSolicitudes();
        return "Procesamiento de solicitudes finalizado.";
    }

    @GetMapping("/historial")
    public void mostrarHistorial() {
        gestorSolicitudes.mostrarHistorial();
    }

    @PostMapping("/opcion/{opcion}")
    public String ejecutarOpcion(@PathVariable int opcion) {
        gestorSolicitudes.ejecutarOpcion(opcion);
        return "Operación ejecutada.";
    }
}
