package Controllers.GoalkeeperControllers;

import View.Goalkeeper.GoalkeeperView;
import Model.Booking.BookingService;
import DTO.Booking.BookingDto;
import Utils.InputValidator;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;

public class GoalkeeperController implements ActionListener {
    private GoalkeeperView view;
    private BookingService bookingService;

    public GoalkeeperController (GoalkeeperView view) {
        this.view = view;
        bookingService = new BookingService();
        
        this.view.searchListener(this);
        this.view.closeListener(this);
        this.view.processListener(this);

        InputValidator.addInputRestriction(this.view.getComponent_txtUserID(), "ONLY_NUMBERS", 8);

        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH); // to display the interface in full screen mode.
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "SEARCH_USER":
                processSearchUser(); // búsqueda de reservación de usuario
                break;
            case "CLOSE_VIEW":
                view.dispose();
                break;
            case "PROCESS_BOOKING":
                //
                break;
            default: break;
        }
    }

    private void processSearchUser() {
        Integer id = view.getID();
        if (id == null) {
            view.showMessage("Por favor, ingrese un número de C.I. válido.");
            return;
        }

        String shift = view.getShift();
        ArrayList<BookingDto> bookingList = bookingService.getTodayBookings(id, shift);

        if (!bookingList.isEmpty()) {

            for (BookingDto booking : bookingList) {
                if (booking.getShift().equalsIgnoreCase(shift)) {
                    view.displayCard(id, booking.getDate(), booking.getPrice(), booking.getStatus());
                    break;
                }
            }
        } else {
            view.showMessage("No se han encontrado reservaciones activas para el usuario");
        }
    }   
}
