package Testing.Menu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import Model.DTO.Menu.CreateMenuDto;
import Model.DTO.Menu.MenuDto;
import Model.DTO.Menu.MenuQtyDto;
import Model.Menu.MenuService;
import Enums.*;

public class CreateMenuTest {

  private static final String FILE_PATH = "src/Model/TestDatabase/Menu/menus.json";
  private static final String INGREDIENT_FILE_PATH = "src/Model/TestDatabase/Ingredient/ingredients.json";
  private static final String FOOD_FILE_PATH = "src/Model/TestDatabase/Food/foods.json";

  private MenuService menuService = new MenuService(FILE_PATH, INGREDIENT_FILE_PATH, FOOD_FILE_PATH);

  @Test
  public void testCreateMenuSuccess() {

    // Usamos IDs de comida que existen en foods.json (ej. 1, 2, 3)
    ArrayList<Integer> foodIds = new ArrayList<>(Arrays.asList(1, 2, 3));
    ArrayList<MenuQtyDto> qtys = new ArrayList<>(Arrays.asList(
        new MenuQtyDto(1, 50, 50),
        new MenuQtyDto(2, 50, 50),
        new MenuQtyDto(3, 50, 50)
    ));

    CreateMenuDto createDto = new CreateMenuDto(
        Days.MONDAY,
        MenuTypes.LUNCH,
        foodIds,
        qtys,
        "2026-03-01");

    // Crear el menú
    MenuDto createdMenu = menuService.create(createDto);

    // Verificaciones
    assertNotNull(createdMenu, "El menú creado no debería ser nulo");
    assertEquals(Days.MONDAY, createdMenu.getDay(), "El día debería ser LUNES");
    assertEquals(MenuTypes.LUNCH, createdMenu.getType(), "El tipo debería ser ALMUERZO");
    assertEquals(3, createdMenu.getFoods().size(), "Debería tener 3 platos");
    assertEquals("2026-03-01", createdMenu.getDate(), "La fecha debería coincidir");
    assertEquals(3, createdMenu.getQtys().size(), "La cantidad de items en qtys debería ser 3");
    assertEquals(50, createdMenu.getQtys().get(0).getQty(), "La cantidad debería ser 50");

    // Limpiar la base de datos (borrado lógico)
    boolean isDeleted = menuService.delete(createdMenu.getId());
    assertTrue(isDeleted, "El menú debería haberse eliminado correctamente");
  }

  @Test
  public void testCreateMenuNoFoods() {

    // Lista de comidas vacía
    ArrayList<Integer> emptyFoodIds = new ArrayList<>();
    ArrayList<MenuQtyDto> emptyQtys = new ArrayList<>();

    CreateMenuDto createDto = new CreateMenuDto(
        Days.TUESDAY,
        MenuTypes.BREACKFAST,
        emptyFoodIds,
        emptyQtys,
        "2026-03-02");

    // Verificar que se lanza la excepción esperada
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      menuService.create(createDto);
    });

    assertEquals("Menu must have between 1 and 3 food items", exception.getMessage());
  }

  @Test
  public void testCreateMenuTooManyFoods() {

    // Lista con más de 3 comidas (usando IDs existentes en foods.json)
    ArrayList<Integer> tooManyFoodIds = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
    ArrayList<MenuQtyDto> qtys = new ArrayList<>(Arrays.asList(
        new MenuQtyDto(1, 40, 40),
        new MenuQtyDto(2, 40, 40),
        new MenuQtyDto(3, 40, 40),
        new MenuQtyDto(4, 40, 40)
    ));

    CreateMenuDto createDto = new CreateMenuDto(
        Days.WEDNESDAY,
        MenuTypes.BREACKFAST,
        tooManyFoodIds,
        qtys,
        "2026-03-03");

    // Verificar que se lanza la excepción esperada
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      menuService.create(createDto);
    });

    assertEquals("Menu must have between 1 and 3 food items", exception.getMessage());
  }
}
