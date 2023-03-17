package Model.Composite;

import Model.Visitor.Visitor;

/**
 * Math Expressions are built as a tree
 * Invert Left and Right are used to deal with doing subtraction and divide
 * Value Field can be operator symbols or numbers cause I saved it as string
 */
public abstract class Expression {

    protected String value;
    private Expression leftNode = null;
    private Expression rightNode = null;
    private boolean invertLeft = false;
    private boolean invertRight = false;

    public abstract double accept(Visitor visitor);

    public String getValue() {
        return this.value;
    }

    public void setValue(String s) {
        this.value = s;
    }

    public Expression getLeft() {
        return this.leftNode;
    }

    public Expression getRight() {
        return this.rightNode;
    }

    public void setLeft(Expression e) {
        this.leftNode = e;
    }

    public void setRight(Expression e) {
        this.rightNode = e;
    }

    public void setInvertLeft(boolean invert) {
        this.invertLeft = invert;
    }

    public boolean isInvertLeft() {
        return this.invertLeft;
    }

    public void setInvertRight(boolean invert) {
        this.invertRight = invert;
    }

    public boolean isInvertRight() {
        return this.invertRight;
    }
}
