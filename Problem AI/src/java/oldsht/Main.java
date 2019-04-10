package oldsht;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> input;

        try { // TODO Read from standard input
//            input = Files.readAllLines(Paths.get("/home/yarince/IdeaProjects/Problem AI/src/source/ai/905.in"));
            input = Files.readAllLines(Paths.get("/home/yarince/IdeaProjects/Problem AI/src/source/input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int fragments = Integer.parseInt(input.get(0));
        int startIndex = 1;
        for (int i = 1; i <= fragments; i++) {

            int wordCount = processFragment(input, startIndex);

            startIndex += wordCount + 1;
        }
    }

    private static int processFragment(List<String> input, int startIndex) {
        int wordCount = Integer.parseInt(input.get(startIndex));
        String[] words = getStrings(input, startIndex, wordCount);

        ArrayList<char[]> wordsCharArray = new ArrayList<>();
        // TODO the amount of possible right answers
        for (String word : words) {
            wordsCharArray.add(word.toCharArray());
        }


        if (wordsCharArray.size() > 1) {
            ArrayList<Character> order = AcientIndexFixer.getOrder(wordsCharArray, new ArrayList<>(),
                    0, 1, 0);
//            System.out.println(order);
        }

//        oldsht.AcientIndexFixer.getUniqueChars(wordsCharArray).forEach(character -> {
//            if (!graph.isInGraph(character))
//                graph.addVerticie(new problemAi.Node(character));
//        });

//        System.out.println("pairs.allTopologicalSorts() = " + pairs.topologicalSort());

        return wordCount;
    }

    private static String[] getStrings(List<String> input, int startIndex, int wordCount) {
        String[] words = new String[wordCount];
        for (int j = 0; j < wordCount; j++) {
            words[j] = (input.get(startIndex + j + 1));
        }
        return words;
    }

}
