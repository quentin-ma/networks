import java.io.FileReader;
import java.io.IOException;

class TP2 {

    public static void mem() {
        Runtime rt = Runtime.getRuntime();
        rt.gc();
        System.err.println("Allocated memory : " + (rt.totalMemory() - rt.freeMemory()) / 1000000 + " Mb");
        System.err.flush();
    }

    public static int exo1(Graph g, Traversal trav, int src) {
        trav.bfs(g, src);
        int v = trav.maxDist();

        if (v == -1) {
            System.out.println(String.format("diam>=%d", 0));
            return 0;
        }

        trav.bfs(g, v);
        int w = trav.maxDist();
        int distance = trav.distance(w);

        System.out.println(String.format("v=%d\nw=%d\ndiam>=%d", w, v, distance));

        return distance;
    }

    public static int exo2(Graph g, Traversal trav, int src) {
        trav.bfs(g, src);
        int v = trav.maxDist();

        if (v == -1) {
            System.out.println(String.format("diam>=%d", 0));
            return 0;
        }

        trav.bfs(g, v);
        int w = trav.maxDist();
        int distance = trav.distance(w);

        int m = w;
        for (int i = 0; i < distance / 2; i++) {
            m = trav.parent(m);
        }

        trav.bfs(g, m);
        int x = trav.maxDist();
        trav.bfs(g, x);
        int y = trav.maxDist();
        distance = trav.distance(y);

        System.out.println(String.format("diam>=%d", distance));

        return distance;
    }

    public static int exo3(Graph g, Traversal trav, int src) {
        int[] dist0, dist1, dist2, dist3;
        int node0, node1, node2, node3;

        int[] sumdist = new int[g.n];

        node0 = src;
        trav.bfs(g, node0);

        if (trav.maxDist() == -1) {
            System.out.println(String.format("diam>=%d", 0));
            return 0;
        }

        dist0 = trav.distances();
        sum(sumdist, dist0);

        node1 = max(sumdist);
        trav.bfs(g, node1);
        dist1 = trav.distances();
        sum(sumdist, dist1);

        node2 = max(sumdist);
        trav.bfs(g, node2);
        dist2 = trav.distances();
        sum(sumdist, dist2);

        node3 = max(sumdist);
        trav.bfs(g, node3);
        dist3 = trav.distances();
        sum(sumdist, dist3);

        int distance = dist0[max(dist0)];
        int currDist = dist1[max(dist1)];
        if (currDist > distance)
            distance = currDist;
        currDist = dist2[max(dist2)];
        if (currDist > distance)
            distance = currDist;
        currDist = dist3[max(dist3)];
        if (currDist > distance)
            distance = currDist;

        System.out.println(String.format("diam>=%d", distance));

        return distance;
    }

    public static int exo4(Graph g, Traversal trav, int src) {
        return 0;
    }

    public static void sum(int[] dest, int[] values) {
        assert (dest.length == values.length);

        for (int i = 0; i < dest.length; i++) {
            dest[i] += values[i];
        }
    }

    public static int max(int[] array) {
        int max = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != Traversal.infinity && (max == -1 || array[i] > array[max]))
                max = i;
        }
        return max;
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 4) {
            System.err.println("Expected 4 arguments, got " + args.length);
            System.exit(1);
        }

        String method = args[0];
        String fname = args[1];
        int m_max = Integer.parseInt(args[2]);
        int src = Integer.parseInt(args[3]);

        Edges edg = new Edges();
        edg.add(new FileReader(fname), m_max);

        Graph g = new Graph(edg, true);
        Traversal trav = new Traversal(g.n);

        if (method.equals("2-sweep")) {
            exo1(g, trav, src);
        } else if (method.equals("4-sweep")) {
            exo2(g, trav, src);
        } else if (method.equals("sum-sweep")) {
            exo3(g, trav, src);
        } else if (method.equals("diametre")) {
            exo4(g, trav, src);
        } else if (method.equals("all")) {
            System.out.println("***** 2-sweep *******");
            exo1(g, trav, src);
            System.out.println("***** 4-sweep *******");
            exo2(g, trav, src);
            System.out.println("***** sum-sweep *****");
            exo3(g, trav, src);
            System.out.println("***** diametre ******");
            exo4(g, trav, src);
        }

    }
}