package Model.Composite;

import Model.Visitor.Visitor;

/**
 * Natural State is Add, Inverted is Sub
 * if being created to do subtraction, false is passed into constructor
 */
public class AddSubOp extends Expression {

    public AddSubOp(String value) {
        this.value = value;
    }

    public double accept(Visitor visitor) {
        return visitor.doForAddSub(this);
    }
}
