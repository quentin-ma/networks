import java.util.ArrayDeque;

class Traversal {
    ArrayDeque<Integer> queue;
    int[] dist, visit, par;
    int nvis; // number of visited nodes in last BFS
    public static final int infinity = Integer.MAX_VALUE; 
    
    Traversal(int n) {
        queue = new ArrayDeque<>();
        dist = new int [n];
        visit = new int [n];
        par = new int [n];
        nvis = 0;
    }

    void clear() {
        queue.clear();
        for (int i = 0; i < dist.length; ++i) { dist[i] = infinity; }
        nvis = 0;
    }

    int distance(int v) { return dist[v]; }

    int ithVisited(int i) { return visit[i]; }

    int nbVisited() { return nvis; }

    int parent(int v) { return par[v]; }

    int ancestor(int v, int ith) {
        while (ith-- > 0) { v = par[v]; }
        return v;
    }

    // Bread first search, returns the last visited node.
    int bfs(Graph g, int src) {
        assert(g.n <= dist.length);
        clear();

        dist[src] = 0;
        par[src] = src;
        queue.add(src);
        
        while ( ! queue.isEmpty()) {
            int u = queue.poll(); // FIFO
            int d = dist[u];
            visit[nvis++] = u;
            for (int v : g.neighbors(u)) {
                if (dist[v] == infinity) { // first discovery of v
                    dist[v] = d + 1;
                    par[v] = u;
                    queue.add(v);
                }
            }
        }

        return visit[nvis - 1];
    }

}