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
import DTO.Wallet.CreateWalletDto;
import DTO.Wallet.MovementDto;
import DTO.Wallet.UpdateWalletDto;
import DTO.Wallet.WalletDto;
import Context.User.UserSession;
import DTO.Booking.BookingDto;
import DTO.Booking.CreateBookingDto;
import DTO.Config.ConfigDto;
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
    UserSession.getInstance().hasRole(UserRoles.GOALKEEPER);
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

  // public BookingDto create(CreateBookingDto bookingDto) {
  //   UserSession.getInstance().isAuthenticated();
  //   if (bookingDto.getUserId() == null) {
  //     throw new IllegalArgumentException("User ID is required.");
  //   }
  //   if (bookingDto.getMenuId() == null) {
  //     throw new IllegalArgumentException("Menu ID is required.");
  //   }
  //   if (bookingDto.getFoodId() == null) {
  //     throw new IllegalArgumentException("Food ID is required.");
  //   }
  //   if (bookingDto.getShift() == null || bookingDto.getShift().isEmpty()) {
  //     throw new IllegalArgumentException("Shift is required.");
  //   }
  //   if (bookingDto.getDate() == null || bookingDto.getDate().isEmpty()) {
  //     throw new IllegalArgumentException("Date is required.");
  //   }
  //   if (bookingDto.getDay() == null || bookingDto.getDay().isEmpty()) {
  //     throw new IllegalArgumentException("Day is required.");
  //   }

    

  //   Integer newId = this.commonServices.getLastIndex(FILE_PATH, BookingModel.class);

  //   BookingModel booking = new BookingModel(
  //       newId,
  //       bookingDto.getUserId(),
  //       bookingDto.getMenuId(),
  //       bookingDto.getFoodId(),
  //       bookingDto.getShift(),
  //       bookingDto.getDate(),
  //       bookingDto.getDay(),
  //       bookingDto.getUserDocumentId(),
  //       bookingDto.getPrice(),
  //       datesUtil.getCurrentDateTime(),
  //       datesUtil.getCurrentDateTime(),
  //       BookingStatus.PENDING);
  //   return this.mapToDto(this.save(booking));
  // }

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

  private Double calculateCCB(String role, Integer qt, Double decrease, Double valueCV) {
    ConfigDto config = this.configService.getConfig();
    
    return 0.0;
  }

}
