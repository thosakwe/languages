package thosakwe.languages;

public class Token {
    private String _type, _text;
    private Pos _pos;

    public Token(String type, String text, int line, int index) {
        _type = type;
        _text = text;
        _pos = new Pos(line, index);
    }

    public String getType() {
        return _type;
    }

    public String getText() {
        return _text;
    }

    public Pos getPos() {
        return _pos;
    }

    @Override
    public String toString() {
        return _pos + " \"" + _text + "\" -> " + _type;
    }
}
