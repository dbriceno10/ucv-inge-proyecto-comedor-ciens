package Testing.FileManager;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;

import Utils.FileManager;

import java.io.File;

public class Main {
  public static void main(String[] args) {
    FileManager fileManager = new FileManager();

    // // Simulación de rutas de imágenes
    // String savedImagePath = "assets/images/UCVUsers/avatar1.jpg";
    // String uploadedImagePath = "assets/filemanager/example.jpg"; // Simula que el usuario subió la misma imagen

    // // Simulación de reconocimiento facial
    // boolean isMatch = fileManager.simulateRecognition(Path.of(savedImagePath), Path.of(uploadedImagePath));

    // if (isMatch) {
    //   System.out.println("Facial recognition successful: The images match.");
    // } else {
    //   System.out.println("Facial recognition failed: The images do not match.");
    // }

    // // Demostración de uso de uploadFile
    // try {
    //   // Load a sample image file as bytes
    //   String imagePath = "assets/images/UCVUsers/avatar2.jpg"; // Replace with an actual image path
    //   byte[] imageData = Files.readAllBytes(Paths.get(imagePath));

    //   // Call the uploadFile method
    //   String uploadedFilePath = fileManager.uploadFile(imageData, "uploaded_sample.jpg", null);

    //   // Print the relative path of the uploaded file
    //   System.out.println("File uploaded to: " + uploadedFilePath);
    // } catch (Exception e) {
    //   e.printStackTrace();
    // }

    // // Demostración de uso de uploadFile con JFileChooser
    // try {
    //   // Open a file chooser dialog to select an image
    //   JFileChooser fileChooser = new JFileChooser();
    //   fileChooser.setDialogTitle("Select an Image");
    //   int userSelection = fileChooser.showOpenDialog(null);

    //   if (userSelection == JFileChooser.APPROVE_OPTION) {
    //     File selectedFile = fileChooser.getSelectedFile();
    //     byte[] imageData = Files.readAllBytes(selectedFile.toPath());

    //     // Call the uploadFile method
    //     String uploadedFilePath = fileManager.uploadFile(imageData, selectedFile.getName(), "assets/filemanager/test");

    //     // Print the relative path of the uploaded file
    //     System.out.println("File uploaded to: " + uploadedFilePath);
    //   } else {
    //     System.out.println("No file selected.");
    //   }
    // } catch (Exception e) {
    //   e.printStackTrace();
    // }

    // Test the pickupFile method with custom file name and folder
    try {

      String uploadedFilePath = fileManager.pickupFile();

      // Print the relative path of the uploaded file
      System.out.println("File uploaded to: " + uploadedFilePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
