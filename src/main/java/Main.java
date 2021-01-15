import java.io.IOException;
import java.util.Arrays;

class Main {



    public static void mem() {
        Runtime rt = Runtime.getRuntime();
        rt.gc();
        System.err.println("Allocated memory : "
                + (rt.totalMemory() - rt.freeMemory()) / 1000000
                + " Mb");
        System.err.flush();
    }

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser(args);
        System.out.println(parser.countNodes());
        System.out.println(parser.countEdges());
        System.out.println(Arrays.toString(parser.edgeStart));
    }
}