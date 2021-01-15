import java.util.Arrays;
import java.util.LinkedList;

public class Graph {

    private int nbNodes;
    private int nbEdges;
    private int maxDegree;

    private int neighbourIndexes[];
    private int neighbours[];

    public Graph(Parser p) {
        this.nbNodes = p.nbNodes;
        this.nbEdges = p.edgeStart.length;
        this.maxDegree = 0;

        this.neighbourIndexes = new int[this.nbNodes];
        this.neighbours = new int[this.nbEdges * 2];
        Arrays.fill(neighbours, -1);

        for (int index = 0; index < this.nbNodes - 1; index++) {
            maxDegree = Integer.max(maxDegree, p.edgeCount[index]);
            this.neighbourIndexes[index + 1] = p.edgeCount[index] + this.neighbourIndexes[index];
        }
        maxDegree = Integer.max(maxDegree, p.edgeCount[p.nbNodes - 1]);

        for (int edge = 0; edge < nbEdges; edge++) {
            int u = p.edgeStart[edge];
            int v = p.edgeEnd[edge];
            addEdge(u, v);
            addEdge(v, u); // Remove this line to remove bidirectionnal vertex
        }
    }

    /**
     * Add an edge to the graph
     * 
     * @param nodeA starting node
     * @param nodeB ending node
     */
    private void addEdge(int nodeA, int nodeB) {
        int currIndex = this.neighbourIndexes[nodeA];
        while (this.neighbours[currIndex] != -1) {
            currIndex++;
        }
        this.neighbours[currIndex] = nodeB;
    }

    /**
     * Given a node, return an array of its neighbours
     * 
     * @param node Node
     * @return Array of neighbours if exists, otherwise empty array
     */
    public int[] getNeighbours(int node) {
        if (node + 1 == nbNodes) {
            return Arrays.copyOfRange(neighbours, neighbourIndexes[node], nbEdges);
        }
        return Arrays.copyOfRange(neighbours, neighbourIndexes[node], neighbourIndexes[node + 1]);
    }

    /**
     * Compute distance between a node A and a node B Algorithm used : BFS
     * 
     * @param nodeA Starting point
     * @param nodeB End point
     * @return A distance d if A is connected to B, -1 otherwise
     */
    public int distance(int nodeA, int nodeB) {
        if (nodeA == nodeB) {
            return 0;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        int[] distances = new int[nbNodes];
        Arrays.fill(distances, -1);
        queue.add(nodeA);
        distances[nodeA] = 0;
        while (queue.isEmpty() == false) {
            int currNode = queue.pop();
            int[] neighbours = getNeighbours(currNode);
            for (int n : neighbours) {
                if (n == nodeB) {
                    return distances[currNode] + 1;
                }
                if (distances[n] == -1 || distances[n] > distances[currNode] + 1) {
                    queue.add(n);
                    distances[n] = distances[currNode] + 1;
                }
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("n=");
        sb.append(this.nbNodes);
        sb.append("\nm=");
        sb.append(this.nbEdges);
        sb.append("\ndegmax=");
        sb.append(this.maxDegree);
        return sb.toString();
    }
}