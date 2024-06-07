package it.langellotti.bang;

import it.kibo.fp.lib.AnsiColors;

public class Utils {
    /**
     * Print an error message and exit the program
     * 
     * @param e
     */
    public static void printErrorAndExit(Exception e) {
        e.printStackTrace();
        System.exit(1);
    }

    /**
     * Print a string with a color
     * 
     * @param s
     * @param color
     */
    public static void print(String s, AnsiColors color) {
        System.out.print(color + s + AnsiColors.RESET);
    }

    /**
     * Print a string with a color and a newline after it
     * 
     * @param s
     * @param color
     */
    public static void println(String s, AnsiColors color) {
        System.out.println(color + s + AnsiColors.RESET);
    }

    /**
     * Print a string with a color and a newline before and after it
     * 
     * @param s
     * @param color
     */
    public static void printlnIsolated(String s, AnsiColors color) {
        System.out.println("\n" + color + s + AnsiColors.RESET + "\n");
    }

    /**
     * Generate a random boolean with a certain percentage of being true
     * 
     * @param percentage
     * @return a random boolean with a certain percentage of being true
     */
    public static boolean randomBoolean(int percentage) {
        return Math.random() < percentage / 100.0;
    }

    /**
     * Generate a random integer between min and max (both included)
     * 
     * @param min
     * @param max
     * @return a random integer between min and max
     */
    public static int randomInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    /**
     * Convert a string to an instance of the class cl
     * 
     * @param <T>
     * @param str
     * @param cl
     * @return an istance of the class cl
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(String str, Class<T> cl) {
        if (str == null)
            return null;
        if (cl == String.class)
            return (T) str;
        if (cl == Character.class) // Character.valueOf(str) doen't exist
            return (T) (Character) str.charAt(0);
        if (cl == Boolean.class && str == "1") // the valueOf method returns "true".equalsIgnoreCase(str);
            return (T) Boolean.TRUE;

        try {
            return (T) cl.getMethod("valueOf", String.class).invoke(null, str);
        } catch (Exception e) {
            var message = "The class " + cl.getSimpleName() + " does not have a valueOf method";
            printErrorAndExit(new IllegalArgumentException(message));
            throw new RuntimeException("Unreachable code");
        }
    }
}
