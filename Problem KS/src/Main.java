import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    private static final int MAX_INT = 2147483647;

    public static void main(String[] args) {
        process(new Scanner(System.in));
    }

    static final StringBuilder result = new StringBuilder();

    static void process(Scanner in) {
        int fragments = in.nextInt();
        for (int i = 1; i <= fragments; i++) {
            processCase(in, i);
        }
        System.out.print(result.toString());
    }

    private static void processCase(Scanner in, int fragmentNumber) {
        BigInteger startCoordinate = new BigInteger(in.next());
        char[] commands = in.next().toCharArray();

        result.append(fragmentNumber)
                .append(": ")
                .append(executeCommands(startCoordinate, commands))
                .append('\n');
    }

    private static String executeCommands(BigInteger startCoordinate, char[] commands) {

        String temp = startCoordinate.toString();
        int[] currentCoordinate = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++) {
            currentCoordinate[i] = temp.charAt(i) - '0';
        }
        for (char command : commands) {
            currentCoordinate = changeCoordinate(currentCoordinate, command, currentCoordinate.length);

            if (currentCoordinate.length == 0)
                return "OUT";
        }

        StringBuilder result = new StringBuilder();
        for (int i : currentCoordinate) {
            result.append(i); //add all the ints to a string
        }
        return result.toString();
    }

    private static int[] changeCoordinate(int[] currentCoordinate, char command, int index) {
        index--;

        if (index > currentCoordinate.length || index < 0)
            return new int[0];

        int change = getChange(currentCoordinate[index], command);

        if (change == MAX_INT)
            currentCoordinate = changeCoordinate(currentCoordinate, command, index);
        else {

            currentCoordinate[index] += change;
            for (int i = index + 1; i < currentCoordinate.length; i++) {
                int coordinate = currentCoordinate[i];

                if (command == 'U') change = getChange(coordinate, 'D');
                if (command == 'D') change = getChange(coordinate, 'U');
                if (command == 'R') change = getChange(coordinate, 'L');
                if (command == 'L') change = getChange(coordinate, 'R');
                    currentCoordinate[i] += change;
            }
        }

        return currentCoordinate;
    }

    private static int getChange(int coordinate, char change) {

        if ((coordinate == 1 && change == 'U')
                || (coordinate == 1 && change == 'L')
                || (coordinate == 2 && change == 'U')
                || (coordinate == 2 && change == 'R')
                || (coordinate == 3 && change == 'D')
                || (coordinate == 3 && change == 'R')
                || (coordinate == 4 && change == 'D')
                || (coordinate == 4 && change == 'L')) return MAX_INT;

        if (coordinate == 1 && change == 'D') return +3;
        if (coordinate == 2 && change == 'D') return +1;
        if (coordinate == 4 && change == 'U') return -3;
        if (coordinate == 3 && change == 'U') return -1;
        if (coordinate == 1 && change == 'R') return +1;
        if (coordinate == 4 && change == 'R') return -1;
        if (coordinate == 2 && change == 'L') return -1;
        if (coordinate == 3 && change == 'L') return +1;


        return Math.abs(MAX_INT);
    }
}