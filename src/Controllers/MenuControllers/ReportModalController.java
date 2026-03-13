package Controllers.MenuControllers;

import View.Menu.ReportModalView;
import Model.Booking.BookingService; 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReportModalController implements ActionListener {
    private ReportModalView view;
    private BookingService bookingService;

    public ReportModalController(ReportModalView view) {
        this.view = view;
        // Instanciamos el servicio que contiene getDinerStatistics()
        this.bookingService = new BookingService(); 

        this.view.closeListener(this);
        this.view.shiftChangeListener(this);

        loadStatistics(this.view.getSelectedShift());
        
        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "CLOSE_REPORT":
                view.dispose();
                break;
            case "SHIFT_CHANGED":
                // Si el administrador selecciona otro turno, recargamos la data
                String selectedShift = view.getSelectedShift();
                loadStatistics(selectedShift);
                break;
            default: break;
        }
    }

    private void loadStatistics(String shift) {
        try {
            ArrayList<String> stats = bookingService.getDinerStatistics(shift);
            
            if (stats != null) {
                view.updateStatistics(stats);
            }
        } catch (Exception ex) {
            System.out.println("Error al cargar estadísticas: " + ex.getMessage());
        }
    }
}