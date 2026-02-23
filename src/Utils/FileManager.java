package Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

  public boolean simulateRecognition(Path savedImagePath, Path uploadedImagePath) {
    try {
      // Compara los archivos byte por byte.
      // -1 significa que son exactamente iguales.
      long mismatchIndex = Files.mismatch(savedImagePath, uploadedImagePath);

      if (mismatchIndex == -1L) {
        System.out.println("¡Simulación exitosa! Es la misma persona.");
        return true;
      } else {
        System.out.println("Simulación fallida: Los archivos son distintos.");
        return false;
      }

    } catch (IOException e) {
      System.err.println("Error al leer los archivos: " + e.getMessage());
      return false;
    }
  }

  public String uploadFile(byte[] fileData, String fileName, String folderName) {
    String directoryPath = folderName;
    if (folderName == null || folderName.isEmpty()) {
      directoryPath = "assets/filemanager";
    }
    File directory = new File(directoryPath);

    // Create directory if it doesn't exist
    if (!directory.exists()) {
      directory.mkdirs();
    }

    String filePath = directoryPath + "/" + fileName;
    try (FileOutputStream fos = new FileOutputStream(filePath)) {
      fos.write(fileData);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to upload file: " + e.getMessage());
    }

    return filePath;
  }
}