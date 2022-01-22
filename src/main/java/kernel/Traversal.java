import java.util.ArrayDeque;
import java.util.Arrays;

// Data-structure for searching a graph.
class Traversal {

    ArrayDeque<Integer> queue;
    int[] dist;
    int maxDist;
    int[] parent;
    public static final int infinity = Integer.MAX_VALUE;

    Traversal(int n) {
        queue = new ArrayDeque<>();
        dist = new int[n];
        maxDist = -1;
        parent = new int[n];
        Arrays.fill(parent, -1);
    }

    void clear() {
        queue.clear();
        for (int i = 0; i < dist.length; ++i) {
            dist[i] = infinity;
        }
    }

    int distance(int v) {
        return dist[v];
    }

    int maxDist() {
        return maxDist;
    }

    int parent(int u) {
        return parent[u];
    }

    int[] distances() {
        return dist.clone();
    }

    // Bread first search, returns the number of visited nodes.
    int bfs(Graph g, int src) {
        assert (g.n <= dist.length);
        clear();

        dist[src] = 0;
        queue.add(src);
        int n = 0;

        while (!queue.isEmpty()) {
            int u = queue.poll(); // FIFO
            int d = dist[u];
            ++n;
            for (int v : g.neighbors(u)) {
                if (dist[v] == infinity) { // first discovery of v
                    dist[v] = d + 1;
                    queue.add(v);
                    maxDist = v;
                    parent[v] = u;
                }
            }
        }

        return n;
    }
}
