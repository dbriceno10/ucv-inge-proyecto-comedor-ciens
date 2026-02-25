package Controllers.WalletControllers;

import Context.User.UserSession; 
import DTO.User.AuthUserDto;

import View.Wallet.TopUpView;
import Model.Wallet.WalletService;
import Utils.InputValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TopUpController implements ActionListener {
    private TopUpView view;
    private WalletService service;

    public TopUpController(TopUpView view) {
        this.view = view;
        this.service = new WalletService();

        // Aplicamos los efectos visuales y validaciones a los campos
        setupValidations();

        this.view.submitListener(this);
        this.view.cancelListener(this);

        this.view.setVisible(true);
    }

    // --- LÓGICA DE VALIDACIÓN Y EFECTOS TIPO APP DE BANCO ---
    private void setupValidations() {
        
        // 1. RESTRICCIÓN DE REFERENCIA (Solo números)
        InputValidator.addInputRestriction(view.getComponentReference(), "ONLY_NUMBERS", 15);

        // 2. EFECTO DEL MONTO (Tipo aplicación de Banco)
        JTextField txtAmount = view.getComponentAmount();
        txtAmount.setHorizontalAlignment(SwingConstants.RIGHT); // Alineado a la derecha
        txtAmount.setText("0.00"); // Valor inicial
        
        txtAmount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                e.consume(); // Anulamos la escritura normal de Java

                // Solo actuamos si es un número o la tecla borrar (Backspace)
                if (Character.isDigit(c) || c == '\b') {
                    // Quitamos el punto y agarramos solo los números puros
                    String current = txtAmount.getText().replaceAll("[^0-9]", "");

                    if (Character.isDigit(c)) {
                        current += c; // Añadimos el nuevo número al final
                    } else if (c == '\b' && current.length() > 0) {
                        current = current.substring(0, current.length() - 1); // Borramos el último
                    }

                    if (current.isEmpty()) {
                        current = "0";
                    }

                    // Dividimos entre 100 para generar los decimales correctamente
                    double val = Double.parseDouble(current) / 100.0;
                    txtAmount.setText(String.format(java.util.Locale.US, "%.2f", val));
                }
            }
        });

        // 3. EFECTO DE FECHA (Pone las barras '/' automáticamente)
        JTextField txtDate = view.getComponentDate();
        txtDate.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = txtDate.getText();

                // Si no es un número y no es la tecla de borrar, se ignora
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();
                    return;
                }

                // Limitar a 10 caracteres (DD/MM/YYYY)
                if (text.length() >= 10 && c != '\b') {
                    e.consume();
                    return;
                }

                // Añadir la barra automáticamente después del día y del mes
                if (c != '\b' && (text.length() == 2 || text.length() == 5)) {
                    txtDate.setText(text + "/");
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Mandar": // <--- Escuchamos el botón "Mandar"
                processTopUp();
                break;
            case "Cancelar":
                view.dispose();
                break;
            default:
                break;
        }
    }

    private void processTopUp() {
        try {
            String amountStr = view.getTxtAmount();
            String reference = view.getTxtReference();
            String bank = view.getCmbBank();
            String date = view.getTxtDate(); 

            // Validar campos vacíos
            if (amountStr.equals("0.00") || reference.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Por favor complete todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar que la fecha esté completa (10 caracteres)
            if (date.length() < 10) {
                JOptionPane.showMessageDialog(view, "La fecha debe estar en formato DD/MM/YYYY.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Double amount = Double.parseDouble(amountStr);

            if (amount <= 0) {
                JOptionPane.showMessageDialog(view, "El monto debe ser mayor a 0.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Obtenemos al usuario actual de la sesión
            AuthUserDto currentUser = UserSession.getInstance().getUser();
            if (currentUser == null) {
                JOptionPane.showMessageDialog(view, "Error: No hay sesión activa.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Enviamos la recarga incluyendo el ID del usuario, como exigen los nuevos cambios
            service.rechargeWallet(currentUser.getId(), amount, reference, bank);

            JOptionPane.showMessageDialog(view, "¡Recarga registrada con éxito! El saldo ha sido actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Cerramos la ventana modal
            view.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Hubo un error con el formato del monto.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error procesando recarga: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}