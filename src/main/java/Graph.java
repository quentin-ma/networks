package grimawan;

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
        Arrays.fill(neighbourIndexes, -1);
        this.neighbours = new int[this.nbEdges * 2];

        int count = 0;
        for(int node = 0; node < this.nbNodes; node++) {
            neighbourIndexes[node] = count;
            int savedCount = count;
            for(int edge = 0; edge < nbEdges; edge++) {
                if(p.edgeStart[edge] == node) {
                    neighbours[count] = p.edgeEnd[edge];
                    count++;
                } else if(p.edgeEnd[edge] == node) {
                    neighbours[count] = p.edgeStart[edge];
                    count++;
                }
            }
            if (count - savedCount > this.maxDegree) {
                this.maxDegree = count - savedCount;
            }
        }
    }

    /**
     * Given a node, return an array of its neighbours
     * @param node Node
     * @return Array of neighbours if exists, otherwise empty array
     */
    public int[] getNeighbours(int node) {
        if(neighbourIndexes[node] == -1) return new int[0];
        int nextStop = node + 1;
        while(nextStop < nbNodes && neighbourIndexes[nextStop] == -1) {
            nextStop++;
        }
        if(nextStop == nbNodes) {
            return Arrays.copyOfRange(neighbours, neighbourIndexes[node], nbEdges);
        }
        return Arrays.copyOfRange(neighbours, neighbourIndexes[node], neighbourIndexes[nextStop]);
    }

    /**
     * Compute distance between a node A and a node B
     * Algorithm used : BFS
     * @param nodeA Starting point
     * @param nodeB End point
     * @return A distance d if A is connected to B, -1 otherwise
     */
    public int distance(int nodeA, int nodeB) {
        if(nodeA == nodeB) { return 0; }
        LinkedList<Integer> queue = new LinkedList<>();
        int[] distances = new int[nbNodes];
        Arrays.fill(distances, -1);
        queue.add(nodeA);
        distances[nodeA] = 0;
        while(queue.isEmpty() == false) {
            int currNode = queue.pop();
            int[] neighbours = getNeighbours(currNode);
            for(int n : neighbours) {
                if(n == nodeB) { return distances[currNode] + 1; }
                if(distances[n] == -1 || distances[n] > distances[currNode] + 1) {
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