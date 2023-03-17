package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Error Msg UI
 * Gives User choice to discard invalid input or reset the whole thing, then closes itself
 */
public class ErrorView extends JFrame {
    public ErrorView(Controller controller) {
        String errorString = "Invalid Input, do you want to `Discard` previous, or `Reset`?";

        JPanel displayPanel = new JPanel();

        add(displayPanel, BorderLayout.NORTH);

        JLabel errorMsg = new JLabel(errorString);
        displayPanel.add(errorMsg);

        JPanel optionButtons = new JPanel();
        add(optionButtons, BorderLayout.CENTER);

        String[] optionStrings = {
                "Discard", "Reset"
        };

        for (String s: optionStrings) {
            Button tempButton = new Button(s);
            tempButton.attach(controller);
            tempButton.addActionListener(e -> tempButton.notifyObservers());
            tempButton.addActionListener(e -> this.dispose());
            optionButtons.add(tempButton);
        }

        optionButtons.setLayout(new GridLayout(1, 2));

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
