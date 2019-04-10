import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Owner Yarince Martis
 */
class Graph {
    private int[][] edges;
    private final ArrayList<Integer[]> commands;
    private int[] weights;

    private Double total = 0d;
    private int answerCounter = 0;


    Graph(int[] weights, int[][] edges, ArrayList<Integer[]> commands) {
        this.edges = edges;
        this.commands = commands;
        this.weights = weights;
    }

    void executeCommands() {
        for (Integer[] commandLine : commands) {
            int command = commandLine[0];
            switch (command) {
                case 0:
                    deleteEdge(commandLine[1]);
                    break;
                case 1:
                    changeWeight(commandLine[1], commandLine[2]);
                    break;
                case 2:
                    getKthSmallestWeight(commandLine[1], commandLine[2]);
                    break;
            }
        }
    }

    private void deleteEdge(int edgeNr) {
        edges[edgeNr] = new int[0];
    }

    private void getKthSmallestWeight(int node, int k) {

        // NOTE ArrayList impl
        ArrayList<Integer> localWeights = new ArrayList<>();
        localWeights.add(this.weights[node]);
        for (int[] edge : edges) {
            if (edge.length == 0) continue;

            int e1 = edge[0];
            int e2 = edge[1];
            if (e1 == node)
                localWeights.add(this.weights[e2]);
            else if (e2 == node) {
                localWeights.add(this.weights[e1]);
            }
        }

        if (k < localWeights.size()) {
            // Get Kth element from the localWeights
            int kthElement;
//           kthElement = FindK.findKthElementByQuickSelect(localWeights.toArray(new Integer[0]), 0, localWeights.size() - 1, (localWeights.size() - 1) - (k));
//           kthElement = FindK.findKthElementByQuickSelect(localWeights, 0, localWeights.size() - 1, (localWeights.size() - 1) - (k));
//           kthElement = FindK.findKthLargestBySorting(localWeights.toArray(new Integer[0]), k);
            kthElement = findKthLargestBySorting(localWeights, k);
            total += kthElement;
            answerCounter++;
        } else {
            answerCounter++;
            //            answers.add(0);
        }
    }

    private static int findKthLargestBySorting(ArrayList<Integer> arr, int k) {
        Collections.sort(arr);
        int targetIndex = arr.size() - k - 1;
        return arr.get(targetIndex);
    }

    private void changeWeight(int node, int weight) {
        weights[node] = weight;
    }

    void calculateAverage() {
        Double average = (total / answerCounter);

        DecimalFormat df = new DecimalFormat("#.######");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(6);
        System.out.println(df.format(average));
    }
}
