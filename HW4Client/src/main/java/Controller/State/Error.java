package Controller.State;

import Controller.Controller;

import java.util.Arrays;

/**
 * handles bad input, and takes in the state that got the error in the constructor so it can send controller back to it
 */
public class Error extends State {

    private final State prevState;


    public Error(Controller controller, State prevState) {
        super(controller);
        this.prevState = prevState;
    }

    @Override
    public void handle(String input) {
        String[] buttonStrings = {"Discard", "Reset"};
        if (!Arrays.asList(buttonStrings).contains(input)) {
            model.throwError(controller); //means it isn't one of the two options
        }

        switch(input) {
            case "Discard":
                model.discardLastFromDisplay();
                controller.setState(prevState);
                break;
            case "Reset":
                clear();
                controller.setState(new Start(controller));
                break;
        }
    }
}
