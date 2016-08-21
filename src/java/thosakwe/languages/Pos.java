package thosakwe.languages;

public class Pos {
    private int _line, _index;

    public Pos() {

    }

    public Pos(int line, int index) {
        _line = line;
        _index = index;
    }

    public int getLine() {
        return _line;
    }

    public int getIndex() {
        return _index;
    }

    @Override
    public String toString() {
        return String.format("(%d:%d)", _line, _index);
    }
}
