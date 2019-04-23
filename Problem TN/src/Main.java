public class Main {
    static StringBuilder result = new StringBuilder();
    private static int[] p;
    private static final int[] B =
            new int[]{
                    1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786, 208012, 742900, 2674440, 9694845, 35357670, 129644790, 477638700
            };

    public static void main(String[] args) {
        process(new Reader(System.in));
    }

    static void process(Reader reader) {

        try {
            int numCases = reader.nextInt();

            for (int i = 1; i <= numCases; i++) {

                int treeNumber = reader.nextInt();
                result.append(i).append(": ");
                executeMethod(treeNumber);
                if (i != numCases) {
                    result.append("\n");
                }
            }

            System.out.println(result.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void executeMethod(int number) {
        int iter = 0;

        for (int catalanNumber = 0; catalanNumber <= number; ) {
            catalanNumber += B[iter];
            iter++;
        }

        int n = iter - 1;

        int startNum = 0;
        for (int i = 1; i < n; i++) {
            startNum += B[i];
        }

        startNum++;

        int i = number - startNum + 1;

        rankInverse(i, n);
        Node base = new Node(p[1]);

        for (int i1 = 2; i1 < n + 1; i1++) {
            int prev = p[i1];

            Node nextNode = new Node(prev);
            addNewNode(base, nextNode);
        }

        getOrderRepresentation(base);
    }

    private static void addNewNode(Node root, Node newNode) {
        if (newNode.getValue() > root.getValue()) {
            if (root.getRight() == null) {
                root.setRight(newNode);
            } else {
                addNewNode(root.getRight(), newNode);
            }
        } else {
            if (root.getLeft() == null) {
                root.setLeft(newNode);
            } else {
                addNewNode(root.getLeft(), newNode);
            }
        }
    }

    private static void rankInverse(int i, int n) {
        p = new int[n + 1];
        ri(i, n, 1, 0);
    }

    private static void ri(int i, int n, int a, int s) {
        int j = 0;
        int k = 0;
        int v = 0;
        int h = 0;
        int w, y;

        while (i > 0) {
            k = n - j - 1;
            h = B[k];
            v = B[j] * h;
            i = i - v;
            j = j + 1;
        }

        i = i + v;
        j = j - 1;
        p[a] = j + 1 + s;
        y = (i - 1 - ((i - 1) / h) * h) + 1;
        w = ((i - y) / h) + 1;

        if (j > 1) ri(w, j, a + 1, s);
        else if (j == 1) p[a + 1] = s + 1;

        if (k > 1)
            ri(y, k, a + j + 1, s + j + 1);
        else if (k == 1)
            p[a + j + 1] = s + j + 2;
    }

    private static void getOrderRepresentation(Node node) {
        if (node == null) {
            return;
        }

        // left node
        if (node.hasLeft()) {
            result.append("(");
            getOrderRepresentation(node.getLeft());
            result.append(")");
        }

        // Root node
        result.append("X");

        // right node
        if (node.hasRight()) {
            result.append("(");
            getOrderRepresentation(node.getRight());
            result.append(")");
        }
    }

    static class Node {
        private Node right;
        private Node left;
        private int value;

        Node(int value) {
            this.value = value;
        }

        Node getRight() {
            return right;
        }

        Node getLeft() {
            return left;
        }

        void setRight(Node right) {
            this.right = right;
        }

        void setLeft(Node left) {
            this.left = left;
        }

        boolean hasLeft() {
            return this.left != null;
        }

        boolean hasRight() {
            return this.right != null;
        }

        int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }
    }

}