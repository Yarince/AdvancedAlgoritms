package internetshizzle;

import java.util.ArrayList;
import java.util.Arrays;

public class Permutation {

    public static void main(String[] args) {
        int[] factoradic = getFactoradic(10);

        String permutation = getPermutation(new char[]{'H', 'H', 'G', 'J'}, factoradic);
        System.out.println("getPermutation = " + permutation);
    }

    // TODO Store factoradic till 16 in an map for easy lookup
    // function to get Factoradic representation of a number,n.
    public static int[] getFactoradic(long n) {
        int[] factoradic = new int[13];
        int i = 1;
        while (n != 0) {
            factoradic[13 - i] = Math.toIntExact(n % i);
            n = n / i;
            i++;
        }
        return removeZeros(factoradic);
    }

    // function takes a String,str and Factoradic representation of a number n.
    // returns the nth lexicographic permutaion of character array, str.
    public static String getPermutation(char[] str, int[] factoradic) {
        Arrays.sort(str);
        ArrayList<Character> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int pos;
        char c;
        boolean[] used = new boolean[str.length]; // by default values are initialised to false.
        for (int i1 : factoradic) {
            pos = i1;
            c = getUnusedCharAtPos(str, pos, used);
            // TODO Get all double chars and move them all instead of just one! or something
            res.add(c);
        }
        for (char some_c : res) {
            sb.append(some_c);
        }
        return (sb.toString());
    }

    //function to get the yet unused character at a given position in a character array.
    private static char getUnusedCharAtPos(char[] str, int pos, boolean[] used) {
        int count = -1;
        for (int i = 0; i < str.length; i++) {
            if (!used[i]) {
                count++;
                if (count == pos) {
                    used[i] = true;
                    return str[i];
                }
            }
        }
        return ' ';
    }

    static int[] removeZeros(int[] a)
    {

        // index to store the first
        // non-zero number
        int ind = -1;

        // traverse in the array and find the first
        // non-zero number
        int n = a.length;
        for (int i = 0; i < n; i++) {
            if (a[i] != 0) {
                ind = i;
                break;
            }
        }

        // if no non-zero number is there
        if (ind == -1) {
            return a;
        }

        // Create an array to store
        // numbers apart from leading zeros
        int[] b = new int[n - ind];

        // store the numbers removing leading zeros
        for (int i = 0; i < n - ind; i++)
            b[i] = a[ind + i];

        // return the array
        return b;
    }
}