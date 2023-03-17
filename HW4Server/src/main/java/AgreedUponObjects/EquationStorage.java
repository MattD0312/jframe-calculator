package AgreedUponObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * I built this to be able to send more stuff between client/server, wound up just sending a single string one-way
 */
public class EquationStorage implements Serializable {
    private final ArrayList<String> equations = new ArrayList<>();

    public void store(String equation) {
        this.equations.add(equation);
    }

    public ArrayList<String> getEquations() {
        return this.equations;
    }

    public void clear() {
        equations.clear();
    }
}
