package Controller;

import Controller.State.*;
import Model.Model;
import View.ButtonObserver;

/**
 * Controller class of MVC
 * Is going to get updated from the calculator buttons
 */
public class Controller implements ButtonObserver {

    private State current;
    private final Model model;

    public Controller(Model model) {
        this.model = model;
        current = new Start(this);
    }

    @Override
    public void update(String text) {
        current.handle(text);
    }

    public void setState(State state) {
        this.current = state;
    }

    public Model getModel() {
        return this.model;
    }
}
