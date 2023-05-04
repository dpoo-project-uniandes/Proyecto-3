package hotel_system.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import hotel_system.controllers.HotelManagementSystem;
import hotel_system.models.Consumible;
import hotel_system.models.Producto;
import hotel_system.models.ProductoRestaurante;
import hotel_system.models.Rol;
import hotel_system.models.TipoHabitacion;
import services.SecValidation;

public class Consola {
	
	public static HotelManagementSystem hotelSystem = new HotelManagementSystem();
	
	public static void main(String[] args ) throws Exception {
		bienvenida();
		boolean escogiendo = true;
		while(escogiendo) {
			try {
				printMenuIngreso();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1) {ingresar();}
				else if (opcion_seleccionada == 2) {registrar();}
				else if (opcion_seleccionada == 3) {escogiendo = false;}
				else {System.out.println("Recuerda que es un número entre 1 y 3");}
			}catch (NumberFormatException e) {
				System.out.println("Por favor digite un Número Válido entre 1 y 3");
			}
		}
		System.out.println("Gracias por usar nuestro sistema");
		
	}
	
	private static void registrar() throws Exception {
		boolean registrando = true;
		while(registrando) {
			try {
				System.out.println("El nombre de usuario debe:\n-Debe empezar con una letra");
				System.out.println("-Ser de entre 4 a 12 caracteres");
				System.out.println("-Puede incluir guiones ");
				String user = input("Por favor ingrese el nombre de usuario");
				if (SecValidation.checkUser(user)) {
					boolean obtenerContra = true;
					while (obtenerContra) {
						System.out.println("La contraseña debe:");
						System.out.println("-Ser de entre 6 a 14 caracteres");
						System.out.println("-Debe incluir al menos una letra, un número y un caracter especial (*,#,+,-,& etc.) ");
						String password = input("Por favor ingrese la contraseña");
						if (SecValidation.checkPassword(password)) {
							boolean obtenerRol = true;
							while (obtenerRol) {
								System.out.println("Roles:\n1)Administrador\n2)Recepcionista");
								String rolEscogido = input("Ingresa un número entre 1 y 2");
								if (rolEscogido.equals("1")) {hotelSystem.registrarUsuario(user, password, Rol.ADMIN);
								obtenerContra = false;registrando = false; obtenerRol=false;break;}
								else if (rolEscogido.equals("2")) {hotelSystem.registrarUsuario(user, password, Rol.RECEPCIONISTA);
								obtenerContra = false;registrando = false; obtenerRol=false;break;}
								else {System.out.println("Recuerda que es un número entre 1 y 2");
								int opcion_seleccionada = Integer.parseInt(input("¿Deseas intentarlo de nuevo?\n1)Si\n2)No"));
								if (opcion_seleccionada == 1) {continue;}
								else if (opcion_seleccionada == 2) {obtenerContra = false;registrando = false; obtenerRol=false;}}
							}
						}
						else {System.out.println("La contraseña no es válida");
							System.out.println("La contraseña debe:");
							System.out.println("Ser de entre 6 a 14 caracteres");
							System.out.println("Debe incluir al menos una letra, un número y un caracter especial (*,#,+,-,& etc.) ");
							int opcion_seleccionada = Integer.parseInt(input("¿Deseas intentarlo de nuevo?\n1)Si\n2)No"));
							if (opcion_seleccionada == 1) {continue;}
							else if (opcion_seleccionada == 2) {obtenerContra = false;registrando = false;}
					}
					}
					}
				
				else {System.out.println("El usuario "+user+" no es un usuario válido en el sistema.");
					System.out.println("Debe empezar con una letra");
					int opcion_seleccionada = Integer.parseInt(input("¿Deseas intentarlo de nuevo?\n1)Si\n2)No"));
					if (opcion_seleccionada == 1) {continue;}
					else if (opcion_seleccionada == 2) {registrando = false;}
				}
			}catch (NumberFormatException e) {
				System.out.println("Por favor digite un Número Válido entre 1 y 2");
			}
		}
		
	}

	public static void ingresar() throws Exception {
		boolean ingresando = true;
		while(ingresando) {
			try {
				String user = input("Por favor ingrese su nombre de usuario");
				if (hotelSystem.validadUsuario(user)) {
					boolean accediendo = true;
					while (accediendo) {
						String password = input("Por favor ingrese su contraseña");
						String rol = hotelSystem.validarContrasenia(user, password);
						if (rol != null) {
							if (rol.equals("ADMIN")) {admin();}
							else if (rol.equals("RECEPCIONISTA")) {recepcionista();}
						}
						else {System.out.println("La contraseña no coincide con el usuario "+user+".");
							int opcion_seleccionada = Integer.parseInt(input("¿Deseas intentarlo de nuevo?\n1)Si\n2)No"));
							if (opcion_seleccionada == 1) {continue;}
							else if (opcion_seleccionada == 2) {accediendo = false;}
					}
					}
					}
				
				else {System.out.println("El usuario "+user+" no es un usuario válido en el sistema.");
					int opcion_seleccionada = Integer.parseInt(input("¿Deseas intentarlo de nuevo?\n1)Si\n2)No"));
					if (opcion_seleccionada == 1) {continue;}
					else if (opcion_seleccionada == 2) {ingresando = false;}
				}
			}catch (NumberFormatException e) {
				System.out.println("Por favor digite un Número Válido entre 1 y 2");
			}
		}
		
	}

	private static void recepcionista() throws Exception {
		boolean recepcionista = true;
		while (recepcionista) {
			try {
				printMenuRecepcionista();
				switch (Integer.parseInt(input("Selecciona una opción:"))) {
				case 1:
					reservar();
					break;
				case 2:
					cancelarReserva();
					break;
				case 3:
					iniciarEstadia();
					break;
				case 4:
					finalizarEstadia();
					break;
				case 5:
					registrarConsumible();
					break;
				case 6:
					break;
				case 7:
					break;
				case 8:
					recepcionista =false;
					break;
				default:
					System.out.println("Es un número entre 1 y 8");
					break;
				}}
			catch (NumberFormatException e) {
				System.out.println("Por favor digite un Número Válido");
			}
			
		}
		
	}

	private static void iniciarEstadia() {
		try {
			String nombre;
			String dniHuesped;
			String edad;
			String dni = input("Ingrese el dni del titular:");
			Integer cantidad = hotelSystem.cantidadReserva(dni);
			List<List<String>> acompañantes= new ArrayList<>();
			for  (Integer count = 1 ; count<cantidad; count++) {
				nombre = input("Ingrese el nombre de su acompañante");
				dniHuesped = input("Ingrese el DNI de su acompañante");
				edad = input("Ingrese la edad de su acompañante");
				acompañantes.add(List.of(nombre, dniHuesped, edad));
			}
			List<Integer> habitaciones = hotelSystem.iniciarEstadia(dni, acompañantes);
			System.out.println("Se inició la estadía con exito");
			System.out.println("Sus habitaciones son: " + habitaciones.toString());
		}catch (Exception e) {
			System.out.println("No se encontró la reserva, por favor, inténtelo de nuevo");
		}
		
	}

	private static void cancelarReserva() {
		try {
			String dni = input("Ingrese el dni del titular:");
			hotelSystem.cancelarReserva(dni);
			System.out.println("Se canceló la reserva con exito");
		}catch (Exception e) {
			System.out.println("No se encontró la reserva, por favor, inténtelo de nuevo");
		}
		
	}
	private static void finalizarEstadia() {
		try {
			String dni = input("Ingrese el dni del titular:");
			hotelSystem.finalizarEstadia(dni);
			System.out.println("Se finalizo la estadia con exito");
		}catch (Exception e) {
			System.out.println("No se encontró la estadia, por favor, inténtelo de nuevo");
		}
		
	}

	private static void registrarConsumible() {
		Boolean escogiendo = true;
		while (escogiendo) {
			printConsumos();
			try {
				switch (Integer.parseInt(input("Selecciona una opción:"))){
					case 1:
						productoSpa();
						break;
					case 2:
						productoRestaurante();
						break;
					case 3:
						producto();
						break;
					case 4:
						escogiendo = false;
						break;
					default:
						System.out.println("Por favor, ingrese un número de 1 a 4");
						break;
						
				}
				
			}
			catch (NumberFormatException e) {
				System.out.println("Por favor, ingrese un Numero Válido");
			}
		}
		
	}
	
	private static void producto() {
		Boolean consumiendo = true;
		List<Consumible> consumos = new ArrayList<>();
		List<Producto> productos = hotelSystem.getInventarioProductos();
		while (consumiendo) {
			Integer count = 0;
			for(Producto producto: productos){
				System.out.println(count+1+") "+producto.getNombre() +" ; precio -> "+producto.getPrecio());
				count++;
			}
			Integer seleccion = Integer.parseInt(input("Selecciona el producto, digite un número entre 1 y "+count));
			if (seleccion <= count && seleccion >= 1) {
				consumos.add(productos.get(seleccion - 1));
			}
			else {
				System.out.println("Selcciona un numero entre 1 y " +count);
				continue;
			}
			if (input("¿Deseas escoger otro producto\n1)Si\n2)No?").equals("1")) 
				continue;
			else 
				consumiendo=false;	
		}
		Boolean pagar = (input("¿Deseas pagar ahora?\n1) Si\n2) No").equals("1")) ? true : false;
		String hab = input("Ingrese el número de habitacion, si tiene varias ingrese solo uno, el que desee");
		hotelSystem.seleccionarProducto(consumos, hab, pagar);
	}

	private static void productoRestaurante() {
		Boolean consumiendo = true;
		List<Producto> consumos = new ArrayList<>();
		List<ProductoRestaurante> productos = hotelSystem.getServicioRestaurante().getProductos();
		while (consumiendo) {
			Integer count = 0;
			for(Producto producto: productos){
				System.out.println(count+1+") "+producto.getNombre() +" ; precio -> "+producto.getPrecio());
				count++;
			}
			Integer seleccion = Integer.parseInt(input("Selecciona el producto, digite un número entre 1 y "+count));
			if (seleccion <= count && seleccion >= 1) {
				consumos.add(productos.get(seleccion - 1));
			}
			else {
				System.out.println("Selcciona un numero entre 1 y " +count);
				continue;
			}
			if (input("¿Deseas escoger otro producto\n1)Si\n2)No?").equals("1")) 
				continue;
			else 
				consumiendo=false;	
		}
		Boolean pagar = (input("¿Deseas pagar ahora?\n1) Si\n2) No").equals("1")) ? true : false;
		String hab = input("Ingrese el número de habitacion, si tiene varias ingrese solo uno, el que desee");
		hotelSystem.seleccionarProductoRestaurante(consumos, hab, pagar);
	}

	private static void productoSpa() {
		Boolean consumiendo = true;
		List<Producto> consumos = new ArrayList<>();
		List<Producto> productos = hotelSystem.getServicioSpa().getProductosYServicios();
		while (consumiendo) {
			Integer count = 0;
			for(Producto producto: productos){
				System.out.println(count+1+") "+producto.getNombre() +" ; precio -> "+producto.getPrecio());
				count++;
			}
			Integer seleccion = Integer.parseInt(input("Selecciona el producto, digite un número entre 1 y "+count));
			if (seleccion <= count && seleccion >= 1) {
				consumos.add(productos.get(seleccion - 1));
			}
			else {
				System.out.println("Selcciona un numero entre 1 y " +count);
				continue;
			}
			if (input("¿Deseas escoger otro producto\n1)Si\n2)No?").equals("1")) 
				continue;
			else 
				consumiendo=false;	
		}
		Boolean pagar = (input("¿Deseas pagar ahora?\n1) Si\n2) No").equals("1")) ? true : false;
		String hab = input("Ingrese el número de habitacion, si tiene varias ingrese solo uno, el que desee");
		hotelSystem.seleccionarProductoRestaurante(consumos, hab, pagar);
	}

	private static void printConsumos() {
		System.out.println("Selecciona entre las siguientes opciones:");
		System.out.println("1) Para un Servicio de Spa");
		System.out.println("2) Para un Servicio de Restaurante");
		System.out.println("3) Para un producto");
		System.out.println("4) Para salir");

	}

	private static void reservar() throws Exception {
		String nombre = input("Ingrese el nombre del titular");
		String email = input("Ingrese el email del titular");
		String dni = input("Ingrese el dni del titular");
		Integer edad = Integer.parseInt(input("Ingrese la edad del titular"));
		String telefono = input("Ingrese el teléfono del titular");
		
		Integer cantidad = Integer.parseInt(input("Ingrese la cantidad de personas"));
		System.out.println("Note que la disponibilidad de fechas empieza desde hoy");
		String fechaLlegada = fechaValida("Llegada");
		String fechaSalida = fechaValida("Salida");
		List<Integer> habitaciones = escogerHabitacion(cantidad, fechaLlegada, fechaSalida);
		hotelSystem.reservar(nombre, email, dni, telefono, edad, cantidad, habitaciones, fechaLlegada, fechaSalida);
	}
	
	public static List<Integer> escogerHabitacion(Integer cantidad, String fecha1, String fecha2) throws Exception{
		List<Integer> ids = new ArrayList<Integer>();

		Boolean escogiendo = true;
		
		while (escogiendo) {
			Integer idHabitacion = selectHabs(fecha1, fecha2);
			if (idHabitacion == 0) {
				throw new Exception("Lo sentimos, no hay disponibilidad para el tipo de habitacion seleccionado");
			}
			ids.add(idHabitacion);
			switch (Integer.parseInt(input("¿Quieres escoger otra habitación?\n1)Si\n2)No"))) {
			case 1:
				break;
			case 2:
				if (hotelSystem.calcularCapacidadTotal(ids)>= cantidad) {
					escogiendo= false;
				}
				else {
					System.out.println("Hay más personas que habitaciones, por favor escoge más habitaciones");
				}
				break;
			default:
				System.out.println("Es un numero entre 1 y 2");
				break;
			}
		}
		
		return ids;
	}
	
	private static Integer selectHabs(String desde, String hasta) {
		Integer count = 0;
		
		for (TipoHabitacion hab:hotelSystem.getOpcionesHabitacion()) {
			System.out.println((count+1)+") nombre:"+hab.getAlias()
			+"; capacidad:"+hab.getCapacidad() 
			+ (hab.getConCocina()? "; Con Cocina":"") 
			+ (hab.getConBalcon()? "; Con Balcón":"") 
			+ (hab.getConVista()? "; Con Vista":""));
			count++;
		}
		
		Integer seleccion = Integer.parseInt(input("Selecciona la habitación, digite un número entre 1 y "+count));
		if (seleccion <= count && seleccion>=1) {
			try {
				return hotelSystem.seleccionarHab(seleccion-1, desde, hasta);
			} catch(Exception e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			System.out.println("Selcciona un numero entre 1 y " +count);
			selectHabs(desde, hasta);
		}
		return seleccion;
		
	}

	private static String fechaValida(String message) {
		Boolean escogiendo = true;
		String fecha;
		while (escogiendo) {
			fecha = input("Ingrese la fecha de "+message);
			if (SecValidation.checkDate(fecha)) {return fecha;}
			else {System.out.println("Por favor ingrese una fecha válida");}
		}
		return null;
	}
	
	private static void admin() {
		printMenuAdministrador();
		
	}

	public static void printMenuIngreso() {
		System.out.println("1) Ingresar ");
		System.out.println("2) Registrar usuario");
		System.out.println("3) Salir");

	}
	public static void printMenuRecepcionista() {
		System.out.println("1) Realizar una reserva");
		System.out.println("2) Cancelar una reserva ");
		System.out.println("3) Iniciar una estadia ");
		System.out.println("4) Finalizar una estadia ");
		System.out.println("5) Registrar un consumible");
		System.out.println("6) Null");
		System.out.println("7) null ");
		System.out.println("8) Log Out");

	}
	public static void printMenuAdministrador() {
		System.out.println("1) Modificar habitacion ");
		System.out.println("2) Administrar XD ");
		System.out.println("3) Log Out");

	}
	public static void bienvenida() {
		System.out.println("Hola! Este es el Hotel Management System");
		
	}
	
	public static String input(String message) {
		try
		{
			System.out.println(message+"\n->");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
}
