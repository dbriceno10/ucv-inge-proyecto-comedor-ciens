package Model.Menu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Common.CommonServices;
import Model.DTO.Food.FoodDto;
import Model.DTO.Menu.CreateMenuDto;
import Model.DTO.Menu.MenuDto;
import Model.DTO.Menu.MenuQtyDto;
import Model.DTO.Menu.UpdateMenuDto;
import Utils.Dates;
import Model.Food.FoodService;

public class MenuService {
  private String FILE_PATH = "src/Model/Database/Menu/menus.json";
  private CommonServices commonServices = new CommonServices();
  private Dates datesUtil = new Dates();
  private FoodService foodService;

  public MenuService() {
    this.foodService = new FoodService();
  }

  public MenuService(String menuFilePath, String ingredientFilePath, String foodFilePath) {
    this.FILE_PATH = menuFilePath;
    this.foodService = new FoodService(foodFilePath, ingredientFilePath);
  }

  public ArrayList<MenuDto> getAllMenus() {
    ArrayList<MenuDto> menus = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        ArrayList<MenuModel> menuModels = mapper.readValue(file,
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

  public ArrayList<MenuDto> getMenus(String day, String type) {
    return this.getByDay(day, type);
  }

  public MenuDto getMenuById(Integer id) {
    MenuModel menuModel = this.getById(id);
    if (menuModel == null) {
      return null;
    }
    return this.mapToDto(menuModel);
  }

  // Para obtener los menus del dia, si se quieren todos los tipos se puede pasar
  // null en el parametro type
  public ArrayList<MenuDto> getMenuOfDay(String type) {
    String currentDay = datesUtil.getDayOfWeek(datesUtil.getCurrentDateTime());
    return this.getByDay(currentDay, type);
  }

  // //el parametro type es opcional, pasarlo en null si no se necesita
  // public ArrayList<MenuDto> getMenuOfWeek(String type) {
  // if(type == null) {
  // return this.getAllMenus();
  // }
  // ArrayList<MenuDto> menus = new ArrayList<>();
  // for(MenuDto menu : this.getAllMenus()) {
  // if(menu.getType().equals(type)) {
  // menus.add(menu);
  // }
  // }
  // return menus;
  // }

  public MenuDto create(CreateMenuDto menuDto) {
    if (menuDto.getDay() == null || menuDto.getDay().isEmpty()) {
      throw new IllegalArgumentException("Menu day cannot be null or empty");
    }
    if (menuDto.getType() == null || menuDto.getType().isEmpty()) {
      throw new IllegalArgumentException("Menu type cannot be null or empty");
    }
    if (menuDto.getFoodIds() == null || menuDto.getFoodIds().size() == 0 || menuDto.getFoodIds().size() > 3) {
      throw new IllegalArgumentException("Menu must have between 1 and 3 food items");
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
    if (menuDto.getType() == null || menuDto.getType().isEmpty()) {
      throw new IllegalArgumentException("Menu type cannot be null or empty");
    }
    if (menuDto.getFoodIds() == null || menuDto.getFoodIds().size() == 0 || menuDto.getFoodIds().size() > 3) {
      throw new IllegalArgumentException("Menu must have between 1 and 3 food items");
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
        menuDto.getType(),
        menuDto.getFoodIds(),
        menuDto.getDate(),
        existing.getIsActive(), // Mantener el estado actual
        existing.getCreatedAt(), // Mantener la fecha de creación
        this.datesUtil.getCurrentDateTime(), // Actualizar la fecha de actualización
        existing.getDeletedAt(), // Mantener la fecha de eliminación,,
        this.mapToMenuQtyList(menuDto.getQtys())); // Mapear los MenuQtyDto a MenuQty
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

  // metodo para verificar si ya existe un menu para un dia y tipo especifico,
  // para que se pueda mostrar la alerta
  public boolean isEixstingMenu(String day, String type) {
    ArrayList<MenuDto> menus = this.getByDay(day, type);
    return !menus.isEmpty();
  }

  // public boolean validateMenu(Integer id) {
  // MenuModel menu = this.getById(id);
  // if (menu == null) {
  // throw new IllegalArgumentException("Menu not found with id: " + id);
  // }
  // if (menu.getCurrentQty() >= 1) {
  // return true;
  // }
  // throw new IllegalStateException("Menu with id " + id + " has no available
  // quantity");
  // }

  public boolean updateCurrentQty(Integer id, Integer foodId, Integer qty, ArrayList<MenuQtyDto> qtys) {
    if (qty == null || qty < 0) {
      throw new IllegalArgumentException("Quantity must be a non-negative value");
    }
    if (qtys == null || qtys.size() == 0) {
      throw new IllegalArgumentException("Menu must have quantity information");
    }

    ArrayList<MenuQtyDto> updatedQtys = new ArrayList<>();
    boolean found = false;

    for (MenuQtyDto qtyDto : qtys) {
      if (qtyDto.getFoodId().equals(foodId)) {
        updatedQtys.add(new MenuQtyDto(qtyDto.getFoodId(), qtyDto.getQty(), qty));
        found = true;
      } else {
        updatedQtys.add(qtyDto);
      }
    }

    if (!found) {
      throw new IllegalArgumentException("Food not found with id: " + foodId);
    }

    MenuModel menu = this.getById(id);
    if (menu == null) {
      throw new IllegalArgumentException("Menu not found with id: " + id);
    }
    menu.setQtys(this.mapToMenuQtyList(updatedQtys));
    MenuModel updatedMenu = this.edit(menu);
    return updatedMenu != null;
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
        menuModel.getType(),
        foodDtos,
        menuModel.getDate(),
        menuModel.getIsActive(),
        menuModel.getCreatedAt(),
        menuModel.getUpdatedAt(),
        this.mapToMenuQtyDtoList(menuModel.getQtys()));
  }

  private ArrayList<MenuModel> getAll() {
    ArrayList<MenuModel> menus = new ArrayList<>();
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      if (file.exists()) {
        ArrayList<MenuModel> menuModels = mapper.readValue(file,
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
    ArrayList<MenuModel> menus = this.getAll();
    MenuModel found = null;
    for (MenuModel menu : menus) {
      if (menu.getId().equals(id)) {
        found = menu;
        break;
      }
    }
    return found;
  }

  private ArrayList<MenuDto> getByDay(String day, String type) {
    ArrayList<MenuModel> menus = this.getAll();
    ArrayList<MenuDto> foundMenus = new ArrayList<>();
    for (MenuModel menu : menus) {
      if (type != null) {
        if (menu.getDay().equals(day) && menu.getType().equals(type)) {
          foundMenus.add(this.mapToDto(menu));
        }
      } else {
        if (menu.getDay().equals(day)) {
          foundMenus.add(this.mapToDto(menu));
        }
      }
    }
    return foundMenus;
  }

  private MenuModel save(MenuModel menu) {
    ArrayList<MenuModel> menus = this.commonServices.getAllElements(FILE_PATH, MenuModel.class);
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
    ArrayList<MenuModel> menus = this.commonServices.getAllElements(FILE_PATH, MenuModel.class);
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

  private ArrayList<MenuQty> mapToMenuQtyList(ArrayList<MenuQtyDto> qtyDtos) {
    ArrayList<MenuQty> qtys = new ArrayList<>();
    for (MenuQtyDto qtyDto : qtyDtos) {
      MenuQty menuQty = new MenuQty(qtyDto.getFoodId(), qtyDto.getQty(), qtyDto.getCurrentQty());
      qtys.add(menuQty);
    }
    return qtys;
  }

  private ArrayList<MenuQtyDto> mapToMenuQtyDtoList(ArrayList<MenuQty> qtys) {
    ArrayList<MenuQtyDto> qtyDtos = new ArrayList<>();
    for (MenuQty menuQty : qtys) {
      MenuQtyDto menuQtyDto = new MenuQtyDto(menuQty.getFoodId(), menuQty.getQty(), menuQty.getCurrentQty());
      qtyDtos.add(menuQtyDto);
    }
    return qtyDtos;
  }

}
