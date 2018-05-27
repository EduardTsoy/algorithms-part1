import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
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
        n--;
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
            if (result == null) {
                throw new ConcurrentModificationException();
            }
        }
        return result.value;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Item> {

        private final Item[] items;
        private int theNext = 0;

        MyIterator() {
            @SuppressWarnings("unchecked")
            Item[] arr = (Item[]) new Object[n];
            QueueNode<Item> current = first;
            for (int i = 0; i < arr.length; i++) {
                if (current == null) {
                    arr = Arrays.copyOf(arr, i);
                    break;
                }
                arr[i] = current.value;
                current = current.next;
            }
            StdRandom.shuffle(arr);
            items = arr;
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
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println(rq.size()); //        ==> 0
        rq.enqueue(537);
        rq.enqueue(714);
        rq.dequeue(); //     ==> 537
        rq.dequeue(); //     ==> 714
        System.out.println(rq.size()); //        ==> actual 2, expected 0
    }

    private static class QueueNode<Item> {

        private final Item value;
        private QueueNode<Item> next;

        QueueNode(Item value) {
            this.value = value;
        }

        public Item getValue() {
            return value;
        }

        public QueueNode<Item> getNext() {
            return next;
        }

        public void setNext(QueueNode<Item> next) {
            this.next = next;
        }
    }
}
