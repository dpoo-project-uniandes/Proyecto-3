package hotel_system.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private final static String PATH = System.getProperty("user.dir")+ "/data/";
	public static void guardarArchivo(String dir, String content) throws IOException {
		File archivo = new File(PATH+dir+".txt");
		if (!archivo.exists())
			archivo.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
		bw.write(content);
		bw.newLine();
		bw.close();
		
	}
	public static BufferedReader obtenerArchivo(String dir) throws IOException {
		File archivo = new File(PATH+dir+".txt");
		if (!archivo.exists()) archivo.createNewFile();
		return new BufferedReader(new FileReader (archivo));
	}
}
