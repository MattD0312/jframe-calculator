package Controller.State;

import Controller.Controller;

/**
 * Digit chosen after getting a multiply or divide symbol
 */
public class GetMulDiv extends State {
    public GetMulDiv(Controller controller) {
        super(controller);
    }

    @Override
    public void handle(String input) {
        State newState;
        switch (input) {
            case "+":
            case "-":
                model.addToDisplay(input);
                model.addRight(model.getRightMostBranch(), model.newExpr(this.storedInput));
                if (this.isInvertInput()) {
                    model.invertRight(model.getRightMostBranch());
                }

                model.recordEquation();
                model.calcResult();

                model.setExpression(model.newExpr(input, model.getResultToString(), true));

                newState = new WaitAddSub(controller);
                if (input.equals("-")) {
                    newState.setInvertInput(true);
                }
                controller.setState(newState);
                break;
            case "*":
            case "/":
                model.addToDisplay(input);
                model.addRight(model.getRightMostBranch(), model.newExpr(input, this.storedInput));
                newState = new WaitMulDiv(controller);
                if (this.isInvertInput()) {
                    model.invertLeft(model.getRightMostBranch());
                }
                if (input.equals("/")) {
                    newState.setInvertInput(true);
                }
                controller.setState(newState);
                break;
            case "C":
                clear(); //clears everything
                controller.setState(new Start(controller));
                break;
            case "=":
                model.addRight(model.getRightMostBranch(), model.newExpr(this.storedInput));
                if (model.getRightMostBranch().getValue().equals("/")) {
                    model.invertRight(model.getRightMostBranch()); //last thing hit was minus, so invert what was just entered
                }
                newState = new Calculate(controller);
                controller.setState(newState);
                controller.update(input); //run calculation
                break;
            default: //[0-9]
                model.addToDisplay(input);
                int tempInt = Integer.parseInt(this.storedInput);
                tempInt = (tempInt * 10) + Integer.parseInt(input);
                setStoredInput(String.valueOf(tempInt));
                break;
        }
    }
}
