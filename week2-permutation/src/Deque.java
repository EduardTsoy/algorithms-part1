import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private DequeNode<Item> first = null, last = null;
    private int n = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        DequeNode<Item> newNode = new DequeNode<>(item);
        newNode.next = first;
        first = newNode;
        n++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        DequeNode<Item> newNode = new DequeNode<>(item);
        newNode.prev = last;
        last = newNode;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Item result = first.value;
        first = first.next;
        if (first != null) {
            first.prev = null;
        }
        return result;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (n == 0) {
            throw new NoSuchElementException();
        }
        Item result = last.value;
        last = last.prev;
        if (first != null) {
            first.next = null;
        }
        return result;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Item> {

        private DequeNode<Item> theNext;

        MyIterator() {
            theNext = first;
        }

        @Override
        public boolean hasNext() {
            return theNext != null;
        }

        @Override
        public Item next() {
            if (theNext == null) {
                throw new NoSuchElementException();
            }
            Item result = theNext.value;
            theNext = theNext.next;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }

    private static class DequeNode<Item> {

        Item value;
        DequeNode<Item> prev, next;

        DequeNode(Item value) {
            this.value = value;
        }
    }
}
