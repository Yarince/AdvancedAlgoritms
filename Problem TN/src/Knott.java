public class Knott {

    private static int[] rankInverse(int[] p, int i, int n) {
        ri(p, i, n, 1, 0);
        return p;
    }

    private static void ri(int[] p, int i, int n, int a, int s) {
        int j = 0;
        int k = 0,
                v = 0,
                h = 0;
        while (i > 0) {
            k = n - j - 1;
            h = b(k);
            v = b(j) * h;
            i -= v;
            j += 1;
        }
        i += v;
        j -= 1;
        p[a - 1] = j + 1 + s;
        int y = (i - 1 - ((i - 1) / h) * h) + 1;
        int w = (i - y) / h + 1;

        if (j > 1)
            ri(p, w, j, a + 1, s);
        else if (j == 1)
            p[a] = s + 1;

        if (k > 1)
            ri(p, y, k, a + j + 1, s + j + 1);
        else if (k == 1)
            p[a + j] = s + j + 2;
    }

    private static int b(int n) {
        if (n == 1 || n == 0) return 1;
        else return 4 * b(n - 1) - 6 * b(n - 1) / (n + 1);
    }

    private static int rank(int[] p, int n) {
        return r(p, n, 1, 0);
    }

    private static int r(int[] p, int n, int a, int s) {
        int j = p[a - 1] - s - 1;
        int v = 0;
        for (int i = 0; i < (j - 1); i++) {
            v += b(i) * b(n - i - 1);
        }
        return v + (j <= 1 ? 0 : b(n - j - 1) * r(p, j, a + 1, s) - 1) +
                (n - j - 1 <= 1 ? 1 : r(p, n - j + 1, a + j + 1, s + j + 1));
    }


}
