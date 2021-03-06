import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

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
    }

    public MainTest(String number, String input, String expected) {
        System.out.println("number = " + number);
        anyInputStream = new ByteArrayInputStream(input.getBytes());
        this.expected = expected;
    }

    @Test
    public void process() {
        System.setIn(anyInputStream);
        Main.process(new Scanner(System.in));
        assertEquals(expected, outContent.toString());
    }


    @Parameterized.Parameters
    public static Collection input() {

        ArrayList<String[]> list = new ArrayList<>();
        String filePath = new File("").getAbsolutePath();
        try {
            for (int i = 1; i < 8; i++) {
                String input = new String(Files.readAllBytes(Paths.get(String.format("%s/src/source/gq/90%s.in", filePath, i))), StandardCharsets.UTF_8);
                String output = new String(Files.readAllBytes(Paths.get(String.format("%s/src/source/gq/90%s.out", filePath, i))), StandardCharsets.UTF_8);
                list.add(new String[]{String.valueOf(i), input, output});
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

        return list;
    }

}