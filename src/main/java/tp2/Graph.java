import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

// A graph on [0,..,n-1].
class Graph implements Iterable<Integer> {
    int n, m;
    int[] adjacency; // all adjacency lists concatenated
    int[] offset; // offset[u] is the index of neighbors of u in adjacency
    // neighbors of u are adjacency[offset[u]]...adjacency[offset[u+1]-1]

    public Graph(Edges edg, boolean symmetrize) {
        n = edg.n;
        m = edg.m;
        // offsets :
        int[] deg = edg.degrees(symmetrize);
        offset = new int[n + 1];
        offset[0] = 0;
        for (int u = 0; u < n; ++u) {
            offset[u + 1] = offset[u] + deg[u];
            deg[u] = 0; // re-use it as an index of neighb[u]
        }
        adjacency = new int[offset[n]];
        for (int e = 0; e < m; ++e) {
            int u = edg.tail[e];
            int v = edg.head[e];
            adjacency[offset[u] + deg[u]] = v;
            deg[u]++;
            if (symmetrize) {
                adjacency[offset[v] + deg[v]] = u;
                deg[v]++;
            }
        }
    }

    public int degree(int u) {
        return offset[u + 1] - offset[u];
    }

    // to iterate on nodes:
    public Iterator<Integer> iterator() {
        return IntStream.range(0, n).iterator();
    }

    // to iterate on neighbors of u:
    public Iterable<Integer> neighbors(int u) {
        return new Iterable<Integer>() {
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    int off_beg = offset[u];
                    int off_end = offset[u + 1];
                    int off_nxt = off_beg; // offset of next neighobr

                    public boolean hasNext() {
                        return off_nxt < off_end;
                    }

                    public Integer next() {
                        if (off_nxt >= off_end) {
                            throw new NoSuchElementException();
                        }
                        return adjacency[off_nxt++];
                    }
                    // UnsupportedOperationException
                };
            }
        };
    }

}