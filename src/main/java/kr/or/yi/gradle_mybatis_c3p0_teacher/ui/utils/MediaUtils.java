package kr.or.yi.gradle_mybatis_c3p0_teacher.ui.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.imgscalr.Scalr;

public class MediaUtils {
	
	private static Map<String, String> mediaMap;
	
	static {
		mediaMap = new HashMap<String, String>();
		mediaMap.put("JPG", "jpeg");
		mediaMap.put("GIF", "gif");
		mediaMap.put("PNG", "png");
	}
	
	public static String getMediaType(String type) {
		return mediaMap.get(type.toUpperCase());
	}
	
	public static boolean checkImageType(String fileName) {
		return fileName.contains("jpg") || fileName.contains("gif") || fileName.contains("png") || fileName.contains("jpeg") ;
	}
	
	public static ImageIcon createImageIcon(File file) throws IOException {
		BufferedImage sourceImg = ImageIO.read(file);
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC,  Scalr.Mode.FIT_TO_HEIGHT, 30);
		ImageIcon icon = new ImageIcon(destImg);
		return icon;
	}

	public static void createThumbnale(File src, File chumbnaleFile,  String formatName) throws IOException {
		BufferedImage sourceImg = ImageIO.read(src);
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC,  Scalr.Mode.FIT_TO_HEIGHT, 30);
		ImageIO.write(destImg, formatName, chumbnaleFile);
	}
}
