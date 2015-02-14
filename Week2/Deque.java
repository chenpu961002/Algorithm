import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node pre;
        private Node next;
    }

    public Deque() {
        first = null;
        last = null;
        N = 0;
        assert check();
    }

    public boolean isEmpty() {
        assert check();
        return 0 == N;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (null == item) throw new java.lang.NullPointerException();
        if (0 == N) {
            Node newNode = new Node();
            newNode.item = item;
            newNode.pre = null;
            newNode.next = null;
            first = newNode;
            last = newNode;
        } else {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.pre = oldfirst;
            first.next = null;
            oldfirst.next = first;
        }
        N++;
        assert check();
    }

    public void addLast(Item item) {
        if (null == item) throw new java.lang.NullPointerException();
        if (0 == N) {
            Node newNode = new Node();
            newNode.item = item;
            newNode.pre = null;
            newNode.next = null;
            first = newNode;
            last = newNode;
        } else {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            last.pre = null;
            last.next = oldlast;
            oldlast.pre = last;
        }
        N++;
        assert check();
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;
        N--;
        if (0 == N) {
            first = null;
            last = null;
        } else {
            first = first.pre;
            first.next.pre = null;
            first.next = null;
        }
        assert check();
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = last.item;
        N--;
        if (0 == N) {
            first = null;
            last = null;
        } else {
            last = last.next;
            last.pre.next = null;
            last.pre = null;
        }
        assert check();
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return null != current;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.pre;
            return item;
        }
    }

    // private String toString() {
    // StringBuilder sb = new StringBuilder();
    // for (Item item : this) {
    // sb.append(item + " ");
    // }
    // return sb.toString();
    // }

    private boolean check() {
        if (N == 0) {
            if (first != null || last != null) return false;
        } else if (N == 1) {
            if (first == null) return false;
            if (last == null) return false;
            if (first.pre != null) return false;
            if (last.next != null) return false;
        } else {
            if (first.pre == null) return false;
            if (last.next == null) return false;
        }

        int numberOfNodes = 0;
        for (Node x = first; x != null; x = x.pre) {
            numberOfNodes++;
        }

        if (numberOfNodes != N) return false;
        return true;
    }

    public static void main(String[] args) {
        // Deque<String> d = new Deque<String>();
        // while (!StdIn.isEmpty()) {
        // String op = StdIn.readString();
        // if (op.equals("af")) {
        // String value = StdIn.readString();
        // d.addFirst(value);
        // } else if (op.equals("al")) {
        // String value = StdIn.readString();
        // d.addLast(value);
        // } else if (op.equals(("rf"))) StdOut.print(d.removeFirst() + " ");
        // else if (op.equals(("rl"))) StdOut.print(d.removeLast() + " ");
        // }
        // StdOut.println("(" + d.size() + " left on stack)");
        // StdOut.println(d);
    }
}
