package AgreedUponObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Protocal Data Unit - is what is actually being sent between sockets
 */
public class PDU implements Serializable {
    private final AgreedUponObjects.EquationStorage storage;

    public PDU() {
        this.storage = new AgreedUponObjects.EquationStorage();
    }

    public void clearStorage() {
        storage.clear();
    }

    public void addEquation(String equation) {
        storage.store(equation);
    }

    public void addEquations(ArrayList<String> equations) {
        for (String eq: equations) {
            this.addEquation(eq);
        }
    }

    public ArrayList<String> getEquations() {
        return this.storage.getEquations();
    }
}
