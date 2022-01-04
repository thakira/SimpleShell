import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple grep implementation. <strong>GP2-EA1b</strong>
 * 
 * @version 1.00 05 Oct 2018
 * @author Verena Kauth
 *
 */
public class Grep {
    // Attributes
    /** the pattern which should be searched within the lines of the string. */
    static String pattern;
    /** The output stream. */
    static String input;
    /** the input stream. */
    static String output;

    /**
     * Entry point for a simple grep implementation.
     * 
     * @param args
     *            - list of arguments transferred from the operating system
     */
    public static void main(String[] args) {
        try {
            evaluate(args);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * checks which input and output possibility is chosen by the user by evaluating
     * the amount of given arguments.
     * 
     * @param args
     *            - arguments given by the user
     * @throws IOException
     *             - Exception if something goes wrong while file handling
     */
    static void evaluate(String[] args) throws IOException {
        switch (args.length) {
        case 1:
            pattern = args[0];
            noFiles(pattern);
            break;
        case 2:
            pattern = args[0];
            input = args[1];
            inputFile(pattern, input);
            break;
        case 3:
            pattern = args[0];
            input = args[1];
            output = args[2];
            bothFiles(pattern, input, output);
            break;
        default:
            System.out.println("usage: Grep PATTERN [from-file] [to-file]");
            System.exit(0);
        }
    }

    /**
     * Method which builds the appropriate Streams if no file is given as an
     * attribute.
     * 
     * @throws IOException
     *             - Exception if something goes wrong while file handling
     * @param pattern
     *            - pattern to be searched
     * 
     */
    static void noFiles(String pattern) throws IOException {
        System.out.print("Bitte geben Sie den gewünschten Text ein" 
            + " und schließen Sie die Eingabe mit Enter ab: ");
        BufferedReader iReader = new BufferedReader(new InputStreamReader(System.in));
        StringReader reader1 = new StringReader(iReader.readLine());
        iReader.close();
        OutputStreamWriter writer1 = new OutputStreamWriter(System.out);
        grep(pattern, reader1, writer1);
    }

    /**
     * Method which builds the appropriate Streams if just an input file is given as
     * an attribute.
     * 
     * @param pattern
     *            - pattern to be searched
     * @param inputFile
     *            - filename of input file
     * @throws IOException
     *             - Exception if something goes wrong while file handling
     */
    static void inputFile(String pattern, String inputFile) throws IOException {
        InputStreamReader reader2 = new InputStreamReader(new FileInputStream(inputFile));
        OutputStreamWriter writer2 = new OutputStreamWriter(System.out);
        grep(pattern, reader2, writer2);
    }

    /**
     * Method which builds the appropriate Streams if in- and output files are given
     * as attributes.
     * 
     * @param pattern
     *            - pattern to be searched
     * @param inputFile
     *            - filename of input file
     * @param outputFile
     *            - filename for output
     * @throws IOException
     *             - Exception if something goes wrong while file handling
     */
    static void bothFiles(String pattern, String inputFile, String outputFile) throws IOException {
        InputStreamReader reader3 = new InputStreamReader(new FileInputStream(inputFile));
        FileWriter writer3 = new FileWriter(outputFile);
        grep(pattern, reader3, writer3);
    }

    /**
     * The method which searchs the input stream for the searched regex and writes
     * the appropriate files to the output stream.
     * 
     * @param regex
     *            - expression to be searched
     * @param reader
     *            - reader for chosen input
     * @param writer
     *            - writer for chosen output
     * @throws IOException
     *             - Exception if something goes wrong while file handling
     */
    static void grep(String regex, java.io.Reader reader, java.io.Writer writer) 
        throws IOException {
        BufferedReader input = new BufferedReader(reader);
        BufferedWriter bWriter = new BufferedWriter(writer);
        pattern = ".*" + regex;
        Pattern p = Pattern.compile(pattern);
        while (input.ready()) {
            String stream = input.readLine();
            Matcher m = p.matcher(stream);
            if (m.find()) {
                bWriter.write(stream);
                bWriter.flush();
                bWriter.newLine();
            }
        }
        bWriter.close();
        input.close();
    }
}
