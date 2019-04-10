import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GraphTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testGraphRaw() {
        Graph graph = new Graph(new int[]{10, 20, 30}, new int[][]{
                new int[]{0, 1},
                new int[]{1, 2},
                new int[]{0, 2},
        },
                new ArrayList<Integer[]>(){{
                        add(new Integer[]{2, 0, 1});
                        add(new Integer[]{0, 2});
                        add(new Integer[]{2, 1, 0});
                        add(new Integer[]{0, 1});
                        add(new Integer[]{2, 2, 1});
                        add(new Integer[]{1, 0, 50});
                        add(new Integer[]{2, 0, 0});
                }});
        graph.executeCommands();
        graph.calculateAverage();
        assertEquals("25.000000\n", outContent.toString());
    }
}