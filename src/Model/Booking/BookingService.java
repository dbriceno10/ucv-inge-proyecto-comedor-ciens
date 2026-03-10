package Model.Booking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Common.CommonServices;
import Utils.*;
import Enums.BookingStatus;
import Enums.UserTypes;
import Model.Wallet.*;
import Model.User.*;
import Model.Menu.*;
import Model.Config.*;
import Model.DTO.Booking.BookingDto;
import Model.DTO.Booking.CreateBookingDto;
import Model.DTO.Booking.UpdateBookingDto;
import Model.DTO.Config.ConfigDto;
import Model.DTO.Food.FoodDto;
import Model.DTO.Menu.*;
import Model.DTO.Wallet.*;
import Model.Food.*;

public class BookingService {
  private String FILE_PATH = "src/Model/Database/Booking/bookings.json";
  private CommonServices commonServices = new CommonServices();
  private Dates datesUtil = new Dates();
  private FileManager fileManager = new FileManager();
  private UserService userService;
  private MenuService menuService;
  private FoodService foodService;
  private ConfigService configService;
  private WalletService walletService;

  public BookingService() {
    this.userService = new UserService();
    this.menuService = new MenuService();
    this.foodService = new FoodService();
    this.configService = new ConfigService();
    this.walletService = new WalletService();

  }

  public BookingService(String bookingFilePath, String userFilePath, String ucvUserFilePath, String walletFilePath,
      String movementsFilePath,
      String menuFilePath, String ingredientFilePath, String foodFilePath, String configFilePath) {
    this.FILE_PATH = bookingFilePath;
    this.userService = new UserService(userFilePath, ucvUserFilePath, walletFilePath, movementsFilePath);
    this.menuService = new MenuService(menuFilePath, ingredientFilePath, foodFilePath);
    this.foodService = new FoodService(foodFilePath, ingredientFilePath);
    this.configService = new ConfigService(configFilePath);
    this.walletService = new WalletService(walletFilePath, movementsFilePath);

  }

  public ArrayList<BookingDto> getTodayBookings(Integer documentId, String shift) {
    // UserSession.getInstance().isAuthenticated();
    String today = this.datesUtil.getDayOfWeek();
    ArrayList<BookingDto> bookings = new ArrayList<>();
    ArrayList<BookingModel> bookingModels = this.getPendingBookings();
    for (BookingModel booking : bookingModels) {
      if (shift != null) {
        if (booking.getUserDocumentId().equals(documentId) && booking.getShift().equals(shift)
            && booking.getDay().equals(today)) {
          bookings.add(this.mapToDto(booking));
        }
      } else {
        if (booking.getUserDocumentId().equals(documentId) && booking.getDay().equals(today)) {
          bookings.add(this.mapToDto(booking));
        }
      }
    }
    return bookings;
  }

