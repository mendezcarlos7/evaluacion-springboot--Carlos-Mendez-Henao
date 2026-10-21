package com.example.demo.service;

import com.example.demo.entity.Solicitud;
import com.example.demo.repository.SolicitudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

@Component
public class GestorSolicitudes {

    private final SolicitudRepository solicitudRepository;

    // LISTA: almacena las solicitudes consultadas de la base de datos
    private final List<Solicitud> solicitudes = new ArrayList<>();

    // COLA: procesa solicitudes en orden de llegada
    private final Queue<Solicitud> colaSolicitudes = new LinkedList<>();

    // PILA: registra las solicitudes de las operaciones recientes
    private final Stack<Solicitud> historial = new Stack<>();

    public GestorSolicitudes(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;

        // Cargar las solicitudes que ya existen en la base de datos
        List<Solicitud> existentes = solicitudRepository.findAll();
        solicitudes.addAll(existentes);

        // Agregar a la cola las solicitudes pendientes
        for (Solicitud solicitud : existentes) {
            if ("PENDIENTE".equalsIgnoreCase(solicitud.getEstado())) {
                colaSolicitudes.offer(solicitud);
            }
        }
    }

    // Registrar y persistir una solicitud
    public String registrarSolicitud(Solicitud solicitud) {

        if (solicitud == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Los datos de la solicitud son obligatorios."
            );
        } else if (solicitud.getTitulo() == null
                || solicitud.getTitulo().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El título de la solicitud es obligatorio."
            );
        } else if (solicitud.getCliente() == null
                || solicitud.getCliente().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe indicar un cliente existente para la solicitud."
            );
        } else {
            Solicitud guardada = solicitudRepository.save(solicitud);

            solicitudes.add(guardada);
            colaSolicitudes.offer(guardada);
            historial.push(guardada);

            return "Solicitud registrada correctamente con ID: "
                    + guardada.getId();
        }
    }

    // Consultar las solicitudes
    public List<Solicitud> listarSolicitudes() {
        solicitudes.clear();
        solicitudes.addAll(solicitudRepository.findAll());

        return new ArrayList<>(solicitudes);
    }

    // Procesar solicitudes por orden de llegada
    public void procesarSolicitudes() {

        if (colaSolicitudes.isEmpty()) {
            System.out.println("No hay solicitudes pendientes.");
        } else {
            while (!colaSolicitudes.isEmpty()) {

                Solicitud solicitud = colaSolicitudes.poll();

                if (solicitud != null) {
                    historial.push(solicitud);

                    System.out.println(
                            "Solicitud procesada: "
                                    + solicitud.getTitulo()
                    );
                }
            }
        }
    }

    // Mostrar historial, primero la solicitud más reciente
    public void mostrarHistorial() {

        if (historial.isEmpty()) {
            System.out.println("El historial está vacío.");
        } else {
            for (int i = historial.size() - 1; i >= 0; i--) {
                Solicitud solicitud = historial.get(i);

                System.out.println(
                        "ID: " + solicitud.getId()
                                + " | Título: " + solicitud.getTitulo()
                                + " | Estado: " + solicitud.getEstado()
                );
            }
        }
    }

    // Ejecutar operaciones mediante switch
    public void ejecutarOpcion(int opcion) {

        switch (opcion) {
            case 1:
                System.out.println("Lista de solicitudes:");

                for (Solicitud solicitud : listarSolicitudes()) {
                    System.out.println(
                            "ID: " + solicitud.getId()
                                    + " | Título: " + solicitud.getTitulo()
                                    + " | Estado: " + solicitud.getEstado()
                    );
                }
                break;

            case 2:
                procesarSolicitudes();
                break;

            case 3:
                mostrarHistorial();
                break;

            case 4:
                System.out.println("Fin de la operación.");
                break;

            default:
                System.out.println("Opción no válida.");
                break;
        }
    }
}
