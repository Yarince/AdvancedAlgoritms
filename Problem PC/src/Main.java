import java.util.Scanner;

public class Main {

    static final StringBuilder result = new StringBuilder();
    private static Manager manager;


    public static void main(String[] args) {
        process(new Scanner(System.in));
    }

    static void process(Scanner in) {
        final int fragments = in.nextInt();
        for (int i = 1; i <= fragments; i++) {
            processCase(in, i);
        }
        System.out.print(result.toString());
    }

    private static void processCase(Scanner in, int fragmentNumber) {

        int delegates = in.nextInt();
        int numberOfOperations = in.nextInt();

        int[][] operations = new int[numberOfOperations][3];

        for (int i = 0; i < numberOfOperations; i++) {
            operations[i][0] = Integer.valueOf(in.next());
            operations[i][1] = Integer.valueOf(in.next());
            operations[i][2] = Integer.valueOf(in.next());
        }

        result.append(fragmentNumber).append(": ").append("\n");

        manager = new Manager();

        handleDelegates(delegates, operations);

    }

    // TODO Broken :)
    private static void handleDelegates(int delegates, int[][] operations) {

        // TODO Optimize
//        Map<List<Integer>, Boolean> rules = new HashMap<>();

        for (int[] operationSet : operations) {
            int operation = operationSet[0];
            int d1 = operationSet[1];
            int d2 = operationSet[2];
            Pair pair = new Pair(d1, d2);
            switch (operation) {
                case 1:
                    if (manager.isContradicting(pair, true)) {
                        result.append(-1).append("\n");
                    }
                    manager.addPair(pair, true);
                    break;
                case 2:
                    if (manager.isContradicting(pair, false)) {
                        result.append(-1).append("\n");
                    }
                    manager.addPair(pair, false);
                    break;
                case 3:
                    if (manager.isUnknown(pair)) // It has not been logged yet so  it is unknown.
                        result.append(-1);
                    else if (manager.isInSameTeam(pair))
                        result.append(1);
                    else result.append(0);

                    result.append("\n");
                    break;
                case 4:
                    if (manager.isUnknown(pair)) // It has not been logged yet so  it is unknown.
                        result.append(-1);
                    else if (!manager.isInSameTeam(pair))
                        result.append(1);
                    else result.append(0);

                    result.append("\n");
                    break;
            }
        }
    }
}
