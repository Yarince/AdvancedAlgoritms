package internetshizzle;

import java.util.Scanner;


class StringPermutation {
    public static void main(String[] args) {
        String str;
        Scanner sc = new Scanner(System.in);
        str = sc.next();
        permutation("", str);
    }

    public static void permutation(String fixed, String sub) {
        if (sub.equals("")) {
            System.out.println(fixed);
        } else {
            int a[] = new int[256];
            for (int i = 0; i < sub.length(); i++) {
                if (a[(int) sub.charAt(i)] == 0) {
                    a[(int) sub.charAt(i)] = 1;
                    permutation((fixed + sub.charAt(i)), sub.substring(0, i) + sub.substring(i + 1));
                }
            }
        }
    }
}

