package com.example.demo.service;

import com.example.demo.entity.Solicitud;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

@Component
public class GestorSolicitudes {

    // LISTA: almacena las solicitudes registradas en el gestor
    private final List<Solicitud> solicitudes = new ArrayList<>();

    // COLA: procesa solicitudes en orden de llegada
    private final Queue<Solicitud> colaSolicitudes = new LinkedList<>();

    // PILA: registra las acciones más recientes
    private final Stack<String> historial = new Stack<>();

    // Registrar y validar una solicitud
    public String registrarSolicitud(Solicitud solicitud) {

        if (solicitud == null) {
            return "Error: la solicitud no puede ser nula.";
        } else if (solicitud.getTitulo() == null
                || solicitud.getTitulo().trim().isEmpty()) {
            return "Error: el título es obligatorio.";
        } else {
            solicitudes.add(solicitud);
            colaSolicitudes.offer(solicitud);

            historial.push(
                    "Se registró la solicitud: " + solicitud.getTitulo()
            );

            return "Solicitud agregada correctamente.";
        }
    }

    // Consultar todas las solicitudes
    public List<Solicitud> listarSolicitudes() {
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
                    historial.push(
                            "Se procesó la solicitud: "
                                    + solicitud.getTitulo()
                    );

                    System.out.println(
                            "Solicitud procesada: "
                                    + solicitud.getTitulo()
                    );
                }
            }
        }
    }

    // Mostrar historial: primero la acción más reciente
    public void mostrarHistorial() {

        if (historial.isEmpty()) {
            System.out.println("El historial está vacío.");
        } else {
            for (int i = historial.size() - 1; i >= 0; i--) {
                System.out.println(historial.get(i));
            }
        }
    }

    // Ejecutar operaciones mediante switch
    public void ejecutarOpcion(int opcion) {

        switch (opcion) {
            case 1:
                System.out.println("Lista de solicitudes:");

                for (Solicitud solicitud : solicitudes) {
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

