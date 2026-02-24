package Testing.Booking;

import DTO.Booking.BookingDto;
import DTO.Booking.CreateBookingDto;
import DTO.Booking.UpdateBookingDto;
import Model.Booking.BookingService;

import java.util.ArrayList;

public class BookingTest {
  public static void main(String[] args) {
    BookingService bookingService = new BookingService();

    System.out.println("Starting booking tests...");

    // Test 1: Obtener todas las reservas
    System.out.println("Test 1: Get all bookings");
    try {
      ArrayList<BookingDto> bookings = bookingService.getTodayBookings(12345678, "morning");
      if (bookings != null && !bookings.isEmpty()) {
        System.out.println("Total bookings: " + bookings.size());
        for (BookingDto booking : bookings) {
          System.out.println("Booking ID: " + booking.getId() + ", User ID: " + booking.getUserId());
        }
      } else {
        System.out.println("No bookings found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get bookings: " + e.getMessage());
    }

    // Test 2: Crear una nueva reserva
    System.out.println("Test 2: Create a new booking");
    try {
      CreateBookingDto newBooking = new CreateBookingDto(
          1, // User ID
          1, // Menu ID
          1, // Food ID
          "morning", // Shift
          "2026-02-23", // Date
          "Monday" // Day
      );
      BookingDto createdBooking = bookingService.create(newBooking);
      System.out.println("Booking created: ID " + createdBooking.getId());
    } catch (Exception e) {
      System.out.println("Failed to create booking: " + e.getMessage());
    }

    // Test 3: Actualizar una reserva
    System.out.println("Test 3: Update a booking");
    try {
      UpdateBookingDto updatedBooking = new UpdateBookingDto(
          1, // Booking ID
          2, // New User ID
          2, // New Menu ID
          2, // New Food ID
          "afternoon", // New Shift
          "2026-02-24", // New Date
          "Tuesday" // New Day
      );
      BookingDto result = bookingService.update(updatedBooking);
      System.out.println("Booking updated: ID " + result.getId() + ", New Shift: " + result.getShift());
    } catch (Exception e) {
      System.out.println("Failed to update booking: " + e.getMessage());
    }

    // Test 4: Eliminar una reserva
    System.out.println("Test 4: Delete a booking");
    try {
      Boolean isDeleted = bookingService.delete(1);
      if (isDeleted) {
        System.out.println("Booking deleted successfully.");
      } else {
        System.out.println("Failed to delete booking.");
      }
    } catch (Exception e) {
      System.out.println("Failed to delete booking: " + e.getMessage());
    }

    // Test 5: Obtener una reserva por ID
    System.out.println("Test 5: Get booking by ID");
    try {
      BookingDto booking = bookingService.getBookingById(1);
      if (booking != null) {
        System.out.println("Booking found: ID " + booking.getId());
      } else {
        System.out.println("Booking not found.");
      }
    } catch (Exception e) {
      System.out.println("Failed to get booking by ID: " + e.getMessage());
    }

    // Test 6: Cobrar por un servicio
    System.out.println("Test 6: Charge for service");
    try {
      BookingDto chargedBooking = bookingService.chargeForService(1, "path/to/image");
      System.out.println("Service charged successfully for booking ID: " + chargedBooking.getId());
    } catch (Exception e) {
      System.out.println("Failed to charge for service: " + e.getMessage());
    }
  }
}