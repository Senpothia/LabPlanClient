package com.michel.lab.utils;

import java.io.File;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class UploadImage {
	
	public static String guardarArchivo(MultipartFile multiPart, String path) {
		// Obtenemos el nombre original del archivo.
		String nomOriginal = multiPart.getOriginalFilename();
		try {
		// Formamos el nombre del archivo para guardarlo en el disco duro.
		File imageFile = new File(path+ nomOriginal);
		System.out.println("Archivo: " + imageFile.getAbsolutePath());
		//Guardamos fisicamente el archivo en HD.
		multiPart.transferTo(imageFile);
		return nomOriginal;
		} catch (IOException e) {
		System.out.println("Error " + e.getMessage());
		return null;
		}
		}

}
