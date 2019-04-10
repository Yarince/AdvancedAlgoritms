package internetshizzle;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class PermuteString2
{
    private String input;
    private long n;

    private long fact[];
    public PermuteString2(String input, long n) {
        this.input = input;
        this.n= n;
        this.fact = new long[input.length()+1];
        precomputeFactorirals();
    }

    // utility for calculating factorial
    private void precomputeFactorirals()
    {
        fact[0] = 1;
        for (int i = 1; i < fact.length; i++)
            fact[i] = fact[i - 1] * i;
    }

    // utility function to get possible combinations using frequency of chars
    private long getPossible(int len, TreeMap<Character, Integer> tmap) {
        long total = fact[len];
        for(Entry<Character, Integer> entry : tmap.entrySet()) {
            total = total/fact[entry.getValue()];
        }
        return total;
    }

    public String nPermute() {
        if (n == 0) return input;
        TreeMap<Character, Integer> tmap = new TreeMap<>(Comparator.comparingInt(o -> o));
        // length of given string
        int len = this.input.length();
        // create ordered map with frequency
        for (int i = 0; i < len; i++) {
            int oldCount = tmap.getOrDefault(this.input.charAt(i), 0);
            tmap.put(this.input.charAt(i), oldCount+1);
        }

        String out = "";
        while(out.length() < len) {
            int remainingLen = len - out.length();
            // get all possible permutations from remaing length and remaing chars
            long totalPossible = getPossible(remainingLen, tmap);
            if(n > totalPossible) {
                return "Not possible"; // invalid input case
            }
            long eachBucket = totalPossible/tmap.size();
            long start = 1;
            for(Entry<Character, Integer> entry : tmap.entrySet()) {
                long myRange = eachBucket * entry.getValue();
                long end = start + myRange;
                if(start <= n && n < end) {
                    out +=  entry.getKey();
                    // update frequency of this Character
                    if(entry.getValue()-1 > 0) {
                        tmap.put(entry.getKey(), entry.getValue()-1);
                    }
                    else {
                        tmap.remove(entry.getKey());
                    }
                    n = n - start+1;
                    break;
                }
                start = end; // for next bucket
            }
        }
        return out;
    }


    // Driver program to test above method
    public static void main(String[] args) {
        PermuteString2 ps = new PermuteString2("GHHJ", 9);
        System.out.println(ps.nPermute());
    }
}
