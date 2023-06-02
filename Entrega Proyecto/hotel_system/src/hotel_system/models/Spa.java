package hotel_system.models;

import java.util.List;

import hotel_system.utils.Utils;

public class Spa extends Servicio {
	
	public Spa(Long id) {
		super(id);
	}
	
	public Spa(Long id, List<Consumible> consumibles) {
		super(id, consumibles);
	}

}
