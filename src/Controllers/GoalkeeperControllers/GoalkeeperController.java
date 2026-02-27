package Controllers.GoalkeeperControllers;

import View.Goalkeeper.GoalkeeperView;
import View.CustomComponents.showMessageView;
import Model.Booking.BookingService;
import DTO.Booking.BookingDto;
import Utils.InputValidator;
import Utils.FileManager;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;

public class GoalkeeperController implements ActionListener {
    private GoalkeeperView view;
    private BookingService bookingService;
    private FileManager fileManager;
    String imagePath; // ruta de la imagen para el "reconocimiento facial".
    Integer current_bookingNumber;

    public GoalkeeperController (GoalkeeperView view) {
        this.view = view;
        bookingService = new BookingService();
        fileManager = new FileManager();
        imagePath = null;
        current_bookingNumber = null;
        
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
                proccesBooking();
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

                    // usado por chargeForService() para continuar el flujo.
                    current_bookingNumber = booking.getId(); 
                    break;
                }
            }
        } else {
            view.showMessage("No se han encontrado reservaciones activas para el usuario");
        }
    }   

    void proccesBooking() {
        try {
            imagePath = fileManager.pickupFile();
            bookingService.chargeForService(current_bookingNumber, imagePath);
            showMessageView.showMsg(view, "Verificación completada con éxito.\nSe ha realizado el cobro correspondiente.", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (IllegalArgumentException e) {
            showMessageView.showMsg(view, e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }  
    }

    void clearFields() {
        view.getComponent_txtUserID().setText("");
        JPanel panel = view.getComponent_cardsContainer();
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
}

    
