import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N;
    private int tail; // the tail of the data(exclusive)

    private RandomizedQueue() {
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
    }

    public void enqueue(Item item) {
        if (null == item) throw new java.lang.NullPointerException();
        if (tail == a.length) resize(2 * N);
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
        public RandomizedQueueIterator() {
            orders = new int[tail];
            for (int i = 0; i < orders.length; i++) {
                orders[i] = i;
            }
            StdRandom.shuffle(orders);
            index = 0;
        }

        public boolean hasNext() {
            return index < orders.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            while (true) {
                Item item = a[orders[index++]];
                if (null != item) return item;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }





}
