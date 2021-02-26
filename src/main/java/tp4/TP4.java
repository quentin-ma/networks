import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TP4 {

    public static int[] generate(int[] E) {
        int size = 0;
        for (int i = 0; i < E.length; i++) {
            size += E[i];
        }
        int[] e = new int[size];
        int index = 0;
        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < E[i]; j++) {
                e[index] = i;
                index++;
            }
        }
        return e;
    }

    public static int[] racine(int n) {
        int[] degrees = new int[n];

        int sum = 0;
        for (int i = 0; i < n; i++) {
            degrees[i] = (int) Math.sqrt(i + 1);
            sum += degrees[i];
        }

        if (sum % 2 == 1) {
            sum += 1;
            degrees[n - 1] += 1;
        }

        int[] graph = new int[sum];
        int index = 0;

        for (int i = 0; i < n; i++) {
            for (int occurrence = 0; occurrence < degrees[i]; occurrence++) {
                graph[index] = i;
                index++;
            }
        }

        return graph;
    }

    public static int[] puissance(int n, double gamma) {
        assert (gamma >= 2.);
        assert (gamma <= 3.);

        double[] coeff = new double[n - 1];
        double c = 0;

        // Determine le coefficient c
        for (int k = 1; k < n; k++) {
            c += Math.pow(k, -gamma);
        }

        int sum = 0;
        int[] occurrences = new int[coeff.length];
        // Compte le nombre de noeuds de degré k
        for(int k = 1; k < coeff.length; k++) {
            int degree = (int) Math.round(Math.pow(k, -gamma) / c * n);
            occurrences[k-1] = degree;
            sum += degree * k;
        }

        if (sum % 2 == 1) {
            sum += 1;
            occurrences[0] += 1;
        }

        int[] degrees = new int[sum];
        int index = 0;
        // Création de sum noeuds avec les bons degrés
        for (int i = 0; i < occurrences.length; i++) {
            for (int j = 0; j < occurrences[i]; j++) {
                degrees[index] = i + 1;
                index++;
            }
        }

        // Randomise les degrés des noeuds précédemment créés
        random_permutation(degrees);

        int[] graph = new int[sum];
        index = 0;
        // Crée le graph dans le format souhaité
        for (int i = 0; i < degrees.length; i++) {
            for (int occurrence = 0; occurrence < degrees[i]; occurrence++) {
                graph[index] = i;
                index++;
            }
        }

        return graph;
    }

    public static void random_permutation(int[] E) {
        Random rnd = new Random(System.currentTimeMillis());
        for (int i = E.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = E[index];
            E[index] = E[i];
            E[i] = a;
        }
    }

    public static String display_graph(int[] E) {
        assert (E.length % 2 == 0);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < E.length - 1; i += 2) {
            sb.append(E[i]).append(" ").append(E[i + 1]).append("\n");
        }
        sb.setLength(sb.length() - 1);
        System.out.println(sb);
        return sb.toString();
    }

    public static void print_graph(String str) throws IOException {
        FileWriter file = new FileWriter("TP4.txt");
        BufferedWriter output = new BufferedWriter(file);
        output.write(str);
        output.close();
    }

    public static void main(String[] args) throws IOException {
        int[] graph = null;
        if (args[0].equals("exemple")) {
            int[] deg_exp = {1, 2, 1, 4};
            graph = generate(deg_exp);
        } else if (args[0].equals("racine")) {
            graph = racine(Integer.parseInt(args[1]));
        } else if (args[0].equals("puissance")) {
            graph = puissance(Integer.parseInt(args[1]), Double.parseDouble(args[2]));
        }
        assert (graph != null);
        random_permutation(graph);
        String g = display_graph(graph);
        print_graph(g);
    }
}