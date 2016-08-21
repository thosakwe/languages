package thosakwe.languages.math;

import thosakwe.languages.GenericStream;
import thosakwe.languages.Pos;
import thosakwe.languages.Token;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MathLexer {
    private InputStream _inputStream;
    public static final String PAREN_L = "PAREN_L";
    public static final String PAREN_R = "PAREN_R";
    public static final String CARET = "CARET";
    public static final String TIMES = "TIMES";
    public static final String SLASH = "SLASH";
    public static final String PLUS = "PLUS";
    public static final String MINUS = "MINUS";
    public static final String NUMBER = "NUMBER";

    public MathLexer(File file) throws FileNotFoundException {
        _inputStream = new FileInputStream(file);
    }

    public MathLexer(String str) {
        _inputStream = new ByteArrayInputStream(str.getBytes());
    }

    private GenericStream<Character> getStream() throws IOException {
        List<Character> result = new ArrayList<Character>();
        Reader reader = new InputStreamReader(_inputStream);

        while(reader.ready()) {
            result.add((char) reader.read());
        }

        reader.close();
        return new GenericStream<Character>(Character.class, result);
    }

    public List<Token> tokenList() throws IOException {
        GenericStream<Character> stream = getStream();
        List<Token> result = new ArrayList<Token>();
        int line = 1, index = -1;

        while (stream.has()) {
            char ch = (char) stream.consume();
            index++;

            if (ch == '\n') {
                line++;
                index = -1;
                continue;
            }

            if (ch == '(') {
                result.add(new Token(PAREN_L, "(", line, index));
            } else if (ch == ')') {
                result.add(new Token(PAREN_R, ")", line, index));
            } else if (ch == '^') {
                result.add(new Token(CARET, "^", line, index));
            } else if (ch == '*') {
                result.add(new Token(TIMES, "*", line, index));
            } else if (ch == '/') {
                result.add(new Token(SLASH, "/", line, index));
            } else if (ch == '+') {
                result.add(new Token(PLUS, "+", line, index));
            } else if (ch == '-') {
                // Todo: Negative numbers
                result.add(new Token(MINUS, "-", line, index));
            } else if (Character.isDigit(ch)) {
                String text = "";
                text += ch;

                while (stream.peek() != null && Character.isDigit(stream.peek())) {
                    index++;
                    text += stream.consume();
                }

                result.add(new Token(NUMBER, text, line, index));
            }
        }

        return result;
    }
}
