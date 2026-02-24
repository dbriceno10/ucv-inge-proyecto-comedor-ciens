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
                System.out.println("case SEARCH_USER");
                break;
            case "CLOSE_VIEW":
                view.dispose();
                break;
            default: break;
        }
    }

    private void processSearchUser() {
        Integer id = view.getID();
        String type = view.getType_();

        ArrayList<BookingDto> bookingList = bookingService.getTodayBookings(id, type);
        System.out.println("SIZE: " + bookingList.size());

        if (!bookingList.isEmpty()) {
            System.out.println("lista NO vacia");
            for (BookingDto booking : bookingList) {
                if (booking.getShift().equals(type)) {
                    view.displayCard(type, type, type, type);
                }
            }
        }
        System.out.println("lista vacia");
    }
}
