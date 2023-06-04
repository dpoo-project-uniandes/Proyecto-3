package hotel_system.pruebas;
import org.junit.jupiter.api.*;
import org.mockito.*;

import hotel_system.models.Estadia;
import hotel_system.models.EstadoReserva;
import hotel_system.models.Habitacion;
import hotel_system.models.Reserva;
import hotel_system.models.Titular;

import java.util.*;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;

public class ReservaTest {

    @Test
    public void testCrearReserva() {
        Date fechaDeLlegada = new Date();
        Date fechaDeSalida = new Date();
        Titular titular = Mockito.mock(Titular.class);
        Integer cantidadPersonas = 2;
        List<Habitacion> habitaciones = Mockito.mock(List.class);

        Reserva reserva = new Reserva(fechaDeLlegada, fechaDeSalida, titular, cantidadPersonas, habitaciones);

        Assertions.assertNotNull(reserva.getNumero());
        Assertions.assertEquals(fechaDeLlegada, reserva.getFechaDeLlegada());
        Assertions.assertNotNull(reserva.getFechaDeCreacion());
        Assertions.assertEquals(cantidadPersonas, reserva.getCantidadPersonas());
        Assertions.assertEquals(fechaDeSalida, reserva.getFechaDeSalida());
        Assertions.assertEquals(titular, reserva.getTitular());
        Assertions.assertEquals(habitaciones, reserva.getHabitaciones());
        Assertions.assertNotNull(reserva.getTarifaTotal());
        Assertions.assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
    }


    @Test
    public void testCrearReservaConIdExistente() {
        // Datos de prueba
        Long id = 100L;
        Date fechaDeLlegada = new Date();
        Date fechaDeSalida = new Date();
        Titular titular = mock(Titular.class);
        Integer cantidadPersonas = 2;
        List<Habitacion> habitaciones = Arrays.asList(mock(Habitacion.class), mock(Habitacion.class));

        // Crear instancia de Reserva con un ID específico
        Reserva reserva = new Reserva(id, fechaDeLlegada, fechaDeSalida, titular, cantidadPersonas, habitaciones);

        // Verificar que el ID se haya asignado correctamente
        Assertions.assertEquals(id, reserva.getId());
        Assertions.assertEquals(id, reserva.getNumero());
    }

    @Test
    public void testCrearReservaConDatosCompletos() {
        // Datos de prueba
        Long numero = 1L;
        Double tarifaTotal = 200.0;
        EstadoReserva estado = EstadoReserva.CONFIRMADA;
        Integer cantidadPersonas = 2;
        Date fechaDeLlegada = new Date();
        Date fechaDeSalida = new Date();
        Date fechaDeCreacion = new Date();
        Titular titular = mock(Titular.class);
        Estadia estadia = mock(Estadia.class);
        List<Habitacion> habitaciones = Arrays.asList(mock(Habitacion.class), mock(Habitacion.class));

        // Crear instancia de Reserva con todos los datos
        Reserva reserva = new Reserva(numero, tarifaTotal, estado, cantidadPersonas, fechaDeLlegada, fechaDeSalida,
                fechaDeCreacion, titular, estadia, habitaciones);

        // Verificar que los atributos se hayan asignado correctamente
        Assertions.assertEquals(numero, reserva.getId());
        Assertions.assertEquals(numero, reserva.getNumero());
        Assertions.assertEquals(tarifaTotal, reserva.getTarifaTotal());
        Assertions.assertEquals(estado, reserva.getEstado());
        Assertions.assertEquals(cantidadPersonas, reserva.getCantidadPersonas());
        Assertions.assertEquals(fechaDeLlegada, reserva.getFechaDeLlegada());
        Assertions.assertEquals(fechaDeSalida, reserva.getFechaDeSalida());
        Assertions.assertEquals(fechaDeCreacion, reserva.getFechaDeCreacion());
        Assertions.assertEquals(titular, reserva.getTitular());
        Assertions.assertEquals(estadia, reserva.getEstadia());
        Assertions.assertEquals(habitaciones, reserva.getHabitaciones());
    }
    @Test
    public void testCerrar() {
        Reserva reserva = new Reserva(new Date(), new Date(), mock(Titular.class), 2, Arrays.asList(mock(Habitacion.class)));
        reserva.cerrar();

        Assertions.assertEquals(EstadoReserva.CERRADA, reserva.getEstado());
        Assertions.assertNotNull(reserva.getTarifaTotal());
    }

    @Test
    public void testCalcularTarifaTotal() {
        Habitacion habitacion1 = mock(Habitacion.class);
        when(habitacion1.calcularTarifa((java.sql.Date) any(Date.class), (java.sql.Date) any(Date.class))).thenReturn(100.0);

        Habitacion habitacion2 = mock(Habitacion.class);
        when(habitacion2.calcularTarifa((java.sql.Date) any(Date.class), (java.sql.Date) any(Date.class))).thenReturn(150.0);

        Reserva reserva = new Reserva(new Date(), new Date(), mock(Titular.class), 2, Arrays.asList(habitacion1, habitacion2));
        Double tarifaTotal = reserva.calcularTarifaTotal();

        Assertions.assertEquals(250.0, tarifaTotal);
    }

    @Test
    public void testConfirmarReserva() {
        Habitacion habitacion1 = mock(Habitacion.class);
        Habitacion habitacion2 = mock(Habitacion.class);
        Reserva reserva = new Reserva(new Date(), new Date(), mock(Titular.class), 2, Arrays.asList(habitacion1, habitacion2));
        reserva.confirmarReserva();

        Assertions.assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
        verify(habitacion1).modificarDisponibilidad((java.sql.Date) any(Date.class), (java.sql.Date) any(Date.class), eq(reserva), eq(false));
        verify(habitacion2).modificarDisponibilidad((java.sql.Date) any(Date.class), (java.sql.Date) any(Date.class), eq(reserva), eq(false));
    }

    @Test
    public void testCancelarReserva() {
        Habitacion habitacion1 = mock(Habitacion.class);
        Habitacion habitacion2 = mock(Habitacion.class);
        Reserva reserva = new Reserva(new Date(), new Date(), mock(Titular.class), 2, Arrays.asList(habitacion1, habitacion2));
        reserva.cancelarReserva();

        Assertions.assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
        verify(habitacion1).modificarDisponibilidad((java.sql.Date) any(Date.class), (java.sql.Date) any(Date.class), eq(null), eq(true));
        verify(habitacion2).modificarDisponibilidad((java.sql.Date) any(Date.class), (java.sql.Date) any(Date.class), eq(null), eq(true));
    }

    @Test
    public void testEstaActiva() {
        Reserva reserva1 = new Reserva(new Date(), new Date(), mock(Titular.class), 2, Arrays.asList(mock(Habitacion.class)));
        reserva1.setEstado(EstadoReserva.CONFIRMADA);
        Assertions.assertTrue(reserva1.estaActiva());

        Reserva reserva2 = new Reserva(new Date(), new Date(), mock(Titular.class), 2, Arrays.asList(mock(Habitacion.class)));
        reserva2.setEstado(EstadoReserva.CANCELADA);
        Assertions.assertFalse(reserva2.estaActiva());

        Reserva reserva3 = new Reserva(new Date(), new Date(), mock(Titular.class), 2, Arrays.asList(mock(Habitacion.class)));
        reserva3.setEstado(EstadoReserva.CERRADA);
        Assertions.assertFalse(reserva3.estaActiva());
    }
}

