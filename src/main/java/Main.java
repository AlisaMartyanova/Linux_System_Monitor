import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        AMD amd = new AMD("sudo");
        ArrayList<Gpu> gpus = amd.fetchUpdates();
        gpus.forEach(p -> p.pprint());
    }
}