  public BookingDto create(CreateBookingDto bookingDto) {
    // UserSession.getInstance().isAuthenticated();
    if (bookingDto.getUserId() == null) {
      throw new IllegalArgumentException("User ID is required.");
    }
    if (bookingDto.getMenuId() == null) {
      throw new IllegalArgumentException("Menu ID is required.");
    }
    if (bookingDto.getFoodId() == null) {
      throw new IllegalArgumentException("Food ID is required.");
    }
    if (bookingDto.getShift() == null || bookingDto.getShift().isEmpty()) {
      throw new IllegalArgumentException("Shift is required.");
    }
    if (bookingDto.getDate() == null || bookingDto.getDate().isEmpty()) {
      throw new IllegalArgumentException("Date is required.");
    }
    if (bookingDto.getDay() == null || bookingDto.getDay().isEmpty()) {
      throw new IllegalArgumentException("Day is required.");
    }

    ArrayList<BookingModel> existing = this.getPendingBookings();
    ArrayList<BookingModel> userBookings = new ArrayList<>();
    if (existing != null) {
      for (BookingModel booking : existing) {
        if (booking.getUserId().equals(bookingDto.getUserId()) && booking.getDay().equals(bookingDto.getDay())
            && booking.getShift().equals(bookingDto.getShift())) {
          userBookings.add(booking);
        }
      }
    }
    if (userBookings.size() >= 1) {
      throw new IllegalArgumentException("User already has a booking for the same date and shift.");
    }

    UserModel user = this.userService.getUserById(bookingDto.getUserId());
    if (user == null) {
      throw new IllegalArgumentException("User not found with ID: " + bookingDto.getUserId());
    }

    FoodDto food = this.foodService.getFoodById(bookingDto.getFoodId());
    if (food == null) {
      throw new IllegalArgumentException("Food not found with ID: " + bookingDto.getFoodId());
    }

    MenuDto menu = this.menuService.getMenuById(bookingDto.getMenuId());
    if (menu == null) {
      throw new IllegalArgumentException("Menu not found with ID: " + bookingDto.getMenuId());
    }

    MenuQtyDto menuQty = this.getMenuQtyByFoodId(menu.getQtys(), bookingDto.getFoodId());

    if (menuQty == null) {
      throw new IllegalArgumentException(
          "Food with ID: " + bookingDto.getFoodId() + " not found in menu with ID: " + bookingDto.getMenuId());
    }

    if (menuQty.getCurrentQty() - 1 < 0) {
      throw new IllegalArgumentException("No hay suficiente cantidad disponible para el menú seleccionado.");
    }

    Double ccb = this.calculateCCB(menuQty.getQty(), food.getDecrease(), food.getValueCV());

    Integer newId = this.commonServices.getLastIndex(FILE_PATH,
        BookingModel.class);

    BookingModel booking = new BookingModel(
        newId,
        bookingDto.getUserId(),
        bookingDto.getMenuId(),
        bookingDto.getFoodId(),
        bookingDto.getShift(),
        bookingDto.getDate(),
        bookingDto.getDay(),
        user.getDocumentId(),
        ccb,
        datesUtil.getCurrentDateTime(),
        datesUtil.getCurrentDateTime(),
        BookingStatus.PENDING);

    BookingModel created = this.save(booking);
    if (created == null) {
      return null;
    }

    this.menuService.updateCurrentQty(menu.getId(), bookingDto.getFoodId(), menuQty.getCurrentQty() - 1,
        menu.getQtys());

    return mapToDto(created);

  }

  public BookingDto update(UpdateBookingDto bookingDto) {
    // UserSession.getInstance().isAuthenticated();
    BookingModel existing = getById(bookingDto.getId());
    if (existing == null) {
      throw new IllegalArgumentException("Booking not found with id: " + bookingDto.getId());
    }
    String date = this.datesUtil.getCurrentDateTime();
    BookingModel updatedBooking = new BookingModel(
        existing.getId(),
        bookingDto.getUserId(),
        bookingDto.getMenuId(),
        bookingDto.getFoodId(),
        bookingDto.getShift(),
        bookingDto.getDate(),
        bookingDto.getDay(),
        existing.getUserDocumentId(),
        existing.getPrice(),
        existing.getCreatedAt(),
        date,
        existing.getStatus());

    BookingModel edited = this.edit(updatedBooking);
    if (edited == null) {
      return null;
    }
    return mapToDto(edited);
  }

  public BookingDto updateStatus(Integer id, String status) {
    // UserSession.getInstance().isAuthenticated();
    if (status.equals(BookingStatus.PENDING) || status.equals(BookingStatus.CONFIRMED)
        || status.equals(BookingStatus.CANCELED)) {
      BookingModel existing = getById(id);
      if (existing == null) {
        throw new IllegalArgumentException("Booking not found with id: " + id);
      }
      MenuDto menu = this.menuService.getMenuById(existing.getMenuId());
      if (menu == null) {
        throw new IllegalArgumentException("Menu not found with ID: " + existing.getMenuId());
      }
      String date = this.datesUtil.getCurrentDateTime();
      BookingModel updatedBooking = new BookingModel(
          existing.getId(),
          existing.getUserId(),
          existing.getMenuId(),
          existing.getFoodId(),
          existing.getShift(),
          existing.getDate(),
          existing.getDay(),
          existing.getUserDocumentId(),
          existing.getPrice(),
          existing.getCreatedAt(),
          date,
          status);

      BookingModel edited = this.edit(updatedBooking);
      if (edited != null) {
        if (status.equals(BookingStatus.CANCELED)) {
          MenuQtyDto menuQty = this.getMenuQtyByFoodId(menu.getQtys(), existing.getFoodId());

          if (menuQty != null) {
            this.menuService.updateCurrentQty(menu.getId(), existing.getFoodId(), menuQty.getCurrentQty() + 1,
                menu.getQtys());
          }
        }
        return mapToDto(edited);
      }
      return null;
    } else {
      throw new IllegalArgumentException("Invalid status value: " + status);
    }
  }

