public class Node {
//    private Node parent;
    private Node left;
    private Node right;
    private int data;

    public Node(int data) {
        this.data = data;
    }


    public Node getRight() {
        return right;
    }

    public Node getLeft() {
        return left;
    }

    public void setRight(Node right) {
        this.right = right;

//        if (this.right != null) {
//            this.right.parent = this;
//        }
    }

    public void setLeft(Node left) {
        this.left = left;

//        if (this.left != null) {
//            this.left.parent = this;
//        }
    }

    public boolean hasLeft() {
        return this.left != null;
    }

    public boolean hasRight() {
        return this.right != null;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
