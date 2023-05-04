package hotel_system.models;

public class Titular extends Huesped {
	
	private String email;
	private String telefono;
	
	
	public Titular(String nombre, String dni, Integer edad, String email, String telefono) {
		super(nombre, dni, edad);
		this.email = email;
		this.telefono = telefono;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@Override
	public String toString() {
		return String.format("nombre:%s,edad:%d,dni:%s,email:%s,telefono:%s", getNombre(), getEdad(), getDni(), getEmail(), getTelefono());
	}
}
