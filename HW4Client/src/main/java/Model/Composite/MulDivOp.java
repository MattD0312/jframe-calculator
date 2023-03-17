package Model.Composite;

import Model.Visitor.Visitor;

/**
 * Multiply or Divide Operation
 */
public class MulDivOp extends MulDivExpression {

    public MulDivOp(String val) {
        this.value = val;
    }

    @Override
    public double accept(Visitor visitor) {
        return visitor.doForMulDiv(this);
    }
}
