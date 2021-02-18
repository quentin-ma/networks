import java.util.*;

public class Tag {

    PriorityQueue<Map.Entry<Integer, Integer>> queue;
    Edges edg;

    boolean[] isTag;
    int[] degrees;
    int n;

    Tag(Edges edg, Graph g) {
        queue = new PriorityQueue<>(Map.Entry.comparingByValue());
        degrees = new int[g.n];
        isTag = new boolean[g.n];
        Arrays.fill(isTag, true); // all vertex are tagged
        for (int i = 0; i < g.n; i++)
            queue.offer(new AbstractMap.SimpleEntry<>(i, g.degree(i)));
        for (int i = 0; i < g.n; i++)
            degrees[i] = g.degree(i); // symmetrize for first iteration
        this.edg = edg;
    }

    Graph undo(int u) {
        for (int i = 0; i < edg.head.length; i++) {
            int head = edg.head[i];
            int tail = edg.tail[i];
            if (head == u || tail == u) {
                edg.head[i] = -1;
                edg.tail[i] = -1;
            }
        }
        int[] oldHead = edg.head;
        int[] oldTail = edg.tail;
        edg.head = new int[edg.head.length];
        edg.tail = new int[edg.tail.length];
        int l = 0, e = 0;
        for (int i = 0; i < oldHead.length; i++) {
            if (oldHead[i] < 0) {
                edg.head[i] = 0;
                edg.tail[i] = 0;
            } else {
                edg.head[e] = oldHead[i];
                edg.tail[e] = oldTail[i];
                e++;
            }
            if (oldHead[i] != - 1) {
                if (oldHead[i] == 0 && oldTail[i] == 0) {
                    l--;
                }
                l++; // count edges
            }
        }
        if (u == edg.n - 1) {
            edg.n -= 1;
        } else {
            n = edg.n - 1;
        }
        edg.m = l;
        isTag[u] = false;
        return new Graph(edg, true);
    }

    int countVertices() {
        int v = 0;
        for (boolean b : isTag) {
            if (b)
                v++;
        }
        return v;
    }

    void finalQueue(Graph g) {
        for (int k = 0; k < g.n; k++) {  // k -> 0..n - 1
            while (true) {
                assert queue.peek() != null;
                if (!(queue.peek().getValue() < k)) break;
                Integer node = queue.peek().getKey(); // get node
                g = undo(node);
                queue.clear();
                for (int i = 0; i < g.deg.length; i++) {
                    if (isTag[i]) {
                        queue.offer(new AbstractMap.SimpleEntry<>(i, g.deg[i]));
                    }
                }
                if (queue.size() == 0) {
                    System.out.format("%d\n", k - 1);
                    int s = isTag.length - this.n;
                    System.out.format("%d\n", s);
                    break;
                }
            }
        }
    }

}
