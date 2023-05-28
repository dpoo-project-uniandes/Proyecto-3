package hotel_system.interfaces.components;

import java.util.List;

public interface FormDataTable {
	
	public List<String> getHeaders();
	public List<List<Object>> getData();
	public List<Double> getWeights();

}
