import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private QueueNode<Item> first = null, last = null;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
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
        QueueNode<Item> newNode = new QueueNode<>(item);
        if (first == null) {
            first = newNode;
        }
        if (last != null) {
            last.next = newNode;
        }
        last = newNode;
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Item result = first.value;
        first = first.next;
        if (first == null) {
            last = null;
        }
        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        int rnd = StdRandom.uniform(n);
        QueueNode<Item> result = first;
        for (int i = 0; i < rnd; i++) {
            result = result.next;
        }
        return result.value;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Item> {

        private Item[] items;
        private int theNext = 0;

        MyIterator() {
            items = (Item[]) new Object[n];
            QueueNode<Item> current = first;
            for (int i = 0; i < items.length; i++) {
                if (current == null) {
                    items = Arrays.copyOf(items, i);
                    break;
                }
                items[i] = current.value;
                current = current.next;
            }
            StdRandom.shuffle(items);
        }

        @Override
        public boolean hasNext() {
            return theNext < items.length;
        }

        @Override
        public Item next() {
            if (theNext >= items.length) {
                throw new NoSuchElementException();
            }
            return items[theNext++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

    private static class QueueNode<Item> {

        Item value;
        QueueNode<Item> next;

        QueueNode(Item value) {
            this.value = value;
        }
    }
}
