package Controllers.MainControllers;

import DTO.Menu.MenuDto;
import Enums.MenuOptions;
import Model.Menu.MenuService;
//import Context.User.UserSession;
import View.Main.DashboardView;
import View.Wallet.WalletView;
import Controllers.WalletControllers.WalletController;

import java.awt.event.ActionListener;
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
    }

    private void loadData() {
        MenuDto dailyMenu = menuService.getMenuOfDay();
        view.showDailyMenu(dailyMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case MenuOptions.DAILY:
                view.updateTitles(MenuOptions.DAILY);
                break;
            case MenuOptions.WEEKLY:
                view.updateTitles(MenuOptions.WEEKLY);
                break;
            case "OPEN_WALLET":
                WalletView walletView = new WalletView(this.view);
                new WalletController(walletView);
                break;
            default:
                break;
        }
    }
}