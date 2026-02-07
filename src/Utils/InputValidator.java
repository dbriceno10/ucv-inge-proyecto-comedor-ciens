package Utils;

import javax.swing.JTextField;
import java.awt.event.*;

public class InputValidator {
    public static void addInputRestriction(JTextField field, String type, int limit) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (Character.isISOControl(c)) { return; }

                int selectionLen = (field.getSelectedText() != null) ? field.getSelectedText().length() : 0;
                if (field.getText().length() - selectionLen >= limit) {
                    e.consume(); 
                    return;
                }
                switch (type) {
                    case "ONLY_LETTERS":
                        if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                            e.consume();
                        }
                        break;
                    case "ONLY_NUMBERS":
                        if (!Character.isDigit(c)) {
                            e.consume();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
