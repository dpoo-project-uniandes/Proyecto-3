package hotel_system.controllers;

import java.util.List;
import java.util.Map;

import hotel_system.models.Rol;
import hotel_system.models.Usuario;
import services.FileManager;

public class HotelManagementUsuarios {
	
	private Map<String,Usuario> usuarios;
	
	public HotelManagementUsuarios(Map<String,Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public boolean userExists(String user) {
		return usuarios.containsKey(user);
	}

	public Usuario userLogin(String user, String password) {
		Usuario usuario = usuarios.get(user);
		if (usuario != null && usuario.getPassword().equals(password)) {
			return usuario;
		}
		return null;
	}
	
	public void userSignUp(String user, String password, Rol rol) throws Exception {
		usuarios.put(user, new Usuario(user, password, rol));
		List<List<String>> rowUser = List.of(List.of(user, password, rol.toString()));
		FileManager.agregarLineasCSV("usuarios.csv", rowUser);
	}
}
