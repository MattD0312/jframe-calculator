package View;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Subject of Observer Pattern
 */
public class Button extends JButton {

    private final ArrayList<ButtonObserver> observers = new ArrayList<>();

    public Button(String s) {
        super(s);
    }

    public void attach(ButtonObserver obs) {
        observers.add(obs);
    }

    public void detach(ButtonObserver obs) {
        observers.remove(obs);
    }

    public void notifyObservers() {
        for (ButtonObserver obs : observers) {
            obs.update(getState());
        }
    }

    public String getState() {
        return this.getText();
    }

}
