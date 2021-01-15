import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.Set;

class Parser {

    public String file;

    public int nbNodes;
    public int nbEdges;

    public int[] edgeCount;
    public int[] edgeStart;
    public int[] edgeEnd;

    public Parser(String filePath, int nbEdges) throws IOException {

        this.file = filePath;

        this.nbNodes = countNodes();
        this.nbEdges = nbEdges;

        this.edgeCount = new int[nbNodes];
        this.edgeStart = new int[nbEdges];
        this.edgeEnd = new int[nbEdges];

        readEdges();
    }

    /*
    Unecessary as nbEdges is given

    private int countEdges() throws IOException {
        BufferedReader br = readFile();
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            if (line.split("\\W+").length == 2) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
    */

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
     * Count the amount of node in a graph, the amount being
     * max(nodes) + 1
     * 
     * @return Number of nodes, -1 if none
     * @throws IOException
     */
    private int countNodes() throws IOException {
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
        }
        return maxNode + 1;
    }

    private BufferedReader readFile() throws IOException {
        return new BufferedReader(new FileReader(file));
    }

}
