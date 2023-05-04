package hotel_system.models;

public class Usuario {
	
	private String login;
	private String password;
	private Rol rol;
	
	public Usuario(String login, String password, Rol rol) {
		this.login = login;
		this.password = password;
		this.rol = rol;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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