  public Boolean delete(Integer id) {
    // UserSession.getInstance().isAuthenticated();
    BookingModel existing = getById(id);
    if (existing == null) {
      throw new IllegalArgumentException("Booking not found with id: " + id);
    }
    String date = this.datesUtil.getCurrentDateTime();
    // TODO: Actualizar qty del menu al cancelar reserva si el estado no es PENDING
    BookingModel deletedBooking = new BookingModel(
        existing.getId(),
        existing.getUserId(),
        existing.getMenuId(),
        existing.getFoodId(),
        existing.getShift(),
        existing.getDate(),
        existing.getDay(),
        existing.getUserDocumentId(),
        existing.getPrice(),
        existing.getCreatedAt(),
        date,
        BookingStatus.CANCELED);
    BookingModel edited = this.edit(deletedBooking);
    if (edited == null) {
      return false;
    }
    return true;
  }

  public BookingDto getBookingById(Integer id) {
    BookingModel booking = this.getById(id);
    if (booking == null) {
      return null;
    }
    return this.mapToDto(booking);
  }

  public BookingDto chargeForService(Integer bookingId, String routePath) {
    ConfigDto config = this.configService.getConfig();
    if (config == null) {
      throw new IllegalArgumentException("Configuration not found.");
    }
    BookingModel booking = this.getById(bookingId);
    if (booking == null) {
      throw new IllegalArgumentException("Booking not found with id: " + bookingId);
    }

    UserModel user = this.userService.getUserById(booking.getUserId());
    if (user == null) {
      throw new IllegalArgumentException("User not found with ID: " + booking.getUserId());
    }

    BaseUserModel baseUser = this.userService.getUCVUserByDocumentId(user.getDocumentId());
    if (baseUser == null) {
      throw new IllegalArgumentException("Base user not found with document ID: " + user.getDocumentId());
    }

    WalletDto wallet = this.walletService.getWalletByUserId(booking.getUserId());
    if (wallet == null) {
      throw new IllegalArgumentException("Wallet not found for user id: " + booking.getUserId());
    }

    Double configPercentage = 1.0;
    if (user.getType().equals(UserTypes.PROFESSOR)) {
      configPercentage = config.getTeacherPercentage() / 100.0;
    } else if (user.getType().equals(UserTypes.STUDENT)) {
      configPercentage = config.getStudentPercentage() / 100.0;
    } else if (user.getType().equals(UserTypes.WORKER)) {
      configPercentage = config.getWorkerPercentage() / 100.0;
    } else if (user.getType().equals(UserTypes.SCHOLAR)) {
      configPercentage = config.getScholarPercentage() / 100.0;
    } else if (user.getType().equals(UserTypes.EXONERATED)) {
      configPercentage = 0.0;
    }

    Double newBalance = wallet.getBalance() - (booking.getPrice() * configPercentage);

    if (newBalance < 0) {
      throw new IllegalArgumentException("Insufficient balance in wallet for user id: " + booking.getUserId());
    }

    Boolean result = this.fileManager.simulateRecognition(baseUser.getImage(), routePath);

    if (result) {
      this.walletService.makePayment(user.getId(), booking.getPrice() * configPercentage);
      booking.setStatus(BookingStatus.CONFIRMED);
      booking.setUpdatedAt(this.datesUtil.getCurrentDateTime());
      this.edit(booking);
      return this.mapToDto(booking);
    }

    throw new IllegalArgumentException("Facial recognition failed for user id: " + booking.getUserId());
  }

