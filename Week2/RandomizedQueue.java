import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N;
    private int tail; // the tail of the data(exclusive)

    public RandomizedQueue() {
        a = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        int index = 0;
        for (int i = 0; i < a.length; i++) {
            if (null != a[i]) {
                temp[index++] = a[i];
            }
        }
        a = temp;
        tail = index;
        assert tail == N;
    }

    public void enqueue(Item item) {
        if (null == item) throw new java.lang.NullPointerException();
        if (tail == a.length) resize(N == 0 ? 2 : 2 * N);
        a[tail++] = item;
        N++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int random = -1;
        while (true) {
            random = StdRandom.uniform(tail);
            if (a[random] != null) break;
        }
        Item item = a[random];
        a[random] = null;
        N--;
        if (N > 0 && N == a.length / 4) resize(a.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int random = -1;
        while (true) {
            random = StdRandom.uniform(tail);
            if (a[random] != null) break;
        }
        Item item = a[random];
        return item;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] orders;
        private int index;
        private int alreadyFound;
        public RandomizedQueueIterator() {
            orders = new int[tail];
            for (int i = 0; i < orders.length; i++) {
                orders[i] = i;
            }
            StdRandom.shuffle(orders);
            index = 0;
            alreadyFound = 0;
        }

        public boolean hasNext() {
            return alreadyFound < N;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            while (true) {
                Item item = a[orders[index++]];
                if (null != item) {
                    alreadyFound++;
                    return item;
                }
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // public String toString() {
    // StringBuilder sb = new StringBuilder();
    // sb.append(N + ":");
    // for (Item item : this) {
    // sb.append(item + " ");
    // }
    // return sb.toString();
    // }

    // public static void main(String[] args) {
    // RandomizedQueue<String> r = new RandomizedQueue<String>();
    // while (!StdIn.isEmpty()) {
    // String op = StdIn.readString();
    // if (op.equals("+")) {
    // String value = StdIn.readString();
    // System.out.println("enqueue " + value);
    // r.enqueue(value);
    // System.out.println(r);
    // } else if (op.equals("-")) {
    // r.dequeue();
    // System.out.println(r);
    // }
    // }
    // System.out.println("test");
    // System.out.println(r);
    // System.out.println(r);
    // System.out.println(r);
    // System.out.println(r);
    // }
}
