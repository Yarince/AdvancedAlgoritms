package problemAi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    int inDegree;
    char data;
    boolean visited;
    List<Node> neighbours;

    Node(char data) {
        this.data = data;
        this.neighbours = new ArrayList<>();
        this.inDegree = 0;
        this.visited = false;
    }

    void addNeighbours(Node neighbour) {
        neighbour.inDegree++;
        this.neighbours.add(neighbour);
    }

    public String toString() {
        return "" + data;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == Character.class && this.data == (char) o) return true;
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return data == node.data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
