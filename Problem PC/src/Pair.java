public class Pair {

    private final int left;
    private final int right;

    public Pair(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairO = (Pair) o;
        return (this.left == pairO.getLeft() && this.right == pairO.getRight()) ||
                (this.right == pairO.getLeft() && this.left == pairO.getRight());
    }

    public boolean partOfPair(Integer part) {
        return left == part || right == part;
    }

}