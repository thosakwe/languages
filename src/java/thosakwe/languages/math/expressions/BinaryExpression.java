package thosakwe.languages.math.expressions;

import thosakwe.languages.Token;
import thosakwe.languages.math.MathExpression;
import thosakwe.languages.math.MathLexer;
import thosakwe.languages.math.MathParser;

public class BinaryExpression extends MathExpression {
    private MathExpression leftExpression;
    private MathExpression rightExpression;
    private String operator;

    public BinaryExpression(MathExpression left, MathExpression right, Token operator) {
        super(MathParser.BinaryExpr);
        getTokens().add(left);
        getTokens().add(operator);
        getTokens().add(right);
        leftExpression = left;
        rightExpression = right;
        this.operator = operator.getType();
    }

    public double getValue() {
        double left = leftExpression.getValue();
        double right = rightExpression.getValue();

        if (operator.equals(MathLexer.CARET))
            return Math.pow(left, right);
        else if (operator.equals(MathLexer.TIMES))
            return left * right;
        else if (operator.equals(MathLexer.SLASH))
            return left / right;
        else if (operator.equals(MathLexer.PLUS))
            return left + right;
        else if (operator.equals(MathLexer.MINUS))
            return left - right;

        return 0;
    }
}
