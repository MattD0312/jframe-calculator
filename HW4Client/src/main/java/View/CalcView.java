package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Calculator UI
 * Never Used JLabel before, kinda neat
 * also has some other functionality that the model can access
 * knows about controller, not model.  Which is correct
 */
public class CalcView extends JFrame {
    private final JLabel resultDisplay;

    private JPanel buttonGroup;

    public CalcView(Controller controller) {
        super("Simple Calculator");

        JPanel displayPanel = new JPanel();
        add(displayPanel, BorderLayout.NORTH);

        resultDisplay = new JLabel(" ");
        displayPanel.add(resultDisplay);

        // create the buttons

        buttonGroup = new JPanel();
        add(buttonGroup, BorderLayout.CENTER);
        buttonGroup.setLayout(new GridLayout(4, 4, 0, 0));

        String[] buttonStrings = {
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "*",
                "0", "C", "=", "/"
        };

        for (String s : buttonStrings) {
            Button tempButton = new Button(s);
            tempButton.attach(controller);
            tempButton.addActionListener(e -> tempButton.notifyObservers());
            buttonGroup.add(tempButton);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
    }

    public void setResultDisplay(String expr) {
        this.resultDisplay.setText(expr);
    }

    public String getResultDisplay() {
        return this.resultDisplay.getText();
    }

    public void showError(Controller controller) {
        new ErrorView(controller);
    }
}
