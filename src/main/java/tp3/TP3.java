import java.io.FileReader;
import java.io.IOException;

public class TP3 {

    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Expected at least 1 argument, got " + args.length);
            System.exit(1);
        }

        String method = args[0];
        String filename = args[1];
        int nbEdges = Integer.parseInt(args[2]);

        Edges edg = new Edges();
        edg.add(new FileReader(filename), nbEdges);

        Graph g = new Graph(edg, true);
        Clusters clusters = new Clusters(g);

        if (method.equals("triangles")) {
            int u = Integer.parseInt(args[3]);
            int n = clusters.tri(g, u);
            System.out.format("%d\n", n);
        } else if (method.equals("clust")) {
            float val = clusters.localCluCf(g);
            System.out.format("%.5f\n", val);
            float cluG = clusters.globalCluCf(g);
            System.out.format("%.5f\n", cluG);
        }
    }

}