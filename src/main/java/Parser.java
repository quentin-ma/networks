import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

class Parser {

    private static final String FILENAME = "com-youtube.ungraph.txt";

    public int nbNodes;
    public int nbEdges;
    public int[] edgeStart;
    public int[] edgeEnd;

    public Parser(String[] args) throws IOException {

        this.nbNodes = countNodes(FILENAME);
        this.nbEdges = countEdges();
        this.edgeStart = new int[nbEdges];
        this.edgeEnd = new int[nbEdges];
    }

    public int countEdges() throws IOException {
        BufferedReader br = readFile(FILENAME);
        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            if (line.split("\\s+").length == 2) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    public void readEdges() throws IOException {
        BufferedReader br = readFile(FILENAME);
        String line;
        int start, end;
        int tmpStart = 0, tmpEnd = 0;
        int i = 0, j = 0;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            String[] vertex = line.split("\\s+");
            start = Integer.parseInt(vertex[0]);
            if (start != tmpStart) {
                this.edgeStart[i++] = start;
                tmpStart = start;
            }
            end = Integer.parseInt(vertex[1]);
            if (end != tmpEnd) {
                this.edgeEnd[j++] = end;
                tmpEnd = end;
            }
        }
    }

    public int countNodes(String file) throws IOException {
        int maxNode = -1;
        BufferedReader br;
        br = new BufferedReader(new FileReader(filePath(file)));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.startsWith("#")) {
                continue;
            }
            String[] tokens = line.split("\\s+");
            int u = Integer.parseInt(tokens[0]);
            int v = Integer.parseInt(tokens[0]);
            maxNode = Integer.max(maxNode, Integer.max(u, v));
        }
        return maxNode;
    }

    public BufferedReader readFile(String filename) throws FileNotFoundException {
        return new BufferedReader(new FileReader(filePath(filename)));
    }

    public String filePath(String filename) {
        return filename;
    }

}

