import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;

public class Coreness {
    PriorityQueue<Map.Entry<Integer, Integer>> queue;
    boolean[] isActivate;
    int n;

    Coreness(Graph g) {
        queue = new PriorityQueue<>(Map.Entry.comparingByValue());
        isActivate = new boolean[g.n];
        Arrays.fill(isActivate, true);
        n = g.n;
        init(g);
    }

    void init(Graph g) {
        for (int i = 0; i < g.n; i++)
            queue.offer(new AbstractMap.SimpleEntry<>(i, g.degree(i)));
    }

    void undo(Graph g, int u) {
        isActivate[u] = false;
        --n;
        g.deg[u] = 0;
        queue.poll();
        if (n == 0) {
            return;
        }
        for (int v : g.neighbors(u)) {
            if (isActivate[v]) {
                g.deg[v]--;
                queue.offer(new AbstractMap.SimpleEntry<>(v, g.deg[v]));
            }
        }
    }

    void coreness(Graph g) {
        for (int k = 0; k < g.n + 1; ++k) {
            int last = n;
            while (true) {
                assert queue.peek() != null;
                if (!(queue.peek().getValue() < k)) break;
                if (g.deg[queue.peek().getKey()] == queue.peek().getValue()) {
                    Integer node = queue.peek().getKey();
                    undo(g, node);
                } else {
                    queue.poll();
                }
                if (n == 0) {
                    System.out.format("%d\n", k - 1);
                    System.out.format("%d\n", last);
                    return;
                }
            }
        }
    }
}