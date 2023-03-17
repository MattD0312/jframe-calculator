package Model;

import AgreedUponObjects.PDU;
import Controller.Controller;
import Model.Composite.AddSubOp;
import Model.Composite.AtomExpr;
import Model.Composite.Expression;
import Model.Composite.MulDivOp;
import Model.Visitor.CalculationVisitor;
import Model.Visitor.SaveStringVisitor;
import View.CalcView;

import java.io.*;
import java.net.Socket;

/**
 * Main Model class of MVC
 * Absolute back-end workhorse:
 *      Saves the Expression being worked on
 *      Modifies the Expressions every which way needed (building tree, setting something as inverted, etc.)
 *      Contains and uses the Concrete Visitors
 *      Connects to the Server
 *
 * Wish I had the time to clean up these methods and make a better interface for controller to deal with
 * 19 Credits this term, and the methods of this class are a result
 */
public class Model {
    private final CalculationVisitor calcVis = new CalculationVisitor();
    private final SaveStringVisitor saveStringVis = new SaveStringVisitor();
    private CalcView view;

    private Socket socket;
    private ObjectOutputStream objectOut;
    private boolean connected;

    private Expression expression;

    /**
     * Used when connection failed
     */
    public Model() {
        this.socket = null;
        this.objectOut = null;
        connected = false;
    }

    /**
     * Used when the connection was successful
     */
    public Model(Socket socket) {
        try {
            this.socket = socket;
            this.objectOut = new ObjectOutputStream(this.socket.getOutputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setView(CalcView view) {
        this.view = view;
        view.setVisible(true);
    }

    /**
     * gets and saves result
     */
    public void calcResult() {
        double result = calcVis.visit(this.expression);
        calcVis.setResult(result);
    }

    public String getResultToString() {
        if ((int)this.getResult() == this.getResult()) {
            return String.valueOf((int) this.getResult());
        } else {
            return String.valueOf(this.getResult());
        }
    }

    private double getResult() {
        return this.calcVis.getResult();
    }

    public void setExpression(Expression expr) {
        this.expression = expr;
    }

    /**
     * puts second expression to right of first, and sets whether the parent is inverting that right child
     */
    public void addRight(Expression parent, Expression child) {
        parent.setRight(child);
    }

    /**
     * inverts left expression of the argument expression
     */
    public void invertLeft(Expression expr) {
        expr.setInvertLeft(true);
    }

    /**
     * inverts right expression of the arg expression
     */
    public void invertRight(Expression expr) {
        expr.setInvertRight(true);
    }

    /**
     * creates a new atomic and marks as condensed (or doesn't)
     */
    private Expression newAtom(String input, boolean condensed) {

        AtomExpr newExpr = new AtomExpr(input);
        if (condensed) {
            newExpr.setCondensed();
        }
        return newExpr;
    }

    /**
     * new AddSubOp, plus or minus, takes in expression to be put to left
     */
    private Expression newASOP(String op, Expression expr) {
        Expression newExpr = new AddSubOp(op);
        newExpr.setLeft(expr);
        return newExpr;
    }

    /**
     * new MulDivOp, times or divide, takes in expression to be put to left
     */
    private Expression newMDOP(String op, Expression expr) {
        Expression newExpr = new MulDivOp(op);
        newExpr.setLeft(expr);
        return newExpr;
    }

    /**
     * creates and returns expression with first arg on the left in an atomic, assumes that arg is not condensed (not result of a calculation
     */
    public Expression newExpr(String op, String input) {
        switch(op) {
            case "+":
            case "-":
                return newASOP(op, newExpr(input, false));
            case "*":
            case "/":
                return newMDOP(op, newExpr(input, false));
            default:
                return null;
        }
    }

    /**
     * creates and returns expression with first arg on the left in an atomic, allows setting arg to be condensed (result of calculation)
     */
    public Expression newExpr(String op, String input, boolean condensed) {
        switch(op) {
            case "+":
            case "-":
                return newASOP(op, newExpr(input, condensed));
            case "*":
            case "/":
                return newMDOP(op, newExpr(input, condensed));
            default:
                return null;
        }
    }

    /**
     * creates and returns atomic with arg value
     */
    public Expression newExpr(String input) {
        return newAtom(input, false);
    }

    /**
     * creates and returns atomic with arg value, but allows to set condensed to true
     */
    public Expression newExpr(String input, boolean condensed) {
        return newAtom(input, condensed);
    }

    /**
     * returns right most branch of tree (node that can't go down into right child)
     */
    public Expression getRightMostBranch() {
        return this.getRightMostBranch(this.expression);
    }

    /**
     * used only in this method to make recursion simpler
     */
    private Expression getRightMostBranch(Expression expr) {
        if ((expr.getRight() == null) || expr.getRight() instanceof AtomExpr) {
            return expr;
        } else {
            return this.getRightMostBranch(expr.getRight());
        }
    }

    public void setDisplay(String toDisplay) {
        this.view.setResultDisplay(toDisplay);
    }

    public void addToDisplay(String newChar) {
        this.view.setResultDisplay(getDisplay() + newChar);
    }

    /**
     * Uses Regex (i knew id find a way to cram it into this project) to get rid of last character
     * Used in Discard function of Error Checking
     */
    public void discardLastFromDisplay() {
        this.view.setResultDisplay(this.view.getResultDisplay().replaceFirst(".$", ""));
    }

    private String getDisplay() {
        return view.getResultDisplay();
    }

    public void clearEquation() {
        this.saveStringVis.clearEquation();
    }

    public void recordEquation() {
        this.saveStringVis.visit(this.expression);
    }

    /**
     * Is what actually sends stuff to the Server
     * Only runs if connection was successful
     */
    public void logEquation() {
        if (connected) {
            try {
                PDU toSend = new PDU();
                String equation = saveStringVis.getEquation() + "=" + this.getResultToString();
                toSend.addEquation(equation);
                objectOut.writeObject(toSend);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.saveStringVis.clearEquation();
    }

    /**
     * throws up the error prompt, sadly this needs to know about controller so it can observe the buttons
     * I'm in 19 credits so it will just have to be like this
     */
    public void throwError(Controller controller) {
        view.showError(controller);
    }
}
