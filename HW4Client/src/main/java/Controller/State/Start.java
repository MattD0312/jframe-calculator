package Controller.State;

import Controller.Controller;

/**
 * Fresh Start, we need a digit
 */
public class Start extends State {

    public Start(Controller controller) {
        super(controller);
    }

    @Override
    public void handle(String input) {
        State newState;
        this.storedInput = input;
        switch(input) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "C":
            case "=":
                break;
            default: //[0-9]
                model.addToDisplay(input);
                newState = new GetFirstOp(controller);
                newState.setStoredInput(this.storedInput);
                controller.setState(newState);
                break;
        }
    }
}
