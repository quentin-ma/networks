import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

class Edges {
    int[] vertices;
    int[] tail, head;
    int n, m;

    Edges() {
        int estim_m = 4; // without a given estimation of m, let's begin with 4
        tail = new int[estim_m];
        head = new int[estim_m];
        n = 0;
        m = 0;
    }

    void add(InputStreamReader input, int m_max) throws IOException {
        BufferedReader inp = new BufferedReader(input);
        while (m < m_max) {
            String line = inp.readLine();
            if (line == null)
                break;
            if (line.charAt(0) == '#') { // a comment ?, show it
                System.err.println(line);
                continue;
            }
            // two ints separated by '\t' or ' ':
            int sep = line.indexOf('\t');
            if (sep < 0)
                sep = line.indexOf(' ');
            if (sep < 0)
                throw new Error("bad edge format");

            String su = line.substring(0, sep);
            String sv = line.substring(sep + 1);
            int u = Integer.parseInt(su);
            int v = Integer.parseInt(sv);
            // add edge u v:
            if (m >= tail.length) {
                increaseCapacity();
            }
            if (u >= n) {
                n = u + 1;
            }
            if (v >= n) {
                n = v + 1;
            }
            tail[m] = u;
            head[m] = v;
            ++m;
        }
    }

    void increaseCapacity() {
        // let's double array sizes:
        int[] t = new int[2 * tail.length];
        int[] h = new int[2 * tail.length];
        for (int i = 0; i < tail.length; ++i) {
            t[i] = tail[i];
            h[i] = head[i];
        }
        tail = t;
        head = h;
    }

    int[] degrees(boolean symmetrize) {
        int[] deg = new int[n];
        for (int e = 0; e < m; ++e) {
            deg[tail[e]]++;
            if (symmetrize)
                deg[head[e]]++;
        }
        return deg;
    }
}