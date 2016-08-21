package thosakwe.languages.math.expressions;

import thosakwe.languages.Token;
import thosakwe.languages.math.MathExpression;
import thosakwe.languages.math.MathParser;

public class NumberExpression extends MathExpression {
    private int value = -1;

    public NumberExpression(Token token) {
        super(MathParser.NumberExpr);
        getTokens().add(token);
        value = Integer.parseInt(token.getText());
    }

    @Override
    public double getValue() {
        return value * 1.0;
    }
}
