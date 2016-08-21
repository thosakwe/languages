package thosakwe.languages;

import java.util.ArrayList;
import java.util.List;

public class Node extends Token {
    private List<Token> _tokens = new ArrayList<Token>();

    public Node(String type) {
        super(type, "", -1, -1);
    }

    public List<Token> getTokens() {
        return _tokens;
    }

    @Override
    public String getText() {
        String result = "";

        for (Token token: _tokens) {
            result += token.getText();
        }

        return result;
    }

    @Override
    public Pos getPos() {
        if (!_tokens.isEmpty()) {
            return _tokens.get(0).getPos();
        } else return super.getPos();
    }

    @Override
    public String toString() {
        return getPos() + " \"" + getText() + "\" -> " + getType();
    }
}
