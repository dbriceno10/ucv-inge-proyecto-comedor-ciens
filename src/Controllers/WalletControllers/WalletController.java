
package Controllers.WalletControllers;

import View.Wallet.WalletView;
import View.Wallet.TopUpView;
import Model.DTO.User.AuthUserDto;
import Model.DTO.Wallet.MovementDto;
import Model.DTO.Wallet.WalletDto;
import Model.Wallet.WalletService;
import Context.User.UserSession;
import Enums.TypeMovement;
import java.util.ArrayList;

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
                TopUpView topUpView = new TopUpView(this.view);
                new TopUpController(topUpView);
                loadWalletInfo(); 
                break; 
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
            
            JPanel listPanel = view.getTransactionListPanel();

            try {
                WalletDto wallet = service.getWalletByUserId(currentUser.getId());

                if (wallet != null) {
                    String saldoFormateado = String.format("%.2f", wallet.getBalance());
                    balanceCmp.setText("$ " + saldoFormateado);

                    // HISTORIAL
                    
                    listPanel.removeAll();
                    ArrayList<MovementDto> movements = wallet.getMovements();
                    
                    if (movements != null && !movements.isEmpty()) {
                        for (int i = movements.size() - 1; i >= 0; i--) {
                            MovementDto mov = movements.get(i);
                            
                            String dateStr = "Desconocida";
                            if (mov.getDate() != null && mov.getDate().length() >= 10) {
                                String[] parts = mov.getDate().substring(0, 10).split("-");
                                dateStr = parts[2] + "/" + parts[1] + "/" + parts[0];
                            }

                            String prefix = mov.getType().equals(TypeMovement.ENTRY) ? "+$" : "-$";
                            String amountStr = prefix + String.format("%.2f", mov.getAmount());

                            listPanel.add(view.createTransactionRow(dateStr, amountStr));
                        }
                    } else {

                        JLabel lblEmpty = new JLabel("No hay movimientos recientes.");
                        lblEmpty.setForeground(java.awt.Color.GRAY);
                        lblEmpty.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                        listPanel.add(lblEmpty);
                    }
                
                    listPanel.revalidate();
                    listPanel.repaint();

                } else {
                    balanceCmp.setText("$ 0.00");
                }
            } catch (Exception ex) {
                System.out.println("Error cargando billetera: " + ex.getMessage());
                balanceCmp.setText("N/A.");
            }
        }
    }
}