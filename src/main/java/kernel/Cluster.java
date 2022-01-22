import java.util.Arrays;

public class Cluster {

    boolean[] isCandidate;
    int[] triangles;
    int[] triplets;
    double[] cluL;

    Cluster(Graph g) {
        isCandidate = new boolean[g.n];
        triangles = new int[g.n];
        triplets = new int[g.n];
        cluL = new double[g.n];
        init(g);
    }

    void init(Graph g) {
        for (int i = 0; i < g.n; i++) {
            triangles[i] = tri(g, i);
            triplets[i] = g.degree(i) * (g.degree(i) - 1);
            cluL[i] = (double) (2 * triangles[i]) / triplets[i];
        }
    }

    int tri(Graph g, int u) {
        Arrays.fill(isCandidate, false);
        int nbTri = 0;
        for (int v : g.neighbors(u)) {
            if (g.degree(v) > 1) {
                isCandidate[v] = true;
                for (int w : g.neighbors(v)) {
                    if (isCandidate[w]) {
                        nbTri++;
                    }
                }
            }
        }
        return nbTri;
    }

    float localCluCf(Graph g) {
        double sumCluL = 0;
        for (int i = 0; i < g.n; i++) {
            if (g.degree(i) >= 2) {
                sumCluL += cluL[i];
            } else {
                sumCluL += 0;
            }
        }
        return (float) sumCluL * 1 / g.n;
    }

    float globalClustCf(Graph g) {
        int sumTriangles = 0;
        long sumTriplets = 0;
        for (int i = 0; i < g.n; i++) {
            sumTriangles += triangles[i];
            sumTriplets += triplets[i] / 2;
        }
        return (float) sumTriangles / sumTriplets;
    }
}
