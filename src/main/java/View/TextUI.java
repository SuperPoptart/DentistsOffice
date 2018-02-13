package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashSet;
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
     * reads a string from user
     *
     * @return the string
     * @throws IOException throws
     */
    public String readStringFromUser() throws IOException {
        String rawString = in.readLine();
        return rawString;
    }

    /**
     * prints a line
     *
     * @param s - what you want to print
     */
    public void display(String s) {
        out.println(s);
    }

    /**
     * reads a boolean from user using numbers
     *
     * @return a boolean
     * @throws IOException throws
     */
    public boolean readBooleanFromUser() throws IOException {
        Set<Integer> valid = new HashSet<>();
        valid.add(0);
        valid.add(1);
        int holdin;

        holdin = readIntFromUser(valid);
        if (holdin == 0) {
            return true;
        } else return false;
    }

    /**
     * reads an int from user
     *
     * @return the int
     * @throws IOException throws
     */
    public int readIntFromUser() throws IOException {
        int rawInt;
        rawInt = Integer.parseInt(in.readLine());
        return rawInt;
    }

    /**
     * reads a double from user
     *
     * @return the double
     * @throws IOException throws
     */
    public double getDoubleFromUser() throws IOException {
        double rawDouble;
        rawDouble = Double.parseDouble(in.readLine());
        return rawDouble;
    }

    /**
     * reads long from user
     *
     * @return long if correct
     * @throws IOException throws
     */
    public long readLongFromUser() throws IOException {
        long rawLong;
        while (true) {
            try {
                rawLong = Long.parseLong(in.readLine());
                if (rawLong >= 1000000000) {
                    return rawLong;
                }
            } catch (NumberFormatException e) {

            }
            out.println("Please enter a number in proper format");
        }
    }

    /**
     * returns a card number if entered correctly
     *
     * @return card number
     * @throws IOException throws
     */
    public String readCardNumber() throws IOException {
        String rawCard;
        while (true) {
            try {
                rawCard = in.readLine();
                if (rawCard.length() == 16) {
                    return rawCard;
                }
            } catch (NumberFormatException e) {

            }
            out.println("Please enter a 16 digit card number");
        }
    }

    /**
     * reads a normal long
     *
     * @return long
     * @throws IOException throws
     */
    public long readLongNormFromUser() throws IOException {
        long rawLong;
        while (true) {
            try {
                rawLong = Long.parseLong(in.readLine());
                return rawLong;
            } catch (NumberFormatException e) {

            }
            out.println("Please enter a number in proper format");
        }
    }

    public int readYearFromUser() throws IOException {
        int year;
        int minYear = 1000;
        while(true) {
            try {
                year = Integer.parseInt(in.readLine());
                if(year > minYear){
                    return year;
                }
            }
            catch(NumberFormatException ex) {

            }
            out.println("Please enter a valid 4 digit year.");
        }
    }

    public int readMonthFromUser() throws IOException {
        int month;
        int maxMonth = 12;
        int minMonth = 1;
        while(true) {
            try {
                month = Integer.parseInt(in.readLine());
                if(month <=maxMonth && month >= minMonth) {
                    return month--;
                }
            }
            catch(NumberFormatException ex) {

            }
            out.println("Please enter a valid month (1-12)");

        }
    }

    public int readDayFromUser() throws IOException {
        int day;
        int maxDay = 31;
        int minDay = 1;
        while(true) {
            try {
                day = Integer.parseInt(in.readLine());
                if(day <= maxDay && day >= minDay) {
                    return day;
                }
            }
            catch(NumberFormatException ex) {

            }
            out.println("Please enter a valid day (1-31");
        }
    }

    public int readHourFromUser() throws IOException {
        int hour;
        int maxHour = 24;
        int minHour = 1;
        while(true) {
            try {
                hour = Integer.parseInt(in.readLine());
                if(hour <= maxHour && hour >= minHour) {
                    return hour--;
                }
            }
            catch(NumberFormatException ex) {

            }
            out.println("Please enter a valid hour (1-24");
        }
    }

    public int readMinuteFromUser() throws IOException {
        int minute;
        int maxMin = 59;
        int minMin = 0;
        while(true) {
            try {
                minute = Integer.parseInt(in.readLine());
                if(minute <= maxMin && minute >= minMin) {
                    return minute;
                }
            }
            catch(NumberFormatException ex) {

            }
            out.println("Please enter a valid minute (0-59)");
        }
    }
}

