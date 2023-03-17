package Controller.State;

import Controller.Controller;

/**
 * equals was just chosen
 */
public class Calculate extends State {

    private boolean calculated = false;

    public Calculate(Controller controller) {
        super(controller);
    }

    @Override
    public void handle(String input) {

        if(!calculated) {
            doCalculation();
        }

        State newState;

        String result = ""; //gets result that was found in case they wanna use it (more operations)
        if (calculated) {
            result = model.getResultToString();
        }

        switch(input) {
            case "+":
            case "-":
                model.addToDisplay(input);
                //operator and prev result
                model.setExpression(model.newExpr(input, result)); //operator and prev result (w/o decimal)
                newState = new WaitAddSub(controller);
                if (input.equals("-")) {
                    newState.setInvertInput(true); //invert next cause they just hit minus
                }
                controller.setState(newState);
                break;
            case "*":
            case "/":
                model.addToDisplay(input);
                model.setExpression(model.newExpr(input, result)); //operator and prev result (w/o decimal)
                newState = new WaitMulDiv(controller);
                if (input.equals("/")) {
                    newState.setInvertInput(true); //invert next cause they just hit minus
                }
                controller.setState(newState);
                break;
            case "C":
                clear(); //clears results
                controller.setState(new Start(controller));
                break;
            case "=":
                break;
            default: //[0-9]
                clear();
                model.addToDisplay(input);
                newState = new GetFirstOp(controller);
                newState.setStoredInput(input);
                controller.setState(newState);
                break;
        }
    }

    private void doCalculation() {
        model.recordEquation();
        model.calcResult();
        model.logEquation();

        //sets display to value of result
        model.setDisplay(model.getResultToString());

        //we only wanna do calculation once, when they first hit equals
        this.calculated = true;
    }
}
