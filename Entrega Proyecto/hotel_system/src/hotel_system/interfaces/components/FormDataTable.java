package hotel_system.interfaces.components;

import java.util.List;

public abstract class FormDataTable<T> {
	
	protected List<String> headers;
	protected List<Double> weights;
	protected List<List<Object>> data;
	
	protected FormDataTable(List<String> headers, List<Double> weights, List<List<Object>> data) {
		super();
		this.headers = headers;
		this.weights = weights;
		this.data = data;
	}

	public List<String> getHeaders() {
		return headers;
	};
	
	public List<List<Object>> getData() {
		return data;
	};
	
	public List<Double> getWeights() {
		return weights;
	};
	
	public void addData(T value) {
		
	};
}
