package Controllers.ConfigControllers;

import View.Config.ConfigView;
import Model.Config.ConfigService;
import Model.DTO.Config.ConfigDto;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConfigController implements ActionListener {
    private ConfigView view;
    private ConfigService service;

    public ConfigController(ConfigView view) {
        this.view = view;
        service = new ConfigService();

        this.view.saveListener(this);
        this.view.cancelListener(this);

        loadConfigInfo(); // Carga los datos antes de mostrar
        this.view.setVisible(true); // Se hace visible al final, igual que WalletController
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "SAVE_CONFIG":
                processConfigUpdate();
                break;
            case "CLOSE_CONFIG":
                view.dispose();
                break;
            default:
                break;
        }
    }

    private void loadConfigInfo() {
        try {
            ConfigDto config = service.getConfig();
            if (config != null) {
                view.setConfigData(
                    String.valueOf(config.getValueCF()),
                    String.valueOf(config.getStudentPercentage()),
                    String.valueOf(config.getTeacherPercentage()),
                    String.valueOf(config.getWorkerPercentage())
                );
            }
        } catch (Exception ex) {
            System.out.println("Error cargando configuración: " + ex.getMessage());
        }
    }

    private void processConfigUpdate() {
        try {
            Double cf = Double.parseDouble(view.getTxtValueCF());
            Double studentPct = Double.parseDouble(view.getTxtStudentPct());
            Double teacherPct = Double.parseDouble(view.getTxtTeacherPct());
            Double workerPct = Double.parseDouble(view.getTxtWorkerPct());
          //TODO: Jean aca debes agregar en la vista un campo nuevo para el porcentaje de becarios y obtener su valor aquí, luego pasarlo al DTO y al servicio
            ConfigDto updatedConfig = new ConfigDto(cf, teacherPct, studentPct, workerPct, 5.0, null);
            service.updateConfig(updatedConfig);

            JOptionPane.showMessageDialog(view, "Configuración actualizada.");
            view.dispose(); // Cierra el modal al guardar exitosamente
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Por favor, ingrese solo números.", "Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }
}