package thosakwe.languages.math;

import thosakwe.languages.Node;

public abstract class MathExpression extends Node {
    public MathExpression(String type) {
        super(type);
    }

    public abstract double getValue();
}
