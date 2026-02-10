package Controllers.MainControllers;

import View.Main.DashboardView;
import View.Wallet.WalletView;
import Controllers.WalletControllers.WalletController;

public class DashboardController {
    private DashboardView view;

    public DashboardController(DashboardView view) {
        this.view = view;

        // AQUÍ CONECTAMOS: Cuando se pulse el botón de la billetera en la vista principal
        if (this.view.btnWallet != null) {
            this.view.btnWallet.addActionListener(e -> {
                // Creamos la vista del monedero, pasándole el Dashboard como ventana padre
                WalletView walletView = new WalletView(this.view);
                // Iniciamos el controlador (él cargará los datos y mostrará la ventana)
                new WalletController(walletView);
            });
        }
    }
}