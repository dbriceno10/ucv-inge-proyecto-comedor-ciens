package Controllers.MainControllers;

import DTO.Menu.MenuDto;
import Enums.MenuOptions;
import Model.Menu.MenuService;
//import Context.User.UserSession;
import View.Main.DashboardView;
import View.Wallet.WalletView;
import Controllers.WalletControllers.WalletController;

import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;

public class DashboardController implements ActionListener {
    private DashboardView view;
    private MenuService menuService;

    public DashboardController(DashboardView view) {
        this.view = view;
        this.menuService = new MenuService();

        this.view.menuTypeListener(this);
        this.view.walletBtnListener(this);

        loadData();
        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH); // to display the interface in full screen mode.
    }

    private void loadData() {
        // MenuDto dailyMenu = menuService.getMenuOfDay();
        MenuDto dailyMenu = menuService.getMenuById(1); //TODO: esto lo puse solo para que no se rompa, ahora getMenuOfDay trae un arreglo en lugar de un solo elemento
        view.showDailyMenu(dailyMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "OPEN_WALLET":
                WalletView walletView = new WalletView(this.view);
                new WalletController(walletView);
                break;
            case "MENU_TYPE_CHANGED":
                JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
                String selectedMenu = (String) comboBox.getSelectedItem();

                if (MenuOptions.DAILY.equals(selectedMenu)) {
                    view.updateTitles(MenuOptions.DAILY);
                } else if (MenuOptions.WEEKLY.equals(selectedMenu)) {
                    view.updateTitles(MenuOptions.WEEKLY);
                }
            default: break;
        }
    }
}