package Model.Visitor;

import Model.Composite.AddSubOp;
import Model.Composite.AtomExpr;
import Model.Composite.MulDivOp;

/**
 * Visits elements of tree and calculates using recursion, and returns the result of the expression
 */
public class CalculationVisitor implements Visitor {

    private double result;

    @Override
    public double doForAddSub(AddSubOp asop) {

        if (!asop.isInvertLeft()) {
            if (!asop.isInvertRight()) {
                return this.visit(asop.getLeft()) + this.visit(asop.getRight()); //inverting neither
            } else {
                return this.visit(asop.getLeft()) + (-1 * this.visit(asop.getRight())); //inverting only right
            }
        } else {
            if (!asop.isInvertRight()) {
                return (-1 * this.visit(asop.getLeft())) + this.visit(asop.getRight()); //inverting only left
            } else {
                return (-1 * this.visit(asop.getLeft())) + (-1 * this.visit(asop.getRight())); //inverting left and right
            }
        }
    }

    @Override
    public double doForMulDiv(MulDivOp mdop) {

        if (!mdop.isInvertLeft()) {
            if (!mdop.isInvertRight()) {
                return this.visit(mdop.getLeft()) * this.visit(mdop.getRight()); //inverting neither
            } else {
                return this.visit(mdop.getLeft()) * (1 / this.visit(mdop.getRight())); //inverting only right
            }
        } else {
            if (!mdop.isInvertRight()) {
                return (1 / this.visit(mdop.getLeft())) * this.visit(mdop.getRight()); //inverting only left
            } else {
                return (1 / this.visit(mdop.getLeft())) + (1 / this.visit(mdop.getRight())); //inverting left and right
            }
        }
    }

    @Override
    public double doForAtom(AtomExpr atom) {
        return Double.parseDouble(atom.getValue());
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
