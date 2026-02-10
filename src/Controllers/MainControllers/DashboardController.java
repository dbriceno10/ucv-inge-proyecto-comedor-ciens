package Controllers.MainControllers;

import DTO.Menu.MenuDto;
import Model.Menu.MenuService;
//import Context.User.UserSession;
//import DTO.User.AuthUserDto;
import View.Main.DashboardView;

public class DashboardController {
    private DashboardView view;
    private MenuService menuService;

    public DashboardController(DashboardView view) {
        this.view = view;
        this.menuService = new MenuService();

        loadData();
    }

    private void loadData() {
        MenuDto dailyMenu = menuService.getMenuOfDay();
        view.showDailyMenu(dailyMenu);
    }
}
