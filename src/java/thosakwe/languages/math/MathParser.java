package thosakwe.languages.math;

import thosakwe.languages.GenericStream;
import thosakwe.languages.Node;
import thosakwe.languages.Token;
import thosakwe.languages.math.expressions.BinaryExpression;
import thosakwe.languages.math.expressions.NestedExpression;
import thosakwe.languages.math.expressions.NumberExpression;

import java.util.ArrayList;
import java.util.List;

public class MathParser {
    private List<Token> tokenList;
    private GenericStream<Token> tokenStream;
    private MathExpression lastExpression = null;

    private final String[] binaryOperators = new String[]{
            MathLexer.CARET,
            MathLexer.TIMES,
            MathLexer.SLASH,
            MathLexer.PLUS,
            MathLexer.MINUS
    };

    public static final String NumberExpr = "NumberExpr";
    public static final String BinaryExpr = "BinaryExpr";
    public static final String NestedExpr = "NestedExpr";

    public MathParser(List<Token> tokenList) {
        this.tokenList = tokenList;
        tokenStream = new GenericStream<Token>(Token.class, tokenList);
    }

    public List<Node> nodeList() {
        List<Node> result = new ArrayList<Node>();

        while (tokenStream.has()) {
            Node next = nextNode();
            if (next != null)
                result.add(next);
        }

        return result;
    }

    public Node nextNode() {
        List<Node> alternatives = findAlternatives(tokenStream);
        int longestLength = 0;
        Node longest = null;

        for (Node node : alternatives) {
            int length = node.getText().length();

            if (length > longestLength) {
                longestLength = length;
                longest = node;
            }
        }

        if (longest != null) {
            try {
                // Consume all its tokens
                tokenStream.consume(longest.getTokens().size());
            } catch (Exception exc) {
                //
            }
        }

        return longest;
    }

    private Node selectFrom(Node[] nodes) {
        if (nodes.length == 0)
            return null;

        List<Node> alternatives = new ArrayList<Node>();

        for (Node node : nodes) {
            if (node != null)
                alternatives.add(node);
        }

        int longestLength = 0;
        Node longest = null;

        for (Node node : alternatives) {
            int length = node.getText().length();

            if (length > longestLength) {
                longestLength = length;
                longest = node;
            }
        }

        return longest;
    }

    MathExpression nextExpression() {
        NumberExpression numberExpression = NumberExpr();
        BinaryExpression binaryExpression = BinaryExpr(numberExpression);
        NestedExpression nestedExpression = NestedExpr();

        return setLastExpression((MathExpression) selectFrom(new Node[]{numberExpression, binaryExpression, nestedExpression}));
    }

    private List<Node> findAlternatives(GenericStream<Token> tokenStream) {
        List<Node> result = new ArrayList<Node>();
        tokenStream.consume();

        return result;
    }

    private NumberExpression NumberExpr() {
        if (tokenStream.current() != null && tokenStream.current().getType().equals(MathLexer.NUMBER)) {
            return new NumberExpression(tokenStream.current());
        } else return null;
    }

    private BinaryExpression BinaryExpr(MathExpression potentialExpression) {
        int flashback = tokenStream.getIndex();
        MathExpression left = lastExpression != null ? lastExpression : potentialExpression;

        if (left != null) {
            tokenStream.consume();
            Token current = tokenStream.current();

            if (current != null) {
                for (String binaryOperator : binaryOperators) {
                    if (current.getType().equals(binaryOperator)) {
                        tokenStream.consume();
                        MathExpression right = nextExpression();

                        if (right != null) {
                            tokenStream.seek(flashback);
                            return new BinaryExpression(left, right, current);
                        }
                    }
                }
            }
        }

        tokenStream.seek(flashback);

        return null;
    }

    private NestedExpression NestedExpr() {
        Token current = tokenStream.current();

        if (current != null) {
            if (current.getType().equals(MathLexer.PAREN_L)) {
                tokenStream.consume();
                MathExpression inner = nextExpression();
                tokenStream.consume(inner.getTokens().size());

                return new NestedExpression(inner, current.getPos(), tokenStream.current().getPos());
            }
        }

        return null;
    }

    private MathExpression setLastExpression(MathExpression lastExpression) {
        this.lastExpression = lastExpression;
        return lastExpression;
    }
}
