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

        return "";
    }
}