import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static final StringBuilder result = new StringBuilder();

    private final static int MAX_FACTORIALS = 17;
    private final static long[] factorials = new long[MAX_FACTORIALS];

    // utility for calculating factorial
    private static void preCalculateFactorials() {
        factorials[0] = 1;
        for (int i = 1; i < MAX_FACTORIALS; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
    }

    public static void main(String[] args) {
        process(new Scanner(System.in));
    }

    static void process(Scanner in) {
        preCalculateFactorials();
        final int fragments = in.nextInt();
        for (int i = 1; i <= fragments; i++) {
            processCase(in, i);
        }
        System.out.print(result.toString());
    }

    private static void processCase(Scanner in, int i) {

        String fragment = in.next();
        long kElement = Long.parseLong(in.next()) - 1;

        char[] chars = fragment.toCharArray();
        // NOTE Maybe optimize this
        Arrays.sort(chars);

        result.append(i)
                .append(": ")
                .append(permUnRank(chars, kElement, new char[0]))
                .append('\n');
    }

    private static char[] permUnRank(char[] base, long kElement, char[] head) {
        final int length = base.length;
        if (length < 2) {
            return concatenate(head, base);
        }
        long total = 0;

        for (int i = 0; i < length; i++) {
            char c = base[i];
            if (i < 1 || c != base[i - 1]) {
                final char[] newBase = concatenate(Arrays.copyOfRange(base, 0, i), Arrays.copyOfRange(base, i + 1, length));
//                char[] newBase = concatenate(throwOutArrayPlaces(base, 0, i), throwOutArrayPlaces(base, i + 1, base.length));
                final long newTotal = total + permCount(newBase);
                if (newTotal > kElement)
                    return permUnRank(newBase, kElement - total, concatenate(head, c));
                total = newTotal;
            }
        }

        return "This was not supposed to happen :/ I'm sad now".toCharArray();
    }

    private static long permCount(char[] base) {
        long n = factorials[base.length];
        int[] letters = countLetters(base);
        for (int i : letters) {
            n /= factorials[i];
        }
        return n;
    }

    private static int[] countLetters(char[] chars) {
        final int[] timesFound = new int[26];
        for (char c : chars)
            timesFound[c - 'A']++;

        return timesFound;
    }

    private static char[] concatenate(char[] a, char[] b) {
        final char[] newArray = new char[a.length + b.length];
        for (int i = 0; i < newArray.length; i++) {
            char c;
            if (i < a.length)
                c = a[i];
            else c = b[i - a.length];
            newArray[i] = c;
        }
        return newArray;
    }

    private static char[] concatenate(char[] a, char b) {
        final char[] newArray = new char[a.length + 1];
        for (int i = 0; i < newArray.length; i++) {
            char c;
            if (i < a.length)
                c = a[i];
            else c = b;
            newArray[i] = c;
        }
        return newArray;
    }

    private static char[] throwOutArrayPlaces(char[] chars, int from, int to) {
        char[] newArray = new char[to - from];

        for (int i = 0, j = 0; i < chars.length; i++) {
            if (i >= from && i < to) {
                newArray[j] = chars[i];
                j++;
            }
        }

        return newArray;
    }
}
