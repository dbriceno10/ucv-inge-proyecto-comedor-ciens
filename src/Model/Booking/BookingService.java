package Model.Booking;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Common.CommonServices;
import Utils.Dates;
import Enums.BookingStatus;
import Enums.UserRoles;
import Enums.UserTypes;
import DTO.Wallet.CreateWalletDto;
import DTO.Wallet.MovementDto;
import DTO.Wallet.UpdateWalletDto;
import DTO.Wallet.WalletDto;
import Context.User.UserSession;
import DTO.Booking.BookingDto;
import DTO.Booking.CreateBookingDto;
import DTO.Booking.UpdateBookingDto;
import DTO.Config.ConfigDto;
import DTO.Food.FoodDto;
import DTO.Menu.*;
import Model.User.*;
import Model.Menu.*;
import Model.Config.*;
import Model.Food.*;

public class BookingService {
  private static final String FILE_PATH = "src/Database/Booking/bookings.json";
  private CommonServices commonServices = new CommonServices();
  private Dates datesUtil = new Dates();
  private UserService userService = new UserService();
  private MenuService menuService = new MenuService();
  private FoodService foodService = new FoodService();
  private ConfigService configService = new ConfigService();

  public ArrayList<BookingDto> getTodayBookings(Integer documentId, String shift) {
    UserSession.getInstance().isAuthenticated();
    ArrayList<BookingDto> bookings = new ArrayList<>();
    ArrayList<BookingModel> bookingModels = this.gePendingBookings();
    for (BookingModel booking : bookingModels) {
      if (shift != null) {
        if (booking.getUserDocumentId().equals(documentId) && booking.getShift().equals(shift)) {
          bookings.add(this.mapToDto(booking));
        }
      } else {
        if (booking.getUserDocumentId().equals(documentId)) {
          bookings.add(this.mapToDto(booking));
        }
      }
    }
    return bookings;
  }

  public BookingDto create(CreateBookingDto bookingDto) {
    UserSession.getInstance().isAuthenticated();
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

    ArrayList<BookingModel> existing = this.gePendingBookings();
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

    if (menu.getCurrentQty() - 1 < 0) {
      throw new IllegalArgumentException("No hay suficiente cantidad disponible para el menú seleccionado.");
    }

    Double ccb = this.calculateCCB(user.getType(), menu.getQty(), food.getDecrease(), food.getValueCV());

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

    this.menuService.updateCurrentQty(menu.getId(), menu.getCurrentQty() - 1);

    return mapToDto(created);

  }

  public BookingDto update(UpdateBookingDto bookingDto) {
    UserSession.getInstance().isAuthenticated();
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
    UserSession.getInstance().isAuthenticated();
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
      if (edited == null) {
        if (status.equals(BookingStatus.CANCELED)) {
          this.menuService.updateCurrentQty(menu.getId(), menu.getCurrentQty() + 1);
        }
        return null;
      }
      return mapToDto(edited);
    } else {
      throw new IllegalArgumentException("Invalid status value: " + status);
    }
  }

  public Boolean delete(Integer id) {
    UserSession.getInstance().isAuthenticated();
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

  private ArrayList<BookingModel> gePendingBookings() {
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

  private Double calculateCCB(String type, Integer qty, Double decrease, Double valueCV) {
    ConfigDto config = this.configService.getConfig();
    double decreasePercentage = decrease / 100.0; // pasamos a porcentaje
    Double ccb = ((config.getValueCF() + valueCV) / qty) * (1 + decreasePercentage);
    return ccb;
  }

}
