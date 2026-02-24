package Controllers.GoalkeeperControllers;

import View.Goalkeeper.GoalkeeperView;

import java.awt.event.ActionListener;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;

public class GoalkeeperController implements ActionListener {
    private GoalkeeperView view;

    public GoalkeeperController (GoalkeeperView view) {
        this.view = view;
        
        this.view.searchListener(this);
        this.view.closeListener(this);

        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH); // to display the interface in full screen mode.
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "SEARCH_USER":
                //
                break;
            case "CLOSE_VIEW":
                view.dispose();
                break;
            default: break;
        }
    }
}
