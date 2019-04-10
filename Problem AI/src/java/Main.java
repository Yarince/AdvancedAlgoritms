import java.util.Scanner;

/**
 * Main.java
 * <p>
 * Owner Yarince Martis
 */
public class Main {

    public static void main(String[] args) {
        process(new Scanner(System.in));
    }

    private static void process(Scanner in) {
        int fragments = in.nextInt();
        for (int i = 1; i <= fragments; i++) {
            System.out.print("" + i + ": ");
            processCase(in);
        }
    }

    private static void processCase(Scanner in) {
        String next = in.next();
        int words = Integer.parseInt(next);
        String[] sentences = new String[words];
        for (int i = 0; i < words; i++) {
            sentences[i] = in.next();
        }

        new Graph(sentences).getAllTopologicalSorts();
    }
}