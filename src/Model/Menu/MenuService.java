package Model.Menu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Common.CommonServices;
import Utils.Dates;
import DTO.Menu.CreateMenuDto;
import DTO.Menu.MenuDto;
import Model.Food.FoodService;
import DTO.Food.FoodDto;
import DTO.Menu.UpdateMenuDto;

public class MenuService {
  private static final String FILE_PATH = "src/Database/Menu/menus.json";
  private CommonServices commonServices = new CommonServices();
  private Dates datesUtil = new Dates();
  private FoodService foodService = new FoodService();

  public List<MenuDto> getAllMenus() {
    List<MenuDto> menus = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        List<MenuModel> menuModels = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, MenuModel.class));
        for (MenuModel menuModel : menuModels) {
          if (menuModel.getDeletedAt() == null) { // Filtrar menús con deletedAt como null
            menus.add(this.mapToDto(menuModel));
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return menus;
  }

  public MenuDto getMenuById(Integer id) {
    MenuModel menuModel = this.getById(id);
    if (menuModel == null) {
      return null;
    }
    return this.mapToDto(menuModel);
  }

   public MenuDto getMenuOfDay() {
    MenuModel menuModel = this.getByDay(datesUtil.getDayOfWeek(datesUtil.getCurrentDateTime()));
    if (menuModel == null) {
      return null;
    }
    return this.mapToDto(menuModel);
  }

  public MenuDto create(CreateMenuDto menuDto) {
    if (menuDto.getDay() == null || menuDto.getDay().isEmpty()) {
      throw new IllegalArgumentException("Menu day cannot be null or empty");
    }
    if (menuDto.getFoodIds() == null || menuDto.getFoodIds().length == 0) {
      throw new IllegalArgumentException("Menu must have at least one food item");
    }
    if (menuDto.getDate() == null || menuDto.getDate().isEmpty()) {
      throw new IllegalArgumentException("Menu date cannot be null or empty");
    }
    Integer nextId = this.commonServices.getLastIndex(FILE_PATH, MenuModel.class);
    String date = this.datesUtil.getCurrentDateTime();
    MenuModel newMenu = new MenuModel(menuDto, nextId, date);
    MenuModel menu = this.save(newMenu);
    if (menu == null) {
      return null;
    }
    return this.mapToDto(menu);
  }

  public MenuDto update(UpdateMenuDto menuDto) {
    if (menuDto.getDay() == null || menuDto.getDay().isEmpty()) {
      throw new IllegalArgumentException("Menu day cannot be null or empty");
    }
    if (menuDto.getFoodIds() == null || menuDto.getFoodIds().length == 0) {
      throw new IllegalArgumentException("Menu must have at least one food item");
    }
    if (menuDto.getDate() == null || menuDto.getDate().isEmpty()) {
      throw new IllegalArgumentException("Menu date cannot be null or empty");
    }
    MenuModel existing = this.getById(menuDto.getId());
    if (existing == null) {
      throw new IllegalArgumentException("Menu not found with id: " + menuDto.getId());
    }
    MenuModel updatedMenu = new MenuModel(
        existing.getId(),
        menuDto.getDay(),
        menuDto.getFoodIds(),
        menuDto.getDate(),
        existing.getIsActive(), // Mantener el estado actual
        existing.getCreatedAt(), // Mantener la fecha de creación
        this.datesUtil.getCurrentDateTime(), // Actualizar la fecha de actualización
        existing.getDeletedAt() // Mantener la fecha de eliminación
    );
    MenuModel menu = this.edit(updatedMenu);
    if (menu == null) {
      return null;
    }
    return this.mapToDto(menu);
  }

  public boolean delete(Integer id) {
    MenuModel existing = this.getById(id);
    if (existing != null) {
      existing.setDeletedAt(this.datesUtil.getCurrentDateTime());
      this.edit(existing);
      return true;
    }
    return false;
  }

  // metodos privados
  private MenuDto mapToDto(MenuModel menuModel) {
    ArrayList<FoodDto> foodDtos = new ArrayList<>();
    for (Integer foodId : menuModel.getFoodIds()) {
      FoodDto foodDto = this.foodService.getFoodById(foodId);
      if (foodDto != null) {
        foodDtos.add(foodDto);
      }
    }
    return new MenuDto(
        menuModel.getId(),
        menuModel.getDay(),
        foodDtos,
        menuModel.getDate(),
        menuModel.getIsActive(),
        menuModel.getCreatedAt(),
        menuModel.getUpdatedAt());
  }

  private List<MenuModel> getAll() {
    List<MenuModel> menus = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        List<MenuModel> menuModels = mapper.readValue(file,
            mapper.getTypeFactory().constructCollectionType(List.class, MenuModel.class));
        for (MenuModel menuModel : menuModels) {
          if (menuModel.getDeletedAt() == null) { // Filtrar menús con deletedAt como null
            menus.add(menuModel);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return menus;
  }

  private MenuModel getById(Integer id) {
    List<MenuModel> menus = this.getAll();
    MenuModel found = null;
    for (MenuModel menu : menus) {
      if (menu.getId().equals(id)) {
        found = menu;
        break;
      }
    }
    return found;
  }

    private MenuModel getByDay(String day) {
    List<MenuModel> menus = this.getAll();
    MenuModel found = null;
    for (MenuModel menu : menus) {
      if (menu.getDay().equals(day)) {
        found = menu;
        break;
      }
    }
    return found;
  }


  private MenuModel save(MenuModel menu) {
    List<MenuModel> menus = this.commonServices.getAllElements(FILE_PATH, MenuModel.class);
    menus.add(menu);
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), menus);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return menu;
  }

  private MenuModel edit(MenuModel menu) {
    List<MenuModel> menus = this.commonServices.getAllElements(FILE_PATH, MenuModel.class);
    boolean found = false;
    for (int i = 0; i < menus.size(); i++) {
      if (menus.get(i).getId().equals(menu.getId())) {
        menus.set(i, menu);
        found = true;
        break;
      }
    }
    if (!found) {
      throw new IllegalArgumentException("Menu not found with id: " + menu.getId());
    }
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), menus);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return menu;
  }
}
