import java.util.ArrayList;
import java.util.Scanner;

public class Main {
//
    public static void main(String args[]) {
        ArrayList<Node> totalTreesFrom1toN = constructTrees(1, 3);
        /* Printing preorder traversal of all constructed BSTs */
        System.out.println("Preorder traversals of all constructed BSTs are ");
        for (int i = 0; i < totalTreesFrom1toN.size(); i++) {
            preorder(totalTreesFrom1toN.get(i));
//            System.out.println(result);
            System.out.println();
        }
    }

//    public static void main(String[] args) {
//        process(new Scanner(System.in));
//    }

    static final StringBuilder result = new StringBuilder();

    static void process(Scanner in) {
        int fragments = in.nextInt();
        for (int i = 1; i <= fragments; i++) {
            processCase(in, i);
        }
        System.out.print(result.toString());
    }

    private static void processCase(Scanner in, int fragmentNumber) {
        long startCoordinate = in.nextLong();

        result.append(fragmentNumber)
                .append(": ")
                .append(executeCommands(startCoordinate))
                .append('\n');
    }

    private static String executeCommands(long treeNumber) {
        ArrayList<Node> totalTreesFrom1toN = constructTrees(1, treeNumber);
        for (Node node : totalTreesFrom1toN) {
            preorder(node);
        }
        return "";
    }

    // A Java prgroam to contrcut all unique BSTs for keys from 1 to n


    // function for constructing trees
    private static ArrayList<Node> constructTrees(long start, long end) {
        ArrayList<Node> list = new ArrayList<>();
		/* if start > end then subtree will be empty so returning NULL
			in the list */
        if (start > end) {
            list.add(null);
            return list;
        }

		/* iterating through all values from start to end for constructing\
			left and right subtree recursively */
        for (long i = start; i <= end; i++) {
            /* constructing left subtree */
            ArrayList<Node> leftSubtree = constructTrees(start, i - 1);

            /* constructing right subtree */
            ArrayList<Node> rightSubtree = constructTrees(i + 1, end);

			/* now looping through all left and right subtrees and connecting
				them to ith root below */
            for (int j = 0; j < leftSubtree.size(); j++) {
                Node left = leftSubtree.get(j);
                for (int k = 0; k < rightSubtree.size(); k++) {
                    Node right = rightSubtree.get(k);
                    Node node = new Node(i);     // making value i as root
                    node.left = left;             // connect left subtree
                    node.right = right;         // connect right subtree
                    list.add(node);             // add this tree to list
                }
            }
        }
        return list;
    }

    // A utility function to do preorder traversal of BST
//    static void preorder(Node root) {
//        if (root != null) {
//            result.append(root.key + " ");
//            preorder(root.left);
//            preorder(root.right);
//        }
//    }

    static void preorder(Node root)
    {
        if (root != null)
        {
            System.out.print(root.key+" ") ;
            preorder(root.left);
            preorder(root.right);
        }
    }

}

// node structure
class Node {
    long key;
    Node left, right;

    Node(long data) {
        this.key = data;
        left = right = null;
    }

}