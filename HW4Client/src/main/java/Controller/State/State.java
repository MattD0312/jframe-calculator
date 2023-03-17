package Controller.State;

import Controller.Controller;
import Model.Model;

/**
 * State Pattern, controller is using these to do stuff
 * All the states work together in a Finite State machine, controller doesn't know about any of them
 */
public abstract class State {

    protected Controller controller;
    protected String storedInput;
    private boolean invertInput = false;
    protected Model model;

    public State(Controller controller) {
        this.controller = controller;
        model = controller.getModel();
    }

    public abstract void handle(String input);

    public void setStoredInput(String storedInput) {
        this.storedInput = storedInput;
    }

    public String getStoredInput() {
        return this.storedInput;
    }

    public void setInvertInput(boolean invert) {
        this.invertInput = invert;
    }

    public boolean isInvertInput() {
        return invertInput;
    }

    protected void clear() {
        model.setExpression(null); //clear expression
        model.setDisplay(" "); //clear result display
        model.clearEquation(); //clear equation that was saved
    }
}
