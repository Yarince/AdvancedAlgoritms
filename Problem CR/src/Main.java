import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static final StringBuilder RESULT = new StringBuilder();
    private static ArrayList<int[]> EDGES = new ArrayList<>();
    //    private static ArrayList<Pair> EDGES = new ArrayList<>();
//    private static int[] moleculeArray;
    private static HashMap<Integer, ArrayList<Triple>> EveryTriple = new HashMap<>();

    public static void main(String[] args) {
        process(new Scanner(System.in));
    }

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

//        moleculeArray = new int[molecules];
//        for (int i = 1; i <= molecules; i++) {
//            moleculeArray[i - 1] = i;
//        }

        if (!EveryTriple.containsKey(molecules-1)) {
            int[] tempArray = new int[molecules];
            for (int j = 1; j <= molecules; j++) {
                tempArray[j - 1] = j;
            }
            EveryTriple.put(molecules-1, getTriples(tempArray, new Triple(), 0, 0));
        }

        EDGES = new ArrayList<>();
        for (int i = 0; i < positiveEdges; i++) {
            EDGES.add(new int[]{in.nextInt(), in.nextInt()});
//            EDGES.add(new Pair(in.nextInt(), in.nextInt()));

        }

        RESULT.append(fragmentNumber)
                .append(": ")
                .append(executeCommands(molecules - 1))
                .append('\n');
    }

    private static int executeCommands(int molecules) {

        ArrayList<Triple> allTriples = EveryTriple.get(molecules);//getTriples(moleculeArray, new Triple(), 0, 0);
        int biggestGroupSize = 0;
        ArrayList<Triple> balancedTriples = new ArrayList<>();


        for (Triple triple : allTriples) {
            boolean balanced = isBalanced(triple);
            if (balanced)
                balancedTriples.add(triple);
        }

        ArrayList<Integer> group;
//        ArrayList<HashSet<Integer>> badGroups = new ArrayList<>();

        for (Triple triple : balancedTriples) {
            group = new ArrayList<>();
            group.add(triple.left);
            group.add(triple.middle);
            group.add(triple.right);

            int nextNumber = 1;

            while (nextNumber <= molecules) {
                boolean skip = false;

                // if the group doesn't contain the root node it's bad
                if (!group.contains(1) && nextNumber != 1) {
                    break;
                }

                // Get a number that is not equal to the numbers currently in the triple
                // Or the number is in the group less than there are combinations
                while (group.contains(nextNumber)) {
                    nextNumber++;
                    if (nextNumber > molecules) skip = true;
                }
                if (skip) continue;

                group.add(nextNumber);

//                 If the group is already known for it's bad qualities it should not be selected
//                for (HashSet<Integer> badGroup : badGroups) {
//                    if (skip) break;
//                    if (badGroup.containsAll(group) && group.containsAll(badGroup)) {
//                        group.remove(group.size() - 1);
//                        nextNumber++;
//                        skip = true;
//                    }
//                }
//                if (skip) continue;

                ArrayList<Triple> newTriples = getTriples(group, new Triple(), 0, 0);


                if (!balancedTriples.containsAll(newTriples)) {
//                    badGroups.add(new HashSet<>(group));
                    group.remove(group.size() - 1);
                    nextNumber++;
                }
            }

            if (group.size() > biggestGroupSize)
                biggestGroupSize = group.size();
        }

        return biggestGroupSize;
    }

    /**
     * @param triple part of chemical reaction
     * @return true if the triple is balanced else false
     */
    private static boolean isBalanced(Triple triple) {
//        Pair ab = new Pair(triple.get(0), triple.get(1));
//        Pair ac = new Pair(triple.get(0), triple.get(2));
//        Pair cb = new Pair(triple.get(2), triple.get(1));
//
//        return (EDGES.contains(ab) ^ EDGES.contains(ac) ^ EDGES.contains(cb)) ||
//                (EDGES.contains(ab) && EDGES.contains(ac) && EDGES.contains(cb));

        int[] ab = new int[]{triple.get(0), triple.get(1)};
        int[] ac = new int[]{triple.get(0), triple.get(2)};
        int[] bc = new int[]{triple.get(2), triple.get(1)};

        if (arrayContains(EDGES, ab) ^ arrayContains(EDGES, ac) ^ arrayContains(EDGES, bc))
            return true;
        else if (arrayContains(EDGES, ab) && arrayContains(EDGES, ac) && arrayContains(EDGES, bc))
            return true;

        else return false;
    }

    private static boolean arrayContains(ArrayList<int[]> array, int[] value) {
        boolean contains = false;
        for (int[] ints : array) {
            if (contains) break;
            if (ints[0] == value[1] && ints[1] == value[0] ||
                    ints[0] == value[0] && ints[1] == value[1])
                contains = true;
        }
        return contains;
    }

    /**
     * Source: https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
     */
    private static ArrayList<Triple> getTriples(ArrayList<Integer> arr, Triple data, int start, int index) {
        int end = arr.size() - 1;
        int r = 3;
        ArrayList<Triple> permutations = new ArrayList<>();
        // Current combination is ready to be printed, print it
        if (index == r) {
            permutations.add(data);
            return permutations;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data = new Triple(data, index, arr.get(i));
            permutations.addAll(getTriples(arr, data, i + 1, index + 1));
        }
        return permutations;
    }

    private static ArrayList<Triple> getTriples(int[] arr, Triple data, int start, int index) {
        int end = arr.length - 1;
        int r = 3;
        ArrayList<Triple> permutations = new ArrayList<>();
        // Current combination is ready to be printed, print it
        if (index == r) {
            permutations.add(data);
            return permutations;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data = new Triple(data, index, arr[i]);
            permutations.addAll(getTriples(arr, data, i + 1, index + 1));
        }
        return permutations;
    }

    final static class Pair {

        final int left;
        final int right;


        Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return left == pair.left && right == pair.right ||
                    left == pair.right && right == pair.left;
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

    final static class Triple {

        final int left;
        final int right;
        final int middle;

        Triple() {
            left = 0;
            right = 0;
            middle = 0;
        }

        Triple(Triple data, int index, int value) {
            switch (index) {
                case 0:
                    right = data.right;
                    middle = data.middle;
                    left = value;
                    break;
                case 1:
                    right = data.right;
                    left = data.left;
                    middle = value;
                    break;
                case 2:
                    left = data.left;
                    middle = data.middle;
                    right = value;
                    break;
                default:
                    left = 0;
                    right = 0;
                    middle = 0;
                    RESULT.append("broken");
                    break;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Triple triple = (Triple) o;
            return left == triple.left && right == triple.right && middle == triple.middle ||
                    left == triple.right && right == triple.left && middle == triple.middle ||
                    left == triple.left && right == triple.middle && middle == triple.right ||
                    left == triple.middle && right == triple.left && middle == triple.right ||
                    left == triple.middle && right == triple.right && middle == triple.left ||
                    left == triple.right && right == triple.middle && middle == triple.left;
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, middle, right);
        }

        @Override
        public String toString() {
            return left + " - " + middle + " - " + right;
        }

        int get(int i) {
            switch (i) {
                case 0:
                    return left;
                case 1:
                    return middle;
                case 2:
                    return right;
                default:
                    return middle; // finger
            }
        }
    }
}