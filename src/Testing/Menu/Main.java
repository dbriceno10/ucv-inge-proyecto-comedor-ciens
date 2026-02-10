package Testing.Menu;

import DTO.Menu.CreateMenuDto;
import DTO.Menu.MenuDto;
import DTO.Menu.UpdateMenuDto;
import Model.Menu.MenuService;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    MenuService menuService = new MenuService();

    System.out.println("Starting menu tests...");

    // Test 1: Obtener todos los menús
    System.out.println("Test 1: Get all menus");
    try {
      List<MenuDto> menus = menuService.getAllMenus();
      if (menus != null && !menus.isEmpty()) {
        System.out.println("Total menus: " + menus.size());
        for (MenuDto menu : menus) {
          System.out.println("Menu: " + menu.getDay() + ", Foods: " + menu.getFoods().size());
        }
      } else {
        System.out.println("No menus found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get menus: " + e.getMessage());
    }

    // Test 2: Buscar un menú por ID
    System.out.println("Test 2: Get menu by ID");
    try {
      MenuDto menu = menuService.getMenuById(1);
      if (menu != null) {
        System.out.println("Menu found: " + menu.getDay() + ", Foods: " + menu.getFoods().size());
      } else {
        System.out.println("Menu not found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get menu by ID: " + e.getMessage());
    }

    // Test 3: Crear un nuevo menú
    System.out.println("Test 3: Create a new menu");
    try {
      CreateMenuDto newMenu = new CreateMenuDto(
          "LUNES",
          new Integer[] { 1, 2, 3 },
          "2026-02-08"
      );
      MenuDto createdMenu = menuService.create(newMenu);
      System.out.println("Menu created: " + createdMenu.getDay());
    } catch (Exception e) {
      System.out.println("Failed to create menu: " + e.getMessage());
    }

    // Test 4: Actualizar un menú
    System.out.println("Test 4: Update a menu");
    try {
      UpdateMenuDto updatedMenu = new UpdateMenuDto(
          1,
          "MARTES",
          new Integer[] { 4, 5, 6 },
          "2026-02-09"
      );
      MenuDto result = menuService.update(updatedMenu);
      System.out.println("Menu updated: " + result.getDay() + ", New Foods: " + result.getFoods().size());
    } catch (Exception e) {
      System.out.println("Failed to update menu: " + e.getMessage());
    }

    // Test 5: Eliminar un menú
    System.out.println("Test 5: Delete a menu");
    try {
      Boolean isDeleted = menuService.delete(1);
      if (isDeleted) {
        System.out.println("Menu deleted successfully.");
      } else {
        System.out.println("Failed to delete menu.");
      }
    } catch (Exception e) {
      System.out.println("Failed to delete menu: " + e.getMessage());
    }
  }
}