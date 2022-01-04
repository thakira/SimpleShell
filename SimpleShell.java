import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * A simple Java shell. <strong>GP2-EA1c</strong>
 * 
 * @version 1.00 - 07 Oct 2018
 * @author Verena Kauth
 * 
 */
public class SimpleShell {
    // Attributes
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
        "grep", "pwd", "ls", "cd", "cat", "touch", "rm", "rmdir", "mkdir", "mv", "cp" };
    /**
     * File attribute for current working directory.
     */
    private File cwd;

    /**
     * Constructor for Shell.
     */
    public SimpleShell() {
        cwd = new File(System.getProperty("user.home"));
    }

    /**
     * Entry point for the shell. Constructs a <i>SimpleShell</i> and calls its
     * <i>loop</i> method.
     * 
     * @param args
     *            - not used
     */
    public static void main(String[] args) {
        // attribute for read input
        SimpleShell shell = new SimpleShell();
        try {
            shell.loop();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Entry point for shell.
     * 
     * @throws IOException
     *             - Exception bei fehlerhafter Eingabe
     */
    private void loop() throws IOException {
        // attribute for read input
        String input = "";
        // Schleife zum Anzeigen des Cursors
        do {
            // read Input
            input = readInput();

        } while (!input.equalsIgnoreCase(EXIT_CMD));
        System.out.println("bye");
    }

    /**
     * writes the cursor to screen and accepts user input.
     * 
     * @return input - the string the user wrote
     * @throws IOException
     *             - Exception bei fehlerhafter Eingabe
     */
    public String readInput() throws IOException {
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
     * @throws IOException
     *             -
     */
    public void evaluateInput(String input) throws IOException {
        String[] inputArray = input.split(" ");
        String cmd = "";
        for (int i = 0; i < commands.length; i++) {
            if (inputArray[0].equalsIgnoreCase(commands[i])) {
                cmd = inputArray[0];
            }
        }
        if (cmd != null && !cmd.equals("")) {
            if (cmd.equalsIgnoreCase("pwd")) {
                // System.out.println("pwd: " + cmd.equalsIgnoreCase("pwd"));
                pwd(inputArray);
            } else if (cmd.equalsIgnoreCase("ls")) {
                // System.out.println("ls: " + cmd.equalsIgnoreCase("ls"));
                ls(inputArray);
            } else if (cmd.equalsIgnoreCase("cd")) {
                cd(inputArray);
            } else if (cmd.equalsIgnoreCase("grep")) {
                grep(inputArray);
            } else {
                int countArgs = inputArray.length - 1;
                System.out.println("command: '" + cmd + "' with " + countArgs + " arguments");
            }
        } else {
            System.out.println(inputArray[0] + ": command not found.");
        }
    }

    /**
     * Shows the current directory as absolute path. This command accepts no
     * arguments.
     * 
     * @param args
     *            - the arguments for this command;
     */
    private void pwd(String[] args) {

        if (args.length > 1) {
            System.out.println("usage: pwd");
        } else {
            System.out.println(cwd.getPath());

        }

    }

    /**
     * Lists all files in the current directory. This command accepts no arguments.
     * 
     * @param args
     *            - the arguments for this command
     */
    private void ls(String[] args) {
        String[] files = null;
        if (args.length > 1) {
            System.out.println("usage: ls");
        } else {
            try {
                files = cwd.list();
                for (String file : files) {
                    System.out.println(file);
                }
            } catch (NullPointerException e) {
                System.out.println("No such files");
            }
        }
    }

    /**
     * changes to the given directory. This command accepts exactly one argument.
     * 
     * @param args
     *            - the arguments for this command @args[0] - the name of the
     *            command @args[1] - the name of the target directory(relative
     *            path!)
     * 
     * 
     */

    private void cd(String[] args) {
        if (args.length != 2) {
            System.out.println("usage: cd directory");
        } else {
            System.setProperty("user.home", cwd.getAbsolutePath() + File.separator + args[1]);
            cwd = new File(System.getProperty("user.home"));
        }

    }

    // /**
    // * Lists the content of a file to standard output. This command accepts
    // exactly
    // * one argument.
    // *
    // * @param args
    // * - the arguments for this command
    // */
    // private void cat(String[] args) {
    // System.out.println("cat");
    // }
    //
     /**
     * Filters the contents of a file to standard output. This command accepts one
     * to three arguments.
     *
     * @param args
     * - the arguments for this command; args[0] - the name of the
     * command args[1] - the substring to find in a line, as regular
     * expression args[2] - the file to read from. OPTIONAL, default:
     * standard input args[3] - the file to write to. OPTIONAL, default:
     * standard output
     * @throws IOException - Exception when file handling goes wrong
     */
    private void grep(String[] args) throws IOException {

        if (args.length == 1) {
            System.out.println("usage: Grep PATTERN [from-file] [to-file]");
        } else {
            int n = args.length - 1;
            String[] newArgs = new String[n];
            System.arraycopy(args, 1, newArgs, 0, n);
            Grep.evaluate(newArgs);
        }
    }

    // /**
    // * Updates the last modified time of a file. Creates a file if the file does
    // not
    // * exist. This command accepts exactly one argument.
    // *
    // * @param args
    // * - the arguments for this command
    // */
    // private void touch(String[] args) {
    // System.out.println("touch");
    // }
    //
    // /**
    // * Deletes a file. This command accepts exactly one argument.
    // *
    // * @param args
    // * - the argument for this command
    // */
    // private void rm(String[] args) {
    // System.out.println("rm");
    // }
    //
    // /**
    // * Deletes a directory. A directory can only be deleted when it is empty. This
    // * command accepts exactly one argument.
    // *
    // * @param args
    // * - the arguments for this command
    // */
    // private void rmdir(String[] args) {
    // System.out.println("rmdir");
    // }
    //
    // /**
    // * creates a new directory. This command accepts exactly one argument.
    // *
    // * @param args
    // * - the arguments for this command
    // */
    // private void mkdir(String[] args) {
    // System.out.println("mkdir");
    // }
    //
    // /**
    // * Move a file to another position. This command accepts exactly two
    // arguments.
    // *
    // * @param args
    // * - the arguments for this command
    // *
    // */
    // private void mv(String[] args) {
    // System.out.println("mv");
    // }
    //
    // /**
    // * Copies a file to another position. This command accepts exactly two
    // * arguments.
    // *
    // * @param args
    // * the arguments for this command
    // */
    // private void cp(String[] args) {
    // System.out.println("cp");
    // }

}
