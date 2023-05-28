package services;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImagesManager {

	public static ImageIcon ImageIcon(String name) {
		try {
			BufferedImage image = ImageIO.read(new File("./assets/" + name + ".png"));
			return new ImageIcon(image);
		} catch (Exception e) {
			System.out.println("Error cargando la imagen de " + name);
			e.printStackTrace();
			return null;
		}
	}

	public static ImageIcon resizeIcon(ImageIcon icon, Integer width, Integer height) {
		try {
			return new ImageIcon(
					icon
					.getImage()
					.getScaledInstance(width, height, Image.SCALE_DEFAULT)
			);
		} catch(Exception e) {
			System.out.println("Error redimensiando el icon");
			e.printStackTrace();
			return icon;
		}
	}
}
