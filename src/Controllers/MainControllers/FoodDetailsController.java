package Controllers.MainControllers;

import View.Main.FoodDetailsView;
import Model.Booking.BookingService;
import Model.DTO.Booking.CreateBookingDto;
import Model.DTO.User.AuthUserDto;
import Model.DTO.Food.FoodDto;
import Model.DTO.Menu.MenuDto;
import Context.User.UserSession;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;

public class FoodDetailsController implements ActionListener {
    private FoodDetailsView view;
    private BookingService bookingService;
    private AuthUserDto userSession = UserSession.getInstance().getUser();

    private FoodDto foodData;
    private MenuDto menuData;

    public FoodDetailsController(FoodDetailsView view, FoodDto foodData, MenuDto menuData) {
        this.view = view;
        this.foodData = foodData;
        this.menuData = menuData;
        bookingService = new BookingService();

        this.view.closeListener(this);
        this.view.reserveListener(this);
        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "CLOSE_MODAL":
                view.dispose();
                break;
            case "CREATE_RESERVATION":
                processBooking();
                break;
            default: break;
        }
    }

    private void processBooking() {
        if (menuData == null) {
            JOptionPane.showMessageDialog(view, "No hay un menú activo para reservar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            CreateBookingDto booking = new CreateBookingDto(
                userSession.getId(),
                menuData.getId(),
                foodData.getId(),
                menuData.getType(),
                menuData.getDate(),
                menuData.getDay()
            );

            bookingService.create(booking);
            JOptionPane.showMessageDialog(view, "¡Reservación creada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error al crear la reservación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
