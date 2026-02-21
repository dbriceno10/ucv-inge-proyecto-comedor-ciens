package Controllers.WalletControllers;

import View.Wallet.WalletView;
import Model.Wallet.WalletService;
import DTO.Wallet.WalletDto;
import DTO.User.AuthUserDto; 
import Context.User.UserSession;
import javax.swing.*;

public class WalletController {
    private WalletView view;
    private WalletService service;

    public WalletController(WalletView view) {
        this.view = view;
        this.service = new WalletService();

        loadWalletInfo();

        if (this.view.btnCancel != null) {
            this.view.btnCancel.addActionListener(e -> view.dispose());
        }

        if (this.view.btnTopUp != null) {
            this.view.btnTopUp.addActionListener(e -> {
                JOptionPane.showMessageDialog(view, "Funcionalidad de recarga simulada.");
            });
        }

        this.view.setVisible(true);
    }

    private void loadWalletInfo() {
        // A. Obtener usuario de la sesión (Usando AuthUserDto)
        AuthUserDto currentUser = UserSession.getInstance().getUser();

        if (currentUser != null) {
            try {
                // B. Llamar al servicio
                WalletDto wallet = service.getWalletByUserId(currentUser.getId());

                // C. Inyectar en la vista
                if (wallet != null) {
                    String saldoFormateado = String.format("%.2f", wallet.getBalance());
                    this.view.lblBalance.setText("$ " + saldoFormateado);
                } else {
                    this.view.lblBalance.setText("$ 0.00");
                }
            } catch (Exception e) {
                System.out.println("Error cargando billetera: " + e.getMessage());
                this.view.lblBalance.setText("$ Error");
            }
        }
    }
}