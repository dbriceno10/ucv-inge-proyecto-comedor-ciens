package Examples.Config;

import Model.Config.ConfigService;
import Model.DTO.Config.ConfigDto;

public class Main {
  public static void main(String[] args) {
    ConfigService configService = new ConfigService();

    System.out.println("Starting config tests...");

    // Test 1: Obtener configuración
    System.out.println("Test 1: Get configuration");
    try {
      ConfigDto config = configService.getConfig();
      if (config != null) {
        System.out.println("Config found: CF Value: " + config.getValueCF());
      } else {
        System.out.println("No config found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get config: " + e.getMessage());
    }

    // Test 2: Crear configuración
    System.out.println("Test 2: Create configuration");
    try {
      ConfigDto newConfig = new ConfigDto(
          1500.0,
          80.0,
          20.0,
          100.0,
          "2026-02-22T00:00:00Z");
      ConfigDto createdConfig = configService.createConfig(newConfig);
      System.out.println("Config created: CF Value: " + createdConfig.getValueCF());
    } catch (Exception e) {
      System.out.println("Failed to create config: " + e.getMessage());
    }

    // Test 3: Actualizar configuración
    System.out.println("Test 3: Update configuration");
    try {
      ConfigDto updatedConfig = new ConfigDto(
          2000.0,
          75.0,
          25.0,
          100.0,
          "2026-02-22T12:00:00Z");
      ConfigDto result = configService.updateConfig(updatedConfig);
      System.out.println("Config updated: CF Value: " + result.getValueCF());
    } catch (Exception e) {
      System.out.println("Failed to update config: " + e.getMessage());
    }
  }
}