package hotel_system.models;

import java.util.List;

public class Restaurante extends Servicio {

	public Restaurante(Long id) {
		super(id);
	}

	public Restaurante(Long id, List<Consumible> productos) {
		super(id, productos);
	}
}
