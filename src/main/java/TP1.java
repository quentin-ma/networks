import java.io.IOException;

class TP1 {

    public static void mem() {
        Runtime rt = Runtime.getRuntime();
        rt.gc();
        System.err.println("Allocated memory : " + (rt.totalMemory() - rt.freeMemory()) / 1000000 + " Mb");
        System.err.flush();
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            System.err.println("Expected 4 arguments : [file path] [edge count] [start node] [end node]");
            System.exit(1);
        }

        String filePath = args[0];
        int edgeCount = 0;
        int startNode = 0;
        int endNode = 0;
        try {
            edgeCount = Integer.parseInt(args[1]);
            startNode = Integer.parseInt(args[2]);
            endNode = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.err.println("[edge count] [start node] [end node] should all be integers");
            System.exit(1);
        }

        long start = System.currentTimeMillis();

        Parser parser = new Parser(filePath, edgeCount);
        Graph g = new Graph(parser);

        long end = System.currentTimeMillis();

        System.err.println("Graph loaded in " + (end - start) + "ms");
        mem();

        System.out.println(g + "\ndist=" + g.distance(startNode, endNode));
        mem();
    }
}