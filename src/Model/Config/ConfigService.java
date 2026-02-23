package Model.Config;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Context.User.UserSession;
import Utils.Dates;
import DTO.Config.ConfigDto;
import Enums.UserRoles;

public class ConfigService {
  private static String configFilePath = "src/Database/Config/config.json";
  private Dates datesUtil = new Dates();

  public ConfigDto getConfig() {
    UserSession.getInstance().isAuthenticated(); // Verificar que el usuario esté
    // autenticado
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(configFilePath);
      if (file.exists()) {
        ConfigModel config = mapper.readValue(file, ConfigModel.class);
        return this.mapToDto(config);
      } else {
        // Crear el archivo JSON con el elemento predeterminado si no existe
        ConfigModel defaultConfig = new ConfigModel(1000.0, 70.0, 20.0, 90.0, "2026-01-01T00:00:00Z");
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, defaultConfig);
        return this.mapToDto(defaultConfig);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Error al leer o crear la configuración");
    }
  }

  public ConfigDto createConfig(ConfigDto configDto) {
    UserSession.getInstance().hasRole(UserRoles.ADMIN); // Verificar que el
    // usuario tenga rol ADMIN
    ConfigDto existingConfig = this.getConfig();
    if (existingConfig != null) {
      throw new RuntimeException("Ya existe una configuración. Use updateConfig para modificarla.");
    }
    this.validateConfigDto(configDto); // Validar el DTO antes de crear
    ObjectMapper mapper = new ObjectMapper();
    try {
      ConfigModel newConfig = new ConfigModel(
          configDto.getValueCF(),
          configDto.getTeacherPercentage(),
          configDto.getStudentPercentage(),
          configDto.getWorkerPercentage(),
          datesUtil.getCurrentDateTime());
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(configFilePath), newConfig);
      return this.mapToDto(newConfig);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Error al crear la configuración");
    }
  }

  public ConfigDto updateConfig(ConfigDto configDto) {
    UserSession.getInstance().hasRole(UserRoles.ADMIN);
    this.validateConfigDto(configDto); // Validar el DTO antes de actualizar
    ObjectMapper mapper = new ObjectMapper();
    try {
      ConfigModel updatedConfig = new ConfigModel(
          configDto.getValueCF(),
          configDto.getTeacherPercentage(),
          configDto.getStudentPercentage(),
          configDto.getWorkerPercentage(),
          datesUtil.getCurrentDateTime());
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(configFilePath), updatedConfig);
      return this.mapToDto(updatedConfig);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Error al actualizar la configuración");
    }
  }

  // metodos privados

  private ConfigDto mapToDto(ConfigModel model) {
    return new ConfigDto(
        model.getValueCF(),
        model.getTeacherPercentage(),
        model.getStudentPercentage(),
        model.getWorkerPercentage(),
        model.getUpdateAt());
  }

  private void validateConfigDto(ConfigDto configDto) {
    if (configDto.getValueCF() == null || configDto.getValueCF() <= 0) {
      throw new RuntimeException("El valor de CF debe ser un número positivo");
    }
    if (configDto.getTeacherPercentage() == null || configDto.getTeacherPercentage() < 70
        || configDto.getTeacherPercentage() > 90) {
      throw new RuntimeException("El porcentaje para profesores debe estar entre 70 y 90");
    }
    if (configDto.getStudentPercentage() == null || configDto.getStudentPercentage() < 20
        || configDto.getStudentPercentage() > 30) {
      throw new RuntimeException("El porcentaje para estudiantes debe estar entre 20 y 30");
    }
    if (configDto.getWorkerPercentage() == null || configDto.getWorkerPercentage() < 90
        || configDto.getWorkerPercentage() > 110) {
      throw new RuntimeException("El porcentaje para trabajadores debe estar entre 90 y 110");
    }
  }

}
