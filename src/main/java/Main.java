public class Main {
    public static void main(String[] args){
        System.out.println("Hello world!");

        Nvidia nv = new Nvidia("optirun");
        nv.fetchUpdates();
    }
}
