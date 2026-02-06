package Model.Common;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonServises {
  public <T> Integer countAllElements(String filePath, Class<T> modelClass) {
    ObjectMapper mapper = new ObjectMapper();
    Integer count = 0;
    try {
      File file = new File(filePath);
      if (file.exists()) {
        // Leer todos los elementos desde el archivo JSON
        List<T> elements = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, modelClass));
        // Contar los elementos
        count = elements.size();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return count;
  }

  public <T> Integer getLastIndex(String filePath, Class<T> modelClass) {
    return this.countAllElements(filePath, modelClass) + 1; // Retorna el siguiente ID disponible
  }

  public <T> List<T> getAllElements(String filePath, Class<T> modelClass) {
    ObjectMapper mapper = new ObjectMapper();
    List<T> elements = null;
    try {
      File file = new File(filePath);
      if (file.exists()) {
        // Leer todos los elementos desde el archivo JSON
        elements = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, modelClass));
      } else {
        System.out.println("File not found: " + filePath);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return elements;
  }
}
