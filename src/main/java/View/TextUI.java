package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

/**
 * displays text using methods
 */
public class TextUI {

    private PrintStream out;
    private BufferedReader in;

    /**
     * default constructor
     */
    public TextUI() {
        out = System.out;
        in = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * shows a menu then returns a users selection
     *
     * @param options a map that has your menu options
     * @return users selection
     * @throws IOException throws
     */
    public int showMenu(Map<Integer, String> options) throws IOException {
        for (Integer key : options.keySet()) {
            out.println(key + " - " + options.get(key));
        }
        int selection = readIntFromUser(options.keySet());
        return selection;
    }

    /**
     * reads int from user
     *
     * @param validValues - sets what is valid
     * @return - the choice
     * @throws IOException - throws
     */
    private int readIntFromUser(Set<Integer> validValues) throws IOException {

        while (true) {
            String rawString = in.readLine();
            try {
                int value = Integer.parseInt(rawString);
                if (validValues.contains(value)) {
                    return value;
                }
            } catch (NumberFormatException e) {
            }
            out.println("Sorry, you have to enter a value selection. Please try again");
        }
    }

    /**
     * prints a line
     *
     * @param s - what you want to print
     */
    public void display(String s) {
        out.println(s);
    }
}