  public ArrayList<String> getDinerStatistics(String shift) {
    int studentCount = 0;
    int scholarCount = 0;
    int exoneratedCount = 0;
    int professorCount = 0;
    int workerCount = 0;
    int adminCount = 0;

    ArrayList<BookingModel> bookings = this.getAll();
    for (BookingModel booking : bookings) {
      if (BookingStatus.CONFIRMED.equals(booking.getStatus()) && booking.getShift().equals(shift)) {
        UserModel user = this.userService.getUserById(booking.getUserId());
        if (user != null && user.getType() != null) {
          String type = user.getType();
          if (type.equals(UserTypes.STUDENT))
            studentCount++;
          else if (type.equals(UserTypes.SCHOLAR))
            scholarCount++;
          else if (type.equals(UserTypes.EXONERATED))
            exoneratedCount++;
          else if (type.equals(UserTypes.PROFESSOR))
            professorCount++;
          else if (type.equals(UserTypes.WORKER))
            workerCount++;
          else if (type.equals(UserTypes.ADMIN))
            adminCount++;
        }
      }
    }

    ArrayList<String> stats = new ArrayList<>();
    stats.add(UserTypes.STUDENT + ": " + studentCount);
    stats.add(UserTypes.SCHOLAR + ": " + scholarCount);
    stats.add(UserTypes.EXONERATED + ": " + exoneratedCount);
    stats.add(UserTypes.PROFESSOR + ": " + professorCount);
    stats.add(UserTypes.WORKER + ": " + workerCount);
    stats.add(UserTypes.ADMIN + ": " + adminCount);

    System.out.println("Estadísticas de comensales para el turno " + shift + " :");
    for (String stat : stats) {
      System.out.println("- " + stat);
    }

    return stats;
  }

  public Double calculateCCB(Integer qty, Double decrease, Double valueCV) {
    ConfigDto config = this.configService.getConfig();
    double decreasePercentage = decrease / 100.0; // pasamos a porcentaje
    Double ccb = ((config.getValueCF() + valueCV) / qty) * (1 + decreasePercentage);
    return ccb;
  }

  // metodos privados
  private BookingDto mapToDto(BookingModel model) {
    return new BookingDto(
        model.getId(),
        model.getUserId(),
        model.getMenuId(),
        model.getFoodId(),
        model.getShift(),
        model.getDate(),
        model.getDay(),
        model.getUserDocumentId(),
        model.getPrice(),
        model.getCreatedAt(),
        model.getUpdatedAt(),
        model.getStatus());
  }

  private ArrayList<BookingModel> getAll() {
    ArrayList<BookingModel> elements = this.commonServices.getAllElements(FILE_PATH, BookingModel.class);

    if (elements == null) {
      elements = new ArrayList<>();
    }

    ArrayList<BookingModel> bookings = new ArrayList<>();
    for (BookingModel booking : elements) {
      if (booking.getDeletedAt() == null) { // Filtrar reservas con deletedAt como null
        bookings.add(booking);
      }
    }
    return bookings;
  }

  private ArrayList<BookingModel> getPendingBookings() {
    ArrayList<BookingModel> bookingModels = this.getAll();
    ArrayList<BookingModel> bookings = new ArrayList<>();
    for (BookingModel booking : bookingModels) {
      if (booking.getStatus().equals(BookingStatus.PENDING)) { // Filtrar reservas con deletedAt como null
        bookings.add(booking);
      }
    }
    return bookings;
  }

  private BookingModel getById(Integer id) {
    ArrayList<BookingModel> bookings = this.getAll();
    BookingModel found = null;
    for (BookingModel booking : bookings) {
      if (booking.getId().equals(id)) {
        found = booking;
        break;
      }
    }
    return found;
  }

  private BookingModel save(BookingModel booking) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      ArrayList<BookingModel> bookings = this.commonServices.getAllElements(FILE_PATH, BookingModel.class);
      if (bookings == null) {
        bookings = new ArrayList<>();
      }
      bookings.add(booking);
      mapper.writeValue(file, bookings);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return booking;
  }

  private BookingModel edit(BookingModel booking) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      File file = new File(FILE_PATH);
      ArrayList<BookingModel> bookings = this.commonServices.getAllElements(FILE_PATH, BookingModel.class);
      // Find and update the ingredient
      for (Integer i = 0; i < bookings.size(); i++) {
        if (bookings.get(i).getId().equals(booking.getId())) {
          bookings.set(i, booking);
          break;
        }
      }
      mapper.writeValue(file, bookings);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    return booking;
  }

  private MenuQtyDto getMenuQtyByFoodId(ArrayList<MenuQtyDto> menuQtyList, Integer foodId) {
    MenuQtyDto found = null;
    for (MenuQtyDto menuQty : menuQtyList) {
      if (menuQty.getFoodId().equals(foodId)) {
        found = menuQty;
        break;
      }
    }
    return found;
  }

}
