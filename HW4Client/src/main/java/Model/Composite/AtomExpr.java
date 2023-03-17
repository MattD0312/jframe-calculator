package Model.Composite;

import Model.Visitor.Visitor;

/**
 * Atomic Expression, AKA Number
 * Condensed means it wasn't freshly entered from the calc, other modules need to know we've visited this already
 *
 * Is a MulDivExpression because it can't contain AddPlusOps
 * Really it can't contain anything if the program is to work correctly
 * Stores the number in Expression's value field
 */
public class AtomExpr extends MulDivExpression {

    private boolean condensed = false;


    public AtomExpr(String val) {
        setValue(val);
    }

    @Override
    public double accept(Visitor visitor) {
        return visitor.doForAtom(this);
    }

    public void setCondensed() {
        this.condensed = true;
    }

    public boolean isCondensed() {
        return this.condensed;
    }
}
