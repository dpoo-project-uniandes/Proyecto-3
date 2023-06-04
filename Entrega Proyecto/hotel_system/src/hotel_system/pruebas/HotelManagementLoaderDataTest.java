package hotel_system.pruebas;

import org.junit.jupiter.api.*;
import org.mockito.*;

import hotel_system.controllers.HotelManagementLoaderData;
import hotel_system.models.Disponibilidad;
import hotel_system.models.Estadia;
import hotel_system.models.EstadoReserva;
import hotel_system.models.Habitacion;
import hotel_system.models.Hotel;
//import hotel_system.models.PasarelaPago;
import hotel_system.models.Producto;
import hotel_system.models.Reserva;
import hotel_system.models.Restaurante;
import hotel_system.models.Rol;
import hotel_system.models.Servicio;
import hotel_system.models.Spa;
import hotel_system.models.TipoHabitacion;
import hotel_system.models.Titular;
import hotel_system.models.Usuario;
import hotel_system.utils.Utils;
import services.FileManager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class HotelManagementLoaderDataTest {

    @InjectMocks
    HotelManagementLoaderData hotelManagementLoaderData;

    @Mock
    FileManager fileManager;
    

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLimpiarDisponibilidadesSuccess() throws Exception {
        doNothing().when(fileManager).eliminarArchivo("disponibilidades.csv");
        doNothing().when(fileManager).agregarLineasCSV("disponibilidades.csv", List.of(List.of("id","numero_habitacion","fecha","estado","precio")));

        hotelManagementLoaderData.limpiarDisponibilidades();

        verify(fileManager, times(1)).eliminarArchivo("disponibilidades.csv");
        verify(fileManager, times(1)).agregarLineasCSV("disponibilidades.csv", List.of(List.of("id","numero_habitacion","fecha","estado","precio")));
    }

    @Test
    public void testLimpiarDisponibilidadesFailure() throws Exception {
        doThrow(new Exception("Error al eliminar el archivo")).when(fileManager).eliminarArchivo("disponibilidades.csv");

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.limpiarDisponibilidades());

        verify(fileManager, times(1)).eliminarArchivo("disponibilidades.csv");
        verify(fileManager, times(0)).agregarLineasCSV(anyString(), anyList());
    }
    @Test
    public void testCargarTipoHabitacionesSuccess() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("alias", "A1");
        mockData.put("balcon", "true");
        mockData.put("vista", "true");
        mockData.put("cocina", "false");
        mockData.put("camas_sencilla", "2");
        mockData.put("camas_doble", "1");
        mockData.put("camas_queen", "0");
        mockData.put("capacidad", "3");
        mockData.put("precio", "120.0");
        mockData.put("area", "40.0");
        mockData.put("aire_acondicionado", "true");
        mockData.put("calefaccion", "false");
        mockData.put("television", "true");
        mockData.put("cafetera", "false");
        mockData.put("cubrelecho", "true");
        mockData.put("tapetes_hipoalergenicos", "false");
        mockData.put("plancha", "true");
        mockData.put("secador_cabello", "false");
        mockData.put("voltaje", "110");
        mockData.put("usb_a", "true");
        mockData.put("usb_c", "false");
        mockData.put("desayuno", "true");

        when(fileManager.cargarArchivoCSV("tipo_habitaciones.csv")).thenReturn(List.of(mockData));

        Map<String, TipoHabitacion> resultado = hotelManagementLoaderData.cargarTipoHabitaciones();

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertTrue(resultado.containsKey("A1"));

        TipoHabitacion habitacion = resultado.get("A1");
        Assertions.assertEquals(3, habitacion.getCapacidad());
        Assertions.assertEquals(120.0, habitacion.getPrecio());
        //Agregar el resto de assertions
    }

    @Test
    public void testCargarTipoHabitacionesFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("tipo_habitaciones.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarTipoHabitaciones());
    }

    @Test
    public void testCargarTipoHabitacionesDataError() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("alias", "A1");
        mockData.put("balcon", "true");
        // Suponemos que se ingresó un string en lugar de un número para capacidad
        mockData.put("capacidad", "no es un número");

        when(fileManager.cargarArchivoCSV("tipo_habitaciones.csv")).thenReturn(List.of(mockData));

        Assertions.assertThrows(NumberFormatException.class, () -> hotelManagementLoaderData.cargarTipoHabitaciones());
    }
    
    @Mock
    Utils utils;

    @Test
    public void testCargarDisponibilidadesSuccess() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("id", "1");
        mockData.put("numero_habitacion", "101");
        mockData.put("fecha", "2023-07-01");
        mockData.put("estado", "true");
        mockData.put("precio", "120.0");

        Habitacion mockHabitacion = mock(Habitacion.class);
        Map<Integer, Habitacion> habitaciones = new HashMap<>();
        habitaciones.put(101, mockHabitacion);

        Reserva mockReserva = mock(Reserva.class);
        Map<Long, Reserva> reservas = new HashMap<>();
        reservas.put(1L, mockReserva);

        when(fileManager.cargarArchivoCSV("disponibilidades.csv")).thenReturn(List.of(mockData));
        when(hotelManagementLoaderData.cargarDisponibilidadesReservas()).thenReturn(new HashMap<Long, Long>() {{ put(1L, 1L); }});
        when(utils.stringToDate("2023-07-01")).thenReturn((java.sql.Date) new Date());

        Map<Long, Disponibilidad> resultado = hotelManagementLoaderData.cargarDisponibilidades(habitaciones, reservas);

        Assertions.assertEquals(1, resultado.size());
        verify(mockHabitacion, times(1)).agregarDisponibilidad(any(Disponibilidad.class));
    }

    @Test
    public void testCargarDisponibilidadesFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("disponibilidades.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarDisponibilidades(new HashMap<>(), new HashMap<>()));
    }

    @Test
    public void testCargarDisponibilidadesDataError() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("id", "1");
        mockData.put("numero_habitacion", "no es un número");  // Suponemos que se ingresó un string en lugar de un número para el número de habitación
        mockData.put("fecha", "2023-07-01");
        mockData.put("estado", "true");
        mockData.put("precio", "120.0");

        when(fileManager.cargarArchivoCSV("disponibilidades.csv")).thenReturn(List.of(mockData));

        Assertions.assertThrows(NumberFormatException.class, () -> hotelManagementLoaderData.cargarDisponibilidades(new HashMap<>(), new HashMap<>()));
    }
    @Mock
    Hotel hotel;

    @Test
    public void testCargarHabitacionesSuccess() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("numero_habitacion", "101");
        mockData.put("tipo_habitacion", "A1");

        TipoHabitacion mockTipoHabitacion = mock(TipoHabitacion.class);
        Map<String, TipoHabitacion> opcionesHabitacion = new HashMap<>();
        opcionesHabitacion.put("A1", mockTipoHabitacion);

        when(fileManager.cargarArchivoCSV("habitaciones.csv")).thenReturn(List.of(mockData));

        Map<Integer, Habitacion> resultado = hotelManagementLoaderData.cargarHabitaciones(opcionesHabitacion, hotel);

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertTrue(resultado.containsKey(101));

        Habitacion habitacion = resultado.get(101);
        Assertions.assertEquals(mockTipoHabitacion, habitacion.getTipo());
        // ... Agregar más validaciones aquí ...
    }

    @Test
    public void testCargarHabitacionesFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("habitaciones.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarHabitaciones(new HashMap<>(), hotel));
    }

    @Test
    public void testCargarHabitacionesDataError() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("numero_habitacion", "no es un número");  // Suponemos que se ingresó un string en lugar de un número para el número de habitación
        mockData.put("tipo_habitacion", "A1");

        when(fileManager.cargarArchivoCSV("habitaciones.csv")).thenReturn(List.of(mockData));

        Assertions.assertThrows(NumberFormatException.class, () -> hotelManagementLoaderData.cargarHabitaciones(new HashMap<>(), hotel));
    }
    @Test
    public void testCargarUsuariosSuccess() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("user", "usuario1");
        mockData.put("password", "password1");
        mockData.put("rol", "ADMIN");

        when(fileManager.cargarArchivoCSV("usuarios.csv")).thenReturn(List.of(mockData));

        Map<String, Usuario> resultado = hotelManagementLoaderData.cargarUsuarios();

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertTrue(resultado.containsKey("usuario1"));

        Usuario usuario = resultado.get("usuario1");
        Assertions.assertEquals("password1", usuario.getPassword());
        Assertions.assertEquals(Rol.ADMIN, usuario.getRol());
    }

    @Test
    public void testCargarUsuariosFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("usuarios.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarUsuarios());
    }

    @Test
    public void testCargarUsuariosDataError() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("user", "usuario1");
        mockData.put("password", "password1");
        mockData.put("rol", "ROL_NO_EXISTE");  // Suponemos que se ingresó un string no válido para el rol

        when(fileManager.cargarArchivoCSV("usuarios.csv")).thenReturn(List.of(mockData));

        Assertions.assertThrows(IllegalArgumentException.class, () -> hotelManagementLoaderData.cargarUsuarios());
    }
    @Test
    public void testLimpiarReservasSuccess() throws Exception {
        doNothing().when(fileManager).eliminarArchivo("reservas.csv");
        doNothing().when(fileManager).agregarLineasCSV("reservas.csv", List.of(List.of("numero","tarifa_total","estado","cantidad_personas","fecha_creacion","fecha_llegada","fecha_salida","titular_dni","numero_estadia")));
        doNothing().when(fileManager).eliminarArchivo("reservas_habitaciones.csv");
        doNothing().when(fileManager).agregarLineasCSV("reservas_habitaciones.csv", List.of(List.of("numero_reserva", "numero_habitacion")));

        List<Reserva> resultado = hotelManagementLoaderData.limpiarReservas();

        verify(fileManager, times(1)).eliminarArchivo("reservas.csv");
        verify(fileManager, times(1)).agregarLineasCSV("reservas.csv", List.of(List.of("numero","tarifa_total","estado","cantidad_personas","fecha_creacion","fecha_llegada","fecha_salida","titular_dni","numero_estadia")));
        verify(fileManager, times(1)).eliminarArchivo("reservas_habitaciones.csv");
        verify(fileManager, times(1)).agregarLineasCSV("reservas_habitaciones.csv", List.of(List.of("numero_reserva", "numero_habitacion")));

        Assertions.assertEquals(0, resultado.size());
    }

    @Test
    public void testLimpiarReservasFailure() throws Exception {
        doThrow(new Exception("Error al eliminar el archivo")).when(fileManager).eliminarArchivo("reservas.csv");

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.limpiarReservas());

        verify(fileManager, times(1)).eliminarArchivo("reservas.csv");
        verify(fileManager, times(0)).agregarLineasCSV(anyString(), anyList());
        verify(fileManager, times(0)).eliminarArchivo("reservas_habitaciones.csv");
        verify(fileManager, times(0)).agregarLineasCSV(anyString(), anyList());
    }

    @Test
    public void testCargarReservasSuccess() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("numero", "1");
        mockData.put("tarifa_total", "200.0");
        mockData.put("estado", "CONFIRMADA");
        mockData.put("cantidad_personas", "2");
        mockData.put("fecha_creacion", "2023-07-01");
        mockData.put("fecha_llegada", "2023-07-02");
        mockData.put("fecha_salida", "2023-07-04");
        mockData.put("titular_dni", "12345678");
        mockData.put("numero_estadia", "1");

        Titular mockTitular = mock(Titular.class);
        Map<String, Titular> titulares = new HashMap<>();
        titulares.put("12345678", mockTitular);

        List<Integer> mockReservasHabitaciones = List.of(101, 102);
        Map<Long, List<Integer>> reservasHabitaciones = new HashMap<>();
        reservasHabitaciones.put(1L, mockReservasHabitaciones);

        Habitacion mockHabitacion1 = mock(Habitacion.class);
        Habitacion mockHabitacion2 = mock(Habitacion.class);
        Map<Integer, Habitacion> habitaciones = new HashMap<>();
        habitaciones.put(101, mockHabitacion1);
        habitaciones.put(102, mockHabitacion2);

        when(fileManager.cargarArchivoCSV("reservas.csv")).thenReturn(List.of(mockData));
        when(hotelManagementLoaderData.cargarTitulares()).thenReturn(titulares);
        when(hotelManagementLoaderData.cargarReservasHabitaciones()).thenReturn(reservasHabitaciones);
        when(utils.stringToDate("2023-07-01")).thenReturn((java.sql.Date) new Date());
        when(utils.stringToDate("2023-07-02")).thenReturn((java.sql.Date) new Date());
        when(utils.stringToDate("2023-07-04")).thenReturn((java.sql.Date) new Date());

        Map<Long, Reserva> resultado = hotelManagementLoaderData.cargarReservas(habitaciones);

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertTrue(resultado.containsKey(1L));

        Reserva reserva = resultado.get(1L);
        Assertions.assertEquals(200.0, reserva.getTarifaTotal());
        Assertions.assertEquals(EstadoReserva.CONFIRMADA, reserva.getEstado());
        // ... Agregar más validaciones aquí ...
    }

    @Test
    public void testCargarReservasFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("reservas.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarReservas(new HashMap<>()));
    }

    @Test
    public void testCargarReservasDataError() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("numero", "1");
        mockData.put("tarifa_total", "no es un número");
        mockData.put("estado", "CONFIRMADA");
        mockData.put("cantidad_personas", "2");
        mockData.put("fecha_creacion", "2023-07-01");
        mockData.put("fecha_llegada", "2023-07-02");
        mockData.put("fecha_salida", "2023-07-04");
        mockData.put("titular_dni", "12345678");
        mockData.put("numero_estadia", "1");

        when(fileManager.cargarArchivoCSV("reservas.csv")).thenReturn(List.of(mockData));

        Assertions.assertThrows(NumberFormatException.class, () -> hotelManagementLoaderData.cargarReservas(new HashMap<>()));
    }
    @Test
    public void testCargarEstadiasSuccess() throws Exception {
        Map<String, String> mockHuespedesData1 = new HashMap<>();
        mockHuespedesData1.put("numero_estadia", "1");
        mockHuespedesData1.put("dni_huesped", "12345678");

        Map<String, String> mockHuespedesData2 = new HashMap<>();
        mockHuespedesData2.put("numero_estadia", "1");
        mockHuespedesData2.put("dni_huesped", "87654321");

        Map<String, Titular> mockHuespedes = new HashMap<>();
        Titular mockTitular1 = mock(Titular.class);
        Titular mockTitular2 = mock(Titular.class);
        mockHuespedes.put("12345678", mockTitular1);
        mockHuespedes.put("87654321", mockTitular2);

        when(fileManager.cargarArchivoCSV("huespedes_estadias.csv")).thenReturn(List.of(mockHuespedesData1, mockHuespedesData2));
        when(hotelManagementLoaderData.cargarTitulares()).thenReturn(mockHuespedes);

        Map<String, String> mockData = new HashMap<>();
        mockData.put("id", "1");
        mockData.put("numero_reserva", "1");
        mockData.put("fecha_ingreso", "2023-07-01");
        mockData.put("fecha_salida", "2023-07-05");
        mockData.put("factura_id", "1");

        Reserva mockReserva = mock(Reserva.class);
        Map<Long, Reserva> reservas = new HashMap<>();
        reservas.put(1L, mockReserva);

        when(fileManager.cargarArchivoCSV("estadias.csv")).thenReturn(List.of(mockData));
        when(utils.stringToDate("2023-07-01")).thenReturn((java.sql.Date) new Date());
        when(utils.stringToDate("2023-07-05")).thenReturn((java.sql.Date) new Date());

        Map<Long, Estadia> resultado = hotelManagementLoaderData.cargarEstadias(reservas);

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertTrue(resultado.containsKey(1L));

        Estadia estadia = resultado.get(1L);
        Assertions.assertEquals(mockReserva, estadia.getReserva());
        // ... Agregar más validaciones aquí ...
    }

    @Test
    public void testCargarEstadiasFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("estadias.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarEstadias(new HashMap<>()));
    }

    @Test
    public void testCargarEstadiasDataError() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("id", "1");
        mockData.put("numero_reserva", "no es un número");
        mockData.put("fecha_ingreso", "2023-07-01");
        mockData.put("fecha_salida", "2023-07-05");
        mockData.put("factura_id", "1");

        when(fileManager.cargarArchivoCSV("estadias.csv")).thenReturn(List.of(mockData));

        Assertions.assertThrows(NumberFormatException.class, () -> hotelManagementLoaderData.cargarEstadias(new HashMap<>()));
    }
    @Test
    public void testCargarHotelSuccess() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("parqueadero", "true");
        mockData.put("piscina", "true");
        mockData.put("zonas_humedas", "false");
        mockData.put("bbq", "true");
        mockData.put("wifi", "true");
        mockData.put("recepcion_24hrs", "true");
        mockData.put("mascotas", "false");

        when(fileManager.cargarArchivoCSV("caracteristicas_hotel.csv")).thenReturn(List.of(mockData));

        Hotel hotel = hotelManagementLoaderData.cargarHotel();

        Assertions.assertTrue(hotel.getConParqueaderoIncluido());
        Assertions.assertTrue(hotel.getConPiscina());
        Assertions.assertFalse(hotel.getConZonasHumedas());
        // ... Agregar más validaciones aquí ...
    }

    @Test
    public void testCargarHotelFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("caracteristicas_hotel.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarHotel());
    }

    @Test
    public void testCargarHotelDataError() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("parqueadero", "no es un booleano");

        when(fileManager.cargarArchivoCSV("caracteristicas_hotel.csv")).thenReturn(List.of(mockData));

        Assertions.assertThrows(IllegalArgumentException.class, () -> hotelManagementLoaderData.cargarHotel());
    }
