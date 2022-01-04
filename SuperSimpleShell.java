import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * A simple framework of a Java shell. <strong>GP2-EA1a</strong>
 * 
 * @version 1.00 - 28 Sep 2018
 * @author Verena Kauth
 * 
 */
public class SuperSimpleShell {
    // Attribute

    /**
     * The cursor shown to indicate that the shell is ready for input.
     */
    private static final String CURSOR = "> ";
    /**
     * The exit command.
     */
    private static final String EXIT_CMD = "exit";
    /**
     * array of allowed commands.
     */
    private static String[] commands = {
        "pwd", "ls", "cd", "cat", "touch", "rm", "rmdir", "mkdir", "mv", "cp" };

    /**
     * Entry point for shell.
     * 
     * @param args
     *            - not used
     */
    public static void main(String[] args) {
        // attribute for read input
        String input = "";
        // Schleife zum Anzeigen des Cursors
        do {
            // read Input
            try {
                input = readInput();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } while (!input.equalsIgnoreCase(EXIT_CMD));

        System.out.println("bye");
    }

    /**
     * writes the cursor to screen and accepts user input.
     * 
     * @return input - the string the user wrote
     * @throws IOException
     *             - Exception if file handling goes wrong
     */
    public static String readInput() throws IOException {
        String input;
        // create reader object to read input from "shell"
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // read input
        System.out.print(CURSOR);
        input = br.readLine();
        if (!input.equalsIgnoreCase(EXIT_CMD)) {
            evaluateInput(input);
        }

        return input;
    }

    /**
     * evaluates input if first argument is a known command and counts arguments
     * after command.
     * 
     * @param input
     *            - user input
     */
    public static void evaluateInput(String input) {
        String[] inputArray = input.split(" ");
        String cmd = "";
        for (int i = 0; i < commands.length; i++) {
            if (inputArray[0].equalsIgnoreCase(commands[i])) {
                cmd = inputArray[0];
            }
        }
        if (cmd != null && !cmd.equals("")) {
            int countArgs = inputArray.length - 1;
            System.out.println("command: '" + cmd + "' with " + countArgs + " arguments");
        } else {
            System.out.println(inputArray[0] + ": command not found.");
        }
    }

}
