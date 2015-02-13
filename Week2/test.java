public class test {
    public static void main(String[] args) {
        Object[] objlist = new Object[10];
        objlist[2] = 23;
        Object a = objlist[2];
        System.out.println(a);
        objlist[2] = null;
        System.out.println(a);
    }
}