//    @Test
//    public void testCargarPasarelasSuccess() throws Exception {
//        Map<String, String> mockData = new HashMap<>();
//        mockData.put("class_name", "com.example.Pasarela1");
//
//        when(fileManager.cargarArchivoCSV("pasarelas.csv")).thenReturn(List.of(mockData));
//
//        PasarelaPago mockPasarela = mock(PasarelaPago.class);
//        ClassLoader mockClassLoader = mock(ClassLoader.class);
//        Class<?> mockClass = mock(Class.class);
//        when(mockClassLoader.loadClass("com.example.Pasarela1")).thenReturn(mockClass);
//        when(mockClass.getDeclaredConstructor()).thenReturn(mockConstructor);
//        when(mockConstructor.newInstance()).thenReturn(mockPasarela);
//
//        Map<String, PasarelaPago> resultado = hotelManagementLoaderData.cargarPasarelas();
//
//        Assertions.assertEquals(1, resultado.size());
//        Assertions.assertTrue(resultado.containsKey(mockPasarela.getName()));
//        Assertions.assertEquals(mockPasarela, resultado.get(mockPasarela.getName()));
//    }
//
//    @Test
//    public void testCargarPasarelasFailure() throws Exception {
//        when(fileManager.cargarArchivoCSV("pasarelas.csv")).thenThrow(new Exception("Error al cargar el archivo"));
//
//        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarPasarelas());
//    }
//
//    @Test
//    public void testCargarPasarelasDataError() throws Exception {
//        Map<String, String> mockData = new HashMap<>();
//        mockData.put("class_name", "com.example.InvalidClass");
//
//        when(fileManager.cargarArchivoCSV("pasarelas.csv")).thenReturn(List.of(mockData));
//
//        Assertions.assertThrows(ClassNotFoundException.class, () -> hotelManagementLoaderData.cargarPasarelas());
//    }
    @Test
    public void testCargarProductosSuccess() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("id", "1");
        mockData.put("nombre", "Producto 1");
        mockData.put("precio", "100.0");

        when(fileManager.cargarArchivoCSV("productos.csv")).thenReturn(List.of(mockData));

        Map<Long, Producto> resultado = hotelManagementLoaderData.cargarProductos();

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertTrue(resultado.containsKey(1L));

        Producto producto = resultado.get(1L);
        Assertions.assertEquals("Producto 1", producto.getNombre());
        Assertions.assertEquals(100.0, producto.getPrecio());
    }

    @Test
    public void testCargarProductosFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("productos.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarProductos());
    }

    @Test
    public void testCargarProductosDataError() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("id", "1");
        mockData.put("nombre", "Producto 1");
        mockData.put("precio", "no es un número");

        when(fileManager.cargarArchivoCSV("productos.csv")).thenReturn(List.of(mockData));

        Assertions.assertThrows(NumberFormatException.class, () -> hotelManagementLoaderData.cargarProductos());
    }
    @Test
    public void testCargarServiciosSuccess() throws Exception {
        Restaurante mockRestaurante = mock(Restaurante.class);
        Spa mockSpa = mock(Spa.class);

        when(hotelManagementLoaderData.cargarServicioRestaurante()).thenReturn(mockRestaurante);
        when(hotelManagementLoaderData.cargarServicioSpa()).thenReturn(mockSpa);

        Map<Long, Servicio> resultado = hotelManagementLoaderData.cargarServicios();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertTrue(resultado.containsKey(mockRestaurante.getId()));
        Assertions.assertTrue(resultado.containsKey(mockSpa.getId()));
        Assertions.assertEquals(mockRestaurante, resultado.get(mockRestaurante.getId()));
        Assertions.assertEquals(mockSpa, resultado.get(mockSpa.getId()));
    }

    @Test
    public void testCargarServiciosFailure() throws Exception {
        when(hotelManagementLoaderData.cargarServicioRestaurante()).thenThrow(new Exception("Error al cargar el servicio Restaurante"));
        when(hotelManagementLoaderData.cargarServicioSpa()).thenThrow(new Exception("Error al cargar el servicio Spa"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarServicios());
    }
    @Test
    public void testCargarTitularesSuccess() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("dni", "12345678");
        mockData.put("nombre", "John Doe");
        mockData.put("edad", "30");
        mockData.put("email", "johndoe@example.com");
        mockData.put("telefono", "123456789");

        when(fileManager.cargarArchivoCSV("huespedes.csv")).thenReturn(List.of(mockData));

        Map<String, Titular> resultado = hotelManagementLoaderData.cargarTitulares();

        Assertions.assertEquals(1, resultado.size());
        Assertions.assertTrue(resultado.containsKey("12345678"));

        Titular titular = resultado.get("12345678");
        Assertions.assertEquals("John Doe", titular.getNombre());
        Assertions.assertEquals(30, titular.getEdad());
        Assertions.assertEquals("johndoe@example.com", titular.getEmail());
        Assertions.assertEquals("123456789", titular.getTelefono());
    }

    @Test
    public void testCargarTitularesFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("huespedes.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarTitulares());
    }

    @Test
    public void testCargarTitularesDataError() throws Exception {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("dni", "12345678");
        mockData.put("nombre", "John Doe");
        mockData.put("edad", "no es un número");
        mockData.put("email", "johndoe@example.com");
        mockData.put("telefono", "123456789");

        when(fileManager.cargarArchivoCSV("huespedes.csv")).thenReturn(List.of(mockData));

        Assertions.assertThrows(NumberFormatException.class, () -> hotelManagementLoaderData.cargarTitulares());
    }
    @Test
    public void testCargarReservasHabitacionesSuccess() throws Exception {
        Map<String, String> mockData1 = new HashMap<>();
        mockData1.put("numero_reserva", "1");
        mockData1.put("numero_habitacion", "101");

        Map<String, String> mockData2 = new HashMap<>();
        mockData2.put("numero_reserva", "1");
        mockData2.put("numero_habitacion", "102");

        Map<String, String> mockData3 = new HashMap<>();
        mockData3.put("numero_reserva", "2");
        mockData3.put("numero_habitacion", "201");

        when(fileManager.cargarArchivoCSV("reservas_habitaciones.csv")).thenReturn(List.of(mockData1, mockData2, mockData3));

        Map<Long, List<Integer>> resultado = hotelManagementLoaderData.cargarReservasHabitaciones();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertTrue(resultado.containsKey(1L));
        Assertions.assertTrue(resultado.containsKey(2L));

        List<Integer> habitacionesReserva1 = resultado.get(1L);
        Assertions.assertEquals(2, habitacionesReserva1.size());
        Assertions.assertTrue(habitacionesReserva1.contains(101));
        Assertions.assertTrue(habitacionesReserva1.contains(102));

        List<Integer> habitacionesReserva2 = resultado.get(2L);
        Assertions.assertEquals(1, habitacionesReserva2.size());
        Assertions.assertTrue(habitacionesReserva2.contains(201));
    }

    @Test
    public void testCargarReservasHabitacionesFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("reservas_habitaciones.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarReservasHabitaciones());
    }
    @Test
    public void testCargarDisponibilidadesReservasSuccess() throws Exception {
        Map<String, String> mockData1 = new HashMap<>();
        mockData1.put("id_disponibilidad", "1");
        mockData1.put("id_reserva", "100");

        Map<String, String> mockData2 = new HashMap<>();
        mockData2.put("id_disponibilidad", "2");
        mockData2.put("id_reserva", "200");

        when(fileManager.cargarArchivoCSV("reservas_disponibilidades.csv")).thenReturn(List.of(mockData1, mockData2));

        Map<Long, Long> resultado = hotelManagementLoaderData.cargarDisponibilidadesReservas();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(100L, resultado.get(1L));
        Assertions.assertEquals(200L, resultado.get(2L));
    }

    @Test
    public void testCargarDisponibilidadesReservasFailure() throws Exception {
        when(fileManager.cargarArchivoCSV("reservas_disponibilidades.csv")).thenThrow(new Exception("Error al cargar el archivo"));

        Assertions.assertThrows(Exception.class, () -> hotelManagementLoaderData.cargarDisponibilidadesReservas());
    }
}





