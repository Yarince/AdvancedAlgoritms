package internetshizzle;

public class AnagramProblem {
    private static int anagramCounter = 0;

    public static void main(String args[]) {
        System.out.println(findAnagram("BIM", 5));
    }

    public static String findAnagram(String word, int index) {
//        ArrayList<String> permutations_list = new ArrayList<>();
        String[] permutations = new String[index];
        permutations("", word.toCharArray(), permutations, index);
        return permutations[index - 1];
    }

    public static void permutations(String prefix, char[] word, String[] anagrams, int kElement) {

        boolean duplicate = false;
        if (word.length == 2 && word[0] != word[1] && anagramCounter <= kElement) {
            String permutation1 = prefix + word[0] + word[1];
//            permutations_list.add(permutation1);
            anagrams[anagramCounter] = permutation1;
            anagramCounter++;
            if (anagramCounter <= kElement) {
                String permutation2 = prefix + word[1] + word[0];
                anagrams[anagramCounter] = permutation2;
//            permutations_list.add(permutation2);
                anagramCounter++;
            }
            return;
        } else if (word.length == 2 && anagramCounter <= kElement) {
            String permutation = prefix + word[0] + word[1];
            anagrams[anagramCounter] = permutation;
//            permutations_list.add(permutation);
            anagramCounter++;
            return;
        }

        for (int i = 0; i < word.length; i++) {
            if (!duplicate) {
                permutations(prefix + word[0], new String(word).substring(1, word.length).toCharArray(), anagrams, kElement);
            }
            if (i < word.length - 1) {
                char temp = word[0];
                word[0] = word[i + 1];
                word[i + 1] = temp;
            }

            duplicate = i < word.length - 1 && word[0] == word[i + 1];
        }
    }
}