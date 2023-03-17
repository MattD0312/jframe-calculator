package Controller.State;

import Controller.Controller;

/**
 * Just got a mul or div symbol, need a digit
 */
public class WaitMulDiv extends State {
    public WaitMulDiv(Controller controller) {
        super(controller);
    }

    @Override
    public void handle(String input) {
        State newState;
        switch (input) {
            case "+":
            case "=":
            case "/":
            case "*":
            case "-":
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
                if (this.isInvertInput() && input.equals("0")) { //means divide was chosen and 0 just got chosen
                    newState = new Error(controller, this);
                    controller.setState(newState);
                    controller.update(input);
                    break;
                }
                newState = new GetMulDiv(controller);
                newState.setStoredInput(input);
                newState.setInvertInput(this.isInvertInput()); //if we're inverting this, make sure we remember that
                controller.setState(newState);
                break;
        }
    }
}
