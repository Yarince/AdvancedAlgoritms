import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final String expected;
    private InputStream anyInputStream;

    @Before
    public void setUpStreams() {
        try {
            anyInputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        try {
            anyInputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Main.result.delete(0,Main.result.length());
    }

    public MainTest(String input, String expected) {
        anyInputStream = new ByteArrayInputStream(input.getBytes());
        this.expected = expected;
    }

    @Test
    public void process() {
        System.setIn(anyInputStream);
        Main.process(new Reader(System.in));
        assertEquals(expected, outContent.toString());
    }


    @Parameters
    public static Collection input() {

        ArrayList<String[]> list = new ArrayList<>();
        String filePath = new File("").getAbsolutePath();
        try {
            for (int i = 1; i < 8; i++) {
                String input = Files.readString(Paths.get(String.format("%s/src/ws/%s.in", filePath, 900+i)));
                String output = Files.readString(Paths.get(String.format("%s/src/ws/%s.out", filePath, 900+i)));
                list.add(new String[]{input, output});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

}