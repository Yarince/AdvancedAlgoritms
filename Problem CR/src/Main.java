import java.util.*;

public class Main {

    private static ArrayList<Pair<Integer>> EDGES = new ArrayList<>();
    private static ArrayList<Integer> moleculeArray;

    public static void main(String[] args) {
        process(new Scanner(System.in));
    }

    static final StringBuilder RESULT = new StringBuilder();

    static void process(Scanner in) {
        int fragments = in.nextInt();
        for (int i = 1; i <= fragments; i++) {
            processCase(in, i);
        }
        System.out.print(RESULT.toString());
    }

    private static void processCase(Scanner in, int fragmentNumber) {
        int molecules = in.nextInt();
        int positiveEdges = in.nextInt();
        moleculeArray = new ArrayList<>();
        for (int i = 1; i <= molecules; i++) {
            moleculeArray.add(i);
        }
        for (int i = 0; i < positiveEdges; i++) {
            EDGES.add(new Pair<>(in.nextInt(), in.nextInt()));
        }

        RESULT.append(fragmentNumber)
                .append(": ")
                .append(executeCommands())
                .append('\n');
    }

    private static int executeCommands() {

        ArrayList<List<Integer>> allTriples = getTriples(moleculeArray, new int[3], 0, 0);
        int biggestGroupSize = 0;
        ArrayList<List<Integer>> balancedTriples = new ArrayList<>();


        for (List<Integer> triple : allTriples) {
            boolean balanced = isBalanced(triple);
            if (balanced)
                balancedTriples.add(triple);
        }

        int[] frequency = new int[moleculeArray.size()];

        for (List<Integer> triple : balancedTriples) {
            frequency[triple.get(0) - 1]++;
            frequency[triple.get(1) - 1]++;
            frequency[triple.get(2) - 1]++;
        }

        int groupIndex = 0;
        ArrayList<Integer> group;
        ArrayList<HashSet<Integer>> badGroups = new ArrayList<>();

        for (List<Integer> triple : balancedTriples) {
            group = new ArrayList<>(triple);

            int nextNumber = 1;

            while (nextNumber <= moleculeArray.size()) {
                // Get a number that is not equal to the numbers currently in the triple
                // Or the number is in the group less than there are combinations
                if (!group.contains(1)) {
                    badGroups.add(new HashSet<>(group));
                    break;
                }
                if (badGroups.contains(new HashSet<>(group)))
                    break;

                while ((group.contains(nextNumber) || frequency[nextNumber - 1] < 4)) {
                    nextNumber++;
                    if (nextNumber > moleculeArray.size()) break;
                }
                if (nextNumber > moleculeArray.size()) break;

                group.add(nextNumber);
                ArrayList<List<Integer>> newTriples = getTriples(group, new int[3], 0, 0);


                if (!balancedTriples.containsAll(newTriples)) {
                    badGroups.add(new HashSet<>(group));
                    group.remove(group.size() - 1);
                    nextNumber++;
                }
            }

            if (group.size() > biggestGroupSize)
                biggestGroupSize = group.size();
        }

        EDGES = new ArrayList<>();
        moleculeArray = new ArrayList<>();
        return biggestGroupSize;
    }

    private static boolean isBalanced(List<Integer> triple) {
        Pair<Integer> ab = new Pair<>(triple.get(0), triple.get(1));
        Pair<Integer> ac = new Pair<>(triple.get(0), triple.get(2));
        Pair<Integer> bc = new Pair<>(triple.get(1), triple.get(2));
        if (EDGES.contains(ab) ^ EDGES.contains(ac) ^ EDGES.contains(bc))
            return true;
        else if (EDGES.contains(ab) && EDGES.contains(bc) && EDGES.contains(ac))
            return true;

        else return false;
    }

    /**
     * Source: https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
     */
    private static ArrayList<List<Integer>> getTriples(ArrayList<Integer> arr, int[] data, int start, int index) {
        int end = arr.size() - 1;
        int r = 3;
        ArrayList<List<Integer>> permutations = new ArrayList<>();
        // Current combination is ready to be printed, print it
        if (index == r) {
            List<Integer> permutation = new ArrayList<>();
            for (int i = 0; i < r; i++) {
                permutation.add(data[i]);
            }
            // NOTE this fixes and breaks everything
            Collections.sort(permutation);
            permutations.add(permutation);
            return permutations;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data[index] = arr.get(i);
            permutations.addAll(getTriples(arr, data, i + 1, index + 1));
        }
        return permutations;
    }

    final static class Pair<T> {

        final T left;
        final T right;

        Pair(T left, T right) {
            if (left == null || right == null) {
                throw new IllegalArgumentException("left and right must be non-null!");
            }
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?> pair = (Pair<?>) o;
            return (left.equals(pair.left) && right.equals(pair.right)) ||
                    (left.equals(pair.right) && right.equals(pair.left));
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

        @Override
        public String toString() {
            return left + " - " + right;
        }
    }
}