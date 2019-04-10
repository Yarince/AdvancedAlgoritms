package problemAi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Java program to order of
 * characters in an alien language
 * <p>
 * Partial source by Harikrishnan Rajan
 * <p>
 * Owner Yarince Martis
 */

public class GraphInefficient {

    // An array representing the graph as an adjacency list
    private final HashMap<Character, List<Character>> nodes = new HashMap<>();
    private int counter;
    private String solution;
    private ArrayList<String> solutions = new ArrayList<>();

    public GraphInefficient(String[] sentences) {

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
//            this.addLoseCases(getUniqueChars(sentences));
        } else
            for (char c : sentences[0].toCharArray()) {
                nodes.put(c, new ArrayList<>());
            }
    }

    // function to add an edge to graph
    public void addEdge(char from, char to) {

        if (edgeExists(from, to))
            return;

        add(from);
        add(to);

        nodes.get(from).add(to);
    }

    private void add(char vertex) {
        if (nodes.containsKey(vertex))
            return;

        nodes.put(vertex, new ArrayList<>());
    }


    private void allTopologicalSortsUtil(HashMap<Character, Boolean> visited,
                                         HashMap<Character, Integer> inDegree,
                                         ArrayList<Character> stack) {

        // To indicate whether all topological are found
        // or not
        boolean flag = false;

        for (Map.Entry<Character, List<Character>> entry : nodes.entrySet()) {
            Character node = entry.getKey();
            List<Character> neighbours = entry.getValue();

            // If inDegree is 0 and not yet visited then
            // only choose that vertex
            if (!visited.get(node) && inDegree.get(node) == 0) {

                // including in result
                visited.put(node, true);
                stack.add(node);
                for (char neighbour : neighbours) {
                    inDegree.put(neighbour, inDegree.get(neighbour) - 1);
                }

                allTopologicalSortsUtil(visited, inDegree, stack);

                // resetting visited, res and inDegree for
                // backtracking
                visited.put(node, false);
                stack.remove(stack.size() - 1);
                for (char neighbour : neighbours) {
                    inDegree.put(neighbour, inDegree.get(neighbour) + 1);
                }

                flag = true;
            }
        }
        // All vertices are visited when we reach here.
        // So the solutions are counted and saved here
        if (!flag) {
            StringBuilder solution = new StringBuilder();
            for (Character c : stack) {
                solution.append(c);
            }
            solutions.add(solution.toString());
            counter++;
        }
    }

    // The function does all Topological Sort.
    // It uses recursive getAllTopologicalSorts()
    public void getAllTopologicalSorts() {

        HashMap<Character, Boolean> visited = new HashMap<>();
        HashMap<Character, Integer> inDegree = new HashMap<>();

        for (char node : nodes.keySet()) {
            inDegree.put(node, 0);
            visited.put(node, false);
        }

        nodes.forEach((character, characters) -> {
            for (Character neighbour : characters) {
                inDegree.put(neighbour, inDegree.get(neighbour) + 1);
            }
        });
//
//        for (Map.Entry<Character, List<Character>> entry : nodes.entrySet()) {
//            List<Character> neighbours = entry.getValue();
//            for (char neighbour : neighbours) {
//                inDegree.put(neighbour, inDegree.get(neighbour) + 1);
//            }
//        }

        ArrayList<Character> stack = new ArrayList<>();

        allTopologicalSortsUtil(visited, inDegree, stack);

        if (solutions.size() > 1)
            System.out.println(counter);
        else
            System.out.println(solutions.get(0));
    }


    private Boolean isInGraph(char newChar) {
        if (nodes.containsKey(newChar))
            return true;
        var characters = nodes.get(newChar);
        return characters != null && characters.contains(newChar);
    }

    private boolean edgeExists(char c1, char c2) {
        var node = nodes.get(c1);
        return node != null && node.contains(c2);
    }

    public void addLoseCases(String uniqueChars) {

        for (int i = 0; i < uniqueChars.length(); i++) {
            char character = uniqueChars.charAt(i);
            if (!isInGraph(character)) {
                nodes.put(character, new ArrayList<>());
            }
        }
    }

    private String getUniqueChars(String[] char2dList) {
        StringBuilder temp = new StringBuilder();
        for (String sentence : char2dList) {
            for (int i = 0; i < sentence.length(); i++) {
                char current = sentence.charAt(i);
                if (temp.toString().indexOf(current) < 0) {
                    temp.append(current);
                } else {
                    temp = new StringBuilder(temp.toString().replace(String.valueOf(current), ""));
                }
            }
        }
        return temp.toString();
    }
}
