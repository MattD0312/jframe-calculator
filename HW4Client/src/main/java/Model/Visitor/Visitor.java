package Model.Visitor;

import Model.Composite.AddSubOp;
import Model.Composite.AtomExpr;
import Model.Composite.Expression;
import Model.Composite.MulDivOp;

/**
 * Interface of visitor, takes in Expressions and allows for different behavior for AddSub, MulDiv, and Atomics
 */
public interface Visitor {
    default double visit(Expression e) {
        return e.accept(this);
    }

    double doForAddSub(AddSubOp asop);
    double doForMulDiv(MulDivOp mdop);
    double doForAtom(AtomExpr atom);
}
