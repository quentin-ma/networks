import java.io.FileReader;
import java.io.IOException;

public class Kernel {

    public static void mem() {
        Runtime rt = Runtime.getRuntime();
        rt.gc();
        System.err.println("Allocated memory : " + (rt.totalMemory() - rt.freeMemory()) / 1000000 + " Mb");
        System.err.flush();
    }

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
        Coreness coreness = new Coreness(g);
        Cluster clusters = new Cluster(g);

        switch (method) {
            case "triangles" -> {
                int u = Integer.parseInt(args[3]);
                int n = clusters.tri(g, u);
                System.out.format("%d\n", n);
            }
            case "clust" -> {
                float val = clusters.localCluCf(g);
                System.out.format("%.5f\n", val);
                float cluG = clusters.globalClustCf(g);
                System.out.format("%.5f\n", cluG);
            }
            case "k-coeur" -> coreness.coreness(g);
        }
    }
}