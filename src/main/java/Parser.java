import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class Parser {

    public String file;

    public int nbNodes;
    public int nbEdges;

    public int[] edgeCount;
    public int[] edgeStart;
    public int[] edgeEnd;

    public Parser(String filePath, int nbEdges) throws IOException {

        this.file = filePath;

        this.countEdgesAndNodes();

        this.edgeCount = new int[nbNodes];
        this.edgeStart = new int[this.nbEdges];
        this.edgeEnd = new int[this.nbEdges];

        Arrays.fill(edgeStart, -1);
        Arrays.fill(edgeEnd, -1);

        readEdges();
    }

    /**
     * Compute all edges and note how many edges each node has
     * @throws IOException
     */
    private void readEdges() throws IOException {
        BufferedReader br = readFile();
        String line;
        int i = 0;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            String[] tokens = line.split("\\W+");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);
            edgeStart[i] = u;
            edgeEnd[i] = v;
            edgeCount[u]++;
            edgeCount[v]++;
            i++;
        }
    }

    /**
     * Compute the amount of edges and nodes of a given
     * graph
     * 
     * @throws IOException
     */
    private void countEdgesAndNodes() throws IOException {
        int maxNode = -1;
        BufferedReader br;
        br = readFile();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            String[] tokens = line.split("\\W+");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[1]);
            maxNode = Integer.max(maxNode, Integer.max(u, v));
            this.nbEdges++;
        }
        this.nbNodes = maxNode + 1;
    }

    private BufferedReader readFile() throws IOException {
        return new BufferedReader(new FileReader(file));
    }

}
