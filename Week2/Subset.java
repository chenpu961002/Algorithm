public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> r = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            r.enqueue(value);
        }
        int outputNum = Integer.valueOf(args[0]);
        int counter = 0;
        for (String s : r) {
            if (counter >= outputNum) break;
            System.out.println(s);
            counter++;
        }
    }
}
