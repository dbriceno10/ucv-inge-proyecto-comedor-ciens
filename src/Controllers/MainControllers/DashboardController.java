package Controllers.MainControllers;

import DTO.Menu.MenuDto;
import DTO.Food.FoodDto;
import Enums.MenuOptions;
import Model.Menu.MenuService;
import Model.Food.FoodService;
import Context.User.UserSession;
import View.Main.DashboardView;
import View.Main.FoodDetailsView;
import View.Wallet.WalletView;
import Controllers.WalletControllers.WalletController;

import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DashboardController implements ActionListener {
    private DashboardView view;
    private MenuService menuService;
    private FoodService foodService;
    //private UserSession userSession;

    public DashboardController(DashboardView view) {
        this.view = view;
        menuService = new MenuService();
        foodService = new FoodService();
        
        this.view.menuTypeListener(this);
        this.view.walletBtnListener(this);
        this.view.setCardListener(this);

        loadData();
        this.view.setVisible(true);
        this.view.setExtendedState(JFrame.MAXIMIZED_BOTH); // to display the interface in full screen mode.
    }

   private void loadData() {
        // 1. Pedimos la lista de TODOS los menús programados para el día de hoy
        // Le pasamos 'null' para que no filtre por turno, sino que traiga todo lo de hoy
        ArrayList<MenuDto> dailyMenus = menuService.getMenuOfDay(null);
        
        MenuDto dailyMenu = null; // Empezamos asumiendo que no hay menú
        
        // 2. Verificamos por seguridad que la lista no venga nula ni vacía
        if (dailyMenus != null && !dailyMenus.isEmpty()) {
            // 3. Agarramos el primer menú de la lista (el índice 0)
            dailyMenu = dailyMenus.get(0); 
        }
        
        // 4. Se lo pasamos a la vista (si es null, la vista ya sabe qué hacer)
        view.showDailyMenu(dailyMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.startsWith("OPEN_FOOD_DETAILS_")) {
            // ex. OPEN_FOOD_DETAILS_5 -> 5)
            String foodIdStr = command.replace("OPEN_FOOD_DETAILS_", "");
            open_foodDetails(foodIdStr);
            return;
        }

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

    void open_foodDetails(String txtID) {
        int foodId = Integer.parseInt(txtID);
        FoodDto selectedFood = foodService.getFoodById(foodId);

        FoodDetailsView detailsModal = new FoodDetailsView(view, selectedFood);
        new FoodDetailsController(detailsModal);
        return;
    }

}