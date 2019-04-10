import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A Java program to order of
 * characters in an alien language
 * <p>
 * Partial source by Harikrishnan Rajan
 * <p>
 * Owner Yarince Martis
 */

class Graph {

    // An array representing the graph as an adjacency list
    private final ArrayList<Node> nodes;
    private int counter;
    private String solution;

    private class Node {
        int inDegree;
        char data;
        boolean visited;
        List<Node> neighbours;

        Node(char data) {
            this.data = data;
            this.neighbours = new ArrayList<>();
            this.inDegree = 0;
            this.visited = false;
        }

        void addNeighbours(Node neighbour)  {
            neighbour.inDegree++;
            this.neighbours.add(neighbour);
        }

        public String toString() {
            return "" + data;
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() == Character.class && this.data == (char) o) return true;
            if (this == o) return true;
            if (getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return data == node.data;
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }

    Graph(String[] sentences) {
        String uniqueChars = getUniqueChars(sentences);

        nodes = new ArrayList<>(uniqueChars.length());
        if (sentences.length > 1) {
            for (int row = 0, nextRow = 1; nextRow < sentences.length; row++, nextRow++) {
                String firstSentence = sentences[row];
                String secondSentence = sentences[nextRow];
                boolean skip = false;
                for (int depth = 0; depth < firstSentence.length() && !skip; depth++) {
                    char c1 = firstSentence.charAt(depth);
                    char c2 = secondSentence.charAt(depth);
                    if (c1 != c2) {
                        this.addEdge(c1, c2);
                        skip = true;
                    }
                }
            }
            // Check if not all unique chars are in the graph
            if (nodes.size() < uniqueChars.length())
                this.addLooseCases(uniqueChars);
        } else
            for (char c : sentences[0].toCharArray()) {
                nodes.add(new Node(c));
            }
    }

    // function to add an edge to graph
    private Node add(char vertex) {

        Node foundNode = getNodeInGraph(vertex);
        if (foundNode != null) {
            return foundNode;
        }

        Node newNode = new Node(vertex);

        nodes.add(newNode);
        return newNode;
    }

    private void addEdge(char from, char to) {
        Node fromNode = add(from);
        Node toNode = add(to);

        if (edgeExists(fromNode, toNode))
            return;

        fromNode.addNeighbours(toNode);
    }

    private void allTopologicalSortsUtil(ArrayList<Character> stack) {

        // To indicate whether all topological are found
        // or not
        boolean flag = false;

        for (Node node : nodes) {
            List<Node> neighbours = node.neighbours;

            // If inDegree is 0 and not yet visited then
            // only choose that vertex
            if (!node.visited && node.inDegree == 0) {

                // including in result
                node.visited = true;
                stack.add(node.data);
                for (Node neighbour : neighbours) {
                    neighbour.inDegree--;
                }

                allTopologicalSortsUtil(stack);

                // resetting visited, res and inDegree for
                // backtracking
                node.visited = false;
                stack.remove(stack.size() - 1);
                for (Node neighbour : neighbours) {
                    neighbour.inDegree++;
                }

                flag = true;
            }
        }
        // All vertices are visited when we reach here.
        // So the solutions are counted and saved here
        if (!flag) {
            if (counter == 0) {
                StringBuilder solutionBuilder = new StringBuilder();
                for (Character c : stack) {
                    solutionBuilder.append(c);
                }
                solution = solutionBuilder.toString();
            }
            counter++;
        }
    }

    // The function does all Topological Sort.
    // It uses recursive getAllTopologicalSorts()
    void getAllTopologicalSorts() {

        ArrayList<Character> stack = new ArrayList<>();

        allTopologicalSortsUtil(stack);

        if (counter > 1)
            System.out.println(counter);
        else
            System.out.println(solution);
    }

    private Boolean isInGraph(char newChar) {
        return getNodeInGraph(newChar) != null;
    }

    private Node getNodeInGraph(char vertex) {
        List<Node> nodeStream = nodes.stream().flatMap(n -> n.neighbours.stream()).collect(Collectors.toList());
        nodeStream.addAll(nodes);
        return nodeStream.stream().filter(n -> n.equals(vertex)).findAny().orElse(null);
    }

    private boolean edgeExists(Node fromNode, Node toNode) {
        return fromNode.neighbours.stream().anyMatch(c -> c == toNode);
    }

    private void addLooseCases(String uniqueChars) {
        for (int i = 0; i < uniqueChars.length(); i++) {
            char character = uniqueChars.charAt(i);
            if (!isInGraph(character)) {
                nodes.add(new Node(character));
            }
        }
    }

    private static String getUniqueChars(String[] char2dList) {
        StringBuilder temp = new StringBuilder();
        for (String sentence : char2dList) {
            for (int i = 0; i < sentence.length(); i++) {
                char current = sentence.charAt(i);
                if (temp.toString().indexOf(current) == -1) {
                    temp.append(current);
                }
            }
        }
        return temp.toString();
    }
}
