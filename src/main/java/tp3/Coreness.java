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
        for (int i = 0; i < g.n; i++) {
            queue.offer(new AbstractMap.SimpleEntry<>(i, g.degree(i)));
        }
    }

    void undo(Graph g, int u) {
        isActivate[u] = false;
        n--;
        g.deg[u] = 0;
        for (int v : g.neighbors(u)) {
            g.deg[v]--;
        }
    }

    void updateQueue(Graph g) {
        queue.clear();
        for (int i = 0; i < g.deg.length; ++i) {
            if (isActivate[i])
                queue.offer(new AbstractMap.SimpleEntry<>(i, g.deg[i]));
        }
    }

    void coreness(Graph g) {
        for (int k = 0; k < g.n; ++k) {
            int last = n;
            while (true) {
                assert queue.peek() != null;
                if (!(queue.peek().getValue() < k)) break;
                Integer node = queue.peek().getKey();
                undo(g, node);
                updateQueue(g);
                if (queue.size() == 0) {
                    System.out.format("%d\n", k - 1);
                    System.out.format("%d\n", last);
                    return;
                }
            }
        }
    }
}
