import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Nvidia nv = new Nvidia("optirun");
        ArrayList<Gpu> gpus = nv.fetchUpdates();
        gpus.forEach(p -> p.pprint());
    }
}
