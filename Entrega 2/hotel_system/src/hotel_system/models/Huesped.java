package hotel_system.models;

public class Huesped {
	
	private String nombre;
	private String dni;
	private Integer edad;
	
	public Huesped(String nombre, String dni, Integer edad) {
		super();
		this.nombre = nombre;
		this.dni = dni;
		this.edad = edad;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}
}
