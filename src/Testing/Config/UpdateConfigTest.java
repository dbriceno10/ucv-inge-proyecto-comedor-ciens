package Testing.Config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Model.Config.ConfigService;
import Model.DTO.Config.ConfigDto;

public class UpdateConfigTest {
  private static final String TEST_CONFIG_FILE = "src/Model/TestDatabase/Config/config.json";
  private ConfigService configService = new ConfigService(TEST_CONFIG_FILE);

  @Test
  public void testUpdateConfigInvalidCF() {
    ConfigDto invalidDto = new ConfigDto(-500.0, 75.0, 25.0, 100.0, 10.0, null);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      configService.updateConfig(invalidDto);
    });
    assertEquals("El valor de CF debe ser un número positivo", exception.getMessage());
  }

  @Test
  public void testUpdateConfigInvalidTeacherPercentage() {
    // Porcentaje de profesor fuera del rango (70-90)
    ConfigDto invalidDto = new ConfigDto(10000.0, 95.0, 25.0, 100.0, 10.0, null);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      configService.updateConfig(invalidDto);
    });
    assertEquals("El porcentaje para profesores debe estar entre 70 y 90", exception.getMessage());
  }

  @Test
  public void testUpdateConfigInvalidStudentPercentage() {
    // Porcentaje de estudiante fuera del rango (20-30)
    ConfigDto invalidDto = new ConfigDto(10000.0, 75.0, 15.0, 100.0, 10.0, null);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      configService.updateConfig(invalidDto);
    });
    assertEquals("El porcentaje para estudiantes debe estar entre 20 y 30", exception.getMessage());
  }

  @Test
  public void testUpdateConfigInvalidWorkerPercentage() {
    // Porcentaje de trabajador fuera del rango (90-110)
    ConfigDto invalidDto = new ConfigDto(10000.0, 75.0, 25.0, 120.0, 10.0, null);

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      configService.updateConfig(invalidDto);
    });
    assertEquals("El porcentaje para trabajadores debe estar entre 90 y 110", exception.getMessage());
  }

  @Test
  public void testUpdateConfigSuccessAndRestore() {

    // 1. Actualizar con datos válidos nuevos
    ConfigDto newValidDto = new ConfigDto(15000.0, 80.0, 25.0, 100.0, 10.0, null);
    ConfigDto updatedConfig = configService.updateConfig(newValidDto);

    assertNotNull(updatedConfig, "La configuración actualizada no debería ser nula");
    assertEquals(15000.0, updatedConfig.getValueCF(), "El valor CF debería haberse actualizado");
    assertEquals(80.0, updatedConfig.getTeacherPercentage(), "El porcentaje de profesor debería haberse actualizado");
    assertEquals(25.0, updatedConfig.getStudentPercentage(), "El porcentaje de estudiante debería haberse actualizado");
    assertEquals(100.0, updatedConfig.getWorkerPercentage(), "El porcentaje de trabajador debería haberse actualizado");

    // 2. Restaurar al estado original solicitado
    ConfigDto originalDto = new ConfigDto(10000.0, 70.0, 20.0, 90.0, 5.0, null);
    ConfigDto restoredConfig = configService.updateConfig(originalDto);

    assertNotNull(restoredConfig, "La configuración restaurada no debería ser nula");
    assertEquals(10000.0, restoredConfig.getValueCF(), "El valor CF debería haberse restaurado");
    assertEquals(70.0, restoredConfig.getTeacherPercentage(), "El porcentaje de profesor debería haberse restaurado");
    assertEquals(20.0, restoredConfig.getStudentPercentage(), "El porcentaje de estudiante debería haberse restaurado");
    assertEquals(90.0, restoredConfig.getWorkerPercentage(), "El porcentaje de trabajador debería haberse restaurado");
    assertEquals(5.0, restoredConfig.getScholarPercentage(), "El porcentaje de becarios debería haberse restaurado");
  }
}
