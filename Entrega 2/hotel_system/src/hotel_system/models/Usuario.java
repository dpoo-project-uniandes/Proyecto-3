package hotel_system.models;

public class Usuario {

	private String alias;
	private String password;
	private Rol rol;

	public Usuario(String login, String password, Rol rol) {
		this.alias = login;
		this.password = password;
		this.rol = rol;
	}
  
	public String getAlias() {
		return alias;
	}

	public void setAlias(String login) {
		this.alias = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
}
