
import org.junit.Test;
import problemAi.Graph;
import problemAi.ProblemAI;

import static org.junit.Assert.assertEquals;


public class ProblemAITest {

    @Test
    public void basicTest() {
        String[] sentences = new String[]{
                "XY", "XWY", "ZX", "ZXY",
                "ZXW", "YXW", "YWWX",};
        Graph graph = new Graph(sentences);

//        graph.print();

        graph.getAllTopologicalSorts();

        Graph expected = new Graph();

        expected.addEdge('X', 'Z');
        expected.addEdge('X', 'W');
        expected.addEdge('Y', 'W');
        expected.addEdge('Z', 'Y');

//        System.out.println("Expected:");
//        expected.print();

        assertEquals(expected, graph);
    }

    @Test
    public void basicTest2() {
        String[] sentences = new String[]{
                "XY", "XWY", "ZX", "ZXY",
                "ZXW", "YXW", "YWWX", "YWWXQ"};

        Graph graph = new Graph(sentences);
        graph.getAllTopologicalSorts();

//        assertEquals(new Node('W'), graph.findInNodes('W'));
    }

    @Test
    public void basicTest3() {
        String[] sentences = new String[]{"Q", "QS"};
        Graph graph = new Graph(sentences);

        graph.addLoseCases(ProblemAI.getUniqueChars(sentences));

        graph.getAllTopologicalSorts();

//        assertEquals(new Node('W'), graph.findInNodes('W'));
    }

    @Test
    public void testNodeTraversing() {
        Graph graph = new Graph();

        graph.addEdge('X', 'Z');
        graph.addEdge('X', 'W');
        graph.addEdge('Y', 'W');
        graph.addEdge('Z', 'Y');
        graph.addEdge('Y', 'A');
        graph.addEdge('A', 'B');

//        assertEquals(new Node('B'), graph.findInNodes('B'));

    }
}