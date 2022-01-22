import java.util.Arrays; // sort()
// file parsing:
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

// A set of edges between integers.
class CorrEdges {
    int[] tail, head;
    int n, m;

    CorrEdges() {
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
            if (line == null) break;
            if (line.charAt(0) == '#') { // a comment ?, show it
                System.err.println(line);
                continue;
            }
            // two ints separated by '\t' or ' ':
            int sep = line.indexOf('\t');
            if (sep < 0) sep = line.indexOf(' ');
            if (sep < 0) throw new Error("bad edge format");
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
            if (symmetrize) deg[head[e]]++;
        }
        return deg;
    }
}


class CorrPower {

    public static void main(String[] args) throws IOException {
        CorrEdges edg = new CorrEdges();
        edg.add(new InputStreamReader(System.in), Integer.MAX_VALUE);

        String com = args[0];
        if (com.equals("exemple")) {
            int[] deg_exp = {1, 2, 1, 4};
            int[] deg = edg.degrees(true);
            checkeq(deg.length, deg_exp.length, "le nombre de sommets");
            for (int u = 0; u < deg.length; ++u) {
                checkeq(deg[u], deg_exp[u], "le degrÃ© de " + u);
            }
        } else if (com.equals("racine")) {
            int n = Integer.parseInt(args[1]);
            int[] deg = edg.degrees(true);
            checkeq(deg.length, n, "le nombre de sommets");
            int sum = 0;
            for (int u = 0; u < n; ++u) {
                int d = (int) Math.sqrt(u + 1.);
                sum += d;
                if (u == n - 1 && sum % 2 == 1) {
                    d += 1;
                }
                checkeq(deg[u], d, "le degrÃ© de " + u);
            }
        } else if (com.equals("puissance")) {
            int n = Integer.parseInt(args[1]);
            double gamma = Double.parseDouble(args[2]);
            int[] deg = edg.degrees(true);
            Arrays.sort(deg);
            int[] nb = new int[deg.length];
            for (int i = 0; i < deg.length; ++i) {
                nb[deg[i]] += 1;
            }
            double sum = 0.;
            for (int k = 1; k < n; ++k) {
                sum += Math.pow((double) k, -gamma);
            }
            for (int k = 1; k < deg.length; ++k) {
                int exp = (int) Math.round(Math.pow((double) k, -gamma)
                        / sum * (double) n);
                checkapprox(nb[k], exp, "le nombre de sommets de degrÃ© " + k);
            }
        } else {
            System.err.println("Commande inconnue: " + args[0]);
            System.exit(1);
        }

        System.out.println("OK");
    }

    static void check(boolean test, String erreur) {
        if (!test) {
            System.out.println("ProblÃ¨me : " + erreur);
            System.exit(1);
        }
    }

    static void checkeq(int val, int expected, String erreur) {
        if (val != expected) {
            System.out.println("ProblÃ¨me avec " + erreur + " : "
                    + val + " au lieu de " + expected);
            System.exit(1);
        }
    }

    static void checkapprox(int val, int expected, String erreur) {
        if (Math.abs(val - expected) > 2) {
            System.out.println("ProblÃ¨me avec " + erreur + " : "
                    + val + " au lieu de " + expected);
            System.exit(1);
        }
    }
}