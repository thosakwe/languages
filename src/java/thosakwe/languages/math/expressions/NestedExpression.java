package thosakwe.languages.math.expressions;

import thosakwe.languages.Pos;
import thosakwe.languages.Token;
import thosakwe.languages.math.MathExpression;
import thosakwe.languages.math.MathLexer;
import thosakwe.languages.math.MathParser;

public class NestedExpression extends MathExpression {
    private MathExpression inner;

    public NestedExpression(MathExpression inner, Pos start, Pos end) {
        super(MathParser.NestedExpr);
        this.inner = inner;
        getTokens().add(inner);
    }

    @Override
    public String getText() {
        return "(" + inner.getText() + ")";
    }

    @Override
    public double getValue() {
        return inner.getValue();
    }
}
