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
                    String.valueOf(config.getWorkerPercentage()),
                    String.valueOf(config.getScholarPercentage())
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
            Double scholarPct = Double.parseDouble(view.getTxtScholarPct());

            // Estudiantes: 20% a 30%
            if (studentPct < 20.0) studentPct = 20.0;
            else if (studentPct > 30.0) studentPct = 30.0;

            // Profesores: 70% a 90%
            if (teacherPct < 70.0) teacherPct = 70.0;
            else if (teacherPct > 90.0) teacherPct = 90.0;

            // Empleados: 90% a 110%
            if (workerPct < 90.0) workerPct = 90.0;
            else if (workerPct > 110.0) workerPct = 110.0;

            // Becarios: 5% a 10%
            if (scholarPct < 5.0) scholarPct = 5.0;
            else if (scholarPct > 10.0) scholarPct = 10.0;

            // 3. Enviamos los datos a la base de datos
            ConfigDto updatedConfig = new ConfigDto(cf, teacherPct, studentPct, workerPct, scholarPct, null);
            service.updateConfig(updatedConfig);

            // Mensaje de éxito informando que se aplicaron reglas
            JOptionPane.showMessageDialog(view, "Configuración guardada.\n(Si un porcentaje estaba fuera de rango, fue auto-corregido por el sistema).");
            view.dispose(); 
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Por favor, ingrese solo números.", "Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }
}