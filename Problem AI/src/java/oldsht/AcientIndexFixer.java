package oldsht;

import java.util.ArrayList;
import java.util.HashSet;

public class AcientIndexFixer {

    public static HashSet<Character> getUniqueChars(ArrayList<char[]> char2dList) {
        HashSet<Character> unique = new HashSet<>();

        char2dList.forEach(chars -> {
            for (char c : chars) unique.add(c);
        });

        return unique;
    }
//
//    public static HashSet<Character> getUniqueCount(String[] char2dList) {
//        HashSet<Character> unique = new HashSet<>();
//
//        for (String s : char2dList) {
//            for (char c : s.toCharArray()) unique.add(c);
//        }
//
//        return unique;
//    }

    public static ArrayList<Character> getOrder(ArrayList<char[]> wordsCharArray, ArrayList<Character> order,
                                                int firstIndex, int secondIndex, int depth) {

        char[] firstChars = wordsCharArray.get(firstIndex);
        char[] secondChars = wordsCharArray.get(secondIndex);

        char c1 = firstChars[depth];
        char c2 = secondChars[depth];

        if (firstIndex == 0 && depth == 0) {
            order.add(c1);
        }

        // If c1 & c1 are the same and there is another character after c1 check one step deeper
        if (c1 != c2) {


            int indexC1 = order.indexOf(c1);
            int indexC2 = order.indexOf(c2);

            if (indexC2 >= 0) { // If c2 exists
                if (indexC1 == -1) { // If c1 doesn't exist add it before c2
                    order.add(indexC2, c1);
                } else if (indexC1 > indexC2) { // If c2 is lower in the array than c1 place c2 behind c1
                    order.remove(Character.valueOf(c2));
                    order.add(indexC1, c2);
                }
            } else if (indexC1 == -1) { // If c1 && c2 don't exist
                order.add(c1);
                order.add(c2);
            } else { // If c1 exists and c2 doesn't add c2 AFTER c1
                order.add(indexC1 + 1, c2);
            }
        }

        if (c1 == c2 && firstChars.length > depth + 1) {
            depth++;
        } else {
            depth = 0;
            firstIndex++;
            secondIndex++;
        }

        if (secondIndex < wordsCharArray.size())
            getOrder(wordsCharArray, order, firstIndex, secondIndex, depth);

        return order;
    }


}
