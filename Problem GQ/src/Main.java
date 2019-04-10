import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        process(new Scanner(System.in));
    }

    static void process(Scanner in) {
        int fragments = in.nextInt();
        for (int i = 1; i <= fragments; i++) {
            System.out.print("" + i + ": ");
            processCase(in);
        }
    }

    private static void processCase(Scanner in) {
        int vertCount = in.nextInt();
        int edgeCount = in.nextInt();

        int[] weights = new int[vertCount];
        int[][] edges = new int[edgeCount][2];

        // Grab all weights
        Arrays.setAll(weights, i -> in.nextInt());
        for (int i = 0; i < edges.length; i++) {
            edges[i][0] = in.nextInt() - 1;
            edges[i][1] = in.nextInt() - 1;
        }

        ArrayList<Integer[]> commands = new ArrayList<>();

        //noinspection UnusedAssignment
        String command = in.nextLine(); // Skip empty line
        command = in.nextLine();
        while (!command.equals("3")) {

            String[] stringCommandArray = command.split(" ");

            Integer[] commandArray = new Integer[stringCommandArray.length];
            for (int i = 0; i < stringCommandArray.length; i++) {

                int parseInt = Integer.parseInt(stringCommandArray[i]);
                if (i == 1 || (i == 2 && commandArray[0] == 2)) parseInt--;
                commandArray[i] = parseInt;
            }

            commands.add(commandArray);

            command = in.nextLine();
        }

        Graph graph = new Graph(weights, edges, commands);
        graph.executeCommands();

        graph.calculateAverage();
    }
}
