import edu.princeton.cs.algs4.StdRandom;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        growIfNecessary();
        items[n++] = item;
    }

    private void growIfNecessary() {
        if (n == items.length) {
            Item[] arr = (Item[]) new Object[n * 2];
            System.arraycopy(items, 0, arr, 0, n);
            items = arr;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Item result = items[--n];
        items[n] = null;
        shrinkIfNecessary();
        return result;
    }

    private void shrinkIfNecessary() {
        if (n < items.length / 2) {
            Item[] arr = (Item[]) new Object[items.length / 2];
            System.arraycopy(items, 0, arr, 0, n);
            items = arr;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Item> {

        private final int[] indexes;
        private int current = 0;

        MyIterator() {
            indexes = new int[n];
            for (int i = 0; i < n; i++) {
                indexes[i] = i;
            }
            StdRandom.shuffle(indexes);
        }

        @Override
        public boolean hasNext() {
            return current < n;
        }

        @Override
        public Item next() {
            if (current >= indexes.length) {
                throw new NoSuchElementException();
            }
            int index = indexes[current++];
            if (index >= items.length) {
                throw new ConcurrentModificationException();
            }
            return items[index];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println(rq.size()); //        ==> 0
        rq.enqueue(537);
        rq.enqueue(714);
        System.out.println(rq.dequeue()); //     ==> 537
        System.out.println(rq.dequeue()); //     ==> 714
        System.out.println(rq.size()); //        ==> expected 0
        for (int i = 0; i < 1000; i++) {
            rq.enqueue(i);
        }
        System.out.println(rq.size());
    }
}
