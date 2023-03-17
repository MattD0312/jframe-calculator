package Model.Composite;

/**
 * Times and Divide Expressions (exists so this one's kids can't take in AddSubOp Expressions)
 * Is built this way for PEMDAS
 */
public abstract class MulDivExpression extends Expression {

    private MulDivExpression leftNode = null;
    private MulDivExpression rightNode = null;

    //need to override so we can only add muldivs
    @Override
    public void setLeft(Expression e) {
        this.setLeft((MulDivExpression) e);
    }

    private void setLeft(MulDivExpression e) {
        this.leftNode = e;
    }

    //need to override so we can only add muldivs
    @Override
    public void setRight(Expression e) {
        this.setRight((MulDivExpression) e);
    }

    public void setRight(MulDivExpression e) {
        this.rightNode = e;
    }

    @Override
    public Expression getLeft() {
        return this.leftNode;
    }

    @Override
    public Expression getRight() {
        return this.rightNode;
    }
}
