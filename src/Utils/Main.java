package Utils;

import javax.swing.SwingUtilities;

import Controllers.AuthControllers.LoginController;
import View.Auth.LoginView;

import Utils.Dates;

public class Main {

  public static void main(String[] args) {
    Dates dates = new Dates();
    System.out.println(dates.getDayOfWeek(dates.getCurrentDateTime()));

  }
}
