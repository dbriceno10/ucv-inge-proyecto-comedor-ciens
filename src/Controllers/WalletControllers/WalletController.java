package Controllers.WalletControllers;

import View.Wallet.WalletView;
import Model.Wallet.WalletService;
import DTO.Wallet.WalletDto;
import DTO.User.AuthUserDto; 
import Context.User.UserSession;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WalletController implements ActionListener {
    private WalletView view;
    private WalletService service;

    public WalletController(WalletView view) {
        this.view = view;
        service = new WalletService();

        this.view.topUpListener(this);
        this.view.cancelListener(this);

        loadWalletInfo();
        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Recarga":
                JOptionPane.showMessageDialog(view, "Funcionalidad de recarga simulada.");
                //integración recarga
            case "CLOSE_WALLET":
                view.dispose();
                break;
            default:
                break;
        }
    }

    private void loadWalletInfo() {
        AuthUserDto currentUser = UserSession.getInstance().getUser();

        if (currentUser != null) {
            JLabel balanceCmp = view.getComponentBlc();
            try {
                WalletDto wallet = service.getWalletByUserId(currentUser.getId());

                if (wallet != null) {
                    String saldoFormateado = String.format("%.2f", wallet.getBalance());
                    balanceCmp.setText("$ " + saldoFormateado);
                } else {
                    balanceCmp.setText("$ 0.00");
                }
            } catch (Exception e) {
                System.out.println("Error cargando billetera: " + e.getMessage());
                balanceCmp.setText("N/A.");
            }
        }
    }
}