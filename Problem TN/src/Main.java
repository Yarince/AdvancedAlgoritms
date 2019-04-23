public class Main {
    static StringBuilder result;
    private static int[] preCalculatedCatalans =
            new int[]{
                    1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786, 208012, 742900, 2674440, 9694845, 35357670, 129644790, 477638700
            };

    private static int[] arr;

    public static void main(String[] args) {
        process(new Reader(System.in));
    }

    static void process(Reader reader) {
        try {
            StringBuilder b = new StringBuilder();
            int numCases = Integer.valueOf(reader.readLine());

            for (int i = 0; i < numCases; i++) {

                b.append(i + 1);
                b.append(": ");
                b.append(makeTree(reader.readLine()));
                if (i != numCases - 1) {
                    b.append("\n");
                }
                result = new StringBuilder();
            }

            System.out.println(b.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String makeTree(String input) {
        StringBuilder b = new StringBuilder();

        int numNodes = getCatalanStartNumber(Integer.valueOf(input)) - 1;
        int number = Integer.valueOf(input);

        int startNum = 0;

        for (int i = 1; i < numNodes; i++) {
            startNum += preCalculatedCatalans[i];
        }

        startNum++;

        int diff = number - startNum + 1;

        rankinverse(diff, numNodes);
        Node s = generateTree(arr, numNodes);
        inOrderRepr(s, b);

        return result.toString();
    }

    private static int getCatalanStartNumber(int num) {
        int catalan = 0;
        int curr = 0;

        while (num >= catalan) {
            catalan += preCalculatedCatalans[curr];
            curr++;
        }

        return curr;
    }

    private static Node generateTree(int[] arr, int numNodes) {
        Node root = new Node(0);
        root.setData(arr[1]);

        for (int i = 2; i < numNodes + 1; i++) {
            int data = arr[i];

            Node newNode = new Node(data);
            insertNode(root, newNode);
        }

        return root;
    }

    private static void insertNode(Node parent, Node insert) {
        if (insert.getData() < parent.getData()) {
            // Check left
            if (parent.getLeft() == null) {
                parent.setLeft(insert);
            } else {
                insertNode(parent.getLeft(), insert);
            }
        } else {
            // Check right
            if (parent.getRight() == null) {
                parent.setRight(insert);
            } else {
                insertNode(parent.getRight(), insert);
            }
        }
    }

    private static void rankinverse(int i, int n) {
        arr = new int[n + 1];
        ri(i, n, 1, 0);
    }

    private static void ri(int i, int n, int a, int s) {
        int j = 0;
        int k = 0, v = 0, h = 0, w = 0, y = 0;

        while (i > 0) {
            k = n - j - 1;
            h = preCalculatedCatalans[k];
            v = preCalculatedCatalans[j] * h;
            i = i - v;
            j = j + 1;
        }

        i = i + v;
        j = j - 1;
        arr[a] = j + 1 + s;
        y = (i - 1 - ((i - 1) / h) * h) + 1;
        w = ((i - y) / h) + 1;

        if (j > 1) {
            ri(w, j, a + 1, s);
        } else if (j == 1) {
            arr[a + 1] = s + 1;
        }

        if (k > 1) {
            ri(y, k, a + j + 1, s + j + 1);
        } else if (k == 1) {
            arr[a + j + 1] = s + j + 2;
        }
    }

    private static void inOrderRepr(Node node, StringBuilder b) {
        if (node == null) {
            return;
        }

        // Left
        if (node.hasLeft()) {
            result.append("(");
            inOrderRepr(node.getLeft(), b);
            result.append(")");
        }

        // Root
        result.append("X");

        // Right
        if (node.hasRight()) {
            result.append("(");
            inOrderRepr(node.getRight(), b);
            result.append(")");
        }
    }
}