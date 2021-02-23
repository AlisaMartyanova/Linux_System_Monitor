import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        AMD amd = new AMD("sudo");
        ArrayList<Gpu> amdGpus = amd.fetchUpdates();
        amdGpus.forEach(p -> p.pprint());

        Nvidia nv = new Nvidia("optirun");
        ArrayList<Gpu> nvidiaGpus = nv.fetchUpdates();
        nvidiaGpus.forEach(p -> p.pprint());
    }
}
