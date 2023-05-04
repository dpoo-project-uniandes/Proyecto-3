package services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileManager {
	
	private static final String path = "./data/";
	
	private static String buildPath(String fileName) {
		return String.format("%s%s", path, fileName);
	}
	
	public static File cargarArchivo(String name) {
		try {
			return new File(buildPath(name));
		} catch (Exception e) {
			System.out.println("Fallo al cargar archivo " + buildPath(name));
			throw e;
		}
	}
	
	public static void eliminarArchivo(String name) {
		try {
			new File(buildPath(name)).delete();
		} catch (Exception e) {
			System.out.println("Fallo al eliminar el archivo " + buildPath(name));
			throw e;
		}
	}
	
	public static List<Map<String, String>> cargarArchivoCSV(String name) throws Exception {
		try {
			List<Map<String, String>> data = new ArrayList<>();
			File file = cargarArchivo(name);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String[] headers = br.readLine().split(",");
			String linea = br.readLine();
			while (linea != null) {
				String[] row = linea.split(",");
				Map<String, String> rowMap = new HashMap<>();
				for(int i = 0; i < headers.length; i++) {
					rowMap.put(headers[i],row[i]);
				}
				data.add(rowMap);
				linea = br.readLine();
			}
			br.close();
			return data;
		} catch (Exception e) {
			System.out.println("Fallo el cargado del archivo csv " + name);
			throw e;
		}
	}
	
	public static void agregarLineasCSV(String name, List<List<String>> filas) throws Exception {
		try {
			File file = cargarArchivo(name);
			BufferedWriter br = new BufferedWriter(new FileWriter(file, true));
			for(List<String> fila : filas) {
				String row = fila.stream().collect(Collectors.joining(","));
				br.append(row += "\n");
			}
			br.close();
		} catch(Exception e) {
			System.out.println("Fallo el agregado de lineas al archivo csv " + name);
			throw e;
		}
	}
}
