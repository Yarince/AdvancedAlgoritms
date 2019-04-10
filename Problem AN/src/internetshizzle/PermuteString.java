package internetshizzle;

import java.util.Arrays;

// Java program to print n-th permutation
public class PermuteString {
    private final static int MAX_CHAR = 'Z';
    private final static int MAX_FACT = 17;
    private static long fact[] = new long[MAX_FACT];

    // utility for calculating factorial 
    private static void precomputeFactorirals() {
        fact[0] = 1;
        for (int i = 1; i < MAX_FACT; i++)
            fact[i] = fact[i - 1] * i;
    }

    // function for nth permutation 
    public static String nPermute(String str, int n) {
        if (n <= 1) return str;
        precomputeFactorirals();

        // length of given string 
        int len = str.length();

        // Count frequencies of all 
        // characters 
        int[] freq = new int[MAX_CHAR];
        for (int i = 0; i < len; i++)
            freq[str.charAt(i) - 'A']++;

        // out string for output string 
        StringBuilder out = new StringBuilder();

        // iterate till sum equals n 
        int sum = 10;
        int k = 0;

        // We update both n and sum in this 
        // loop. 
        while (sum >= n) {

            // check for characters present in freq[] 
            for (int i = 0; i < MAX_CHAR; i++) {
                if (freq[i] == 0)
                    continue;

                // Remove character 
                freq[i]--;

                // calculate sum after fixing 
                // a particular char 
                sum = 0;
                int xsum = (int) fact[len - 1 - k];
                for (int j = 0; j < MAX_CHAR; j++)
                    xsum /= fact[freq[j]];
                sum += xsum;

                // if sum > n fix that char as 
                // present char and update sum 
                // and required nth after fixing 
                // char at that position 
                if (sum >= n) {
                    out.append((char) (i + 'A'));
                    k++;
                    n -= (sum - xsum);
                    break;
                } else {
                    // if sum < n, add character back
                    freq[i]++;
                }
            }
        }

        // if sum == n means this char will provide its 
        // greatest permutation as nth permutation 
        for (int i = MAX_CHAR - 1; k < len && i >= 0; i--)
            if (freq[i] != 0) {
                out.append((char) (i + 'A'));
                freq[i++]--;
            }

        // append string termination 
        // character and print result 
        return out.toString();
    }

    // Driver program to test above method 
    public static void main(String[] args) {

        // TODO Auto-generated method stub 
        int n = 1;
        String str = "RPBY";
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        str = new String(chars);

        System.out.println(nPermute(str, n));
    }
} 