package Controller.State;

import Controller.Controller;

import java.io.IOException;

/**
 * One Digit has been hit, now we are getting (building) first operand
 */
public class GetFirstOp extends State {

    public GetFirstOp(Controller controller) {
        super(controller);
    }

    @Override
    public void handle(String input) {
        State newState;
        switch (input) {
            case "+":
            case "-":
                model.addToDisplay(input);
                model.setExpression(model.newExpr(input, this.storedInput)); //operator then current digits
                newState = new WaitAddSub(controller);
                if (input.equals("-")) {
                    newState.setInvertInput(true);//have to invert input next state is taking cause subtracting it
                }
                controller.setState(newState);
                break;
            case "*":
            case "/":
                model.addToDisplay(input);
                model.setExpression(model.newExpr(input, this.storedInput)); //operator then current digits
                newState = new WaitMulDiv(controller);
                if (input.equals("/")) {
                    newState.setInvertInput(true);//have to invert input next state is taking cause dividing it
                }
                controller.setState(newState);
                break;
            case "C":
                clear(); //clears everything
                controller.setState(new Start(controller));
                break;
            case "=":
                model.addToDisplay(input);
                newState = new Error(controller, this);
                controller.setState(newState);
                controller.update(input); //pass the stored input (whatever was entered prior) to error and run it
                break;
            default: //[0-9]
                model.addToDisplay(input);
                int tempInt = 0;
                if (this.getStoredInput().equals("") || this.getStoredInput() == null) {
                    this.storedInput = input;
                } else {
                    tempInt = Integer.parseInt(this.storedInput);
                    tempInt = (tempInt * 10) + Integer.parseInt(input);
                }
                this.setStoredInput(String.valueOf(tempInt));
                break;
        }
    }
}
