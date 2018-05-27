import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Usage:");
            StdOut.println("> java Permutation <k>");
            StdOut.println("where <k> is the number of elements to print");
            return;
        }
        Integer k = Integer.valueOf(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            q.enqueue(s);
        }
        Iterator<String> iterator = q.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(iterator.next());
        }
    }
}
