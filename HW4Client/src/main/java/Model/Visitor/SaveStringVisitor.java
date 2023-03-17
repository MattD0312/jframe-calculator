package Model.Visitor;

import Model.Composite.AddSubOp;
import Model.Composite.AtomExpr;
import Model.Composite.MulDivOp;

/**
 * Visitor that traverses tree and saves the expression into a variable to get referenced later
 */
public class SaveStringVisitor implements Visitor {

    private String equation = "";

    @Override
    public double doForAddSub(AddSubOp asop) {
        this.visit(asop.getLeft());
        this.equation+=asop.getValue();
        this.visit(asop.getRight());

        return 1; //success
    }

    @Override
    public double doForMulDiv(MulDivOp mdop) {
        this.visit(mdop.getLeft());
        this.equation+=mdop.getValue();
        this.visit(mdop.getRight());

        return 1; //success
    }

    @Override
    public double doForAtom(AtomExpr atom) {
        if (!atom.isCondensed()) {
            this.equation+=atom.getValue();
        }
        return 1; //success
    }

    public String getEquation() {
        return this.equation;
    }

    public void clearEquation() {
        this.equation = "";
    }
}
