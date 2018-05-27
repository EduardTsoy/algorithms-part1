import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {

    public static void main(String[] args) {
        if (args.length == 0) {
            StdOut.println("Usage:");
            StdOut.println("> java Permutation <k>");
            StdOut.println("where <k> is the number of elements to print");
            return;
        }
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            count++;
            if (q.size() < k) {
                q.enqueue(s);
            } else if (q.size() == k && StdRandom.uniform(count) < k) {
                q.dequeue();
                q.enqueue(s);
            }
        }
        Iterator<String> iterator = q.iterator();
        for (int i = 0; i < k; i++) {
            StdOut.println(iterator.next());
        }
    }
}
