package Controller.State;

import Controller.Controller;

/**
 * A Plus or Minus was just chosen, waiting for next operand
 */
public class WaitAddSub extends State {

    public WaitAddSub(Controller controller) {
        super(controller);
    }

    @Override
    public void handle(String input) {
        State newState;
        switch (input) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "=":
                model.addToDisplay(input);
                newState = new Error(controller, this);
                controller.setState(newState);
                controller.update(input);
                break;
            case "C":
                clear(); //clears everything
                controller.setState(new Start(controller));
                break;
            default: //[0-9]
                model.addToDisplay(input);
                newState = new GetAddSub(controller);
                newState.setStoredInput(input);
                newState.setInvertInput(this.isInvertInput()); //if we're inverting this, make sure we remember that
                controller.setState(newState);
                break;
        }
    }
}
