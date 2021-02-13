import java.util.Arrays;

public class Clusters {

    boolean[] isNeighbor;

    Clusters(Graph g) {
        isNeighbor = new boolean[g.n];
    }

    int tri(Graph g, int u) {
        Arrays.fill(isNeighbor, false);

        int nbTri = 0;
        for (int v : g.neighbors(u)) {
            isNeighbor[v] = true;
            if (g.degree(v) == 1) {
                continue;
            }
            for (int w : g.neighbors(v)) {
                if (isNeighbor[w]) {
                    nbTri++;
                }
            }
        }
        return nbTri;
    }

    float localCluCf(Graph g) {
        float cluL = 0;
        int tri_x;
        for (int i = 1; i < g.n; i++) {
            tri_x = tri(g, i);
            if (tri_x > 0) {
                cluL += (float) (2 * tri_x) / (g.degree(i) * (g.degree(i) - 1));
            }
        }
        cluL = cluL * ((float) 1 / g.n);
        return cluL;
    }

    float globalCluCf(Graph g) {
        int nbTri = 0;
        for (int i = 0; i < g.n; i++) {
            nbTri += tri(g, i);
        }
        float res = 3 * ((float) 1 / 3 * nbTri);

        int t_i = 0;
        float t_g;
        for (int i = 0; i < g.n; i++) {
            if (g.degree(i) >= 2) {
                t_i += (g.degree(i) * (g.degree(i) - 1));
            }
        }
        t_g = res / t_i;
        return t_g;
    }
}
