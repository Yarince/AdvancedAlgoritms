import java.util.LinkedList;
import java.util.Queue;

public class Tree {
    private Node root;


    public Tree(int[] rootData) {
        root = new Node(rootData);
    }

    public class Node {
        private int[] data;
        private Node parent;
        private Node left;
        private Node right;

        public Node(int[] value) {
            data = value;
        }
    }

    public void add(int value) {
        root = addRecursive(root, new int[]{value});
    }

    private Node addRecursive(Node current, int[] value) {

        if (current == null) {
            return new Node(value);
        }

        current.left = addRecursive(current.left, current.data);

        int[] concatenate = combine(current.data, value);
        current.right = addRecursive(current.right, concatenate);

        return current;
    }

    static int[] combine(int[] a, int[] b) {
        int length = a.length + b.length;
        int[] result = new int[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getSize() {
        return getSizeRecursive(root);
    }

    private int getSizeRecursive(Node current) {
        return current == null ? 0 : getSizeRecursive(current.left) + 1 + getSizeRecursive(current.right);
    }

    public boolean containsNode(int[] value) {
        return containsNodeRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node current, int[] value) {
        if (current == null) {
            return false;
        }

        if (value == current.data) {
            return true;
        }

        return value.length < current.data.length
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }

    public void delete(int[] value) {
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node current, int[] value) {
        if (current == null) {
            return null;
        }

        if (value == current.data) {
            // Case 1: no children
            if (current.left == null && current.right == null) {
                return null;
            }

            // Case 2: only 1 child
            if (current.right == null) {
                return current.left;
            }

            if (current.left == null) {
                return current.right;
            }

            // Case 3: 2 children
            int[] smallestValue = findSmallestValue(current.right);
            current.data = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue);
            return current;
        }
        if (value.length < current.data.length) {
            current.left = deleteRecursive(current.left, value);
            return current;
        }

        current.right = deleteRecursive(current.right, value);
        return current;
    }

    private int[] findSmallestValue(Node root) {
        return root.left == null ? root.data : findSmallestValue(root.left);
    }

    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(" " + node.data);
            traverseInOrder(node.right);
        }
    }

    public void traversePreOrder(Node node) {
        if (node != null) {
            System.out.print(" " + node.data);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }

    public void traversePostOrder(Node node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.print(" " + node.data);
        }
    }

    public void traverseLevelOrder() {
        if (root == null) {
            return;
        }

        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {

            Node node = nodes.remove();

            System.out.print(" " + node.data);

            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.left != null) {
                nodes.add(node.right);
            }
        }
    }
}