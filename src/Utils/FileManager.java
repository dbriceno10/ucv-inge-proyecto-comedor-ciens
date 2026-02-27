package Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;

public class FileManager {

  public boolean simulateRecognition(String routeSaved, String routeUploaded) {
    Path savedImagePath = Path.of(routeSaved);
    Path uploadedImagePath = Path.of(routeUploaded);

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

  public String uploadFile(byte[] fileData, String fileName) {
    return this.uploadFile(fileData, fileName, null);
  }

  public String pickupFile(String folderName) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Select a File");
    int userSelection = fileChooser.showOpenDialog(null);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      try {
        byte[] fileData = Files.readAllBytes(selectedFile.toPath());
        return this.uploadFile(fileData, selectedFile.getName(), folderName);
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to process the selected file: " + e.getMessage());
      }
    } else {
      throw new RuntimeException("No file was selected.");
    }
  }

  public String pickupFile() {
    return this.pickupFile(null);
  }
}