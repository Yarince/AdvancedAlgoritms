import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final ArrayList<Pair<Integer>> EDGES = new ArrayList<>();
    private static int[] moleculeArray;

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
        moleculeArray = new int[molecules];
        for (int i = 0; i < molecules; i++) {
            moleculeArray[i] = i + 1;
        }
        for (int i = 0; i < positiveEdges; i++) {
            EDGES.add(new Pair<>(in.nextInt(), in.nextInt()));
        }

        RESULT.append(fragmentNumber)
                .append(": ")
                .append(executeCommands(molecules))
                .append('\n');
    }

    static Tree tree;

    private static int executeCommands(int molecules) {

        ArrayList<int[]> allTriples = getTriples(moleculeArray, new int[molecules], 0, 0);

        ArrayList<int[]> groups = new ArrayList<>();
        for (int[] triple : allTriples) {
            boolean balanced = isBalanced(triple);
            if (balanced) {
                groups.add(triple);
                groups.addAll(createGroup(groups, new ArrayList<>(), 0));
            }
        }
        return groups.size();
    }

    private static ArrayList<int[]> createGroup(ArrayList<int[]> balancedTriples, ArrayList<Integer> group, int index) {
//        int[] startTriple = balancedTriples.get(index);
        // loop one by one through all possibilities



        // TODO Find out what the next step is. Need to check if every combination in the group is
        //  still balanced or the last added should be removed and replaced by the next molecule available in the array.
        //  This also changes the for loop with molecule array.
        //  First do the is balanced check before actually adding it to the group or something.
//        boolean balanced = isBalanced(group[0], group[1], group[2]);
        // If the correct triple is balanced
//        if (balanced) {
            for (int extraMolecule : moleculeArray) {
                // Check if extra molecule isn't already in current triple
                if (group.contains(extraMolecule))
                    continue;
                // add extraMolecule to the triple
                group.add(extraMolecule);

                // Get new triples
                ArrayList<int[]> triples = getTriples(group, new int[group.size()], 0, 0);
                boolean balanced = true;
                for (int[] triple : triples) {
                    if (!balancedTriples.contains(triple) && balanced) {
                        balanced = isBalanced(triple);
                    }
                }
                if (balanced){



                }

                for (int[] x : triples) {
                    if (!balancedTriples.contains(x))
                        balancedTriples.add(x);
                }

            }
            // Recursively try to create the biggest group of balanced triples
            if (index < balancedTriples.size())
                return createGroup(balancedTriples, new ArrayList<>(), index + 1);
//        } else {
//            balancedTriples.remove(group);
//        }

        return balancedTriples;
    }


    private static boolean isBalanced(int[] triple) {
        Pair<Integer> ab = new Pair<>(triple[0], triple[1]);
        Pair<Integer> ac = new Pair<>(triple[0], triple[2]);
        Pair<Integer> bc = new Pair<>(triple[1], triple[2]);
        if (EDGES.contains(ab) ^ EDGES.contains(ac) ^ EDGES.contains(bc))
            return true;
        else if (EDGES.contains(ab) && EDGES.contains(bc) && EDGES.contains(ac))
            return true;

        else return false;
    }

    /**
     * Source: https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
     */
    private static ArrayList<int[]> getTriples(ArrayList<Integer> arr, int[] data, int start, int index) {
        int end = arr.size() - 1;
        int r = 3;
        ArrayList<int[]> permutations = new ArrayList<>();
        // Current combination is ready to be printed, print it
        if (index == r) {
            int[] permutation = new int[r];
            System.arraycopy(data, 0, permutation, 0, r);
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

        public Pair(T left, T right) {
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