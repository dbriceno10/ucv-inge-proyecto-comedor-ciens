package Controllers.MainControllers;

import View.Main.FoodDetailsView;
import Model.Booking.BookingService;
import DTO.Booking.BookingDto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FoodDetailsController implements ActionListener {
    private FoodDetailsView view;

    public FoodDetailsController(FoodDetailsView view) {
        this.view = view;

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
                //
                break;
            default: break;
        }
    }
}
