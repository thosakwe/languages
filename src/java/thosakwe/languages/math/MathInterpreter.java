package thosakwe.languages.math;

import org.apache.commons.cli.*;
import thosakwe.languages.Node;
import thosakwe.languages.Token;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MathInterpreter {
    public static void main(String[] args) {
        CommandLineParser commandLineParser = new DefaultParser();

        try {
            if (args.length == 0)
                throw new ParseException("Empty arg list");

            CommandLine commandLine = commandLineParser.parse(makeOptions(), args);
            String[] commandLineArgs = commandLine.getArgs();

            if (commandLineArgs.length == 0)
                throw new ParseException("Empty arg list");

            MathLexer lexer = new MathLexer(new File(commandLineArgs[0]));
            MathParser parser = new MathParser(lexer.tokenList());
            MathExpression mathExpression = parser.nextExpression();

            if (mathExpression == null) {
                throw new Exception("Invalid expression.");
            } else {
                System.out.println("Result of computation: " + mathExpression.getValue());
            }

        } catch (ParseException exc) {
            new HelpFormatter().printHelp("math [options...] <file>", makeOptions());
        } catch (Exception exc) {
            System.err.printf("Whoops! %s%n", exc.getMessage());
            exc.printStackTrace();
        }
    }

    public static MathParser parseString(String str) throws IOException {
        MathLexer lexer = new MathLexer(str);
        return new MathParser(lexer.tokenList());
    }

    private static Options makeOptions() {
        Options result = new Options();
        result.addOption("d", "debug", false, "Enable debug output.");
        return result;
    }
}
