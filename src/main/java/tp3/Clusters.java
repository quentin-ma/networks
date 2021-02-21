import java.util.Arrays;

public class Clusters {

    boolean[] isCandidate;
    int[] triangles;
    int[] triplets;

    Clusters(Graph g) {
        isCandidate = new boolean[g.n];
        triangles = new int[g.n];
        triplets = new int[g.n];
        init(g);
    }

    void init(Graph g) {
        for (int i = 0; i < g.n; i++) {
            triangles[i] = tri(g, i);
            triplets[i] = g.degree(i) * (g.degree(i) - 1);
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
        float cluL = 0;
        for (int i = 0; i < g.n; i++) {
            if (g.degree(i) >= 2) {
                cluL += (float) (2 * triangles[i]) / triplets[i];
            } else {
                cluL += 0;
            }
        }
        return cluL * 1 / g.n;
    }

    float globalClustCf(Graph g) {
        int sumTriangles = 0, sumTriplets = 0;
        for (int i = 0; i < g.n; i++) {
            sumTriangles += triangles[i];
            sumTriplets += triplets[i] / 2;
        }
        return (float) sumTriangles / sumTriplets;
    }
}
